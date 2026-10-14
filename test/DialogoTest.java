import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DialogoTest {

    private final Personagem narrador = new Personagem("Narrador", 0, "Neutro");

    @Test
    public void dialogoSemEscolhasNaoDevePossuirOpcoes() {
        Dialogo dialogo = new Dialogo(narrador, "Texto");

        assertFalse(dialogo.possuiOpcoes());
        assertEquals(0, dialogo.getQuantidadeEscolhas());
        assertEquals(0, dialogo.getEscolhas().length);
        assertNull(dialogo.getEscolha(1));
    }

    @Test
    public void dialogoSemEscolhasDevePermitirAdicionarEscolha() {
        Dialogo dialogo = new Dialogo(narrador, "Texto");
        dialogo.adicionaEscolha(new Escolha("[1] Opção"));

        assertTrue(dialogo.possuiOpcoes());
        assertEquals(1, dialogo.getQuantidadeEscolhas());
    }

    @Test
    public void deveBuscarEscolhaPeloIndiceQueComecaEmUm() {
        Escolha primeira = new Escolha("[1] A");
        Escolha segunda = new Escolha("[2] B");
        Dialogo dialogo = new Dialogo(narrador, "Texto", primeira, segunda);

        assertSame(primeira, dialogo.getEscolha(1));
        assertSame(segunda, dialogo.getEscolha(2));
        assertNull(dialogo.getEscolha(0));
        assertNull(dialogo.getEscolha(3));
    }
}
