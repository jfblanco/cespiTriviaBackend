package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Nivel;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Nivel entity.
 */
public interface NivelRepository extends JpaRepository<Nivel,Long> {

    @Query("from Nivel n where n.estado = true")
    public List<Nivel> findAllHabilitado();
}
