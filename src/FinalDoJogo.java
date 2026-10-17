/**
 * os três finais possíveis da história, decididos na escolha final do Ato VI
 *
 * O id é o mesmo número usado pelo EstadoPartida em finalEscolhido (1, 2 ou 3).
 */
public enum FinalDoJogo {
    BALANCA(1, "A Balança", "Razão"),
    NEVOA(2, "A Névoa se Levanta", "Paranoia"),
    SANGUE(3, "O Preço do Sangue", "Violência");

    private final int id;
    private final String nome;
    private final String tema;

    FinalDoJogo(int id, String nome, String tema) {
        this.id = id;
        this.nome = nome;
        this.tema = tema;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTema() {
        return tema;
    }

    /**
     * procura o final que tem o id informado
     *
     * @param id número do final (1, 2 ou 3)
     * @return o final correspondente ou null se não existir
     */
    public static FinalDoJogo porId(int id) {
        FinalDoJogo[] todos = values();

        for (int i = 0; i < todos.length; i++) {
            if (todos[i].id == id) {
                return todos[i];
            }
        }

        return null;
    }
}
