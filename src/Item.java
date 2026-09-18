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

    public void remover() {
        if (this.itemStatus) {
            this.itemStatus = false;
        }
    }

    public void adicionar() {
        if (!this.itemStatus) {
            this.itemStatus = true;
        }
    }
}
