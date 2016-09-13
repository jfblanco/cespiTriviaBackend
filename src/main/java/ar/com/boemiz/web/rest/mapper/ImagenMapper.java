package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.ImagenDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dificultad and its DTO DificultadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ImagenMapper {

    ImagenDTO imagenToImagenDTO(Imagen imagen);

    Imagen imagenDTOToImagen(ImagenDTO imagenDTO);
}
