package br.sigo.aplicacao.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the {@link br.sigo.aplicacao.domain.Compra} entity.
 */
public class CompraDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Integer quantidade;

    @NotNull
    private LocalDate data;

    private String tamanho;

    private BigDecimal valor;

    private Set<ProdutoDTO> produtos = new HashSet<>();

    private Long clienteId;
    
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

    public Set<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(Set<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompraDTO)) {
            return false;
        }

        return id != null && id.equals(((CompraDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompraDTO{" +
            "id=" + getId() +
            ", quantidade=" + getQuantidade() +
            ", data='" + getData() + "'" +
            ", tamanho='" + getTamanho() + "'" +
            ", valor=" + getValor() +
            ", produtos='" + getProdutos() + "'" +
            ", clienteId=" + getClienteId() +
            "}";
    }
}
