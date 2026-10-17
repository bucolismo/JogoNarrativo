/**
 * guarda TODO o estado de uma partida em andamento
 *
 * Reúne o protagonista (atributos e inventário), a confiança dos NPCs que acompanham a história,
 * o ato atual, as rotas escolhidas e o registro das decisões tomadas. Também concentra as regras
 * de domínio que dependem desse estado: se uma escolha está liberada e quais as consequências
 * de aplicá-la. Assim o Controlador só faz a ponte entre o jogador (Menu) e o Model, e salvar
 * ou carregar uma partida é apenas gravar/restaurar este objeto.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EstadoPartida {
    private final Protagonista protagonista;
    private final NPC jonas;
    private final NPC daniel;
    private final NPC helena;

    private Ato ato;
    private int rotaAtoII;       // 1=1A, 2=1B, 3=2A, 4=2B (fim), 5=3A, 6=3B (fim); 0 = ainda não escolhida
    private int rotaAtoV;        // 1=Rota A, 2=Rota B, 3=Rota C; 0 = ainda não escolhida
    private int finalEscolhido;  // 1=Balança, 2=Névoa, 3=Sangue; 0 = ainda não escolhido
    private final List<String> decisoes;

    private boolean mostrarEfeitosDasEscolhas;

    public EstadoPartida(Protagonista protagonista, NPC jonas, NPC daniel, NPC helena) {
        this.protagonista = protagonista;
        this.jonas = jonas;
        this.daniel = daniel;
        this.helena = helena;
        this.ato = Ato.PROLOGO;
        this.decisoes = new ArrayList<>();
    }

    /**
     * cria o estado de uma partida nova: NPCs com a confiança inicial e a história no prólogo
     *
     * @param protagonista protagonista já criado (com os itens cadastrados)
     * @return estado inicial da partida
     */
    public static EstadoPartida nova(Protagonista protagonista) {
        return new EstadoPartida(
                protagonista,
                new NPC("Jonas", 30, "Masculino", ConstrutorDeCenas.CONFIANCA_INICIAL),
                new NPC("Daniel", 40, "Masculino", ConstrutorDeCenas.CONFIANCA_INICIAL),
                new NPC("Helena", 23, "Feminino", ConstrutorDeCenas.CONFIANCA_INICIAL));
    }

    public Protagonista getProtagonista() {
        return protagonista;
    }

    public NPC getJonas() {
        return jonas;
    }

    public NPC getDaniel() {
        return daniel;
    }

    public NPC getHelena() {
        return helena;
    }

    public Ato getAto() {
        return ato;
    }

    public void setAto(Ato ato) {
        this.ato = ato;
    }

    public int getRotaAtoII() {
        return rotaAtoII;
    }

    public void setRotaAtoII(int rotaAtoII) {
        this.rotaAtoII = rotaAtoII;
    }

    public int getRotaAtoV() {
        return rotaAtoV;
    }

    public void setRotaAtoV(int rotaAtoV) {
        this.rotaAtoV = rotaAtoV;
    }

    public int getFinalEscolhido() {
        return finalEscolhido;
    }

    public void setFinalEscolhido(int finalEscolhido) {
        this.finalEscolhido = finalEscolhido;
    }

    /**
     * @return as decisões tomadas até agora, em ordem (somente leitura)
     */
    public List<String> getDecisoes() {
        return Collections.unmodifiableList(decisoes);
    }

    /**
     * registra uma decisão do jogador no formato "ATO#dialogo=opcao" (ex.: "ATO_II#3=2")
     *
     * @param decisao identificador da decisão
     */
    public void registrarDecisao(String decisao) {
        decisoes.add(decisao);
    }

    /**
     * verifica se a escolha está liberada para o jogador neste momento da partida
     * (itens, atributos e confiança com NPCs exigidos pela escolha)
     *
     * @param escolha escolha que se quer verificar
     * @return null se a escolha está liberada; caso contrário, o motivo do bloqueio para mostrar ao jogador
     */
    public String motivoDeBloqueio(Escolha escolha) {
        int itemExigido = escolha.getRequisitoItem();
        if (itemExigido != -1 && !protagonista.getInventario().possuiItemPorId(itemExigido)) {
            return "Você não tem o item necessário para essa escolha.";
        }

        String atributoExigido = escolha.getRequisitoAtributo();
        if (atributoExigido != null
                && protagonista.getAtributo(atributoExigido) < escolha.getValorRequisitoAtributo()) {
            return "Você não possui o atributo necessário para essa escolha.";
        }

        NPC npcExigido = escolha.getRequisitoNpc();
        if (npcExigido != null && npcExigido.getConfianca() < escolha.getConfiancaMinima()) {
            return "Você não possui confiança suficiente com " + npcExigido.getNome() + ".";
        }

        return null;
    }

    /**
     * aplica todos os efeitos da escolha ao estado da partida
     *
     * @param escolha escolha selecionada pelo jogador (já liberada)
     * @return true se algum efeito encerra a partida (game over)
     * @throws IllegalStateException se o efeito tiver um tipo desconhecido
     */
    public boolean aplicarEfeitos(Escolha escolha) {
        boolean fimDeJogo = false;

        for (Efeito efeito : escolha.getEfeitos()) {
            switch (efeito.getTipo()) {
                case Efeito.TIPO_ATRIBUTO ->
                        protagonista.alteraAtributo(efeito.getAlvo(), efeito.getValor());

                case Efeito.TIPO_CONFIANCA ->
                        efeito.getNpc().alterarConfianca(efeito.getValor());

                case Efeito.TIPO_ADICIONAR_ITEM ->
                        protagonista.getInventario().adicionarItemPorId(efeito.getItemId());

                case Efeito.TIPO_GAME_OVER ->
                        fimDeJogo = true;

                default ->
                        throw new IllegalStateException("Tipo de efeito desconhecido: " + efeito.getTipo());
            }
        }

        return fimDeJogo;
    }

    /**
     * resumo curto da progressão, usado nos metadados do save
     *
     * @return texto como "Ato II | Razão 9, Paranoia 3, Violência 0, Provas 4 | 12 decisões"
     */
    public String getResumo() {
        StringBuilder resumo = new StringBuilder(ato.getRotulo()).append(" | ");
        boolean primeiro = true;

        for (var atributo : protagonista.getAtributos().entrySet()) {
            if (!primeiro) {
                resumo.append(", ");
            }
            resumo.append(atributo.getKey()).append(' ').append(atributo.getValue());
            primeiro = false;
        }

        resumo.append(" | ").append(decisoes.size()).append(decisoes.size() == 1 ? " decisão" : " decisões");
        return resumo.toString();
    }
    public boolean isMostrarEfeitosDasEscolhas() {
        return mostrarEfeitosDasEscolhas;
    }
    public void setMostrarEfeitosDasEscolhas(boolean valor) {
        this.mostrarEfeitosDasEscolhas = valor;
    }
}
