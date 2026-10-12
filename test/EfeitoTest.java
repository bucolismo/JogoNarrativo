import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EfeitoTest {

    @Test
    public void deveCriarEfeitoDeAtributo() {
        Efeito efeito = new Efeito("ATRIBUTO", "Razão", 10);

        assertEquals("ATRIBUTO", efeito.getTipo());
        assertEquals("Razão", efeito.getAlvo());
        assertEquals(10, efeito.getValor());
    }

    @Test
    public void deveCriarEfeitoDeConfianca() {
        NPC helena = new NPC("Helena", 30, "Feminino", 50);

        Efeito efeito = new Efeito(helena, 10);

        assertEquals("CONFIANCA", efeito.getTipo());
        assertSame(helena, efeito.getNpc());
        assertEquals(10, efeito.getValor());
    }

    @Test
    public void deveCriarEfeitoDeAdicionarItem() {
        Efeito efeito = new Efeito(3);

        assertEquals("ADICIONAR_ITEM", efeito.getTipo());
        assertEquals(3, efeito.getItemId());
        assertEquals(1, efeito.getValor());
    }
}
