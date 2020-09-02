package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Compra;
import br.sigo.aplicacao.repository.CompraRepository;
import br.sigo.aplicacao.service.CompraService;
import br.sigo.aplicacao.service.dto.CompraDTO;
import br.sigo.aplicacao.service.mapper.CompraMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompraResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompraResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TAMANHO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANHO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private CompraRepository compraRepository;

    @Mock
    private CompraRepository compraRepositoryMock;

    @Autowired
    private CompraMapper compraMapper;

    @Mock
    private CompraService compraServiceMock;

    @Autowired
    private CompraService compraService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompraMockMvc;

    private Compra compra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compra createEntity(EntityManager em) {
        Compra compra = new Compra()
            .quantidade(DEFAULT_QUANTIDADE)
            .data(DEFAULT_DATA)
            .tamanho(DEFAULT_TAMANHO)
            .valor(DEFAULT_VALOR);
        return compra;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compra createUpdatedEntity(EntityManager em) {
        Compra compra = new Compra()
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .tamanho(UPDATED_TAMANHO)
            .valor(UPDATED_VALOR);
        return compra;
    }

    @BeforeEach
    public void initTest() {
        compra = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompra() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();
        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isCreated());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate + 1);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testCompra.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testCompra.getTamanho()).isEqualTo(DEFAULT_TAMANHO);
        assertThat(testCompra.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createCompraWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compraRepository.findAll().size();

        // Create the Compra with an existing ID
        compra.setId(1L);
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompraMockMvc.perform(post("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setQuantidade(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);


        restCompraMockMvc.perform(post("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = compraRepository.findAll().size();
        // set the field null
        compra.setData(null);

        // Create the Compra, which fails.
        CompraDTO compraDTO = compraMapper.toDto(compra);


        restCompraMockMvc.perform(post("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCompras() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get all the compraList
        restCompraMockMvc.perform(get("/api/compras?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compra.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].tamanho").value(hasItem(DEFAULT_TAMANHO)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllComprasWithEagerRelationshipsIsEnabled() throws Exception {
        when(compraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompraMockMvc.perform(get("/api/compras?eagerload=true"))
            .andExpect(status().isOk());

        verify(compraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllComprasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(compraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCompraMockMvc.perform(get("/api/compras?eagerload=true"))
            .andExpect(status().isOk());

        verify(compraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", compra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(compra.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.tamanho").value(DEFAULT_TAMANHO))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCompra() throws Exception {
        // Get the compra
        restCompraMockMvc.perform(get("/api/compras/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Update the compra
        Compra updatedCompra = compraRepository.findById(compra.getId()).get();
        // Disconnect from session so that the updates on updatedCompra are not directly saved in db
        em.detach(updatedCompra);
        updatedCompra
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .tamanho(UPDATED_TAMANHO)
            .valor(UPDATED_VALOR);
        CompraDTO compraDTO = compraMapper.toDto(updatedCompra);

        restCompraMockMvc.perform(put("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isOk());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
        Compra testCompra = compraList.get(compraList.size() - 1);
        assertThat(testCompra.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testCompra.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testCompra.getTamanho()).isEqualTo(UPDATED_TAMANHO);
        assertThat(testCompra.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingCompra() throws Exception {
        int databaseSizeBeforeUpdate = compraRepository.findAll().size();

        // Create the Compra
        CompraDTO compraDTO = compraMapper.toDto(compra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompraMockMvc.perform(put("/api/compras")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(compraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Compra in the database
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompra() throws Exception {
        // Initialize the database
        compraRepository.saveAndFlush(compra);

        int databaseSizeBeforeDelete = compraRepository.findAll().size();

        // Delete the compra
        restCompraMockMvc.perform(delete("/api/compras/{id}", compra.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compra> compraList = compraRepository.findAll();
        assertThat(compraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
