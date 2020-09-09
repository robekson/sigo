package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Consultoria;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Consultoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultoriaRepository extends JpaRepository<Consultoria, Long> {
}
