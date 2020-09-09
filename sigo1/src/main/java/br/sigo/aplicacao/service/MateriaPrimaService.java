package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.MateriaPrimaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.MateriaPrima}.
 */
public interface MateriaPrimaService {

    /**
     * Save a materiaPrima.
     *
     * @param materiaPrimaDTO the entity to save.
     * @return the persisted entity.
     */
    MateriaPrimaDTO save(MateriaPrimaDTO materiaPrimaDTO);

    /**
     * Get all the materiaPrimas.
     *
     * @return the list of entities.
     */
    List<MateriaPrimaDTO> findAll();
    /**
     * Get all the MateriaPrimaDTO where Produto is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MateriaPrimaDTO> findAllWhereProdutoIsNull();


    /**
     * Get the "id" materiaPrima.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MateriaPrimaDTO> findOne(Long id);

    /**
     * Delete the "id" materiaPrima.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
