package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.ConsultorService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.ConsultorDTO;

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
 * REST controller for managing {@link br.sigo.aplicacao.domain.Consultor}.
 */
@RestController
@RequestMapping("/api")
public class ConsultorResource {

    private final Logger log = LoggerFactory.getLogger(ConsultorResource.class);

    private static final String ENTITY_NAME = "consultor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultorService consultorService;

    public ConsultorResource(ConsultorService consultorService) {
        this.consultorService = consultorService;
    }

    /**
     * {@code POST  /consultors} : Create a new consultor.
     *
     * @param consultorDTO the consultorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultorDTO, or with status {@code 400 (Bad Request)} if the consultor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consultors")
    public ResponseEntity<ConsultorDTO> createConsultor(@Valid @RequestBody ConsultorDTO consultorDTO) throws URISyntaxException {
        log.debug("REST request to save Consultor : {}", consultorDTO);
        if (consultorDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultorDTO result = consultorService.save(consultorDTO);
        return ResponseEntity.created(new URI("/api/consultors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consultors} : Updates an existing consultor.
     *
     * @param consultorDTO the consultorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultorDTO,
     * or with status {@code 400 (Bad Request)} if the consultorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consultors")
    public ResponseEntity<ConsultorDTO> updateConsultor(@Valid @RequestBody ConsultorDTO consultorDTO) throws URISyntaxException {
        log.debug("REST request to update Consultor : {}", consultorDTO);
        if (consultorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConsultorDTO result = consultorService.save(consultorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /consultors} : get all the consultors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultors in body.
     */
    @GetMapping("/consultors")
    public ResponseEntity<List<ConsultorDTO>> getAllConsultors(Pageable pageable) {
        log.debug("REST request to get a page of Consultors");
        Page<ConsultorDTO> page = consultorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consultors/:id} : get the "id" consultor.
     *
     * @param id the id of the consultorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultors/{id}")
    public ResponseEntity<ConsultorDTO> getConsultor(@PathVariable Long id) {
        log.debug("REST request to get Consultor : {}", id);
        Optional<ConsultorDTO> consultorDTO = consultorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultorDTO);
    }

    /**
     * {@code DELETE  /consultors/:id} : delete the "id" consultor.
     *
     * @param id the id of the consultorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consultors/{id}")
    public ResponseEntity<Void> deleteConsultor(@PathVariable Long id) {
        log.debug("REST request to delete Consultor : {}", id);
        consultorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
