package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ProcessoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessoDTO.class);
        ProcessoDTO processoDTO1 = new ProcessoDTO();
        processoDTO1.setId(1L);
        ProcessoDTO processoDTO2 = new ProcessoDTO();
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
        processoDTO2.setId(processoDTO1.getId());
        assertThat(processoDTO1).isEqualTo(processoDTO2);
        processoDTO2.setId(2L);
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
        processoDTO1.setId(null);
        assertThat(processoDTO1).isNotEqualTo(processoDTO2);
    }
}
