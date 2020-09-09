package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.NormasService;
import br.sigo.aplicacao.domain.Normas;
import br.sigo.aplicacao.repository.NormasRepository;
import br.sigo.aplicacao.service.dto.NormasDTO;
import br.sigo.aplicacao.service.mapper.NormasMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Normas}.
 */
@Service
@Transactional
public class NormasServiceImpl implements NormasService {

    private final Logger log = LoggerFactory.getLogger(NormasServiceImpl.class);

    private final NormasRepository normasRepository;

    private final NormasMapper normasMapper;

    public NormasServiceImpl(NormasRepository normasRepository, NormasMapper normasMapper) {
        this.normasRepository = normasRepository;
        this.normasMapper = normasMapper;
    }

    /**
     * Save a normas.
     *
     * @param normasDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public NormasDTO save(NormasDTO normasDTO) {
        log.debug("Request to save Normas : {}", normasDTO);
        Normas normas = normasMapper.toEntity(normasDTO);
        normas = normasRepository.save(normas);
        return normasMapper.toDto(normas);
    }

    /**
     * Get all the normas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<NormasDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Normas");
        return normasRepository.findAll(pageable)
            .map(normasMapper::toDto);
    }


    /**
     * Get one normas by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<NormasDTO> findOne(Long id) {
        log.debug("Request to get Normas : {}", id);
        return normasRepository.findById(id)
            .map(normasMapper::toDto);
    }

    /**
     * Delete the normas by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Normas : {}", id);
        normasRepository.deleteById(id);
    }
}
