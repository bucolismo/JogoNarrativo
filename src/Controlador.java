/**
 * classe que controla o fluxo da execução do jogo
 *
 * faz a ponte entre o jogador (Menu), o estado da partida (EstadoPartida) e a persistência
 * (RepositorioDeSaves): inicia/continua/carrega partidas, executa as cenas de cada ato e
 * oferece os pontos de salvamento. As regras do jogo (requisitos, efeitos) ficam no Model.
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Controlador {

    // valor devolvido por executaCena quando uma escolha com efeito GAME_OVER é aplicada
    private static final int SENTINELA_GAME_OVER = -1;

    // na lista de saves do menu, a opção 4 representa o salvamento automático (slot 0)
    private static final int OPCAO_DO_SAVE_AUTOMATICO = RepositorioDeSaves.ULTIMO_SLOT_MANUAL + 1;

    private final Menu menu;
    private final RepositorioDeSaves repositorio;

    /**
     * o Controlador não cria suas dependências: quem monta as peças do jogo é a classe Partida
     * (e os testes), o que também permite trocar o Menu ou o local dos saves sem mexer aqui
     *
     * @param menu tela (entrada e saída de dados) usada para falar com o jogador
     * @param repositorio local de armazenamento dos saves
     */
    public Controlador(Menu menu, RepositorioDeSaves repositorio) {
        this.menu = menu;
        this.repositorio = repositorio;
    }

    /**
     * mostra e controla o menu principal do jogo
     *
     * aqui o jogador pode iniciar uma partida, continuar ou carregar uma partida salva,
     * ver as instruções, ver os créditos ou sair do jogo
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
                    continuarPartida();
                    break;

                case 3:
                    gerenciarSaves();
                    break;

                case 4:
                    menu.mostraInstrucoes();
                    break;

                case 5:
                    menu.mostraCreditos();
                    break;

                case 6:
                    menu.mostraSaida();
                    break;

            }

        } while (opcao != 6);
    }

    /**
     * inicia uma nova partida
     *
     * Cria o protagonista e o estado inicial da partida e depois executa a história
     */
    public void iniciarPartida() {
        Protagonista protagonista = criarProtagonista();

        executarHistoria(EstadoPartida.nova(protagonista));
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

    // ------------------------------------------------------------------ saves

    /**
     * retoma a partida do save mais recente (entre os slots manuais e o automático)
     */
    private void continuarPartida() {
        int slot = slotMaisRecente();

        if (slot == -1) {
            menu.mostraString("Não há partidas salvas para continuar.");
            return;
        }

        carregarEExecutar(slot);
    }

    /**
     * tela de saves do menu principal: lista os slots e permite carregar ou excluir
     */
    private void gerenciarSaves() {
        while (true) {
            int opcao = menu.mostraListaDeSaves("CARREGAR PARTIDA", descricaoDosSlots(true));

            if (opcao == 0) {
                return;
            }

            int slot = (opcao == OPCAO_DO_SAVE_AUTOMATICO) ? RepositorioDeSaves.SLOT_AUTOMATICO : opcao;

            if (!repositorio.existe(slot)) {
                menu.mostraString("Esse slot está vazio.");
                continue;
            }

            int acao = menu.escolheAcaoDoSave();

            if (acao == 1) {
                carregarEExecutar(slot);
                return;
            }

            if (acao == 2) {
                excluirComConfirmacao(slot);
            }
        }
    }

    private void carregarEExecutar(int slot) {
        EstadoPartida estado;

        try {
            estado = repositorio.carregar(slot);
        } catch (CarregamentoException e) {
            menu.mostraString("Não foi possível carregar a partida: " + e.getMessage());
            return;
        }

        menu.mostraString("Partida carregada.");
        executarHistoria(estado);
    }

    private void excluirComConfirmacao(int slot) {
        if (!menu.confirma("Deseja realmente excluir esse save? Essa ação não pode ser desfeita.")) {
            return;
        }

        try {
            repositorio.excluir(slot);
            menu.mostraString("Save excluído.");
        } catch (SaveException e) {
            menu.mostraString("Não foi possível excluir o save: " + e.getMessage());
        }
    }

    /**
     * @return o slot (0 a 3) do save mais recente, ou -1 se não houver nenhum save válido
     */
    private int slotMaisRecente() {
        int melhorSlot = -1;
        LocalDateTime melhorData = null;

        for (int slot = RepositorioDeSaves.SLOT_AUTOMATICO; slot <= RepositorioDeSaves.ULTIMO_SLOT_MANUAL; slot++) {
            try {
                MetadadosSave metadados = repositorio.lerMetadados(slot);

                if (metadados != null) {
                    LocalDateTime data = metadados.getDataHora();

                    if (melhorData == null || data.isAfter(melhorData)) {
                        melhorData = data;
                        melhorSlot = slot;
                    }
                }
            } catch (CarregamentoException e) {
                // save corrompido não entra na disputa pelo mais recente
            }
        }

        return melhorSlot;
    }

    /**
     * monta a descrição de cada slot para as telas de saves
     *
     * @param incluiAutomatico true para acrescentar o salvamento automático como última opção
     * @return uma linha por slot: slots manuais 1 a 3 e, se pedido, o automático
     */
    private List<String> descricaoDosSlots(boolean incluiAutomatico) {
        List<String> linhas = new ArrayList<>();

        for (int slot = RepositorioDeSaves.PRIMEIRO_SLOT_MANUAL; slot <= RepositorioDeSaves.ULTIMO_SLOT_MANUAL; slot++) {
            linhas.add(descricaoDoSlot(slot));
        }

        if (incluiAutomatico) {
            linhas.add("[Automático] " + descricaoDoSlot(RepositorioDeSaves.SLOT_AUTOMATICO));
        }

        return linhas;
    }

    private String descricaoDoSlot(int slot) {
        try {
            MetadadosSave metadados = repositorio.lerMetadados(slot);

            return (metadados == null) ? "(vazio)" : metadados.descricao();
        } catch (CarregamentoException e) {
            return "(save corrompido)";
        }
    }

    private void salvarAutomaticamente(EstadoPartida estado) {
        try {
            repositorio.salvar(RepositorioDeSaves.SLOT_AUTOMATICO, estado);
            menu.mostraString("[Progresso salvo automaticamente]");
        } catch (SaveException e) {
            menu.mostraString("Aviso: não foi possível salvar automaticamente. " + e.getMessage());
        }
    }

    private void salvarManualmente(EstadoPartida estado) {
        int slot = menu.mostraListaDeSaves("SALVAR PARTIDA", descricaoDosSlots(false));

        if (slot == 0) {
            return;
        }

        if (repositorio.existe(slot)
                && !menu.confirma("O slot " + slot + " já possui um save. Deseja sobrescrever?")) {
            return;
        }

        try {
            repositorio.salvar(slot, estado);
            menu.mostraString("Partida salva no slot " + slot + ".");
        } catch (SaveException e) {
            menu.mostraString("Não foi possível salvar a partida: " + e.getMessage());
        }
    }

    /**
     * ponto de salvamento: salva automaticamente e oferece o salvamento manual
     *
     * @return true se o jogador quer continuar a história, false se voltou ao menu principal
     */
    private boolean pontoDeSalvamento(EstadoPartida estado) {
        salvarAutomaticamente(estado);

        while (true) {
            int opcao = menu.mostraPontoDeSalvamento(estado.getAto().getRotulo());

            if (opcao == 1) {
                return true;
            }

            if (opcao == 2) {
                salvarManualmente(estado);
            } else if (menu.confirma("Voltar ao menu principal? Você poderá retomar daqui pelo salvamento automático.")) {
                return false;
            }
        }
    }

    // ---------------------------------------------------------------- história

    /**
     * executa a história a partir do ato em que o estado está (nova partida ou save carregado)
     *
     * se alguma cena não puder ser carregada (arquivo JSON ausente ou inválido), mostra o erro e
     * volta ao menu principal
     */
    private void executarHistoria(EstadoPartida estado) {
        try {
            rodarHistoria(estado);
        } catch (CenaInvalidaException e) {
            menu.mostraString("Erro ao carregar a história: " + e.getMessage());
        }
    }

    /**
     * em cada ato, entrega as cenas, executa e decide o próximo ato de acordo com as escolhas
     */
    private void rodarHistoria(EstadoPartida estado) throws CenaInvalidaException {
        ConstrutorDeCenas construtor = new ConstrutorDeCenas(estado);

        while (estado.getAto() != Ato.FIM) {

            if (estado.getAto().ehPontoDeSalvamento() && !pontoDeSalvamento(estado)) {
                return; // voltou ao menu principal
            }

            switch (estado.getAto()) {

                case PROLOGO: {
                    executaCena(construtor.criarPrologo(), estado); // sem escolhas relevantes para roteamento
                    estado.setAto(Ato.ATO_I);
                    break;
                }

                case ATO_I: {
                    executaCena(construtor.criarAtoI(), estado);
                    estado.setAto(Ato.FINAL_ATO_I);
                    break;
                }

                case FINAL_ATO_I: {
                    int escolha = executaCena(construtor.criarFinalAtoI(), estado);
                    estado.setRotaAtoII(escolha); // 1..6, mapeado 1:1 com as Escolhas do desfecho do Ato I
                    estado.setAto(Ato.ATO_II);
                    break;
                }

                case ATO_II: {
                    Cena cena = obterCenaDoAtoII(estado.getRotaAtoII(), construtor);
                    if (cena == null) { estado.setAto(Ato.FIM); break; }

                    int escolha = executaCena(cena, estado);

                    // Rotas 2B e 3B terminam em Game Over dentro do próprio arquivo
                    // (não têm Escolha nenhuma — só narração de captura/prisão).
                    if (estado.getRotaAtoII() == 4 || estado.getRotaAtoII() == 6 || escolha == SENTINELA_GAME_OVER) {
                        estado.setAto(Ato.FIM);
                    } else {
                        estado.setAto(Ato.ATO_III);
                    }
                    break;
                }

                case ATO_III: {
                    Cena cena = obterCenaDoAtoIII(estado.getRotaAtoII(), construtor);
                    if (cena == null) { estado.setAto(Ato.FIM); break; }

                    int escolha = executaCena(cena, estado);

                    // O ato IV é o ponto que centraliza toda a história
                    estado.setAto(escolha == SENTINELA_GAME_OVER ? Ato.FIM : Ato.ATO_IV);
                    break;
                }

                case ATO_IV: {
                    int escolha = executaCena(construtor.criarAtoIV(), estado);
                    // escolha reflete a Escolha Decisiva final (Cena 4 do Ato IV):
                    // 1=Razão->Rota1A, 2=Paranoia->Rota1B, 3=Violência->Rota1C, 4/sentinela=Game Over
                    if (escolha >= 1 && escolha <= 3) {
                        estado.setRotaAtoV(escolha);
                        estado.setAto(Ato.ATO_V);
                    } else {
                        estado.setAto(Ato.FIM); // opção 4 (discurso sem provas) ou GameOver de outra etapa da cena
                    }
                    break;
                }

                case ATO_V: {
                    Cena cena;
                    switch (estado.getRotaAtoV()) {
                        case 1: cena = construtor.criarAtoVRota1A(); break;
                        case 2: cena = construtor.criarAtoVRota1B(); break;
                        case 3: cena = construtor.criarAtoVRota1C(); break;
                        default: cena = null;
                    }
                    if (cena == null) { estado.setAto(Ato.FIM); break; }

                    int escolha = executaCena(cena, estado);

                    estado.setAto(escolha == SENTINELA_GAME_OVER ? Ato.FIM : Ato.ATO_VI);
                    break;
                }

                case ATO_VI: {
                    int escolha = executaCena(construtor.criarAtoVI(), estado);
                    // 1=Balança, 2=Névoa, 3=Sangue, 4/sentinela=Game Over ("O Silêncio de Santa Aurora")
                    if (escolha >= 1 && escolha <= 3) {
                        estado.setFinalEscolhido(escolha);
                        estado.setAto(Ato.FINAL);
                    } else {
                        estado.setAto(Ato.FIM);
                    }
                    break;
                }

                case FINAL: {
                    Cena cena;
                    switch (estado.getFinalEscolhido()) {
                        case 1: cena = construtor.criarFinalBalanca(); break;
                        case 2: cena = construtor.criarFinalNevoa(); break;
                        case 3: cena = construtor.criarFinalSangue(); break;
                        default: cena = null;
                    }
                    if (cena != null) {
                        executaCena(cena, estado); // epílogo puro, sem escolhas
                    }
                    estado.setAto(Ato.FIM);
                    break;
                }

                case FIM:
                    break;
            }
        }
    }

    private Cena obterCenaDoAtoII(int rota, ConstrutorDeCenas construtor) throws CenaInvalidaException {
        switch (rota) {
            case 1: return construtor.criarAtoIIRota1A();
            case 2: return construtor.criarAtoIIRota1B();
            case 3: return construtor.criarAtoIIRota2A();
            case 4: return construtor.criarAtoIIRota2B();
            case 5: return construtor.criarAtoIIRota3A();
            case 6: return construtor.criarAtoIIRota3B();
            default: return null;
        }
    }

    private Cena obterCenaDoAtoIII(int rotaAtoII, ConstrutorDeCenas construtor) throws CenaInvalidaException {
        switch (rotaAtoII) {
            case 1: return construtor.criarAtoIIIPerspectiva1A();
            case 2: return construtor.criarAtoIIIPerspectiva1B();
            case 3: return construtor.criarAtoIIIPerspectiva2A();
            case 5: return construtor.criarAtoIIIPerspectiva3A();
            default: return null; // 4 e 6 já terminaram no Ato II
        }
    }

    /**
     * controla a progressão da narrativa dentro de uma cena.
     *
     * mostra cada diálogo, pede a escolha do jogador, impede escolhas bloqueadas (a regra de
     * bloqueio fica no EstadoPartida), aplica os efeitos e registra a decisão tomada.
     *
     * @return o número da última escolha feita na cena (usado para decidir a próxima rota),
     *         0 se a cena não teve escolhas ou SENTINELA_GAME_OVER se uma escolha encerrou a partida
     */
    private int executaCena(Cena cena, EstadoPartida estado) {
        if (cena == null || cena.getDialogos() == null) {
            return 0;
        }

        int ultimaEscolha = 0;
        List<Dialogo> dialogos = cena.getDialogos();

        for (int i = 0; i < dialogos.size(); i++) {
            Dialogo dialogo = dialogos.get(i);

            if (dialogo == null) {
                continue;
            }

            // Mostra o diálogo e captura a escolha (0 se não houver opções)
            int escolha = menu.mostraDialogo(dialogo);

            if (dialogo.possuiOpcoes()) {
                Escolha escolhaSelecionada = dialogo.getEscolha(escolha);
                String motivo = estado.motivoDeBloqueio(escolhaSelecionada);

                while (motivo != null) {
                    menu.mostraString(motivo);
                    escolha = menu.validaOpcao(dialogo.getQuantidadeEscolhas()); // pede nova escolha
                    escolhaSelecionada = dialogo.getEscolha(escolha);
                    motivo = estado.motivoDeBloqueio(escolhaSelecionada);
                }

                if (escolhaSelecionada.getTextoConsequencia() != null) {
                    menu.mostraString(escolhaSelecionada.getTextoConsequencia());
                }

                estado.registrarDecisao(estado.getAto().name() + "#" + (i + 1) + "=" + escolha);

                // game over: a cena é interrompida na hora e o jogo volta para o menu
                if (estado.aplicarEfeitos(escolhaSelecionada)) {
                    return SENTINELA_GAME_OVER;
                }

                ultimaEscolha = escolha; // guarda a última escolha feita
            }
        }

        return ultimaEscolha;
    }
}
