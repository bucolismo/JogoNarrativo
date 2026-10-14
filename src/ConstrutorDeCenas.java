/**
 * é a classe responsável por entregar as cenas que compõem a narrativa do jogo
 *
 * As cenas do prólogo até o Ato III são carregadas de arquivos JSON (pasta resources/cenas) pelo
 * CarregadorDeCenas. Os Atos IV, V, VI e os finais ainda são montados aqui em Java, com os mesmos
 * princípios: cada método cria uma cena com seus diálogos, personagens e escolhas.
 *
 * O construtor recebe o estado da partida porque os efeitos e requisitos das escolhas apontam para
 * os mesmos objetos (protagonista e NPCs) que o jogo usa: assim, depois de carregar um save, as
 * cenas passam a agir sobre os NPCs restaurados.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstrutorDeCenas {

    // Confiança dos NPCs: escala de 0 a 100, todo NPC começa em 50 (neutro)
    public static final int CONFIANCA_INICIAL = 50;
    private static final int CONFIANCA_PARCERIA_BASICA = 70; // portão de acesso a favores pontuais

    private static final int LIMITE_CHECKPOINT_ATO_IV = 15; // portão da escolha decisiva do Ato IV
    private static final int LIMITE_FINAL_ATO_VI = 24;       // portão de atributo para os finais
    private static final int LIMITE_PROVAS_FINAL_RAZAO = 6;  // evidências mínimas para o final legal

    private static final String PASTA_DAS_CENAS = "/cenas/";

    private final Personagem narrador;
    private final Protagonista protagonista;
    private final NPC jonas;
    private final NPC daniel;
    private final NPC helena;

    // personagens que as cenas em JSON podem citar, pela chave usada nos arquivos
    private final Map<String, Personagem> elenco = new HashMap<>();
    // efeitos reaproveitados pelas cenas em JSON (campo "PREDEFINIDO")
    private final Map<String, Efeito> predefinidos = new HashMap<>();

    Efeito aumentaRazao = new Efeito("ATRIBUTO", "Razão", 3);
    Efeito aumentaParanioa = new Efeito("ATRIBUTO", "Paranoia", 3);
    Efeito aumentaViolencia = new Efeito("ATRIBUTO", "Violência", 3);

    Efeito ganhaProva = new Efeito("ATRIBUTO", "Provas", 1);

    /**
     * @param estado estado da partida cujos personagens as cenas vão usar
     */
    public ConstrutorDeCenas(EstadoPartida estado) {
        this.narrador = new Personagem("Narrador", 0, "Neutro");
        this.protagonista = estado.getProtagonista();
        this.jonas = estado.getJonas();
        this.daniel = estado.getDaniel();
        this.helena = estado.getHelena();

        elenco.put("narrador", narrador);
        elenco.put("protagonista", protagonista);
        elenco.put("jonas", jonas);
        elenco.put("daniel", daniel);
        elenco.put("helena", helena);

        // figurantes e antagonistas: só falam, não entram no save
        elenco.put("radio", new Personagem("Rádio", 0, "Informativo"));
        elenco.put("moradora", new Personagem("Moradora Local", 50, "Figurante"));
        elenco.put("policial", new Personagem("Policial Militar", 35, "Figurante"));
        elenco.put("jornaleiro", new Personagem("Jornaleiro", 60, "Figurante"));
        elenco.put("homemArmado", new NPC("Homem Armado", 44, "Antagonista", CONFIANCA_INICIAL));
        elenco.put("agenteSeguranca", new NPC("Agente da Segurança", 42, "Antagonista", CONFIANCA_INICIAL));
        elenco.put("segurancaPrivado", new NPC("Segurança Privado", 34, "Antagonista", CONFIANCA_INICIAL));
        elenco.put("policialAntagonista", new NPC("Policial", 25, "Antagonista", CONFIANCA_INICIAL));

        predefinidos.put("aumentaRazao", aumentaRazao);
        predefinidos.put("aumentaParanoia", aumentaParanioa);
        predefinidos.put("aumentaViolencia", aumentaViolencia);
        predefinidos.put("ganhaProva", ganhaProva);
    }

    // ---------------------------------------------------- cenas carregadas dos JSON

    private Cena carregar(String arquivo) throws CenaInvalidaException {
        return CarregadorDeCenas.montarCena(PASTA_DAS_CENAS + arquivo, elenco, predefinidos);
    }

    public Cena criarPrologo() throws CenaInvalidaException {
        return carregar("prologo.json");
    }

    public Cena criarAtoI() throws CenaInvalidaException {
        return carregar("ato_1.json");
    }

    public Cena criarFinalAtoI() throws CenaInvalidaException {
        return carregar("fim_ato_1.json");
    }

    public Cena criarAtoIIRota1A() throws CenaInvalidaException {
        return carregar("ato2_opcao1a.json");
    }

    public Cena criarAtoIIRota1B() throws CenaInvalidaException {
        return carregar("ato2_opcao1b.json");
    }

    public Cena criarAtoIIRota2A() throws CenaInvalidaException {
        return carregar("ato2_rota2a.json");
    }

    public Cena criarAtoIIRota2B() throws CenaInvalidaException {
        return carregar("ato2_rota2b.json");
    }

    public Cena criarAtoIIRota3A() throws CenaInvalidaException {
        return carregar("ato2_rota3a.json");
    }

    public Cena criarAtoIIRota3B() throws CenaInvalidaException {
        return carregar("ato2_rota3b.json");
    }

    public Cena criarAtoIIIPerspectiva1A() throws CenaInvalidaException {
        return carregar("ato3_perspectiva1a.json");
    }

    public Cena criarAtoIIIPerspectiva1B() throws CenaInvalidaException {
        return carregar("ato3_perspectiva1b.json");
    }

    public Cena criarAtoIIIPerspectiva2A() throws CenaInvalidaException {
        return carregar("ato3_perspectiva2a.json");
    }

    public Cena criarAtoIIIPerspectiva3A() throws CenaInvalidaException {
        return carregar("ato3_perspectiva3a.json");
    }

    // ------------------------------------------- cenas montadas em Java (ainda não migradas)

    public Cena criarAtoIV() {
        return criarAtoIV(narrador, protagonista, helena, daniel);
    }

    public Cena criarAtoVRota1A() {
        return criarAtoVRota1A(narrador, protagonista, helena, daniel);
    }

    public Cena criarAtoVRota1B() {
        return criarAtoVRota1B(narrador, protagonista, helena, daniel);
    }

    public Cena criarAtoVRota1C() {
        return criarAtoVRota1C(narrador, protagonista, helena, daniel);
    }

    public Cena criarAtoVI() {
        return criarAtoVI(narrador, protagonista, helena, daniel);
    }

    public Cena criarFinalBalanca() {
        return criarFinalBalanca(narrador, protagonista, helena);
    }

    public Cena criarFinalNevoa() {
        return criarFinalNevoa(narrador, protagonista, helena);
    }

    public Cena criarFinalSangue() {
        return criarFinalSangue(narrador, protagonista);
    }

    private Cena criarAtoIV(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {
        // ANTES: AdicionaStatus("Infiltração Silenciosa Concluída") — removido, flavor sem uso posterior
        Escolha escolhaAto4_1 = new Escolha("""
            [1] Usar a passagem técnica de manutenção para acessar o sistema de áudio e projeção, carregando a prova correspondente ao seu caminho para execução imediata.
            """, aumentaRazao, """
            Você se posiciona na cabine de controle. As provas colhidas no Ato III estão
            prontas para serem injetadas no sistema de som da câmara.
            """);

        // ANTES: AdicionaStatus("Ponto de Acesso Tomado no Aço") — removido
        Escolha escolhaAto4_2 = new Escolha("""
            [2] Aproveitar a troca de guarda das 05h30 para render o sentinela do subsolo e tomar o controle da entrada de emergência do plenário à força.
            """, aumentaViolencia, """
            Com um golpe certeiro, você neutraliza a segurança do acesso privado. O
            plenário está vulnerável a uma invasão direta.
            """);

        // ANTES: AdicionaStatus("Disfarce de Imprensa Ativo") — removido, Efeito(helena,1) já cobre o ganho real
        Escolha escolhaAto4_3 = new Escolha("""
            [3] Aguardar a chegada de Helena com a equipe da imprensa na porta lateral para entrar disfarçado entre os jornalistas e fotógrafos credenciados.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 10))), """
            Helena pisca para você e te entrega um crachá de imprensa reserva. Você passa
            pela barreira policial sem disparar um único alarme.
            """);

        // Mantido como está — decisão sem plano, Game Over intencional (sem via alternativa)
        Escolha escolhaAto4_4 = new Escolha("""
            [4] Tentar forçar as portas de bronze do plenário principal sem armas ou disfarce.
            """, Efeito.gameOver(), """
            
            A FALTA DE ESTRATÉGIA CUSTOU A SUA VIDA
            Os guardas municipais na entrada identificam você imediatamente. Cercado no
            saguão e sem cobertura, você é alvejado antes de alcançar os degraus.
            """);

        // ANTES: AdicionaStatus("Daniel Desarmado Politicamente") — removido
        Escolha escolhaAto4_5 = new Escolha("""
            [1] Usar a cobertura da imprensa trazida por Helena para expor publicamente a prova diante dos repórteres.
            """, aumentaRazao, """
            Os repórteres registram as provas em fotos e áudio. Daniel percebe que disparar
            diante das câmeras é suicídio e recua desarmado.
            """);

        // ANTES: AdicionaStatus("Daniel Imobilizado na Força") — removido
        Escolha escolhaAto4_6 = new Escolha("""
            [2] Sacar a arma e confrontar Daniel em um duelo direto antes que ele abra fogo.
            """, aumentaViolencia, """
            O espoco do tiro ecoa no corredor. Daniel é atingido no braço e larga a arma,
            caindo gemendo no chão de mármore.
            """);

        // ANTES: AdicionaStatus("Daniel Coagido a Cooperar") — removido
        Escolha escolhaAto4_7 = new Escolha("""
            [3] Usar as provas obtidas no Ato III para chantagear Daniel, oferecendo uma rota de fuga para ele caso ele entregue a cumplicidade do Prefeito na tribuna.
            """, aumentaParanioa, """
            Daniel avalia o peso das provas e percebe que foi abandonado pela prefeitura.
            DANIEL: "Se eu abrir aquela porta para você, você promete que o meu nome sai da manchete principal?"
            """);

        // Corrigido: antes, esta opção SEMPRE dava Game Over, ignorando a
        // confiança construída com Helena ao longo do jogo. Agora, se a
        // parceria for forte o suficiente, ela realmente intervém.
        // AdicionaStatus("Daniel Distraído por Helena") removido — flavor sem uso posterior.
        Escolha escolhaAto4_8_sucesso = new Escolha("""
            [4] Fazer um sinal para Helena — contando com a confiança que vocês construíram ao longo da noite — para que ela distraia Daniel.
            """, aumentaRazao, """
            Helena não hesita. Ela avança e derruba uma pilha de processos sobre o braço
            armado de Daniel, dando a você a fração de segundo necessária para desarmá-lo.

            * A noite inteira construindo confiança com ela valeu a pena.
            """);
        escolhaAto4_8_sucesso.defineRequisitoConfianca(helena, CONFIANCA_PARCERIA_BASICA);

        Escolha escolhaAto4_8_falha = new Escolha("""
            [4] Gritar por ajuda e torcer para que Helena arrisque a própria vida por você.
            """, Efeito.gameOver(), """
            SUA ALIADA NÃO CONFIAVA EM VOCÊ PARA ARRISCAR A VIDA
            Devido ao histórico de atritos entre vocês, Helena hesita — e a hesitação dá
            tempo para Daniel disparar no seu peito. Vocês nunca construíram confiança
            suficiente para isso.
            """);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1A...") — convertido, mantido como marcador de rota
        Escolha escolhaAto4_9 = new Escolha("""
            [1] Executar a gravação nas caixas de som/distribuir as fraudes para a mesa diretora, forçando a Polícia Federal a dar ordem de prisão ao Prefeito em plenário.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(35))), """
            As provas ecoam e circulam no plenário. Os agentes federais fecham as saídas e dão voz de prisão ao Prefeito diante de todas as câmeras.

            [Rota Ativada: Ato V — A Queda pelo Direito]
            """);
        escolhaAto4_9.defineRequisitoAtributo("RAZÃO", LIMITE_CHECKPOINT_ATO_IV);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1B...") — convertido
        Escolha escolhaAto4_10 = new Escolha("""
            [2] Vazar as cópias para a população no saguão e incitar a multidão a cercar a câmara, impedindo a saída dos corruptos e derrubando o governo municipal.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(36))), """
            A multidão em fúria rompe os bloqueios e cerca o prédio. O governo municipal desmorona sob o peso da insurreição popular.

            [Rota Ativada: Ato V — A Revolta da Cidade]
            """);
        escolhaAto4_10.defineRequisitoAtributo("PARANOIA", LIMITE_CHECKPOINT_ATO_IV);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1C...") — convertido
        Escolha escolhaAto4_11 = new Escolha("""
            [3] Invadir a tribuna de arma em punho, forçar o Prefeito a assinar a confissão da morte de Gabriel sob a mira do aço e executar sua vingança antes de fugir.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(37))), """
            Sob a mira do aço, o Prefeito assina o papel em pânico antes de ser derrubado. Vingança cumprida, você desaparece na névoa da cidade.

            [Rota Ativada: Ato V — A Sentença de Sangue]
            """);
        escolhaAto4_11.defineRequisitoAtributo("VIOLÊNCIA", LIMITE_CHECKPOINT_ATO_IV);

        // Mantido — Game Over intencional, tematicamente documentado no cabeçalho (nota 2)
        Escolha escolhaAto4_12 = new Escolha("""
            [4] Tentar impedir a votação apenas com um discurso moral, sem apresentar provas ou armas.
            """, Efeito.gameOver(),"""
            PALAVRAS SEM PROVAS NÃO DERRUBAM O PODER
            O Prefeito ignora suas palavras, bate o martelo e manda os guardas te levarem
            para o subsolo. Você é executado em segredo antes do fim do dia.

            (Nenhum dos seus atributos alcançou compromisso suficiente com uma linha de
            conduta clara — indecisão não muda uma cidade como Santa Aurora.)
            """);

        Cena atoIV = new Cena("ato4_a_verdade");

        atoIV.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO IV — A VERDADE (CONVERGÊNCIA UNIVERSAL DAS ROTAS DO ATO III)
        ===============================================================================

        [CENA 1: Subsolo da Câmara Municipal de Santa Aurora / 05h15]

        Não importa o caminho que você percorreu até aqui — todas as pistas apontam para
        o mesmo destino: a Sessão Extraordinária das 06h00.

        ===============================================================================
        CENA 2: A PREPARAÇÃO TÁTICA
        ===============================================================================
        """,
                escolhaAto4_1,
                escolhaAto4_2,
                escolhaAto4_3,
                escolhaAto4_4
        ));

        atoIV.adicionaDialogo(new Dialogo(daniel, """
        ===============================================================================
        PONTE NARRATIVA I — O CONFRONTO NO CORREDOR DOS VEREADORES / 05h45
        ===============================================================================

        DANIEL:
        "Eu sabia que você viria. Você não vai estragar a votação das seis da manhã."

        Ele empunha a arma. Passos apressados ecoam atrás de você: é Helena.
        """,
                escolhaAto4_5,
                escolhaAto4_6,
                escolhaAto4_7,
                escolhaAto4_8_sucesso,
                escolhaAto4_8_falha
        ));

        atoIV.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        PONTE NARRATIVA II — A ENTRADA NO PLENÁRIO / 06h00
        ===============================================================================

        O relógio do Palácio Municipal soa seis batidas graves. As portas duplas do
        plenário se abrem. O Prefeito ergue o martelo na tribuna.

        ===============================================================================
        CENA 4: A ESCOLHA DECISIVA DO ATO IV
        ===============================================================================
        """,
                escolhaAto4_9,
                escolhaAto4_10,
                escolhaAto4_11,
                escolhaAto4_12
        ));

        return atoIV;
    }

    // =============================================================================
    // ATO V — ROTA 1A: A QUEDA PELO DIREITO (Razão)
    // =============================================================================
    private Cena criarAtoVRota1A(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1A_1 = new Escolha("""
    [1] Apresentar a cadeia de custódia completa, dos arquivos do Cartório até o dossiê, e chamar Helena como testemunha da origem das provas.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 10))), """
    Você expõe cada etapa em ordem cronológica. Helena confirma sob juramento que o
    material passou pelo jornal antes de chegar à mesa. O argumento de prova ilícita
    perde o fôlego.
    """);

        // ANTES: AdicionaStatus("Servidores Apreendidos") — removido, sem uso posterior
        Escolha escolhaAto5_1A_2 = new Escolha("""
    [2] Exigir que a Polícia Federal apreenda os servidores da Câmara antes que aconteça uma "pane" conveniente nos registros.
    """, aumentaParanioa, """
    O delegado hesita, mas você aponta o técnico de informática suando junto à sala
    de máquinas. Minutos depois, encontram um script de exclusão programado.
    """);

        Escolha escolhaAto5_1A_3 = new Escolha("""
    [3] Interromper o advogado aos gritos e avançar até a tribuna para arrancar o martelo do Prefeito.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -10))), """
    Dois agentes federais seguram seus braços antes que você alcance a tribuna. O
    advogado sorri: agora ele tem o que precisava para te pintar como um agressor.
    """);

        // Mantido — passividade em julgamento, mesmo tipo de Game Over intencional do Ato IV
        Escolha escolhaAto5_1A_4 = new Escolha("""
    [4] Ficar em silêncio e deixar que a Polícia Federal decida sozinha o que fazer com as provas.
    """, Efeito.gameOver(), """
    
    A PROVA SEM DONO FOI ANULADA
    Sem ninguém para sustentar a origem do material, o juiz de plantão o anula. O
    Prefeito sai do plenário de cabeça erguida.
    """);

        // new Efeito(10) mantido como está — reutilização correta do mesmo ID do
        // "Diário do Irmão" (cadastrado originalmente em criarAtoIIRota2A). Rota
        // alternativa de recuperação do mesmo item, não uma colisão.
        Escolha escolhaAto5_1A_5 = new Escolha("""
    [1] Pedir que Helena entregue as cópias catalogadas do Diário de Gabriel que ela guardou no cofre do jornal e exigir uma perícia independente.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 10), new Efeito(10), ganhaProva)), """
    Helena tira do cofre do jornal a cópia integral do Diário de Gabriel. A perícia
    independente confirma a autenticidade da letra do seu irmão.

    (Você recuperou o Diário de Gabriel [ID: 10])
    """);

        // ANTES: AdicionaStatus("Proteção de Testemunha") — removido, sem uso posterior
        Escolha escolhaAto5_1A_6 = new Escolha("""
    [2] Exigir proteção de testemunha antes de depor: o Gabinete tem homens dentro da polícia.
    """, aumentaParanioa, """
    O delegado aceita, contrariado. Você é transferido para uma sala sem janelas com
    dois agentes de confiança dele.
    """);

        Escolha escolhaAto5_1A_7 = new Escolha("""
    [3] Depor com raiva e exigir a prisão imediata de Daniel e de todo o Gabinete, sem esperar o trâmite.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -20))), """
    O delegado pede calma duas vezes. Você bate na mesa na terceira. O depoimento
    registra que você "demonstrou hostilidade".
    """);

        Cena atoV1A = new Cena("ato5_rota1a");

        atoV1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A QUEDA PELO DIREITO (ROTA 1A)
