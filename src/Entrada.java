/**
 * buscando atingir o padrão MVC, esse método é o responsável pela entrada
 * de dados do usuario
 *
 * Utiliza um objeto Scanner para realizar a leitura de valores inteiros
 * e de strings digitados pelo jogador
 */

import java.util.Scanner;

public class Entrada {
    private Scanner scanner;

    public Entrada() {
        scanner = new Scanner(System.in);
    }

    /**
     * Lê um número inteiro informado pelo usuário e remove a quebra de linha
     *
     * @return retorna o número inteiro que foi informado pelo jogador
     */
    public int lerInteiro() {
        int numero = scanner.nextInt();
        scanner.nextLine(); // remove o Enter do buffer
        return numero;
    }

    /**
     * Lê uma linha de texto informada pelo usuário.
     *
     * @return retonar o texto informado pelo usuário
     */
    public String lerString(){
        return scanner.nextLine();
    }
}
