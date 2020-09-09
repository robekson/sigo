package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.CompraService;
import br.sigo.aplicacao.domain.Compra;
import br.sigo.aplicacao.repository.CompraRepository;
import br.sigo.aplicacao.service.dto.CompraDTO;
import br.sigo.aplicacao.service.mapper.CompraMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Compra}.
 */
@Service
@Transactional
public class CompraServiceImpl implements CompraService {

    private final Logger log = LoggerFactory.getLogger(CompraServiceImpl.class);

    private final CompraRepository compraRepository;

    private final CompraMapper compraMapper;

    public CompraServiceImpl(CompraRepository compraRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
    }

    /**
     * Save a compra.
     *
     * @param compraDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CompraDTO save(CompraDTO compraDTO) {
        log.debug("Request to save Compra : {}", compraDTO);
        Compra compra = compraMapper.toEntity(compraDTO);
        compra = compraRepository.save(compra);
        return compraMapper.toDto(compra);
    }

    /**
     * Get all the compras.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<CompraDTO> findAll() {
        log.debug("Request to get all Compras");
        return compraRepository.findAllWithEagerRelationships().stream()
            .map(compraMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get all the compras with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CompraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return compraRepository.findAllWithEagerRelationships(pageable).map(compraMapper::toDto);
    }

    /**
     * Get one compra by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CompraDTO> findOne(Long id) {
        log.debug("Request to get Compra : {}", id);
        return compraRepository.findOneWithEagerRelationships(id)
            .map(compraMapper::toDto);
    }

    /**
     * Delete the compra by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Compra : {}", id);
        compraRepository.deleteById(id);
    }
}
