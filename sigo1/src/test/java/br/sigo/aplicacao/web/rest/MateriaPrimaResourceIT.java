package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.MateriaPrima;
import br.sigo.aplicacao.repository.MateriaPrimaRepository;
import br.sigo.aplicacao.service.MateriaPrimaService;
import br.sigo.aplicacao.service.dto.MateriaPrimaDTO;
import br.sigo.aplicacao.service.mapper.MateriaPrimaMapper;

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
 * Integration tests for the {@link MateriaPrimaResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MateriaPrimaResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPOSICAO = "AAAAAAAAAA";
    private static final String UPDATED_COMPOSICAO = "BBBBBBBBBB";

    private static final String DEFAULT_FIO = "AAAAAAAAAA";
    private static final String UPDATED_FIO = "BBBBBBBBBB";

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private MateriaPrimaMapper materiaPrimaMapper;

    @Autowired
    private MateriaPrimaService materiaPrimaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMateriaPrimaMockMvc;

    private MateriaPrima materiaPrima;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaPrima createEntity(EntityManager em) {
        MateriaPrima materiaPrima = new MateriaPrima()
            .tipo(DEFAULT_TIPO)
            .composicao(DEFAULT_COMPOSICAO)
            .fio(DEFAULT_FIO);
        return materiaPrima;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MateriaPrima createUpdatedEntity(EntityManager em) {
        MateriaPrima materiaPrima = new MateriaPrima()
            .tipo(UPDATED_TIPO)
            .composicao(UPDATED_COMPOSICAO)
            .fio(UPDATED_FIO);
        return materiaPrima;
    }

    @BeforeEach
    public void initTest() {
        materiaPrima = createEntity(em);
    }

    @Test
    @Transactional
    public void createMateriaPrima() throws Exception {
        int databaseSizeBeforeCreate = materiaPrimaRepository.findAll().size();
        // Create the MateriaPrima
        MateriaPrimaDTO materiaPrimaDTO = materiaPrimaMapper.toDto(materiaPrima);
        restMateriaPrimaMockMvc.perform(post("/api/materia-primas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiaPrimaDTO)))
            .andExpect(status().isCreated());

        // Validate the MateriaPrima in the database
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeCreate + 1);
        MateriaPrima testMateriaPrima = materiaPrimaList.get(materiaPrimaList.size() - 1);
        assertThat(testMateriaPrima.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testMateriaPrima.getComposicao()).isEqualTo(DEFAULT_COMPOSICAO);
        assertThat(testMateriaPrima.getFio()).isEqualTo(DEFAULT_FIO);
    }

    @Test
    @Transactional
    public void createMateriaPrimaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = materiaPrimaRepository.findAll().size();

        // Create the MateriaPrima with an existing ID
        materiaPrima.setId(1L);
        MateriaPrimaDTO materiaPrimaDTO = materiaPrimaMapper.toDto(materiaPrima);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMateriaPrimaMockMvc.perform(post("/api/materia-primas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiaPrimaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MateriaPrima in the database
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = materiaPrimaRepository.findAll().size();
        // set the field null
        materiaPrima.setTipo(null);

        // Create the MateriaPrima, which fails.
        MateriaPrimaDTO materiaPrimaDTO = materiaPrimaMapper.toDto(materiaPrima);


        restMateriaPrimaMockMvc.perform(post("/api/materia-primas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiaPrimaDTO)))
            .andExpect(status().isBadRequest());

        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMateriaPrimas() throws Exception {
        // Initialize the database
        materiaPrimaRepository.saveAndFlush(materiaPrima);

        // Get all the materiaPrimaList
        restMateriaPrimaMockMvc.perform(get("/api/materia-primas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materiaPrima.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].composicao").value(hasItem(DEFAULT_COMPOSICAO)))
            .andExpect(jsonPath("$.[*].fio").value(hasItem(DEFAULT_FIO)));
    }
    
    @Test
    @Transactional
    public void getMateriaPrima() throws Exception {
        // Initialize the database
        materiaPrimaRepository.saveAndFlush(materiaPrima);

        // Get the materiaPrima
        restMateriaPrimaMockMvc.perform(get("/api/materia-primas/{id}", materiaPrima.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materiaPrima.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.composicao").value(DEFAULT_COMPOSICAO))
            .andExpect(jsonPath("$.fio").value(DEFAULT_FIO));
    }
    @Test
    @Transactional
    public void getNonExistingMateriaPrima() throws Exception {
        // Get the materiaPrima
        restMateriaPrimaMockMvc.perform(get("/api/materia-primas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMateriaPrima() throws Exception {
        // Initialize the database
        materiaPrimaRepository.saveAndFlush(materiaPrima);

        int databaseSizeBeforeUpdate = materiaPrimaRepository.findAll().size();

        // Update the materiaPrima
        MateriaPrima updatedMateriaPrima = materiaPrimaRepository.findById(materiaPrima.getId()).get();
        // Disconnect from session so that the updates on updatedMateriaPrima are not directly saved in db
        em.detach(updatedMateriaPrima);
        updatedMateriaPrima
            .tipo(UPDATED_TIPO)
            .composicao(UPDATED_COMPOSICAO)
            .fio(UPDATED_FIO);
        MateriaPrimaDTO materiaPrimaDTO = materiaPrimaMapper.toDto(updatedMateriaPrima);

        restMateriaPrimaMockMvc.perform(put("/api/materia-primas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiaPrimaDTO)))
            .andExpect(status().isOk());

        // Validate the MateriaPrima in the database
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeUpdate);
        MateriaPrima testMateriaPrima = materiaPrimaList.get(materiaPrimaList.size() - 1);
        assertThat(testMateriaPrima.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testMateriaPrima.getComposicao()).isEqualTo(UPDATED_COMPOSICAO);
        assertThat(testMateriaPrima.getFio()).isEqualTo(UPDATED_FIO);
    }

    @Test
    @Transactional
    public void updateNonExistingMateriaPrima() throws Exception {
        int databaseSizeBeforeUpdate = materiaPrimaRepository.findAll().size();

        // Create the MateriaPrima
        MateriaPrimaDTO materiaPrimaDTO = materiaPrimaMapper.toDto(materiaPrima);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMateriaPrimaMockMvc.perform(put("/api/materia-primas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(materiaPrimaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MateriaPrima in the database
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMateriaPrima() throws Exception {
        // Initialize the database
        materiaPrimaRepository.saveAndFlush(materiaPrima);

        int databaseSizeBeforeDelete = materiaPrimaRepository.findAll().size();

        // Delete the materiaPrima
        restMateriaPrimaMockMvc.perform(delete("/api/materia-primas/{id}", materiaPrima.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MateriaPrima> materiaPrimaList = materiaPrimaRepository.findAll();
        assertThat(materiaPrimaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
