package ar.com.boemiz.service;

import ar.com.boemiz.domain.Categoria;
import ar.com.boemiz.domain.Dificultad;
import ar.com.boemiz.domain.Nivel;
import ar.com.boemiz.domain.Partida;
import ar.com.boemiz.domain.Pregunta;
import ar.com.boemiz.repository.PartidaRepository;
import ar.com.boemiz.repository.PreguntaRepository;
import ar.com.boemiz.web.rest.dto.PartidaDTO;
import ar.com.boemiz.web.rest.dto.PreguntaDTO;
import ar.com.boemiz.web.rest.mapper.PartidaMapper;
import ar.com.boemiz.web.rest.mapper.PreguntaMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PartidaService {

    private final Logger log = LoggerFactory.getLogger(PartidaService.class);
    
    @Inject
    private PreguntaRepository preguntaRepository;
    
    @Inject
    private PreguntaMapper preguntaMapper;    
    
    @Inject
    private PartidaMapper partidaMapper;    
    
    @Inject
    private PartidaRepository partidaRepository;
    
    @Value("${preguntas.cantidad}")
    private Integer cantidadDePreguntas;
    
    public ResponseEntity<PartidaDTO> cargarPreguntasALaPartida(Partida partida) {
        
        Long[] categorias = new Long[partida.getCategorias().size()];
        Long[] niveles = new Long[partida.getNivels().size()];
        Long[] dificultades = new Long[partida.getDificultades().size()];
        
        Integer i = 0;
        for(Categoria c : partida.getCategorias()){
            categorias[i] = c.getId();
            i++;
        }
           
        i = 0;
        for(Nivel n : partida.getNivels()){
            niveles[i] = n.getId();
            i++;
        }
        
        i = 0;
        for(Dificultad d : partida.getDificultades()){
            dificultades[i] = d.getId();
            i++;
        }
        
        Pageable pageable = new PageRequest(0, cantidadDePreguntas);
        
        List<Pregunta> preguntas = new ArrayList<Pregunta>(preguntaRepository.findSiguientePregunta(categorias, niveles, dificultades, pageable));
        
        long seed = System.nanoTime();
        
        PartidaDTO partidaDTO = partidaMapper.partidaToPartidaDTO(partida);
        
        if(preguntas.size() > 0){
            for(Pregunta pregunta : preguntas){
                PreguntaDTO preguntaDTO = new PreguntaDTO();
                preguntaDTO.setDescripcion(pregunta.getDescripcion());
                preguntaDTO.setCorrecta(pregunta.getCorrecta());
                
                List<String> opciones = new ArrayList<String>();
                opciones.add(pregunta.getOpcion1());
                opciones.add(pregunta.getOpcion2());
                opciones.add(pregunta.getOpcion3());                
                Collections.shuffle(opciones, new Random(seed));
                
                preguntaDTO.setOpcion1(opciones.get(0));
                preguntaDTO.setOpcion2(opciones.get(1));
                preguntaDTO.setOpcion3(opciones.get(2));
                
                partidaDTO.getPreguntas().add(preguntaDTO);
            }
        }
        
        return new ResponseEntity<>(partidaDTO,HttpStatus.OK);
    }

}
