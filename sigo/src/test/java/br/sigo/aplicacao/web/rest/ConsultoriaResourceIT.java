package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Consultoria;
import br.sigo.aplicacao.repository.ConsultoriaRepository;
import br.sigo.aplicacao.service.ConsultoriaService;
import br.sigo.aplicacao.service.dto.ConsultoriaDTO;
import br.sigo.aplicacao.service.mapper.ConsultoriaMapper;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConsultoriaResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsultoriaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CONTRATACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CONTRATACAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO_SERVICO_PRESTADO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_SERVICO_PRESTADO = "BBBBBBBBBB";

    @Autowired
    private ConsultoriaRepository consultoriaRepository;

    @Autowired
    private ConsultoriaMapper consultoriaMapper;

    @Autowired
    private ConsultoriaService consultoriaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultoriaMockMvc;

    private Consultoria consultoria;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultoria createEntity(EntityManager em) {
        Consultoria consultoria = new Consultoria()
            .nome(DEFAULT_NOME)
            .cnpj(DEFAULT_CNPJ)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE)
            .tipoServicoPrestado(DEFAULT_TIPO_SERVICO_PRESTADO);
        return consultoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultoria createUpdatedEntity(EntityManager em) {
        Consultoria consultoria = new Consultoria()
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .tipoServicoPrestado(UPDATED_TIPO_SERVICO_PRESTADO);
        return consultoria;
    }

    @BeforeEach
    public void initTest() {
        consultoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultoria() throws Exception {
        int databaseSizeBeforeCreate = consultoriaRepository.findAll().size();
        // Create the Consultoria
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(consultoria);
        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeCreate + 1);
        Consultoria testConsultoria = consultoriaList.get(consultoriaList.size() - 1);
        assertThat(testConsultoria.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsultoria.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testConsultoria.getDataContratacao()).isEqualTo(DEFAULT_DATA_CONTRATACAO);
        assertThat(testConsultoria.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testConsultoria.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testConsultoria.getTipoServicoPrestado()).isEqualTo(DEFAULT_TIPO_SERVICO_PRESTADO);
    }

    @Test
    @Transactional
    public void createConsultoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultoriaRepository.findAll().size();

        // Create the Consultoria with an existing ID
        consultoria.setId(1L);
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(consultoria);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoriaRepository.findAll().size();
        // set the field null
        consultoria.setNome(null);

        // Create the Consultoria, which fails.
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(consultoria);


        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isBadRequest());

        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataContratacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultoriaRepository.findAll().size();
        // set the field null
        consultoria.setDataContratacao(null);

        // Create the Consultoria, which fails.
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(consultoria);


        restConsultoriaMockMvc.perform(post("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isBadRequest());

        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultorias() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get all the consultoriaList
        restConsultoriaMockMvc.perform(get("/api/consultorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)))
            .andExpect(jsonPath("$.[*].tipoServicoPrestado").value(hasItem(DEFAULT_TIPO_SERVICO_PRESTADO)));
    }
    
    @Test
    @Transactional
    public void getConsultoria() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        // Get the consultoria
        restConsultoriaMockMvc.perform(get("/api/consultorias/{id}", consultoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultoria.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE))
            .andExpect(jsonPath("$.tipoServicoPrestado").value(DEFAULT_TIPO_SERVICO_PRESTADO));
    }
    @Test
    @Transactional
    public void getNonExistingConsultoria() throws Exception {
        // Get the consultoria
        restConsultoriaMockMvc.perform(get("/api/consultorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultoria() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        int databaseSizeBeforeUpdate = consultoriaRepository.findAll().size();

        // Update the consultoria
        Consultoria updatedConsultoria = consultoriaRepository.findById(consultoria.getId()).get();
        // Disconnect from session so that the updates on updatedConsultoria are not directly saved in db
        em.detach(updatedConsultoria);
        updatedConsultoria
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE)
            .tipoServicoPrestado(UPDATED_TIPO_SERVICO_PRESTADO);
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(updatedConsultoria);

        restConsultoriaMockMvc.perform(put("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isOk());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeUpdate);
        Consultoria testConsultoria = consultoriaList.get(consultoriaList.size() - 1);
        assertThat(testConsultoria.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsultoria.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testConsultoria.getDataContratacao()).isEqualTo(UPDATED_DATA_CONTRATACAO);
        assertThat(testConsultoria.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testConsultoria.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testConsultoria.getTipoServicoPrestado()).isEqualTo(UPDATED_TIPO_SERVICO_PRESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultoria() throws Exception {
        int databaseSizeBeforeUpdate = consultoriaRepository.findAll().size();

        // Create the Consultoria
        ConsultoriaDTO consultoriaDTO = consultoriaMapper.toDto(consultoria);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultoriaMockMvc.perform(put("/api/consultorias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultoriaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultoria in the database
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsultoria() throws Exception {
        // Initialize the database
        consultoriaRepository.saveAndFlush(consultoria);

        int databaseSizeBeforeDelete = consultoriaRepository.findAll().size();

        // Delete the consultoria
        restConsultoriaMockMvc.perform(delete("/api/consultorias/{id}", consultoria.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultoria> consultoriaList = consultoriaRepository.findAll();
        assertThat(consultoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
