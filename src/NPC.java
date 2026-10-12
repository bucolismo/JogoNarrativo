/**
 * classe que representa os personagens não jogáveis da narrativa do jogo
 *
 * O único atributo do NPC é a confiança em relação ao protagonista,
 * que é mútua; não existe diferença entre a confiança do protagonista para o NPC X e o inverso.
 * O atributo pode ser alterado durante a partida e permanece limitado entre 0 e 100.
 *
 */
public class NPC extends Personagem {
    private int confianca;

    public NPC(String nome, int idade, String genero, int confiancaInicial) {
        super(nome, idade, genero);
        this.confianca = 0;
        this.confianca = confiancaInicial;
    }


    /**
     * muda o valor da relação de confiança entre o protagonista e o npc
     *
     * esse método, através do outro método descrito abaixo "validaConfiança" já se encarrega
     * de manter o valor variando entre 0 e 100
     * @param quantidade valor que será acrescentado ou subtraído da confiança
     */
    public void alterarConfianca(int quantidade){
        this.confianca += quantidade;
        validaConfianca();
    }

    public int getConfianca(){
        return this.confianca;
    }

    /**
     * método que mantem o nível de confiança dentro dos limites permitidos
     */
    private void validaConfianca() {
        if (confianca > 100) {
            confianca = 100;
        } else if (confianca < 0) {
            confianca = 0;
        }
    }
}
