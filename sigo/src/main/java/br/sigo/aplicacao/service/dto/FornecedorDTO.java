package br.sigo.aplicacao.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.Fornecedor} entity.
 */
public class FornecedorDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String nome;

    private String cnpj;

    private String email;

    private String telefone;

    private String razaoSocial;

    
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

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FornecedorDTO)) {
            return false;
        }

        return id != null && id.equals(((FornecedorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FornecedorDTO{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", razaoSocial='" + getRazaoSocial() + "'" +
            "}";
    }
}
