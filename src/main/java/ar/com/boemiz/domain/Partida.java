package ar.com.boemiz.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ar.com.boemiz.domain.util.CustomDateTimeDeserializer;
import ar.com.boemiz.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Partida.
 */
@Entity
@Table(name = "PARTIDA")
public class Partida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    @JoinColumn(name="bitacoraPartida")
    private BitacoraPartida bitacoraPartida;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PARTIDA_CATEGORIA",
      joinColumns=@JoinColumn(name="part_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="cat_id", referencedColumnName="id"))
    private Set<Categoria> categorias = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PARTIDA_NIVEL",
      joinColumns=@JoinColumn(name="part_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="niv_id", referencedColumnName="id"))
    private Set<Nivel> nivels = new HashSet<>();
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable( name="PARTIDA_DIFICULTAD",
      joinColumns=@JoinColumn(name="part_id", referencedColumnName="id"),
      inverseJoinColumns=@JoinColumn(name="dif_id", referencedColumnName="id"))
    private Set<Dificultad> dificultades = new HashSet<>();

    @NotNull
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "fecha", nullable = false)
    private DateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getFecha() {
        return fecha;
    }

    public void setFecha(DateTime fecha) {
        this.fecha = fecha;
    }

    public BitacoraPartida getBitacoraPartida() {
        return bitacoraPartida;
    }

    public void setBitacoraPartida(BitacoraPartida bitacoraPartida) {
        this.bitacoraPartida = bitacoraPartida;
    }

    public Set<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Set<Nivel> getNivels() {
        return nivels;
    }

    public void setNivels(Set<Nivel> nivels) {
        this.nivels = nivels;
    }

    public Set<Dificultad> getDificultades() {
        return dificultades;
    }

    public void setDificultades(Set<Dificultad> dificultades) {
        this.dificultades = dificultades;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Partida partida = (Partida) o;

        if ( ! Objects.equals(id, partida.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", fecha='" + fecha + "'" +
                '}';
    }
}
