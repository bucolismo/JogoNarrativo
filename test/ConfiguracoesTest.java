import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfiguracoesTest {

    @Test
    public void configuracoesNovasDevemTerOsValoresPadrao() {
        Configuracoes configuracoes = new Configuracoes();

        assertFalse(configuracoes.isMostrarEfeitosDasEscolhas());
        assertFalse(configuracoes.isPausaEntreDialogos());
        assertFalse(configuracoes.temPersonagemFavorito());
        assertNull(configuracoes.getPersonagemFavorito());
    }

    @Test
    public void deveAlterarCadaPreferencia() {
        Configuracoes configuracoes = new Configuracoes();

        configuracoes.setMostrarEfeitosDasEscolhas(true);
        configuracoes.setPausaEntreDialogos(true);
        configuracoes.setPersonagemFavorito(new PersonagemFavorito("Levi", 23, "Masculino"));

        assertTrue(configuracoes.isMostrarEfeitosDasEscolhas());
        assertTrue(configuracoes.isPausaEntreDialogos());
        assertTrue(configuracoes.temPersonagemFavorito());
        assertEquals("Levi", configuracoes.getPersonagemFavorito().getNome());
    }

    @Test
    public void removerOFavoritoDeveVoltarAoEstadoSemFavorito() {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setPersonagemFavorito(new PersonagemFavorito("Levi", 23, "Masculino"));

        configuracoes.setPersonagemFavorito(null);

        assertFalse(configuracoes.temPersonagemFavorito());
    }

    @Test
    public void configuracoesSemFavoritoOuComFavoritoValidoDevemSerValidas() {
        Configuracoes configuracoes = new Configuracoes();
        assertDoesNotThrow(() -> configuracoes.validar());

        configuracoes.setPersonagemFavorito(new PersonagemFavorito("Levi", 23, "Masculino"));
        assertDoesNotThrow(() -> configuracoes.validar());
    }

    @Test
    public void favoritoInvalidoDeveTornarAsConfiguracoesInvalidas() {
        Configuracoes configuracoes = new Configuracoes();
        configuracoes.setPersonagemFavorito(new PersonagemFavorito("", 23, "Masculino"));

        assertThrows(DadosInvalidosException.class, () -> configuracoes.validar());
    }
}
