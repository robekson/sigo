package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Venda;
import br.sigo.aplicacao.domain.Produto;
import br.sigo.aplicacao.domain.Funcionario;
import br.sigo.aplicacao.repository.VendaRepository;
import br.sigo.aplicacao.service.VendaService;
import br.sigo.aplicacao.service.dto.VendaDTO;
import br.sigo.aplicacao.service.mapper.VendaMapper;
import br.sigo.aplicacao.service.dto.VendaCriteria;
import br.sigo.aplicacao.service.VendaQueryService;

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
    private static final Integer SMALLER_QUANTIDADE = 1 - 1;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_DATA_ENTREGA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ENTREGA = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_DATA_ENTREGA = LocalDate.ofEpochDay(-1L);

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);
    private static final BigDecimal SMALLER_VALOR = new BigDecimal(1 - 1);

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaMapper vendaMapper;

    @Autowired
    private VendaService vendaService;

    @Autowired
    private VendaQueryService vendaQueryService;

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
    public void getVendasByIdFiltering() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        Long id = venda.getId();

        defaultVendaShouldBeFound("id.equals=" + id);
        defaultVendaShouldNotBeFound("id.notEquals=" + id);

        defaultVendaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.greaterThan=" + id);

        defaultVendaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultVendaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade equals to DEFAULT_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade not equals to DEFAULT_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.notEquals=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade not equals to UPDATED_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.notEquals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the vendaList where quantidade equals to UPDATED_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade is not null
        defaultVendaShouldBeFound("quantidade.specified=true");

        // Get all the vendaList where quantidade is null
        defaultVendaShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade is greater than or equal to DEFAULT_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.greaterThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade is greater than or equal to UPDATED_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.greaterThanOrEqual=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade is less than or equal to DEFAULT_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.lessThanOrEqual=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade is less than or equal to SMALLER_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.lessThanOrEqual=" + SMALLER_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade is less than DEFAULT_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade is less than UPDATED_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllVendasByQuantidadeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where quantidade is greater than DEFAULT_QUANTIDADE
        defaultVendaShouldNotBeFound("quantidade.greaterThan=" + DEFAULT_QUANTIDADE);

        // Get all the vendaList where quantidade is greater than SMALLER_QUANTIDADE
        defaultVendaShouldBeFound("quantidade.greaterThan=" + SMALLER_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllVendasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data equals to DEFAULT_DATA
        defaultVendaShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the vendaList where data equals to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data not equals to DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.notEquals=" + DEFAULT_DATA);

        // Get all the vendaList where data not equals to UPDATED_DATA
        defaultVendaShouldBeFound("data.notEquals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data in DEFAULT_DATA or UPDATED_DATA
        defaultVendaShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the vendaList where data equals to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is not null
        defaultVendaShouldBeFound("data.specified=true");

        // Get all the vendaList where data is null
        defaultVendaShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is greater than or equal to DEFAULT_DATA
        defaultVendaShouldBeFound("data.greaterThanOrEqual=" + DEFAULT_DATA);

        // Get all the vendaList where data is greater than or equal to UPDATED_DATA
        defaultVendaShouldNotBeFound("data.greaterThanOrEqual=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is less than or equal to DEFAULT_DATA
        defaultVendaShouldBeFound("data.lessThanOrEqual=" + DEFAULT_DATA);

        // Get all the vendaList where data is less than or equal to SMALLER_DATA
        defaultVendaShouldNotBeFound("data.lessThanOrEqual=" + SMALLER_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is less than DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the vendaList where data is less than UPDATED_DATA
        defaultVendaShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where data is greater than DEFAULT_DATA
        defaultVendaShouldNotBeFound("data.greaterThan=" + DEFAULT_DATA);

        // Get all the vendaList where data is greater than SMALLER_DATA
        defaultVendaShouldBeFound("data.greaterThan=" + SMALLER_DATA);
    }


    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega equals to DEFAULT_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.equals=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega equals to UPDATED_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.equals=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega not equals to DEFAULT_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.notEquals=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega not equals to UPDATED_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.notEquals=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega in DEFAULT_DATA_ENTREGA or UPDATED_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.in=" + DEFAULT_DATA_ENTREGA + "," + UPDATED_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega equals to UPDATED_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.in=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega is not null
        defaultVendaShouldBeFound("dataEntrega.specified=true");

        // Get all the vendaList where dataEntrega is null
        defaultVendaShouldNotBeFound("dataEntrega.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega is greater than or equal to DEFAULT_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.greaterThanOrEqual=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega is greater than or equal to UPDATED_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.greaterThanOrEqual=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega is less than or equal to DEFAULT_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.lessThanOrEqual=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega is less than or equal to SMALLER_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.lessThanOrEqual=" + SMALLER_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega is less than DEFAULT_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.lessThan=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega is less than UPDATED_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.lessThan=" + UPDATED_DATA_ENTREGA);
    }

    @Test
    @Transactional
    public void getAllVendasByDataEntregaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where dataEntrega is greater than DEFAULT_DATA_ENTREGA
        defaultVendaShouldNotBeFound("dataEntrega.greaterThan=" + DEFAULT_DATA_ENTREGA);

        // Get all the vendaList where dataEntrega is greater than SMALLER_DATA_ENTREGA
        defaultVendaShouldBeFound("dataEntrega.greaterThan=" + SMALLER_DATA_ENTREGA);
    }


    @Test
    @Transactional
    public void getAllVendasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor equals to DEFAULT_VALOR
        defaultVendaShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the vendaList where valor equals to UPDATED_VALOR
        defaultVendaShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor not equals to DEFAULT_VALOR
        defaultVendaShouldNotBeFound("valor.notEquals=" + DEFAULT_VALOR);

        // Get all the vendaList where valor not equals to UPDATED_VALOR
        defaultVendaShouldBeFound("valor.notEquals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultVendaShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the vendaList where valor equals to UPDATED_VALOR
        defaultVendaShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor is not null
        defaultVendaShouldBeFound("valor.specified=true");

        // Get all the vendaList where valor is null
        defaultVendaShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor is greater than or equal to DEFAULT_VALOR
        defaultVendaShouldBeFound("valor.greaterThanOrEqual=" + DEFAULT_VALOR);

        // Get all the vendaList where valor is greater than or equal to UPDATED_VALOR
        defaultVendaShouldNotBeFound("valor.greaterThanOrEqual=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor is less than or equal to DEFAULT_VALOR
        defaultVendaShouldBeFound("valor.lessThanOrEqual=" + DEFAULT_VALOR);

        // Get all the vendaList where valor is less than or equal to SMALLER_VALOR
        defaultVendaShouldNotBeFound("valor.lessThanOrEqual=" + SMALLER_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsLessThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor is less than DEFAULT_VALOR
        defaultVendaShouldNotBeFound("valor.lessThan=" + DEFAULT_VALOR);

        // Get all the vendaList where valor is less than UPDATED_VALOR
        defaultVendaShouldBeFound("valor.lessThan=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllVendasByValorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);

        // Get all the vendaList where valor is greater than DEFAULT_VALOR
        defaultVendaShouldNotBeFound("valor.greaterThan=" + DEFAULT_VALOR);

        // Get all the vendaList where valor is greater than SMALLER_VALOR
        defaultVendaShouldBeFound("valor.greaterThan=" + SMALLER_VALOR);
    }


    @Test
    @Transactional
    public void getAllVendasByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Produto produto = ProdutoResourceIT.createEntity(em);
        em.persist(produto);
        em.flush();
        venda.addProduto(produto);
        vendaRepository.saveAndFlush(venda);
        Long produtoId = produto.getId();

        // Get all the vendaList where produto equals to produtoId
        defaultVendaShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the vendaList where produto equals to produtoId + 1
        defaultVendaShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllVendasByFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        vendaRepository.saveAndFlush(venda);
        Funcionario funcionario = FuncionarioResourceIT.createEntity(em);
        em.persist(funcionario);
        em.flush();
        venda.setFuncionario(funcionario);
        vendaRepository.saveAndFlush(venda);
        Long funcionarioId = funcionario.getId();

        // Get all the vendaList where funcionario equals to funcionarioId
        defaultVendaShouldBeFound("funcionarioId.equals=" + funcionarioId);

        // Get all the vendaList where funcionario equals to funcionarioId + 1
        defaultVendaShouldNotBeFound("funcionarioId.equals=" + (funcionarioId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultVendaShouldBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(venda.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].dataEntrega").value(hasItem(DEFAULT_DATA_ENTREGA.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));

        // Check, that the count call also returns 1
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultVendaShouldNotBeFound(String filter) throws Exception {
        restVendaMockMvc.perform(get("/api/vendas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restVendaMockMvc.perform(get("/api/vendas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
