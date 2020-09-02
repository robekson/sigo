package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ConsultorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultorDTO.class);
        ConsultorDTO consultorDTO1 = new ConsultorDTO();
        consultorDTO1.setId(1L);
        ConsultorDTO consultorDTO2 = new ConsultorDTO();
        assertThat(consultorDTO1).isNotEqualTo(consultorDTO2);
        consultorDTO2.setId(consultorDTO1.getId());
        assertThat(consultorDTO1).isEqualTo(consultorDTO2);
        consultorDTO2.setId(2L);
        assertThat(consultorDTO1).isNotEqualTo(consultorDTO2);
        consultorDTO1.setId(null);
        assertThat(consultorDTO1).isNotEqualTo(consultorDTO2);
    }
}
