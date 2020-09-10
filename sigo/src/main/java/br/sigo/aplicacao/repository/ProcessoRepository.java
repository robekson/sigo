package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Processo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Processo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
}
