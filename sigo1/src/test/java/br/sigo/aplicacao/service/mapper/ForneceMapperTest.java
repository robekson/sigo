package br.sigo.aplicacao.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ForneceMapperTest {

    private ForneceMapper forneceMapper;

    @BeforeEach
    public void setUp() {
        forneceMapper = new ForneceMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(forneceMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(forneceMapper.fromId(null)).isNull();
    }
}
