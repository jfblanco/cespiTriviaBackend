package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.Dificultad;
import ar.com.boemiz.repository.DificultadRepository;
import ar.com.boemiz.web.rest.dto.DificultadDTO;
import ar.com.boemiz.web.rest.mapper.DificultadMapper;
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
 * REST controller for managing Dificultad.
 */
@RestController
@RequestMapping("/api")
public class DificultadResource {

    private final Logger log = LoggerFactory.getLogger(DificultadResource.class);

    @Inject
    private DificultadRepository dificultadRepository;

    @Inject
    private DificultadMapper dificultadMapper;

    /**
     * POST  /dificultads -> Create a new dificultad.
     */
    @RequestMapping(value = "/dificultads",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody DificultadDTO dificultadDTO) throws URISyntaxException {
        log.debug("REST request to save Dificultad : {}", dificultadDTO);
        if (dificultadDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dificultad cannot already have an ID").build();
        }
        Dificultad dificultad = dificultadMapper.dificultadDTOToDificultad(dificultadDTO);
        dificultad.setEstado(Boolean.TRUE);
        dificultadRepository.save(dificultad);
        return ResponseEntity.created(new URI("/api/dificultads/" + dificultadDTO.getId())).build();
    }

    /**
     * PUT  /dificultads -> Updates an existing dificultad.
     */
    @RequestMapping(value = "/dificultads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody DificultadDTO dificultadDTO) throws URISyntaxException {
        log.debug("REST request to update Dificultad : {}", dificultadDTO);
        if (dificultadDTO.getId() == null) {
            return create(dificultadDTO);
        }
        Dificultad dificultad = dificultadMapper.dificultadDTOToDificultad(dificultadDTO);
        dificultadRepository.save(dificultad);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /dificultads -> get all the dificultads.
     */
    @RequestMapping(value = "/dificultads",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<DificultadDTO> getAll() {
        log.debug("REST request to get all Dificultads");
        return dificultadRepository.findAll().stream()
            .map(dificultad -> dificultadMapper.dificultadToDificultadDTO(dificultad))
            .collect(Collectors.toCollection(LinkedList::new));
    }
    
    /**
     * GET  /dificultads -> get all the dificultads.
     */
    @RequestMapping(value = "/dificultadsHabilitadas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<DificultadDTO> getAllHabilitadas() {
        log.debug("REST request to get all Dificultads");
        return dificultadRepository.findAllHabilitadas().stream()
            .map(dificultad -> dificultadMapper.dificultadToDificultadDTO(dificultad))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /dificultads/:id -> get the "id" dificultad.
     */
    @RequestMapping(value = "/dificultads/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DificultadDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Dificultad : {}", id);
        return Optional.ofNullable(dificultadRepository.findOne(id))
            .map(dificultadMapper::dificultadToDificultadDTO)
            .map(dificultadDTO -> new ResponseEntity<>(
                dificultadDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dificultads/:id -> delete the "id" dificultad.
     */
    @RequestMapping(value = "/dificultads/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Dificultad : {}", id);
        Dificultad difucultad = dificultadRepository.findOne(id);
        difucultad.setEstado(!difucultad.getEstado());
        dificultadRepository.save(difucultad);
    }
}