===============================================================================

[CENA 1: Plenário da Câmara Municipal / 06h20]

O advogado do Prefeito se levanta, ajeita a gravata e sorri.

ADVOGADO:
"Excelência, tudo o que foi apresentado aqui foi obtido por invasão de imóvel público,
arrombamento de cartório e furto de arquivos oficiais. Nada disso é prova. É crime."
""",
                escolhaAto5_1A_1,
                escolhaAto5_1A_2,
                escolhaAto5_1A_3,
                escolhaAto5_1A_4
        ));

        atoV1A.adicionaDialogo(new Dialogo(helena, """
===============================================================================
CENA 2: Sala de Depoimentos da Polícia Federal / 08h00
===============================================================================

HELENA:
"O que você disser aqui vai ser lido por um juiz daqui a um ano. Não diga nada
que você não consiga provar."
""",
                escolhaAto5_1A_5,
                escolhaAto5_1A_6,
                escolhaAto5_1A_7
        ));

        atoV1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1A) — O PROCESSO COMEÇA
===============================================================================

O depoimento termina às 13h40. A notícia já chegou às ruas: "PREFEITO PRESO EM
PLENÁRIO". O processo será longo. Mas a prova existe, está catalogada e está
sob custódia federal.
"""));

        return atoV1A;
    }

    // =============================================================================
    // ATO V — ROTA 1B: A REVOLTA DA CIDADE (Paranoia)
    // =============================================================================
    private Cena criarAtoVRota1B(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1B_1 = new Escolha("""
    [1] Subir na escadaria de mármore e organizar a multidão em uma comissão pacífica, lendo em voz alta os nomes dos envolvidos.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 10))), """
    Sua voz atravessa o saguão. Um a um, os nomes são lidos. A multidão, que estava
    prestes a virar uma turba, se transforma em plateia.
    """);

        // ANTES: AdicionaStatus("Provocadores Identificados") — removido, sem uso posterior
        Escolha escolhaAto5_1B_2 = new Escolha("""
    [2] Observar a multidão e identificar os provocadores infiltrados pela Guarda Municipal para justificar uma repressão.
    """, aumentaParanioa, """
    Três homens de jaqueta preta distribuem pedras para quem está na frente. Você os
    aponta para a imprensa. Sem o pretexto, a Guarda Municipal recua.
    """);

        // ANTES: AdicionaStatus("Tumulto Descontrolado") — removido. Nota: o texto descreve
        // uma consequência real (provas pisoteadas, Secretário foge); hoje isso não afeta
        // nenhum atributo. Se quiser dar peso mecânico de verdade, o lugar certo seria um
        // Efeito("ATRIBUTO", "Provas", -1), não um status nunca lido.
        Escolha escolhaAto5_1B_3 = new Escolha("""
    [3] Incitar a multidão a invadir o gabinete do Prefeito e quebrar tudo o que encontrar.
    """, aumentaViolencia, """
    A multidão explode. Documentos que poderiam servir de prova são pisoteados junto
    com o resto. Na confusão, o Secretário de Governo escapa.
    """);

        // Mantido — abandono/passividade, mesmo tipo de Game Over intencional já visto
        Escolha escolhaAto5_1B_4 = new Escolha("""
    [4] Sair pela porta lateral e deixar a multidão sozinha, sem rosto nem liderança.
    """, Efeito.gameOver(), """
    UMA REVOLTA SEM ROSTO É ESMAGADA
    Sem ninguém para conduzi-la, a multidão é dispersa à força. O Gabinete afirma que
    foi um tumulto de vândalos.
    """);

        Escolha escolhaAto5_1B_5 = new Escolha("""
    [1] Exigir que a rádio comunitária leia a ata da sessão e os nomes oficiais envolvidos, sem tocar a gravação.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 10))), """
    O locutor lê a ata e os nomes em tom sóbrio. A rádio ganha credibilidade, mas não
    emociona ninguém.
    """);

        // new Efeito(11) mantido como está — reutilização correta do mesmo ID da
        // "Gravação de Gabriel" (cadastrada originalmente em criarAtoIIRota2A).
        Escolha escolhaAto5_1B_6 = new Escolha("""
    [2] Pedir ao operador da rádio a cópia da gravação que ele guardou e transmiti-la na frequência aberta para toda a cidade.
    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(11), ganhaProva)), """
    O operador tira de uma caixa de sapatos a cópia da fita que Gabriel deixou com ele
    semanas antes. A voz do seu irmão ecoa em todos os rádios de Santa Aurora.

    (Você recuperou a Gravação [ID: 11])
    """);

        // ANTES: AdicionaStatus("Secretário Capturado no Túnel") — removido, sem uso posterior
        Escolha escolhaAto5_1B_7 = new Escolha("""
    [3] Descer pelas galerias subterrâneas e capturar o Secretário de Governo à força antes que ele fuja da cidade.
    """, aumentaViolencia, """
    No fim de uma galeria úmida, você o alcança. Você o arrasta de volta à superfície
    pelo colarinho diante de dezenas de testemunhas.
    """);

        Cena atoV1B = new Cena("ato5_rota1b");

        atoV1B.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A REVOLTA DA CIDADE (ROTA 1B)
===============================================================================

[CENA 1: Saguão da Câmara e Praça da Matriz / 06h20]

As cópias vazadas passam de mão em mão. A cidade inteira parece prender o fôlego.
""",
                escolhaAto5_1B_1,
                escolhaAto5_1B_2,
                escolhaAto5_1B_3,
                escolhaAto5_1B_4
        ));

        atoV1B.adicionaDialogo(new Dialogo(helena, """
===============================================================================
CENA 2: Rádio Comunitária de Santa Aurora / 07h30
===============================================================================

OPERADOR DA RÁDIO:
"Gabriel me trouxe uma fita na semana em que morreu. Acho que agora não posso mais
ter medo."
""",
                escolhaAto5_1B_5,
                escolhaAto5_1B_6,
                escolhaAto5_1B_7
        ));

        atoV1B.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1B) — A CIDADE ACORDA
