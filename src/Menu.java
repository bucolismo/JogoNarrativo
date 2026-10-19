/**
 * classe que representa o menu responsável pela interação textual com o jogador
 *
 * ela faz uso da classe Entrada para receber os dados do usuário e também apresenta
 * informações, instruções, créditos no menu inicial, sáida e tambem os  diálogos
 * ao longo do jogo
 */

public class Menu {

    public static final int OPCOES_MENU_PRINCIPAL = 7;

    private Entrada entrada;

    public Menu(Entrada entrada) {
        this.entrada = entrada;
    }

    public String recebeNome() {
        System.out.println("Digite o nome do protagonista:");
        String nome = entrada.lerString().trim();

        while (nome.isEmpty()) {
            System.out.println("O nome não pode ficar vazio. Digite o nome do protagonista:");
            nome = entrada.lerString().trim();
        }

        return nome;
    }

    public int recebeIdade() {
        System.out.println("Digite a idade do protagonista:");
        int idade = entrada.lerInteiro();

        while (idade <= 0) {
            System.out.println("Idade inválida. Digite um número inteiro maior que zero:");
            idade = entrada.lerInteiro();
        }

        return idade;
    }

    public String recebeGenero() {
        System.out.println("Digite o gênero do protagonista:");
        String genero = entrada.lerString().trim();

        while (genero.isEmpty()) {
            System.out.println("O gênero não pode ficar vazio. Digite o gênero do protagonista:");
            genero = entrada.lerString().trim();
        }

        return genero;
    }

    public int recebeOpcao() {
        System.out.println("Digite a opção desejada: ");
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
     * Sobrecarga para o menu principal, que tem 6 opções fixas.
     */
    public int validaOpcao() {
        return validaOpcao(OPCOES_MENU_PRINCIPAL);
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

                O jogo salva automaticamente ao iniciar cada ato e, nesses mesmos
                pontos, você também pode salvar manualmente em um dos 3 slots.
                Use "Continuar" ou "Carregar partida" no menu para retomar.

                Os finais que você alcançar e as conquistas que desbloquear ficam
                guardados entre as execuções do jogo: veja em "Conquistas".

                Boa sorte e boa história!
                """);
    }

    public void mostraCreditos() {
        System.out.println("""
================ CRÉDITOS ================
        
Jogo Narrativo Interativo

Projeto desenvolvido para o MI de Programação - EXA863
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
    4 - Conquistas
    5 - Instruções
    6 - Créditos
    7 - Sair
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

    /**
     * Mostra a tela de conquistas: quantos finais já foram alcançados e o estado de cada conquista.
     * A conquista oculta (Platina) aparece como "???" com uma dica até ser desbloqueada.
     *
     * @param progresso progresso do jogador
     */
    public void mostraConquistas(ProgressoGlobal progresso) {
        System.out.println("\n===== CONQUISTAS =====");
        System.out.println("Finais alcançados: " + progresso.getQuantidadeDeFinais() + "/" + ProgressoGlobal.TOTAL_DE_FINAIS);
        System.out.println();

        Conquista[] todas = Conquista.values();

        for (int i = 0; i < todas.length; i++) {
            Conquista conquista = todas[i];

            if (progresso.temConquista(conquista)) {
                System.out.println("[X] " + conquista.getTitulo());
                System.out.println("    " + conquista.getDescricao() + " (" + progresso.getDataDaConquista(conquista) + ")");
            } else if (conquista.isOculta()) {
                System.out.println("[ ] ???");
                System.out.println("    Dica: " + conquista.getDescricao());
            } else {
                System.out.println("[ ] " + conquista.getTitulo());
                System.out.println("    " + conquista.getDescricao());
            }
        }
    }

    /**
     * Mostra as opções da tela de conquistas.
     *
     * @return 1 para redefinir as conquistas ou 0 para voltar
     */
    public int mostraOpcoesDasConquistas() {
        System.out.println();
        System.out.println("1 - Redefinir conquistas");
        System.out.println("0 - Voltar");

        return validaOpcaoOuZero(1);
    }

    /**
     * Avisa o jogador que uma conquista acabou de ser desbloqueada.
     *
     * @param conquista conquista desbloqueada
     */
    public void mostraConquistaDesbloqueada(Conquista conquista) {
        System.out.println("\n*** CONQUISTA DESBLOQUEADA: " + conquista.getTitulo() + " ***");
    }

    public void mostraSaida() {
        System.out.println("Encerrando o jogo. Até a próxima!");
    }

    /**
     * Valida a opção digitada garantindo que ela esteja entre 0 e maxOpcoes (0 serve para voltar/cancelar).
     *
     * @param maxOpcoes maior opção válida
     * @return a opção escolhida
     */
    public int validaOpcaoOuZero(int maxOpcoes) {
        int opcao = recebeOpcao();

        while (opcao < 0 || opcao > maxOpcoes) {
            System.out.println("Escolha inválida. Digite um número de 0 a " + maxOpcoes + ".");
            opcao = recebeOpcao();
        }

        return opcao;
    }

    /**
     * Faz uma pergunta de sim ou não. Qualquer resposta diferente de "s" ou "sim" conta como não,
     * que é a escolha segura para ações que apagam ou substituem dados.
     *
     * @param pergunta texto da pergunta
     * @return true somente se o jogador confirmou
     */
    public boolean confirma(String pergunta) {
        System.out.println(pergunta + " (s/n)");
        String resposta = entrada.lerString().trim().toLowerCase();

        return resposta.equals("s") || resposta.equals("sim");
    }

    /**
     * Mostra o menu exibido nos pontos de salvamento (início de cada ato).
     *
     * @param rotuloDoAto nome do ato em que o jogador está
     * @return 1 para continuar, 2 para salvar, 3 para voltar ao menu principal
     */
    public int mostraPontoDeSalvamento(String rotuloDoAto) {
        System.out.println("""

    ===== PONTO DE SALVAMENTO — %s =====
    1 - Continuar a história
    2 - Salvar manualmente
    3 - Voltar ao menu principal
    """.formatted(rotuloDoAto));

        return validaOpcao(3);
    }

    /**
     * Mostra uma lista numerada de saves e lê a opção do jogador.
     *
     * @param titulo título da tela
     * @param linhas descrição de cada save, na ordem das opções 1, 2, 3...
     * @return o número da opção escolhida, ou 0 para voltar
     */
    public int mostraListaDeSaves(String titulo, java.util.List<String> linhas) {
        System.out.println("\n===== " + titulo + " =====");

        for (int i = 0; i < linhas.size(); i++) {
            System.out.println((i + 1) + " - " + linhas.get(i));
        }
        System.out.println("0 - Voltar");

        return validaOpcaoOuZero(linhas.size());
    }

    /**
     * Pergunta o que fazer com o save selecionado.
     *
     * @return 1 para carregar, 2 para excluir, 0 para voltar
     */
    public int escolheAcaoDoSave() {
        System.out.println("""

    1 - Carregar este save
    2 - Excluir este save
    0 - Voltar
    """);

        return validaOpcaoOuZero(2);
    }

    public void mostraString(String texto) {
        System.out.println(texto);
    }
}
