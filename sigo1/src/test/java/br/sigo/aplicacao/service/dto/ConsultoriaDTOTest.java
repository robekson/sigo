package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ConsultoriaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultoriaDTO.class);
        ConsultoriaDTO consultoriaDTO1 = new ConsultoriaDTO();
        consultoriaDTO1.setId(1L);
        ConsultoriaDTO consultoriaDTO2 = new ConsultoriaDTO();
        assertThat(consultoriaDTO1).isNotEqualTo(consultoriaDTO2);
        consultoriaDTO2.setId(consultoriaDTO1.getId());
        assertThat(consultoriaDTO1).isEqualTo(consultoriaDTO2);
        consultoriaDTO2.setId(2L);
        assertThat(consultoriaDTO1).isNotEqualTo(consultoriaDTO2);
        consultoriaDTO1.setId(null);
        assertThat(consultoriaDTO1).isNotEqualTo(consultoriaDTO2);
    }
}
