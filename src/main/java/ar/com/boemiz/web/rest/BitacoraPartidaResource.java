package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.BitacoraPartida;
import ar.com.boemiz.repository.BitacoraPartidaRepository;
import ar.com.boemiz.web.rest.dto.BitacoraPartidaDTO;
import ar.com.boemiz.web.rest.mapper.BitacoraPartidaMapper;
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
 * REST controller for managing BitacoraPartida.
 */
@RestController
@RequestMapping("/api")
public class BitacoraPartidaResource {

    private final Logger log = LoggerFactory.getLogger(BitacoraPartidaResource.class);

    @Inject
    private BitacoraPartidaRepository bitacoraPartidaRepository;

    @Inject
    private BitacoraPartidaMapper bitacoraPartidaMapper;

    /**
     * POST  /bitacoraPartidas -> Create a new bitacoraPartida.
     */
    @RequestMapping(value = "/bitacoraPartidas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@RequestBody BitacoraPartidaDTO bitacoraPartidaDTO) throws URISyntaxException {
        log.debug("REST request to save BitacoraPartida : {}", bitacoraPartidaDTO);
        if (bitacoraPartidaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bitacoraPartida cannot already have an ID").build();
        }
        BitacoraPartida bitacoraPartida = bitacoraPartidaMapper.bitacoraPartidaDTOToBitacoraPartida(bitacoraPartidaDTO);
        bitacoraPartidaRepository.save(bitacoraPartida);
        return ResponseEntity.created(new URI("/api/bitacoraPartidas/" + bitacoraPartidaDTO.getId())).build();
    }

    /**
     * PUT  /bitacoraPartidas -> Updates an existing bitacoraPartida.
     */
    @RequestMapping(value = "/bitacoraPartidas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@RequestBody BitacoraPartidaDTO bitacoraPartidaDTO) throws URISyntaxException {
        log.debug("REST request to update BitacoraPartida : {}", bitacoraPartidaDTO);
        if (bitacoraPartidaDTO.getId() == null) {
            return create(bitacoraPartidaDTO);
        }
        BitacoraPartida bitacoraPartida = bitacoraPartidaMapper.bitacoraPartidaDTOToBitacoraPartida(bitacoraPartidaDTO);
        bitacoraPartidaRepository.save(bitacoraPartida);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /bitacoraPartidas -> get all the bitacoraPartidas.
     */
    @RequestMapping(value = "/bitacoraPartidas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<BitacoraPartidaDTO> getAll() {
        log.debug("REST request to get all BitacoraPartidas");
        return bitacoraPartidaRepository.findAll().stream()
            .map(bitacoraPartida -> bitacoraPartidaMapper.bitacoraPartidaToBitacoraPartidaDTO(bitacoraPartida))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /bitacoraPartidas/:id -> get the "id" bitacoraPartida.
     */
    @RequestMapping(value = "/bitacoraPartidas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BitacoraPartidaDTO> get(@PathVariable Long id) {
        log.debug("REST request to get BitacoraPartida : {}", id);
        return Optional.ofNullable(bitacoraPartidaRepository.findOneWithEagerRelationships(id))
            .map(bitacoraPartidaMapper::bitacoraPartidaToBitacoraPartidaDTO)
            .map(bitacoraPartidaDTO -> new ResponseEntity<>(
                bitacoraPartidaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bitacoraPartidas/:id -> delete the "id" bitacoraPartida.
     */
    @RequestMapping(value = "/bitacoraPartidas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete BitacoraPartida : {}", id);
        bitacoraPartidaRepository.delete(id);
    }
}
