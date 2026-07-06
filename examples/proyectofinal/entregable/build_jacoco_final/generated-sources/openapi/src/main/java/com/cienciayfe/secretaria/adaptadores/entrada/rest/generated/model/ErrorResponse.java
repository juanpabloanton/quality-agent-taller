package com.cienciayfe.secretaria.adaptadores.entrada.rest.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ErrorResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T18:54:39.747842900-05:00[America/Guayaquil]", comments = "Generator version: 7.6.0")
public class ErrorResponse {

  private String codigo;

  private String mensaje;

  private String detalle = null;

  public ErrorResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ErrorResponse(String codigo, String mensaje) {
    this.codigo = codigo;
    this.mensaje = mensaje;
  }

  public ErrorResponse codigo(String codigo) {
    this.codigo = codigo;
    return this;
  }

  /**
   * Código interno del error
   * @return codigo
  */
  @NotNull 
  @Schema(name = "codigo", example = "ARCHIVO_VACIO", description = "Código interno del error", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("codigo")
  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public ErrorResponse mensaje(String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * Descripción del error orientada al usuario
   * @return mensaje
  */
  @NotNull 
  @Schema(name = "mensaje", example = "El archivo no puede estar vacío", description = "Descripción del error orientada al usuario", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mensaje")
  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  public ErrorResponse detalle(String detalle) {
    this.detalle = detalle;
    return this;
  }

  /**
   * Información técnica adicional (opcional)
   * @return detalle
  */
  
  @Schema(name = "detalle", description = "Información técnica adicional (opcional)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("detalle")
  public String getDetalle() {
    return detalle;
  }

  public void setDetalle(String detalle) {
    this.detalle = detalle;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.codigo, errorResponse.codigo) &&
        Objects.equals(this.mensaje, errorResponse.mensaje) &&
        Objects.equals(this.detalle, errorResponse.detalle);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigo, mensaje, detalle);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    sb.append("    codigo: ").append(toIndentedString(codigo)).append("\n");
    sb.append("    mensaje: ").append(toIndentedString(mensaje)).append("\n");
    sb.append("    detalle: ").append(toIndentedString(detalle)).append("\n");
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

