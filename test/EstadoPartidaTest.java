import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class EstadoPartidaTest {

    private EstadoPartida estado;

    @BeforeEach
    public void criaEstado() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.cadastrarItens();
        estado = EstadoPartida.nova(protagonista);
    }

    @Test
    public void partidaNovaDeveComecarNoPrologoComNpcsNeutros() {
        assertEquals(Ato.PROLOGO, estado.getAto());
        assertEquals(50, estado.getJonas().getConfianca());
        assertEquals(50, estado.getDaniel().getConfianca());
        assertEquals(50, estado.getHelena().getConfianca());
        assertEquals(0, estado.getRotaAtoII());
        assertEquals(0, estado.getRotaAtoV());
        assertTrue(estado.getDecisoes().isEmpty());
    }

    @Test
    public void escolhaSemRequisitoDeveEstarLiberada() {
        assertNull(estado.motivoDeBloqueio(new Escolha("[1] livre")));
    }

    @Test
    public void escolhaComItemExigidoDeveSerBloqueadaAteTerOItem() {
        Escolha escolha = new Escolha("[1] precisa de item");
        escolha.defineRequisitoItem(10);

        assertNotNull(estado.motivoDeBloqueio(escolha));

        estado.getProtagonista().getInventario().adicionarItemPorId(10);
        assertNull(estado.motivoDeBloqueio(escolha));
    }

    @Test
    public void escolhaComAtributoExigidoDeveSerBloqueadaAteAtingirOValor() {
        Escolha escolha = new Escolha("[1] precisa de razão");
        escolha.defineRequisitoAtributo("RAZÃO", 6);

        assertNotNull(estado.motivoDeBloqueio(escolha));

        estado.getProtagonista().alteraAtributo("Razão", 6);
        assertNull(estado.motivoDeBloqueio(escolha));
    }

    @Test
    public void escolhaComConfiancaExigidaDeveSerBloqueadaAteAtingirOValor() {
        Escolha escolha = new Escolha("[1] precisa da confiança do Daniel");
        escolha.defineRequisitoConfianca(estado.getDaniel(), 70);

        assertNotNull(estado.motivoDeBloqueio(escolha));

        estado.getDaniel().alterarConfianca(20);
        assertNull(estado.motivoDeBloqueio(escolha));
    }

    @Test
    public void deveAplicarOsEfeitosDaEscolha() {
        ArrayList<Efeito> efeitos = new ArrayList<>();
        efeitos.add(new Efeito("ATRIBUTO", "Razão", 3));
        efeitos.add(new Efeito("ATRIBUTO", "Provas", 1));
        efeitos.add(new Efeito(estado.getHelena(), -20));
        efeitos.add(new Efeito(6));

        boolean fim = estado.aplicarEfeitos(new Escolha("[1] tudo", efeitos));

        assertFalse(fim);
        assertEquals(3, estado.getProtagonista().getAtributo("Razão"));
        assertEquals(1, estado.getProtagonista().getAtributo("Provas"));
        assertEquals(30, estado.getHelena().getConfianca());
        assertTrue(estado.getProtagonista().getInventario().possuiItemPorId(6));
    }

    @Test
    public void efeitoDeGameOverDeveEncerrarAPartida() {
        assertTrue(estado.aplicarEfeitos(new Escolha("[1] fim", Efeito.gameOver())));
    }

    @Test
    public void efeitoDeTipoDesconhecidoDeveLancarExcecao() {
        Escolha escolha = new Escolha("[1] estranho", new Efeito("VOAR", "x", 1));

        assertThrows(IllegalStateException.class, () -> estado.aplicarEfeitos(escolha));
    }

    @Test
    public void deveRegistrarDecisoesEmOrdem() {
        estado.registrarDecisao("ATO_I#2=1");
        estado.registrarDecisao("ATO_I#5=3");

        assertEquals(java.util.List.of("ATO_I#2=1", "ATO_I#5=3"), estado.getDecisoes());
    }

    @Test
    public void alterarAListaDevolvidaNaoDeveAlterarAsDecisoesDaPartida() {
        estado.registrarDecisao("ATO_I#1=1");

        estado.getDecisoes().add("intruso");
        estado.getDecisoes().clear();

        assertEquals(java.util.List.of("ATO_I#1=1"), estado.getDecisoes());
    }

    @Test
    public void resumoDeveTrazerAtoAtributosEDecisoes() {
        estado.setAto(Ato.ATO_II);
        estado.getProtagonista().alteraAtributo("Razão", 9);
        estado.registrarDecisao("ATO_I#1=1");

        String resumo = estado.getResumo();

        assertTrue(resumo.contains("Ato II"));
        assertTrue(resumo.contains("Razão 9"));
        assertTrue(resumo.contains("Decisões: 1"));
    }
}
