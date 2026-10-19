/**
 * preferências do jogador que valem para o jogo todo e são mantidas entre as execuções
 *
 * São três: mostrar as mudanças de atributos e relações depois de cada escolha, fazer uma pausa
 * (ENTER para continuar) entre os diálogos, e o personagem favorito. Ficam em um arquivo JSON
 * próprio (ver RepositorioDeConfiguracoes), separado dos saves.
 */
public class Configuracoes {
    private boolean mostrarEfeitosDasEscolhas;
    private boolean pausaEntreDialogos;
    private PersonagemFavorito personagemFavorito;   // null se o jogador não tiver um favorito

    public Configuracoes() {
        this.mostrarEfeitosDasEscolhas = false;
        this.pausaEntreDialogos = false;
        this.personagemFavorito = null;
    }

    public boolean isMostrarEfeitosDasEscolhas() {
        return mostrarEfeitosDasEscolhas;
    }

    public void setMostrarEfeitosDasEscolhas(boolean mostrarEfeitosDasEscolhas) {
        this.mostrarEfeitosDasEscolhas = mostrarEfeitosDasEscolhas;
    }

    public boolean isPausaEntreDialogos() {
        return pausaEntreDialogos;
    }

    public void setPausaEntreDialogos(boolean pausaEntreDialogos) {
        this.pausaEntreDialogos = pausaEntreDialogos;
    }

    public PersonagemFavorito getPersonagemFavorito() {
        return personagemFavorito;
    }

    public void setPersonagemFavorito(PersonagemFavorito personagemFavorito) {
        this.personagemFavorito = personagemFavorito;
    }

    public boolean temPersonagemFavorito() {
        return personagemFavorito != null;
    }

    /**
     * confere se as configurações lidas de um arquivo fazem sentido
     *
     * @throws DadosInvalidosException se o personagem favorito existir mas estiver inválido
     */
    public void validar() throws DadosInvalidosException {
        if (personagemFavorito != null) {
            personagemFavorito.validar();
        }
    }
}
