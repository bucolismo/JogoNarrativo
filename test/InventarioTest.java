import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventarioTest {

    @Test
    public void deveAdicionarItem() {
        Inventario inventario = new Inventario();
        Item item = new Item("Chave", "Acesso", "Uma chave", 1);
        inventario.cadastrarItem(item);
        inventario.adicionarItemPorId(1);

        assertTrue(inventario.possuiItemPorId(1));
    }

    @Test
    public void naoDevePossuirItemNaoAdicionado() {
        Inventario inventario = new Inventario();
        Item item = new Item("Chave", "Acesso", "Uma chave", 1);
        inventario.cadastrarItem(item);

        assertFalse(inventario.possuiItemPorId(1));
    }

    @Test
    public void deveRemoverItem() {
        Inventario inventario = new Inventario();
        Item item = new Item("Chave", "Acesso", "Uma chave", 1);
        inventario.cadastrarItem(item);
        inventario.adicionarItemPorId(1);
        inventario.removerItemPorId(1);

        assertFalse(inventario.possuiItemPorId(1));
    }
}