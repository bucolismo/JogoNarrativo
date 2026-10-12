/**
 * Classe responsável por iniciar a execução do jogo.
 */
public class Partida {

    public static void main(String[] args) {
        try {
            Entrada entrada = new Entrada();

            Controlador controlador = new Controlador();

            Menu menu = new Menu(controlador, entrada);

            menu.executarMenuInicial();

        } catch (IllegalStateException e) {
            System.out.println("\nEntrada encerrada. Saindo do jogo.");
        }
    }
}
