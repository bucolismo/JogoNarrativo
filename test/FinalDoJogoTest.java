import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FinalDoJogoTest {

    @Test
    public void devePossuirOsTresFinaisComNomeETema() {
        assertEquals(3, FinalDoJogo.values().length);

        assertEquals("A Balança", FinalDoJogo.BALANCA.getNome());
        assertEquals("Razão", FinalDoJogo.BALANCA.getTema());
        assertEquals("A Névoa se Levanta", FinalDoJogo.NEVOA.getNome());
        assertEquals("Paranoia", FinalDoJogo.NEVOA.getTema());
        assertEquals("O Preço do Sangue", FinalDoJogo.SANGUE.getNome());
        assertEquals("Violência", FinalDoJogo.SANGUE.getTema());
    }

    @Test
    public void deveEncontrarOFinalPeloId() {
        assertEquals(FinalDoJogo.BALANCA, FinalDoJogo.porId(1));
        assertEquals(FinalDoJogo.NEVOA, FinalDoJogo.porId(2));
        assertEquals(FinalDoJogo.SANGUE, FinalDoJogo.porId(3));
    }

    @Test
    public void idInexistenteDeveDevolverNull() {
        assertNull(FinalDoJogo.porId(0));
        assertNull(FinalDoJogo.porId(4));
        assertNull(FinalDoJogo.porId(-1));
    }

    @Test
    public void cadaFinalDeveTerUmaConquistaEAPlatinaNaoDependeDeUmSo() {
        for (FinalDoJogo alvo : FinalDoJogo.values()) {
            int conquistasDoFinal = 0;

            for (Conquista conquista : Conquista.values()) {
                if (conquista.getFinalExigido() == alvo) {
                    conquistasDoFinal++;
                }
            }

            assertEquals(1, conquistasDoFinal, "final: " + alvo);
        }

        assertNull(Conquista.PLATINA.getFinalExigido());
        assertTrue(Conquista.PLATINA.isOculta());
        assertFalse(Conquista.FINAL_RAZAO.isOculta());
    }
}
