import java.util.ArrayList;
import java.util.List;

public class Cena {

    private List<Dialogo> dialogos;

    public Cena() {
        this.dialogos = new ArrayList<>();
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

}
