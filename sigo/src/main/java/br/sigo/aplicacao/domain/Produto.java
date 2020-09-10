package br.sigo.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Produto.
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "referencia", nullable = false)
    private String referencia;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cores")
    private String cores;

    @Column(name = "insumo")
    private String insumo;

    @OneToOne
    @JoinColumn(unique = true)
    private MateriaPrima materiaPrima;

    @ManyToOne
    @JsonIgnoreProperties(value = "produtos", allowSetters = true)
    private Venda venda;

    @ManyToMany(mappedBy = "produtos")
    @JsonIgnore
    private Set<Compra> compras = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public Produto referencia(String referencia) {
        this.referencia = referencia;
        return this;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNome() {
        return nome;
    }

    public Produto nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCores() {
        return cores;
    }

    public Produto cores(String cores) {
        this.cores = cores;
        return this;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public String getInsumo() {
        return insumo;
    }

    public Produto insumo(String insumo) {
        this.insumo = insumo;
        return this;
    }

    public void setInsumo(String insumo) {
        this.insumo = insumo;
    }

    public MateriaPrima getMateriaPrima() {
        return materiaPrima;
    }

    public Produto materiaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
        return this;
    }

    public void setMateriaPrima(MateriaPrima materiaPrima) {
        this.materiaPrima = materiaPrima;
    }

    public Venda getVenda() {
        return venda;
    }

    public Produto venda(Venda venda) {
        this.venda = venda;
        return this;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public Set<Compra> getCompras() {
        return compras;
    }

    public Produto compras(Set<Compra> compras) {
        this.compras = compras;
        return this;
    }

    public Produto addCompra(Compra compra) {
        this.compras.add(compra);
        compra.getProdutos().add(this);
        return this;
    }

    public Produto removeCompra(Compra compra) {
        this.compras.remove(compra);
        compra.getProdutos().remove(this);
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
        if (!(o instanceof Produto)) {
            return false;
        }
        return id != null && id.equals(((Produto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Produto{" +
            "id=" + getId() +
            ", referencia='" + getReferencia() + "'" +
            ", nome='" + getNome() + "'" +
            ", cores='" + getCores() + "'" +
            ", insumo='" + getInsumo() + "'" +
            "}";
    }
}
