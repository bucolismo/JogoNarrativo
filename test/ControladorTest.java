import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testes de integração: o Controlador é executado com uma entrada roteirizada e uma pasta
 * temporária de saves, e a saída do terminal é capturada para conferir as mensagens.
 */
public class ControladorTest {

    @TempDir
    Path pasta;

    private PrintStream saidaOriginal;
    private ByteArrayOutputStream saidaCapturada;
    private RepositorioJson repositorio;
    private RepositorioDeProgresso repositorioDeProgresso;

    @BeforeEach
    public void preparaCaptura() {
        saidaOriginal = System.out;
        saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada, true, StandardCharsets.UTF_8));
        repositorio = new RepositorioJson(pasta.resolve("saves"));
        repositorioDeProgresso = new RepositorioDeProgresso(pasta.resolve("saves"));
    }

    @AfterEach
    public void restauraSaida() {
        System.setOut(saidaOriginal);
    }

    private String joga(String... linhas) {
        String roteiro = String.join("\n", linhas) + "\n";
        Entrada entrada = new Entrada(new ByteArrayInputStream(roteiro.getBytes(StandardCharsets.UTF_8)));

        new Controlador(new Menu(entrada), repositorio, repositorioDeProgresso).menuInicial();

        return saidaCapturada.toString(StandardCharsets.UTF_8);
    }

    private int ocorrencias(String texto, String trecho) {
        int total = 0;
        for (int i = texto.indexOf(trecho); i >= 0; i = texto.indexOf(trecho, i + 1)) {
            total++;
        }
        return total;
    }

    @Test
    public void continuarSemSavesDeveAvisar() {
        String saida = joga("2", "7");

        assertTrue(saida.contains("Não há partidas salvas para continuar."));
    }

    @Test
    public void iniciarPartidaDeveSalvarAutomaticamenteNoPrimeiroAto() throws Exception {
        String saida = joga("1", "Levi", "23", "Masculino", "3", "s", "7");

        assertTrue(saida.contains("[Progresso salvo automaticamente]"));
        assertTrue(repositorio.existe(RepositorioDeSaves.SLOT_AUTOMATICO));
        assertEquals("Ato I", repositorio.lerMetadados(0).getAto());
        assertEquals("Levi", repositorio.lerMetadados(0).getNomeJogador());
    }

    @Test
    public void deveSalvarSobrescreverComConfirmacaoEExcluirComConfirmacao() throws Exception {
        String saida = joga(
                "1", "Levi", "23", "Masculino",
                "2", "1",          // salvar manualmente no slot 1 (vazio: sem confirmação)
                "2", "1", "n",     // slot 1 ocupado: não sobrescreve
                "2", "1", "s",     // slot 1 ocupado: sobrescreve
                "3", "s",          // volta ao menu principal
                "3", "1", "2", "n", // carregar > slot 1 > excluir > não confirma
                "1", "2", "s",     // slot 1 > excluir > confirma
                "0",               // sai da tela de saves
                "7");

        assertEquals(2, ocorrencias(saida, "Partida salva no slot 1."));
        assertEquals(2, ocorrencias(saida, "já possui um save"));
        assertEquals(1, ocorrencias(saida, "Save excluído."));
        assertFalse(repositorio.existe(1));
        assertTrue(repositorio.existe(RepositorioDeSaves.SLOT_AUTOMATICO));
    }

    @Test
    public void continuarDeveRetomarDoUltimoPontoSalvoComOEstadoRestaurado() throws Exception {
        String saida = joga(
                "1", "Levi", "23", "Masculino",
                "1",                          // ponto de salvamento do Ato I: continua
                "1", "1", "1", "1", "1",      // cinco escolhas do Ato I
                "1",                          // ponto de salvamento do desfecho: continua
                "3",                          // escolha da rota do Ato II
                "3", "s",                     // no Ato II: volta ao menu (já salvou automaticamente)
                "2",                          // continuar
                "3", "s",                     // de novo no Ato II: volta ao menu
                "7");

        assertEquals(1, ocorrencias(saida, "Partida carregada."));
        assertEquals(2, ocorrencias(saida, "PONTO DE SALVAMENTO — Ato II ="));

        EstadoPartida estado = repositorio.carregar(RepositorioDeSaves.SLOT_AUTOMATICO);
        assertEquals(Ato.ATO_II, estado.getAto());
        assertEquals(3, estado.getRotaAtoII());
        assertEquals(6, estado.getDecisoes().size());
        assertTrue(estado.getProtagonista().getInventario().possuiItemPorId(6));
        assertEquals(12, estado.getProtagonista().getAtributo("Razão"));
        assertEquals(100, estado.getJonas().getConfianca());
    }

    @Test
    public void saveCorrompidoDeveSerSinalizadoENaoDerrubarOJogo() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("slot1.json"), "{ isso nao e json");

        String saida = joga("3", "1", "1", "0", "7");

        assertTrue(saida.contains("(save corrompido)"));
        assertTrue(saida.contains("Não foi possível carregar a partida"));
        assertTrue(saida.contains("Encerrando o jogo"));
    }

    @Test
    public void saveCorrompidoNaoDeveSerEscolhidoPeloContinuar() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("slot1.json"), "{ isso nao e json");

        String saida = joga("2", "7");

        assertTrue(saida.contains("Não há partidas salvas para continuar."));
    }

    // ------------------------------------------------ registro de finais e conquistas

    /**
     * grava no slot 1 uma partida já no Ato VI, com atributos e itens que liberam os três finais
     * (1 = A Balança, 2 = A Névoa se Levanta, 3 = O Preço do Sangue)
     */
    private void salvaPartidaNoAtoVI() throws Exception {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.cadastrarItens();
        protagonista.alteraAtributo("Razão", 30);
        protagonista.alteraAtributo("Paranoia", 30);
        protagonista.alteraAtributo("Violência", 30);
        protagonista.getInventario().adicionarItemPorId(8);
        protagonista.getInventario().adicionarItemPorId(10);
        protagonista.getInventario().adicionarItemPorId(11);

        EstadoPartida estado = EstadoPartida.nova(protagonista);
        estado.setAto(Ato.ATO_VI);
        estado.setRotaAtoII(1);
        estado.setRotaAtoV(1);

        repositorio.salvar(1, estado);
    }

    // carrega o slot 1, continua no ponto de salvamento do Ato VI e escolhe o final informado
    private String[] jogaAteOFinal(String numeroDoFinal) {
        return new String[]{"3", "1", "1", "1", numeroDoFinal};
    }

    private String[] junta(String[] primeiro, String... resto) {
        String[] todos = new String[primeiro.length + resto.length];

        for (int i = 0; i < primeiro.length; i++) {
            todos[i] = primeiro[i];
        }
        for (int i = 0; i < resto.length; i++) {
            todos[primeiro.length + i] = resto[i];
        }

        return todos;
    }

    @Test
    public void telaDeConquistasSemProgressoDeveMostrarTudoBloqueado() {
        String saida = joga("4", "0", "7");

        assertTrue(saida.contains("Finais alcançados: 0/3"));
        assertTrue(saida.contains("[ ] Final da Razão: A Balança"));
        assertTrue(saida.contains("[ ] Final da Paranoia: A Névoa se Levanta"));
        assertTrue(saida.contains("[ ] Final da Violência: O Preço do Sangue"));
        assertTrue(saida.contains("[ ] ???"));
        assertFalse(saida.contains("Platina"));
    }

    @Test
    public void alcancarUmFinalDeveDesbloquearASuaConquistaEGuardarOProgresso() throws Exception {
        salvaPartidaNoAtoVI();

        String saida = joga(junta(jogaAteOFinal("3"), "4", "0", "7"));

        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Final da Violência: O Preço do Sangue"));
        assertFalse(saida.contains("CONQUISTA DESBLOQUEADA: Platina"));
        assertTrue(saida.contains("Finais alcançados: 1/3"));
        assertTrue(saida.contains("[X] Final da Violência: O Preço do Sangue"));
        assertTrue(saida.contains("[ ] Final da Razão: A Balança"));

        ProgressoGlobal gravado = repositorioDeProgresso.carregar();
        assertTrue(gravado.alcancouFinal(FinalDoJogo.SANGUE));
        assertFalse(gravado.alcancouFinal(FinalDoJogo.BALANCA));
        assertTrue(gravado.temConquista(Conquista.FINAL_VIOLENCIA));
    }

    @Test
    public void cadaFinalDeveDesbloquearAConquistaCorrespondente() throws Exception {
        salvaPartidaNoAtoVI();

        String saida = joga(junta(jogaAteOFinal("1"), "7"));
        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Final da Razão: A Balança"));

        // a nova partida do teste começa de novo no mesmo repositório, com o progresso já gravado
        saidaCapturada.reset();
        saida = joga(junta(jogaAteOFinal("2"), "7"));
        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Final da Paranoia: A Névoa se Levanta"));
        assertFalse(saida.contains("Platina"));
    }

    @Test
    public void alcancarOsTresFinaisDeveDesbloquearAPlatina() throws Exception {
        salvaPartidaNoAtoVI();

        String saida = joga(junta(jogaAteOFinal("1"), junta(jogaAteOFinal("2"), junta(jogaAteOFinal("3"), "4", "0", "7"))));

        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Platina"));
        assertTrue(saida.contains("Finais alcançados: 3/3"));
        assertTrue(saida.contains("[X] Platina"));
        assertFalse(saida.contains("???"));

        ProgressoGlobal gravado = repositorioDeProgresso.carregar();
        assertEquals(3, gravado.getQuantidadeDeFinais());
        assertTrue(gravado.temConquista(Conquista.PLATINA));
    }

    @Test
    public void repetirUmFinalNaoDeveDesbloquearNadaDeNovo() throws Exception {
        salvaPartidaNoAtoVI();

        String saida = joga(junta(jogaAteOFinal("3"), junta(jogaAteOFinal("3"), "7")));

        assertEquals(1, ocorrencias(saida, "CONQUISTA DESBLOQUEADA"));
    }

    @Test
    public void conquistasDevemSobreviverAoFimDaPartidaEAoReiniciarOJogo() throws Exception {
        salvaPartidaNoAtoVI();
        joga(junta(jogaAteOFinal("2"), "7"));

        // novo "jogo": outro Controlador lendo o mesmo arquivo de progresso
        saidaCapturada.reset();
        String saida = joga("4", "0", "7");

        assertTrue(saida.contains("Finais alcançados: 1/3"));
        assertTrue(saida.contains("[X] Final da Paranoia: A Névoa se Levanta"));
    }

    @Test
    public void redefinirConquistasDeveApagarOProgressoSoComConfirmacao() throws Exception {
        salvaPartidaNoAtoVI();
        joga(junta(jogaAteOFinal("3"), "7"));

        // responde "n": nada muda
        saidaCapturada.reset();
        String saida = joga("4", "1", "n", "0", "7");
        assertFalse(saida.contains("Conquistas redefinidas."));
        assertEquals(1, repositorioDeProgresso.carregar().getQuantidadeDeFinais());

        // responde "s": tudo é apagado
        saidaCapturada.reset();
        saida = joga("4", "1", "s", "0", "7");
        assertTrue(saida.contains("Conquistas redefinidas."));
        assertEquals(0, repositorioDeProgresso.carregar().getQuantidadeDeFinais());
        assertFalse(repositorioDeProgresso.carregar().temConquista(Conquista.FINAL_VIOLENCIA));
    }

    @Test
    public void progressoCorrompidoDeveSerSinalizadoENaoDerrubarOJogo() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("progresso.json"), "{ isso nao e json");

        String saida = joga("4", "0", "7");

        assertTrue(saida.contains("não foi possível carregar suas conquistas"));
        assertTrue(saida.contains("Finais alcançados: 0/3"));
        assertTrue(saida.contains("Encerrando o jogo"));
    }
}
