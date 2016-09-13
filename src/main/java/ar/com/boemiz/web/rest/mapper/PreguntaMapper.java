package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.PreguntaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Pregunta and its DTO PreguntaDTO.
 */
@Mapper(componentModel = "spring", uses = {NivelMapper.class, CategoriaMapper.class, DificultadMapper.class, ImagenMapper.class, UserMapper.class})
public interface PreguntaMapper {
    
    PreguntaDTO preguntaToPreguntaDTO(Pregunta pregunta);

    Pregunta preguntaDTOToPregunta(PreguntaDTO preguntaDTO);
    
    default User userFromId(Long id){
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
    
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
    
    default Imagen imagenFromId(Long id) {
        if (id == null) {
            return null;
        }
        Imagen imagen = new Imagen();
        imagen.setId(id);
        return imagen;
    }
}
