package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.ForneceDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Fornece}.
 */
public interface ForneceService {

    /**
     * Save a fornece.
     *
     * @param forneceDTO the entity to save.
     * @return the persisted entity.
     */
    ForneceDTO save(ForneceDTO forneceDTO);

    /**
     * Get all the forneces.
     *
     * @return the list of entities.
     */
    List<ForneceDTO> findAll();


    /**
     * Get the "id" fornece.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ForneceDTO> findOne(Long id);

    /**
     * Delete the "id" fornece.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
