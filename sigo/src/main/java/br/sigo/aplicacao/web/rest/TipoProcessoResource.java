package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.TipoProcessoService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.TipoProcessoDTO;

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
 * REST controller for managing {@link br.sigo.aplicacao.domain.TipoProcesso}.
 */
@RestController
@RequestMapping("/api")
public class TipoProcessoResource {

    private final Logger log = LoggerFactory.getLogger(TipoProcessoResource.class);

    private static final String ENTITY_NAME = "tipoProcesso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProcessoService tipoProcessoService;

    public TipoProcessoResource(TipoProcessoService tipoProcessoService) {
        this.tipoProcessoService = tipoProcessoService;
    }

    /**
     * {@code POST  /tipo-processos} : Create a new tipoProcesso.
     *
     * @param tipoProcessoDTO the tipoProcessoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProcessoDTO, or with status {@code 400 (Bad Request)} if the tipoProcesso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-processos")
    public ResponseEntity<TipoProcessoDTO> createTipoProcesso(@Valid @RequestBody TipoProcessoDTO tipoProcessoDTO) throws URISyntaxException {
        log.debug("REST request to save TipoProcesso : {}", tipoProcessoDTO);
        if (tipoProcessoDTO.getId() != null) {
            throw new BadRequestAlertException("A new tipoProcesso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProcessoDTO result = tipoProcessoService.save(tipoProcessoDTO);
        return ResponseEntity.created(new URI("/api/tipo-processos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-processos} : Updates an existing tipoProcesso.
     *
     * @param tipoProcessoDTO the tipoProcessoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProcessoDTO,
     * or with status {@code 400 (Bad Request)} if the tipoProcessoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProcessoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-processos")
    public ResponseEntity<TipoProcessoDTO> updateTipoProcesso(@Valid @RequestBody TipoProcessoDTO tipoProcessoDTO) throws URISyntaxException {
        log.debug("REST request to update TipoProcesso : {}", tipoProcessoDTO);
        if (tipoProcessoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoProcessoDTO result = tipoProcessoService.save(tipoProcessoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProcessoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-processos} : get all the tipoProcessos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProcessos in body.
     */
    @GetMapping("/tipo-processos")
    public List<TipoProcessoDTO> getAllTipoProcessos() {
        log.debug("REST request to get all TipoProcessos");
        return tipoProcessoService.findAll();
    }

    /**
     * {@code GET  /tipo-processos/:id} : get the "id" tipoProcesso.
     *
     * @param id the id of the tipoProcessoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProcessoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-processos/{id}")
    public ResponseEntity<TipoProcessoDTO> getTipoProcesso(@PathVariable Long id) {
        log.debug("REST request to get TipoProcesso : {}", id);
        Optional<TipoProcessoDTO> tipoProcessoDTO = tipoProcessoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoProcessoDTO);
    }

    /**
     * {@code DELETE  /tipo-processos/:id} : delete the "id" tipoProcesso.
     *
     * @param id the id of the tipoProcessoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-processos/{id}")
    public ResponseEntity<Void> deleteTipoProcesso(@PathVariable Long id) {
        log.debug("REST request to delete TipoProcesso : {}", id);
        tipoProcessoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
