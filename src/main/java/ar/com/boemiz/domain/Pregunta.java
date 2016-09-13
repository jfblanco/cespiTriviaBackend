package ar.com.boemiz.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Pregunta.
 */
@Entity
@Table(name = "PREGUNTA")
public class Pregunta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 140)
    @Column(name = "descripcion", length = 140, nullable = false)
    private String descripcion;
    
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="imagen")
    private Imagen imagen;

    @NotNull
    @Size(max = 140)
    @Column(name = "opcion1", length = 140, nullable = false)
    private String opcion1;

    @NotNull
    @Size(max = 140)
    @Column(name = "opcion2", length = 140, nullable = false)
    private String opcion2;

    @NotNull
    @Size(max = 140)
    @Column(name = "opcion3", length = 140, nullable = false)
    private String opcion3;

    @NotNull
    @Size(max = 140)
    @Column(name = "correcta", length = 140, nullable = false)
    private String correcta;
    
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PREGUNTA_NIVEL",
      joinColumns=@JoinColumn(name="preg_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="niv_id", referencedColumnName="id"))
    private Set<Nivel> nivels = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PREGUNTA_CATEGORIA",
      joinColumns=@JoinColumn(name="preg_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="cat_id", referencedColumnName="id"))
    private Set<Categoria> categorias = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PREGUNTA_DIFICULTAD",
      joinColumns=@JoinColumn(name="preg_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="dif_id", referencedColumnName="id"))
    private Set<Dificultad> dificultades = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getCorrecta() {
        return correcta;
    }

    public void setCorrecta(String correcta) {
        this.correcta = correcta;
    }

    public Set<Nivel> getNivels() {
        return nivels;
    }

    public void setNivels(Set<Nivel> nivels) {
        this.nivels = nivels;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Dificultad> getDificultades() {
        return dificultades;
    }

    public void setDificultades(Set<Dificultad> dificultades) {
        this.dificultades = dificultades;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }  

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pregunta pregunta = (Pregunta) o;

        if ( ! Objects.equals(id, pregunta.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pregunta{" +
                "id=" + id +
                ", descripcion='" + descripcion + "'" +
                ", opcion1='" + opcion1 + "'" +
                ", opcion2='" + opcion2 + "'" +
                ", opcion3='" + opcion3 + "'" +
                ", correcta='" + correcta + "'" +
                '}';
    }
}
