package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Dificultad;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Dificultad entity.
 */
public interface DificultadRepository extends JpaRepository<Dificultad,Long> {

    @Query("from Dificultad d where d.estado = true")
    public List<Dificultad> findAllHabilitadas();

}
