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
 * WhatsAppTextBody
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T14:56:29.709420100-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppTextBody {

  private String body;

  public WhatsAppTextBody body(String body) {
    this.body = body;
    return this;
  }

  /**
   * Get body
   * @return body
   */
  
  @Schema(name = "body", example = "CANCELAR", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("body")
  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppTextBody whatsAppTextBody = (WhatsAppTextBody) o;
    return Objects.equals(this.body, whatsAppTextBody.body);
  }

  @Override
  public int hashCode() {
    return Objects.hash(body);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppTextBody {\n");
    sb.append("    body: ").append(toIndentedString(body)).append("\n");
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

