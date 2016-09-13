package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.NivelDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Nivel and its DTO NivelDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NivelMapper {

    NivelDTO nivelToNivelDTO(Nivel nivel);

    Nivel nivelDTOToNivel(NivelDTO nivelDTO);
}
