package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.Partida;
import ar.com.boemiz.repository.PartidaRepository;
import ar.com.boemiz.service.PartidaService;
import ar.com.boemiz.web.rest.dto.PartidaDTO;
import ar.com.boemiz.web.rest.mapper.PartidaMapper;
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
import org.joda.time.DateTime;

/**
 * REST controller for managing Partida.
 */
@RestController
@RequestMapping("/api")
public class PartidaResource {

    private final Logger log = LoggerFactory.getLogger(PartidaResource.class);

    @Inject
    private PartidaRepository partidaRepository;

    @Inject
    private PartidaMapper partidaMapper;
    
    @Inject
    private PartidaService partidaService;

    /**
     * POST  /partidas -> Create a new partida.
     */
    @RequestMapping(value = "/partidas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to save Partida : {}", partidaDTO);
        if (partidaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new partida cannot already have an ID").build();
        }
        Partida partida = partidaMapper.partidaDTOToPartida(partidaDTO);
        if(partida.getFecha() == null)
            partida.setFecha(new DateTime());
        partidaRepository.save(partida);
        return ResponseEntity.created(new URI("/api/partidas/" + partidaDTO.getId())).build();
    }

    /**
     * PUT  /partidas -> Updates an existing partida.
     */
    @RequestMapping(value = "/partidas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to update Partida : {}", partidaDTO);
        if (partidaDTO.getId() == null) {
            return create(partidaDTO);
        }
        Partida partida = partidaMapper.partidaDTOToPartida(partidaDTO);
        partidaRepository.save(partida);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /partidas -> get all the partidas.
     */
    @RequestMapping(value = "/partidas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PartidaDTO> getAll() {
        log.debug("REST request to get all Partidas");
        return partidaRepository.findAll().stream()
            .map(partida -> partidaMapper.partidaToPartidaDTO(partida))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /partidas/:id -> get the "id" partida.
     */
    @RequestMapping(value = "/partidas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartidaDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Partida : {}", id);
        return Optional.ofNullable(partidaRepository.findOne(id))
            .map(partidaMapper::partidaToPartidaDTO)
            .map(partidaDTO -> new ResponseEntity<>(
                partidaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /partidas/:id -> delete the "id" partida.
     */
    @RequestMapping(value = "/partidas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Partida : {}", id);
        partidaRepository.delete(id);
    }
    
    /**
     * POST  /crearNuevaPartida -> Create a new partida, whit questions ready to play.
     */
    @RequestMapping(value = "/crearNuevaPartida",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PartidaDTO> crearNuevaPartida(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to crearNuevaPartida Partida : {}", partidaDTO);
        Partida partida = partidaMapper.partidaDTOToPartida(partidaDTO);
        if(partida.getFecha() == null)
            partida.setFecha(new DateTime());
        partidaRepository.save(partida);
        return partidaService.cargarPreguntasALaPartida(partida);
    }
}
