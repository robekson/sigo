package br.sigo.aplicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ProcessoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Processo.class);
        Processo processo1 = new Processo();
        processo1.setId(1L);
        Processo processo2 = new Processo();
        processo2.setId(processo1.getId());
        assertThat(processo1).isEqualTo(processo2);
        processo2.setId(2L);
        assertThat(processo1).isNotEqualTo(processo2);
        processo1.setId(null);
        assertThat(processo1).isNotEqualTo(processo2);
    }
}
