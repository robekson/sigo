package br.sigo.aplicacao.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import br.sigo.aplicacao.domain.enumeration.CategoryStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link br.sigo.aplicacao.domain.Normas} entity. This class is used
 * in {@link br.sigo.aplicacao.web.rest.NormasResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /normas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NormasCriteria implements Serializable, Criteria {
    /**
     * Class for filtering CategoryStatus
     */
    public static class CategoryStatusFilter extends Filter<CategoryStatus> {

        public CategoryStatusFilter() {
        }

        public CategoryStatusFilter(CategoryStatusFilter filter) {
            super(filter);
        }

        @Override
        public CategoryStatusFilter copy() {
            return new CategoryStatusFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter codigo;

    private StringFilter titulo;

    private LocalDateFilter date;

    private CategoryStatusFilter status;

    public NormasCriteria() {
    }

    public NormasCriteria(NormasCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.codigo = other.codigo == null ? null : other.codigo.copy();
        this.titulo = other.titulo == null ? null : other.titulo.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.status = other.status == null ? null : other.status.copy();
    }

    @Override
    public NormasCriteria copy() {
        return new NormasCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCodigo() {
        return codigo;
    }

    public void setCodigo(StringFilter codigo) {
        this.codigo = codigo;
    }

    public StringFilter getTitulo() {
        return titulo;
    }

    public void setTitulo(StringFilter titulo) {
        this.titulo = titulo;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public CategoryStatusFilter getStatus() {
        return status;
    }

    public void setStatus(CategoryStatusFilter status) {
        this.status = status;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NormasCriteria that = (NormasCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(codigo, that.codigo) &&
            Objects.equals(titulo, that.titulo) &&
            Objects.equals(date, that.date) &&
            Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        codigo,
        titulo,
        date,
        status
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NormasCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (codigo != null ? "codigo=" + codigo + ", " : "") +
                (titulo != null ? "titulo=" + titulo + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
            "}";
    }

}
