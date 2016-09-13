package ar.com.boemiz.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A BitacoraPartida.
 */
@Entity
@Table(name = "BITACORAPARTIDA")
public class BitacoraPartida implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "BITACORAPARTIDA_PREGUNTA",
               joinColumns = @JoinColumn(name="bitacorapartidas_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="preguntas_id", referencedColumnName="ID"))
    private Set<Pregunta> preguntas = new HashSet<>();

    @OneToOne(mappedBy = "bitacoraPartida")
    @JsonIgnore
    private Partida partida;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Pregunta> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<Pregunta> preguntas) {
        this.preguntas = preguntas;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitacoraPartida bitacoraPartida = (BitacoraPartida) o;

        if ( ! Objects.equals(id, bitacoraPartida.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BitacoraPartida{" +
                "id=" + id +
                '}';
    }
}
