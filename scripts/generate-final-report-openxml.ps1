$ErrorActionPreference = 'Stop'
Add-Type -AssemblyName System.IO.Compression
Add-Type -AssemblyName System.IO.Compression.FileSystem
Add-Type -AssemblyName System.Drawing

$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$parent = (Resolve-Path (Join-Path $root '..')).Path
$output = Join-Path $root 'Informe_Final_Agentes_SDD.docx'

$script:body = New-Object System.Text.StringBuilder
$script:relationships = New-Object System.Collections.Generic.List[string]
$script:images = New-Object System.Collections.Generic.List[object]
$script:nextRel = 10
$script:nextDocPr = 1

function Escape-Xml([string]$value) {
    if ($null -eq $value) { return '' }
    # Windows PowerShell 5.1 puede interpretar un script UTF-8 sin BOM como
    # Windows-1252. Repara ese mojibake antes de escribir el XML del DOCX.
    if ($value.IndexOf([char]0x00C3) -ge 0 -or
        $value.IndexOf([char]0x00C2) -ge 0 -or
        $value.IndexOf([char]0x00E2) -ge 0) {
        $bytes = [System.Text.Encoding]::GetEncoding(1252).GetBytes($value)
        $value = [System.Text.Encoding]::UTF8.GetString($bytes)
    }
    return [System.Security.SecurityElement]::Escape($value)
}

function Add-Paragraph {
    param(
        [string]$Text,
        [string]$Style = 'Normal',
        [switch]$Bold,
        [switch]$Italic,
        [ValidateSet('left','center','right','both')][string]$Align = 'both',
        [int]$Before = 0,
        [int]$After = 120
    )
    $escaped = Escape-Xml $Text
    $rPr = ''
    if ($Bold -or $Italic) {
        $rPr = '<w:rPr>' + $(if ($Bold) {'<w:b/>'} else {''}) + $(if ($Italic) {'<w:i/>'} else {''}) + '</w:rPr>'
    }
    $xml = "<w:p><w:pPr><w:pStyle w:val=`"$Style`"/><w:jc w:val=`"$Align`"/><w:spacing w:before=`"$Before`" w:after=`"$After`"/></w:pPr><w:r>$rPr<w:t xml:space=`"preserve`">$escaped</w:t></w:r></w:p>"
    [void]$script:body.Append($xml)
}

function Add-Heading([string]$Text, [int]$Level = 1) {
    Add-Paragraph -Text $Text -Style "Heading$Level" -Align left -Before 180 -After 120
}

function Add-Bullet([string]$Text) {
    Add-Paragraph -Text "• $Text" -Style Normal -Align left -After 60
}

function Add-Numbered([int]$Number, [string]$Text) {
    Add-Paragraph -Text "$Number. $Text" -Style Normal -Align left -After 60
}

