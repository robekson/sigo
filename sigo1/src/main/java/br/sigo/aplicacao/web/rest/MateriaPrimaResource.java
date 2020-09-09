package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.MateriaPrimaService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.MateriaPrimaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link br.sigo.aplicacao.domain.MateriaPrima}.
 */
@RestController
@RequestMapping("/api")
public class MateriaPrimaResource {

    private final Logger log = LoggerFactory.getLogger(MateriaPrimaResource.class);

    private static final String ENTITY_NAME = "materiaPrima";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MateriaPrimaService materiaPrimaService;

    public MateriaPrimaResource(MateriaPrimaService materiaPrimaService) {
        this.materiaPrimaService = materiaPrimaService;
    }

    /**
     * {@code POST  /materia-primas} : Create a new materiaPrima.
     *
     * @param materiaPrimaDTO the materiaPrimaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materiaPrimaDTO, or with status {@code 400 (Bad Request)} if the materiaPrima has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materia-primas")
    public ResponseEntity<MateriaPrimaDTO> createMateriaPrima(@Valid @RequestBody MateriaPrimaDTO materiaPrimaDTO) throws URISyntaxException {
        log.debug("REST request to save MateriaPrima : {}", materiaPrimaDTO);
        if (materiaPrimaDTO.getId() != null) {
            throw new BadRequestAlertException("A new materiaPrima cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MateriaPrimaDTO result = materiaPrimaService.save(materiaPrimaDTO);
        return ResponseEntity.created(new URI("/api/materia-primas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materia-primas} : Updates an existing materiaPrima.
     *
     * @param materiaPrimaDTO the materiaPrimaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materiaPrimaDTO,
     * or with status {@code 400 (Bad Request)} if the materiaPrimaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materiaPrimaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materia-primas")
    public ResponseEntity<MateriaPrimaDTO> updateMateriaPrima(@Valid @RequestBody MateriaPrimaDTO materiaPrimaDTO) throws URISyntaxException {
        log.debug("REST request to update MateriaPrima : {}", materiaPrimaDTO);
        if (materiaPrimaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MateriaPrimaDTO result = materiaPrimaService.save(materiaPrimaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materiaPrimaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /materia-primas} : get all the materiaPrimas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materiaPrimas in body.
     */
    @GetMapping("/materia-primas")
    public List<MateriaPrimaDTO> getAllMateriaPrimas(@RequestParam(required = false) String filter) {
        if ("produto-is-null".equals(filter)) {
            log.debug("REST request to get all MateriaPrimas where produto is null");
            return materiaPrimaService.findAllWhereProdutoIsNull();
        }
        log.debug("REST request to get all MateriaPrimas");
        return materiaPrimaService.findAll();
    }

    /**
     * {@code GET  /materia-primas/:id} : get the "id" materiaPrima.
     *
     * @param id the id of the materiaPrimaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materiaPrimaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materia-primas/{id}")
    public ResponseEntity<MateriaPrimaDTO> getMateriaPrima(@PathVariable Long id) {
        log.debug("REST request to get MateriaPrima : {}", id);
        Optional<MateriaPrimaDTO> materiaPrimaDTO = materiaPrimaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materiaPrimaDTO);
    }

    /**
     * {@code DELETE  /materia-primas/:id} : delete the "id" materiaPrima.
     *
     * @param id the id of the materiaPrimaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materia-primas/{id}")
    public ResponseEntity<Void> deleteMateriaPrima(@PathVariable Long id) {
        log.debug("REST request to delete MateriaPrima : {}", id);
        materiaPrimaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
