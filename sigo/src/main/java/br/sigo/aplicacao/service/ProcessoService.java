package br.sigo.aplicacao.service;

import br.sigo.aplicacao.service.dto.ProcessoDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link br.sigo.aplicacao.domain.Processo}.
 */
public interface ProcessoService {

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessoDTO save(ProcessoDTO processoDTO);

    /**
     * Get all the processos.
     *
     * @return the list of entities.
     */
    List<ProcessoDTO> findAll();
    /**
     * Get all the ProcessoDTO where ProcessoFilho is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<ProcessoDTO> findAllWhereProcessoFilhoIsNull();


    /**
     * Get the "id" processo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessoDTO> findOne(Long id);

    /**
     * Delete the "id" processo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
