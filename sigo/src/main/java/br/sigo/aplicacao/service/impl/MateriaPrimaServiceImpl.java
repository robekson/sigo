package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.MateriaPrimaService;
import br.sigo.aplicacao.domain.MateriaPrima;
import br.sigo.aplicacao.repository.MateriaPrimaRepository;
import br.sigo.aplicacao.service.dto.MateriaPrimaDTO;
import br.sigo.aplicacao.service.mapper.MateriaPrimaMapper;
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
 * Service Implementation for managing {@link MateriaPrima}.
 */
@Service
@Transactional
public class MateriaPrimaServiceImpl implements MateriaPrimaService {

    private final Logger log = LoggerFactory.getLogger(MateriaPrimaServiceImpl.class);

    private final MateriaPrimaRepository materiaPrimaRepository;

    private final MateriaPrimaMapper materiaPrimaMapper;

    public MateriaPrimaServiceImpl(MateriaPrimaRepository materiaPrimaRepository, MateriaPrimaMapper materiaPrimaMapper) {
        this.materiaPrimaRepository = materiaPrimaRepository;
        this.materiaPrimaMapper = materiaPrimaMapper;
    }

    /**
     * Save a materiaPrima.
     *
     * @param materiaPrimaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MateriaPrimaDTO save(MateriaPrimaDTO materiaPrimaDTO) {
        log.debug("Request to save MateriaPrima : {}", materiaPrimaDTO);
        MateriaPrima materiaPrima = materiaPrimaMapper.toEntity(materiaPrimaDTO);
        materiaPrima = materiaPrimaRepository.save(materiaPrima);
        return materiaPrimaMapper.toDto(materiaPrima);
    }

    /**
     * Get all the materiaPrimas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<MateriaPrimaDTO> findAll() {
        log.debug("Request to get all MateriaPrimas");
        return materiaPrimaRepository.findAll().stream()
            .map(materiaPrimaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }



    /**
     *  Get all the materiaPrimas where Produto is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<MateriaPrimaDTO> findAllWhereProdutoIsNull() {
        log.debug("Request to get all materiaPrimas where Produto is null");
        return StreamSupport
            .stream(materiaPrimaRepository.findAll().spliterator(), false)
            .filter(materiaPrima -> materiaPrima.getProduto() == null)
            .map(materiaPrimaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one materiaPrima by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MateriaPrimaDTO> findOne(Long id) {
        log.debug("Request to get MateriaPrima : {}", id);
        return materiaPrimaRepository.findById(id)
            .map(materiaPrimaMapper::toDto);
    }

    /**
     * Delete the materiaPrima by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MateriaPrima : {}", id);
        materiaPrimaRepository.deleteById(id);
    }
}