===============================================================================

Ao meio-dia, Santa Aurora parece outra cidade. O governo municipal ruiu sem que
ninguém tivesse dado um tiro de aviso: a maioria dos vereadores renunciou antes
do almoço.
"""));

        return atoV1B;
    }

    // =============================================================================
    // ATO V — ROTA 1C: A SENTENÇA DE SANGUE (Violência)
    // =============================================================================
    private Cena criarAtoVRota1C(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1C_1 = new Escolha("""
    [1] Usar a confissão assinada como escudo legal e exigir que Helena a fotografe e publique antes de qualquer passo seu.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 10))), """
    Helena fotografa a confissão com as mãos trêmulas e corre até o telefone público.
    Em dez minutos, a imagem está nas mãos de três redações.
    """);

        // ANTES: AdicionaStatus("Rastro Apagado") — removido, sem uso posterior
        Escolha escolhaAto5_1C_2 = new Escolha("""
    [2] Fugir pelas galerias subterrâneas e despistar a Guarda Municipal por um caminho que só você conhece.
    """, aumentaParanioa, """
    Você se move pelos túneis como se os tivesse construído. Os guardas perdem o seu
    rastro na terceira bifurcação.
    """);

        // ANTES: AdicionaStatus("Ferido a Bala") — removido, sem uso posterior
        // (por critério revisado, dano físico só ganha ID quando checado em algum
        // requisito posterior; se quiser um sistema de ferimentos com efeito real
        // no jogo, isso pede uma mecânica dedicada, não um "status" solto)
        Escolha escolhaAto5_1C_3 = new Escolha("""
    [3] Abrir caminho à bala pelo corredor central e sair pela porta principal diante de todos.
    """, aumentaViolencia, """
    Você abre caminho a tiros. Um deles o atinge de raspão no ombro, mas os guardas
    recuam diante de alguém que não tem mais nada a perder.
    """);

        // Mantido — rendição/passividade, mesmo padrão de Game Over intencional já visto
        Escolha escolhaAto5_1C_4 = new Escolha("""
    [4] Baixar a arma e se render aos guardas para "explicar tudo com calma".
    """, Efeito.gameOver(), """
    
    A CONFISSÃO FOI CASSADA E VOCÊ TAMBÉM
    A confissão, feita sob a mira de uma arma, é declarada nula em minutos. Você é
    preso por sequestro e tentativa de homicídio.
    """);

        Escolha escolhaAto5_1C_5 = new Escolha("""
    [1] Ligar para Daniel de um telefone público e propor uma troca: ele entrega o laudo original em troca de tempo para fugir.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 10))), """
    Daniel escuta em silêncio. Depois diz apenas: "Cinco minutos." Ele deixa o laudo
    original na caixa de correio da delegacia.
    """);

        Escolha escolhaAto5_1C_6 = new Escolha("""
    [2] Esperar escondido a saída de Daniel e segui-lo para descobrir se ele guarda as provas originais em algum lugar.
    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -10))), """
    Daniel sai da delegacia, olha para os lados e pega um caminho torto até a casa
    de Gabriel. Ele entra e não sai mais.
    """);

        // new Efeito(8) mantido como está — reutilização correta do "Laudo Adulterado",
        // cadastrado originalmente no Ato I.
        Escolha escolhaAto5_1C_7 = new Escolha("""
    [3] Arrombar o armário de evidências da delegacia, recuperar o laudo original adulterado e queimar o resto.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(8), ganhaProva)), """
    A fechadura cede na segunda pancada. Entre dezenas de envelopes, você encontra o
    laudo original, com as rasuras que você reconheceria em qualquer lugar.

    (Você recuperou o Laudo Adulterado [ID: 8])
    """);

        Cena atoV1C = new Cena("ato5_rota1c");

        atoV1C.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A SENTENÇA DE SANGUE (ROTA 1C)
===============================================================================

[CENA 1: Tribuna da Câmara Municipal / 06h10]

O Prefeito assina o papel com a mão trêmula, o cano da arma encostado no peito.
Vingança feita? A palavra pesa mais do que você imaginava.
""",
                escolhaAto5_1C_1,
                escolhaAto5_1C_2,
                escolhaAto5_1C_3,
                escolhaAto5_1C_4
        ));

        atoV1C.adicionaDialogo(new Dialogo(daniel, """
===============================================================================
CENA 2: Delegacia Central, Armário de Evidências / 07h40
===============================================================================

DANIEL:
"Eu sabia que você viria aqui. Seu irmão me deixou uma carta antes de morrer, e nela
dizia que, se você chegasse até aqui, eu devia deixar você passar."
""",
                escolhaAto5_1C_5,
                escolhaAto5_1C_6,
                escolhaAto5_1C_7
        ));

        atoV1C.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1C) — O PREÇO DA SENTENÇA
