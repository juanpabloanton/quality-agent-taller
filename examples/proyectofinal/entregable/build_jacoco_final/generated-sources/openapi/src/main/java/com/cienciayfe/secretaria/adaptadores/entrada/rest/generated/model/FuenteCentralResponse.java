package com.cienciayfe.secretaria.adaptadores.entrada.rest.generated.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * FuenteCentralResponse
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2026-07-05T18:54:39.747842900-05:00[America/Guayaquil]", comments = "Generator version: 7.6.0")
public class FuenteCentralResponse {

  private String codigoPeriodo;

  /**
   * Estado de la fuente central para el período
   */
  public enum EstadoEnum {
    DISPONIBLE("DISPONIBLE"),
    
    SIN_INFORMACION("SIN_INFORMACION");

    private String value;

    EstadoEnum(String value) {
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
    public static EstadoEnum fromValue(String value) {
      for (EstadoEnum b : EstadoEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private EstadoEnum estado;

  private String nombreArchivo = null;

  private Long tamanioBytes = null;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime fechaCarga = null;

  private String usuarioResponsable = null;

  private String mensaje;

  public FuenteCentralResponse() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public FuenteCentralResponse(String codigoPeriodo, EstadoEnum estado, String mensaje) {
    this.codigoPeriodo = codigoPeriodo;
    this.estado = estado;
    this.mensaje = mensaje;
  }

  public FuenteCentralResponse codigoPeriodo(String codigoPeriodo) {
    this.codigoPeriodo = codigoPeriodo;
    return this;
  }

  /**
   * Código del período consultado
   * @return codigoPeriodo
  */
  @NotNull 
  @Schema(name = "codigoPeriodo", example = "2025-II", description = "Código del período consultado", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("codigoPeriodo")
  public String getCodigoPeriodo() {
    return codigoPeriodo;
  }

  public void setCodigoPeriodo(String codigoPeriodo) {
    this.codigoPeriodo = codigoPeriodo;
  }

  public FuenteCentralResponse estado(EstadoEnum estado) {
    this.estado = estado;
    return this;
  }

  /**
   * Estado de la fuente central para el período
   * @return estado
  */
  @NotNull 
  @Schema(name = "estado", description = "Estado de la fuente central para el período", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("estado")
  public EstadoEnum getEstado() {
    return estado;
  }

  public void setEstado(EstadoEnum estado) {
    this.estado = estado;
  }

  public FuenteCentralResponse nombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
    return this;
  }

  /**
   * Nombre del archivo cargado (null si no hay información)
   * @return nombreArchivo
  */
  
  @Schema(name = "nombreArchivo", example = "datos_academicos_2025II.csv", description = "Nombre del archivo cargado (null si no hay información)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("nombreArchivo")
  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public FuenteCentralResponse tamanioBytes(Long tamanioBytes) {
    this.tamanioBytes = tamanioBytes;
    return this;
  }

  /**
   * Tamaño del archivo en bytes (null si no hay información)
   * @return tamanioBytes
  */
  
  @Schema(name = "tamanioBytes", example = "45312", description = "Tamaño del archivo en bytes (null si no hay información)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tamanioBytes")
  public Long getTamanioBytes() {
    return tamanioBytes;
  }

  public void setTamanioBytes(Long tamanioBytes) {
    this.tamanioBytes = tamanioBytes;
  }

  public FuenteCentralResponse fechaCarga(OffsetDateTime fechaCarga) {
    this.fechaCarga = fechaCarga;
    return this;
  }

  /**
   * Fecha y hora de la última carga (null si no hay información)
   * @return fechaCarga
  */
  @Valid 
  @Schema(name = "fechaCarga", description = "Fecha y hora de la última carga (null si no hay información)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fechaCarga")
  public OffsetDateTime getFechaCarga() {
    return fechaCarga;
  }

  public void setFechaCarga(OffsetDateTime fechaCarga) {
    this.fechaCarga = fechaCarga;
  }

  public FuenteCentralResponse usuarioResponsable(String usuarioResponsable) {
    this.usuarioResponsable = usuarioResponsable;
    return this;
  }

  /**
   * Usuario que realizó la última carga (null si no hay información)
   * @return usuarioResponsable
  */
  
  @Schema(name = "usuarioResponsable", example = "secretaria01", description = "Usuario que realizó la última carga (null si no hay información)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("usuarioResponsable")
  public String getUsuarioResponsable() {
    return usuarioResponsable;
  }

  public void setUsuarioResponsable(String usuarioResponsable) {
    this.usuarioResponsable = usuarioResponsable;
  }

  public FuenteCentralResponse mensaje(String mensaje) {
    this.mensaje = mensaje;
    return this;
  }

  /**
   * Mensaje descriptivo del estado para la secretaria
   * @return mensaje
  */
  @NotNull 
  @Schema(name = "mensaje", example = "Disponible para revisión", description = "Mensaje descriptivo del estado para la secretaria", requiredMode = Schema.RequiredMode.REQUIRED)
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
    FuenteCentralResponse fuenteCentralResponse = (FuenteCentralResponse) o;
    return Objects.equals(this.codigoPeriodo, fuenteCentralResponse.codigoPeriodo) &&
        Objects.equals(this.estado, fuenteCentralResponse.estado) &&
        Objects.equals(this.nombreArchivo, fuenteCentralResponse.nombreArchivo) &&
        Objects.equals(this.tamanioBytes, fuenteCentralResponse.tamanioBytes) &&
        Objects.equals(this.fechaCarga, fuenteCentralResponse.fechaCarga) &&
        Objects.equals(this.usuarioResponsable, fuenteCentralResponse.usuarioResponsable) &&
        Objects.equals(this.mensaje, fuenteCentralResponse.mensaje);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codigoPeriodo, estado, nombreArchivo, tamanioBytes, fechaCarga, usuarioResponsable, mensaje);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FuenteCentralResponse {\n");
    sb.append("    codigoPeriodo: ").append(toIndentedString(codigoPeriodo)).append("\n");
    sb.append("    estado: ").append(toIndentedString(estado)).append("\n");
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

