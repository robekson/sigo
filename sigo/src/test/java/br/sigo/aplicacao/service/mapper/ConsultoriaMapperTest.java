package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultoriaMapperTest {

    private ConsultoriaMapper consultoriaMapper;

    @BeforeEach
    public void setUp() {
        consultoriaMapper = new ConsultoriaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(consultoriaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consultoriaMapper.fromId(null)).isNull();
    }
}
