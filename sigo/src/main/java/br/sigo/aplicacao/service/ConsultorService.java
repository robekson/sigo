package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.ConsultorDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Consultor}.
 */
public interface ConsultorService {

    /**
     * Save a consultor.
     *
     * @param consultorDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultorDTO save(ConsultorDTO consultorDTO);

    /**
     * Get all the consultors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultorDTO> findAll(Pageable pageable);


    /**
     * Get the "id" consultor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultorDTO> findOne(Long id);

    /**
     * Delete the "id" consultor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
