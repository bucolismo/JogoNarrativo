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

    public Cena criarAtoIV() throws CenaInvalidaException {
        return carregar("ato4_a_verdade.json");
    }

    public Cena criarAtoVRota1A() throws CenaInvalidaException {
        return carregar("ato5_rota1a.json");
    }

    public Cena criarAtoVRota1B() throws CenaInvalidaException {
        return carregar("ato5_rota1b.json");
    }

    public Cena criarAtoVRota1C() throws CenaInvalidaException {
        return carregar("ato5_rota1c.json");
    }

    public Cena criarAtoVI() throws CenaInvalidaException {
        return carregar("ato6_o_preco.json");
    }

    public Cena criarFinalBalanca() throws CenaInvalidaException {
        return carregar("final1_balanca.json");
    }

    public Cena criarFinalNevoa() throws CenaInvalidaException {
        return carregar("final2_nevoa.json");
    }

    public Cena criarFinalSangue() throws CenaInvalidaException {
        return carregar("final3_sangue.json");
    }
}
