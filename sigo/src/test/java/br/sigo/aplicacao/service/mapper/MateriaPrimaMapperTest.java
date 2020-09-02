package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MateriaPrimaMapperTest {

    private MateriaPrimaMapper materiaPrimaMapper;

    @BeforeEach
    public void setUp() {
        materiaPrimaMapper = new MateriaPrimaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(materiaPrimaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(materiaPrimaMapper.fromId(null)).isNull();
    }
}
