/**
 * diálogo da narrativa, que é associado a um personagem e a um trecho de texto
 *
 * os diálogos podem conter nenhuma ou várias escolhas que serão apresentadas
 * ao jogador durante a partida
 *
 * @author Levi Sena Andrade
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dialogo {

    private Personagem personagem;
    private String texto;
    private List<Escolha> escolhas;

    // Construtor 1: Diálogo sem opções (apenas narrativa/fala)
    public Dialogo(Personagem personagem, String texto) {
        this.personagem = personagem;
        this.texto = texto;
        this.escolhas = null;
    }

    public Dialogo(Personagem personagem, String texto, Escolha... escolhas) {
        this.personagem = personagem;
        this.texto = texto;

        if (escolhas != null && escolhas.length > 0) {
            this.escolhas = new ArrayList<>(Arrays.asList(escolhas));
        } else {
            this.escolhas = null;
        }
    }

    // Retorna o Arraydinâmico como um array normal
    public Escolha[] getEscolhas() {
        if (escolhas == null) {
            return null;
        }
        return escolhas.toArray(new Escolha[0]);
    }


    public List<Escolha> getListaEscolhas() {
        return escolhas;
    }

    public String getTexto() {
        return texto;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    /**
     * esse método ve se o diálogo possui escolhas disponíveis para o jogador poder escolher ou não
     *
     * @return retorna true se tiver pelo menos 1 escolha, se não tiver ele retorna false
     */
    public boolean possuiOpcoes() {
        return escolhas != null && !escolhas.isEmpty();
    }

    /**
     * esse método serve somente pra retornar uma escolha do diálogo a partir de seu índice
     *
     * O índice utilizado pelo método começa em 1, que ocrresponde numeração apresentada ao jogador.
     *
     * @param indice índice da escolha a ser retornada
     * @return retorna a escolha correspondente ao índice ou null se o índice for inválido
     */
    public Escolha getEscolha(int indice) {
        if (escolhas == null || indice < 1 || indice > escolhas.size()) {
            return null;
        }
        return escolhas.get(indice - 1);
    }

    /**
     * eses método serve pra adiiconar uma nova escolha ao diálogo
     *
     * @param novaEscolha é a nova escolha que será adicionada ao diálogo
     */
    public void adicionaEscolha(Escolha novaEscolha) {
        this.escolhas.add(novaEscolha);
    }

    public int getQuantidadeEscolhas() {
        return escolhas.size();
    }
}
