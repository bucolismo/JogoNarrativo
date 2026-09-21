/**
 * classe que representa o menu responsável pela interação textual com o jogador
 *
 * ela faz uso da classe Entrada para receber os dados do usuário e também apresenta
 * informações, instruções, créditos no menu inicial, sáida e tambem os  diálogos
 * ao longo do jogo
 */

public class Menu {

    private Entrada entrada;

    public Menu(Entrada entrada) {
        this.entrada = entrada;
    }

    public String recebeNome() {
        System.out.println("Digite o nome do protagonista:");
        return entrada.lerString();
    }

    public int recebeIdade() {
        System.out.println("Digite a idade do protagonista:");
        return entrada.lerInteiro();
    }

    public String recebeGenero() {
        System.out.println("Digite o genero do protagonista:");
        return entrada.lerString();
    }

    public int recebeOpcao() {
        System.out.println("Digite a opcao desejada: ");
        return entrada.lerInteiro();
    }

    /**
     * Valida a opção digitada garantindo que ela esteja no intervalo de 1 até maxOpcoes.
     * Funciona dinamicamente para diálogos com qualquer quantidade de escolhas.
     *
     * @param maxOpcoes O número máximo de opções válidas disponíveis.
     * @return O número da opção escolhida válida.
     */
    public int validaOpcao(int maxOpcoes) {
        int opcao = recebeOpcao();

        while (opcao < 1 || opcao > maxOpcoes) {
            System.out.println("Escolha inválida. Digite um número de 1 a " + maxOpcoes + ".");
            opcao = recebeOpcao();
        }

        return opcao;
    }

    /**
     * Sobrecarga mantida para o menu principal com 4 opções fixas.
     */
    public int validaOpcao() {
        return validaOpcao(4);
    }

    public int recebeEscolha() {
        System.out.println("Digite a opção desejada: ");
        int escolha = entrada.lerInteiro();

        while (escolha != 1 && escolha != 2 && escolha != 3) {
            System.out.println("Escolha inválida.");
            escolha = entrada.lerInteiro();
        }

        return escolha;
    }

    public void mostraInstrucoes() {
        System.out.println("""
                \n===== INSTRUÇÕES =====

                Bem-vindo ao nosso jogo narrativo interativo!

                Você assumirá o papel do protagonista e deverá tomar decisões
                ao longo da história. Suas escolhas poderão alterar os
                acontecimentos, os relacionamentos com outros personagens,
                o acesso a determinadas cenas e até mesmo o final da história.

                Durante a aventura, fique atento às informações apresentadas
                e pense bem antes de escolher. Algumas consequências podem
                não ser percebidas imediatamente.

                O jogo é baseado em narrativa e escolhas. Não existe apenas
                um caminho correto: diferentes decisões podem levar a
                diferentes acontecimentos e finais.

                Boa sorte e boa história!
                """);
    }

    public void mostraCreditos() {
        System.out.println("""
================ CRÉDITOS ================
        
Jogo Narrativo Interativo

Projeto desenvolvido para a disciplina de Algoritmos II - EXA863
Universidade Estadual de Feira de Santana (UEFS)
Docente: Roberto Almeida Bittencourt

Autores:
Levi Sena Andrade
Luis Felipe Batista

2026
                 
        """);
    }

    public void mostraMenu() {
        System.out.println("""
        ================  MENU ================
        1 - Iniciar partida
        2 - Instruções
        3 - Créditos
        4 - Sair
        """);
    }

    /**
     * Exibe um diálogo e se tiver apresenta as opções
     * disponíveis ao jogador e recebe a entrada com a escolha dele
     *
     * @param dialogo diálogo que será apresentado
     * @return ele retorna o número da escolha selecionada ou retorna "0" se o diálogo não tiver opções
     */
    public int mostraDialogo(Dialogo dialogo) {

        // Caso de não ser o narrador:
        if (!dialogo.getPersonagem().getNome().equalsIgnoreCase("Narrador")) {
            System.out.println("[" + dialogo.getPersonagem().getNome() + "]");
        }

        // Printa o texto do diálogo
        System.out.println(dialogo.getTexto());

        // Caso haja opções no diálogo:
        if (dialogo.possuiOpcoes()) {
            System.out.println("\n--- Opções ---");
            Escolha[] escolhas = dialogo.getEscolhas();

            for (int i = 0; i < escolhas.length; i++) {
                System.out.println(escolhas[i].getTexto());
            }

            // Valida considerando a quantidade total de escolhas presentes no diálogo
            return validaOpcao(escolhas.length);
        }

        return 0; // Diálogo sem opções
    }

    public void mostraSaida() {
        System.out.println("Saída");
    }

    public void mostraString(String texto) {
        System.out.println(texto);
    }
}
