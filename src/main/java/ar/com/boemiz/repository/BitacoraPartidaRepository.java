package ar.com.boemiz.repository;

import ar.com.boemiz.domain.BitacoraPartida;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BitacoraPartida entity.
 */
public interface BitacoraPartidaRepository extends JpaRepository<BitacoraPartida,Long> {

    @Query("select bitacoraPartida from BitacoraPartida bitacoraPartida left join fetch bitacoraPartida.preguntas where bitacoraPartida.id =:id")
    BitacoraPartida findOneWithEagerRelationships(@Param("id") Long id);

}
