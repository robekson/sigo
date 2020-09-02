package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.FornecedorService;
import br.sigo.aplicacao.domain.Fornecedor;
import br.sigo.aplicacao.repository.FornecedorRepository;
import br.sigo.aplicacao.service.dto.FornecedorDTO;
import br.sigo.aplicacao.service.mapper.FornecedorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Fornecedor}.
 */
@Service
@Transactional
public class FornecedorServiceImpl implements FornecedorService {

    private final Logger log = LoggerFactory.getLogger(FornecedorServiceImpl.class);

    private final FornecedorRepository fornecedorRepository;

    private final FornecedorMapper fornecedorMapper;

    public FornecedorServiceImpl(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    /**
     * Save a fornecedor.
     *
     * @param fornecedorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FornecedorDTO save(FornecedorDTO fornecedorDTO) {
        log.debug("Request to save Fornecedor : {}", fornecedorDTO);
        Fornecedor fornecedor = fornecedorMapper.toEntity(fornecedorDTO);
        fornecedor = fornecedorRepository.save(fornecedor);
        return fornecedorMapper.toDto(fornecedor);
    }

    /**
     * Get all the fornecedors.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FornecedorDTO> findAll() {
        log.debug("Request to get all Fornecedors");
        return fornecedorRepository.findAll().stream()
            .map(fornecedorMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one fornecedor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FornecedorDTO> findOne(Long id) {
        log.debug("Request to get Fornecedor : {}", id);
        return fornecedorRepository.findById(id)
            .map(fornecedorMapper::toDto);
    }

    /**
     * Delete the fornecedor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fornecedor : {}", id);
        fornecedorRepository.deleteById(id);
    }
}
