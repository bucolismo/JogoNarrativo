/**
 * Classe responsável pela interação textual com o jogador.
 *
 * Apresenta menus, instruções, créditos, diálogos e mensagens,
 * além de receber e validar as entradas do jogador.
 */
public class Menu {

    private final Entrada entrada;
    private final Controlador controlador;

    public Menu(Controlador controlador, Entrada entrada) {
        this.controlador = controlador;
        this.entrada = entrada;
    }

    /**
     * Executa o menu principal e encaminha as ações ao controlador.
     */
    public void executarMenuInicial() {
        int opcao;

        do {
            mostraMenu();
            opcao = validaOpcao();

            switch (opcao) {
                case 1:
                    controlador.iniciarPartida(this);
                    break;

                case 2:
                    mostraString("Funcionalidade de continuar partida ainda não implementada.");
                    break;

                case 3:
                    mostraString("Funcionalidade de carregar partida ainda não implementada.");
                    break;

                case 4:
                    mostraInstrucoes();
                    break;

                case 5:
                    mostraCreditos();
                    break;

                case 6:
                    mostraSaida();
                    break;
            }

        } while (opcao != 6);
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
        System.out.println("Digite o gênero do protagonista:");
        return entrada.lerString();
    }

    public int recebeOpcao() {
        System.out.println("Digite a opção desejada:");
        return entrada.lerInteiro();
    }

    /**
     * Recebe e valida uma opção entre 1 e maxOpcoes.
     */
    public int validaOpcao(int maxOpcoes) {
        int opcao = recebeOpcao();

        while (opcao < 1 || opcao > maxOpcoes) {
            System.out.println(
                    "Escolha inválida. Digite um número de 1 a "
                            + maxOpcoes + "."
            );

            opcao = recebeOpcao();
        }

        return opcao;
    }

    /**
     * Valida as seis opções do menu principal.
     */
    public int validaOpcao() {
        return validaOpcao(6);
    }

    /**
     * Recebe uma escolha entre três alternativas.
     * Mantido para compatibilidade com chamadas existentes.
     */
    public int recebeEscolha() {
        return validaOpcao(3);
    }

    public void mostraInstrucoes() {
        System.out.println("""
                
                ===== INSTRUÇÕES =====

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
                
                ================ MENU ================
                1 - Nova partida
                2 - Continuar partida
                3 - Carregar partida
                4 - Instruções
                5 - Créditos
                6 - Sair
                """);
    }

    /**
     * Exibe um diálogo e recebe a escolha, caso existam opções.
     *
     * @param dialogo diálogo que será apresentado
     * @return índice da escolha selecionada ou 0 se não houver opções
     */
    public int mostraDialogo(Dialogo dialogo) {

        if (dialogo == null) {
            return 0;
        }

        if (!dialogo.getPersonagem().getNome()
                .equalsIgnoreCase("Narrador")) {
            System.out.println(
                    "[" + dialogo.getPersonagem().getNome() + "]"
            );
        }

        System.out.println(dialogo.getTexto());

        if (dialogo.possuiOpcoes()) {
            System.out.println("\n--- Opções ---");

            Escolha[] escolhas = dialogo.getEscolhas();

            for (int i = 0; i < escolhas.length; i++) {
                System.out.println((i + 1) + " - " + escolhas[i].getTexto());
            }

            return validaOpcao(escolhas.length);
        }

        return 0;
    }

    public void mostraSaida() {
        System.out.println("Saindo do jogo...");
    }

    public void mostraString(String texto) {
        System.out.println(texto);
    }
}
