public class Controlador {

    private Menu menu;
    private Entrada entrada;

    public Controlador() {
        entrada = new Entrada();
        menu = new Menu(entrada);
    }

    public void menuInicial() {

        int opcao;

        do {
            menu.mostraMenu();

            opcao = menu.validaOpcao();

            switch (opcao) {

                case 1:
                    iniciarPartida();
                    break;

                case 2:
                    menu.mostraInstrucoes();
                    break;

                case 3:
                    menu.mostraCreditos();
                    break;

                case 4:
                    menu.mostraSaida();
                    break;

            }

        } while (opcao != 4);
    }

    public void iniciarPartida() {

        String nome = menu.recebeNome();
        int idade = menu.recebeIdade();
        String genero = menu.recebeGenero();

        Protagonista protagonista = new Protagonista(nome, idade, genero);

        protagonista.cadastrarItens();

        // inicialização dos NPCs
        NPC npc1 = new NPC("Marcos", 30, "Masculino", 50);
        NPC npc2 = new NPC("Angela", 40, "Feminino", 50);
        NPC npc3 = new NPC("Henrique", 25, "Masculino", 50);
        NPC npc4 = new NPC("Lourdes", 35, "Feminino", 50);
        NPC npc5 = new NPC("Almeida", 50, "Masculino", 50);

        Personagem narrador =
                new Personagem(
                        "Narrador",
                        0,
                        "Neutro"
                );

        ConstrutorDeCenas construtor = new ConstrutorDeCenas();

        Cena prologo = construtor.criarPrologo(
                narrador,
                protagonista,
                npc1
        );

        executaCena(prologo, protagonista);
    }

    public void executaCena(
            Cena cena,
            Protagonista protagonista
    ) {

        for (Dialogo dialogo : cena.getDialogos()) {

            dialogo.executarDialogo();

            if (dialogo.possuiOpcoes()) {

                int escolha = menu.recebeEscolha();

                Escolha escolhaSelecionada =
                        dialogo.getEscolha(escolha);

                System.out.println(
                        escolhaSelecionada.getTexto()
                );

                for (Efeito efeito :
                        escolhaSelecionada.getEfeitos()) {

                    if (efeito.getTipo().equals("ATRIBUTO")) {

                        protagonista.alteraAtributo(
                                efeito.getAlvo(),
                                efeito.getValor()
                        );

                    } else if (efeito.getTipo().equals("CONFIANCA")) {

                        efeito.getNpc().alterarConfianca(
                                efeito.getValor()
                        );

                    } else if (efeito.getTipo().equals("ADICIONAR_ITEM")) {

                        protagonista.getInventario()
                                .adicionarItemPorId(efeito.getValor());

                    } else if (efeito.getTipo().equals("REMOVER_ITEM")) {

                        protagonista.getInventario()
                                .removerItemPorId(efeito.getValor());
                    }
                }
            }
        }
    }
}
