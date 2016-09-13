package ar.com.boemiz.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A Nivel.
 */
@Entity
@Table(name = "NIVEL")
public class Nivel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codigo")
    private Integer codigo;
    
    @NotNull
    @Column(name = "estado")
    private Boolean estado;
    
    @NotNull
    @Size(max = 30)
    @Column(name = "descripcion", length = 30, nullable = false)
    private String descripcion;

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

        Nivel nivel = (Nivel) o;

        if ( ! Objects.equals(id, nivel.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Nivel{" +
                "id=" + id +
                ", numero='" + codigo + "'" +
                ", descripcion='" + descripcion + "'" +
                ", estado='" + estado + "'" +
                '}';
    }
}
