package br.sigo.aplicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class NormasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Normas.class);
        Normas normas1 = new Normas();
        normas1.setId(1L);
        Normas normas2 = new Normas();
        normas2.setId(normas1.getId());
        assertThat(normas1).isEqualTo(normas2);
        normas2.setId(2L);
        assertThat(normas1).isNotEqualTo(normas2);
        normas1.setId(null);
        assertThat(normas1).isNotEqualTo(normas2);
    }
}
