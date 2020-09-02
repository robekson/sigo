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
 * A Venda.
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

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

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

    @Column(name = "valor", precision = 21, scale = 2)
    private BigDecimal valor;

    @OneToMany(mappedBy = "venda")
    private Set<Produto> produtos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "vendas", allowSetters = true)
    private Funcionario funcionario;

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

    public Venda quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public Venda data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalDate getDataEntrega() {
        return dataEntrega;
    }

    public Venda dataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
        return this;
    }

    public void setDataEntrega(LocalDate dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Venda valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Venda produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Venda addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.setVenda(this);
        return this;
    }

    public Venda removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.setVenda(null);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public Venda funcionario(Funcionario funcionario) {
        this.funcionario = funcionario;
        return this;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Venda)) {
            return false;
        }
        return id != null && id.equals(((Venda) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Venda{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", data='" + getData() + "'" +
            ", dataEntrega='" + getDataEntrega() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
