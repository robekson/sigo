package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.FornecedorDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Fornecedor}.
 */
public interface FornecedorService {

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    FornecedorDTO save(FornecedorDTO fornecedorDTO);

    /**
     * Get all the fornecedors.
     *
     * @return the list of entities.
     */
    List<FornecedorDTO> findAll();


    /**
     * Get the "id" fornecedor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FornecedorDTO> findOne(Long id);

    /**
     * Delete the "id" fornecedor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
