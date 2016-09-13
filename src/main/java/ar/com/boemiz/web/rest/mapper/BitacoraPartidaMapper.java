package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.BitacoraPartidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BitacoraPartida and its DTO BitacoraPartidaDTO.
 */
@Mapper(componentModel = "spring", uses = {PreguntaMapper.class, })
public interface BitacoraPartidaMapper {

    BitacoraPartidaDTO bitacoraPartidaToBitacoraPartidaDTO(BitacoraPartida bitacoraPartida);

    BitacoraPartida bitacoraPartidaDTOToBitacoraPartida(BitacoraPartidaDTO bitacoraPartidaDTO);

    default Pregunta preguntaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Pregunta pregunta = new Pregunta();
        pregunta.setId(id);
        return pregunta;
    }
}
