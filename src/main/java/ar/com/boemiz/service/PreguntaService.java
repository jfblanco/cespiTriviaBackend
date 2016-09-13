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
import ar.com.boemiz.web.rest.mapper.PreguntaMapper;
import java.util.List;
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
public class PreguntaService {

    private final Logger log = LoggerFactory.getLogger(PreguntaService.class);
    
    @Inject
    private PreguntaRepository preguntaRepository;
    
    @Inject
    private PreguntaMapper preguntaMapper;    
    
    @Inject
    private PartidaRepository partidaRepository;
    
    @Value("${preguntas.cantidad}")
    private Integer cantidadDePreguntas;

    public ResponseEntity<PreguntaDTO> pedirSiguientePregunta(PartidaDTO partidaDTO) {        
        
        Partida partida = partidaRepository.findOne(partidaDTO.getId());
        
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
        
        Pageable pageable = new PageRequest(0, 1);
        
        List<Pregunta> preguntas = preguntaRepository.findSiguientePregunta(categorias, niveles,dificultades, pageable);
        
        PreguntaDTO preguntaDTO = new PreguntaDTO();
        if(preguntas.size() > 0)
            preguntaDTO = preguntaMapper.preguntaToPreguntaDTO(preguntas.get(0));
        return new ResponseEntity<>(preguntaDTO,HttpStatus.OK);
    }

}
