import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Item> todosOsItens;

    public Inventario() {
        this.todosOsItens = new ArrayList<>();
    }

    public void cadastrarItem(Item item) {
        this.todosOsItens.add(item);
    }

    public Item itemPorId(int id) {
        for (Item item : todosOsItens) {
            if (item.getItemID() == id) {
                return item;
            }
        }

        return null;
    }
    public boolean adicionarItemPorId(int id) {
        Item item = itemPorId(id);
        if (item != null && !item.getItemStatus()) {
            item.adicionar();
            return true;
        }
        return false;
    }

    public boolean consumirItemPorId(int id) {
        Item item = itemPorId(id);
        if (item != null && item.getItemStatus()) {
            item.consumir();
            return true;
        }
        return false;
    }


}
