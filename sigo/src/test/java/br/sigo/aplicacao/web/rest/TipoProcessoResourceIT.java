package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.TipoProcesso;
import br.sigo.aplicacao.repository.TipoProcessoRepository;
import br.sigo.aplicacao.service.TipoProcessoService;
import br.sigo.aplicacao.service.dto.TipoProcessoDTO;
import br.sigo.aplicacao.service.mapper.TipoProcessoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoProcessoResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoProcessoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoProcessoRepository tipoProcessoRepository;

    @Autowired
    private TipoProcessoMapper tipoProcessoMapper;

    @Autowired
    private TipoProcessoService tipoProcessoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoProcessoMockMvc;

    private TipoProcesso tipoProcesso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProcesso createEntity(EntityManager em) {
        TipoProcesso tipoProcesso = new TipoProcesso()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO);
        return tipoProcesso;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProcesso createUpdatedEntity(EntityManager em) {
        TipoProcesso tipoProcesso = new TipoProcesso()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        return tipoProcesso;
    }

    @BeforeEach
    public void initTest() {
        tipoProcesso = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProcesso() throws Exception {
        int databaseSizeBeforeCreate = tipoProcessoRepository.findAll().size();
        // Create the TipoProcesso
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(tipoProcesso);
        restTipoProcessoMockMvc.perform(post("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoProcesso in the database
        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProcesso testTipoProcesso = tipoProcessoList.get(tipoProcessoList.size() - 1);
        assertThat(testTipoProcesso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testTipoProcesso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProcessoRepository.findAll().size();

        // Create the TipoProcesso with an existing ID
        tipoProcesso.setId(1L);
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(tipoProcesso);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProcessoMockMvc.perform(post("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProcesso in the database
        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProcessoRepository.findAll().size();
        // set the field null
        tipoProcesso.setNome(null);

        // Create the TipoProcesso, which fails.
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(tipoProcesso);


        restTipoProcessoMockMvc.perform(post("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescricaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProcessoRepository.findAll().size();
        // set the field null
        tipoProcesso.setDescricao(null);

        // Create the TipoProcesso, which fails.
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(tipoProcesso);


        restTipoProcessoMockMvc.perform(post("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoProcessos() throws Exception {
        // Initialize the database
        tipoProcessoRepository.saveAndFlush(tipoProcesso);

        // Get all the tipoProcessoList
        restTipoProcessoMockMvc.perform(get("/api/tipo-processos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProcesso.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoProcesso() throws Exception {
        // Initialize the database
        tipoProcessoRepository.saveAndFlush(tipoProcesso);

        // Get the tipoProcesso
        restTipoProcessoMockMvc.perform(get("/api/tipo-processos/{id}", tipoProcesso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProcesso.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }
    @Test
    @Transactional
    public void getNonExistingTipoProcesso() throws Exception {
        // Get the tipoProcesso
        restTipoProcessoMockMvc.perform(get("/api/tipo-processos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProcesso() throws Exception {
        // Initialize the database
        tipoProcessoRepository.saveAndFlush(tipoProcesso);

        int databaseSizeBeforeUpdate = tipoProcessoRepository.findAll().size();

        // Update the tipoProcesso
        TipoProcesso updatedTipoProcesso = tipoProcessoRepository.findById(tipoProcesso.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProcesso are not directly saved in db
        em.detach(updatedTipoProcesso);
        updatedTipoProcesso
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO);
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(updatedTipoProcesso);

        restTipoProcessoMockMvc.perform(put("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoProcesso in the database
        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeUpdate);
        TipoProcesso testTipoProcesso = tipoProcessoList.get(tipoProcessoList.size() - 1);
        assertThat(testTipoProcesso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testTipoProcesso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProcesso() throws Exception {
        int databaseSizeBeforeUpdate = tipoProcessoRepository.findAll().size();

        // Create the TipoProcesso
        TipoProcessoDTO tipoProcessoDTO = tipoProcessoMapper.toDto(tipoProcesso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProcessoMockMvc.perform(put("/api/tipo-processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProcessoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProcesso in the database
        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoProcesso() throws Exception {
        // Initialize the database
        tipoProcessoRepository.saveAndFlush(tipoProcesso);

        int databaseSizeBeforeDelete = tipoProcessoRepository.findAll().size();

        // Delete the tipoProcesso
        restTipoProcessoMockMvc.perform(delete("/api/tipo-processos/{id}", tipoProcesso.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProcesso> tipoProcessoList = tipoProcessoRepository.findAll();
        assertThat(tipoProcessoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
