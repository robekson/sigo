package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.TipoProcessoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.TipoProcesso}.
 */
public interface TipoProcessoService {

    /**
     * Save a tipoProcesso.
     *
     * @param tipoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    TipoProcessoDTO save(TipoProcessoDTO tipoProcessoDTO);

    /**
     * Get all the tipoProcessos.
     *
     * @return the list of entities.
     */
    List<TipoProcessoDTO> findAll();


    /**
     * Get the "id" tipoProcesso.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TipoProcessoDTO> findOne(Long id);

    /**
     * Delete the "id" tipoProcesso.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
