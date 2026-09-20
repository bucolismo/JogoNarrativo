public class Efeito {

    private String tipo;
    private String alvo;
    private int valor;
    private NPC npc;
    private Protagonista protagonista;
    private int itemId;

    // Efeito sobre atributo do protagonista
    public Efeito(String tipo, String alvo, int valor) {
        this.tipo = tipo;
        this.alvo = alvo;
        this.valor = valor;
        this.npc = null;
        this.itemId = -1;
    }

    // Efeito sobre confiança de um NPC
    public Efeito(NPC npc, int valor) {
        this.tipo = "CONFIANCA";
        this.alvo = null;
        this.valor = valor;
        this.npc = npc;
        this.itemId = -1;
    }
    public Efeito(int itemId) {
        this.tipo = "ADICIONAR_ITEM";
        this.alvo = null;
        this.valor = 1;
        this.npc = null;
        this.itemId = itemId;
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
    public int getItemId() {
        return itemId;
    }
}
