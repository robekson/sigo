package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NormasMapperTest {

    private NormasMapper normasMapper;

    @BeforeEach
    public void setUp() {
        normasMapper = new NormasMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(normasMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(normasMapper.fromId(null)).isNull();
    }
}
