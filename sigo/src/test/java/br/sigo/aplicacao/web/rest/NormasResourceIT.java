package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Normas;
import br.sigo.aplicacao.repository.NormasRepository;
import br.sigo.aplicacao.service.NormasService;
import br.sigo.aplicacao.service.dto.NormasDTO;
import br.sigo.aplicacao.service.mapper.NormasMapper;

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

import br.sigo.aplicacao.domain.enumeration.CategoryStatus;
/**
 * Integration tests for the {@link NormasResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NormasResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final CategoryStatus DEFAULT_STATUS = CategoryStatus.EM_VIGOR;
    private static final CategoryStatus UPDATED_STATUS = CategoryStatus.EM_VIGOR;

    @Autowired
    private NormasRepository normasRepository;

    @Autowired
    private NormasMapper normasMapper;

    @Autowired
    private NormasService normasService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNormasMockMvc;

    private Normas normas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normas createEntity(EntityManager em) {
        Normas normas = new Normas()
            .codigo(DEFAULT_CODIGO)
            .titulo(DEFAULT_TITULO)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS);
        return normas;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Normas createUpdatedEntity(EntityManager em) {
        Normas normas = new Normas()
            .codigo(UPDATED_CODIGO)
            .titulo(UPDATED_TITULO)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS);
        return normas;
    }

    @BeforeEach
    public void initTest() {
        normas = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormas() throws Exception {
        int databaseSizeBeforeCreate = normasRepository.findAll().size();
        // Create the Normas
        NormasDTO normasDTO = normasMapper.toDto(normas);
        restNormasMockMvc.perform(post("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isCreated());

        // Validate the Normas in the database
        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeCreate + 1);
        Normas testNormas = normasList.get(normasList.size() - 1);
        assertThat(testNormas.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testNormas.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testNormas.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNormas.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createNormasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normasRepository.findAll().size();

        // Create the Normas with an existing ID
        normas.setId(1L);
        NormasDTO normasDTO = normasMapper.toDto(normas);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormasMockMvc.perform(post("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Normas in the database
        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = normasRepository.findAll().size();
        // set the field null
        normas.setCodigo(null);

        // Create the Normas, which fails.
        NormasDTO normasDTO = normasMapper.toDto(normas);


        restNormasMockMvc.perform(post("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isBadRequest());

        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTituloIsRequired() throws Exception {
        int databaseSizeBeforeTest = normasRepository.findAll().size();
        // set the field null
        normas.setTitulo(null);

        // Create the Normas, which fails.
        NormasDTO normasDTO = normasMapper.toDto(normas);


        restNormasMockMvc.perform(post("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isBadRequest());

        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = normasRepository.findAll().size();
        // set the field null
        normas.setDate(null);

        // Create the Normas, which fails.
        NormasDTO normasDTO = normasMapper.toDto(normas);


        restNormasMockMvc.perform(post("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isBadRequest());

        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNormas() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList
        restNormasMockMvc.perform(get("/api/normas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normas.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getNormas() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get the normas
        restNormasMockMvc.perform(get("/api/normas/{id}", normas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(normas.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNormas() throws Exception {
        // Get the normas
        restNormasMockMvc.perform(get("/api/normas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormas() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        int databaseSizeBeforeUpdate = normasRepository.findAll().size();

        // Update the normas
        Normas updatedNormas = normasRepository.findById(normas.getId()).get();
        // Disconnect from session so that the updates on updatedNormas are not directly saved in db
        em.detach(updatedNormas);
        updatedNormas
            .codigo(UPDATED_CODIGO)
            .titulo(UPDATED_TITULO)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS);
        NormasDTO normasDTO = normasMapper.toDto(updatedNormas);

        restNormasMockMvc.perform(put("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isOk());

        // Validate the Normas in the database
        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeUpdate);
        Normas testNormas = normasList.get(normasList.size() - 1);
        assertThat(testNormas.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testNormas.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testNormas.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNormas.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingNormas() throws Exception {
        int databaseSizeBeforeUpdate = normasRepository.findAll().size();

        // Create the Normas
        NormasDTO normasDTO = normasMapper.toDto(normas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormasMockMvc.perform(put("/api/normas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(normasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Normas in the database
        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNormas() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        int databaseSizeBeforeDelete = normasRepository.findAll().size();

        // Delete the normas
        restNormasMockMvc.perform(delete("/api/normas/{id}", normas.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Normas> normasList = normasRepository.findAll();
        assertThat(normasList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
