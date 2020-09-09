package br.sigo.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.Consultoria} entity.
 */
public class ConsultoriaDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String cnpj;

    @NotNull
    private LocalDate dataContratacao;

    private String email;

    private String telefone;

    private String tipoServicoPrestado;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTipoServicoPrestado() {
        return tipoServicoPrestado;
    }

    public void setTipoServicoPrestado(String tipoServicoPrestado) {
        this.tipoServicoPrestado = tipoServicoPrestado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConsultoriaDTO)) {
            return false;
        }

        return id != null && id.equals(((ConsultoriaDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConsultoriaDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", tipoServicoPrestado='" + getTipoServicoPrestado() + "'" +
            "}";
    }
}
