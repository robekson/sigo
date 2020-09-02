package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CompraMapperTest {

    private CompraMapper compraMapper;

    @BeforeEach
    public void setUp() {
        compraMapper = new CompraMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(compraMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(compraMapper.fromId(null)).isNull();
    }
}
