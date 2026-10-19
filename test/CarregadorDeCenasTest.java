import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CarregadorDeCenasTest {

    private Map<String, Personagem> elenco;
    private Map<String, Efeito> predefinidos;
    private NPC jonas;
    private Efeito aumentaRazao;

    @BeforeEach
    public void preparaElenco() {
        elenco = new HashMap<>();
        jonas = new NPC("Jonas", 30, "Masculino", 50);
        elenco.put("narrador", new Personagem("Narrador", 0, "Neutro"));
        elenco.put("jonas", jonas);

        aumentaRazao = new Efeito("ATRIBUTO", "Razão", 3);
        predefinidos = new HashMap<>();
        predefinidos.put("aumentaRazao", aumentaRazao);
    }

    private Cena monta(String json) throws CenaInvalidaException {
        return CarregadorDeCenas.montarCena(new StringReader(json), "teste.json", elenco, predefinidos);
    }

    private CenaInvalidaException erroAoMontar(String json) {
        return assertThrows(CenaInvalidaException.class, () -> monta(json));
    }

    private String cenaComEscolha(String escolhaJson) {
        return "{ \"id\": \"x\", \"dialogos\": [ { \"personagem\": \"narrador\", \"texto\": [\"Fala\"], "
                + "\"escolhas\": [ " + escolhaJson + " ] } ] }";
    }

    // ------------------------------------------------------------ casos válidos

    @Test
    public void deveMontarCenaComDialogosEEscolhas() throws Exception {
        Cena cena = monta("""
                { "id": "cena_teste",
                  "dialogos": [
                    { "personagem": "narrador", "texto": ["Linha 1", "Linha 2"] },
                    { "personagem": "jonas", "texto": ["Escolha algo"],
                      "escolhas": [
                        { "texto": ["[1] Primeira"], "resposta": ["Deu certo"],
                          "efeitos": [ {"tipo": "ATRIBUTO", "atributo": "Paranoia", "valor": 2} ] },
                        { "texto": ["[2] Segunda"] }
                      ] }
                  ] }
                """);

        assertEquals("cena_teste", cena.getCenaId());
        assertEquals(2, cena.getDialogos().size());

        Dialogo primeiro = cena.getDialogos().get(0);
        assertEquals("Linha 1\nLinha 2\n", primeiro.getTexto());
        assertFalse(primeiro.possuiOpcoes());

        Dialogo segundo = cena.getDialogos().get(1);
        assertEquals("Jonas", segundo.getPersonagem().getNome());
        assertEquals(2, segundo.getQuantidadeEscolhas());
        assertEquals("[1] Primeira\n", segundo.getEscolha(1).getTexto());
        assertEquals("Deu certo\n", segundo.getEscolha(1).getTextoConsequencia());
        assertNull(segundo.getEscolha(2).getTextoConsequencia());
        assertEquals("Paranoia", segundo.getEscolha(1).getEfeitos().get(0).getAlvo());
    }

    @Test
    public void deveMontarTodosOsTiposDeEfeito() throws Exception {
        Cena cena = monta(cenaComEscolha("""
                { "texto": ["[1] tudo"],
                  "efeitos": [
                    {"tipo": "PREDEFINIDO", "nome": "aumentaRazao"},
                    {"tipo": "ATRIBUTO", "atributo": "Violência", "valor": 4},
                    {"tipo": "RELACAO", "personagem": "jonas", "valor": -20},
                    {"tipo": "ITEM", "idItem": 6},
                    {"tipo": "GAME_OVER"} ] }
                """));

        var efeitos = cena.getDialogos().get(0).getEscolha(1).getEfeitos();

        assertEquals(5, efeitos.size());
        assertSame(aumentaRazao, efeitos.get(0));
        assertEquals(Efeito.TIPO_ATRIBUTO, efeitos.get(1).getTipo());
        assertEquals(4, efeitos.get(1).getValor());
        assertEquals(Efeito.TIPO_CONFIANCA, efeitos.get(2).getTipo());
        assertSame(jonas, efeitos.get(2).getNpc());
        assertEquals(-20, efeitos.get(2).getValor());
        assertEquals(Efeito.TIPO_ADICIONAR_ITEM, efeitos.get(3).getTipo());
        assertEquals(6, efeitos.get(3).getItemId());
        assertEquals(Efeito.TIPO_GAME_OVER, efeitos.get(4).getTipo());
    }

    @Test
    public void deveMontarOsRequisitosDaEscolha() throws Exception {
        Cena cena = monta(cenaComEscolha("""
                { "texto": ["[1] exigente"],
                  "requisitoItem": 6,
                  "requisitoAtributo": "Violência", "valorRequisitoAtributo": 7,
                  "requisitoNpc": "jonas", "confiancaMinima": 70 }
                """));

        Escolha escolha = cena.getDialogos().get(0).getEscolha(1);

        assertEquals(6, escolha.getRequisitoItem());
        assertEquals("Violência", escolha.getRequisitoAtributo());
        assertEquals(7, escolha.getValorRequisitoAtributo());
        assertSame(jonas, escolha.getRequisitoNpc());
        assertEquals(70, escolha.getConfiancaMinima());
    }

    @Test
    public void escolhaSemRequisitosNaoDeveExigirNada() throws Exception {
        Escolha escolha = monta(cenaComEscolha("{ \"texto\": [\"[1] livre\"] }")).getDialogos().get(0).getEscolha(1);

        assertEquals(-1, escolha.getRequisitoItem());
        assertNull(escolha.getRequisitoAtributo());
        assertNull(escolha.getRequisitoNpc());
    }

    // ------------------------------------------------------------ arquivos

    @Test
    public void recursoInexistenteDeveLancarExcecao() {
        CenaInvalidaException e = assertThrows(CenaInvalidaException.class,
                () -> CarregadorDeCenas.montarCena("/cenas/nao_existe.json", elenco, predefinidos));

        assertTrue(e.getMessage().contains("/cenas/nao_existe.json"));
    }

    @Test
    public void jsonMalformadoDeveLancarExcecao() {
        assertTrue(erroAoMontar("{ isso nao e json").getMessage().contains("teste.json"));
        assertNotNull(erroAoMontar("{ \"id\": 5, \"dialogos\": \"abc\" }"));
    }

    @Test
    public void jsonVazioDeveLancarExcecao() {
        erroAoMontar("");
    }

    // ------------------------------------------------------------ conteúdo inválido

    @Test
    public void cenaSemIdOuSemDialogosDeveSerRejeitada() {
        erroAoMontar("{ \"dialogos\": [ { \"personagem\": \"narrador\", \"texto\": [\"x\"] } ] }");
        erroAoMontar("{ \"id\": \"x\" }");
        erroAoMontar("{ \"id\": \"x\", \"dialogos\": [] }");
        erroAoMontar("{ \"id\": \"x\", \"dialogos\": [ null ] }");
    }

    @Test
    public void personagemDesconhecidoDeveSerRejeitadoEIndicarOLocal() {
        CenaInvalidaException e = erroAoMontar("""
                { "id": "x", "dialogos": [
                    { "personagem": "narrador", "texto": ["ok"] },
                    { "personagem": "fantasma", "texto": ["buu"] } ] }
                """);

        assertTrue(e.getMessage().contains("fantasma"));
        assertTrue(e.getMessage().contains("diálogo 2"));
    }

    @Test
    public void dialogoSemTextoDeveSerRejeitado() {
        erroAoMontar("{ \"id\": \"x\", \"dialogos\": [ { \"personagem\": \"narrador\" } ] }");
    }

    @Test
    public void escolhaSemTextoDeveSerRejeitada() {
        erroAoMontar(cenaComEscolha("{ \"efeitos\": [] }"));
    }

    @Test
    public void efeitoInvalidoDeveSerRejeitadoEIndicarOLocal() {
        CenaInvalidaException e = erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"VOAR\"} ] }"));

        assertTrue(e.getMessage().contains("VOAR"));
        assertTrue(e.getMessage().contains("diálogo 1, escolha 1"));

        erroAoMontar(cenaComEscolha("{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"valor\": 1} ] }"));
        erroAoMontar(cenaComEscolha("{ \"texto\": [\"[1] x\"], \"efeitos\": [ null ] }"));
    }

    @Test
    public void efeitoPredefinidoDesconhecidoDeveSerRejeitado() {
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"PREDEFINIDO\", \"nome\": \"naoExiste\"} ] }"));
    }

    @Test
    public void atributoInexistenteDeveSerRejeitado() {
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"ATRIBUTO\", \"atributo\": \"Sorte\", \"valor\": 1} ] }"));
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"requisitoAtributo\": \"Sorte\", \"valorRequisitoAtributo\": 3 }"));
    }

    @Test
    public void itemInexistenteNoCatalogoDeveSerRejeitado() {
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"ITEM\", \"idItem\": 9999} ] }"));
        erroAoMontar(cenaComEscolha("{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"ITEM\"} ] }"));
        erroAoMontar(cenaComEscolha("{ \"texto\": [\"[1] x\"], \"requisitoItem\": 9999 }"));
    }

    @Test
    public void idDeItemNegativoNaoDeveMaisServirDeGameOver() {
        // o antigo "item -1" foi substituído pelo efeito GAME_OVER
        erroAoMontar(cenaComEscolha("{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"ITEM\", \"idItem\": -1} ] }"));
    }

    @Test
    public void npcDesconhecidoOuQueNaoENpcDeveSerRejeitado() {
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"RELACAO\", \"personagem\": \"fantasma\", \"valor\": 1} ] }"));
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"efeitos\": [ {\"tipo\": \"RELACAO\", \"personagem\": \"narrador\", \"valor\": 1} ] }"));
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"requisitoNpc\": \"narrador\", \"confiancaMinima\": 10 }"));
    }

    @Test
    public void limitesForaDoIntervaloDevemSerRejeitados() {
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"requisitoNpc\": \"jonas\", \"confiancaMinima\": 101 }"));
        erroAoMontar(cenaComEscolha(
                "{ \"texto\": [\"[1] x\"], \"requisitoAtributo\": \"Razão\", \"valorRequisitoAtributo\": -1 }"));
    }
}
