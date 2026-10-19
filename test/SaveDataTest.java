import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaveDataTest {

    private final Gson gson = new Gson();
    private EstadoPartida estado;

    @BeforeEach
    public void criaEstado() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.cadastrarItens();
        protagonista.alteraAtributo("Razão", 9);
        protagonista.alteraAtributo("Paranoia", 3);
        protagonista.alteraAtributo("Provas", 4);
        protagonista.getInventario().adicionarItemPorId(6);
        protagonista.getInventario().adicionarItemPorId(35);

        estado = EstadoPartida.nova(protagonista);
        estado.getJonas().alterarConfianca(20);
        estado.getDaniel().alterarConfianca(-30);
        estado.setAto(Ato.ATO_V);
        estado.setRotaAtoII(1);
        estado.setRotaAtoV(2);
        estado.registrarDecisao("ATO_I#2=1");
        estado.registrarDecisao("ATO_II#4=3");
    }

    private JsonObject jsonValido() {
        return gson.toJsonTree(SaveData.de(estado, 1)).getAsJsonObject();
    }

    private EstadoPartida restaura(JsonObject json) throws DadosInvalidosException {
        return gson.fromJson(json, SaveData.class).paraEstado();
    }

    @Test
    public void idaEVoltaDeveReproduzirOEstado() throws Exception {
        EstadoPartida restaurado = restaura(jsonValido());

        Protagonista p = restaurado.getProtagonista();
        assertEquals("Levi", p.getNome());
        assertEquals(23, p.getIdade());
        assertEquals("Masculino", p.getGenero());
        assertEquals(9, p.getAtributo("Razão"));
        assertEquals(3, p.getAtributo("Paranoia"));
        assertEquals(0, p.getAtributo("Violência"));
        assertEquals(4, p.getAtributo("Provas"));
        assertEquals(java.util.List.of(6, 35), p.getInventario().getIdsDosItensDisponiveis());
        assertEquals(70, restaurado.getJonas().getConfianca());
        assertEquals(20, restaurado.getDaniel().getConfianca());
        assertEquals(50, restaurado.getHelena().getConfianca());
        assertEquals(Ato.ATO_V, restaurado.getAto());
        assertEquals(1, restaurado.getRotaAtoII());
        assertEquals(2, restaurado.getRotaAtoV());
        assertEquals(estado.getDecisoes(), restaurado.getDecisoes());
    }

    @Test
    public void metadadosDevemDescreverOSave() throws Exception {
        MetadadosSave metadados = SaveData.de(estado, 2).getMetadados();

        assertEquals(2, metadados.getSlot());
        assertEquals("Levi", metadados.getNomeJogador());
        assertEquals("Ato V", metadados.getAto());
        assertTrue(metadados.getResumo().contains("Razão 9"));
        assertNotNull(metadados.getDataHora());
        assertTrue(metadados.descricao().contains("Levi"));
    }

    @Test
    public void versaoIncompativelDeveSerRejeitada() {
        JsonObject json = jsonValido();
        json.addProperty("versao", 99);

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void campoObrigatorioAusenteDeveSerRejeitado() {
        for (String campo : new String[]{"metadados", "nomeProtagonista", "atributos", "confiancas",
                "itens", "ato", "decisoes", "generoProtagonista"}) {
            JsonObject json = jsonValido();
            json.remove(campo);

            assertThrows(DadosInvalidosException.class, () -> restaura(json), "campo: " + campo);
        }
    }

    @Test
    public void atributoForaDoIntervaloDeveSerRejeitado() {
        JsonObject json = jsonValido();
        json.getAsJsonObject("atributos").addProperty("Razão", 101);

        assertThrows(DadosInvalidosException.class, () -> restaura(json));

        json.getAsJsonObject("atributos").addProperty("Razão", -1);
        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void atributoAusenteDeveSerRejeitado() {
        JsonObject json = jsonValido();
        json.getAsJsonObject("atributos").remove("Provas");

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void confiancaForaDoIntervaloDeveSerRejeitada() {
        JsonObject json = jsonValido();
        json.getAsJsonObject("confiancas").addProperty("Helena", 150);

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void itemInexistenteDeveSerRejeitado() {
        JsonObject json = jsonValido();
        json.getAsJsonArray("itens").add(9999);

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void atoInvalidoOuQueNaoEPontoDeSalvamentoDeveSerRejeitado() {
        for (String ato : new String[]{"ATO_XX", "FIM", "PROLOGO", "FINAL"}) {
            JsonObject json = jsonValido();
            json.addProperty("ato", ato);

            assertThrows(DadosInvalidosException.class, () -> restaura(json), "ato: " + ato);
        }
    }

    @Test
    public void rotasIncoerentesComOAtoDevemSerRejeitadas() {
        JsonObject json = jsonValido();
        json.addProperty("rotaAtoII", 0);
        assertThrows(DadosInvalidosException.class, () -> restaura(json));

        json.addProperty("rotaAtoII", 4); // rota que termina em game over no Ato II
        assertThrows(DadosInvalidosException.class, () -> restaura(json));

        json.addProperty("rotaAtoII", 1);
        json.addProperty("rotaAtoV", 0);
        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void idadeInvalidaDeveSerRejeitada() {
        JsonObject json = jsonValido();
        json.addProperty("idadeProtagonista", 0);

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }

    @Test
    public void dataInvalidaNosMetadadosDeveSerRejeitada() {
        JsonObject json = jsonValido();
        json.getAsJsonObject("metadados").addProperty("dataHora", "ontem");

        assertThrows(DadosInvalidosException.class, () -> restaura(json));
    }
}
