package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.VendaService;
import br.sigo.aplicacao.domain.Venda;
import br.sigo.aplicacao.repository.VendaRepository;
import br.sigo.aplicacao.service.dto.VendaDTO;
import br.sigo.aplicacao.service.mapper.VendaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Venda}.
 */
@Service
@Transactional
public class VendaServiceImpl implements VendaService {

    private final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);

    private final VendaRepository vendaRepository;

    private final VendaMapper vendaMapper;

    public VendaServiceImpl(VendaRepository vendaRepository, VendaMapper vendaMapper) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }

    /**
     * Save a venda.
     *
     * @param vendaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VendaDTO save(VendaDTO vendaDTO) {
        log.debug("Request to save Venda : {}", vendaDTO);
        Venda venda = vendaMapper.toEntity(vendaDTO);
        venda = vendaRepository.save(venda);
        return vendaMapper.toDto(venda);
    }

    /**
     * Get all the vendas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<VendaDTO> findAll() {
        log.debug("Request to get all Vendas");
        return vendaRepository.findAll().stream()
            .map(vendaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one venda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VendaDTO> findOne(Long id) {
        log.debug("Request to get Venda : {}", id);
        return vendaRepository.findById(id)
            .map(vendaMapper::toDto);
    }

    /**
     * Delete the venda by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venda : {}", id);
        vendaRepository.deleteById(id);
    }
}
