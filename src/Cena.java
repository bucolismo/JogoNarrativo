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
