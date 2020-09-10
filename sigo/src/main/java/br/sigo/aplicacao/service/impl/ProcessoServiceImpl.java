package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.ProcessoService;
import br.sigo.aplicacao.domain.Processo;
import br.sigo.aplicacao.repository.ProcessoRepository;
import br.sigo.aplicacao.service.dto.ProcessoDTO;
import br.sigo.aplicacao.service.mapper.ProcessoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Processo}.
 */
@Service
@Transactional
public class ProcessoServiceImpl implements ProcessoService {

    private final Logger log = LoggerFactory.getLogger(ProcessoServiceImpl.class);

    private final ProcessoRepository processoRepository;

    private final ProcessoMapper processoMapper;

    public ProcessoServiceImpl(ProcessoRepository processoRepository, ProcessoMapper processoMapper) {
        this.processoRepository = processoRepository;
        this.processoMapper = processoMapper;
    }

    /**
     * Save a processo.
     *
     * @param processoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ProcessoDTO save(ProcessoDTO processoDTO) {
        log.debug("Request to save Processo : {}", processoDTO);
        Processo processo = processoMapper.toEntity(processoDTO);
        processo = processoRepository.save(processo);
        return processoMapper.toDto(processo);
    }

    /**
     * Get all the processos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProcessoDTO> findAll() {
        log.debug("Request to get all Processos");
        return processoRepository.findAll().stream()
            .map(processoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the processos where ProcessoFilho is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ProcessoDTO> findAllWhereProcessoFilhoIsNull() {
        log.debug("Request to get all processos where ProcessoFilho is null");
        return StreamSupport
            .stream(processoRepository.findAll().spliterator(), false)
            .filter(processo -> processo.getProcessoFilho() == null)
            .map(processoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessoDTO> findOne(Long id) {
        log.debug("Request to get Processo : {}", id);
        return processoRepository.findById(id)
            .map(processoMapper::toDto);
    }

    /**
     * Delete the processo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Processo : {}", id);
        processoRepository.deleteById(id);
    }
}
