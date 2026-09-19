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

    public int validaOpcao() {
        int opcao = recebeOpcao();

        while (opcao != 1 && opcao != 2 && opcao != 3 && opcao != 4) {
            System.out.println("Escolha inválida.");
            opcao = recebeOpcao();
        }
        return opcao;
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

    public void mostraCreditos(){
        System.out.println("""
================ CRÉDITOS ================
        
Jogo Narrativo Interativo

Projeto desenvolvido para a disciplina de Algoritmos II
Universidade Estadual de Feira de Santana (UEFS)

Autores:
Levi Sena Andrade
Luis Felipe Batista

2026
                 
        """);
    }

    public void mostraMenu(){
        System.out.println("""
        ================  MENU ================
        1 - Iniciar partida
        2 - Instruções
        3 - Créditos
        4 - Sair
        """);
    }

    public int mostraDialogo(Dialogo dialogo){

        //Caso de ser o narrador:
        if (!dialogo.getPersonagem().getNome().equalsIgnoreCase("Narrador")) {
            System.out.println("[" + dialogo.getPersonagem().getNome() + "]");
        }
        //Printa o texto do dialogo
        System.out.println(dialogo.getTexto());

        //Caso de haver opções no dialogo:
        if (dialogo.possuiOpcoes()) {
            System.out.println("\n--- Opções ---");
            Escolha[] escolhas = dialogo.getEscolhas();
            for (int i = 0; i < escolhas.length; i++) {
                System.out.println(escolhas[i].getTexto());
            }
           return validaOpcao();//Opção escolhida do dialogo
        }
        return 0;//Dialogo sem opções
    }

    public void mostraSaida(){
        System.out.println("Saída");
    }
}
