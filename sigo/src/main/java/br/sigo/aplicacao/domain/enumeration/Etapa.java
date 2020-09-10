package br.sigo.aplicacao.domain.enumeration;

/**
 * The Etapa enumeration.
 */
public enum Etapa {
    FIBRA_FILAMENTO("Industria Fibra e Filamento"),
    TEXTIL("Industria Textil"),
    CONFECCAO("Industria de Confecção"),
    DISTRIBUICAO_VAREJO("Distribuição e Varejo");

    private final String value;


    Etapa(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
