package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FornecedorMapperTest {

    private FornecedorMapper fornecedorMapper;

    @BeforeEach
    public void setUp() {
        fornecedorMapper = new FornecedorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fornecedorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fornecedorMapper.fromId(null)).isNull();
    }
}
