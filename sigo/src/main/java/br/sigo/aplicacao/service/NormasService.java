package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.NormasDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Normas}.
 */
public interface NormasService {

    /**
     * Save a normas.
     *
     * @param normasDTO the entity to save.
     * @return the persisted entity.
     */
    NormasDTO save(NormasDTO normasDTO);

    /**
     * Get all the normas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NormasDTO> findAll(Pageable pageable);


    /**
     * Get the "id" normas.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NormasDTO> findOne(Long id);

    /**
     * Delete the "id" normas.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
