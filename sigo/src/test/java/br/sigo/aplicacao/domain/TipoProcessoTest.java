package br.sigo.aplicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class TipoProcessoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProcesso.class);
        TipoProcesso tipoProcesso1 = new TipoProcesso();
        tipoProcesso1.setId(1L);
        TipoProcesso tipoProcesso2 = new TipoProcesso();
        tipoProcesso2.setId(tipoProcesso1.getId());
        assertThat(tipoProcesso1).isEqualTo(tipoProcesso2);
        tipoProcesso2.setId(2L);
        assertThat(tipoProcesso1).isNotEqualTo(tipoProcesso2);
        tipoProcesso1.setId(null);
        assertThat(tipoProcesso1).isNotEqualTo(tipoProcesso2);
    }
}
