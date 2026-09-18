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
                    menu.mostraSaida();
                    break;
            }

        } while (opcao != 3);
    }

    public void iniciarPartida() {

        String nome = menu.recebeNome();
        int idade = menu.recebeIdade();
        String genero = menu.recebeGenero();

        Protagonista protagonista =
                new Protagonista(nome, idade, genero);

        // inicialização dos NPCs
        NPC npc1 = new NPC("Marcos", 30, "Masculino", 50);
        NPC npc2 = new NPC("Angela", 40, "Feminino", 50);
        NPC npc3 = new NPC("Henrique", 25, "Masculino", 50);
        NPC npc4 = new NPC("Lourdes", 35, "Feminino", 50);
        NPC npc5 = new NPC("Almeida", 50, "Masculino", 50);

        Cena prologo = new Cena();

        Personagem narrador =
                new Personagem(
                        "Narrador",
                        0,
                        "Neutro"
                );

        Escolha escolha1 =
                new Escolha(
                        "1 - Ir até o carro",
                        new Efeito("ATRIBUTO", "Paranoia", 3)
                );

        Escolha escolha2 =
                new Escolha(
                        "2 - Continuar andando",
                        new Efeito("ATRIBUTO", "Razão", 3)
                );

        Escolha escolha3 = new Escolha("3 - Desconfiar do NPC");

        escolha3.adicionaEfeito(
                new Efeito("ATRIBUTO", "Paranoia", -5)
        );

        escolha3.adicionaEfeito(
                new Efeito(npc1, -10)
        );

        // Escolha para testar confiança
        Escolha escolhaConfianca =
                new Escolha("4 - Desconfiar do NPC");

        escolhaConfianca.adicionaEfeito(
                new Efeito(npc1, -10)
        );

        prologo.adicionaDialogoSemOpcoes(
                narrador,
                """
                Silêncio.
                
                Por alguns segundos, nenhum som.
                Então, lentamente, começa a chover. Primeiro algumas gotas.
                Depois, uma chuva constante.
                
                Ao fundo, quase imperceptível, o som de carros passando
                sobre o asfalto molhado.
                """
        );

        prologo.adicionaDialogoSemOpcoes(
                narrador,
                """
                Uma cidade é vista de longe.
                O céu está encoberto.
                A chuva cai sobre os prédios.
                
                Uma fina camada de névoa cobre parte das ruas.
                """
        );

        prologo.adicionaDialogoSemOpcoes(
                protagonista,
                """
                Onde eu estou?
                """
        );

        // TESTE
        System.out.println(
                "Paranoia antes: "
                        + protagonista.getAtributo("Paranoia")
        );

        System.out.println(
                "Confiança do NPC 1 antes: "
                        + npc1.getConfianca()
        );

        prologo.adicionaDialogoComOpcoes(
                narrador,
                """
                Você olha ao redor. A rua está completamente vazia.
                Há um carro estacionado alguns metros à frente.
                """,
                escolha1,
                escolha2,
                escolha3
        );

        executaCena(prologo, protagonista);

        // TESTE
        System.out.println(
                "Paranoia depois: "
                        + protagonista.getAtributo("Paranoia")
        );

        System.out.println(
                "Confiança do NPC 1 depois: "
                        + npc1.getConfianca()
        );
    }

    public void executaCena(
            Cena cena,
            Protagonista protagonista
    ) {

        for (int i = 0; i < cena.getQuantidadeDialogos(); i++) {

            Dialogo dialogo = cena.getDialogo(i);

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

                    } else if (
                            efeito.getTipo().equals("CONFIANCA")
                    ) {

                        efeito.getNpc().alterarConfianca(
                                efeito.getValor()
                        );
                    }
                }
            }
        }
    }
}
