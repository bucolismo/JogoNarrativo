/**
 * aqwui temos aas etapas da narrativa, na ordem em que acontecem
 *
 * O controlador usa o ato atual da partida para saber qual cena construir e vai executar
 * Alguns atos são pontos de salvamento: o jogo salva automaticamente ao entrar neles e
 * oferece o salvamento manual (método lá embaixo Ato.PontoDeSalvamento)
 */

public enum Ato {
    PROLOGO("Prólogo"),
    ATO_I("Ato I"),
    FINAL_ATO_I("Ato I — Desfecho"),
    ATO_II("Ato II"),
    ATO_III("Ato III"),
    ATO_IV("Ato IV"),
    ATO_V("Ato V"),
    ATO_VI("Ato VI"),
    FINAL("Final"),
    FIM("Fim");

    private final String rotulo;

    Ato(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    /**
     * @return true se é permitido salvar (e carregar) a partida ao entrar nesse ato
     */

    public boolean pontoDeSalvamento() {
        return this != PROLOGO && this != FINAL && this != FIM;
    }
}
