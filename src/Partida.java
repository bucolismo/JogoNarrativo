/**
 * Classe responsável por iniciar a execução do jogo.
 *
 * É também onde as peças do MVC são montadas: a Entrada (leitura do teclado) é entregue ao Menu
 * (View), e o Menu e os repositórios (saves e progresso) são entregues ao Controlador.
 *
 * @author Levi Sena Andrade
 */

public class Partida {
    public static void main(String[] args) {
        Entrada entrada = new Entrada();
        Menu menu = new Menu(entrada);
        RepositorioDeSaves repositorio = new RepositorioJson();
        RepositorioDeProgresso repositorioDeProgresso = new RepositorioDeProgresso();
        Controlador controlador = new Controlador(menu, repositorio, repositorioDeProgresso);

        try {
            controlador.menuInicial();
        } catch (IllegalStateException e) {
            // a entrada de dados acabou (ex.: Ctrl+D): encerra sem mostrar erro técnico
            System.out.println("\nEntrada encerrada. Saindo do jogo.");
        }
    }
}
