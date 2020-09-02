package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Venda;
import br.sigo.aplicacao.repository.VendaRepository;
import br.sigo.aplicacao.service.VendaService;
import br.sigo.aplicacao.service.dto.VendaDTO;
import br.sigo.aplicacao.service.mapper.VendaMapper;

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
 * Integration tests for the {@link VendaResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class VendaResourceIT {

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ENTREGA = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaMapper vendaMapper;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVendaMockMvc;

    private Venda venda;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createEntity(EntityManager em) {
        Venda venda = new Venda()
            .quantidade(DEFAULT_QUANTIDADE)
            .data(DEFAULT_DATA)
            .dataEntrega(DEFAULT_DATA_ENTREGA)
            .valor(DEFAULT_VALOR);
        return venda;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Venda createUpdatedEntity(EntityManager em) {
        Venda venda = new Venda()
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .valor(UPDATED_VALOR);
        return venda;
    }

    @BeforeEach
    public void initTest() {
        venda = createEntity(em);
    }

    @Test
    @Transactional
    public void createVenda() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();
        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isCreated());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate + 1);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testVenda.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testVenda.getDataEntrega()).isEqualTo(DEFAULT_DATA_ENTREGA);
        assertThat(testVenda.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createVendaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vendaRepository.findAll().size();

        // Create the Venda with an existing ID
        venda.setId(1L);
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendaRepository.findAll().size();
        // set the field null
        venda.setQuantidade(null);

        // Create the Venda, which fails.
        VendaDTO vendaDTO = vendaMapper.toDto(venda);


        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = vendaRepository.findAll().size();
        // set the field null
        venda.setData(null);

        // Create the Venda, which fails.
        VendaDTO vendaDTO = vendaMapper.toDto(venda);


        restVendaMockMvc.perform(post("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVendas() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(DEFAULT_DATA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", venda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(venda.getId().intValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.dataEntrega").value(DEFAULT_DATA_ENTREGA.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingVenda() throws Exception {
        // Get the venda
        restVendaMockMvc.perform(get("/api/vendas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Update the venda
        Venda updatedVenda = vendaRepository.findById(venda.getId()).get();
        // Disconnect from session so that the updates on updatedVenda are not directly saved in db
        em.detach(updatedVenda);
        updatedVenda
            .quantidade(UPDATED_QUANTIDADE)
            .data(UPDATED_DATA)
            .dataEntrega(UPDATED_DATA_ENTREGA)
            .valor(UPDATED_VALOR);
        VendaDTO vendaDTO = vendaMapper.toDto(updatedVenda);

        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isOk());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
        Venda testVenda = vendaList.get(vendaList.size() - 1);
        assertThat(testVenda.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testVenda.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testVenda.getDataEntrega()).isEqualTo(UPDATED_DATA_ENTREGA);
        assertThat(testVenda.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingVenda() throws Exception {
        int databaseSizeBeforeUpdate = vendaRepository.findAll().size();

        // Create the Venda
        VendaDTO vendaDTO = vendaMapper.toDto(venda);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVendaMockMvc.perform(put("/api/vendas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(vendaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Venda in the database
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVenda() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        int databaseSizeBeforeDelete = vendaRepository.findAll().size();

        // Delete the venda
        restVendaMockMvc.perform(delete("/api/vendas/{id}", venda.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Venda> vendaList = vendaRepository.findAll();
        assertThat(vendaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
