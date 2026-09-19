import java.util.ArrayList;

public class Escolha {
    private String texto;
    private ArrayList<Efeito> efeitos;
    private int requisitoItem;
    private String requisitoAtributo;
    private int valorRequisitoAtributo;
    private NPC requisitoNpc;
    private int confiancaMinima;

    public Escolha(String texto) {
        this.texto = texto;
        this.efeitos = new ArrayList<>();
        this.requisitoItem = -1;
        this.requisitoAtributo = null;
        this.valorRequisitoAtributo = 0;
        this.requisitoNpc = null;
        this.confiancaMinima = 0;
    }

    public Escolha(String texto, Efeito efeito) {
        this.texto = texto;
        this.efeitos = new ArrayList<>();
        this.efeitos.add(efeito);
        this.requisitoItem = -1;
        this.requisitoAtributo = null;
        this.valorRequisitoAtributo = 0;
        this.requisitoNpc = null;
        this.confiancaMinima = 0;
    }

    public Escolha(String texto, ArrayList<Efeito> efeitos) {
        this.texto = texto;
        this.efeitos = efeitos;
        this.requisitoItem = -1;
        this.requisitoAtributo = null;
        this.valorRequisitoAtributo = 0;
        this.requisitoNpc = null;
        this.confiancaMinima = 0;
    }

    public void defineRequisitoItem(int id) {
        this.requisitoItem = id;
    }

    public int getRequisitoItem() {
        return requisitoItem;
    }

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

    /**
     * módulos de requisito (restrição de acesso) da interação do protagonista com a confiança do NPC
     */

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

    public String getTexto() {
        return texto;
    }

    public ArrayList<Efeito> getEfeitos() {
        return efeitos;
    }

    public void adicionaEfeito(Efeito efeito) {
        efeitos.add(efeito);
    }

    public boolean possuiEfeitos() {
        return !efeitos.isEmpty();
    }
}
