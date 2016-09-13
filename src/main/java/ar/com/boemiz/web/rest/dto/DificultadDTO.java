package ar.com.boemiz.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Dificultad entity.
 */
public class DificultadDTO implements Serializable {

    private Long id;

    private String codigo;

    private Boolean estado;

    private String descripcion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }


    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DificultadDTO dificultadDTO = (DificultadDTO) o;

        if ( ! Objects.equals(id, dificultadDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DificultadDTO{" +
                "id=" + id +
                ", codigo='" + codigo + "'" +
                ", estado='" + estado + "'" +
                ", descripcion='" + descripcion + "'" +
                '}';
    }
}
