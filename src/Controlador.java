/**
 * classe que controla o fluxo da execução do jogo
 *
 * ela que inicializa os componentes de interação, inicia a partida,
 * cria o protagonista e NPCs também e rege a progressão da narrativa entre as cenas
 */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controlador {
    /**
     * mostra e controla o menu principal do jogo
     *
     *  aqui o jogador pode iniciar uma partida, ver as instruções,
     * ver os créditos ou sair do jogo
     */


    /**
     * inicia uma nova partida
     *
     * Cria o protagonista, os NPCs e também o construtor responsável pela criação das cenas,
     * e depois a execução da história/do jogo
     */
    public void iniciarPartida(Menu menu) {
        Protagonista protagonista = criarProtagonista(menu);

        Map<String, Personagem> elenco = new HashMap<>();

        elenco.put("narrador", new Personagem("Narrador", 0, "Neutro"));
        elenco.put("jonas", new NPC("Jonas", 30, "Masculino", 5));
        elenco.put("daniel", new NPC("Daniel", 40, "Masculino", 5));
        elenco.put("helena", new NPC("Helena", 23, "Feminino", 5));
        elenco.put("protagonista", protagonista);

        // Figurantes
        elenco.put("radio",new Personagem("Rádio", 0, "Informativo"));
        elenco.put("moradora",new Personagem("Moradora Local", 50, "Figurante"));
        elenco.put("policial",new Personagem("Policial Militar", 35, "Figurante"));
        elenco.put("jornaleiro",new Personagem("Jornaleiro", 60, "Figurante"));
        elenco.put("homemArmado",new NPC("Homem Armado", 44, "Antagonista", 5));
        elenco.put("agenteSeguranca",new NPC("Agente da Segurança", 42, "Antagonista", 5));
        elenco.put("segurancaPrivado",new NPC("Segurança Privado", 34, "Antagonista", 5));
        elenco.put("policialAntagonista",new NPC("Policial", 25, "Antagonista", 5));

        ConstrutorDeCenas construtor = new ConstrutorDeCenas(elenco);

        try {
            executarHistoria(construtor, elenco, menu);
        } catch (IOException | IllegalStateException e) {
            menu.mostraString("Erro ao carregar a história: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * cadastra o protagonista com as informações fornecidas pelo jogador
     *
     * faz o cadastro dos os itens disponíveis para o inventário do protagonista.
     *
     * @return retona o protagonista criado para a partida
     */

    private Protagonista criarProtagonista(Menu menu) {
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

    private void executarHistoria(ConstrutorDeCenas construtor, Map<String, Personagem> elenco, Menu menu) throws IOException {

        Protagonista protagonista = (Protagonista) elenco.get("protagonista");
        Personagem narrador = elenco.get("narrador");
        NPC jonas = (NPC) elenco.get("jonas");
        NPC daniel = (NPC) elenco.get("daniel");
        NPC helena = (NPC) elenco.get("helena");

        System.out.println(">>> executarHistoria iniciou");
        EstadoJogo estado = EstadoJogo.PROLOGO;
        int rotaAtoII = 0; // 1=1A, 2=1B, 3=2A, 4=2B(fim), 5=3A, 6=3B(fim)
        int rotaAtoV = 0;  // 1=RotaA, 2=RotaB, 3=RotaC

        while (estado != EstadoJogo.FIM) {
            System.out.println(">>> estado atual: " + estado);
            if(protagonista.getInventario().possuiItemPorId(-1)){
                estado= EstadoJogo.FIM;
            }
            switch (estado) {

                case PROLOGO: {
                    Cena cena = construtor.criarPrologo();
                    executaCena(cena, protagonista, menu);
                    estado = EstadoJogo.ATO_I;
                    break;
                }

                case ATO_I: {
                    Cena cena = construtor.criarAtoI();
                    executaCena(cena, protagonista, menu);
                    estado = EstadoJogo.FINAL_ATO_I;
                    break;
                }

                case FINAL_ATO_I: {
                    Cena cena = construtor.criarFinalAtoI();
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


                           }
        }
    }

    private Cena obterRotaDoAtoII(int opcaoEscolhida, ConstrutorDeCenas construtor, Personagem narrador, Protagonista protagonista, NPC daniel) throws IOException {
        switch (opcaoEscolhida) {
            case 1: return construtor.criarAtoIIRota1A();
            case 2: return construtor.criarAtoIIRota1B();
            case 3: return construtor.criarAtoIIRota2A();
            case 4: return construtor.criarAtoIIRota2B();
            case 5: return construtor.criarAtoIIRota3A();
            case 6: return construtor.criarAtoIIRota3B();

            default: return null;
        }
    }
    private Cena obterCenaDoAtoIII(int rotaAtoII, ConstrutorDeCenas construtor) throws IOException {
        switch (rotaAtoII) {
            case 1: return construtor.criarAtoIII1A();
            case 2: return construtor.criarAtoIII1B();
            case 3: return construtor.criarAtoIII2A();
            case 5: return construtor.criarAtoIII3A();
            default: return null; // 4 e 6 já terminaram no Ato II
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
