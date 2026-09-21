/**
 * Essa classe representa uma opção de escolha apresentada ao jogador durante um diálogo qualquer
 *
 * Uma escolha pode exigir requisitos (item, atributo mínimo ou confiança
 * mínima com um NPC) para ficar disponível (que é a restrição de acesso),
 * e pode aplicar um ou mais efeito sobre o estado do jogo quando selecionada
 * e que por sua veza ltera os atributos do protagonista, confiança de NPCs ou o inventário.
 */

import java.util.ArrayList;

public class Escolha {

    private String texto;
    private ArrayList<Efeito> efeitos;
    private int requisitoItem;
    private String requisitoAtributo;
    private int valorRequisitoAtributo;
    private NPC requisitoNpc;
    private int confiancaMinima;
    private String textoConsequencia;

    // Construtor básico
    public Escolha(String texto) {
        this.texto = texto;
        this.efeitos = new ArrayList<>();
        this.requisitoItem = -1;
        this.requisitoAtributo = null;
        this.valorRequisitoAtributo = 0;
        this.requisitoNpc = null;
        this.confiancaMinima = 0;
        this.textoConsequencia = null;
    }

    // Construtor com um efeito
    public Escolha(String texto, Efeito efeito) {
        this(texto);
        this.efeitos.add(efeito);
    }

    // Construtor com um efeito e consequência
    public Escolha(String texto, Efeito efeito, String textoConsequencia) {
        this(texto, efeito);
        this.textoConsequencia = textoConsequencia;
    }

    // Construtor com lista de efeitos
    public Escolha(String texto, ArrayList<Efeito> efeitos) {
        this(texto);
        this.efeitos = efeitos;
    }

    // Construtor com lista de efeitos e consequência
    public Escolha(String texto, ArrayList<Efeito> efeitos, String textoConsequencia) {
        this(texto, efeitos);
        this.textoConsequencia = textoConsequencia;
    }

    /**
     * Define um item como requisito para que a escolha fique disponível
     * é o que permite fazermos a restriação de acesso
     *
     * @param id identificador (índice) do item necessário
     */
    public void defineRequisitoItem(int id) {
        this.requisitoItem = id;
    }

    public int getRequisitoItem() {
        return requisitoItem;
    }


    /**
     * Define um atributo e o valor mínimo como requisito para que a escolha
     * fique disponível.
     *
     * @param atributo nome do atributo exigido (como "Violência, Paranoia ou Razão)
     * @param valor valor mínimo a ser atingodo do atributo
     */

    public void defineRequisitoAtributo(String atributo, int valor) {
        this.requisitoAtributo = atributo;
        this.valorRequisitoAtributo = valor;
    }

    public String getRequisitoAtributo() {
        return requisitoAtributo;
    }

    public int getValorRequisitoAtributo() {
        return valorRequisitoAtributo;
    }

    // Requisito de confiança com NPC
    public void defineRequisitoConfianca(NPC npc, int confiancaMinima) {
        this.requisitoNpc = npc;
        this.confiancaMinima = confiancaMinima;
    }

    public NPC getRequisitoNpc() {
        return requisitoNpc;
    }

    public int getConfiancaMinima() {
        return confiancaMinima;
    }

    // Texto da escolha
    public String getTexto() {
        return texto;
    }

    // Efeitos
    public ArrayList<Efeito> getEfeitos() {
        return efeitos;
    }

    /**
     * Adiciona um efeito à escolha.
     *
     * @param efeito efeito que será aplicado quando a escolha for selecionada
     */
    public void adicionaEfeito(Efeito efeito) {
        efeitos.add(efeito);
    }

    /**
     * checa se a escolha possui algum efeito
     *
     * @return true se a escolha possuir pelo menos um efeito, false se não tiver
     */
    public boolean possuiEfeitos() {
        return !efeitos.isEmpty();
    }

    /**
     * define o texto que será apresentado como consequência da escolha
     *
     * @param consequencia texto da consequência
     */
    public void adicionaConsequencia(String consequencia) {
        this.textoConsequencia = consequencia;
    }

    public String getTextoConsequencia() {
        return textoConsequencia;
    }
}
