import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class RepositorioDeConfiguracoesTest {

    @TempDir
    Path pasta;

    private RepositorioDeConfiguracoes repositorio;

    @BeforeEach
    public void prepara() {
        repositorio = new RepositorioDeConfiguracoes(pasta.resolve("saves"));
    }

    private void gravaArquivo(String conteudo) throws Exception {
        Path saves = pasta.resolve("saves");
        Files.createDirectories(saves);
        Files.writeString(saves.resolve("config.json"), conteudo);
    }

    @Test
    public void semArquivoDeveDevolverAsConfiguracoesPadrao() throws Exception {
        Configuracoes configuracoes = repositorio.carregar();

        assertFalse(configuracoes.isMostrarEfeitosDasEscolhas());
        assertFalse(configuracoes.isPausaEntreDialogos());
        assertFalse(configuracoes.temPersonagemFavorito());
    }

    @Test
    public void deveSalvarECarregarAsTresPreferencias() throws Exception {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setMostrarEfeitosDasEscolhas(true);
        configuracoes.setPausaEntreDialogos(true);
        configuracoes.setPersonagemFavorito(new PersonagemFavorito("Levi", 23, "Masculino"));

        repositorio.salvar(configuracoes);
        Configuracoes lida = repositorio.carregar();

        assertTrue(lida.isMostrarEfeitosDasEscolhas());
        assertTrue(lida.isPausaEntreDialogos());
        assertEquals("Levi", lida.getPersonagemFavorito().getNome());
        assertEquals(23, lida.getPersonagemFavorito().getIdade());
        assertEquals("Masculino", lida.getPersonagemFavorito().getGenero());
    }

    @Test
    public void deveGravarArquivoJsonLegivel() throws Exception {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setPausaEntreDialogos(true);

        repositorio.salvar(configuracoes);

        String json = Files.readString(pasta.resolve("saves").resolve("config.json"));
        assertTrue(json.contains("\"pausaEntreDialogos\": true"));
        assertTrue(json.contains("\"mostrarEfeitosDasEscolhas\": false"));
    }

    @Test
    public void salvarDeNovoDeveSubstituirAsConfiguracoesAnteriores() throws Exception {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setPersonagemFavorito(new PersonagemFavorito("Levi", 23, "Masculino"));
        repositorio.salvar(configuracoes);

        repositorio.salvar(new Configuracoes());

        assertFalse(repositorio.carregar().temPersonagemFavorito());
    }

    @Test
    public void camposAusentesNoJsonDevemUsarOsValoresPadrao() throws Exception {
        gravaArquivo("{ \"pausaEntreDialogos\": true }");

        Configuracoes lida = repositorio.carregar();

        assertTrue(lida.isPausaEntreDialogos());
        assertFalse(lida.isMostrarEfeitosDasEscolhas());
        assertFalse(lida.temPersonagemFavorito());
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
    public void tipoErradoNoJsonDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"pausaEntreDialogos\": [1, 2] }");

        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void favoritoIncompletoOuInvalidoDeveLancarDadosInvalidos() throws Exception {
        gravaArquivo("{ \"personagemFavorito\": {} }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());

        gravaArquivo("{ \"personagemFavorito\": { \"nome\": \"Levi\", \"idade\": -3, \"genero\": \"Masculino\" } }");
        assertThrows(DadosInvalidosException.class, () -> repositorio.carregar());
    }

    @Test
    public void falhaDeEscritaDeveLancarSaveException() throws Exception {
        // a "pasta" de saves é, na verdade, um arquivo comum: não dá para criar o diretório
        Path arquivoNoLugarDaPasta = pasta.resolve("bloqueada");
        Files.writeString(arquivoNoLugarDaPasta, "x");

        RepositorioDeConfiguracoes quebrado = new RepositorioDeConfiguracoes(arquivoNoLugarDaPasta);

        assertThrows(SaveException.class, () -> quebrado.salvar(new Configuracoes()));
    }
}
