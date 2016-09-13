package ar.com.boemiz.web.rest;

import ar.com.boemiz.domain.Imagen;
import com.codahale.metrics.annotation.Timed;
import ar.com.boemiz.repository.ImagenRepository;
import ar.com.boemiz.web.rest.dto.ImagenDTO;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing Pregunta.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    @Inject
    private ImagenRepository imagenRepository;

    /**
     * POST  /preguntas -> Create a new pregunta.
     */
    @RequestMapping(value = "/imagen",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ImagenDTO create(@RequestParam(value = "file") MultipartFile file) throws URISyntaxException, IOException {
        Imagen imagen = new Imagen();
        imagen.setData(file.getBytes());
        imagenRepository.save(imagen);
        ImagenDTO imagenDTO = new ImagenDTO();
        imagenDTO.setId(imagen.getId());
        return imagenDTO;
    }
    
    @RequestMapping(value = "/imagen",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Imagen getImange(@RequestParam(value = "id") Long id) throws URISyntaxException, IOException {
        Imagen imagen = new Imagen();
        if(id != null)
            imagen = imagenRepository.findOne(id);
        return imagen;
    }
}
