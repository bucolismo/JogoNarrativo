import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProtagonistaTest {

    @Test
    public void deveAlterarAtributo() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Razão", 20);

        assertEquals(70, protagonista.getAtributo("Razão"));
    }

    @Test
    public void deveReduzirAtributo() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Paranoia", -20);

        assertEquals(30, protagonista.getAtributo("Paranoia"));
    }


    @Test
    public void devePossuirInventario() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");

        assertNotNull(protagonista.getInventario());
    }
}