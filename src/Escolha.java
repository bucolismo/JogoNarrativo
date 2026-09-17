public class Escolha {

    private String texto;
    private String atributoAfetado;
    private int valorAlteracao;

    public Escolha(String texto) {
        this.texto = texto;
        this.atributoAfetado = null;
        this.valorAlteracao = 0;
    }

    public Escolha(String texto, String atributoAfetado, int valorAlteracao) {
        this.texto = texto;
        this.atributoAfetado = atributoAfetado;
        this.valorAlteracao = valorAlteracao;
    }

    public String getTexto() {
        return texto;
    }

    public String getAtributoAfetado() {
        return atributoAfetado;
    }

    public int getValorAlteracao() {
        return valorAlteracao;
    }

    public boolean afetaAtributo() {
        return atributoAfetado != null;
    }
}
