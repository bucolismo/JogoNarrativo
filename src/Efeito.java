public class Efeito {

    private String tipo;
    private String alvo;
    private int valor;

    private NPC npc;

    // Efeito sobre atributo do protagonista
    public Efeito(String tipo, String alvo, int valor) {
        this.tipo = tipo;
        this.alvo = alvo;
        this.valor = valor;
        this.npc = null;
    }

    // Efeito sobre confiança de um NPC
    public Efeito(NPC npc, int valor) {
        this.tipo = "CONFIANCA";
        this.alvo = null;
        this.valor = valor;
        this.npc = npc;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAlvo() {
        return alvo;
    }

    public int getValor() {
        return valor;
    }

    public NPC getNpc() {
        return npc;
    }
}
