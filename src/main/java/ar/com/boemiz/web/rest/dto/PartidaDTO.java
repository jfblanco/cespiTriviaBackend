package ar.com.boemiz.web.rest.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ar.com.boemiz.domain.util.CustomDateTimeDeserializer;
import ar.com.boemiz.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the Partida entity.
 */
public class PartidaDTO implements Serializable {

    private Long id;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime fecha;
    
    private Set<CategoriaDTO> categorias = new HashSet<>();
    
    private Set<NivelDTO> nivels = new HashSet<>();
    
    private Set<PreguntaDTO> preguntas = new HashSet<>();   
    
    private Set<DificultadDTO> dificultades = new HashSet<>();

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

    public Set<CategoriaDTO> getCategorias() {
        return categorias;
    }

    public void setCategorias(Set<CategoriaDTO> categorias) {
        this.categorias = categorias;
    }

    public Set<NivelDTO> getNivels() {
        return nivels;
    }

    public void setNivels(Set<NivelDTO> nivels) {
        this.nivels = nivels;
    }

    public Set<PreguntaDTO> getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(Set<PreguntaDTO> preguntas) {
        this.preguntas = preguntas;
    }

    public Set<DificultadDTO> getDificultades() {
        return dificultades;
    }

    public void setDificultades(Set<DificultadDTO> dificultades) {
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

        PartidaDTO partidaDTO = (PartidaDTO) o;

        if ( ! Objects.equals(id, partidaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PartidaDTO{" +
                "id=" + id +
                ", fecha='" + fecha + "'" +
                '}';
    }
}
