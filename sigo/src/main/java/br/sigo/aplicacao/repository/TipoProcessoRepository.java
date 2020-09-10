package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.TipoProcesso;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoProcesso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProcessoRepository extends JpaRepository<TipoProcesso, Long> {
}
