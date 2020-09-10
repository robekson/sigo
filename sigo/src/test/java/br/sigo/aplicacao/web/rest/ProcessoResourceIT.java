package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Processo;
import br.sigo.aplicacao.repository.ProcessoRepository;
import br.sigo.aplicacao.service.ProcessoService;
import br.sigo.aplicacao.service.dto.ProcessoDTO;
import br.sigo.aplicacao.service.mapper.ProcessoMapper;

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

import br.sigo.aplicacao.domain.enumeration.StatusProcesso;
import br.sigo.aplicacao.domain.enumeration.Etapa;
/**
 * Integration tests for the {@link ProcessoResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProcessoResourceIT {

    private static final Integer DEFAULT_ID_MATERIA_PRIMA = 1;
    private static final Integer UPDATED_ID_MATERIA_PRIMA = 2;

    private static final Integer DEFAULT_ID_PRODUTO = 1;
    private static final Integer UPDATED_ID_PRODUTO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final StatusProcesso DEFAULT_STATUS = StatusProcesso.ANDAMENTO;
    private static final StatusProcesso UPDATED_STATUS = StatusProcesso.FINALIZADO;

    private static final Etapa DEFAULT_ETAPA = Etapa.FIBRA_FILAMENTO;
    private static final Etapa UPDATED_ETAPA = Etapa.TEXTIL;

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ProcessoMapper processoMapper;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessoMockMvc;

    private Processo processo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createEntity(EntityManager em) {
        Processo processo = new Processo()
            .idMateriaPrima(DEFAULT_ID_MATERIA_PRIMA)
            .idProduto(DEFAULT_ID_PRODUTO)
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .status(DEFAULT_STATUS)
            .etapa(DEFAULT_ETAPA);
        return processo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Processo createUpdatedEntity(EntityManager em) {
        Processo processo = new Processo()
            .idMateriaPrima(UPDATED_ID_MATERIA_PRIMA)
            .idProduto(UPDATED_ID_PRODUTO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .etapa(UPDATED_ETAPA);
        return processo;
    }

    @BeforeEach
    public void initTest() {
        processo = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesso() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();
        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isCreated());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate + 1);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdMateriaPrima()).isEqualTo(DEFAULT_ID_MATERIA_PRIMA);
        assertThat(testProcesso.getIdProduto()).isEqualTo(DEFAULT_ID_PRODUTO);
        assertThat(testProcesso.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProcesso.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProcesso.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProcesso.getEtapa()).isEqualTo(DEFAULT_ETAPA);
    }

    @Test
    @Transactional
    public void createProcessoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = processoRepository.findAll().size();

        // Create the Processo with an existing ID
        processo.setId(1L);
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessoMockMvc.perform(post("/api/processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcessos() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get all the processoList
        restProcessoMockMvc.perform(get("/api/processos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processo.getId().intValue())))
            .andExpect(jsonPath("$.[*].idMateriaPrima").value(hasItem(DEFAULT_ID_MATERIA_PRIMA)))
            .andExpect(jsonPath("$.[*].idProduto").value(hasItem(DEFAULT_ID_PRODUTO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].etapa").value(hasItem(DEFAULT_ETAPA.toString())));
    }
    
    @Test
    @Transactional
    public void getProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", processo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processo.getId().intValue()))
            .andExpect(jsonPath("$.idMateriaPrima").value(DEFAULT_ID_MATERIA_PRIMA))
            .andExpect(jsonPath("$.idProduto").value(DEFAULT_ID_PRODUTO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.etapa").value(DEFAULT_ETAPA.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingProcesso() throws Exception {
        // Get the processo
        restProcessoMockMvc.perform(get("/api/processos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Update the processo
        Processo updatedProcesso = processoRepository.findById(processo.getId()).get();
        // Disconnect from session so that the updates on updatedProcesso are not directly saved in db
        em.detach(updatedProcesso);
        updatedProcesso
            .idMateriaPrima(UPDATED_ID_MATERIA_PRIMA)
            .idProduto(UPDATED_ID_PRODUTO)
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .status(UPDATED_STATUS)
            .etapa(UPDATED_ETAPA);
        ProcessoDTO processoDTO = processoMapper.toDto(updatedProcesso);

        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isOk());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
        Processo testProcesso = processoList.get(processoList.size() - 1);
        assertThat(testProcesso.getIdMateriaPrima()).isEqualTo(UPDATED_ID_MATERIA_PRIMA);
        assertThat(testProcesso.getIdProduto()).isEqualTo(UPDATED_ID_PRODUTO);
        assertThat(testProcesso.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProcesso.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcesso.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProcesso.getEtapa()).isEqualTo(UPDATED_ETAPA);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesso() throws Exception {
        int databaseSizeBeforeUpdate = processoRepository.findAll().size();

        // Create the Processo
        ProcessoDTO processoDTO = processoMapper.toDto(processo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessoMockMvc.perform(put("/api/processos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(processoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Processo in the database
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcesso() throws Exception {
        // Initialize the database
        processoRepository.saveAndFlush(processo);

        int databaseSizeBeforeDelete = processoRepository.findAll().size();

        // Delete the processo
        restProcessoMockMvc.perform(delete("/api/processos/{id}", processo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Processo> processoList = processoRepository.findAll();
        assertThat(processoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
