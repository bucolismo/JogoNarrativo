/**
 * informações exibidas na lista de saves: quem jogou, quando, onde parou e um resumo da progressão
 */

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MetadadosSave {
    private static final DateTimeFormatter FORMATO_EXIBICAO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private int slot;
    private String nomeJogador;
    private String dataHora;   // ISO-8601, ex.: 2026-10-11T14:32:05
    private String ato;        // rótulo legível, ex.: "Ato II"
    private String resumo;

    public MetadadosSave() {
    }

    public MetadadosSave(int slot, String nomeJogador, LocalDateTime dataHora, String ato, String resumo) {
        this.slot = slot;
        this.nomeJogador = nomeJogador;
        this.dataHora = dataHora.toString();
        this.ato = ato;
        this.resumo = resumo;
    }

    public int getSlot() {
        return slot;
    }

    public String getNomeJogador() {
        return nomeJogador;
    }

    public String getAto() {
        return ato;
    }

    public String getResumo() {
        return resumo;
    }

    /**
     * @return data e hora do save
     * @throws DadosInvalidosException se o texto gravado não for uma data/hora válida
     */
    public LocalDateTime getDataHora() throws DadosInvalidosException {
        try {
            return LocalDateTime.parse(dataHora);
        } catch (DateTimeParseException | NullPointerException e) {
            throw new DadosInvalidosException("Data e hora do save inválidas.", e);
        }
    }

    /**
     * confere se todos os campos estão preenchidos e coerentes
     *
     * @throws DadosInvalidosException se algum campo estiver faltando ou inválido
     */
    public void validar() throws DadosInvalidosException {
        if (nomeJogador == null || nomeJogador.isBlank()) {
            throw new DadosInvalidosException("Save sem nome do jogador.");
        }
        if (ato == null || ato.isBlank()) {
            throw new DadosInvalidosException("Save sem capítulo/cena.");
        }
        if (resumo == null) {
            throw new DadosInvalidosException("Save sem resumo da progressão.");
        }
        getDataHora();
    }

    /**
     * @return descrição de uma linha para a lista de saves
     */
    public String descricao() {
        String quando;

        try {
            quando = getDataHora().format(FORMATO_EXIBICAO);
        } catch (DadosInvalidosException e) {
            quando = "data desconhecida";
        }

        return nomeJogador + " — " + quando + " — " + ato + " — " + resumo;
    }
}
