import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dialogo {

    private Personagem personagem;
    private String texto;
    private List<Escolha> escolhas;

    // Construtor 1: Diálogo sem opções (apenas narrativa/fala)
    public Dialogo(Personagem personagem, String texto) {
        this.personagem = personagem;
        this.texto = texto;
        this.escolhas = null;
    }


    public Dialogo(Personagem personagem, String texto, Escolha... escolhas) {
        this.personagem = personagem;
        this.texto = texto;

        if (escolhas != null && escolhas.length > 0) {
            this.escolhas = new ArrayList<>(Arrays.asList(escolhas));
        } else {
            this.escolhas = null;
        }
    }

    // Retorna o Arraydinâmico como um array normal
    public Escolha[] getEscolhas() {
        if (escolhas == null) {
            return null;
        }
        return escolhas.toArray(new Escolha[0]);
    }


    public List<Escolha> getListaEscolhas() {
        return escolhas;
    }

    public String getTexto() {
        return texto;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public boolean possuiOpcoes() {
        return escolhas != null && !escolhas.isEmpty();
    }

    // Robocop Fortaleceu aqui, simula o acesso por indice do array comum
    public Escolha getEscolha(int indice) {
        if (escolhas == null || indice < 1 || indice > escolhas.size()) {
            return null;
        }
        return escolhas.get(indice - 1);
    }

    public void adicionaEscolha(Escolha novaEscolha) {
        this.escolhas.add(novaEscolha);
    }

    public int getQuantidadeEscolhas() {
        return escolhas.size();
    }
}