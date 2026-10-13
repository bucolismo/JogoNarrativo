/**
 * Representa um efeito que pode ser aplicado ao estado do jogo
 *
 * Um efeito pode alterar um atributo do protagonista, modificar a confiança
 * de um NPC, adicionar um item ao inventário do protagonista ou encerrar
 * a partida (game over).
 */
public class Efeito {

    public static final String TIPO_ATRIBUTO = "ATRIBUTO";
    public static final String TIPO_CONFIANCA = "CONFIANCA";
    public static final String TIPO_ADICIONAR_ITEM = "ADICIONAR_ITEM";
    public static final String TIPO_GAME_OVER = "GAME_OVER";

    private String tipo;
    private String alvo;
    private int valor;
    private NPC npc;
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
        this.tipo = TIPO_CONFIANCA;
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
        this.tipo = TIPO_ADICIONAR_ITEM;
        this.alvo = null;
        this.valor = 1;
        this.npc = null;
        this.itemId = itemId;
    }

    /**
     * Cria um efeito que encerra a partida (game over) assim que a escolha é aplicada.
     *
     * @return efeito de fim de jogo
     */
    public static Efeito gameOver() {
        return new Efeito(TIPO_GAME_OVER, null, 0);
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
