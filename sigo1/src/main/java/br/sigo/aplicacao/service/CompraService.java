package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.CompraDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Compra}.
 */
public interface CompraService {

    /**
     * Save a compra.
     *
     * @param compraDTO the entity to save.
     * @return the persisted entity.
     */
    CompraDTO save(CompraDTO compraDTO);

    /**
     * Get all the compras.
     *
     * @return the list of entities.
     */
    List<CompraDTO> findAll();

    /**
     * Get all the compras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<CompraDTO> findAllWithEagerRelationships(Pageable pageable);


    /**
     * Get the "id" compra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompraDTO> findOne(Long id);

    /**
     * Delete the "id" compra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
