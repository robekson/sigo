package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.ConsultoriaService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.ConsultoriaDTO;

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
 * REST controller for managing {@link br.sigo.aplicacao.domain.Consultoria}.
 */
@RestController
@RequestMapping("/api")
public class ConsultoriaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultoriaResource.class);

    private static final String ENTITY_NAME = "consultoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultoriaService consultoriaService;

    public ConsultoriaResource(ConsultoriaService consultoriaService) {
        this.consultoriaService = consultoriaService;
    }

    /**
     * {@code POST  /consultorias} : Create a new consultoria.
     *
     * @param consultoriaDTO the consultoriaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultoriaDTO, or with status {@code 400 (Bad Request)} if the consultoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultorias")
    public ResponseEntity<ConsultoriaDTO> createConsultoria(@Valid @RequestBody ConsultoriaDTO consultoriaDTO) throws URISyntaxException {
        log.debug("REST request to save Consultoria : {}", consultoriaDTO);
        if (consultoriaDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultoriaDTO result = consultoriaService.save(consultoriaDTO);
        return ResponseEntity.created(new URI("/api/consultorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consultorias} : Updates an existing consultoria.
     *
     * @param consultoriaDTO the consultoriaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultoriaDTO,
     * or with status {@code 400 (Bad Request)} if the consultoriaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultoriaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultorias")
    public ResponseEntity<ConsultoriaDTO> updateConsultoria(@Valid @RequestBody ConsultoriaDTO consultoriaDTO) throws URISyntaxException {
        log.debug("REST request to update Consultoria : {}", consultoriaDTO);
        if (consultoriaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsultoriaDTO result = consultoriaService.save(consultoriaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultoriaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consultorias} : get all the consultorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultorias in body.
     */
    @GetMapping("/consultorias")
    public List<ConsultoriaDTO> getAllConsultorias() {
        log.debug("REST request to get all Consultorias");
        return consultoriaService.findAll();
    }

    /**
     * {@code GET  /consultorias/:id} : get the "id" consultoria.
     *
     * @param id the id of the consultoriaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultoriaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultorias/{id}")
    public ResponseEntity<ConsultoriaDTO> getConsultoria(@PathVariable Long id) {
        log.debug("REST request to get Consultoria : {}", id);
        Optional<ConsultoriaDTO> consultoriaDTO = consultoriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultoriaDTO);
    }

    /**
     * {@code DELETE  /consultorias/:id} : delete the "id" consultoria.
     *
     * @param id the id of the consultoriaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultorias/{id}")
    public ResponseEntity<Void> deleteConsultoria(@PathVariable Long id) {
        log.debug("REST request to delete Consultoria : {}", id);
        consultoriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
