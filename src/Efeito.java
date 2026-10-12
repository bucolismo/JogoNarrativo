/**
 * Representa um efeito que pode ser aplicado ao estado do jogo
 *
 * Um efeito pode alterar um atributo do protagonista, modificar a confiança
 * de um NPC ou adicionar um item ao inventário do protagonista.
 */
public class Efeito {

    private String tipo;
    private String alvo;
    private int valor;
    private NPC npc;
    private Protagonista protagonista;
    private int itemId;

    /**
     * Cria um efeito relacionado a um atributo do protagonista.
     *
     * @param tipo tipo do efeito
     * @param alvo atributo que será afetado
     * @param valor valor da alteração
     */

    public Efeito(String tipo, String alvo, int valor) {
        this.tipo = tipo;
        this.alvo = alvo;
        this.valor = valor;
        this.npc = null;
        this.itemId = -1;
    }

    /**
     * Cria um efeito que altera a confiança de um NPC.
     *
     * @param npc NPC cuja confiança será alterada
     * @param valor valor da alteração na confiança
     */
    public Efeito(NPC npc, int valor) {
        this.tipo = "CONFIANCA";
        this.alvo = null;
        this.valor = valor;
        this.npc = npc;
        this.itemId = -1;
    }

    /**
     * Cria um efeito que adiciona um item ao inventário do protagonista.
     *
     * @param itemId identificador (indice) do item que será adicionado ao ivnentario
     */
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
