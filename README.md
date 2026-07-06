# Informe final 3/3: Quality Agent — Citas Salud

## Resumen

El Quality Agent verifica una implementación Spring Boot contra tres pilares:
**pruebas**, **seguridad** y **criterios funcionales**. Es la última etapa del flujo:

```text
Discovery Agent  ->  Agile Delivery Team  ->  Quality Agent
problema validado     historia lista           evidencia de cumplimiento
```

El agente no aprueba por intuición. Reúne evidencia mediante Gradle, JaCoCo,
Semgrep MCP, los tests y el `spec.md`; después, el hook determinista
`.claude/hooks/quality-gate.py` decide si el trabajo se aprueba o se bloquea.

## Objetivo y alcance

El objetivo fue comprobar la historia de recordatorios y cancelación de citas por
WhatsApp del proyecto **Citas Salud**. La verificación debía demostrar que:

- todas las pruebas pasan y la cobertura global es al menos 80 %;
- no existen vulnerabilidades críticas, altas ni secretos expuestos;
- cada requisito `FR-xxx` del spec tiene código y pruebas que lo demuestran.

Quality recibe código ya implementado. Si algo falla, devuelve el trabajo al equipo
para corregir código, pruebas o especificación y ejecutar nuevamente el mismo gate.

## Relación con los proyectos anteriores

Delivery debería entregar criterios Gherkin que se convierten en requisitos y
escenarios de `specs/<feature>/spec.md`. Quality cierra la trazabilidad así:

```text
entrevista -> requisito -> US -> criterio Gherkin -> FR
            -> código -> prueba -> verification.json
```

> **Limitación comprobable:** Discovery y Delivery utilizan **Ciencia y Fe**, mientras
> este repositorio verifica **Citas Salud**. Los tres proyectos demuestran el flujo y
> sus contratos, pero las evidencias actuales no muestran una única historia
> atravesando las tres etapas. Para completar esa prueba integral se debe ejecutar
> Quality sobre una implementación de Ciencia y Fe.

## Los tres pilares del gate

| Pilar | Condición de aprobación | Fuente de evidencia |
|---|---|---|
| Pruebas | Todas pasan y cobertura global ≥ 80 % | Gradle + JaCoCo XML |
| Seguridad | 0 críticas, 0 altas y 0 secretos | Semgrep MCP + revisión de secretos |
| Criterios | Cada `FR-xxx` tiene una prueba concreta | `spec.md` cruzado con `src/test` |

La cobertura mide líneas ejecutadas; no demuestra por sí sola que se haya cumplido
lo prometido. Por eso el tercer pilar busca una prueba para cada requisito.

## Estructura relevante

```text
quality-agent-taller/
├── CLAUDE.md
├── .mcp.json
├── .claude/
│   ├── agents/
│   │   ├── auditor.md
│   │   └── security-reviewer.md
│   ├── commands/quality/
│   ├── hooks/quality-gate.py
│   ├── scripts/build-report.py
│   └── skills/quality/SKILL.md
├── examples/citasSalud - qualityAgent/
│   ├── specs/
│   ├── src/
│   └── quality-output/
└── evidenciaCitasSalud/
```

## Configuración de Semgrep MCP

`.mcp.json` conecta Claude Code con el servidor oficial de Semgrep. El token debe
definirse como variable de entorno; nunca se almacena en el repositorio.

```json
{
  "mcpServers": {
    "semgrep": {
      "command": "uvx",
      "args": ["--python", "3.13", "--with", "setuptools<81", "--from", "semgrep", "semgrep", "mcp"],
      "env": { "SEMGREP_APP_TOKEN": "${SEMGREP_APP_TOKEN}" }
    }
  }
}
```

El MCP obtiene hallazgos; el gate local conserva la decisión final.

![Semgrep MCP conectado](<evidenciaCitasSalud/16.- vinculacion con semgrep.png>)

## Prompts y comandos utilizados

La ejecución original registrada en las capturas utilizó:

```text
/quality:verify C:\Users\jpanton\Documents\Maestria\spring-boot-citasSalud
/quality:generate-report C:\Users\jpanton\Documents\Maestria\spring-boot-citasSalud
```

Para la copia incluida en este repositorio:

```text
/quality:verify "examples/citasSalud - qualityAgent"
/quality:generate-report "examples/citasSalud - qualityAgent"
```

| Comando | Por qué se ejecuta | Salida |
|---|---|---|
| `/quality:verify` | Ejecuta tests y cobertura, analiza seguridad y relaciona FR con pruebas. | `quality-output/verification.json` |
| `/quality:generate-report` | Presenta el mismo veredicto en HTML sin recalcularlo de otra manera. | `quality-output/report.html` |

Internamente, la verificación ejecuta en el proyecto:

```powershell
.\gradlew.bat clean test jacocoTestReport
```

Luego lee `build/reports/jacoco/test/jacocoTestReport.xml`, usa el subagente
`security-reviewer` y revisa cada requisito de
`specs/001-recordatorio-whatsapp-citas/spec.md`.

## Análisis de la ejecución

### 1. Verificación inicial

