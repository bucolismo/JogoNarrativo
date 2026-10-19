import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class EntradaTest {

    private Entrada entradaCom(String texto) {
        return new Entrada(new ByteArrayInputStream(texto.getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void deveLerInteiro() {
        Entrada entrada = entradaCom("42\n");

        assertEquals(42, entrada.lerInteiro());
    }

    @Test
    public void textoNaoNumericoNaoDeveQuebrarOJogo() {
        Entrada entrada = entradaCom("abc\n\n3,5\n7\n");

        assertEquals(Entrada.ENTRADA_INVALIDA, entrada.lerInteiro());
        assertEquals(Entrada.ENTRADA_INVALIDA, entrada.lerInteiro());
        assertEquals(Entrada.ENTRADA_INVALIDA, entrada.lerInteiro());
        assertEquals(7, entrada.lerInteiro());
    }

    @Test
    public void deveLerLinhaDeTexto() {
        Entrada entrada = entradaCom("Maria da Silva\n");

        assertEquals("Maria da Silva", entrada.lerString());
    }

    @Test
    public void entradaEncerradaDeveLancarExcecao() {
        Entrada entrada = entradaCom("");

        assertThrows(IllegalStateException.class, entrada::lerString);
    }
}
