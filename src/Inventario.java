/**
 * Representa o inventário do protagonista e controla os itens disponíveis
 * durante a partida.
 *
 * O inventário utiliza os identificadores que são os índices dos itens para localizar,
 * adicionar, remover e verificar a posse de itens. Esses índices são valores numericos
 * únicso para cada item que permitem acessar cada um individualmente.
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private List<Item> todosOsItens;

    public Inventario() {
        this.todosOsItens = new ArrayList<>();
    }

    /**
     * cadastra um item no inventário
     * @param item item que será cadastrado
     */
    public void cadastrarItem(Item item) {
        this.todosOsItens.add(item);
    }

    /**
     * procura um item pelo seu índice.
     *
     * @param id identificador (íondice) do item procurado
     * @return item correspondente ao índice ou ""null" caso não seja encontrado
     */
    public Item itemPorId(int id) {
        for (Item item : todosOsItens) {
            if (item.getItemID() == id) {
                return item;
            }
        }

        return null;
    }

    /**
     * Adiciona ao inventário o item correspondente ao identificador informado.
     *
     * @param id índice do item que será adicionado
     * @return "true" se o item foi adicionado com sucesso, "false" caso o item não exista
     *         ou se já estiver no inventário
     */
    public boolean adicionarItemPorId(int id) {
        Item item = itemPorId(id);
        if (item != null && !item.getItemStatus()) {
            item.adicionar();
            return true;
        }
        return false;
    }

    /**
     * "desativa" do inventário o item correspondente ao indíce informado
     * apesar do nome do módulo ser "remover" como a parte relacionada aos itens funciona como um boolean
     * ou seja, se o item está "habilitado" ou não, os itens não são removidos, eles são "desabilitados",
     * deixando de estar disponpiveis no inventário do protagonista
     * @param id indícee do item que será removido
     *
     * @return "true" se o item foi desativado com sucesso, "false" caso o item não exista
     * ou se o item não estiver no inventário
     *
     */
    public boolean removerItemPorId(int id) {
        Item item = itemPorId(id);
        if (item != null && item.getItemStatus()) {
            item.remover();
            return true;
        }
        return false;
    }

    /**
     * Verifica se o está no inventário procurando pelo índice
     *
     * @param id índice do item procurado
     * @return "true" se o item estiver no inventário, "false" se não estiver
     */
    public boolean possuiItemPorId(int id) {
        Item item = itemPorId(id);

        return item != null && item.getItemStatus();
    }
}
