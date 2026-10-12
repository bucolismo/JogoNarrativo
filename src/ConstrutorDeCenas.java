import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstrutorDeCenas {

    private final Map<String, Personagem> elenco;
    private final Map<String, Efeito> predefinidos = new HashMap<>();

    private Cena cenaAtual;
    private String cenaID;

    // ... suas constantes de CONFIANCA_* e LIMITE_* continuam iguais ...

    // Os campos de efeito continuam aqui, porque os atos ainda não migrados usam
    Efeito aumentaRazao = new Efeito("ATRIBUTO", "Razão", 3);
    Efeito aumentaParanioa = new Efeito("ATRIBUTO", "Paranoia", 3);
    Efeito aumentaViolencia = new Efeito("ATRIBUTO", "Violência", 3);
    Efeito ganhaProva = new Efeito("ATRIBUTO", "Provas", 1);

    public ConstrutorDeCenas(Map<String, Personagem> elenco) {
        this.elenco = elenco;
        this.cenaAtual = new Cena();

        // Os campos acima já foram inicializados neste ponto, então reaproveitamos as instâncias
        predefinidos.put("aumentaRazao", aumentaRazao);
        predefinidos.put("aumentaParanoia", aumentaParanioa);
        predefinidos.put("aumentaViolencia", aumentaViolencia);
        predefinidos.put("ganhaProva", ganhaProva);
    }

    public Cena criarPrologo() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/prologo.json", elenco, predefinidos);
    }

    public Cena criarAtoI() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato_1.json", elenco, predefinidos);
    }

    public Cena criarFinalAtoI() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/fim_ato_1.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota1B() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_opcao1b.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota2B() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_rota2b.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota3B() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_rota3b.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota1A() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_opcao1a.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota2A() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_rota2a.json", elenco, predefinidos);
    }

    public Cena criarAtoIIRota3A() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato2_rota3a.json", elenco, predefinidos);
    }
    public Cena criarAtoIII1A() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato3_perspectiva1a.json", elenco, predefinidos);
    }

    public Cena criarAtoIII1B() throws IOException {
        return CarregadorDeCenas.montarCena("/cenas/ato3_perspectiva1b.json", elenco, predefinidos);
    }
}
