package br.sigo.aplicacao.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import br.sigo.aplicacao.domain.Normas;
import br.sigo.aplicacao.domain.*; // for static metamodels
import br.sigo.aplicacao.repository.NormasRepository;
import br.sigo.aplicacao.service.dto.NormasCriteria;
import br.sigo.aplicacao.service.dto.NormasDTO;
import br.sigo.aplicacao.service.mapper.NormasMapper;

/**
 * Service for executing complex queries for {@link Normas} entities in the database.
 * The main input is a {@link NormasCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NormasDTO} or a {@link Page} of {@link NormasDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NormasQueryService extends QueryService<Normas> {

    private final Logger log = LoggerFactory.getLogger(NormasQueryService.class);

    private final NormasRepository normasRepository;

    private final NormasMapper normasMapper;

    public NormasQueryService(NormasRepository normasRepository, NormasMapper normasMapper) {
        this.normasRepository = normasRepository;
        this.normasMapper = normasMapper;
    }

    /**
     * Return a {@link List} of {@link NormasDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NormasDTO> findByCriteria(NormasCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Normas> specification = createSpecification(criteria);
        return normasMapper.toDto(normasRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NormasDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NormasDTO> findByCriteria(NormasCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Normas> specification = createSpecification(criteria);
        return normasRepository.findAll(specification, page)
            .map(normasMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NormasCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Normas> specification = createSpecification(criteria);
        return normasRepository.count(specification);
    }

    /**
     * Function to convert {@link NormasCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Normas> createSpecification(NormasCriteria criteria) {
        Specification<Normas> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Normas_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), Normas_.codigo));
            }
            if (criteria.getTitulo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitulo(), Normas_.titulo));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Normas_.date));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Normas_.status));
            }
        }
        return specification;
    }
}
