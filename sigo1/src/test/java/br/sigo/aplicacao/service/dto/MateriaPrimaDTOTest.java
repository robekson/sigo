package br.sigo.aplicacao.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.sigo.aplicacao.web.rest.TestUtil;

public class MateriaPrimaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MateriaPrimaDTO.class);
        MateriaPrimaDTO materiaPrimaDTO1 = new MateriaPrimaDTO();
        materiaPrimaDTO1.setId(1L);
        MateriaPrimaDTO materiaPrimaDTO2 = new MateriaPrimaDTO();
        assertThat(materiaPrimaDTO1).isNotEqualTo(materiaPrimaDTO2);
        materiaPrimaDTO2.setId(materiaPrimaDTO1.getId());
        assertThat(materiaPrimaDTO1).isEqualTo(materiaPrimaDTO2);
        materiaPrimaDTO2.setId(2L);
        assertThat(materiaPrimaDTO1).isNotEqualTo(materiaPrimaDTO2);
        materiaPrimaDTO1.setId(null);
        assertThat(materiaPrimaDTO1).isNotEqualTo(materiaPrimaDTO2);
    }
}
