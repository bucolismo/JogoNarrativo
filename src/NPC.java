public class NPC extends Personagem {
    private int confianca;

    public NPC(String nome, int idade, String genero, int confiancaInicial) {
        super(nome, idade, genero);
        this.confianca = 0;
        this.confianca = confiancaInicial;
    }

    public void alterarConfianca(int quantidade){
        this.confianca += quantidade;
        validaConfianca();
    }

    public int getConfianca(){
        return this.confianca;
    }

    private void validaConfianca() {
        if (confianca > 100) {
            confianca = 100;
        } else if (confianca < 0) {
            confianca = 0;
        }
    }
}
