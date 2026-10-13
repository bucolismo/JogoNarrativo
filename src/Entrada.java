/**
 * buscando atingir o padrão MVC, esse método é o responsável pela entrada
 * de dados do usuario
 *
 * Utiliza um objeto Scanner para realizar a leitura de valores inteiros
 * e de strings digitados pelo jogador
 */

import java.io.InputStream;
import java.util.Scanner;

public class Entrada {
    public static final int ENTRADA_INVALIDA = -1;

    private Scanner scanner;

    public Entrada() {
        this(System.in);
    }

    /**
     * permite informar de onde a entrada será lida (útil nos testes de unidade)
     *
     * @param origem fluxo de onde os dados serão lidos
     */
    public Entrada(InputStream origem) {
        scanner = new Scanner(origem);
    }

    /**
     * Lê um número inteiro informado pelo usuário.
     *
     * Se o jogador digitar algo que não seja um número inteiro, o método não quebra
     * o jogo: devolve ENTRADA_INVALIDA (-1), que quem chama deve tratar como opção inválida.
     *
     * @return retorna o número inteiro que foi informado pelo jogador ou ENTRADA_INVALIDA
     */
    public int lerInteiro() {
        String linha = lerString().trim();

        try {
            return Integer.parseInt(linha);
        } catch (NumberFormatException e) {
            return ENTRADA_INVALIDA;
        }
    }

    /**
     * Lê uma linha de texto informada pelo usuário.
     *
     * @return retonar o texto informado pelo usuário
     * @throws IllegalStateException se a entrada acabou (ex.: Ctrl+D/EOF)
     */
    public String lerString(){
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Entrada encerrada.");
        }
        return scanner.nextLine();
    }
}
