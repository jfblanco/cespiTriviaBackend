package ar.com.boemiz.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the BitacoraPartida entity.
 */
public class BitacoraPartidaDTO implements Serializable {

    private Long id;

    private Set<PreguntaDTO> preguntas = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Set<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BitacoraPartidaDTO bitacoraPartidaDTO = (BitacoraPartidaDTO) o;

        if ( ! Objects.equals(id, bitacoraPartidaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BitacoraPartidaDTO{" +
                "id=" + id +
                '}';
    }
}
