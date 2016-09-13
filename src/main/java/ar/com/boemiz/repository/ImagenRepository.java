package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Imagen;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Partida entity.
 */
public interface ImagenRepository extends JpaRepository<Imagen,Long> {

}
