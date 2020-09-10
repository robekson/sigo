package br.sigo.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A MateriaPrima.
 */
@Entity
@Table(name = "materia_prima")
public class MateriaPrima implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "composicao")
    private String composicao;

    @Column(name = "fio")
    private String fio;

    @OneToOne(mappedBy = "materiaPrima")
    @JsonIgnore
    private Produto produto;

    @ManyToOne
    @JsonIgnoreProperties(value = "materiaPrimas", allowSetters = true)
    private Fornece fornece;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public MateriaPrima tipo(String tipo) {
        this.tipo = tipo;
        return this;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComposicao() {
        return composicao;
    }

    public MateriaPrima composicao(String composicao) {
        this.composicao = composicao;
        return this;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getFio() {
        return fio;
    }

    public MateriaPrima fio(String fio) {
        this.fio = fio;
        return this;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Produto getProduto() {
        return produto;
    }

    public MateriaPrima produto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Fornece getFornece() {
        return fornece;
    }

    public MateriaPrima fornece(Fornece fornece) {
        this.fornece = fornece;
        return this;
    }

    public void setFornece(Fornece fornece) {
        this.fornece = fornece;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MateriaPrima)) {
            return false;
        }
        return id != null && id.equals(((MateriaPrima) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MateriaPrima{" +
            "id=" + getId() +
            ", tipo='" + getTipo() + "'" +
            ", composicao='" + getComposicao() + "'" +
            ", fio='" + getFio() + "'" +
            "}";
    }
}
