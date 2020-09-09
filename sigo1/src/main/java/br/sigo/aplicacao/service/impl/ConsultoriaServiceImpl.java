package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.ConsultoriaService;
import br.sigo.aplicacao.domain.Consultoria;
import br.sigo.aplicacao.repository.ConsultoriaRepository;
import br.sigo.aplicacao.service.dto.ConsultoriaDTO;
import br.sigo.aplicacao.service.mapper.ConsultoriaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Consultoria}.
 */
@Service
@Transactional
public class ConsultoriaServiceImpl implements ConsultoriaService {

    private final Logger log = LoggerFactory.getLogger(ConsultoriaServiceImpl.class);

    private final ConsultoriaRepository consultoriaRepository;

    private final ConsultoriaMapper consultoriaMapper;

    public ConsultoriaServiceImpl(ConsultoriaRepository consultoriaRepository, ConsultoriaMapper consultoriaMapper) {
        this.consultoriaRepository = consultoriaRepository;
        this.consultoriaMapper = consultoriaMapper;
    }

    /**
     * Save a consultoria.
     *
     * @param consultoriaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsultoriaDTO save(ConsultoriaDTO consultoriaDTO) {
        log.debug("Request to save Consultoria : {}", consultoriaDTO);
        Consultoria consultoria = consultoriaMapper.toEntity(consultoriaDTO);
        consultoria = consultoriaRepository.save(consultoria);
        return consultoriaMapper.toDto(consultoria);
    }

    /**
     * Get all the consultorias.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultoriaDTO> findAll() {
        log.debug("Request to get all Consultorias");
        return consultoriaRepository.findAll().stream()
            .map(consultoriaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one consultoria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultoriaDTO> findOne(Long id) {
        log.debug("Request to get Consultoria : {}", id);
        return consultoriaRepository.findById(id)
            .map(consultoriaMapper::toDto);
    }

    /**
     * Delete the consultoria by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultoria : {}", id);
        consultoriaRepository.deleteById(id);
    }
}
