package br.sigo.aplicacao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Consultor.
 */
@Entity
@Table(name = "consultor")
public class Consultor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cnpj")
    private String cnpj;

    @NotNull
    @Column(name = "data_contratacao", nullable = false)
    private LocalDate dataContratacao;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public Consultor nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Consultor cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public Consultor dataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
        return this;
    }

    public void setDataContratacao(LocalDate dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getEmail() {
        return email;
    }

    public Consultor email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public Consultor telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Consultor)) {
            return false;
        }
        return id != null && id.equals(((Consultor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Consultor{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", dataContratacao='" + getDataContratacao() + "'" +
            ", email='" + getEmail() + "'" +
            ", telefone='" + getTelefone() + "'" +
            "}";
    }
}
