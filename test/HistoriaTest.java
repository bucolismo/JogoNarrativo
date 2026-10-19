import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * testes de integridade da história: garantem que o conteúdo narrativo (cenas em JSON e cenas em Java)
 * só referencia itens, atributos, personagens e efeitos que realmente existem no motor do jogo.
 * Foi esse tipo de erro que deixava escolhas bloqueadas para sempre e finais inalcançáveis.
 */
public class HistoriaTest {

    private EstadoPartida estado;
    private List<Cena> cenas;

    @BeforeEach
    public void montaTodasAsCenas() throws Exception {
        estado = EstadoPartida.nova(new Protagonista("Teste", 20, "Neutro"));
        ConstrutorDeCenas c = new ConstrutorDeCenas(estado);

        cenas = new ArrayList<>();

        // carregadas dos JSON
        cenas.add(c.criarPrologo());
        cenas.add(c.criarAtoI());
        cenas.add(c.criarFinalAtoI());
        cenas.add(c.criarAtoIIRota1A());
        cenas.add(c.criarAtoIIRota1B());
        cenas.add(c.criarAtoIIRota2A());
        cenas.add(c.criarAtoIIRota2B());
        cenas.add(c.criarAtoIIRota3A());
        cenas.add(c.criarAtoIIRota3B());
        cenas.add(c.criarAtoIIIPerspectiva1A());
        cenas.add(c.criarAtoIIIPerspectiva1B());
        cenas.add(c.criarAtoIIIPerspectiva2A());
        cenas.add(c.criarAtoIIIPerspectiva3A());

        // montadas em Java
        cenas.add(c.criarAtoIV());
        cenas.add(c.criarAtoVRota1A());
        cenas.add(c.criarAtoVRota1B());
        cenas.add(c.criarAtoVRota1C());
        cenas.add(c.criarAtoVI());
        cenas.add(c.criarFinalBalanca());
        cenas.add(c.criarFinalNevoa());
        cenas.add(c.criarFinalSangue());
    }

    private Protagonista protagonistaComCatalogo() {
        Protagonista p = new Protagonista("Teste", 20, "Neutro");
        p.cadastrarItens();
        return p;
    }

    @Test
    public void todasAsCenasDevemTerDialogos() {
        assertEquals(21, cenas.size());

        for (Cena cena : cenas) {
            assertFalse(cena.getDialogos().isEmpty());
        }
    }

    @Test
    public void todosOsItensConcedidosPelaHistoriaDevemExistirNoCatalogo() {
        Protagonista p = protagonistaComCatalogo();

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    for (Efeito efeito : escolha.getEfeitos()) {
                        if (efeito.getTipo().equals(Efeito.TIPO_ADICIONAR_ITEM)) {
                            assertNotNull(p.getInventario().itemPorId(efeito.getItemId()),
                                    "item inexistente no catálogo: " + efeito.getItemId());
                        }
                    }
                }
            }
        }
    }

    @Test
    public void todosOsItensExigidosPelaHistoriaDevemExistirNoCatalogo() {
        Protagonista p = protagonistaComCatalogo();

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    int requisito = escolha.getRequisitoItem();
                    if (requisito != -1) {
                        assertNotNull(p.getInventario().itemPorId(requisito),
                                "requisito de item inexistente no catálogo: " + requisito);
                    }
                }
            }
        }
    }

    @Test
    public void todosOsAtributosUsadosNaHistoriaDevemExistir() {
        Protagonista p = new Protagonista("Teste", 20, "Neutro");

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    if (escolha.getRequisitoAtributo() != null) {
                        assertTrue(p.possuiAtributo(escolha.getRequisitoAtributo()),
                                "requisito de atributo inexistente: " + escolha.getRequisitoAtributo());
                    }
                    for (Efeito efeito : escolha.getEfeitos()) {
                        if (efeito.getTipo().equals(Efeito.TIPO_ATRIBUTO)) {
                            assertTrue(p.possuiAtributo(efeito.getAlvo()),
                                    "efeito em atributo inexistente: " + efeito.getAlvo());
                        }
                    }
                }
            }
        }
    }

    @Test
    public void todosOsEfeitosDevemTerTipoConhecido() {
        List<String> conhecidos = List.of(Efeito.TIPO_ATRIBUTO, Efeito.TIPO_CONFIANCA,
                Efeito.TIPO_ADICIONAR_ITEM, Efeito.TIPO_GAME_OVER);

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    for (Efeito efeito : escolha.getEfeitos()) {
                        assertTrue(conhecidos.contains(efeito.getTipo()), "tipo desconhecido: " + efeito.getTipo());
                    }
                }
            }
        }
    }

    @Test
    public void ninguemDeveComecarJaCumprindoRequisitoDeAtributoDeAcesso() {
        Protagonista p = new Protagonista("Teste", 20, "Neutro");

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    if (escolha.getRequisitoAtributo() != null) {
                        assertTrue(p.getAtributo(escolha.getRequisitoAtributo()) < escolha.getValorRequisitoAtributo(),
                                "requisito sempre satisfeito: " + escolha.getRequisitoAtributo());
                    }
                }
            }
        }
    }

    @Test
    public void ninguemDeveComecarJaCumprindoRequisitoDeConfianca() {
        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    if (escolha.getRequisitoNpc() != null) {
                        assertTrue(escolha.getRequisitoNpc().getConfianca() < escolha.getConfiancaMinima(),
                                "requisito de confiança sempre satisfeito: " + escolha.getRequisitoNpc().getNome());
                    }
                }
            }
        }
    }

    @Test
    public void efeitosERequisitosDevemApontarParaOsNpcsDoEstadoDaPartida() {
        // é isso que faz um save carregado valer também para as cenas: elas agem sobre os NPCs restaurados
        List<NPC> npcsDoEstado = List.of(estado.getJonas(), estado.getDaniel(), estado.getHelena());

        for (Cena cena : cenas) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    if (escolha.getRequisitoNpc() != null) {
                        assertTrue(npcsDoEstado.contains(escolha.getRequisitoNpc()));
                    }
                    for (Efeito efeito : escolha.getEfeitos()) {
                        if (efeito.getTipo().equals(Efeito.TIPO_CONFIANCA)) {
                            assertTrue(npcsDoEstado.contains(efeito.getNpc()), "NPC fora do estado: " + efeito.getNpc().getNome());
                        }
                    }
                }
            }
        }
    }

    @Test
    public void escolhasComGameOverDevemExistirNasCenasEmJson() throws Exception {
        ConstrutorDeCenas c = new ConstrutorDeCenas(estado);
        int gameOvers = 0;

        for (Cena cena : List.of(c.criarAtoIIIPerspectiva1A(), c.criarAtoIIIPerspectiva1B(),
                c.criarAtoIIIPerspectiva2A(), c.criarAtoIIIPerspectiva3A())) {
            for (Dialogo dialogo : cena.getDialogos()) {
                for (Escolha escolha : dialogo.getEscolhas()) {
                    for (Efeito efeito : escolha.getEfeitos()) {
                        if (efeito.getTipo().equals(Efeito.TIPO_GAME_OVER)) {
                            gameOvers++;
                        }
                    }
                }
            }
        }

        assertEquals(4, gameOvers);
    }
}
