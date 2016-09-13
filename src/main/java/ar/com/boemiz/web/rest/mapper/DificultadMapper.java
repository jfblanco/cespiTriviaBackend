package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.DificultadDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Dificultad and its DTO DificultadDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DificultadMapper {

    DificultadDTO dificultadToDificultadDTO(Dificultad dificultad);

    Dificultad dificultadDTOToDificultad(DificultadDTO dificultadDTO);
}
