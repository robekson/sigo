package br.sigo.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.Fornece} entity.
 */
public class ForneceDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer quantidade;

    @NotNull
    private LocalDate data;

    private String tamanho;

    private BigDecimal valor;


    private Long fornecedorId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Long getFornecedorId() {
        return fornecedorId;
    }

    public void setFornecedorId(Long fornecedorId) {
        this.fornecedorId = fornecedorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ForneceDTO)) {
            return false;
        }

        return id != null && id.equals(((ForneceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ForneceDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", data='" + getData() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            ", valor=" + getValor() +
            ", fornecedorId=" + getFornecedorId() +
            "}";
    }
}
