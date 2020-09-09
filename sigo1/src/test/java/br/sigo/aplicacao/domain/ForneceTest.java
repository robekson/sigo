package br.sigo.aplicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class ForneceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fornece.class);
        Fornece fornece1 = new Fornece();
        fornece1.setId(1L);
        Fornece fornece2 = new Fornece();
        fornece2.setId(fornece1.getId());
        assertThat(fornece1).isEqualTo(fornece2);
        fornece2.setId(2L);
        assertThat(fornece1).isNotEqualTo(fornece2);
        fornece1.setId(null);
        assertThat(fornece1).isNotEqualTo(fornece2);
    }
}
