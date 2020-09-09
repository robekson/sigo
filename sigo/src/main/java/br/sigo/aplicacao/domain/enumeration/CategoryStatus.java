package br.sigo.aplicacao.domain.enumeration;

/**
 * The CategoryStatus enumeration.
 */
public enum CategoryStatus {
    EM_VIGOR("Em Vigor");

    private final String value;


    CategoryStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
