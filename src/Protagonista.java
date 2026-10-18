/**
 * é a classe que representa o personagem controlado pelo jogador
 *
 * O protagonista possui atributos que representam seu estado durante a
 * partida (violência/razão/paranoia), as provas reunidas e um inventário que
 * armazena os itens disponíveis. Todos os atributos começam em 0 e vão até 100.
 */

import java.util.LinkedHashMap;
import java.util.Map;

public class Protagonista extends Personagem {
    public static final String RAZAO = "Razão";
    public static final String PARANOIA = "Paranoia";
    public static final String VIOLENCIA = "Violência";
    public static final String PROVAS = "Provas";
    public static final String[] NOMES_DOS_ATRIBUTOS = {RAZAO, PARANOIA, VIOLENCIA, PROVAS};

    private static final int VALOR_INICIAL = 0;
    private static final int VALOR_MAXIMO = 100;

    private Atributo[] atributos;
    private Inventario inventario;

    public Protagonista(String nome, int idade, String genero) {
        super(nome, idade, genero);

        atributos = new Atributo[4];

        atributos[0] = new Atributo(RAZAO, VALOR_INICIAL, VALOR_MAXIMO);
        atributos[1] = new Atributo(PARANOIA, VALOR_INICIAL, VALOR_MAXIMO);
        atributos[2] = new Atributo(VIOLENCIA, VALOR_INICIAL, VALOR_MAXIMO);
        atributos[3] = new Atributo(PROVAS, VALOR_INICIAL, VALOR_MAXIMO);

        inventario = new Inventario();
    }

    /**
     * esse método altera o valor de um atributo do protagonista.
     *
     * esse método funciona baseado no @param quantidade que é passado,
     * se a quantidade for positiva adiciona, se a quantidade for negativa
     * o valor do atributo é reduzido.
     *
     * @param nomeAtributo nome do atributo que será alterado
     * @param quantidade valor que será acrescentado ou subtraído do atributo
     */
    public void alteraAtributo(String nomeAtributo, int quantidade) {
        for (int i = 0; i < atributos.length; i++) {
            if (atributos[i].getNome().equalsIgnoreCase(nomeAtributo)) {
                if (quantidade >= 0) {
                    atributos[i].aumentaValor(quantidade);
                } else {
                    atributos[i].reduzValor(Math.abs(quantidade));
                }
                return;
            }
        }
        System.out.println("Atributo não existente: " + nomeAtributo);
    }

    /**
     * @return mapa novo com o nome e o valor atual de cada atributo (na ordem de declaração)
     */
    public Map<String, Integer> getAtributos() {
        Map<String, Integer> valores = new LinkedHashMap<>();

        for (Atributo atributo : atributos) {
            valores.put(atributo.getNome(), atributo.getValor());
        }

        return valores;
    }

    /**
     * informa se o protagonista possui um atributo com o nome informado (sem diferenciar maiúsculas)
     *
     * @param nomeAtributo nome do atributo procurado
     * @return true se o atributo existe, false caso contrário
     */
    public boolean possuiAtributo(String nomeAtributo) {
        for (Atributo atributo : atributos) {
            if (atributo.getNome().equalsIgnoreCase(nomeAtributo)) {
                return true;
            }
        }
        return false;
    }

    public int getAtributo(String nomeAtributo) {
        for (int i = 0; i < atributos.length; i++) {
            if (atributos[i].getNome().equalsIgnoreCase(nomeAtributo)) {
                return atributos[i].getValor();
            }
        }
        return 0;
    }

    /**
     * cadastra no inventário do protagonista todos os itens que a história pode conceder
     * (o catálogo completo fica na classe CatalogoDeItens). Todos os itens são cadastrados
     * porque, por padrão, ficam desabilitados: o jogador precisa pegar o item em algum
     * momento da história para que ele seja habilitado e passe a fazer parte do inventário.
     */
    public void cadastrarItens() {
        for (Item item : CatalogoDeItens.criarTodos()) {
            inventario.cadastrarItem(item);
        }
    }

    public Inventario getInventario() {
        return inventario;
    }
}
