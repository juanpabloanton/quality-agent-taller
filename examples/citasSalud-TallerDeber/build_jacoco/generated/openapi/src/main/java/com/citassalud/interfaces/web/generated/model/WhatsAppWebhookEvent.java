package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.citassalud.interfaces.web.generated.model.WhatsAppEntry;
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
 * WhatsAppWebhookEvent
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T14:56:29.709420100-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppWebhookEvent {

  private String _object;

  @Valid
  private List<@Valid WhatsAppEntry> entry = new ArrayList<>();

  public WhatsAppWebhookEvent() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WhatsAppWebhookEvent(List<@Valid WhatsAppEntry> entry) {
    this.entry = entry;
  }

  public WhatsAppWebhookEvent _object(String _object) {
    this._object = _object;
    return this;
  }

  /**
   * Get _object
   * @return _object
   */
  
  @Schema(name = "object", example = "whatsapp_business_account", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("object")
  public String getObject() {
    return _object;
  }

  public void setObject(String _object) {
    this._object = _object;
  }

  public WhatsAppWebhookEvent entry(List<@Valid WhatsAppEntry> entry) {
    this.entry = entry;
    return this;
  }

  public WhatsAppWebhookEvent addEntryItem(WhatsAppEntry entryItem) {
    if (this.entry == null) {
      this.entry = new ArrayList<>();
    }
    this.entry.add(entryItem);
    return this;
  }

  /**
   * Get entry
   * @return entry
   */
  @NotNull @Valid 
  @Schema(name = "entry", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("entry")
  public List<@Valid WhatsAppEntry> getEntry() {
    return entry;
  }

  public void setEntry(List<@Valid WhatsAppEntry> entry) {
    this.entry = entry;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppWebhookEvent whatsAppWebhookEvent = (WhatsAppWebhookEvent) o;
    return Objects.equals(this._object, whatsAppWebhookEvent._object) &&
        Objects.equals(this.entry, whatsAppWebhookEvent.entry);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_object, entry);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppWebhookEvent {\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    entry: ").append(toIndentedString(entry)).append("\n");
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

