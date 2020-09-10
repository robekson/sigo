package br.sigo.aplicacao.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.MateriaPrima} entity.
 */
public class MateriaPrimaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String tipo;

    private String composicao;

    private String fio;


    private Long forneceId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Long getForneceId() {
        return forneceId;
    }

    public void setForneceId(Long forneceId) {
        this.forneceId = forneceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriaPrimaDTO)) {
            return false;
        }

        return id != null && id.equals(((MateriaPrimaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaPrimaDTO{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", composicao='" + getComposicao() + "'" +
            ", fio='" + getFio() + "'" +
            ", forneceId=" + getForneceId() +
            "}";
    }
}
