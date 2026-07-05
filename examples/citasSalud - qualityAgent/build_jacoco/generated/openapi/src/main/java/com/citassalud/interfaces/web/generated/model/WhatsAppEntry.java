package com.citassalud.interfaces.web.generated.model;

import java.net.URI;
import java.util.Objects;
import com.citassalud.interfaces.web.generated.model.WhatsAppChange;
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
 * WhatsAppEntry
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T17:23:27.536903300-05:00[America/Guayaquil]", comments = "Generator version: 7.10.0")
public class WhatsAppEntry {

  private String id;

  @Valid
  private List<@Valid WhatsAppChange> changes = new ArrayList<>();

  public WhatsAppEntry() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public WhatsAppEntry(List<@Valid WhatsAppChange> changes) {
    this.changes = changes;
  }

  public WhatsAppEntry id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
   */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public WhatsAppEntry changes(List<@Valid WhatsAppChange> changes) {
    this.changes = changes;
    return this;
  }

  public WhatsAppEntry addChangesItem(WhatsAppChange changesItem) {
    if (this.changes == null) {
      this.changes = new ArrayList<>();
    }
    this.changes.add(changesItem);
    return this;
  }

  /**
   * Get changes
   * @return changes
   */
  @NotNull @Valid 
  @Schema(name = "changes", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("changes")
  public List<@Valid WhatsAppChange> getChanges() {
    return changes;
  }

  public void setChanges(List<@Valid WhatsAppChange> changes) {
    this.changes = changes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WhatsAppEntry whatsAppEntry = (WhatsAppEntry) o;
    return Objects.equals(this.id, whatsAppEntry.id) &&
        Objects.equals(this.changes, whatsAppEntry.changes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, changes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class WhatsAppEntry {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    changes: ").append(toIndentedString(changes)).append("\n");
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

