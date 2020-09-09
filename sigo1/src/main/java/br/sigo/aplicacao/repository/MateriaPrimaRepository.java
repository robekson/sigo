package br.sigo.aplicacao.repository;

import br.sigo.aplicacao.domain.MateriaPrima;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MateriaPrima entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Long> {
}
