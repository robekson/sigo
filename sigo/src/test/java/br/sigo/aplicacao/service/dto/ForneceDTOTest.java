package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ForneceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ForneceDTO.class);
        ForneceDTO forneceDTO1 = new ForneceDTO();
        forneceDTO1.setId(1L);
        ForneceDTO forneceDTO2 = new ForneceDTO();
        assertThat(forneceDTO1).isNotEqualTo(forneceDTO2);
        forneceDTO2.setId(forneceDTO1.getId());
        assertThat(forneceDTO1).isEqualTo(forneceDTO2);
        forneceDTO2.setId(2L);
        assertThat(forneceDTO1).isNotEqualTo(forneceDTO2);
        forneceDTO1.setId(null);
        assertThat(forneceDTO1).isNotEqualTo(forneceDTO2);
    }
}
