/**
 * classe que controla o fluxo da execução do jogo
 *
 * ela que inicializa os componentes de interação, inicia a partida,
 * cria o protagonista e NPCs também e rege a progressão da narrativa entre as cenas
 */

public class Controlador {

    private Menu menu;
    private Entrada entrada;

    public Controlador() {
        entrada = new Entrada();
        menu = new Menu(entrada);
    }
    
    /**
     * mostra e controla o menu principal do jogo
     *
     *  aqui o jogador pode iniciar uma partida, ver as instruções,
     * ver os créditos ou sair do jogo
     */
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
    
    /**
     * inicia uma nova partida
     *
     * Cria o protagonista, os NPCs e também o construtor responsável pela criação das cenas,
     * e depois a execução da história/do jogo
     */
    public void iniciarPartida() {
        // Cria o protagonista
        Protagonista protagonista = criarProtagonista();

        // Instancia os personagens da história
        Personagem narrador = new Personagem("Narrador", 0, "Neutro");
        NPC jonas = new NPC("Jonas", 30, "Masculino", 5);
        NPC daniel = new NPC("Daniel", 40, "Masculino", 5);
        NPC helena = new NPC("Helena",23,"Feminino",5);
        // Instancia o construtor de cenas
        ConstrutorDeCenas construtor = new ConstrutorDeCenas();

        // Executa o loop principal da narrativa
        executarHistoria(construtor, protagonista, narrador, jonas, daniel,helena, menu);
    }
    
    /**
     * cadastra o protagonista com as informações fornecidas pelo jogador
     *
     * faz o cadastro dos os itens disponíveis para o inventário do protagonista.
     *
     * @return retona o protagonista criado para a partida
     */

    private Protagonista criarProtagonista() {
        String nome = menu.recebeNome();
        int idade = menu.recebeIdade();
        String genero = menu.recebeGenero();
        Protagonista protagonista = new Protagonista(nome, idade, genero);
        protagonista.cadastrarItens();
        return protagonista;
    }

    private enum EstadoJogo {
        PROLOGO, ATO_I, FINAL_ATO_I, ATO_II, ATO_III, ATO_IV, ATO_V, ATO_VI, FINAL, FIM
    }

    private static final int SENTINELA_GAME_OVER = -1; // ver observação 1 acima

    private void executarHistoria(ConstrutorDeCenas construtor, Protagonista protagonista, Personagem narrador,
                                  NPC jonas, NPC daniel, NPC helena, Menu menu) {

        EstadoJogo estado = EstadoJogo.PROLOGO;
        int rotaAtoII = 0; // 1=1A, 2=1B, 3=2A, 4=2B(fim), 5=3A, 6=3B(fim)
        int rotaAtoV = 0;  // 1=RotaA, 2=RotaB, 3=RotaC

        while (estado != EstadoJogo.FIM) {
            if(protagonista.getInventario().possuiItemPorId(-1)){
                estado= EstadoJogo.FIM;
            }
            switch (estado) {

                case PROLOGO: {
                    Cena cena = construtor.criarPrologo(narrador, protagonista);
                    executaCena(cena, protagonista, menu); // sem escolhas relevantes para roteamento
                    estado = EstadoJogo.ATO_I;
                    break;
                }

                case ATO_I: {
                    Cena cena = construtor.criarAtoI(protagonista, narrador, jonas, daniel);
                    executaCena(cena, protagonista, menu);
                    estado = EstadoJogo.FINAL_ATO_I;
                    break;
                }

                case FINAL_ATO_I: {
                    Cena cena = construtor.criarFinalAtoI(narrador, protagonista);
                    int escolha = executaCena(cena, protagonista, menu);
                    rotaAtoII = escolha; // 1..6, mapeado 1:1 com as Escolhas de criarFinalAtoI
                    estado = EstadoJogo.ATO_II;
                    break;
                }

                case ATO_II: {
                    Cena cena = obterRotaDoAtoII(rotaAtoII, construtor, narrador, protagonista, daniel);
                    if (cena == null) { estado = EstadoJogo.FIM; break; }

                    int escolha = executaCena(cena, protagonista, menu);

                    // Rotas 2B e 3B terminam em Game Over dentro do próprio método
                    // (não têm Escolha nenhuma — só narração de captura/prisão).
                    if (rotaAtoII == 4 || rotaAtoII == 6 || escolha == SENTINELA_GAME_OVER) {
                        estado = EstadoJogo.FIM;
                    } else {
                        estado = EstadoJogo.ATO_III;
                    }
                    break;
                }

                case ATO_III: {
                    // NPCs de emboscada, só existem localmente
                    Cena cena;
                    switch (rotaAtoII) {
                        case 1: {
                            NPC homemArmado = new NPC("Homem Armado",44,"Antagonista",5);
                            cena = construtor.criarAtoIIIPerspectiva1A(narrador, protagonista, helena, homemArmado);
                            break;
                        }
                        case 2: {
                            NPC agenteSeguranca = new NPC("Agente da Segurança",42,"Antagonista",5);
                            cena = construtor.criarAtoIIIPerspectiva1B(narrador, protagonista, helena, agenteSeguranca);
                            break;
                        }
                        case 3: {
                            NPC segurancaPrivado = new NPC("Segurança Privado",34,"Antagonista",5);
                            cena = construtor.criarAtoIIIPerspectiva2A(narrador, protagonista, helena, segurancaPrivado);
                            break;
                        }
                        case 5: {
                            NPC policial = new NPC("Policial",25,"Antagonista",5);
                            cena = construtor.criarAtoIIIPerspectiva3A(narrador, protagonista, helena, policial);
                            break;
                        }
                        default:
                            cena = null; // não deveria ocorrer: 4 e 6 já terminaram no Ato II
                    }

                    if (cena == null) { estado = EstadoJogo.FIM; break; }

                    int escolha = executaCena(cena, protagonista, menu);

                    // O ato IV é o ponto que centraliza toda a história
                    estado = (escolha == SENTINELA_GAME_OVER) ? EstadoJogo.FIM : EstadoJogo.ATO_IV;
                    break;
                }

                case ATO_IV: {
                    Cena cena = construtor.criarAtoIV(narrador, protagonista, helena, daniel);
                    int escolha = executaCena(cena, protagonista, menu);
                    // escolha reflete a Escolha Decisiva final (Cena 4 do Ato IV):
                    
                    // 1=Razão->Rota1A, 2=Paranoia->Rota1B, 3=Violência->Rota1C, 4/sentinela=Game Over
                    if (escolha == 1) { rotaAtoV = 1; estado = EstadoJogo.ATO_V; }
                    else if (escolha == 2) { rotaAtoV = 2; estado = EstadoJogo.ATO_V; }
                    else if (escolha == 3) { rotaAtoV = 3; estado = EstadoJogo.ATO_V; }
                    else { estado = EstadoJogo.FIM; } // opção 4 (discurso sem provas) ou GameOver de outra etapa da cena
                    break;
                }

                case ATO_V: {
                    Cena cena;
                    switch (rotaAtoV) {
                        case 1: cena = construtor.criarAtoVRota1A(narrador, protagonista, helena, daniel); break;
                        case 2: cena = construtor.criarAtoVRota1B(narrador, protagonista, helena, daniel); break;
                        case 3: cena = construtor.criarAtoVRota1C(narrador, protagonista, helena, daniel); break;
                        default: cena = null;
                    }
                    if (cena == null) { estado = EstadoJogo.FIM; break; }

                    int escolha = executaCena(cena, protagonista, menu);
                    
                    estado = (escolha == SENTINELA_GAME_OVER) ? EstadoJogo.FIM : EstadoJogo.ATO_VI;
                    break;
                }

                case ATO_VI: {
                    Cena cena = construtor.criarAtoVI(narrador, protagonista, helena, daniel);
                    int escolha = executaCena(cena, protagonista, menu);
                    // 1=Balança, 2=Névoa, 3=Sangue, 4/sentinela=Game Over ("O Silêncio de Santa Aurora")
                    if (escolha == 1 || escolha == 2 || escolha == 3) {
                        rotaAtoV = escolha; // reaproveito a variável só para carregar o final escolhido
                        estado = EstadoJogo.FINAL;
                    } else {
                        estado = EstadoJogo.FIM;
                    }
                    break;
                }

                case FINAL: {
                    Cena cena;
                    switch (rotaAtoV) {
                        case 1: cena = construtor.criarFinalBalanca(narrador, protagonista, helena); break;
                        case 2: cena = construtor.criarFinalNevoa(narrador, protagonista, helena); break;
                        case 3: cena = construtor.criarFinalSangue(narrador, protagonista); break;
                        default: cena = null;
                    }
                    if (cena != null) {
                        executaCena(cena, protagonista, menu); // epílogo puro, sem escolhas
                    }
                    estado = EstadoJogo.FIM;
                    break;
                }

                case FIM:
                    break;
            }
        }
    }

