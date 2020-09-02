package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.VendaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Venda}.
 */
public interface VendaService {

    /**
     * Save a venda.
     *
     * @param vendaDTO the entity to save.
     * @return the persisted entity.
     */
    VendaDTO save(VendaDTO vendaDTO);

    /**
     * Get all the vendas.
     *
     * @return the list of entities.
     */
    List<VendaDTO> findAll();


    /**
     * Get the "id" venda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<VendaDTO> findOne(Long id);

    /**
     * Delete the "id" venda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
