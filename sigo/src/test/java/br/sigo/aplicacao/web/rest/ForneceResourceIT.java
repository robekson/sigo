package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Fornece;
import br.sigo.aplicacao.repository.ForneceRepository;
import br.sigo.aplicacao.service.ForneceService;
import br.sigo.aplicacao.service.dto.ForneceDTO;
import br.sigo.aplicacao.service.mapper.ForneceMapper;

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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ForneceResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ForneceResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TAMANHO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANHO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private ForneceRepository forneceRepository;

    @Autowired
    private ForneceMapper forneceMapper;

    @Autowired
    private ForneceService forneceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restForneceMockMvc;

    private Fornece fornece;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornece createEntity(EntityManager em) {
        Fornece fornece = new Fornece()
            .quantidade(DEFAULT_QUANTIDADE)
            .data(DEFAULT_DATA)
            .tamanho(DEFAULT_TAMANHO)
            .valor(DEFAULT_VALOR);
        return fornece;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fornece createUpdatedEntity(EntityManager em) {
        Fornece fornece = new Fornece()
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .tamanho(UPDATED_TAMANHO)
            .valor(UPDATED_VALOR);
        return fornece;
    }

    @BeforeEach
    public void initTest() {
        fornece = createEntity(em);
    }

    @Test
    @Transactional
    public void createFornece() throws Exception {
        int databaseSizeBeforeCreate = forneceRepository.findAll().size();
        // Create the Fornece
        ForneceDTO forneceDTO = forneceMapper.toDto(fornece);
        restForneceMockMvc.perform(post("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isCreated());

        // Validate the Fornece in the database
        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeCreate + 1);
        Fornece testFornece = forneceList.get(forneceList.size() - 1);
        assertThat(testFornece.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testFornece.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testFornece.getTamanho()).isEqualTo(DEFAULT_TAMANHO);
        assertThat(testFornece.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createForneceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = forneceRepository.findAll().size();

        // Create the Fornece with an existing ID
        fornece.setId(1L);
        ForneceDTO forneceDTO = forneceMapper.toDto(fornece);

        // An entity with an existing ID cannot be created, so this API call must fail
        restForneceMockMvc.perform(post("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornece in the database
        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = forneceRepository.findAll().size();
        // set the field null
        fornece.setQuantidade(null);

        // Create the Fornece, which fails.
        ForneceDTO forneceDTO = forneceMapper.toDto(fornece);


        restForneceMockMvc.perform(post("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isBadRequest());

        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = forneceRepository.findAll().size();
        // set the field null
        fornece.setData(null);

        // Create the Fornece, which fails.
        ForneceDTO forneceDTO = forneceMapper.toDto(fornece);


        restForneceMockMvc.perform(post("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isBadRequest());

        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllForneces() throws Exception {
        // Initialize the database
        forneceRepository.saveAndFlush(fornece);

        // Get all the forneceList
        restForneceMockMvc.perform(get("/api/forneces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fornece.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tamanho").value(hasItem(DEFAULT_TAMANHO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getFornece() throws Exception {
        // Initialize the database
        forneceRepository.saveAndFlush(fornece);

        // Get the fornece
        restForneceMockMvc.perform(get("/api/forneces/{id}", fornece.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fornece.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.tamanho").value(DEFAULT_TAMANHO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingFornece() throws Exception {
        // Get the fornece
        restForneceMockMvc.perform(get("/api/forneces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFornece() throws Exception {
        // Initialize the database
        forneceRepository.saveAndFlush(fornece);

        int databaseSizeBeforeUpdate = forneceRepository.findAll().size();

        // Update the fornece
        Fornece updatedFornece = forneceRepository.findById(fornece.getId()).get();
        // Disconnect from session so that the updates on updatedFornece are not directly saved in db
        em.detach(updatedFornece);
        updatedFornece
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .tamanho(UPDATED_TAMANHO)
            .valor(UPDATED_VALOR);
        ForneceDTO forneceDTO = forneceMapper.toDto(updatedFornece);

        restForneceMockMvc.perform(put("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isOk());

        // Validate the Fornece in the database
        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeUpdate);
        Fornece testFornece = forneceList.get(forneceList.size() - 1);
        assertThat(testFornece.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testFornece.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testFornece.getTamanho()).isEqualTo(UPDATED_TAMANHO);
        assertThat(testFornece.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingFornece() throws Exception {
        int databaseSizeBeforeUpdate = forneceRepository.findAll().size();

        // Create the Fornece
        ForneceDTO forneceDTO = forneceMapper.toDto(fornece);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restForneceMockMvc.perform(put("/api/forneces")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(forneceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fornece in the database
        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFornece() throws Exception {
        // Initialize the database
        forneceRepository.saveAndFlush(fornece);

        int databaseSizeBeforeDelete = forneceRepository.findAll().size();

        // Delete the fornece
        restForneceMockMvc.perform(delete("/api/forneces/{id}", fornece.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fornece> forneceList = forneceRepository.findAll();
        assertThat(forneceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
