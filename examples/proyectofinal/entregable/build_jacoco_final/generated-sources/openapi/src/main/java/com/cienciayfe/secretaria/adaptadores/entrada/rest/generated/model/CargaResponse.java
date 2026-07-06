package com.cienciayfe.secretaria.adaptadores.entrada.rest.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CargaResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T18:54:39.747842900-05:00[America/Guayaquil]", comments = "Generator version: 7.6.0")
public class CargaResponse {

  private String codigoPeriodo;

  private String nombreArchivo;

  private Long tamanioBytes;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaCarga;

  private String usuarioResponsable;

  private String mensaje;

  public CargaResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CargaResponse(String codigoPeriodo, String nombreArchivo, Long tamanioBytes, OffsetDateTime fechaCarga, String usuarioResponsable, String mensaje) {
    this.codigoPeriodo = codigoPeriodo;
    this.nombreArchivo = nombreArchivo;
    this.tamanioBytes = tamanioBytes;
    this.fechaCarga = fechaCarga;
    this.usuarioResponsable = usuarioResponsable;
    this.mensaje = mensaje;
  }

  public CargaResponse codigoPeriodo(String codigoPeriodo) {
    this.codigoPeriodo = codigoPeriodo;
    return this;
  }

  /**
   * Código del período para el que se realizó la carga
   * @return codigoPeriodo
  */
  @NotNull 
  @Schema(name = "codigoPeriodo", example = "2025-II", description = "Código del período para el que se realizó la carga", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("codigoPeriodo")
  public String getCodigoPeriodo() {
    return codigoPeriodo;
  }

  public void setCodigoPeriodo(String codigoPeriodo) {
    this.codigoPeriodo = codigoPeriodo;
  }

  public CargaResponse nombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
    return this;
  }

  /**
   * Nombre original del archivo cargado
   * @return nombreArchivo
  */
  @NotNull 
  @Schema(name = "nombreArchivo", example = "datos_academicos_2025II.csv", description = "Nombre original del archivo cargado", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("nombreArchivo")
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public CargaResponse tamanioBytes(Long tamanioBytes) {
    this.tamanioBytes = tamanioBytes;
    return this;
  }

  /**
   * Tamaño del archivo en bytes
   * @return tamanioBytes
  */
  @NotNull 
  @Schema(name = "tamanioBytes", example = "45312", description = "Tamaño del archivo en bytes", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("tamanioBytes")
  public Long getTamanioBytes() {
    return tamanioBytes;
  }

  public void setTamanioBytes(Long tamanioBytes) {
    this.tamanioBytes = tamanioBytes;
  }

  public CargaResponse fechaCarga(OffsetDateTime fechaCarga) {
    this.fechaCarga = fechaCarga;
    return this;
  }

  /**
   * Fecha y hora en que se registró la carga
   * @return fechaCarga
  */
  @NotNull @Valid 
  @Schema(name = "fechaCarga", description = "Fecha y hora en que se registró la carga", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("fechaCarga")
  public OffsetDateTime getFechaCarga() {
    return fechaCarga;
  }

  public void setFechaCarga(OffsetDateTime fechaCarga) {
    this.fechaCarga = fechaCarga;
  }

  public CargaResponse usuarioResponsable(String usuarioResponsable) {
    this.usuarioResponsable = usuarioResponsable;
    return this;
  }

  /**
   * Usuario que realizó la carga
   * @return usuarioResponsable
  */
  @NotNull 
  @Schema(name = "usuarioResponsable", example = "secretaria01", description = "Usuario que realizó la carga", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("usuarioResponsable")
  public String getUsuarioResponsable() {
    return usuarioResponsable;
  }

  public void setUsuarioResponsable(String usuarioResponsable) {
    this.usuarioResponsable = usuarioResponsable;
  }

  public CargaResponse mensaje(String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * Mensaje de confirmación
   * @return mensaje
  */
  @NotNull 
  @Schema(name = "mensaje", example = "Información académica del período 2025-II registrada exitosamente como fuente central.", description = "Mensaje de confirmación", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("mensaje")
  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String mensaje) {
    this.mensaje = mensaje;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CargaResponse cargaResponse = (CargaResponse) o;
    return Objects.equals(this.codigoPeriodo, cargaResponse.codigoPeriodo) &&
        Objects.equals(this.nombreArchivo, cargaResponse.nombreArchivo) &&
        Objects.equals(this.tamanioBytes, cargaResponse.tamanioBytes) &&
        Objects.equals(this.fechaCarga, cargaResponse.fechaCarga) &&
        Objects.equals(this.usuarioResponsable, cargaResponse.usuarioResponsable) &&
        Objects.equals(this.mensaje, cargaResponse.mensaje);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoPeriodo, nombreArchivo, tamanioBytes, fechaCarga, usuarioResponsable, mensaje);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CargaResponse {\n");
    sb.append("    codigoPeriodo: ").append(toIndentedString(codigoPeriodo)).append("\n");
    sb.append("    nombreArchivo: ").append(toIndentedString(nombreArchivo)).append("\n");
    sb.append("    tamanioBytes: ").append(toIndentedString(tamanioBytes)).append("\n");
    sb.append("    fechaCarga: ").append(toIndentedString(fechaCarga)).append("\n");
    sb.append("    usuarioResponsable: ").append(toIndentedString(usuarioResponsable)).append("\n");
    sb.append("    mensaje: ").append(toIndentedString(mensaje)).append("\n");
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

