package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Partida;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partida entity.
 */
public interface PartidaRepository extends JpaRepository<Partida,Long> {

}
