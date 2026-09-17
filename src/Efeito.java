public class Efeito {

    private String tipo;
    private String alvo;
    private int valor;

    public Efeito(String tipo, String alvo, int valor) {
        this.tipo = tipo;
        this.alvo = alvo;
        this.valor = valor;
    }

    public String getTipo() {
        return tipo;
    }

    public String getAlvo() {
        return alvo;
    }

    public int getValor() {
        return valor;
    }
}
