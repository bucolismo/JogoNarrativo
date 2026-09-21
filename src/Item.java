/**
 * essa classe representa um item que pode estar disponível no inventário do protagonista
 *
 * cada item tem nome, tipo, descrição e um identificador único (id/índice)
 * o atributo status que é do tipo boolean indica se o item está atualmente disponível no inventário
 * true se está "habilitado" ou seja, disponível, e false se não está
 */

public class Item {
    private String itemNome;
    private String itemTipo;
    private String itemDescricao;
    private int itemID;
    private boolean itemStatus;

    public Item(String itemNome, String itemTipo, String itemDescricao, int itemId) {
        this.itemNome = itemNome;
        this.itemTipo = itemTipo;
        this.itemDescricao = itemDescricao;
        this.itemID = itemId;
        this.itemStatus = false; // Inicia fora do inventário
    }

    public String getNome() {
        return this.itemNome;
    }

    public String getDescricao() {
        return this.itemDescricao;
    }

    public int getItemID() {
        return this.itemID;
    }

    public boolean getItemStatus() {
        return this.itemStatus;
    }

    /**
     * "remove" o item do inventário, mudando seu status para indisponível.
     */
    public void remover() {
        if (this.itemStatus) {
            this.itemStatus = false;
        }
    }

    /**
     * adiciona o item ao inventário, mudando seu status para disponível.
     */
    public void adicionar() {
        if (!this.itemStatus) {
            this.itemStatus = true;
        }
    }
}
