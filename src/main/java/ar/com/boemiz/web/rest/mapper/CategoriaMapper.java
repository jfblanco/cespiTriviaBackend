package ar.com.boemiz.web.rest.mapper;

import ar.com.boemiz.domain.*;
import ar.com.boemiz.web.rest.dto.CategoriaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Categoria and its DTO CategoriaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriaMapper {
    
    CategoriaDTO categoriaToCategoriaDTO(Categoria categoria);

    Categoria categoriaDTOToCategoria(CategoriaDTO categoriaDTO);
}