Se ejecutó `/quality:verify` y el agente cargó la skill, localizó el spec, lanzó
pruebas con JaCoCo y delegó la revisión de seguridad.

![Prompt inicial de quality verify](<evidenciaCitasSalud/1.-ejecucion del verify.png>)

### 2. Gate bloqueado

La primera ejecución fue bloqueada pese a que pruebas y seguridad estaban en estado
correcto. Los criterios **FR-002** y **FR-007** aparecieron como incumplidos.

![Gate bloqueado por criterios](<evidenciaCitasSalud/2.-bloqueo del gate.png>)

Log registrado:

```text
GATE DE CALIDAD: BLOQUEADO
PRUEBAS: OK
SEGURIDAD: OK
CRITERIOS: FR-002 incumple; FR-007 incumple
```

Esto demuestra la diferencia entre “los tests existentes pasan” y “existen tests
que demuestran todos los requisitos”. El gate devolvió el trabajo al equipo.

### 3. Corrección basada en `verification.json`

El equipo leyó los hallazgos, añadió pruebas y corrigió controles de seguridad. Las
capturas conservan el proceso de modificación y nueva ejecución:

![Lectura del verification para corregir](<evidenciaCitasSalud/7.- leyendo el verification.json para poder resolver el bloqueo de calidad del quality agent.png>)

![Correcciones realizadas por el equipo](<evidenciaCitasSalud/9.- realizo todas las incidencias y todas pasan.png>)

Entre los cambios documentados se encuentran pruebas para el contenido del mensaje,
exclusión de citas canceladas, validación HMAC, configuración de Spring Security y
persistencia real de la auditoría de recordatorios.

### 4. Gate aprobado

Al repetir `/quality:verify`, el hook devolvió `exit 0` y los tres pilares quedaron
aprobados.

![Gate de calidad aprobado](<evidenciaCitasSalud/12.- GATE DE CALIDAD APROBADO.png>)

La captura corresponde a una iteración aprobada de 49/49 pruebas. El artefacto
versionado actual contiene una revisión posterior con 53/53 pruebas.

### 5. Reporte final

El prompt `/quality:generate-report` creó el HTML a partir del JSON aprobado. Gate y
reporte usan la misma regla para evitar veredictos contradictorios.

![Generación del reporte final](<evidenciaCitasSalud/20.- reporte con los cambios de semgrep.png>)

## Resultado final verificable

`examples/citasSalud - qualityAgent/quality-output/verification.json` registra:

```text
GATE:      APROBADO
PRUEBAS:   53/53 aprobadas
COBERTURA: 92.61 % de líneas (umbral global: 80 %)
SEGURIDAD: 0 críticas, 0 altas, 0 secretos
CRITERIOS: 10/10 requisitos cumplen
SPEC:      specs/001-recordatorio-whatsapp-citas/spec.md
```

Las pruebas incluyen unidades, integración con H2, contratos, Cucumber, WireMock y
reglas de arquitectura. Cada entrada de `criteria` identifica el FR y explica qué
test demuestra su cumplimiento.

## Deuda técnica documentada

“Aprobado” no significa “sin riesgos”. El JSON conserva observaciones no
bloqueantes:

- una clase interna de infraestructura tiene 57.14 % de cobertura por clase;
- un número de WhatsApp puede aparecer en logs WARN y debería enmascararse;
- FR-006 puede reforzarse con una prueba BDD/integración del camino fallido;
- FR-007 recomienda probar también la invocación directa fuera del scheduler.

Esta transparencia permite aprobar la Definition of Done sin perder trabajo futuro.

## Evidencias auditables

| Evidencia | Ubicación | Qué demuestra |
|---|---|---|
| Especificación | `examples/citasSalud - qualityAgent/specs/001-recordatorio-whatsapp-citas/spec.md` | Fuente de FR y escenarios |
| Resultado estructurado | `examples/citasSalud - qualityAgent/quality-output/verification.json` | Métricas, hallazgos y trazabilidad |
| Reporte | `examples/citasSalud - qualityAgent/quality-output/report.html` | Presentación del veredicto |
| Capturas | `evidenciaCitasSalud/` | Prompts, bloqueo, corrección y aprobación |

## Reproducción

Con Claude Code:

```powershell
claude
# aprobar Semgrep MCP la primera vez
# ejecutar los dos prompts /quality:* mostrados arriba
```

Sin Claude Code, cuando Python está disponible:

```powershell
python .claude/hooks/quality-gate.py "examples/citasSalud - qualityAgent/quality-output/verification.json"
python .claude/scripts/build-report.py "examples/citasSalud - qualityAgent/quality-output/verification.json"
```

> En el entorno usado para editar este informe, `python` y `py` no estaban
> disponibles en `PATH`. Por honestidad, no se presenta una reejecución nueva como
> si hubiera ocurrido; los resultados proceden de las capturas y del JSON versionado.

## Conclusión

Quality demostró el ciclo completo **verificar → bloquear → corregir → volver a
verificar → aprobar**. Su contribución principal es impedir que una entrega sea
aceptada solo porque compila o porque algunos tests pasan: cada requisito debe estar
respaldado por pruebas, seguridad y evidencia reproducible.
