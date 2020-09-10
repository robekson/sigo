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

    @OneToMany(mappedBy = "fornece")
    private Set<MateriaPrima> materiaPrimas = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "forneces", allowSetters = true)
    private Fornecedor fornecedor;

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

    public Set<MateriaPrima> getMateriaPrimas() {
        return materiaPrimas;
    }

    public Fornece materiaPrimas(Set<MateriaPrima> materiaPrimas) {
        this.materiaPrimas = materiaPrimas;
        return this;
    }

    public Fornece addMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrimas.add(materiaPrima);
        materiaPrima.setFornece(this);
        return this;
    }

    public Fornece removeMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrimas.remove(materiaPrima);
        materiaPrima.setFornece(null);
        return this;
    }

    public void setMateriaPrimas(Set<MateriaPrima> materiaPrimas) {
        this.materiaPrimas = materiaPrimas;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public Fornece fornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
