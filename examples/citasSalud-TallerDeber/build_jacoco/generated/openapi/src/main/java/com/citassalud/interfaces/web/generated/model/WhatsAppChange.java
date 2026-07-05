package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.citassalud.interfaces.web.generated.model.WhatsAppChangeValue;
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
 * WhatsAppChange
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T14:56:29.709420100-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppChange {

  private String field;

  private WhatsAppChangeValue value;

  public WhatsAppChange field(String field) {
    this.field = field;
    return this;
  }

  /**
   * Get field
   * @return field
   */
  
  @Schema(name = "field", example = "messages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("field")
  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public WhatsAppChange value(WhatsAppChangeValue value) {
    this.value = value;
    return this;
  }

  /**
   * Get value
   * @return value
   */
  @Valid 
  @Schema(name = "value", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("value")
  public WhatsAppChangeValue getValue() {
    return value;
  }

  public void setValue(WhatsAppChangeValue value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppChange whatsAppChange = (WhatsAppChange) o;
    return Objects.equals(this.field, whatsAppChange.field) &&
        Objects.equals(this.value, whatsAppChange.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(field, value);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppChange {\n");
    sb.append("    field: ").append(toIndentedString(field)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
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