function Add-Code([string]$Text) {
    $lines = $Text -split "`r?`n"
    $runs = New-Object System.Text.StringBuilder
    for ($i=0; $i -lt $lines.Count; $i++) {
        if ($i -gt 0) { [void]$runs.Append('<w:r><w:br/></w:r>') }
        $line = Escape-Xml $lines[$i]
        [void]$runs.Append("<w:r><w:rPr><w:rFonts w:ascii=`"Consolas`" w:hAnsi=`"Consolas`"/><w:sz w:val=`"18`"/></w:rPr><w:t xml:space=`"preserve`">$line</w:t></w:r>")
    }
    [void]$script:body.Append("<w:p><w:pPr><w:pStyle w:val=`"Code`"/><w:shd w:val=`"clear`" w:fill=`"F2F2F2`"/><w:spacing w:before=`"80`" w:after=`"120`"/><w:ind w:left=`"180`" w:right=`"180`"/></w:pPr>$runs</w:p>")
}

function Add-PageBreak {
    [void]$script:body.Append('<w:p><w:r><w:br w:type="page"/></w:r></w:p>')
}

function Add-Table {
    param([string[]]$Headers, [object[][]]$Rows)
    $xml = New-Object System.Text.StringBuilder
    [void]$xml.Append('<w:tbl><w:tblPr><w:tblW w:w="0" w:type="auto"/><w:tblBorders><w:top w:val="single" w:sz="4" w:color="B7B7B7"/><w:left w:val="single" w:sz="4" w:color="B7B7B7"/><w:bottom w:val="single" w:sz="4" w:color="B7B7B7"/><w:right w:val="single" w:sz="4" w:color="B7B7B7"/><w:insideH w:val="single" w:sz="4" w:color="D9D9D9"/><w:insideV w:val="single" w:sz="4" w:color="D9D9D9"/></w:tblBorders></w:tblPr>')
    [void]$xml.Append('<w:tr>')
    foreach ($header in $Headers) {
        $h = Escape-Xml $header
        [void]$xml.Append("<w:tc><w:tcPr><w:shd w:val=`"clear`" w:fill=`"5B2C83`"/><w:tcMar><w:top w:w=`"80`" w:type=`"dxa`"/><w:left w:w=`"100`" w:type=`"dxa`"/><w:bottom w:w=`"80`" w:type=`"dxa`"/><w:right w:w=`"100`" w:type=`"dxa`"/></w:tcMar></w:tcPr><w:p><w:r><w:rPr><w:b/><w:color w:val=`"FFFFFF`"/></w:rPr><w:t>$h</w:t></w:r></w:p></w:tc>")
    }
    [void]$xml.Append('</w:tr>')
    foreach ($row in $Rows) {
        [void]$xml.Append('<w:tr>')
        foreach ($value in $row) {
            $v = Escape-Xml ([string]$value)
            [void]$xml.Append("<w:tc><w:tcPr><w:tcMar><w:top w:w=`"70`" w:type=`"dxa`"/><w:left w:w=`"100`" w:type=`"dxa`"/><w:bottom w:w=`"70`" w:type=`"dxa`"/><w:right w:w=`"100`" w:type=`"dxa`"/></w:tcMar></w:tcPr><w:p><w:r><w:rPr><w:sz w:val=`"18`"/></w:rPr><w:t xml:space=`"preserve`">$v</w:t></w:r></w:p></w:tc>")
        }
        [void]$xml.Append('</w:tr>')
    }
    [void]$xml.Append('</w:tbl><w:p/>')
    [void]$script:body.Append($xml.ToString())
}

function Add-Image {
    param([string]$Path, [string]$Caption)
    if (-not (Test-Path -LiteralPath $Path)) {
        Add-Paragraph -Text "[Evidencia no encontrada: $Path]" -Italic -Align left
        return
    }
    $ext = [System.IO.Path]::GetExtension($Path).TrimStart('.').ToLowerInvariant()
    if ($ext -eq 'jpeg') { $ext = 'jpg' }
    $relId = "rId$($script:nextRel)"
    $script:nextRel++
    $mediaName = "image$($script:nextDocPr).$ext"
    $docPrId = $script:nextDocPr
    $script:nextDocPr++
    $img = [System.Drawing.Image]::FromFile($Path)
    try {
        $maxCx = 5400000L
        $cx = [long]($img.Width / $img.HorizontalResolution * 914400)
        $cy = [long]($img.Height / $img.VerticalResolution * 914400)
        if ($cx -gt $maxCx) {
            $ratio = $maxCx / [double]$cx
            $cx = $maxCx
            $cy = [long]($cy * $ratio)
        }
    } finally { $img.Dispose() }
    $script:relationships.Add("<Relationship Id=`"$relId`" Type=`"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image`" Target=`"media/$mediaName`"/>")
    $script:images.Add([pscustomobject]@{ Path=$Path; Name=$mediaName })
    $nameEsc = Escape-Xml $mediaName
    $drawing = @"
<w:p><w:pPr><w:jc w:val="center"/><w:spacing w:before="120" w:after="40"/></w:pPr><w:r><w:drawing><wp:inline distT="0" distB="0" distL="0" distR="0"><wp:extent cx="$cx" cy="$cy"/><wp:effectExtent l="0" t="0" r="0" b="0"/><wp:docPr id="$docPrId" name="$nameEsc"/><wp:cNvGraphicFramePr><a:graphicFrameLocks xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" noChangeAspect="1"/></wp:cNvGraphicFramePr><a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"><a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture"><pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture"><pic:nvPicPr><pic:cNvPr id="0" name="$nameEsc"/><pic:cNvPicPr/></pic:nvPicPr><pic:blipFill><a:blip r:embed="$relId"/><a:stretch><a:fillRect/></a:stretch></pic:blipFill><pic:spPr><a:xfrm><a:off x="0" y="0"/><a:ext cx="$cx" cy="$cy"/></a:xfrm><a:prstGeom prst="rect"><a:avLst/></a:prstGeom></pic:spPr></pic:pic></a:graphicData></a:graphic></wp:inline></w:drawing></w:r></w:p>
"@
    [void]$script:body.Append($drawing)
    Add-Paragraph -Text $Caption -Style Caption -Italic -Align center -After 120
}

function Add-Hyperlink([string]$Label, [string]$Url) {
    $relId = "rId$($script:nextRel)"
    $script:nextRel++
    $urlEsc = Escape-Xml $Url
    $script:relationships.Add("<Relationship Id=`"$relId`" Type=`"http://schemas.openxmlformats.org/officeDocument/2006/relationships/hyperlink`" Target=`"$urlEsc`" TargetMode=`"External`"/>")
    $labelEsc = Escape-Xml $Label
    [void]$script:body.Append("<w:p><w:hyperlink r:id=`"$relId`"><w:r><w:rPr><w:color w:val=`"0563C1`"/><w:u w:val=`"single`"/></w:rPr><w:t>$labelEsc</w:t></w:r></w:hyperlink></w:p>")
}

function Add-ZipText($Zip, [string]$Name, [string]$Text) {
    $entry = $Zip.CreateEntry($Name, [System.IO.Compression.CompressionLevel]::Optimal)
    $stream = $entry.Open()
    $writer = New-Object System.IO.StreamWriter($stream, (New-Object System.Text.UTF8Encoding($false)))
    try { $writer.Write($Text) } finally { $writer.Dispose(); $stream.Dispose() }
}

# Portada
Add-Paragraph -Text 'INFORME FINAL' -Style Title -Align center -Before 1200 -After 180
Add-Paragraph -Text 'Aplicación de agentes de IA y Spec-Driven Development' -Style Subtitle -Align center -After 300
Add-Paragraph -Text 'Discovery Agent · Agile Delivery Team · Spec-Kit · Quality Agent' -Bold -Align center -After 400
Add-Paragraph -Text 'Caso de estudio: Ciencia y Fe / Citas Salud' -Align center -After 80
Add-Paragraph -Text 'Maestría en Software — Ingeniería de Software' -Align center -After 80
Add-Paragraph -Text 'Autor: Juan Pablo Antón' -Align center -After 80
Add-Paragraph -Text 'Fecha: 5 de julio de 2026' -Align center -After 80
Add-PageBreak

Add-Heading 'Contenido' 1
foreach ($line in @('1. Introducción','2. Análisis del Discovery Agent','3. Análisis del Agile Delivery Team','4. Análisis del desarrollo con SDD y Spec-Kit','5. Análisis del Quality Agent','6. Conclusiones','7. Recomendaciones','8. Anexos: repositorios de GitHub')) { Add-Paragraph -Text $line -Align left -After 50 }
Add-PageBreak

Add-Heading '1. Introducción' 1
Add-Paragraph 'Este informe presenta el uso coordinado de tres agentes de inteligencia artificial y una metodología de desarrollo dirigida por especificaciones. El flujo inicia con Discovery Agent, que convierte entrevistas en evidencia de producto; continúa con Agile Delivery Team, que transforma esa evidencia en historias refinadas, arquitectura y planificación; aplica Spec-Driven Development (SDD) con Spec-Kit para definir e implementar una funcionalidad; y finaliza con Quality Agent, que valida la Definition of Done mediante pruebas, cobertura, seguridad y cumplimiento de requisitos.'
Add-Paragraph 'La cadena busca evitar tres fallos frecuentes: construir sin entender el problema, desarrollar historias que todavía no están listas y aprobar código que no demuestra los criterios funcionales. Cada etapa produce artefactos consumibles por la siguiente y utiliza gates deterministas para impedir que una deficiencia se oculte mediante una descripción convincente.'
Add-Table @('Etapa','Pregunta principal','Salida') @(
    @('Discovery Agent','¿Qué problema real debe resolverse?','Personas, requisitos, historias y MVP Canvas'),
    @('Agile Delivery Team','¿Cómo se organizará la entrega?','Backlog, Gherkin, arquitectura, ADR y Sprint Plan'),
    @('Desarrollo con SDD','¿Cómo se convierte la historia en una implementación trazable?','Spec, plan, tareas, código y pruebas'),
    @('Quality Agent','¿La implementación cumple la DoD?','verification.json, gate y reporte HTML')
)
Add-Paragraph -Text 'Alcance: Discovery y Agile Delivery documentan Ciencia y Fe; SDD y Quality documentan Citas Salud. Se demuestra el flujo y sus contratos, pero no una misma historia atravesando todas las etapas.' -Italic

Add-Heading '2. Análisis del Discovery Agent' 1
Add-Heading '2.1 Cómo se utilizó el agente' 2
Add-Paragraph 'El agente se ejecutó sobre discoveries/cienciayfe. Leyó entrevistas de la rectora, la secretaria y el desarrollador, y extrajo personas, stakeholders, dolores y requisitos sin convertir supuestos en hechos. Posteriormente generó historias INVEST, un MVP Canvas e hipótesis falsables.'
Add-Paragraph -Text 'Prompts utilizados:' -Bold -Align left
Add-Code "/discovery:analyze discoveries/cienciayfe`n/discovery:generate-mvp discoveries/cienciayfe`n/discovery:experiments discoveries/cienciayfe`n/discovery:report discoveries/cienciayfe"
Add-Table @('Comando','Propósito','Artefacto') @(
    @('/discovery:analyze','Extraer evidencia sin invención','personas.md, requisitos.md, evidence-map.json'),
    @('/discovery:generate-mvp','Priorizar valor y generar historias','user-stories.md, mvp-canvas.md'),
    @('/discovery:experiments','Diseñar hipótesis falsables','hypotheses.md, experiment-board.json'),
    @('/discovery:report','Generar reporte visual','report.html')
)
Add-Heading '2.2 Resultados y evidencia' 2
Add-Paragraph 'El primer intento fue bloqueado porque el mapa citaba secretaria.md, pero esa entrevista no existía. El readiness gate informó que la persona Secretaria estaba sustentada solo por referencias de terceros. Así se evitó generar un MVP con evidencia incompleta.'
Add-Image (Join-Path $parent 'discovery-agent-taller\discoveries\cienciayfe\evidencia - fotos\Antes.png') 'Figura 1. Readiness gate bloqueado por entrevista faltante.'
Add-Code "Persona “Secretaria” no tiene una entrevista en primera persona en disco.`nAcción: levanta más evidencia (agrega/entrevista) y reintenta."
Add-Paragraph 'Después de incorporar la entrevista se repitió /discovery:generate-mvp. El agente generó user-stories.md y mvp-canvas.md. US-01 propone generar el cuadro trimestral con nombres de materias tomados de la base de datos y conserva trazabilidad hacia la entrevista de la Secretaria.'
Add-Image (Join-Path $parent 'discovery-agent-taller\discoveries\cienciayfe\evidencia - fotos\despues.png') 'Figura 2. Generación del MVP después de corregir la evidencia.'
Add-Code "Cadena documental:`nentrevista -> dolor “materias quemadas” -> requisito R-01 -> historia US-01"

Add-Heading '3. Análisis del Agile Delivery Team' 1
Add-Heading '3.1 Cómo se utilizó el agente' 2
Add-Paragraph 'Los artefactos de Discovery se colocaron en deliveries/cienciayfe/inbox. El Product Owner generó épicas y backlog; el Developer refinó historias con criterios Gherkin, dependencias y estimaciones; el Architect produjo arquitectura y ADR; y el Scrum Master seleccionó trabajo listo según una capacidad de 20 puntos.'
Add-Paragraph -Text 'Prompts utilizados:' -Bold -Align left
Add-Code "/delivery:generate-epics deliveries/cienciayfe`n/delivery:generate-stories deliveries/cienciayfe`n/delivery:architecture deliveries/cienciayfe`n/delivery:sprint-plan deliveries/cienciayfe 20`n/delivery:report deliveries/cienciayfe"
Add-Heading '3.2 Resultados y evidencia' 2
Add-Paragraph 'Para comprobar el gate se introdujo US-99: una historia vaga, sin criterios de aceptación, estimada en 13 puntos y dependiente de US-404, que no existía. Al ejecutar generate-stories, el orquestador detectó el defecto y lanzó al Developer para corregir el backlog.'
Add-Image (Join-Path $parent 'agile-delivery-team-taller\deliveries\evidencia\2.-genero bloqueo por historia problematica es la que agregamos.png') 'Figura 3. Detección de US-99 y ejecución del subagente Developer.'
Add-Code "US-99: vaga, sin AC, 13 pts, depende de US-404 inexistente, preguntas abiertas.`nAcción: refinar, dividir o retirar antes de superar Definition of Ready."
Add-Paragraph 'El resultado final registró siete historias listas y 34 puntos. US-01 conservó trazabilidad hacia R-01, fue estimada en cinco puntos y quedó dependiente de US-07. Sus criterios se expresaron como Given/When/Then para convertirse en pruebas.'
Add-Image (Join-Path $parent 'agile-delivery-team-taller\deliveries\evidencia\4.-fin de la ejecucion.png') 'Figura 4. Finalización después de superar el gate DoR/INVEST.'

Add-Heading '4. Análisis del desarrollo con SDD y Spec-Kit' 1
Add-Heading '4.1 Funcionalidad implementada' 2
Add-Paragraph 'La funcionalidad de Citas Salud envía un recordatorio por WhatsApp 24 horas antes del turno y permite cancelar respondiendo la palabra exacta CANCELAR hasta dos horas antes. La cancelación libera la franja y confirma el resultado al paciente.'
Add-Paragraph -Text 'Secuencia de prompts de Spec-Kit documentada por los artefactos:' -Bold -Align left
Add-Code "/speckit.specify [US-03 y criterios del recordatorio]`n/speckit.clarify`n/speckit.plan`n/speckit.tasks`n/speckit.implement"
Add-Heading '4.2 Especificación antes del código' 2
Add-Paragraph 'spec.md registró dos historias, escenarios independientes, casos límite, criterios medibles y diez requisitos funcionales. La aclaración resolvió la palabra exacta para cancelar, el límite de dos horas y la asociación de la respuesta con el hilo específico.'
Add-Code "Log de spec.md:`nFR-001: envío 24 horas antes.`nFR-003/FR-003a: CANCELAR exacto o respuesta no reconocida.`nFR-008: cancelación permitida hasta 2 horas antes.`nFR-009: asociación con el hilo específico."
Add-Heading '4.3 Plan técnico y diseño' 2
Add-Paragraph 'plan.md aplicó Clean Architecture: domain contiene entidades y políticas; application contiene los casos de uso; infrastructure implementa JPA, WhatsApp Cloud API y el scheduler; interfaces expone el webhook. El contrato OpenAPI se definió antes del controlador. research.md justificó las decisiones de proveedor, scheduling, persistencia, BDD, ArchUnit, OpenAPI Generator y JaCoCo.'
Add-Table @('Decisión','Implementación') @(
    @('Arquitectura limpia','Puertos y adaptadores; dominio sin Spring/JPA/HTTP'),
    @('API First','whatsapp-webhook.openapi.yaml y controlador generado'),
    @('BDD','Cucumber + JUnit 5'),
    @('Persistencia','Adaptadores JPA probados con H2'),
    @('Seguridad','HMAC, comparación constante y Spring Security'),
    @('Calidad','JaCoCo, ArchUnit y gate de cobertura')
)
Add-Heading '4.4 Implementación guiada por tareas y pruebas' 2
Add-Paragraph 'tasks.md descompuso el trabajo en T001–T040. T016–T020 y T027–T031 exigieron escribir primero pruebas BDD, unitarias, de integración y contrato. Después se implementaron casos de uso, repositorios, adaptador de WhatsApp, scheduler y webhook. Las tareas aparecen completadas.'
Add-Code "[X] T016-T020: pruebas de US1 antes de EnviarRecordatorioUseCase.`n[X] T021-T026: recordatorio y auditoría.`n[X] T027-T031: pruebas de US2 antes de CancelarCitaUseCase.`n[X] T032-T036: webhook, cancelación, confirmación y aclaración.`n[X] T038-T040: check, arquitectura y flujo manual."
Add-Paragraph 'La implementación mantiene trazabilidad: RecordatorioSchedulerJob activa EnviarRecordatorioUseCase; WhatsAppCloudApiAdapter implementa el puerto de mensajería; WhatsAppWebhookController delega la cancelación; RecordatorioJpaRepositoryAdapter persiste la auditoría; y VentanaCancelacionPolicy centraliza la regla de dos horas.'

Add-Heading '5. Análisis del Quality Agent' 1
Add-Heading '5.1 Cómo se utilizó el agente' 2
Add-Paragraph 'Quality leyó el spec, ejecutó Gradle y JaCoCo, delegó seguridad a Semgrep MCP y buscó una prueba concreta para cada FR. Escribió quality-output/verification.json y el hook quality-gate.py decidió el veredicto.'
Add-Paragraph -Text 'Prompts utilizados:' -Bold -Align left
Add-Code "/quality:verify C:\Users\jpanton\Documents\Maestria\spring-boot-citasSalud`n/quality:generate-report C:\Users\jpanton\Documents\Maestria\spring-boot-citasSalud"
Add-Image (Join-Path $root 'evidenciaCitasSalud\1.-ejecucion del verify.png') 'Figura 5. Inicio de /quality:verify.'
Add-Heading '5.2 Validación de la Definition of Done' 2
Add-Paragraph 'La primera ejecución fue bloqueada: pruebas y seguridad estaban correctas, pero FR-002 y FR-007 carecían de evidencia suficiente. Esto demuestra que cobertura alta no sustituye la trazabilidad requisito-prueba.'
Add-Image (Join-Path $root 'evidenciaCitasSalud\2.-bloqueo del gate.png') 'Figura 6. Gate bloqueado por FR-002 y FR-007.'
Add-Code "GATE DE CALIDAD: BLOQUEADO`nPRUEBAS: OK`nSEGURIDAD: OK`nCRITERIOS: FR-002 incumple; FR-007 incumple"
Add-Paragraph 'Después de corregir código y pruebas se repitió el prompt. La captura aprobada corresponde a 49/49 pruebas; verification.json contiene una revisión posterior con 53/53 pruebas, 92,61 % de cobertura, cero hallazgos críticos/altos/secretos y 10/10 criterios.'
Add-Image (Join-Path $root 'evidenciaCitasSalud\12.- GATE DE CALIDAD APROBADO.png') 'Figura 7. Gate aprobado después de las correcciones.'
Add-Table @('Pilar DoD','Resultado final','Estado') @(
    @('Pruebas','53/53; cobertura global 92,61 %','APROBADO'),
    @('Seguridad','0 críticas, 0 altas, 0 secretos','APROBADO'),
    @('Criterios','10/10 requisitos con evidencia','APROBADO')
)
Add-Paragraph 'El reporte se generó desde verification.json y conserva el mismo veredicto. También documenta deuda no bloqueante: cobertura baja en una clase interna, posible PII en logs y escenarios adicionales para FR-006 y FR-007.'
Add-Image (Join-Path $root 'evidenciaCitasSalud\20.- reporte con los cambios de semgrep.png') 'Figura 8. Generación del reporte final aprobado.'

Add-Heading '6. Conclusiones' 1
$conclusions = @(
    'Discovery demostró que la calidad empieza en la evidencia: sin la entrevista de la Secretaria, el MVP fue bloqueado.',
    'Agile Delivery convirtió necesidades en historias estimadas y testeables; el gate DoR/INVEST rechazó una historia defectuosa.',
    'Spec-Kit mantuvo la especificación como fuente de verdad y conectó requisitos, plan, tareas, código y pruebas.',
    'Quality comprobó la DoD: 53/53 pruebas, 92,61 % de cobertura y 10/10 criterios.',
    'Los gates deterministas reducen el riesgo de aprobaciones subjetivas por parte de los agentes.',
    'La demostración no es aún extremo a extremo sobre un caso único: Ciencia y Fe cubre Discovery/Delivery y Citas Salud cubre SDD/Quality.'
)
for ($i=0; $i -lt $conclusions.Count; $i++) { Add-Numbered ($i+1) $conclusions[$i] }

Add-Heading '7. Recomendaciones' 1
foreach ($item in @(
    'Implementar una historia de Ciencia y Fe con Spec-Kit y Quality para cerrar una traza única de extremo a extremo.',
    'Conservar identificadores de origen: entrevista, dolor, R-xx, US-xx, FR-xxx y prueba.',
    'Automatizar los gates en integración continua.',
    'Versionar verification.json y los reportes junto con el commit exacto evaluado.',
    'Enmascarar PII en logs, elevar cobertura por clase y añadir pruebas end-to-end de caminos fallidos.',
    'Mantener secretos en variables de entorno y rotarlos según política.',
    'Repetir Quality después de cambios en el spec, dependencias, seguridad o dominio.'
)) { Add-Bullet $item }

Add-Heading '8. Anexos: enlaces a los repositorios de GitHub' 1
Add-Hyperlink 'Discovery Agent — https://github.com/juanpabloanton/discovery-agent-taller' 'https://github.com/juanpabloanton/discovery-agent-taller'
Add-Hyperlink 'Agile Delivery Team — https://github.com/juanpabloanton/agile-delivery-team-taller' 'https://github.com/juanpabloanton/agile-delivery-team-taller'
Add-Hyperlink 'Quality Agent — https://github.com/juanpabloanton/quality-agent-taller' 'https://github.com/juanpabloanton/quality-agent-taller'
Add-Heading '8.1 Artefactos principales' 2
Add-Table @('Proyecto','Artefactos') @(
    @('Discovery','interviews/, evidence-map.json, requisitos.md, user-stories.md, mvp-canvas.md'),
    @('Agile Delivery','inbox/, backlog.json, stories.md, architecture.md, adr/, sprint-plan.json'),
    @('SDD / Spec-Kit','spec.md, research.md, plan.md, data-model.md, contracts/, tasks.md, quickstart.md'),
    @('Quality','verification.json, report.html, evidenciaCitasSalud/')
)

$sectPr = '<w:sectPr><w:pgSz w:w="12240" w:h="15840"/><w:pgMar w:top="1417" w:right="1417" w:bottom="1417" w:left="1701" w:header="708" w:footer="708" w:gutter="0"/></w:sectPr>'
$documentXml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><w:document xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main" xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture"><w:body>' + $script:body.ToString() + $sectPr + '</w:body></w:document>'

$stylesXml = @'
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:styles xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
 <w:docDefaults><w:rPrDefault><w:rPr><w:rFonts w:ascii="Aptos" w:hAnsi="Aptos"/><w:sz w:val="22"/><w:lang w:val="es-EC"/></w:rPr></w:rPrDefault><w:pPrDefault><w:pPr><w:spacing w:after="120" w:line="276" w:lineRule="auto"/></w:pPr></w:pPrDefault></w:docDefaults>
 <w:style w:type="paragraph" w:default="1" w:styleId="Normal"><w:name w:val="Normal"/><w:qFormat/></w:style>
 <w:style w:type="paragraph" w:styleId="Title"><w:name w:val="Title"/><w:basedOn w:val="Normal"/><w:qFormat/><w:rPr><w:rFonts w:ascii="Aptos Display"/><w:b/><w:color w:val="5B2C83"/><w:sz w:val="48"/></w:rPr></w:style>
 <w:style w:type="paragraph" w:styleId="Subtitle"><w:name w:val="Subtitle"/><w:basedOn w:val="Normal"/><w:qFormat/><w:rPr><w:rFonts w:ascii="Aptos Display"/><w:color w:val="7030A0"/><w:sz w:val="34"/></w:rPr></w:style>
 <w:style w:type="paragraph" w:styleId="Heading1"><w:name w:val="heading 1"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:keepLines/><w:outlineLvl w:val="0"/></w:pPr><w:rPr><w:rFonts w:ascii="Aptos Display"/><w:b/><w:color w:val="5B2C83"/><w:sz w:val="32"/></w:rPr></w:style>
 <w:style w:type="paragraph" w:styleId="Heading2"><w:name w:val="heading 2"/><w:basedOn w:val="Normal"/><w:next w:val="Normal"/><w:qFormat/><w:pPr><w:keepNext/><w:keepLines/><w:outlineLvl w:val="1"/></w:pPr><w:rPr><w:rFonts w:ascii="Aptos Display"/><w:b/><w:color w:val="7030A0"/><w:sz w:val="26"/></w:rPr></w:style>
 <w:style w:type="paragraph" w:styleId="Caption"><w:name w:val="Caption"/><w:basedOn w:val="Normal"/><w:qFormat/><w:rPr><w:i/><w:color w:val="666666"/><w:sz w:val="18"/></w:rPr></w:style>
 <w:style w:type="paragraph" w:styleId="Code"><w:name w:val="Code"/><w:basedOn w:val="Normal"/><w:rPr><w:rFonts w:ascii="Consolas" w:hAnsi="Consolas"/><w:sz w:val="18"/></w:rPr></w:style>
</w:styles>
'@

$contentTypes = @'
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types">
 <Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/>
 <Default Extension="xml" ContentType="application/xml"/>
 <Default Extension="png" ContentType="image/png"/>
 <Default Extension="jpg" ContentType="image/jpeg"/>
 <Override PartName="/word/document.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml"/>
 <Override PartName="/word/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml"/>
 <Override PartName="/docProps/core.xml" ContentType="application/vnd.openxmlformats-package.core-properties+xml"/>
 <Override PartName="/docProps/app.xml" ContentType="application/vnd.openxmlformats-officedocument.extended-properties+xml"/>
</Types>
'@

$rootRels = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="word/document.xml"/><Relationship Id="rId2" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties" Target="docProps/core.xml"/><Relationship Id="rId3" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties" Target="docProps/app.xml"/></Relationships>'
$docRels = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/>' + ($script:relationships -join '') + '</Relationships>'
$authorName = Escape-Xml 'Juan Pablo Antón'
$coreXml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"><dc:title>Informe final: agentes de IA y SDD</dc:title><dc:creator>' + $authorName + '</dc:creator><dc:subject>Discovery, Agile Delivery, Spec-Kit y Quality</dc:subject><dcterms:created xsi:type="dcterms:W3CDTF">2026-07-05T00:00:00Z</dcterms:created></cp:coreProperties>'
$appXml = '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><Properties xmlns="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties" xmlns:vt="http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes"><Application>Microsoft Office Word</Application><AppVersion>16.0000</AppVersion></Properties>'

if (Test-Path -LiteralPath $output) { Remove-Item -LiteralPath $output -Force }
$fileStream = [System.IO.File]::Open($output, [System.IO.FileMode]::CreateNew)
try {
    $zip = New-Object System.IO.Compression.ZipArchive($fileStream, [System.IO.Compression.ZipArchiveMode]::Create, $false)
    try {
        Add-ZipText $zip '[Content_Types].xml' $contentTypes
        Add-ZipText $zip '_rels/.rels' $rootRels
        Add-ZipText $zip 'word/document.xml' $documentXml
        Add-ZipText $zip 'word/styles.xml' $stylesXml
        Add-ZipText $zip 'word/_rels/document.xml.rels' $docRels
        Add-ZipText $zip 'docProps/core.xml' $coreXml
        Add-ZipText $zip 'docProps/app.xml' $appXml
        foreach ($image in $script:images) {
            $entry = $zip.CreateEntry("word/media/$($image.Name)", [System.IO.Compression.CompressionLevel]::Optimal)
            $entryStream = $entry.Open()
            $source = [System.IO.File]::OpenRead($image.Path)
            try { $source.CopyTo($entryStream) } finally { $source.Dispose(); $entryStream.Dispose() }
        }
    } finally { $zip.Dispose() }
} finally { $fileStream.Dispose() }

Write-Output $output
