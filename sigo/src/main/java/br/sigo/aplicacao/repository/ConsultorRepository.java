package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Consultor;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Consultor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultorRepository extends JpaRepository<Consultor, Long> {
}
