package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Consultor;
import br.sigo.aplicacao.repository.ConsultorRepository;
import br.sigo.aplicacao.service.ConsultorService;
import br.sigo.aplicacao.service.dto.ConsultorDTO;
import br.sigo.aplicacao.service.mapper.ConsultorMapper;

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
 * Integration tests for the {@link ConsultorResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConsultorResourceIT {

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

    @Autowired
    private ConsultorRepository consultorRepository;

    @Autowired
    private ConsultorMapper consultorMapper;

    @Autowired
    private ConsultorService consultorService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultorMockMvc;

    private Consultor consultor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultor createEntity(EntityManager em) {
        Consultor consultor = new Consultor()
            .nome(DEFAULT_NOME)
            .cnpj(DEFAULT_CNPJ)
            .dataContratacao(DEFAULT_DATA_CONTRATACAO)
            .email(DEFAULT_EMAIL)
            .telefone(DEFAULT_TELEFONE);
        return consultor;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consultor createUpdatedEntity(EntityManager em) {
        Consultor consultor = new Consultor()
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        return consultor;
    }

    @BeforeEach
    public void initTest() {
        consultor = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultor() throws Exception {
        int databaseSizeBeforeCreate = consultorRepository.findAll().size();
        // Create the Consultor
        ConsultorDTO consultorDTO = consultorMapper.toDto(consultor);
        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeCreate + 1);
        Consultor testConsultor = consultorList.get(consultorList.size() - 1);
        assertThat(testConsultor.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testConsultor.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testConsultor.getDataContratacao()).isEqualTo(DEFAULT_DATA_CONTRATACAO);
        assertThat(testConsultor.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testConsultor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    public void createConsultorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultorRepository.findAll().size();

        // Create the Consultor with an existing ID
        consultor.setId(1L);
        ConsultorDTO consultorDTO = consultorMapper.toDto(consultor);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setNome(null);

        // Create the Consultor, which fails.
        ConsultorDTO consultorDTO = consultorMapper.toDto(consultor);


        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataContratacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultorRepository.findAll().size();
        // set the field null
        consultor.setDataContratacao(null);

        // Create the Consultor, which fails.
        ConsultorDTO consultorDTO = consultorMapper.toDto(consultor);


        restConsultorMockMvc.perform(post("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isBadRequest());

        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultors() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        // Get all the consultorList
        restConsultorMockMvc.perform(get("/api/consultors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].dataContratacao").value(hasItem(DEFAULT_DATA_CONTRATACAO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }
    
    @Test
    @Transactional
    public void getConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        // Get the consultor
        restConsultorMockMvc.perform(get("/api/consultors/{id}", consultor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.dataContratacao").value(DEFAULT_DATA_CONTRATACAO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }
    @Test
    @Transactional
    public void getNonExistingConsultor() throws Exception {
        // Get the consultor
        restConsultorMockMvc.perform(get("/api/consultors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        int databaseSizeBeforeUpdate = consultorRepository.findAll().size();

        // Update the consultor
        Consultor updatedConsultor = consultorRepository.findById(consultor.getId()).get();
        // Disconnect from session so that the updates on updatedConsultor are not directly saved in db
        em.detach(updatedConsultor);
        updatedConsultor
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .dataContratacao(UPDATED_DATA_CONTRATACAO)
            .email(UPDATED_EMAIL)
            .telefone(UPDATED_TELEFONE);
        ConsultorDTO consultorDTO = consultorMapper.toDto(updatedConsultor);

        restConsultorMockMvc.perform(put("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isOk());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeUpdate);
        Consultor testConsultor = consultorList.get(consultorList.size() - 1);
        assertThat(testConsultor.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testConsultor.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testConsultor.getDataContratacao()).isEqualTo(UPDATED_DATA_CONTRATACAO);
        assertThat(testConsultor.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testConsultor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultor() throws Exception {
        int databaseSizeBeforeUpdate = consultorRepository.findAll().size();

        // Create the Consultor
        ConsultorDTO consultorDTO = consultorMapper.toDto(consultor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultorMockMvc.perform(put("/api/consultors")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consultorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultor in the database
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsultor() throws Exception {
        // Initialize the database
        consultorRepository.saveAndFlush(consultor);

        int databaseSizeBeforeDelete = consultorRepository.findAll().size();

        // Delete the consultor
        restConsultorMockMvc.perform(delete("/api/consultors/{id}", consultor.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consultor> consultorList = consultorRepository.findAll();
        assertThat(consultorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
