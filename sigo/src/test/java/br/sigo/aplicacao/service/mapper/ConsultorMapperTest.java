package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConsultorMapperTest {

    private ConsultorMapper consultorMapper;

    @BeforeEach
    public void setUp() {
        consultorMapper = new ConsultorMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(consultorMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(consultorMapper.fromId(null)).isNull();
    }
}
