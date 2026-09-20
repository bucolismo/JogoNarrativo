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
        // 1. Cria o protagonista
        Protagonista protagonista = criarProtagonista();

        // 2. Instancia os personagens da história
        Personagem narrador = new Personagem("Narrador", 0, "Neutro");
        NPC jonas = new NPC("Jonas", 30, "Masculino", 50);
        NPC daniel = new NPC("Daniel", 40, "Masculino", 50);

        // 3. Instancia o construtor de cenas
        ConstrutorDeCenas construtor = new ConstrutorDeCenas();

        // 4. Executa o loop principal da narrativa
        executarHistoria(construtor, protagonista, narrador, jonas, daniel, menu);
    }

    private Protagonista criarProtagonista() {
        String nome = menu.recebeNome();
        int idade = menu.recebeIdade();
        String genero = menu.recebeGenero();
        Protagonista protagonista = new Protagonista(nome, idade, genero);
        protagonista.cadastrarItens();
        return protagonista;
    }

    private void executarHistoria(ConstrutorDeCenas construtor, Protagonista protagonista, Personagem narrador, NPC jonas, NPC daniel, Menu menu) {
        int idCenaAtual = 1;
        int ultimaEscolha = 0;

        while (idCenaAtual > 0) {
            Cena cena = obterProximaCena(idCenaAtual, ultimaEscolha, construtor, narrador, protagonista, jonas, daniel);
            if (cena == null) {
                break;
            }
            ultimaEscolha = executaCena(cena, protagonista, menu);
            idCenaAtual++;
        }
    }

    private Cena obterProximaCena(int idCena, int opcaoEscolhida, ConstrutorDeCenas construtor, Personagem narrador, Protagonista protagonista, NPC jonas, NPC daniel) {
        switch (idCena) {
            case 1:
                return construtor.criarPrologo(narrador, protagonista);
            case 2:
                return construtor.criarAtoI(protagonista, narrador, jonas, daniel);
            case 3:
                return construtor.criarFinalAtoI(narrador, protagonista);
            case 4:
                return obterRotaDoAtoII(opcaoEscolhida, construtor, narrador, protagonista, daniel);
            default:
                return null;
        }
    }

    private Cena obterRotaDoAtoII(int opcaoEscolhida, ConstrutorDeCenas construtor, Personagem narrador, Protagonista protagonista, NPC daniel) {
        switch (opcaoEscolhida) {
            case 1: return construtor.criarAtoIIRota1A(narrador, protagonista);
            case 2: return construtor.criarAtoIIRota1B(narrador, protagonista, daniel);
            case 3: return construtor.criarAtoIIRota2A(narrador, protagonista);
            case 4: return construtor.criarAtoIIRota2B(narrador, protagonista);
            case 5: return construtor.criarAtoIIRota3A(narrador, protagonista, daniel);
            case 6: return construtor.criarAtoIIRota3B(narrador, protagonista);
            default: return null;
        }
    }

    private int executaCena(Cena cena, Protagonista protagonista, Menu menu) {
        if (cena == null || cena.getDialogos() == null) {
            return 0;
        }

        int ultimaEscolha = 0;

        for (Dialogo dialogo : cena.getDialogos()) {
            if (dialogo == null) {
                continue;
            }

            // Mostra o diálogo e captura a escolha (0 se não houver opções)
            int escolha = menu.mostraDialogo(dialogo);

            if (dialogo.possuiOpcoes()) {
                Escolha escolhaSelecionada;

                do {
                    // Usa o índice da escolha do jogador para pegar o objeto correto
                    escolhaSelecionada = dialogo.getEscolha(escolha);

                    // Verifica se possui o item requisito
                    int requisito = escolhaSelecionada.getRequisitoItem();
                    if (requisito != -1 && !protagonista.getInventario().possuiItemPorId(requisito)) {
                        menu.mostraString("Você não tem o item necessário para essa escolha.");
                        escolhaSelecionada = null;
                        escolha = menu.validaOpcao(); // pede nova escolha
                    }

                    // Verifica requisito de atributo
                    if (escolhaSelecionada != null && escolhaSelecionada.getRequisitoAtributo() != null) {
                        String atributo = escolhaSelecionada.getRequisitoAtributo();
                        int valorMinimo = escolhaSelecionada.getValorRequisitoAtributo();
                        if (protagonista.getAtributo(atributo) < valorMinimo) {
                            menu.mostraString("Você não possui o atributo necessário para essa escolha.");
                            escolhaSelecionada = null;
                            escolha = menu.validaOpcao();
                        }
                    }

                    // Verifica requisito de confiança com NPC
                    if (escolhaSelecionada != null && escolhaSelecionada.getRequisitoNpc() != null) {
                        NPC npc = escolhaSelecionada.getRequisitoNpc();
                        int confiancaMinima = escolhaSelecionada.getConfiancaMinima();
                        if (npc.getConfianca() < confiancaMinima) {
                            menu.mostraString("Você não possui confiança suficiente com " + npc.getNome() + ".");
                            escolhaSelecionada = null;
                            escolha = menu.validaOpcao();
                        }
                    }

                } while (escolhaSelecionada == null);

                if (escolhaSelecionada.getTextoConsequencia() != null) {
                    menu.mostraString(escolhaSelecionada.getTextoConsequencia());
                }

                // Aplica os efeitos da escolha selecionada
                for (Efeito efeito : escolhaSelecionada.getEfeitos()) {
                    switch (efeito.getTipo()) {
                        case "ATRIBUTO" ->
                                protagonista.alteraAtributo(efeito.getAlvo(), efeito.getValor());

                        case "CONFIANCA" ->
                                efeito.getNpc().alterarConfianca(efeito.getValor());

                        case "ADICIONAR_ITEM" ->
                                protagonista.getInventario().adicionarItemPorId(efeito.getValor());

                        case "REMOVER_ITEM" ->
                                protagonista.getInventario().removerItemPorId(efeito.getValor());

                        default ->
                                menu.mostraString("Tipo de efeito desconhecido: " + efeito.getTipo());
                    }
                }

                ultimaEscolha = escolha; // guarda a última escolha feita
            }
        }

        return ultimaEscolha;
    }

}
