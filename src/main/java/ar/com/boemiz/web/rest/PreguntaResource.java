package ar.com.boemiz.web.rest;

import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.domain.Pregunta;
import ar.com.boemiz.domain.User;
import ar.com.boemiz.repository.ImagenRepository;
import ar.com.boemiz.repository.PreguntaRepository;
import ar.com.boemiz.repository.UserRepository;
import ar.com.boemiz.service.PreguntaService;
import ar.com.boemiz.web.rest.dto.PartidaDTO;
import ar.com.boemiz.web.rest.dto.PreguntaDTO;
import ar.com.boemiz.web.rest.dto.UserDTO;
import ar.com.boemiz.web.rest.mapper.PreguntaMapper;
import ar.com.boemiz.web.rest.mapper.UserMapper;
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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Pregunta.
 */
@RestController
@RequestMapping("/api")
public class PreguntaResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaResource.class);

    @Inject
    private PreguntaRepository preguntaRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private ImagenRepository imagenRepository;

    @Inject
    private PreguntaMapper preguntaMapper;   
    
    @Inject
    private UserMapper userMapper;    
    
    @Inject
    private PreguntaService preguntaService;

    /**
     * POST  /preguntas -> Create a new pregunta.
     */
    @RequestMapping(value = "/preguntas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> create(@Valid @RequestBody PreguntaDTO preguntaDTO) throws URISyntaxException {
        log.debug("REST request to save Pregunta : {}", preguntaDTO);
        if (preguntaDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pregunta cannot already have an ID").build();
        }
        Pregunta pregunta = preguntaMapper.preguntaDTOToPregunta(preguntaDTO);
        if(pregunta.getImagen() != null && pregunta.getImagen().getId() != null)
            pregunta.setImagen(imagenRepository.findOne(pregunta.getImagen().getId()));
        if(preguntaDTO.getUser() != null){
            Optional<User> user = userRepository.findOneByLogin(preguntaDTO.getUser().getLogin());
            pregunta.setUsuario(user.get());
        }
        preguntaRepository.save(pregunta);
        return ResponseEntity.created(new URI("/api/preguntas/" + pregunta.getId())).build();
    }

    /**
     * PUT  /preguntas -> Updates an existing pregunta.
     */
    @RequestMapping(value = "/preguntas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> update(@Valid @RequestBody PreguntaDTO preguntaDTO) throws URISyntaxException {
        log.debug("REST request to update Pregunta : {}", preguntaDTO);
        if (preguntaDTO.getId() == null) {
            return create(preguntaDTO);
        }
        Pregunta pregunta = preguntaMapper.preguntaDTOToPregunta(preguntaDTO);
        preguntaRepository.save(pregunta);
        return ResponseEntity.ok().build();
    }

    /**
     * GET  /preguntas -> get all the preguntas.
     */
    @RequestMapping(value = "/preguntas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PreguntaDTO> getAll() {
        log.debug("REST request to get all Preguntas");
        List<Pregunta> preguntas = preguntaRepository.findAll();
        List<PreguntaDTO> preguntasDTO = new ArrayList<PreguntaDTO>();
        
        for(Pregunta pregunta : preguntas){
            PreguntaDTO preguntaDTO = preguntaMapper.preguntaToPreguntaDTO(pregunta);
            UserDTO userDTO = new UserDTO(pregunta.getUsuario().getLogin(),null,null,null,null,null,null);
            preguntaDTO.setUser(userDTO);
            preguntasDTO.add(preguntaDTO);
        }
        return preguntasDTO;
    }

    /**
     * GET  /preguntas/:id -> get the "id" pregunta.
     */
    @RequestMapping(value = "/preguntas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreguntaDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Pregunta : {}", id);
        return Optional.ofNullable(preguntaRepository.findOne(id))
            .map(preguntaMapper::preguntaToPreguntaDTO)
            .map(preguntaDTO -> new ResponseEntity<>(
                preguntaDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /preguntas/:id -> delete the "id" pregunta.
     */
    @RequestMapping(value = "/preguntas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Pregunta : {}", id);
        preguntaRepository.delete(id);
    }
    
    @RequestMapping(value = "/siguientePregunta",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreguntaDTO> pedirSiguientePregunta(@Valid @RequestBody PartidaDTO partidaDTO) throws URISyntaxException {
        log.debug("REST request to siguientePregunta : {}", partidaDTO);
        return preguntaService.pedirSiguientePregunta(partidaDTO);
    }
}
