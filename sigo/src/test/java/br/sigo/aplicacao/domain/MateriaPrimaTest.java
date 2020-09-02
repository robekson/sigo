package br.sigo.aplicacao.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class MateriaPrimaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriaPrima.class);
        MateriaPrima materiaPrima1 = new MateriaPrima();
        materiaPrima1.setId(1L);
        MateriaPrima materiaPrima2 = new MateriaPrima();
        materiaPrima2.setId(materiaPrima1.getId());
        assertThat(materiaPrima1).isEqualTo(materiaPrima2);
        materiaPrima2.setId(2L);
        assertThat(materiaPrima1).isNotEqualTo(materiaPrima2);
        materiaPrima1.setId(null);
        assertThat(materiaPrima1).isNotEqualTo(materiaPrima2);
    }
}
