package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.citassalud.interfaces.web.generated.model.WhatsAppMessageContext;
import com.citassalud.interfaces.web.generated.model.WhatsAppTextBody;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WhatsAppInboundMessage
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T17:23:27.536903300-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppInboundMessage {

  private String from;

  private String id;

  private String timestamp;

  private WhatsAppMessageContext context;

  private WhatsAppTextBody text;

  /**
   * Gets or Sets type
   */
  public enum TypeEnum {
    TEXT("text");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private TypeEnum type;

  public WhatsAppInboundMessage() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WhatsAppInboundMessage(String from, String id, TypeEnum type) {
    this.from = from;
    this.id = id;
    this.type = type;
  }

  public WhatsAppInboundMessage from(String from) {
    this.from = from;
    return this;
  }

  /**
   * Número de WhatsApp del paciente en formato E.164 sin \"+\".
   * @return from
   */
  @NotNull 
  @Schema(name = "from", example = "573001234567", description = "Número de WhatsApp del paciente en formato E.164 sin \"+\".", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("from")
  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public WhatsAppInboundMessage id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Identificador del mensaje entrante.
   * @return id
   */
  @NotNull 
  @Schema(name = "id", description = "Identificador del mensaje entrante.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WhatsAppInboundMessage timestamp(String timestamp) {
    this.timestamp = timestamp;
    return this;
  }

  /**
   * Marca de tiempo Unix (segundos) provista por WhatsApp.
   * @return timestamp
   */
  
  @Schema(name = "timestamp", description = "Marca de tiempo Unix (segundos) provista por WhatsApp.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("timestamp")
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public WhatsAppInboundMessage context(WhatsAppMessageContext context) {
    this.context = context;
    return this;
  }

  /**
   * Get context
   * @return context
   */
  @Valid 
  @Schema(name = "context", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("context")
  public WhatsAppMessageContext getContext() {
    return context;
  }

  public void setContext(WhatsAppMessageContext context) {
    this.context = context;
  }

  public WhatsAppInboundMessage text(WhatsAppTextBody text) {
    this.text = text;
    return this;
  }

  /**
   * Get text
   * @return text
   */
  @Valid 
  @Schema(name = "text", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("text")
  public WhatsAppTextBody getText() {
    return text;
  }

  public void setText(WhatsAppTextBody text) {
    this.text = text;
  }

  public WhatsAppInboundMessage type(TypeEnum type) {
    this.type = type;
    return this;
  }

  /**
   * Get type
   * @return type
   */
  @NotNull 
  @Schema(name = "type", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("type")
  public TypeEnum getType() {
    return type;
  }

  public void setType(TypeEnum type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppInboundMessage whatsAppInboundMessage = (WhatsAppInboundMessage) o;
    return Objects.equals(this.from, whatsAppInboundMessage.from) &&
        Objects.equals(this.id, whatsAppInboundMessage.id) &&
        Objects.equals(this.timestamp, whatsAppInboundMessage.timestamp) &&
        Objects.equals(this.context, whatsAppInboundMessage.context) &&
        Objects.equals(this.text, whatsAppInboundMessage.text) &&
        Objects.equals(this.type, whatsAppInboundMessage.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, id, timestamp, context, text, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppInboundMessage {\n");
    sb.append("    from: ").append(toIndentedString(from)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    context: ").append(toIndentedString(context)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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

