package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.ConsultoriaDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Consultoria}.
 */
public interface ConsultoriaService {

    /**
     * Save a consultoria.
     *
     * @param consultoriaDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultoriaDTO save(ConsultoriaDTO consultoriaDTO);

    /**
     * Get all the consultorias.
     *
     * @return the list of entities.
     */
    List<ConsultoriaDTO> findAll();


    /**
     * Get the "id" consultoria.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultoriaDTO> findOne(Long id);

    /**
     * Delete the "id" consultoria.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
