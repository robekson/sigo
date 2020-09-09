package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.ForneceService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.ForneceDTO;

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

/**
 * REST controller for managing {@link br.sigo.aplicacao.domain.Fornece}.
 */
@RestController
@RequestMapping("/api")
public class ForneceResource {

    private final Logger log = LoggerFactory.getLogger(ForneceResource.class);

    private static final String ENTITY_NAME = "fornece";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ForneceService forneceService;

    public ForneceResource(ForneceService forneceService) {
        this.forneceService = forneceService;
    }

    /**
     * {@code POST  /forneces} : Create a new fornece.
     *
     * @param forneceDTO the forneceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new forneceDTO, or with status {@code 400 (Bad Request)} if the fornece has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/forneces")
    public ResponseEntity<ForneceDTO> createFornece(@Valid @RequestBody ForneceDTO forneceDTO) throws URISyntaxException {
        log.debug("REST request to save Fornece : {}", forneceDTO);
        if (forneceDTO.getId() != null) {
            throw new BadRequestAlertException("A new fornece cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForneceDTO result = forneceService.save(forneceDTO);
        return ResponseEntity.created(new URI("/api/forneces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /forneces} : Updates an existing fornece.
     *
     * @param forneceDTO the forneceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated forneceDTO,
     * or with status {@code 400 (Bad Request)} if the forneceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the forneceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/forneces")
    public ResponseEntity<ForneceDTO> updateFornece(@Valid @RequestBody ForneceDTO forneceDTO) throws URISyntaxException {
        log.debug("REST request to update Fornece : {}", forneceDTO);
        if (forneceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ForneceDTO result = forneceService.save(forneceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, forneceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /forneces} : get all the forneces.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of forneces in body.
     */
    @GetMapping("/forneces")
    public List<ForneceDTO> getAllForneces() {
        log.debug("REST request to get all Forneces");
        return forneceService.findAll();
    }

    /**
     * {@code GET  /forneces/:id} : get the "id" fornece.
     *
     * @param id the id of the forneceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the forneceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/forneces/{id}")
    public ResponseEntity<ForneceDTO> getFornece(@PathVariable Long id) {
        log.debug("REST request to get Fornece : {}", id);
        Optional<ForneceDTO> forneceDTO = forneceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(forneceDTO);
    }

    /**
     * {@code DELETE  /forneces/:id} : delete the "id" fornece.
     *
     * @param id the id of the forneceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/forneces/{id}")
    public ResponseEntity<Void> deleteFornece(@PathVariable Long id) {
        log.debug("REST request to delete Fornece : {}", id);
        forneceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
