/**
 * Classe responsável por iniciar a execução do jogo.
 *
 * É também onde as peças do MVC são montadas: a Entrada (leitura do teclado) é entregue ao Menu
 * (View), e o Menu e o repositório de saves são entregues ao Controlador.
 *
 */

public class Partida {
    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Menu menu = new Menu(entrada);
        RepositorioDeSaves repositorio = new RepositorioJson();
        Controlador controlador = new Controlador(menu, repositorio);

        try {
            controlador.menuInicial();
        } catch (IllegalStateException e) {
            System.out.println("\nEntrada encerrada. Saindo do jogo.");
        }
    }
}