===============================================================================

Às 09h00, você está escondido no depósito de uma padaria fechada, com o ombro
latejando. A cidade inteira o procura. O Prefeito foi preso. A confissão foi
divulgada. Mas nada disso apaga o fato de que você apontou uma arma para um
homem em plenário.
"""));

        return atoV1C;
    }

    // =============================================================================
    // ATO VI — O PREÇO DE SANTA AURORA (CONVERGÊNCIA E ESCOLHA DO FINAL)
    // =============================================================================
    private Cena criarAtoVI(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        // FINAL 1 — A BALANÇA (Razão + Item 10 + Provas mínimas)
        // ANTES: AdicionaStatus("Libera Final 1 — A Balança") — removido. A própria
        // Escolha já direciona para criarFinalBalanca(); o status nunca é relido.
        Escolha escolhaAto6_1 = new Escolha("""
    [1] Colocar o diário de Gabriel sobre o túmulo e prometer que a verdade será levada até o fim nos tribunais, por mais que o processo demore.
    """, aumentaRazao, """
    Você deposita o diário sobre a lápide, ainda molhada da chuva. Não há vingança
    nem multidão. Só a promessa lenta e teimosa de que cada nome escrito naquelas
    páginas será chamado a responder diante de um juiz.

    [FINAL 1 — A BALANÇA: A verdade prevalece pelo caminho da lei]
    """);
        escolhaAto6_1.defineRequisitoAtributo("RAZÃO", LIMITE_FINAL_ATO_VI);
        escolhaAto6_1.defineRequisitoItem(10);

        // FINAL 2 — A NÉVOA SE LEVANTA (Paranoia + Item 11)
        // ANTES: AdicionaStatus("Libera Final 2...") — removido, mesmo motivo
        Escolha escolhaAto6_2 = new Escolha("""
    [2] Tocar a gravação de Gabriel no rádio do cemitério e deixar que a cidade inteira ouça a última mensagem dele, mesmo sabendo que a rede é maior do que se vê.
    """, aumentaParanioa, """
    A voz do seu irmão atravessa o cemitério, sobe os morros e chega às janelas
    de Santa Aurora. A névoa, pela primeira vez, começa a se levantar de verdade.

    [FINAL 2 — A NÉVOA SE LEVANTA: a cidade desperta, e você não está mais sozinho]
    """);
        escolhaAto6_2.defineRequisitoAtributo("PARANOIA", LIMITE_FINAL_ATO_VI);
        escolhaAto6_2.defineRequisitoItem(11);

        // FINAL 3 — O PREÇO DO SANGUE (Violência + Item 8)
        // ANTES: AdicionaStatus("Libera Final 3...") — removido, mesmo motivo
        Escolha escolhaAto6_3 = new Escolha("""
    [3] Queimar o laudo adulterado diante do túmulo, empunhar a arma e partir antes que a polícia chegue, sabendo que agora você é o procurado.
    """, aumentaViolencia, """
    O laudo queima em segundos, junto com a última mentira oficial sobre a morte
    de Gabriel. Você guarda a arma sem olhar para trás.

    [FINAL 3 — O PREÇO DO SANGUE: a vingança cobra o seu preço]
    """);
        escolhaAto6_3.defineRequisitoAtributo("VIOLÊNCIA", LIMITE_FINAL_ATO_VI);
        escolhaAto6_3.defineRequisitoItem(8);

        // Mantido — abandono/passividade, mesmo padrão de Game Over intencional
        Escolha escolhaAto6_4 = new Escolha("""
    [4] Virar as costas ao túmulo, pegar o primeiro ônibus e deixar Santa Aurora sem olhar para trás.
    """, Efeito.gameOver(), """
    
    O SILÊNCIO DE SANTA AURORA
    O ônibus parte na chuva fina. Pelo vidro molhado, a cidade encolhe. Ninguém
    fala do que aconteceu no plenário, e em uma semana ninguém mais lembra de
    você. A névoa cobre a cidade como sempre cobriu.

    (Nenhuma das três causas foi levada longe o suficiente. Talvez, numa outra
    tentativa, você escolha se comprometer de verdade com uma delas.)
    """);

        Cena atoVI = new Cena("ato6_o_preco");

        atoVI.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO VI — O PREÇO DE SANTA AURORA (CONVERGÊNCIA DAS ROTAS DO ATO V)
===============================================================================

[CENA: Cemitério de Santa Aurora / Fim de Tarde / 17h30]

Não importa o caminho: pela lei, pela revolta ou pelo aço, todos terminam aqui.
O cemitério fica no alto do morro. De lá, dá para ver a cidade inteira, com as
luzes da Prefeitura já apagadas.

O túmulo de Gabriel é simples: uma lápide de concreto, o nome gravado à mão e um
vaso de flores murchas. Você tira do bolso o medalhão de bronze, o mesmo que ele
lhe deu na infância, e o coloca sobre a pedra molhada.

> VOZ DA RAZÃO:
> "Você tem provas, tem registro, tem uma história que pode ser contada com cuidado."

> VOZ DA CONSPIRAÇÃO:
> "A cidade acordou, mas a rede continua lá. Uma voz vale mais que mil papéis."

> VOZ DA VIOLÊNCIA:
> "Você prometeu que ele seria vingado. Não deixe pela metade."

===============================================================================
A ESCOLHA FINAL — O QUE VOCÊ FAZ COM O QUE SOBROU?
===============================================================================
""",
                escolhaAto6_1,
                escolhaAto6_2,
                escolhaAto6_3,
                escolhaAto6_4
        ));

        return atoVI;
    }

    // =============================================================================
    // FINAL 1 — A BALANÇA (Razão)
    // O final "correto", mas deliberadamente incompleto: recompensa cumprir a
    // lei, mas nega o fechamento emocional. A intenção é deixar o jogador
    // sentindo que faltou alguma coisa — e com vontade de tentar de novo.
    // =============================================================================
    private Cena criarFinalBalanca(Personagem narrador, Protagonista protagonista, NPC helena) {
        Cena finalBalanca = new Cena("final1_balanca");

        finalBalanca.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 1 — A BALANÇA
===============================================================================

[CENA: Fórum Federal de Santa Aurora / Onze meses depois]

O julgamento dura quatro semanas. O Prefeito é condenado a trinta e dois anos.
O Secretário de Governo, a vinte e um. Mas o Vice-Prefeito e o Tabelião Chefe
fecham acordo de delação premiada e cumprem pena em regime domiciliar antes do
fim do ano — a lei que você tanto defendeu também protege quem sabe negociar
com ela.
"""));

        finalBalanca.adicionaDialogo(new Dialogo(helena, """
===============================================================================
EPÍLOGO — O QUE SOBRA DEPOIS DA SENTENÇA
===============================================================================

Helena publica a série completa. O jornal recebe assinaturas de todo o estado,
mas ela raramente responde suas mensagens agora — o processo consumiu meses da
vida dela também, e algo entre vocês esfriou no meio das audiências.

HELENA (por telefone, distante):
"Você fez a coisa certa. Isso devia ser o suficiente, não devia?"

Você não sabe responder. Fica na cidade por obrigação, não por escolha — testemunha
de acusação em processos que ainda vão levar anos. Toda sexta-feira sobe ao
cemitério sozinho e troca as flores do túmulo de Gabriel. Nunca chorou por ele,
não de verdade; estava ocupado demais construindo um caso.

Santa Aurora seguiu em frente. Os jornais pararam de falar do assunto depois do
terceiro mês. Você conseguiu a justiça que prometeu, ponto por ponto, prova por
prova — e mesmo assim, sentado sozinho na sala vazia do apartamento alugado,
não consegue deixar de se perguntar se venceu o caso certo, do jeito errado.

===============================================================================
FIM — [FINAL 1: A BALANÇA]
===============================================================================
"""));

        return finalBalanca;
    }

    // =============================================================================
    // FINAL 2 — A NÉVOA SE LEVANTA (Paranoia)
    // O final mais satisfatório: vigilância reenquadrada como cuidado, não
    // isolamento. Vitória coletiva, aliança mantida.
    // =============================================================================
    private Cena criarFinalNevoa(Personagem narrador, Protagonista protagonista, NPC helena) {
        Cena finalNevoa = new Cena("final2_nevoa");

        finalNevoa.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 2 — A NÉVOA SE LEVANTA
===============================================================================

[CENA: Praça da Matriz / Seis meses depois]

A voz de Gabriel foi tocada tantas vezes que virou trilha de protesto. Na praça,
crianças repetem o refrão "Se alguém está ouvindo isso..." sem saber exatamente
de onde vem. Bairros inteiros se organizaram em associações de vigilância
comunitária — não por medo, mas por cuidado uns com os outros. O governo
municipal foi substituído por uma junta provisória de moradores, e pela primeira
vez em anos as reuniões da Câmara são abertas e transmitidas ao vivo pela rádio
comunitária.
"""));

        finalNevoa.adicionaDialogo(new Dialogo(helena, """
===============================================================================
EPÍLOGO — A CIDADE QUE APRENDEU A OUVIR
===============================================================================

Helena virou editora-chefe do jornal, agora com uma redação de verdade — mesa
para seis repórteres, financiada por assinantes de toda a região. Ela te chamou
para ser o correspondente de investigações locais, um trabalho que parece ter
sido desenhado sob medida para a pessoa que você se tornou.

HELENA:
"Você continua olhando por cima do ombro. Mas agora tem gente olhando com você.
Isso conta como progresso, sabia?"

Você sorri, de verdade, pela primeira vez desde que voltou a Santa Aurora. Na
parede do seu apartamento — que já não é mais provisório —, o velho mapa com
linhas vermelhas foi substituído por fotos: Helena na redação, o pessoal da
associação de bairro, uma foto do túmulo de Gabriel com flores frescas.

Você não parou de ser vigilante. Mas a vigilância deixou de ser solidão para
virar comunidade. Ainda existem nomes no seu mapa mental que a justiça não
alcançou — mas agora você tem uma cidade inteira ajudando a procurar, e isso,
descobre você, é o mais parecido com paz que alguém como você consegue ter.

Santa Aurora acordou. E, pela primeira vez, você acordou junto com ela.

===============================================================================
FIM — [FINAL 2: A NÉVOA SE LEVANTA]
===============================================================================
"""));

        return finalNevoa;
    }

    // =============================================================================
    // FINAL 3 — O PREÇO DO SANGUE (Violência)
    // Final deliberadamente "mediano": justiça real, mas custo pessoal
    // concreto e ambíguo. Nem tragédia total, nem vitória limpa.
    // =============================================================================
    private Cena criarFinalSangue(Personagem narrador, Protagonista protagonista) {
        Cena finalSangue = new Cena("final3_sangue");

        finalSangue.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 3 — O PREÇO DO SANGUE
===============================================================================

[CENA: Rodovia Estadual, saindo de Santa Aurora / Noite]

A caminhonete que você comprou com o resto do dinheiro percorre a estrada de
terra sob uma chuva fina. No banco do passageiro, o medalhão de bronze balança
com o movimento. No retrovisor, as luzes da cidade encolhem.

Santa Aurora o chama de herói ou de assassino, dependendo de quem responde. O
Prefeito está preso. O laudo adulterado virou cinzas. Mas o mandado de prisão
está em vigor e o seu retrato circula em todas as delegacias do estado.
"""));

        finalSangue.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
EPÍLOGO — O QUE FICOU PARA TRÁS
===============================================================================

O rádio da caminhonete sintoniza uma estação de outra cidade. Uma locutora fala
sobre um "vigilante armado que invadiu a Câmara Municipal de Santa Aurora" — e
depois, num tom mais sóbrio, sobre um Prefeito preso por corrupção e homicídio.
As duas notícias são a mesma história, contada por gente diferente.

Você não se arrepende de ter feito o Prefeito pagar. Também não se orgulha do
que precisou fazer para chegar até ele. Gabriel, pensa você, jamais teria
apontado a arma — mas Gabriel também não teria sobrevivido ao que você viveu
em uma única madrugada, e pelo menos os nomes na parede da prefeitura hoje
significam alguma coisa para os cinco mortos que vieram antes dele.

Na próxima cidade, você vai usar outro nome. Vai aprender a dormir de olhos
abertos, sem Helena, sem Daniel, sem ninguém que saiba o que você fez ou por
quê. A justiça foi feita — só não sobrou ninguém para comemorar com você.

===============================================================================
FIM — [FINAL 3: O PREÇO DO SANGUE]
===============================================================================
"""));

        return finalSangue;
    }
}
