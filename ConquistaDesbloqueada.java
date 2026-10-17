/**
 * registro de uma conquista que o jogador já desbloqueou: qual foi e quando
 *
 * O id é o nome da conquista no enum Conquista (ex.: "PLATINA") e a data fica guardada já
 * formatada para exibição (ex.: "17/10/2026 14:32").
 */
public class ConquistaDesbloqueada {
    private String id;
    private String dataHora;

    public ConquistaDesbloqueada() {
    }

    public ConquistaDesbloqueada(String id, String dataHora) {
        this.id = id;
        this.dataHora = dataHora;
    }

    public String getId() {
        return id;
    }

    public String getDataHora() {
        return dataHora;
    }
}
