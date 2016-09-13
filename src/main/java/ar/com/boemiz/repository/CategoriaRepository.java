package ar.com.boemiz.repository;

import ar.com.boemiz.domain.Categoria;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Categoria entity.
 */
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {

    @Query("from Categoria c where c.estado = true")
    public List<Categoria> findAllHabilitadas();
}
