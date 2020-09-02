package br.sigo.aplicacao.service.impl;

import br.sigo.aplicacao.service.ConsultorService;
import br.sigo.aplicacao.domain.Consultor;
import br.sigo.aplicacao.repository.ConsultorRepository;
import br.sigo.aplicacao.service.dto.ConsultorDTO;
import br.sigo.aplicacao.service.mapper.ConsultorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Consultor}.
 */
@Service
@Transactional
public class ConsultorServiceImpl implements ConsultorService {

    private final Logger log = LoggerFactory.getLogger(ConsultorServiceImpl.class);

    private final ConsultorRepository consultorRepository;

    private final ConsultorMapper consultorMapper;

    public ConsultorServiceImpl(ConsultorRepository consultorRepository, ConsultorMapper consultorMapper) {
        this.consultorRepository = consultorRepository;
        this.consultorMapper = consultorMapper;
    }

    /**
     * Save a consultor.
     *
     * @param consultorDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ConsultorDTO save(ConsultorDTO consultorDTO) {
        log.debug("Request to save Consultor : {}", consultorDTO);
        Consultor consultor = consultorMapper.toEntity(consultorDTO);
        consultor = consultorRepository.save(consultor);
        return consultorMapper.toDto(consultor);
    }

    /**
     * Get all the consultors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consultors");
        return consultorRepository.findAll(pageable)
            .map(consultorMapper::toDto);
    }


    /**
     * Get one consultor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultorDTO> findOne(Long id) {
        log.debug("Request to get Consultor : {}", id);
        return consultorRepository.findById(id)
            .map(consultorMapper::toDto);
    }

    /**
     * Delete the consultor by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultor : {}", id);
        consultorRepository.deleteById(id);
    }
}
