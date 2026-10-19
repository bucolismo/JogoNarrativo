import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonagemFavoritoTest {

    @Test
    public void deveGuardarNomeIdadeEGenero() {
        PersonagemFavorito favorito = new PersonagemFavorito("Levi", 23, "Masculino");

        assertEquals("Levi", favorito.getNome());
        assertEquals(23, favorito.getIdade());
        assertEquals("Masculino", favorito.getGenero());
    }

    @Test
    public void favoritoCompletoDeveSerValido() {
        assertDoesNotThrow(() -> new PersonagemFavorito("Levi", 23, "Masculino").validar());
    }

    @Test
    public void nomeVazioOuNuloDeveSerRejeitado() {
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito(null, 23, "Masculino").validar());
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("", 23, "Masculino").validar());
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("   ", 23, "Masculino").validar());
    }

    @Test
    public void idadeNaoPositivaDeveSerRejeitada() {
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("Levi", 0, "Masculino").validar());
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("Levi", -5, "Masculino").validar());
    }

    @Test
    public void generoVazioOuNuloDeveSerRejeitado() {
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("Levi", 23, null).validar());
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito("Levi", 23, " ").validar());
    }

    @Test
    public void favoritoCriadoSemDadosDeveSerRejeitado() {
        assertThrows(DadosInvalidosException.class, () -> new PersonagemFavorito().validar());
    }
}
