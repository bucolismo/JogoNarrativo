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

    // Requisito de item
    public void defineRequisitoItem(int id) {
        this.requisitoItem = id;
    }

    public int getRequisitoItem() {
        return requisitoItem;
    }

    // Requisito de atributo
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

    public void adicionaEfeito(Efeito efeito) {
        efeitos.add(efeito);
    }

    public boolean possuiEfeitos() {
        return !efeitos.isEmpty();
    }

    // Consequência
    public void adicionaConsequencia(String consequencia) {
        this.textoConsequencia = consequencia;
    }

    public String getTextoConsequencia() {
        return textoConsequencia;
    }
}
