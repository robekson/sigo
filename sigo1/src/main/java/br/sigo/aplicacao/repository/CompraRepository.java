package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Compra;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Compra entity.
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query(value = "select distinct compra from Compra compra left join fetch compra.produtos",
        countQuery = "select count(distinct compra) from Compra compra")
    Page<Compra> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct compra from Compra compra left join fetch compra.produtos")
    List<Compra> findAllWithEagerRelationships();

    @Query("select compra from Compra compra left join fetch compra.produtos where compra.id =:id")
    Optional<Compra> findOneWithEagerRelationships(@Param("id") Long id);
}
