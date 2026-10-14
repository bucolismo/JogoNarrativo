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

    @Test
    public void valorNaoDevePassarDoMaximo() {
        Atributo atributo = new Atributo("Razão", 90, 100);
        atributo.aumentaValor(200);

        assertEquals(100, atributo.getValor());
    }

    @Test
    public void valorInicialDeveRespeitarOsLimites() {
        assertEquals(0, new Atributo("Razão", -5, 100).getValor());
        assertEquals(100, new Atributo("Razão", 500, 100).getValor());
    }

    @Test
    public void quantidadeNaoPositivaNaoDeveAlterarAoAumentar() {
        Atributo atributo = new Atributo("Razão", 10, 100);
        atributo.aumentaValor(0);
        atributo.aumentaValor(-5);

        assertEquals(10, atributo.getValor());
    }
}
