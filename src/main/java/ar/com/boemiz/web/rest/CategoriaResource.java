package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.Categoria;
import ar.com.boemiz.repository.CategoriaRepository;
import ar.com.boemiz.web.rest.dto.CategoriaDTO;
import ar.com.boemiz.web.rest.mapper.CategoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Categoria.
 */
@RestController
@RequestMapping("/api")
public class CategoriaResource {

    private final Logger log = LoggerFactory.getLogger(CategoriaResource.class);

    @Inject
    private CategoriaRepository categoriaRepository;

    @Inject
    private CategoriaMapper categoriaMapper;

    /**
     * POST  /categorias -> Create a new categoria.
     */
    @RequestMapping(value = "/categorias",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new categoria cannot already have an ID").build();
        }
        Categoria categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO);
        categoria.setEstado(Boolean.TRUE);
        categoriaRepository.save(categoria);
        return ResponseEntity.created(new URI("/api/categorias/" + categoriaDTO.getId())).build();
    }

    /**
     * PUT  /categorias -> Updates an existing categoria.
     */
    @RequestMapping(value = "/categorias",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Categoria : {}", categoriaDTO);
        if (categoriaDTO.getId() == null) {
            return create(categoriaDTO);
        }
        Categoria categoria = categoriaMapper.categoriaDTOToCategoria(categoriaDTO);
        categoriaRepository.save(categoria);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /categorias -> get all the categorias.
     */
    @RequestMapping(value = "/categorias",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CategoriaDTO> getAll() {
        log.debug("REST request to get all Categorias");
        return categoriaRepository.findAll().stream()
            .map(categoria -> categoriaMapper.categoriaToCategoriaDTO(categoria))
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * GET  /categorias -> get all the categorias.
     */
    @RequestMapping(value = "/categoriasHabilitadas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<CategoriaDTO> getAllHabilitadas() {
        log.debug("REST request to get all Categorias");
        return categoriaRepository.findAllHabilitadas().stream()
            .map(categoria -> categoriaMapper.categoriaToCategoriaDTO(categoria))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /categorias/:id -> get the "id" categoria.
     */
    @RequestMapping(value = "/categorias/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CategoriaDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Categoria : {}", id);
        return Optional.ofNullable(categoriaRepository.findOne(id))
            .map(categoriaMapper::categoriaToCategoriaDTO)
            .map(categoriaDTO -> new ResponseEntity<>(
                categoriaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /categorias/:id -> delete the "id" categoria.
     */
    @RequestMapping(value = "/categorias/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Categoria : {}", id);
        Categoria categoria = categoriaRepository.findOne(id);
        categoria.setEstado(!categoria.getEstado());
        categoriaRepository.save(categoria);
    }
}
