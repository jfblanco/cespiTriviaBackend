package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.Nivel;
import ar.com.boemiz.repository.NivelRepository;
import ar.com.boemiz.web.rest.dto.NivelDTO;
import ar.com.boemiz.web.rest.mapper.NivelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Nivel.
 */
@RestController
@RequestMapping("/api")
public class NivelResource {

    private final Logger log = LoggerFactory.getLogger(NivelResource.class);

    @Inject
    private NivelRepository nivelRepository;

    @Inject
    private NivelMapper nivelMapper;

    /**
     * POST  /nivels -> Create a new nivel.
     */
    @RequestMapping(value = "/nivels",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody NivelDTO nivelDTO) throws URISyntaxException {
        log.debug("REST request to save Nivel : {}", nivelDTO);
        if (nivelDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new nivel cannot already have an ID").build();
        }
        Nivel nivel = nivelMapper.nivelDTOToNivel(nivelDTO);
        nivel.setEstado(Boolean.TRUE);
        nivelRepository.save(nivel);
        return ResponseEntity.created(new URI("/api/nivels/" + nivelDTO.getId())).build();
    }

    /**
     * PUT  /nivels -> Updates an existing nivel.
     */
    @RequestMapping(value = "/nivels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody NivelDTO nivelDTO) throws URISyntaxException {
        log.debug("REST request to update Nivel : {}", nivelDTO);
        if (nivelDTO.getId() == null) {
            return create(nivelDTO);
        }
        Nivel nivel = nivelMapper.nivelDTOToNivel(nivelDTO);
        nivelRepository.save(nivel);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /nivels -> get all the nivels.
     */
    @RequestMapping(value = "/nivels",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<NivelDTO> getAll() {
        log.debug("REST request to get all Nivels");
        return nivelRepository.findAll().stream()
            .map(nivel -> nivelMapper.nivelToNivelDTO(nivel))
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * GET  /nivels -> get all the nivels.
     */
    @RequestMapping(value = "/nivelesHabilitados",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<NivelDTO> getAllHabilitados() {
        log.debug("REST request to get all Nivels");
        return nivelRepository.findAllHabilitado().stream()
            .map(nivel -> nivelMapper.nivelToNivelDTO(nivel))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /nivels/:id -> get the "id" nivel.
     */
    @RequestMapping(value = "/nivels/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NivelDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Nivel : {}", id);
        return Optional.ofNullable(nivelRepository.findOne(id))
            .map(nivelMapper::nivelToNivelDTO)
            .map(nivelDTO -> new ResponseEntity<>(
                nivelDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nivels/:id -> delete the "id" nivel.
     */
    @RequestMapping(value = "/nivels/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Nivel : {}", id);
        Nivel nivel = nivelRepository.findOne(id);
        nivel.setEstado(!nivel.getEstado());
        nivelRepository.save(nivel);
    }
}
