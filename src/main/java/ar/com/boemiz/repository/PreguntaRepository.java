package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Pregunta;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pregunta entity.
 */
public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {
    
    @Query("SELECT p FROM Pregunta p INNER JOIN p.categorias c INNER JOIN p.nivels n INNER JOIN p.dificultades d WHERE c.id IN ?1 AND n.id IN ?2 AND d.id IN ?3 ORDER BY RAND()")
    public List<Pregunta> findSiguientePregunta(Long[] categorias, Long[] niveles, Long[] dificultades, Pageable pageable);

}
