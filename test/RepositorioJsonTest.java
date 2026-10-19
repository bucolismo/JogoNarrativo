import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioJsonTest {

    @TempDir
    Path pasta;

    private RepositorioJson repositorio;
    private EstadoPartida estado;

    @BeforeEach
    public void prepara() {
        repositorio = new RepositorioJson(pasta.resolve("saves"));

        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.cadastrarItens();
        protagonista.alteraAtributo("Violência", 12);
        protagonista.getInventario().adicionarItemPorId(8);

        estado = EstadoPartida.nova(protagonista);
        estado.setAto(Ato.ATO_II);
        estado.setRotaAtoII(3);
        estado.registrarDecisao("ATO_I#7=3");
    }

    @Test
    public void slotVazioNaoDeveExistirENaoTemMetadados() throws Exception {
        assertFalse(repositorio.existe(1));
        assertNull(repositorio.lerMetadados(1));
    }

    @Test
    public void deveSalvarECarregarNosTresSlotsEnoAutomatico() throws Exception {
        for (int slot = 0; slot <= 3; slot++) {
            repositorio.salvar(slot, estado);

            assertTrue(repositorio.existe(slot));

            EstadoPartida carregado = repositorio.carregar(slot);
            assertEquals(Ato.ATO_II, carregado.getAto());
            assertEquals(12, carregado.getProtagonista().getAtributo("Violência"));
            assertTrue(carregado.getProtagonista().getInventario().possuiItemPorId(8));
            assertEquals(3, carregado.getRotaAtoII());
            assertEquals(java.util.List.of("ATO_I#7=3"), carregado.getDecisoes());
        }
    }

    @Test
    public void slotsDevemSerIndependentes() throws Exception {
        repositorio.salvar(1, estado);
        estado.setAto(Ato.ATO_IV);
        estado.setRotaAtoII(1);
        repositorio.salvar(2, estado);

        assertEquals(Ato.ATO_II, repositorio.carregar(1).getAto());
        assertEquals(Ato.ATO_IV, repositorio.carregar(2).getAto());
        assertFalse(repositorio.existe(3));
    }

    @Test
    public void salvarEmSlotOcupadoDeveSubstituirOSave() throws Exception {
        repositorio.salvar(1, estado);
        estado.setAto(Ato.ATO_III);
        estado.setRotaAtoII(1);
        repositorio.salvar(1, estado);

        assertEquals(Ato.ATO_III, repositorio.carregar(1).getAto());
        assertEquals("Ato III", repositorio.lerMetadados(1).getAto());
    }

    @Test
    public void deveGravarArquivoJsonLegivel() throws Exception {
        repositorio.salvar(1, estado);

        Path arquivo = pasta.resolve("saves").resolve("slot1.json");
        String json = Files.readString(arquivo);

        assertTrue(json.contains("\"nomeProtagonista\": \"Levi\""));
    }

    @Test
    public void metadadosDevemEstarDisponiveisSemCarregarAPartida() throws Exception {
        repositorio.salvar(2, estado);

        MetadadosSave metadados = repositorio.lerMetadados(2);

        assertEquals(2, metadados.getSlot());
        assertEquals("Levi", metadados.getNomeJogador());
        assertEquals("Ato II", metadados.getAto());
    }

    @Test
    public void excluirDeveApagarOSaveEPermitirExcluirSlotVazio() throws Exception {
        repositorio.salvar(1, estado);

        repositorio.excluir(1);
        assertFalse(repositorio.existe(1));

        assertDoesNotThrow(() -> repositorio.excluir(1));
    }

    @Test
    public void carregarSlotVazioDeveLancarCarregamentoException() {
        assertThrows(CarregamentoException.class, () -> repositorio.carregar(2));
    }

    @Test
    public void slotInvalidoDeveSerRejeitado() {
        assertThrows(SaveException.class, () -> repositorio.salvar(4, estado));
        assertThrows(SaveException.class, () -> repositorio.salvar(-1, estado));
        assertThrows(SaveException.class, () -> repositorio.excluir(9));
        assertThrows(CarregamentoException.class, () -> repositorio.carregar(7));
        assertFalse(repositorio.existe(7));
    }

    private void gravaArquivo(String nome, String conteudo) throws Exception {
        Path saves = pasta.resolve("saves");
        Files.createDirectories(saves);
        Files.writeString(saves.resolve(nome), conteudo);
    }

    @Test
    public void jsonMalformadoDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("slot1.json", "{ isso nao e json");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar(1));
        assertThrows(DadosInvalidosException.class, () -> repositorio.lerMetadados(1));
    }

    @Test
    public void arquivoVazioDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("slot1.json", "");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar(1));
    }

    @Test
    public void jsonValidoMasIncompletoDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("slot1.json", "{ \"versao\": 1 }");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar(1));
    }

    @Test
    public void tipoErradoNoJsonDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("slot1.json", "{ \"versao\": \"abc\" }");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar(1));
    }

    @Test
    public void dadosInvalidosSaoUmaCarregamentoException() {
        assertTrue(CarregamentoException.class.isAssignableFrom(DadosInvalidosException.class));
        assertTrue(PersistenciaException.class.isAssignableFrom(SaveException.class));
        assertTrue(PersistenciaException.class.isAssignableFrom(CarregamentoException.class));
    }

    @Test
    public void falhaDeEscritaDeveLancarSaveException() throws Exception {
        // a "pasta" de saves é, na verdade, um arquivo comum: não dá para criar o diretório
        Path arquivoNoLugarDaPasta = pasta.resolve("bloqueada");
        Files.writeString(arquivoNoLugarDaPasta, "x");

        RepositorioJson quebrado = new RepositorioJson(arquivoNoLugarDaPasta);

        assertThrows(SaveException.class, () -> quebrado.salvar(1, estado));
    }
}
