package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.service.VendaService;
import br.sigo.aplicacao.web.rest.errors.BadRequestAlertException;
import br.sigo.aplicacao.service.dto.VendaDTO;
import br.sigo.aplicacao.service.dto.VendaCriteria;
import br.sigo.aplicacao.service.VendaQueryService;

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
 * REST controller for managing {@link br.sigo.aplicacao.domain.Venda}.
 */
@RestController
@RequestMapping("/api")
public class VendaResource {

    private final Logger log = LoggerFactory.getLogger(VendaResource.class);

    private static final String ENTITY_NAME = "venda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendaService vendaService;

    private final VendaQueryService vendaQueryService;

    public VendaResource(VendaService vendaService, VendaQueryService vendaQueryService) {
        this.vendaService = vendaService;
        this.vendaQueryService = vendaQueryService;
    }

    /**
     * {@code POST  /vendas} : Create a new venda.
     *
     * @param vendaDTO the vendaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vendaDTO, or with status {@code 400 (Bad Request)} if the venda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendas")
    public ResponseEntity<VendaDTO> createVenda(@Valid @RequestBody VendaDTO vendaDTO) throws URISyntaxException {
        log.debug("REST request to save Venda : {}", vendaDTO);
        if (vendaDTO.getId() != null) {
            throw new BadRequestAlertException("A new venda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VendaDTO result = vendaService.save(vendaDTO);
        return ResponseEntity.created(new URI("/api/vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendas} : Updates an existing venda.
     *
     * @param vendaDTO the vendaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vendaDTO,
     * or with status {@code 400 (Bad Request)} if the vendaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vendaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendas")
    public ResponseEntity<VendaDTO> updateVenda(@Valid @RequestBody VendaDTO vendaDTO) throws URISyntaxException {
        log.debug("REST request to update Venda : {}", vendaDTO);
        if (vendaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VendaDTO result = vendaService.save(vendaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vendaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vendas} : get all the vendas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendas in body.
     */
    @GetMapping("/vendas")
    public ResponseEntity<List<VendaDTO>> getAllVendas(VendaCriteria criteria) {
        log.debug("REST request to get Vendas by criteria: {}", criteria);
        List<VendaDTO> entityList = vendaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /vendas/count} : count all the vendas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/vendas/count")
    public ResponseEntity<Long> countVendas(VendaCriteria criteria) {
        log.debug("REST request to count Vendas by criteria: {}", criteria);
        return ResponseEntity.ok().body(vendaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /vendas/:id} : get the "id" venda.
     *
     * @param id the id of the vendaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vendaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendas/{id}")
    public ResponseEntity<VendaDTO> getVenda(@PathVariable Long id) {
        log.debug("REST request to get Venda : {}", id);
        Optional<VendaDTO> vendaDTO = vendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(vendaDTO);
    }

    /**
     * {@code DELETE  /vendas/:id} : delete the "id" venda.
     *
     * @param id the id of the vendaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendas/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        log.debug("REST request to delete Venda : {}", id);
        vendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
