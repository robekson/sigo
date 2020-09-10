package br.sigo.aplicacao.service.dto;

import java.io.Serializable;
import br.sigo.aplicacao.domain.enumeration.StatusProcesso;
import br.sigo.aplicacao.domain.enumeration.Etapa;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.Processo} entity.
 */
public class ProcessoDTO implements Serializable {
    
    private Long id;

    private Integer idMateriaPrima;

    private Integer idProduto;

    private String nome;

    private String descricao;

    private StatusProcesso status;

    private Etapa etapa;


    private Long processoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public StatusProcesso getStatus() {
        return status;
    }

    public void setStatus(StatusProcesso status) {
        this.status = status;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Long getProcessoId() {
        return processoId;
    }

    public void setProcessoId(Long processoId) {
        this.processoId = processoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessoDTO)) {
            return false;
        }

        return id != null && id.equals(((ProcessoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessoDTO{" +
            "id=" + getId() +
            ", idMateriaPrima=" + getIdMateriaPrima() +
            ", idProduto=" + getIdProduto() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", status='" + getStatus() + "'" +
            ", etapa='" + getEtapa() + "'" +
            ", processoId=" + getProcessoId() +
            "}";
    }
}