    private Cena obterRotaDoAtoII(int opcaoEscolhida, ConstrutorDeCenas construtor, Personagem narrador, Protagonista protagonista, NPC daniel) {
        switch (opcaoEscolhida) {
            case 1: return construtor.criarAtoIIRota1A(narrador, protagonista, daniel);
            case 2: return construtor.criarAtoIIRota1B(narrador, protagonista, daniel);
            case 3: return construtor.criarAtoIIRota2A(narrador, protagonista);
            case 4: return construtor.criarAtoIIRota2B(narrador, protagonista);
            case 5: return construtor.criarAtoIIRota3A(narrador, protagonista, daniel);
            case 6: return construtor.criarAtoIIRota3B(narrador, protagonista);
            default: return null;
        }
    }
    
    /**
     * ciontrola a progressão da narrativa entre as cenas.
     *
     * recebe as cenas de acordo com a escolha realizada anteriormente,
     * executa cada cena e utiliza a última escolha para determinar
     * a próxima
     */
    
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
                        escolha = menu.validaOpcao(dialogo.getQuantidadeEscolhas());// pede nova escolha
                    }

                    // Verifica requisito de atributo
                    if (escolhaSelecionada != null && escolhaSelecionada.getRequisitoAtributo() != null) {
                        String atributo = escolhaSelecionada.getRequisitoAtributo();
                        int valorMinimo = escolhaSelecionada.getValorRequisitoAtributo();
                        if (protagonista.getAtributo(atributo) < valorMinimo) {
                            menu.mostraString("Você não possui o atributo necessário para essa escolha.");
                            escolhaSelecionada = null;
                            escolha = menu.validaOpcao(dialogo.getQuantidadeEscolhas());
                        }
                    }

                    // Verifica requisito de confiança com NPC
                    if (escolhaSelecionada != null && escolhaSelecionada.getRequisitoNpc() != null) {
                        NPC npc = escolhaSelecionada.getRequisitoNpc();
                        int confiancaMinima = escolhaSelecionada.getConfiancaMinima();
                        if (npc.getConfianca() < confiancaMinima) {
                            menu.mostraString("Você não possui confiança suficiente com " + npc.getNome() + ".");
                            escolhaSelecionada = null;
                            escolha = menu.validaOpcao(dialogo.getQuantidadeEscolhas());
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
                                protagonista.getInventario().adicionarItemPorId(efeito.getItemId());

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
