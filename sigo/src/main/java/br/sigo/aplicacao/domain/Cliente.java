package br.sigo.aplicacao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

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

    @Column(name = "telefone")
    private String telefone;

    @Column(name = "contato")
    private String contato;

    @Column(name = "endereco")
    private String endereco;

    @Column(name = "cep")
    private String cep;

    @OneToMany(mappedBy = "cliente")
    private Set<Compra> compras = new HashSet<>();

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

    public Cliente nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Cliente cnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getTelefone() {
        return telefone;
    }

    public Cliente telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getContato() {
        return contato;
    }

    public Cliente contato(String contato) {
        this.contato = contato;
        return this;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEndereco() {
        return endereco;
    }

    public Cliente endereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public Cliente cep(String cep) {
        this.cep = cep;
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public Cliente compras(Set<Compra> compras) {
        this.compras = compras;
        return this;
    }

    public Cliente addCompra(Compra compra) {
        this.compras.add(compra);
        compra.setCliente(this);
        return this;
    }

    public Cliente removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.setCliente(null);
        return this;
    }

    public void setCompras(Set<Compra> compras) {
        this.compras = compras;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cliente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", contato='" + getContato() + "'" +
            ", endereco='" + getEndereco() + "'" +
            ", cep='" + getCep() + "'" +
            "}";
    }
}
