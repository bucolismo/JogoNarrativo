import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressoGlobalTest {

    private ProgressoGlobal progresso;

    @BeforeEach
    public void criaProgresso() {
        progresso = new ProgressoGlobal();
    }

    @Test
    public void progressoNovoDeveComecarVazio() {
        assertEquals(0, progresso.getQuantidadeDeFinais());

        for (FinalDoJogo alvo : FinalDoJogo.values()) {
            assertFalse(progresso.alcancouFinal(alvo));
        }
        for (Conquista conquista : Conquista.values()) {
            assertFalse(progresso.temConquista(conquista));
            assertNull(progresso.getDataDaConquista(conquista));
        }
    }

    @Test
    public void registrarUmFinalDeveDesbloquearSoASuaConquista() {
        List<Conquista> novas = progresso.registrarFinal(FinalDoJogo.NEVOA);

        assertEquals(1, novas.size());
        assertEquals(Conquista.FINAL_PARANOIA, novas.get(0));
        assertTrue(progresso.alcancouFinal(FinalDoJogo.NEVOA));
        assertEquals(1, progresso.getQuantidadeDeFinais());
        assertTrue(progresso.temConquista(Conquista.FINAL_PARANOIA));
        assertFalse(progresso.temConquista(Conquista.FINAL_RAZAO));
        assertFalse(progresso.temConquista(Conquista.PLATINA));
    }

    @Test
    public void conquistaDeveGuardarADataDoDesbloqueio() {
        progresso.registrarFinal(FinalDoJogo.BALANCA);

        String data = progresso.getDataDaConquista(Conquista.FINAL_RAZAO);

        assertNotNull(data);
        // formato dd/MM/yyyy HH:mm, ex.: 17/10/2026 14:32
        assertEquals(16, data.length());
        assertEquals('/', data.charAt(2));
        assertEquals('/', data.charAt(5));
        assertEquals(':', data.charAt(13));
    }

    @Test
    public void repetirUmFinalNaoDeveDesbloquearNadaDeNovo() {
        progresso.registrarFinal(FinalDoJogo.SANGUE);
        String dataOriginal = progresso.getDataDaConquista(Conquista.FINAL_VIOLENCIA);

        List<Conquista> novas = progresso.registrarFinal(FinalDoJogo.SANGUE);

        assertTrue(novas.isEmpty());
        assertEquals(1, progresso.getQuantidadeDeFinais());
        assertEquals(dataOriginal, progresso.getDataDaConquista(Conquista.FINAL_VIOLENCIA));
    }

    @Test
    public void platinaSoDeveSerDesbloqueadaComTodosOsFinais() {
        progresso.registrarFinal(FinalDoJogo.BALANCA);
        progresso.registrarFinal(FinalDoJogo.NEVOA);
        assertFalse(progresso.temConquista(Conquista.PLATINA));

        List<Conquista> novas = progresso.registrarFinal(FinalDoJogo.SANGUE);

        assertEquals(2, novas.size());
        assertEquals(Conquista.FINAL_VIOLENCIA, novas.get(0));
        assertEquals(Conquista.PLATINA, novas.get(1));
        assertEquals(3, progresso.getQuantidadeDeFinais());
        assertTrue(progresso.temConquista(Conquista.PLATINA));
    }

    @Test
    public void ordemDosFinaisNaoDeveImportarParaAPlatina() {
        progresso.registrarFinal(FinalDoJogo.SANGUE);
        progresso.registrarFinal(FinalDoJogo.BALANCA);
        List<Conquista> novas = progresso.registrarFinal(FinalDoJogo.NEVOA);

        assertTrue(novas.contains(Conquista.PLATINA));
    }

    @Test
    public void repetirFinaisNaoDeveContarComoFinaisDiferentes() {
        progresso.registrarFinal(FinalDoJogo.SANGUE);
        progresso.registrarFinal(FinalDoJogo.SANGUE);
        progresso.registrarFinal(FinalDoJogo.SANGUE);

        assertEquals(1, progresso.getQuantidadeDeFinais());
        assertFalse(progresso.temConquista(Conquista.PLATINA));
    }

    @Test
    public void progressoRecemCriadoDeveSerValido() {
        assertDoesNotThrow(() -> progresso.validar());
    }
}
