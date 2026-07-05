package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Referencia al mensaje original al que el paciente respondió; se usa para correlacionar la respuesta con el Recordatorio/Cita origen (FR-009). 
 */

@Schema(name = "WhatsAppMessageContext", description = "Referencia al mensaje original al que el paciente respondió; se usa para correlacionar la respuesta con el Recordatorio/Cita origen (FR-009). ")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T17:23:27.536903300-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppMessageContext {

  private String id;

  public WhatsAppMessageContext id(String id) {
    this.id = id;
    return this;
  }

  /**
   * mensajeProveedorId del Recordatorio original.
   * @return id
   */
  
  @Schema(name = "id", description = "mensajeProveedorId del Recordatorio original.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppMessageContext whatsAppMessageContext = (WhatsAppMessageContext) o;
    return Objects.equals(this.id, whatsAppMessageContext.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppMessageContext {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

