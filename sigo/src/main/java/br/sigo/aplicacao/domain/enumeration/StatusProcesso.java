package br.sigo.aplicacao.domain.enumeration;

/**
 * The StatusProcesso enumeration.
 */
public enum StatusProcesso {
    ANDAMENTO("Em Andamento"),
    FINALIZADO("Finalizado");

    private final String value;


    StatusProcesso(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
