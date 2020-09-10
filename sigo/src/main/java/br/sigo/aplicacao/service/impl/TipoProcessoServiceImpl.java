package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.TipoProcessoService;
import br.sigo.aplicacao.domain.TipoProcesso;
import br.sigo.aplicacao.repository.TipoProcessoRepository;
import br.sigo.aplicacao.service.dto.TipoProcessoDTO;
import br.sigo.aplicacao.service.mapper.TipoProcessoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TipoProcesso}.
 */
@Service
@Transactional
public class TipoProcessoServiceImpl implements TipoProcessoService {

    private final Logger log = LoggerFactory.getLogger(TipoProcessoServiceImpl.class);

    private final TipoProcessoRepository tipoProcessoRepository;

    private final TipoProcessoMapper tipoProcessoMapper;

    public TipoProcessoServiceImpl(TipoProcessoRepository tipoProcessoRepository, TipoProcessoMapper tipoProcessoMapper) {
        this.tipoProcessoRepository = tipoProcessoRepository;
        this.tipoProcessoMapper = tipoProcessoMapper;
    }

    /**
     * Save a tipoProcesso.
     *
     * @param tipoProcessoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TipoProcessoDTO save(TipoProcessoDTO tipoProcessoDTO) {
        log.debug("Request to save TipoProcesso : {}", tipoProcessoDTO);
        TipoProcesso tipoProcesso = tipoProcessoMapper.toEntity(tipoProcessoDTO);
        tipoProcesso = tipoProcessoRepository.save(tipoProcesso);
        return tipoProcessoMapper.toDto(tipoProcesso);
    }

    /**
     * Get all the tipoProcessos.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoProcessoDTO> findAll() {
        log.debug("Request to get all TipoProcessos");
        return tipoProcessoRepository.findAll().stream()
            .map(tipoProcessoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one tipoProcesso by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TipoProcessoDTO> findOne(Long id) {
        log.debug("Request to get TipoProcesso : {}", id);
        return tipoProcessoRepository.findById(id)
            .map(tipoProcessoMapper::toDto);
    }

    /**
     * Delete the tipoProcesso by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoProcesso : {}", id);
        tipoProcessoRepository.deleteById(id);
    }
}
