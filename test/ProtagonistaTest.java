import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProtagonistaTest {

    @Test
    public void atributosDevemComecarEmZero() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");

        assertEquals(0, protagonista.getAtributo("Razão"));
        assertEquals(0, protagonista.getAtributo("Paranoia"));
        assertEquals(0, protagonista.getAtributo("Violência"));
        assertEquals(0, protagonista.getAtributo("Provas"));
    }

    @Test
    public void deveAlterarAtributo() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Razão", 20);

        assertEquals(20, protagonista.getAtributo("Razão"));
    }

    @Test
    public void deveReduzirAtributo() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Paranoia", 30);
        protagonista.alteraAtributo("Paranoia", -20);

        assertEquals(10, protagonista.getAtributo("Paranoia"));
    }

    @Test
    public void atributoNaoDeveFicarAbaixoDeZero() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Violência", -20);

        assertEquals(0, protagonista.getAtributo("Violência"));
    }

    @Test
    public void provasDevemSerContadas() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("Provas", 1);
        protagonista.alteraAtributo("Provas", 1);

        assertEquals(2, protagonista.getAtributo("Provas"));
    }

    @Test
    public void nomeDoAtributoNaoDeveDiferenciarMaiusculas() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.alteraAtributo("RAZÃO", 6);

        assertEquals(6, protagonista.getAtributo("Razão"));
        assertTrue(protagonista.possuiAtributo("violência"));
        assertFalse(protagonista.possuiAtributo("Sorte"));
    }

    @Test
    public void devePossuirInventario() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");

        assertNotNull(protagonista.getInventario());
    }

    @Test
    public void deveCadastrarTodosOsItensDoCatalogo() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");
        protagonista.cadastrarItens();

        for (Item item : CatalogoDeItens.criarTodos()) {
            assertNotNull(protagonista.getInventario().itemPorId(item.getItemID()));
        }
    }

    @Test
    public void personagemDeveExporIdadeEGenero() {
        Protagonista protagonista = new Protagonista("Levi", 23, "Masculino");

        assertEquals(23, protagonista.getIdade());
        assertEquals("Masculino", protagonista.getGenero());
    }
}
