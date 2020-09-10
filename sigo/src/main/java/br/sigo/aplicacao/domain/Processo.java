package br.sigo.aplicacao.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import br.sigo.aplicacao.domain.enumeration.StatusProcesso;

import br.sigo.aplicacao.domain.enumeration.Etapa;

/**
 * A Processo.
 */
@Entity
@Table(name = "processo")
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "id_materia_prima")
    private Integer idMateriaPrima;

    @Column(name = "id_produto")
    private Integer idProduto;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcesso status;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa")
    private Etapa etapa;

    @OneToOne
    @JoinColumn(unique = true)
    private Processo processo;

    @OneToMany(mappedBy = "processo")
    private Set<TipoProcesso> tipoProcessos = new HashSet<>();

    @OneToOne(mappedBy = "processo")
    @JsonIgnore
    private Processo processoFilho;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public Processo idMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
        return this;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public Processo idProduto(Integer idProduto) {
        this.idProduto = idProduto;
        return this;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public Processo nome(String nome) {
        this.nome = nome;
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Processo descricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusProcesso getStatus() {
        return status;
    }

    public Processo status(StatusProcesso status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public Processo etapa(Etapa etapa) {
        this.etapa = etapa;
        return this;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Processo getProcesso() {
        return processo;
    }

    public Processo processo(Processo processo) {
        this.processo = processo;
        return this;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Set<TipoProcesso> getTipoProcessos() {
        return tipoProcessos;
    }

    public Processo tipoProcessos(Set<TipoProcesso> tipoProcessos) {
        this.tipoProcessos = tipoProcessos;
        return this;
    }

    public Processo addTipoProcesso(TipoProcesso tipoProcesso) {
        this.tipoProcessos.add(tipoProcesso);
        tipoProcesso.setProcesso(this);
        return this;
    }

    public Processo removeTipoProcesso(TipoProcesso tipoProcesso) {
        this.tipoProcessos.remove(tipoProcesso);
        tipoProcesso.setProcesso(null);
        return this;
    }

    public void setTipoProcessos(Set<TipoProcesso> tipoProcessos) {
        this.tipoProcessos = tipoProcessos;
    }

    public Processo getProcessoFilho() {
        return processoFilho;
    }

    public Processo processoFilho(Processo processo) {
        this.processoFilho = processo;
        return this;
    }

    public void setProcessoFilho(Processo processo) {
        this.processoFilho = processo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Processo)) {
            return false;
        }
        return id != null && id.equals(((Processo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Processo{" +
            "id=" + getId() +
            ", idMateriaPrima=" + getIdMateriaPrima() +
            ", idProduto=" + getIdProduto() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", status='" + getStatus() + "'" +
            ", etapa='" + getEtapa() + "'" +
            "}";
    }
}
