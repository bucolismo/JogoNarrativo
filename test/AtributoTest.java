import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AtributoTest {

    @Test
    public void deveAumentarValor() {
        Atributo atributo = new Atributo("Razão", 50, 100);
        atributo.aumentaValor(20);

        assertEquals(70, atributo.getValor());
    }

    @Test
    public void deveReduzirValor() {
        Atributo atributo = new Atributo("Razão", 50, 100);
        atributo.reduzValor(20);

        assertEquals(30, atributo.getValor());
    }

    @Test
    public void valorNaoDeveFicarNegativo() {
        Atributo atributo = new Atributo("Razão", 20, 100);
        atributo.reduzValor(50);

        assertEquals(0, atributo.getValor());
    }
}