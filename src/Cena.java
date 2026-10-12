/**
 * unidade de cena da narrativa e organiza os diálogos que fazem parte dela
 *
 * Uma cena possui um índice para identificaç~ao e uma lista de diálogos, que são
 * apresentados ao jogador durante a partida.
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Cena {

    private List<Dialogo> dialogos;
    private String cenaId;

    public Cena() {
        this.dialogos = new ArrayList<>();
        this.cenaId = null  ;
    }

    public Cena(String cenaId) {
        this.dialogos = new ArrayList<>();
        this.cenaId = cenaId;
    }

    /**
     * adiciona um diálogo à cenaa
     *
     * @param dialogo é o diálogo que será adicionado à cena
     */
    public void adicionaDialogo(Dialogo dialogo) {
        this.dialogos.add(dialogo);
    }

    public int getQuantidadeDialogos() {
        return this.dialogos.size();
    }

    public List<Dialogo> getDialogos() {
        return this.dialogos;
    }

    public String getCenaId(){
        return cenaId;
    }
}
