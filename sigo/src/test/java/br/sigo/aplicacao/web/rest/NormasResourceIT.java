package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Normas;
import br.sigo.aplicacao.repository.NormasRepository;
import br.sigo.aplicacao.service.NormasService;
import br.sigo.aplicacao.service.dto.NormasDTO;
import br.sigo.aplicacao.service.mapper.NormasMapper;
import br.sigo.aplicacao.service.dto.NormasCriteria;
import br.sigo.aplicacao.service.NormasQueryService;

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
    private static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    private static final CategoryStatus DEFAULT_STATUS = CategoryStatus.EM_VIGOR;
    private static final CategoryStatus UPDATED_STATUS = CategoryStatus.EM_VIGOR;

    @Autowired
    private NormasRepository normasRepository;

    @Autowired
    private NormasMapper normasMapper;

    @Autowired
    private NormasService normasService;

    @Autowired
    private NormasQueryService normasQueryService;

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
    public void getNormasByIdFiltering() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        Long id = normas.getId();

        defaultNormasShouldBeFound("id.equals=" + id);
        defaultNormasShouldNotBeFound("id.notEquals=" + id);

        defaultNormasShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNormasShouldNotBeFound("id.greaterThan=" + id);

        defaultNormasShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNormasShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNormasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo equals to DEFAULT_CODIGO
        defaultNormasShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the normasList where codigo equals to UPDATED_CODIGO
        defaultNormasShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNormasByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo not equals to DEFAULT_CODIGO
        defaultNormasShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the normasList where codigo not equals to UPDATED_CODIGO
        defaultNormasShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNormasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultNormasShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the normasList where codigo equals to UPDATED_CODIGO
        defaultNormasShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNormasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo is not null
        defaultNormasShouldBeFound("codigo.specified=true");

        // Get all the normasList where codigo is null
        defaultNormasShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNormasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo contains DEFAULT_CODIGO
        defaultNormasShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the normasList where codigo contains UPDATED_CODIGO
        defaultNormasShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNormasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where codigo does not contain DEFAULT_CODIGO
        defaultNormasShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the normasList where codigo does not contain UPDATED_CODIGO
        defaultNormasShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllNormasByTituloIsEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo equals to DEFAULT_TITULO
        defaultNormasShouldBeFound("titulo.equals=" + DEFAULT_TITULO);

        // Get all the normasList where titulo equals to UPDATED_TITULO
        defaultNormasShouldNotBeFound("titulo.equals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo not equals to DEFAULT_TITULO
        defaultNormasShouldNotBeFound("titulo.notEquals=" + DEFAULT_TITULO);

        // Get all the normasList where titulo not equals to UPDATED_TITULO
        defaultNormasShouldBeFound("titulo.notEquals=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsInShouldWork() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo in DEFAULT_TITULO or UPDATED_TITULO
        defaultNormasShouldBeFound("titulo.in=" + DEFAULT_TITULO + "," + UPDATED_TITULO);

        // Get all the normasList where titulo equals to UPDATED_TITULO
        defaultNormasShouldNotBeFound("titulo.in=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloIsNullOrNotNull() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo is not null
        defaultNormasShouldBeFound("titulo.specified=true");

        // Get all the normasList where titulo is null
        defaultNormasShouldNotBeFound("titulo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNormasByTituloContainsSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo contains DEFAULT_TITULO
        defaultNormasShouldBeFound("titulo.contains=" + DEFAULT_TITULO);

        // Get all the normasList where titulo contains UPDATED_TITULO
        defaultNormasShouldNotBeFound("titulo.contains=" + UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void getAllNormasByTituloNotContainsSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where titulo does not contain DEFAULT_TITULO
        defaultNormasShouldNotBeFound("titulo.doesNotContain=" + DEFAULT_TITULO);

        // Get all the normasList where titulo does not contain UPDATED_TITULO
        defaultNormasShouldBeFound("titulo.doesNotContain=" + UPDATED_TITULO);
    }


    @Test
    @Transactional
    public void getAllNormasByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date equals to DEFAULT_DATE
        defaultNormasShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the normasList where date equals to UPDATED_DATE
        defaultNormasShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date not equals to DEFAULT_DATE
        defaultNormasShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the normasList where date not equals to UPDATED_DATE
        defaultNormasShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsInShouldWork() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNormasShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the normasList where date equals to UPDATED_DATE
        defaultNormasShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date is not null
        defaultNormasShouldBeFound("date.specified=true");

        // Get all the normasList where date is null
        defaultNormasShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date is greater than or equal to DEFAULT_DATE
        defaultNormasShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the normasList where date is greater than or equal to UPDATED_DATE
        defaultNormasShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date is less than or equal to DEFAULT_DATE
        defaultNormasShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the normasList where date is less than or equal to SMALLER_DATE
        defaultNormasShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date is less than DEFAULT_DATE
        defaultNormasShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the normasList where date is less than UPDATED_DATE
        defaultNormasShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNormasByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where date is greater than DEFAULT_DATE
        defaultNormasShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the normasList where date is greater than SMALLER_DATE
        defaultNormasShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }


    @Test
    @Transactional
    public void getAllNormasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where status equals to DEFAULT_STATUS
        defaultNormasShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the normasList where status equals to UPDATED_STATUS
        defaultNormasShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNormasByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where status not equals to DEFAULT_STATUS
        defaultNormasShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the normasList where status not equals to UPDATED_STATUS
        defaultNormasShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNormasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultNormasShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the normasList where status equals to UPDATED_STATUS
        defaultNormasShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllNormasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        normasRepository.saveAndFlush(normas);

        // Get all the normasList where status is not null
        defaultNormasShouldBeFound("status.specified=true");

        // Get all the normasList where status is null
        defaultNormasShouldNotBeFound("status.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNormasShouldBeFound(String filter) throws Exception {
        restNormasMockMvc.perform(get("/api/normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normas.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restNormasMockMvc.perform(get("/api/normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNormasShouldNotBeFound(String filter) throws Exception {
        restNormasMockMvc.perform(get("/api/normas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNormasMockMvc.perform(get("/api/normas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
