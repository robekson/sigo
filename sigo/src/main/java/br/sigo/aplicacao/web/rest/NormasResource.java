package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.NormasService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.NormasDTO;
import br.sigo.aplicacao.service.dto.NormasCriteria;
import br.sigo.aplicacao.service.NormasQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.sigo.aplicacao.domain.Normas}.
 */
@RestController
@RequestMapping("/api")
public class NormasResource {

    private final Logger log = LoggerFactory.getLogger(NormasResource.class);

    private static final String ENTITY_NAME = "normas";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NormasService normasService;

    private final NormasQueryService normasQueryService;

    public NormasResource(NormasService normasService, NormasQueryService normasQueryService) {
        this.normasService = normasService;
        this.normasQueryService = normasQueryService;
    }

    /**
     * {@code POST  /normas} : Create a new normas.
     *
     * @param normasDTO the normasDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new normasDTO, or with status {@code 400 (Bad Request)} if the normas has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/normas")
    public ResponseEntity<NormasDTO> createNormas(@Valid @RequestBody NormasDTO normasDTO) throws URISyntaxException {
        log.debug("REST request to save Normas : {}", normasDTO);
        if (normasDTO.getId() != null) {
            throw new BadRequestAlertException("A new normas cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NormasDTO result = normasService.save(normasDTO);
        return ResponseEntity.created(new URI("/api/normas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /normas} : Updates an existing normas.
     *
     * @param normasDTO the normasDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated normasDTO,
     * or with status {@code 400 (Bad Request)} if the normasDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the normasDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/normas")
    public ResponseEntity<NormasDTO> updateNormas(@Valid @RequestBody NormasDTO normasDTO) throws URISyntaxException {
        log.debug("REST request to update Normas : {}", normasDTO);
        if (normasDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NormasDTO result = normasService.save(normasDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, normasDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /normas} : get all the normas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of normas in body.
     */
    @GetMapping("/normas")
    public ResponseEntity<List<NormasDTO>> getAllNormas(NormasCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Normas by criteria: {}", criteria);
        Page<NormasDTO> page = normasQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /normas/count} : count all the normas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/normas/count")
    public ResponseEntity<Long> countNormas(NormasCriteria criteria) {
        log.debug("REST request to count Normas by criteria: {}", criteria);
        return ResponseEntity.ok().body(normasQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /normas/:id} : get the "id" normas.
     *
     * @param id the id of the normasDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the normasDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/normas/{id}")
    public ResponseEntity<NormasDTO> getNormas(@PathVariable Long id) {
        log.debug("REST request to get Normas : {}", id);
        Optional<NormasDTO> normasDTO = normasService.findOne(id);
        return ResponseUtil.wrapOrNotFound(normasDTO);
    }

    /**
     * {@code DELETE  /normas/:id} : delete the "id" normas.
     *
     * @param id the id of the normasDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/normas/{id}")
    public ResponseEntity<Void> deleteNormas(@PathVariable Long id) {
        log.debug("REST request to delete Normas : {}", id);
        normasService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
