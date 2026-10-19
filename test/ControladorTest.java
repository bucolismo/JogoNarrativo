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
    private RepositorioDeConfiguracoes repositorioDeConfiguracoes;

    @BeforeEach
    public void preparaCaptura() {
        saidaOriginal = System.out;
        saidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(saidaCapturada, true, StandardCharsets.UTF_8));
        repositorio = new RepositorioJson(pasta.resolve("saves"));
        repositorioDeProgresso = new RepositorioDeProgresso(pasta.resolve("saves"));
        repositorioDeConfiguracoes = new RepositorioDeConfiguracoes(pasta.resolve("saves"));
    }

    @AfterEach
    public void restauraSaida() {
        System.setOut(saidaOriginal);
    }

    private String joga(String... linhas) {
        String roteiro = String.join("\n", linhas) + "\n";
        Entrada entrada = new Entrada(new ByteArrayInputStream(roteiro.getBytes(StandardCharsets.UTF_8)));

        new Controlador(new Menu(entrada), repositorio, repositorioDeProgresso, repositorioDeConfiguracoes).menuInicial();

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
        String saida = joga("2", "8");

        assertTrue(saida.contains("Não há partidas salvas para continuar."));
    }

    @Test
    public void iniciarPartidaDeveSalvarAutomaticamenteNoPrimeiroAto() throws Exception {
        String saida = joga("1", "Levi", "23", "Masculino", "n", "3", "s", "8");

        assertTrue(saida.contains("[Progresso salvo automaticamente]"));
        assertTrue(repositorio.existe(RepositorioDeSaves.SLOT_AUTOMATICO));
        assertEquals("Ato I", repositorio.lerMetadados(0).getAto());
        assertEquals("Levi", repositorio.lerMetadados(0).getNomeJogador());
    }

    @Test
    public void deveSalvarSobrescreverComConfirmacaoEExcluirComConfirmacao() throws Exception {
        String saida = joga(
                "1", "Levi", "23", "Masculino", "n",
                "2", "1",          // salvar manualmente no slot 1 (vazio: sem confirmação)
                "2", "1", "n",     // slot 1 ocupado: não sobrescreve
                "2", "1", "s",     // slot 1 ocupado: sobrescreve
                "3", "s",          // volta ao menu principal
                "3", "1", "2", "n", // carregar > slot 1 > excluir > não confirma
                "1", "2", "s",     // slot 1 > excluir > confirma
                "0",               // sai da tela de saves
                "8");

        assertEquals(2, ocorrencias(saida, "Partida salva no slot 1."));
        assertEquals(2, ocorrencias(saida, "já possui um save"));
        assertEquals(1, ocorrencias(saida, "Save excluído."));
        assertFalse(repositorio.existe(1));
        assertTrue(repositorio.existe(RepositorioDeSaves.SLOT_AUTOMATICO));
    }

    @Test
    public void continuarDeveRetomarDoUltimoPontoSalvoComOEstadoRestaurado() throws Exception {
        String saida = joga(
                "1", "Levi", "23", "Masculino", "n",
                "1",                          // ponto de salvamento do Ato I: continua
                "1", "1", "1", "1", "1",      // cinco escolhas do Ato I
                "1",                          // ponto de salvamento do desfecho: continua
                "3",                          // escolha da rota do Ato II
                "3", "s",                     // no Ato II: volta ao menu (já salvou automaticamente)
                "2",                          // continuar
                "3", "s",                     // de novo no Ato II: volta ao menu
                "8");

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

        String saida = joga("3", "1", "1", "0", "8");

        assertTrue(saida.contains("(save corrompido)"));
        assertTrue(saida.contains("Não foi possível carregar a partida"));
        assertTrue(saida.contains("Encerrando o jogo"));
    }

    @Test
    public void saveCorrompidoNaoDeveSerEscolhidoPeloContinuar() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("slot1.json"), "{ isso nao e json");

        String saida = joga("2", "8");

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
        String saida = joga("4", "0", "8");

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

        String saida = joga(junta(jogaAteOFinal("3"), "4", "0", "8"));

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

        String saida = joga(junta(jogaAteOFinal("1"), "8"));
        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Final da Razão: A Balança"));

        // a nova partida do teste começa de novo no mesmo repositório, com o progresso já gravado
        saidaCapturada.reset();
        saida = joga(junta(jogaAteOFinal("2"), "8"));
        assertTrue(saida.contains("CONQUISTA DESBLOQUEADA: Final da Paranoia: A Névoa se Levanta"));
        assertFalse(saida.contains("Platina"));
    }

    @Test
    public void alcancarOsTresFinaisDeveDesbloquearAPlatina() throws Exception {
        salvaPartidaNoAtoVI();

        String saida = joga(junta(jogaAteOFinal("1"), junta(jogaAteOFinal("2"), junta(jogaAteOFinal("3"), "4", "0", "8"))));

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

        String saida = joga(junta(jogaAteOFinal("3"), junta(jogaAteOFinal("3"), "8")));

        assertEquals(1, ocorrencias(saida, "CONQUISTA DESBLOQUEADA"));
    }

    @Test
    public void conquistasDevemSobreviverAoFimDaPartidaEAoReiniciarOJogo() throws Exception {
        salvaPartidaNoAtoVI();
        joga(junta(jogaAteOFinal("2"), "8"));

        // novo "jogo": outro Controlador lendo o mesmo arquivo de progresso
        saidaCapturada.reset();
        String saida = joga("4", "0", "8");

        assertTrue(saida.contains("Finais alcançados: 1/3"));
        assertTrue(saida.contains("[X] Final da Paranoia: A Névoa se Levanta"));
    }

    @Test
    public void redefinirConquistasDeveApagarOProgressoSoComConfirmacao() throws Exception {
        salvaPartidaNoAtoVI();
        joga(junta(jogaAteOFinal("3"), "8"));

        // responde "n": nada muda
        saidaCapturada.reset();
        String saida = joga("4", "1", "n", "0", "8");
        assertFalse(saida.contains("Conquistas redefinidas."));
        assertEquals(1, repositorioDeProgresso.carregar().getQuantidadeDeFinais());

        // responde "s": tudo é apagado
        saidaCapturada.reset();
        saida = joga("4", "1", "s", "0", "8");
        assertTrue(saida.contains("Conquistas redefinidas."));
        assertEquals(0, repositorioDeProgresso.carregar().getQuantidadeDeFinais());
        assertFalse(repositorioDeProgresso.carregar().temConquista(Conquista.FINAL_VIOLENCIA));
    }

    @Test
    public void progressoCorrompidoDeveSerSinalizadoENaoDerrubarOJogo() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("progresso.json"), "{ isso nao e json");

        String saida = joga("4", "0", "8");

        assertTrue(saida.contains("não foi possível carregar suas conquistas"));
        assertTrue(saida.contains("Finais alcançados: 0/3"));
        assertTrue(saida.contains("Encerrando o jogo"));
    }

    // ------------------------------------------------------------ configurações

    // nova partida de um personagem novo, joga o Ato I todo escolhendo sempre a opção 1 e volta ao menu
    private String[] jogaOAtoIComSempreAOpcao1() {
        return new String[]{"1", "Levi", "23", "Masculino", "n", "1", "1", "1", "1", "1", "1", "3", "s"};
    }

    @Test
    public void configuracoesNovasDevemComecarDesligadasESemFavorito() throws Exception {
        String saida = joga("5", "0", "8");

        assertTrue(saida.contains("Mostrar mudanças de atributos e relações após cada escolha: DESLIGADO"));
        assertTrue(saida.contains("Pausa entre diálogos (ENTER para continuar): DESLIGADO"));
        assertTrue(saida.contains("Personagem favorito: nenhum"));
    }

    @Test
    public void ligarAsOpcoesDeveGravarAsConfiguracoes() throws Exception {
        String saida = joga("5", "1", "2", "0", "8");

        assertTrue(saida.contains("Mostrar mudanças de atributos e relações após cada escolha: LIGADO"));
        assertTrue(saida.contains("Pausa entre diálogos (ENTER para continuar): LIGADO"));

        Configuracoes gravada = repositorioDeConfiguracoes.carregar();
        assertTrue(gravada.isMostrarEfeitosDasEscolhas());
        assertTrue(gravada.isPausaEntreDialogos());
    }

    @Test
    public void desligarDeNovoDeveGravarAsConfiguracoes() throws Exception {
        joga("5", "1", "2", "0", "8");

        saidaCapturada.reset();
        joga("5", "1", "2", "0", "8");

        Configuracoes gravada = repositorioDeConfiguracoes.carregar();
        assertFalse(gravada.isMostrarEfeitosDasEscolhas());
        assertFalse(gravada.isPausaEntreDialogos());
    }

    @Test
    public void configuracoesDevemSerMantidasAoReabrirOJogo() throws Exception {
        joga("5", "1", "0", "8");

        // novo "jogo": outro Controlador lendo o mesmo arquivo de configurações
        saidaCapturada.reset();
        String saida = joga("5", "0", "8");

        assertTrue(saida.contains("Mostrar mudanças de atributos e relações após cada escolha: LIGADO"));
        assertTrue(saida.contains("Pausa entre diálogos (ENTER para continuar): DESLIGADO"));
    }

    @Test
    public void configuracoesCorrompidasDevemAvisarEUsarOsValoresPadrao() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("config.json"), "{ isso nao e json");

        String saida = joga("5", "0", "8");

        assertTrue(saida.contains("não foi possível carregar suas configurações"));
        assertTrue(saida.contains("Mostrar mudanças de atributos e relações após cada escolha: DESLIGADO"));
        assertTrue(saida.contains("Encerrando o jogo"));
    }

    // ------------------------------------------------ mostrar efeitos das escolhas

    @Test
    public void comAOpcaoDesligadaNaoDeveMostrarAsMudancas() throws Exception {
        String saida = joga(jogaOAtoIComSempreAOpcao1()[0], "Levi", "23", "Masculino", "n", "1",
                "1", "1", "1", "1", "1", "3", "s", "8");

        assertFalse(saida.contains("[Razão +"));
        assertFalse(saida.contains("[Relação com Jonas"));
    }

    @Test
    public void comAOpcaoLigadaDeveMostrarAsMudancasDeAtributosERelacoes() throws Exception {
        String saida = joga(junta(new String[]{"5", "1", "0"}, jogaOAtoIComSempreAOpcao1()[0], "Levi", "23",
                "Masculino", "n", "1", "1", "1", "1", "1", "1", "3", "s", "8"));

        assertTrue(saida.contains("[Razão +"));
        assertTrue(saida.contains("[Relação com Jonas +"));
        assertFalse(saida.contains("[Paranoia"));   // atributo que não mudou não aparece
    }

    // ------------------------------------------------------------ pausa entre diálogos

    @Test
    public void comAPausaDesligadaNaoDevePedirEnter() throws Exception {
        String saida = joga("1", "Levi", "23", "Masculino", "n", "3", "s", "8");

        assertFalse(saida.contains("Pressione ENTER"));
    }

    @Test
    public void comAPausaLigadaDevePedirEnterDepoisDeCadaDialogoSemEscolhas() throws Exception {
        // o prólogo tem 8 diálogos e nenhum deles tem escolhas
        String saida = joga("5", "2", "0",
                "1", "Levi", "23", "Masculino", "n",
                "", "", "", "", "", "", "", "",      // 8 vezes ENTER
                "3", "s", "8");

        assertEquals(8, ocorrencias(saida, "Pressione ENTER para continuar"));
        assertTrue(saida.contains("PONTO DE SALVAMENTO — Ato I"));
    }

    // ------------------------------------------------------------ personagem favorito

    @Test
    public void salvarPersonagemComoFavoritoDeveGravarNasConfiguracoes() throws Exception {
        String saida = joga("1", "Levi", "23", "Masculino", "s", "3", "s", "8");

        assertTrue(saida.contains("Personagem salvo como favorito."));

        PersonagemFavorito favorito = repositorioDeConfiguracoes.carregar().getPersonagemFavorito();
        assertEquals("Levi", favorito.getNome());
        assertEquals(23, favorito.getIdade());
        assertEquals("Masculino", favorito.getGenero());
    }

    @Test
    public void naoSalvarComoFavoritoNaoDeveGravarNada() throws Exception {
        joga("1", "Levi", "23", "Masculino", "n", "3", "s", "8");

        assertFalse(repositorioDeConfiguracoes.carregar().temPersonagemFavorito());
    }

    @Test
    public void novaPartidaDeveOferecerOFavoritoEUsaLoSemPedirOsDados() throws Exception {
        joga("1", "Levi", "23", "Masculino", "s", "3", "s", "8");

        saidaCapturada.reset();
        String saida = joga("1", "s", "3", "s", "8");

        assertTrue(saida.contains("Deseja usar seu personagem favorito (Levi, 23 anos, Masculino)"));
        assertFalse(saida.contains("Digite o nome do protagonista"));
        assertEquals("Levi", repositorio.lerMetadados(RepositorioDeSaves.SLOT_AUTOMATICO).getNomeJogador());
        // usar o favorito não pergunta de novo se quer salvá-lo como favorito
        assertFalse(saida.contains("Deseja salvar este personagem como favorito?"));
    }

    @Test
    public void recusarOFavoritoDeveCriarUmPersonagemNovoSemApagarOFavorito() throws Exception {
        joga("1", "Levi", "23", "Masculino", "s", "3", "s", "8");

        saidaCapturada.reset();
        String saida = joga("1", "n", "Ana", "30", "Feminino", "n", "3", "s", "8");

        assertTrue(saida.contains("Digite o nome do protagonista"));
        assertEquals("Ana", repositorio.lerMetadados(RepositorioDeSaves.SLOT_AUTOMATICO).getNomeJogador());
        assertEquals("Levi", repositorioDeConfiguracoes.carregar().getPersonagemFavorito().getNome());
    }

    @Test
    public void substituirOFavoritoDevePedirConfirmacao() throws Exception {
        joga("1", "Levi", "23", "Masculino", "s", "3", "s", "8");

        // "n" na confirmação: o favorito antigo fica
        saidaCapturada.reset();
        String saida = joga("1", "n", "Ana", "30", "Feminino", "s", "n", "3", "s", "8");
        assertTrue(saida.contains("Já existe um favorito salvo"));
        assertEquals("Levi", repositorioDeConfiguracoes.carregar().getPersonagemFavorito().getNome());

        // "s" na confirmação: o favorito novo substitui o antigo
        saidaCapturada.reset();
        joga("1", "n", "Ana", "30", "Feminino", "s", "s", "3", "s", "8");
        assertEquals("Ana", repositorioDeConfiguracoes.carregar().getPersonagemFavorito().getNome());
    }

    @Test
    public void excluirOFavoritoDevePedirConfirmacao() throws Exception {
        joga("1", "Levi", "23", "Masculino", "s", "3", "s", "8");

        // mostra o favorito na tela de configurações; "n" mantém
        saidaCapturada.reset();
        String saida = joga("5", "3", "n", "0", "8");
        assertTrue(saida.contains("Personagem favorito: Levi, 23 anos, Masculino"));
        assertTrue(repositorioDeConfiguracoes.carregar().temPersonagemFavorito());

        // "s" exclui
        saidaCapturada.reset();
        saida = joga("5", "3", "s", "0", "8");
        assertTrue(saida.contains("Personagem favorito excluído."));
        assertFalse(repositorioDeConfiguracoes.carregar().temPersonagemFavorito());
    }

    @Test
    public void semFavoritoAOpcaoDeExcluirDeveExplicar() throws Exception {
        String saida = joga("5", "3", "0", "8");

        assertTrue(saida.contains("Você ainda não tem um personagem favorito."));
    }

    @Test
    public void favoritoInvalidoNoArquivoDeveSerRejeitadoSemQuebrarOJogo() throws Exception {
        Files.createDirectories(pasta.resolve("saves"));
        Files.writeString(pasta.resolve("saves").resolve("config.json"),
                "{ \"personagemFavorito\": { \"nome\": \"\", \"idade\": 0 } }");

        String saida = joga("1", "Levi", "23", "Masculino", "n", "3", "s", "8");

        assertTrue(saida.contains("não foi possível carregar suas configurações"));
        assertTrue(saida.contains("Digite o nome do protagonista"));
    }
}
