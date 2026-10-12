/**
 * Classe responsável por iniciar a execução do jogo.
 *
 */

public class Partida {
    public static void main(String[] args) {
        Controlador controlador = new Controlador();

        try {
            controlador.menuInicial();
        } catch (IllegalStateException e) {
            System.out.println("\nEntrada encerrada. Saindo do jogo.");
        }
    }
}
