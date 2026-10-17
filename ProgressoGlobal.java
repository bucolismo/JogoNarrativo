/**
 * guarda o progresso do jogador que vale para o jogo todo, e não para uma partida só:
 * os finais já alcançados (registro de finais) e as conquistas desbloqueadas
 *
 * Diferente dos saves, esse progresso não some quando um save é apagado nem quando uma nova partida
 * começa. Ele é gravado em um arquivo JSON próprio (ver RepositorioDeProgresso).
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProgressoGlobal {
    public static final int TOTAL_DE_FINAIS = FinalDoJogo.values().length;

    private static final DateTimeFormatter FORMATO_DA_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private List<String> finaisAlcancados;              // nomes do enum FinalDoJogo (ex.: "SANGUE")
    private List<ConquistaDesbloqueada> conquistas;

    public ProgressoGlobal() {
        this.finaisAlcancados = new ArrayList<>();
        this.conquistas = new ArrayList<>();
    }

    /**
     * registra que o jogador alcançou um final e desbloqueia as conquistas que isso garante
     * (a do próprio final e, se foi o último que faltava, a Platina)
     *
     * @param alcancado final que acabou de ser alcançado
     * @return as conquistas desbloqueadas agora, em ordem (vazia se o final já tinha sido alcançado antes)
     */
    public List<Conquista> registrarFinal(FinalDoJogo alcancado) {
        List<Conquista> novas = new ArrayList<>();

        if (!alcancouFinal(alcancado)) {
            finaisAlcancados.add(alcancado.name());
        }

        Conquista[] todas = Conquista.values();

        for (int i = 0; i < todas.length; i++) {
            if (!temConquista(todas[i]) && mereceConquista(todas[i])) {
                String agora = LocalDateTime.now().format(FORMATO_DA_DATA);

                conquistas.add(new ConquistaDesbloqueada(todas[i].name(), agora));
                novas.add(todas[i]);
            }
        }

        return novas;
    }

    private boolean mereceConquista(Conquista conquista) {
        if (conquista.getFinalExigido() == null) {
            return getQuantidadeDeFinais() == TOTAL_DE_FINAIS;
        }

        return alcancouFinal(conquista.getFinalExigido());
    }

    public boolean alcancouFinal(FinalDoJogo alvo) {
        return finaisAlcancados.contains(alvo.name());
    }

    /**
     * @return quantos finais diferentes o jogador já alcançou (de 0 a TOTAL_DE_FINAIS)
     */
    public int getQuantidadeDeFinais() {
        int quantidade = 0;
        FinalDoJogo[] todos = FinalDoJogo.values();

        for (int i = 0; i < todos.length; i++) {
            if (alcancouFinal(todos[i])) {
                quantidade++;
            }
        }

        return quantidade;
    }

    public boolean temConquista(Conquista conquista) {
        return getDataDaConquista(conquista) != null;
    }

    /**
     * @return a data e hora em que a conquista foi desbloqueada, ou null se ainda não foi
     */
    public String getDataDaConquista(Conquista conquista) {
        for (int i = 0; i < conquistas.size(); i++) {
            if (conquistas.get(i).getId().equals(conquista.name())) {
                return conquistas.get(i).getDataHora();
            }
        }

        return null;
    }

    /**
     * confere se o progresso lido de um arquivo faz sentido (listas presentes e só com finais
     * e conquistas que existem no jogo)
     *
     * @throws DadosInvalidosException se algo estiver faltando ou for desconhecido
     */
    public void validar() throws DadosInvalidosException {
        if (finaisAlcancados == null) {
            throw new DadosInvalidosException("Progresso sem a lista de finais alcançados.");
        }
        if (conquistas == null) {
            throw new DadosInvalidosException("Progresso sem a lista de conquistas.");
        }

        for (int i = 0; i < finaisAlcancados.size(); i++) {
            if (!existeFinalComNome(finaisAlcancados.get(i))) {
                throw new DadosInvalidosException("Final desconhecido no progresso: " + finaisAlcancados.get(i));
            }
        }

        for (int i = 0; i < conquistas.size(); i++) {
            ConquistaDesbloqueada registro = conquistas.get(i);

            if (registro == null || registro.getId() == null || !existeConquistaComNome(registro.getId())) {
                throw new DadosInvalidosException("Conquista desconhecida no progresso.");
            }
            if (registro.getDataHora() == null) {
                throw new DadosInvalidosException("Conquista sem data no progresso: " + registro.getId());
            }
        }
    }

    private boolean existeFinalComNome(String nome) {
        FinalDoJogo[] todos = FinalDoJogo.values();

        for (int i = 0; i < todos.length; i++) {
            if (todos[i].name().equals(nome)) {
                return true;
            }
        }

        return false;
    }

    private boolean existeConquistaComNome(String nome) {
        Conquista[] todas = Conquista.values();

        for (int i = 0; i < todas.length; i++) {
            if (todas[i].name().equals(nome)) {
                return true;
            }
        }

        return false;
    }
}
