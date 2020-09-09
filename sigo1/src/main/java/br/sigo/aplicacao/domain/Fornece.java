package br.sigo.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fornece.
 */
@Entity
@Table(name = "fornece")
public class Fornece implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @NotNull
    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(name = "tamanho")
    private String tamanho;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @OneToMany(mappedBy = "cliente")
    private Set<Produto> compras = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "produtos", allowSetters = true)
    private Fornecedor fornece;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public Fornece quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public Fornece data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTamanho() {
        return tamanho;
    }

    public Fornece tamanho(String tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Fornece valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<Produto> getCompras() {
        return compras;
    }

    public Fornece compras(Set<Produto> produtos) {
        this.compras = produtos;
        return this;
    }

    public Fornece addCompra(Produto produto) {
        this.compras.add(produto);
        produto.setCliente(this);
        return this;
    }

    public Fornece removeCompra(Produto produto) {
        this.compras.remove(produto);
        produto.setCliente(null);
        return this;
    }

    public void setCompras(Set<Produto> produtos) {
        this.compras = produtos;
    }

    public Fornecedor getFornece() {
        return fornece;
    }

    public Fornece fornece(Fornecedor fornecedor) {
        this.fornece = fornecedor;
        return this;
    }

    public void setFornece(Fornecedor fornecedor) {
        this.fornece = fornecedor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fornece)) {
            return false;
        }
        return id != null && id.equals(((Fornece) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fornece{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", data='" + getData() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
