import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioDeProgressoTest {

    @TempDir
    Path pasta;

    private RepositorioDeProgresso repositorio;

    @BeforeEach
    public void prepara() {
        repositorio = new RepositorioDeProgresso(pasta.resolve("saves"));
    }

    private void gravaArquivo(String conteudo) throws Exception {
        Path saves = pasta.resolve("saves");
        Files.createDirectories(saves);
        Files.writeString(saves.resolve("progresso.json"), conteudo);
    }

    @Test
    public void semArquivoDeveDevolverProgressoVazio() throws Exception {
        ProgressoGlobal progresso = repositorio.carregar();

        assertEquals(0, progresso.getQuantidadeDeFinais());
    }

    @Test
    public void deveSalvarECarregarOProgresso() throws Exception {
        ProgressoGlobal progresso = new ProgressoGlobal();
        progresso.registrarFinal(FinalDoJogo.BALANCA);
        progresso.registrarFinal(FinalDoJogo.SANGUE);

        repositorio.salvar(progresso);
        ProgressoGlobal lido = repositorio.carregar();

        assertEquals(2, lido.getQuantidadeDeFinais());
        assertTrue(lido.alcancouFinal(FinalDoJogo.BALANCA));
        assertTrue(lido.alcancouFinal(FinalDoJogo.SANGUE));
        assertFalse(lido.alcancouFinal(FinalDoJogo.NEVOA));
        assertTrue(lido.temConquista(Conquista.FINAL_RAZAO));
        assertTrue(lido.temConquista(Conquista.FINAL_VIOLENCIA));
        assertEquals(progresso.getDataDaConquista(Conquista.FINAL_RAZAO), lido.getDataDaConquista(Conquista.FINAL_RAZAO));
    }

    @Test
    public void deveGravarArquivoJsonLegivel() throws Exception {
        ProgressoGlobal progresso = new ProgressoGlobal();
        progresso.registrarFinal(FinalDoJogo.NEVOA);

        repositorio.salvar(progresso);

        String json = Files.readString(pasta.resolve("saves").resolve("progresso.json"));
        assertTrue(json.contains("NEVOA"));
        assertTrue(json.contains("FINAL_PARANOIA"));
    }

    @Test
    public void salvarDeNovoDeveSubstituirOProgressoAnterior() throws Exception {
        ProgressoGlobal progresso = new ProgressoGlobal();
        progresso.registrarFinal(FinalDoJogo.NEVOA);
        repositorio.salvar(progresso);

        repositorio.salvar(new ProgressoGlobal());

        assertEquals(0, repositorio.carregar().getQuantidadeDeFinais());
    }

    @Test
    public void jsonMalformadoDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ isso nao e json");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void arquivoVazioDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void finalDesconhecidoDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"finaisAlcancados\": [\"FINAL_SECRETO\"], \"conquistas\": [] }");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void conquistaDesconhecidaOuSemDataDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"finaisAlcancados\": [], \"conquistas\": [ { \"id\": \"SUPER_PLATINA\", \"dataHora\": \"01/01/2026 10:00\" } ] }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());

        gravaArquivo("{ \"finaisAlcancados\": [], \"conquistas\": [ { \"id\": \"PLATINA\" } ] }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void listasNulasDevemLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"finaisAlcancados\": null, \"conquistas\": [] }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());

        gravaArquivo("{ \"finaisAlcancados\": [], \"conquistas\": null }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void tipoErradoNoJsonDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"finaisAlcancados\": \"BALANCA\", \"conquistas\": [] }");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void falhaDeEscritaDeveLancarSaveException() throws Exception {
        // a "pasta" de saves é, na verdade, um arquivo comum: não dá para criar o diretório
        Path arquivoNoLugarDaPasta = pasta.resolve("bloqueada");
        Files.writeString(arquivoNoLugarDaPasta, "x");

        RepositorioDeProgresso quebrado = new RepositorioDeProgresso(arquivoNoLugarDaPasta);

        assertThrows(SaveException.class, () -> quebrado.salvar(new ProgressoGlobal()));
    }
}
