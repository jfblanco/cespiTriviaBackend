package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.PartidaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Partida and its DTO PartidaDTO.
 */
@Mapper(componentModel = "spring", uses = {NivelMapper.class, CategoriaMapper.class, DificultadMapper.class,})
public interface PartidaMapper {

    PartidaDTO partidaToPartidaDTO(Partida partida);

    Partida partidaDTOToPartida(PartidaDTO partidaDTO);
    
    default Nivel nivelFromId(Long id) {
        if (id == null) {
            return null;
        }
        Nivel nivel = new Nivel();
        nivel.setId(id);
        return nivel;
    }
    
    default Categoria categoriaFromId(Long id) {
        if (id == null) {
            return null;
        }
        Categoria categoria = new Categoria();
        categoria.setId(id);
        return categoria;
    }
    
    default Dificultad dificultadFromId(Long id) {
        if (id == null) {
            return null;
        }
        Dificultad dificultad = new Dificultad();
        dificultad.setId(id);
        return dificultad;
    }
}
