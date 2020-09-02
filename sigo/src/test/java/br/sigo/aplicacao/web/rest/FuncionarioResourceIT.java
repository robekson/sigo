package br.sigo.aplicacao.web.rest;

import br.sigo.aplicacao.SigoApp;
import br.sigo.aplicacao.domain.Funcionario;
import br.sigo.aplicacao.repository.FuncionarioRepository;
import br.sigo.aplicacao.service.FuncionarioService;
import br.sigo.aplicacao.service.dto.FuncionarioDTO;
import br.sigo.aplicacao.service.mapper.FuncionarioMapper;

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
 * Integration tests for the {@link FuncionarioResource} REST controller.
 */
@SpringBootTest(classes = SigoApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FuncionarioResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_ADMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_ADMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_RG = "AAAAAAAAAA";
    private static final String UPDATED_RG = "BBBBBBBBBB";

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN_ACESSO = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_ACESSO = "BBBBBBBBBB";

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncionarioMapper funcionarioMapper;

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuncionarioMockMvc;

    private Funcionario funcionario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .dataAdmissao(DEFAULT_DATA_ADMISSAO)
            .cpf(DEFAULT_CPF)
            .rg(DEFAULT_RG)
            .sexo(DEFAULT_SEXO)
            .loginAcesso(DEFAULT_LOGIN_ACESSO);
        return funcionario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Funcionario createUpdatedEntity(EntityManager em) {
        Funcionario funcionario = new Funcionario()
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cpf(UPDATED_CPF)
            .rg(UPDATED_RG)
            .sexo(UPDATED_SEXO)
            .loginAcesso(UPDATED_LOGIN_ACESSO);
        return funcionario;
    }

    @BeforeEach
    public void initTest() {
        funcionario = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuncionario() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();
        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate + 1);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testFuncionario.getDataAdmissao()).isEqualTo(DEFAULT_DATA_ADMISSAO);
        assertThat(testFuncionario.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testFuncionario.getRg()).isEqualTo(DEFAULT_RG);
        assertThat(testFuncionario.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testFuncionario.getLoginAcesso()).isEqualTo(DEFAULT_LOGIN_ACESSO);
    }

    @Test
    @Transactional
    public void createFuncionarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = funcionarioRepository.findAll().size();

        // Create the Funcionario with an existing ID
        funcionario.setId(1L);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setNome(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);


        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataAdmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = funcionarioRepository.findAll().size();
        // set the field null
        funcionario.setDataAdmissao(null);

        // Create the Funcionario, which fails.
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);


        restFuncionarioMockMvc.perform(post("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFuncionarios() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get all the funcionarioList
        restFuncionarioMockMvc.perform(get("/api/funcionarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(funcionario.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].dataAdmissao").value(hasItem(DEFAULT_DATA_ADMISSAO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].rg").value(hasItem(DEFAULT_RG)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO)))
            .andExpect(jsonPath("$.[*].loginAcesso").value(hasItem(DEFAULT_LOGIN_ACESSO)));
    }
    
    @Test
    @Transactional
    public void getFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", funcionario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(funcionario.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.dataAdmissao").value(DEFAULT_DATA_ADMISSAO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.rg").value(DEFAULT_RG))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO))
            .andExpect(jsonPath("$.loginAcesso").value(DEFAULT_LOGIN_ACESSO));
    }
    @Test
    @Transactional
    public void getNonExistingFuncionario() throws Exception {
        // Get the funcionario
        restFuncionarioMockMvc.perform(get("/api/funcionarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Update the funcionario
        Funcionario updatedFuncionario = funcionarioRepository.findById(funcionario.getId()).get();
        // Disconnect from session so that the updates on updatedFuncionario are not directly saved in db
        em.detach(updatedFuncionario);
        updatedFuncionario
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .dataAdmissao(UPDATED_DATA_ADMISSAO)
            .cpf(UPDATED_CPF)
            .rg(UPDATED_RG)
            .sexo(UPDATED_SEXO)
            .loginAcesso(UPDATED_LOGIN_ACESSO);
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(updatedFuncionario);

        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isOk());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
        Funcionario testFuncionario = funcionarioList.get(funcionarioList.size() - 1);
        assertThat(testFuncionario.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFuncionario.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testFuncionario.getDataAdmissao()).isEqualTo(UPDATED_DATA_ADMISSAO);
        assertThat(testFuncionario.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testFuncionario.getRg()).isEqualTo(UPDATED_RG);
        assertThat(testFuncionario.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testFuncionario.getLoginAcesso()).isEqualTo(UPDATED_LOGIN_ACESSO);
    }

    @Test
    @Transactional
    public void updateNonExistingFuncionario() throws Exception {
        int databaseSizeBeforeUpdate = funcionarioRepository.findAll().size();

        // Create the Funcionario
        FuncionarioDTO funcionarioDTO = funcionarioMapper.toDto(funcionario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuncionarioMockMvc.perform(put("/api/funcionarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(funcionarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Funcionario in the database
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuncionario() throws Exception {
        // Initialize the database
        funcionarioRepository.saveAndFlush(funcionario);

        int databaseSizeBeforeDelete = funcionarioRepository.findAll().size();

        // Delete the funcionario
        restFuncionarioMockMvc.perform(delete("/api/funcionarios/{id}", funcionario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Funcionario> funcionarioList = funcionarioRepository.findAll();
        assertThat(funcionarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
