/**
 * as conquistas que o jogador pode desbloquear
 *
 * Existe uma conquista para cada final (finalExigido) e a Platina, que não depende de um final
 * específico: é desbloqueada quando todos os finais foram alcançados. A Platina é "oculta":
 * enquanto não for desbloqueada, aparece na tela como "???" apenas com uma dica.
 */
public enum Conquista {
    FINAL_RAZAO("Final da Razão: A Balança",
            "Atingir o final A Balança, o caminho da Razão.",
            FinalDoJogo.BALANCA, false),

    FINAL_PARANOIA("Final da Paranoia: A Névoa se Levanta",
            "Atingir o final A Névoa se Levanta, o caminho da Paranoia.",
            FinalDoJogo.NEVOA, false),

    FINAL_VIOLENCIA("Final da Violência: O Preço do Sangue",
            "Atingir o final O Preço do Sangue, o caminho da Violência.",
            FinalDoJogo.SANGUE, false),

    PLATINA("Platina",
            "Atingir todos os finais do jogo.",
            null, true);

    private final String titulo;
    private final String descricao;
    private final FinalDoJogo finalExigido;
    private final boolean oculta;

    Conquista(String titulo, String descricao, FinalDoJogo finalExigido, boolean oculta) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.finalExigido = finalExigido;
        this.oculta = oculta;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * @return o final que desbloqueia a conquista, ou null se ela não depende de um final específico (Platina)
     */
    public FinalDoJogo getFinalExigido() {
        return finalExigido;
    }

    public boolean isOculta() {
        return oculta;
    }
}
