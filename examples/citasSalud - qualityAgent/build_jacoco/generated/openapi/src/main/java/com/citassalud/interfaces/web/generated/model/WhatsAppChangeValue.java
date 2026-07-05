package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.citassalud.interfaces.web.generated.model.WhatsAppInboundMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * WhatsAppChangeValue
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T17:23:27.536903300-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppChangeValue {

  @Valid
  private List<@Valid WhatsAppInboundMessage> messages = new ArrayList<>();

  public WhatsAppChangeValue messages(List<@Valid WhatsAppInboundMessage> messages) {
    this.messages = messages;
    return this;
  }

  public WhatsAppChangeValue addMessagesItem(WhatsAppInboundMessage messagesItem) {
    if (this.messages == null) {
      this.messages = new ArrayList<>();
    }
    this.messages.add(messagesItem);
    return this;
  }

  /**
   * Get messages
   * @return messages
   */
  @Valid 
  @Schema(name = "messages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("messages")
  public List<@Valid WhatsAppInboundMessage> getMessages() {
    return messages;
  }

  public void setMessages(List<@Valid WhatsAppInboundMessage> messages) {
    this.messages = messages;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppChangeValue whatsAppChangeValue = (WhatsAppChangeValue) o;
    return Objects.equals(this.messages, whatsAppChangeValue.messages);
  }

  @Override
  public int hashCode() {
    return Objects.hash(messages);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppChangeValue {\n");
    sb.append("    messages: ").append(toIndentedString(messages)).append("\n");
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

