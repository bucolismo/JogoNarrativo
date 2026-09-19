import java.util.ArrayList;

public class Escolha {

    private String texto;
    private ArrayList<Efeito> efeitos;
    private int requisitoItem;

    public Escolha(String texto) {
        this.texto = texto;
        this.efeitos = new ArrayList<>();
        this.requisitoItem = -1;
    }

    public Escolha(String texto, Efeito efeito) {
        this.texto = texto;
        this.efeitos = new ArrayList<>();
        this.efeitos.add(efeito);
        this.requisitoItem = -1;
    }

    public Escolha(String texto, ArrayList<Efeito> efeitos) {
        this.texto = texto;
        this.efeitos = efeitos;
        this.requisitoItem = -1;
    }

    public void defineRequisitoItem(int id) {
        this.requisitoItem = id;
    }

    public int getRequisitoItem() {
        return requisitoItem;
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
