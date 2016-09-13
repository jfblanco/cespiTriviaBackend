package ar.com.boemiz.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Nivel entity.
 */
public class NivelDTO implements Serializable {

    private Long id;

    private Integer codigo;
    
    private String descripcion;
    
    private Boolean estado;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

        NivelDTO nivelDTO = (NivelDTO) o;

        if ( ! Objects.equals(id, nivelDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "NivelDTO{" +
                "id=" + id +
                ", numero='" + codigo + "'" +
                ", descripcion='" + descripcion + "'" +
                ", estado='" + estado + "'" +
                '}';
    }
}
