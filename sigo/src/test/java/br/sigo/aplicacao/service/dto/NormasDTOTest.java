package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class NormasDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormasDTO.class);
        NormasDTO normasDTO1 = new NormasDTO();
        normasDTO1.setId(1L);
        NormasDTO normasDTO2 = new NormasDTO();
        assertThat(normasDTO1).isNotEqualTo(normasDTO2);
        normasDTO2.setId(normasDTO1.getId());
        assertThat(normasDTO1).isEqualTo(normasDTO2);
        normasDTO2.setId(2L);
        assertThat(normasDTO1).isNotEqualTo(normasDTO2);
        normasDTO1.setId(null);
        assertThat(normasDTO1).isNotEqualTo(normasDTO2);
    }
}
