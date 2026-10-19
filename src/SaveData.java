/**
 * representação gravável de uma partida: é o que vai para o arquivo JSON de um save
 *
 * Fica separada do EstadoPartida de propósito: o JSON só conhece tipos simples (texto, números,
 * listas e mapas), então o formato do arquivo não depende de como as classes do Model estão
 * organizadas. SaveData.de(...) converte o estado em dados gravaveis e paraEstado() faz o caminho
 * inverso, validando tudo antes de montar o estado para que um save corrompido nunca chegue ao jogo.
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SaveData {
    public static final int VERSAO_ATUAL = 1;

    private int versao;
    private MetadadosSave metadados;

    private String nomeProtagonista;
    private int idadeProtagonista;
    private String generoProtagonista;
    private Map<String, Integer> atributos;
    private Map<String, Integer> confiancas;   // nome do NPC -> confiança
    private List<Integer> itens;                // ids dos itens disponíveis

    private String ato;
    private int rotaAtoII;
    private int rotaAtoV;
    private int finalEscolhido;
    private List<String> decisoes;

    public SaveData() {
    }

    /**
     * monta os dados gravaveis a partir do estado atual da partida
     *
     * @param estado estado da partida que será salvo
     * @param slot slot em que o save será gravado
     * @return dados prontos para serem convertidos em JSON
     */
    public static SaveData de(EstadoPartida estado, int slot) {
        SaveData dados = new SaveData();
        Protagonista protagonista = estado.getProtagonista();

        dados.versao = VERSAO_ATUAL;
        dados.metadados = new MetadadosSave(slot, protagonista.getNome(), LocalDateTime.now().withNano(0),
                estado.getAto().getRotulo(), estado.getResumo());

        dados.nomeProtagonista = protagonista.getNome();
        dados.idadeProtagonista = protagonista.getIdade();
        dados.generoProtagonista = protagonista.getGenero();
        dados.atributos = protagonista.getAtributos();

        dados.confiancas = new LinkedHashMap<>();
        dados.confiancas.put(estado.getJonas().getNome(), estado.getJonas().getConfianca());
        dados.confiancas.put(estado.getDaniel().getNome(), estado.getDaniel().getConfianca());
        dados.confiancas.put(estado.getHelena().getNome(), estado.getHelena().getConfianca());

        dados.itens = protagonista.getInventario().getIdsDosItensDisponiveis();

        dados.ato = estado.getAto().name();
        dados.rotaAtoII = estado.getRotaAtoII();
        dados.rotaAtoV = estado.getRotaAtoV();
        dados.finalEscolhido = estado.getFinalEscolhido();
        dados.decisoes = new ArrayList<>(estado.getDecisoes());

        return dados;
    }

    public MetadadosSave getMetadados() {
        return metadados;
    }

    /**
     * confere se os dados formam um save válido, sem montar o estado
     *
     * @throws DadosInvalidosException se algo estiver faltando, fora do intervalo ou incoerente
     */
    public void validar() throws DadosInvalidosException {
        paraEstado();
    }

    /**
     * valida os dados e reconstrói o estado da partida
     *
     * @return estado restaurado, pronto para continuar a história a partir do ato salvo
     * @throws DadosInvalidosException se o conteúdo não for um save válido
     */
    public EstadoPartida paraEstado() throws DadosInvalidosException {
        if (versao != VERSAO_ATUAL) {
            throw new DadosInvalidosException("Versão de save incompatível: " + versao
                    + " (esperada " + VERSAO_ATUAL + ").");
        }
        if (metadados == null) {
            throw new DadosInvalidosException("Save sem metadados.");
        }
        metadados.validar();

        if (nomeProtagonista == null || nomeProtagonista.isBlank()) {
            throw new DadosInvalidosException("Save sem nome do protagonista.");
        }
        if (idadeProtagonista <= 0) {
            throw new DadosInvalidosException("Idade do protagonista inválida: " + idadeProtagonista);
        }
        if (generoProtagonista == null || generoProtagonista.isBlank()) {
            throw new DadosInvalidosException("Save sem gênero do protagonista.");
        }

        Ato atoSalvo = lerAto();
        validarRotas(atoSalvo);

        Protagonista protagonista = new Protagonista(nomeProtagonista, idadeProtagonista, generoProtagonista);
        protagonista.cadastrarItens();

        restaurarAtributos(protagonista);
        restaurarItens(protagonista);

        NPC jonas = restaurarNpc("Jonas", 30, "Masculino");
        NPC daniel = restaurarNpc("Daniel", 40, "Masculino");
        NPC helena = restaurarNpc("Helena", 23, "Feminino");

        if (decisoes == null || decisoes.contains(null)) {
            throw new DadosInvalidosException("Registro de decisões inválido.");
        }

        EstadoPartida estado = new EstadoPartida(protagonista, jonas, daniel, helena);
        estado.setAto(atoSalvo);
        estado.setRotaAtoII(rotaAtoII);
        estado.setRotaAtoV(rotaAtoV);
        estado.setFinalEscolhido(finalEscolhido);
        for (String decisao : decisoes) {
            estado.registrarDecisao(decisao);
        }

        return estado;
    }

    private Ato lerAto() throws DadosInvalidosException {
        if (ato == null) {
            throw new DadosInvalidosException("Save sem ato atual.");
        }

        Ato atoLido;
        try {
            atoLido = Ato.valueOf(ato);
        } catch (IllegalArgumentException e) {
            throw new DadosInvalidosException("Ato desconhecido: " + ato, e);
        }

        if (!atoLido.ehPontoDeSalvamento()) {
            throw new DadosInvalidosException("Ato que não é ponto de salvamento: " + ato);
        }

        return atoLido;
    }

    private void validarRotas(Ato atoSalvo) throws DadosInvalidosException {
        // a rota do Ato II já foi escolhida a partir do Ato II
        boolean noAtoIIOuDepois = atoSalvo == Ato.ATO_II || atoSalvo == Ato.ATO_III || atoSalvo == Ato.ATO_IV
                || atoSalvo == Ato.ATO_V || atoSalvo == Ato.ATO_VI;

        // as rotas 4 e 6 terminam em game over dentro do Ato II, então só existe save no Ato III em diante
        // se a rota for outra
        boolean noAtoIIIOuDepois = atoSalvo == Ato.ATO_III || atoSalvo == Ato.ATO_IV
                || atoSalvo == Ato.ATO_V || atoSalvo == Ato.ATO_VI;

        // a rota do Ato V já foi escolhida (no Ato IV) a partir do Ato V
        boolean noAtoVOuDepois = atoSalvo == Ato.ATO_V || atoSalvo == Ato.ATO_VI;

        if (noAtoIIOuDepois && (rotaAtoII < 1 || rotaAtoII > 6)) {
            throw new DadosInvalidosException("Rota do Ato II inválida: " + rotaAtoII);
        }
        if (noAtoIIIOuDepois && (rotaAtoII == 4 || rotaAtoII == 6)) {
            throw new DadosInvalidosException("Rota do Ato II incompatível com o ato salvo: " + rotaAtoII);
        }
        if (noAtoVOuDepois && (rotaAtoV < 1 || rotaAtoV > 3)) {
            throw new DadosInvalidosException("Rota do Ato V inválida: " + rotaAtoV);
        }
    }

    private void restaurarAtributos(Protagonista protagonista) throws DadosInvalidosException {
        if (atributos == null) {
            throw new DadosInvalidosException("Save sem atributos.");
        }

        for (String nome : Protagonista.NOMES_DOS_ATRIBUTOS) {
            Integer valor = atributos.get(nome);

            if (valor == null) {
                throw new DadosInvalidosException("Atributo ausente no save: " + nome);
            }
            if (valor < 0 || valor > 100) {
                throw new DadosInvalidosException("Valor inválido para " + nome + ": " + valor);
            }

            protagonista.alteraAtributo(nome, valor);
        }
    }

    private void restaurarItens(Protagonista protagonista) throws DadosInvalidosException {
        if (itens == null) {
            throw new DadosInvalidosException("Save sem lista de itens.");
        }

        for (Integer id : itens) {
            if (id == null || protagonista.getInventario().itemPorId(id) == null) {
                throw new DadosInvalidosException("Item inexistente no save: " + id);
            }
            protagonista.getInventario().adicionarItemPorId(id);
        }
    }

    private NPC restaurarNpc(String nome, int idade, String genero) throws DadosInvalidosException {
        if (confiancas == null) {
            throw new DadosInvalidosException("Save sem confiança dos NPCs.");
        }

        Integer confianca = confiancas.get(nome);

        if (confianca == null) {
            throw new DadosInvalidosException("Confiança ausente no save: " + nome);
        }
        if (confianca < 0 || confianca > 100) {
            throw new DadosInvalidosException("Confiança inválida para " + nome + ": " + confianca);
        }

        return new NPC(nome, idade, genero, confianca);
    }
}
