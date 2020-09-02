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
 * A Compra.
 */
@Entity
@Table(name = "compra")
public class Compra implements Serializable {

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

    @ManyToMany
    @JoinTable(name = "compra_produto",
               joinColumns = @JoinColumn(name = "compra_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "produto_id", referencedColumnName = "id"))
    private Set<Produto> produtos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "compras", allowSetters = true)
    private Cliente cliente;

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

    public Compra quantidade(Integer quantidade) {
        this.quantidade = quantidade;
        return this;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public Compra data(LocalDate data) {
        this.data = data;
        return this;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTamanho() {
        return tamanho;
    }

    public Compra tamanho(String tamanho) {
        this.tamanho = tamanho;
        return this;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Compra valor(BigDecimal valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Set<Produto> getProdutos() {
        return produtos;
    }

    public Compra produtos(Set<Produto> produtos) {
        this.produtos = produtos;
        return this;
    }

    public Compra addProduto(Produto produto) {
        this.produtos.add(produto);
        produto.getCompras().add(this);
        return this;
    }

    public Compra removeProduto(Produto produto) {
        this.produtos.remove(produto);
        produto.getCompras().remove(this);
        return this;
    }

    public void setProdutos(Set<Produto> produtos) {
        this.produtos = produtos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Compra cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compra)) {
            return false;
        }
        return id != null && id.equals(((Compra) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Compra{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", data='" + getData() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            ", valor=" + getValor() +
            "}";
    }
}
