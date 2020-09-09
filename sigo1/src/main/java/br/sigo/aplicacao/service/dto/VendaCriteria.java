package br.sigo.aplicacao.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.sigo.aplicacao.domain.Venda} entity. This class is used
 * in {@link br.sigo.aplicacao.web.rest.VendaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vendas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class VendaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter quantidade;

    private LocalDateFilter data;

    private LocalDateFilter dataEntrega;

    private BigDecimalFilter valor;

    private LongFilter produtoId;

    private LongFilter funcionarioId;

    public VendaCriteria() {
    }

    public VendaCriteria(VendaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.quantidade = other.quantidade == null ? null : other.quantidade.copy();
        this.data = other.data == null ? null : other.data.copy();
        this.dataEntrega = other.dataEntrega == null ? null : other.dataEntrega.copy();
        this.valor = other.valor == null ? null : other.valor.copy();
        this.produtoId = other.produtoId == null ? null : other.produtoId.copy();
        this.funcionarioId = other.funcionarioId == null ? null : other.funcionarioId.copy();
    }

    @Override
    public VendaCriteria copy() {
        return new VendaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(IntegerFilter quantidade) {
        this.quantidade = quantidade;
    }

    public LocalDateFilter getData() {
        return data;
    }

    public void setData(LocalDateFilter data) {
        this.data = data;
    }

    public LocalDateFilter getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(LocalDateFilter dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public BigDecimalFilter getValor() {
        return valor;
    }

    public void setValor(BigDecimalFilter valor) {
        this.valor = valor;
    }

    public LongFilter getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(LongFilter produtoId) {
        this.produtoId = produtoId;
    }

    public LongFilter getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(LongFilter funcionarioId) {
        this.funcionarioId = funcionarioId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final VendaCriteria that = (VendaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(quantidade, that.quantidade) &&
            Objects.equals(data, that.data) &&
            Objects.equals(dataEntrega, that.dataEntrega) &&
            Objects.equals(valor, that.valor) &&
            Objects.equals(produtoId, that.produtoId) &&
            Objects.equals(funcionarioId, that.funcionarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        quantidade,
        data,
        dataEntrega,
        valor,
        produtoId,
        funcionarioId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (quantidade != null ? "quantidade=" + quantidade + ", " : "") +
                (data != null ? "data=" + data + ", " : "") +
                (dataEntrega != null ? "dataEntrega=" + dataEntrega + ", " : "") +
                (valor != null ? "valor=" + valor + ", " : "") +
                (produtoId != null ? "produtoId=" + produtoId + ", " : "") +
                (funcionarioId != null ? "funcionarioId=" + funcionarioId + ", " : "") +
            "}";
    }

}
