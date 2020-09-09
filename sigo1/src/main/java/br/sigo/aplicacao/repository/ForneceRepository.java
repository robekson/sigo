package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.Fornece;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Fornece entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForneceRepository extends JpaRepository<Fornece, Long> {
}
