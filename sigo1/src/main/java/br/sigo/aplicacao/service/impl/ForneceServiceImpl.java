package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.ForneceService;
import br.sigo.aplicacao.domain.Fornece;
import br.sigo.aplicacao.repository.ForneceRepository;
import br.sigo.aplicacao.service.dto.ForneceDTO;
import br.sigo.aplicacao.service.mapper.ForneceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Fornece}.
 */
@Service
@Transactional
public class ForneceServiceImpl implements ForneceService {

    private final Logger log = LoggerFactory.getLogger(ForneceServiceImpl.class);

    private final ForneceRepository forneceRepository;

    private final ForneceMapper forneceMapper;

    public ForneceServiceImpl(ForneceRepository forneceRepository, ForneceMapper forneceMapper) {
        this.forneceRepository = forneceRepository;
        this.forneceMapper = forneceMapper;
    }

    /**
     * Save a fornece.
     *
     * @param forneceDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ForneceDTO save(ForneceDTO forneceDTO) {
        log.debug("Request to save Fornece : {}", forneceDTO);
        Fornece fornece = forneceMapper.toEntity(forneceDTO);
        fornece = forneceRepository.save(fornece);
        return forneceMapper.toDto(fornece);
    }

    /**
     * Get all the forneces.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<ForneceDTO> findAll() {
        log.debug("Request to get all Forneces");
        return forneceRepository.findAll().stream()
            .map(forneceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fornece by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ForneceDTO> findOne(Long id) {
        log.debug("Request to get Fornece : {}", id);
        return forneceRepository.findById(id)
            .map(forneceMapper::toDto);
    }

    /**
     * Delete the fornece by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fornece : {}", id);
        forneceRepository.deleteById(id);
    }
}
