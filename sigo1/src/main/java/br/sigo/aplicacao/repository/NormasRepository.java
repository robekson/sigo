package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Normas;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Normas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NormasRepository extends JpaRepository<Normas, Long>, JpaSpecificationExecutor<Normas> {
}
