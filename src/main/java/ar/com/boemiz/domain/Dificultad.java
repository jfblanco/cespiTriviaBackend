package ar.com.boemiz.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Dificultad.
 */
@Entity
@Table(name = "DIFICULTAD")
public class Dificultad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "codigo")
    private Integer codigo;

    @Column(name = "estado")
    private Boolean estado;

    @NotNull
    @Size(min = 1, max = 30)
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

        Dificultad dificultad = (Dificultad) o;

        if ( ! Objects.equals(id, dificultad.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Dificultad{" +
                "id=" + id +
                ", codigo='" + codigo + "'" +
                ", estado='" + estado + "'" +
                ", descripcion='" + descripcion + "'" +
                '}';
    }
}
