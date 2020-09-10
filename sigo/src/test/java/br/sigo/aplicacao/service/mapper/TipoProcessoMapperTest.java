package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TipoProcessoMapperTest {

    private TipoProcessoMapper tipoProcessoMapper;

    @BeforeEach
    public void setUp() {
        tipoProcessoMapper = new TipoProcessoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tipoProcessoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tipoProcessoMapper.fromId(null)).isNull();
    }
}
