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

        Personagem narrador = new Personagem("Narrador", 0, "Neutro");

        NPC npc1 = new NPC("Marcos", 30, "Masculino", 50);
        NPC npc2 = new NPC("Angela", 40, "Feminino", 50);
        NPC npc3 = new NPC("Henrique", 25, "Masculino", 50);
        NPC npc4 = new NPC("Lourdes", 35, "Feminino", 50);
        NPC npc5 = new NPC("Almeida", 50, "Masculino", 50);

        ConstrutorDeCenas construtor = new ConstrutorDeCenas();

        Cena prologo = construtor.criarPrologo(narrador, protagonista, npc1);

        executaCena(prologo, protagonista);

        testarRequisitoItem(narrador, protagonista);
        testarRequisitoAtributo(narrador, protagonista);
        testarRequisitoConfianca(narrador, protagonista, npc1);
    }

    private void testarRequisitoItem(Personagem narrador, Protagonista protagonista) {

        protagonista.getInventario().adicionarItemPorId(5);

        Escolha testeItem = new Escolha("1 - Abrir a porta com o pé de cabra.");
        testeItem.defineRequisitoItem(5);

        Cena cenaTesteItem = new Cena();

        cenaTesteItem.adicionaDialogo(
                new Dialogo(
                        narrador,
                        "Você encontra uma porta trancada.",
                        testeItem,
                        new Escolha("2 - Ir embora."),
                        new Escolha("3 - Observar a porta.")
                )
        );

        System.out.println("[Teste item] Possui pé de cabra: " +
                protagonista.getInventario().possuiItemPorId(5));

        executaCena(cenaTesteItem, protagonista);

        System.out.println("[Teste item] Possui pé de cabra depois: " +
                protagonista.getInventario().possuiItemPorId(5));
    }

    private void testarRequisitoAtributo(Personagem narrador, Protagonista protagonista) {

        Escolha testeAtributo = new Escolha("1 - Analisar cuidadosamente a porta.");
        testeAtributo.defineRequisitoAtributo("Razão", 60);

        Cena cenaTesteAtributo = new Cena();

        cenaTesteAtributo.adicionaDialogo(
                new Dialogo(
                        narrador,
                        "Você encontra uma porta trancada.",
                        testeAtributo,
                        new Escolha("2 - Ir embora."),
                        new Escolha("3 - Observar a porta.")
                )
        );

        executaCena(cenaTesteAtributo, protagonista);
    }

    public void executaCena(Cena cena, Protagonista protagonista) {

        for (Dialogo dialogo : cena.getDialogos()) {

            dialogo.executarDialogo();

            if (dialogo.possuiOpcoes()) {

                Escolha escolhaSelecionada;

                do {
                    int escolha = menu.recebeEscolha();

                    escolhaSelecionada = dialogo.getEscolha(escolha);

                    if (escolhaSelecionada == null) {
                        System.out.println("Escolha inválida.");
                        continue;
                    }

                    int requisito = escolhaSelecionada.getRequisitoItem();

                    if (requisito != -1 &&
                            !protagonista.getInventario().possuiItemPorId(requisito)) {

                        System.out.println("Você não tem o item necessário para essa escolha.");
                        escolhaSelecionada = null;
                    }

                    if (escolhaSelecionada != null &&
                            !permiteEscolherPorAtributo(escolhaSelecionada, protagonista)) {

                        System.out.println("Você não possui o atributo necessário para essa escolha.");
                        escolhaSelecionada = null;
                    }

                    if (escolhaSelecionada != null &&
                            !permiteEscolherPorConfianca(escolhaSelecionada)) {

                        System.out.println("Você não tem confiança suficiente para essa escolha.");
                        escolhaSelecionada = null;
                    }

                } while (escolhaSelecionada == null);

                System.out.println(escolhaSelecionada.getTexto());

                for (Efeito efeito : escolhaSelecionada.getEfeitos()) {

                    if (efeito.getTipo().equals("ATRIBUTO")) {
                        protagonista.alteraAtributo(efeito.getAlvo(), efeito.getValor());

                    } else if (efeito.getTipo().equals("CONFIANCA")) {
                        efeito.getNpc().alterarConfianca(efeito.getValor());

                    } else if (efeito.getTipo().equals("ADICIONAR_ITEM")) {
                        protagonista.getInventario().adicionarItemPorId(efeito.getValor());

                    } else if (efeito.getTipo().equals("REMOVER_ITEM")) {
                        protagonista.getInventario().removerItemPorId(efeito.getValor());
                    }
                }
            }
        }
    }

    private boolean permiteEscolherPorAtributo(Escolha escolha, Protagonista protagonista) {

        String atributo = escolha.getRequisitoAtributo();

        if (atributo == null) {
            return true;
        }

        int valorAtual = protagonista.getAtributo(atributo);
        int valorNecessario = escolha.getValorRequisitoAtributo();

        return valorAtual >= valorNecessario;
    }

    private boolean permiteEscolherPorConfianca(Escolha escolha) {

        NPC npc = escolha.getRequisitoNpc();

        if (npc == null) {
            return true;
        }

        return npc.getConfianca() >= escolha.getConfiancaMinima();
    }

    private void testarRequisitoConfianca(Personagem narrador, Protagonista protagonista, NPC npc) {

        Escolha testeConfianca =
                new Escolha("1 - Pedir ajuda para Marcos.");

        testeConfianca.defineRequisitoConfianca(npc, 60);

        Cena cenaTesteConfianca = new Cena();

        cenaTesteConfianca.adicionaDialogo(
                new Dialogo(
                        narrador,
                        "Marcos observa você em silêncio.",
                        testeConfianca,
                        new Escolha("2 - Ir embora."),
                        new Escolha("3 - Continuar observando.")
                )
        );

        System.out.println("[Teste confiança] Confiança atual: "
                + npc.getConfianca());

        System.out.println("[Teste confiança] Pode escolher: "
                + permiteEscolherPorConfianca(testeConfianca));

        npc.alterarConfianca(10);

        System.out.println("[Teste confiança] Confiança depois: "
                + npc.getConfianca());

        System.out.println("[Teste confiança] Pode escolher: "
                + permiteEscolherPorConfianca(testeConfianca));

        executaCena(cenaTesteConfianca, protagonista);
    }
}
