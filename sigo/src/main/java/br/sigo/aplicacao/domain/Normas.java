package br.sigo.aplicacao.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import br.sigo.aplicacao.domain.enumeration.CategoryStatus;

/**
 * A Normas.
 */
@Entity
@Table(name = "normas")
public class Normas implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CategoryStatus status;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public Normas codigo(String codigo) {
        this.codigo = codigo;
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public Normas titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getDate() {
        return date;
    }

    public Normas date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public Normas status(CategoryStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Normas)) {
            return false;
        }
        return id != null && id.equals(((Normas) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Normas{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
