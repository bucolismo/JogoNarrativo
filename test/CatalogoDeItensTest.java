import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CatalogoDeItensTest {

    @Test
    public void idsDoCatalogoDevemSerUnicosEPositivos() {
        Set<Integer> ids = new HashSet<>();

        for (Item item : CatalogoDeItens.criarTodos()) {
            assertTrue(item.getItemID() > 0, "id inválido: " + item.getItemID());
            assertTrue(ids.add(item.getItemID()), "id repetido: " + item.getItemID());
        }
    }

    @Test
    public void todosOsItensDevemComecarForaDoInventario() {
        for (Item item : CatalogoDeItens.criarTodos()) {
            assertFalse(item.getItemStatus());
        }
    }

    @Test
    public void cadaChamadaDeveDevolverItensNovos() {
        List<Item> primeira = CatalogoDeItens.criarTodos();
        primeira.get(0).adicionar();

        assertFalse(CatalogoDeItens.criarTodos().get(0).getItemStatus());
    }

    @Test
    public void idsDeveListarTodosOsItensDoCatalogo() {
        Set<Integer> ids = CatalogoDeItens.ids();

        assertEquals(CatalogoDeItens.criarTodos().size(), ids.size());
        assertTrue(ids.contains(10));
        assertFalse(ids.contains(-1));
    }
}
