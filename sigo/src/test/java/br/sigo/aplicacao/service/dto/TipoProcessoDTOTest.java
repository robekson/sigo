package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class TipoProcessoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProcessoDTO.class);
        TipoProcessoDTO tipoProcessoDTO1 = new TipoProcessoDTO();
        tipoProcessoDTO1.setId(1L);
        TipoProcessoDTO tipoProcessoDTO2 = new TipoProcessoDTO();
        assertThat(tipoProcessoDTO1).isNotEqualTo(tipoProcessoDTO2);
        tipoProcessoDTO2.setId(tipoProcessoDTO1.getId());
        assertThat(tipoProcessoDTO1).isEqualTo(tipoProcessoDTO2);
        tipoProcessoDTO2.setId(2L);
        assertThat(tipoProcessoDTO1).isNotEqualTo(tipoProcessoDTO2);
        tipoProcessoDTO1.setId(null);
        assertThat(tipoProcessoDTO1).isNotEqualTo(tipoProcessoDTO2);
    }
}
