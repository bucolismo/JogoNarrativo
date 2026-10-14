/**
 * contrato de armazenamento dos saves
 *
 * O jogo só depende desta interface; hoje a implementação grava arquivos JSON (RepositorioJson),
 * mas poderia ser outro formato ou local sem alterar o Controlador.
 *
 * Slots: 0 é o salvamento automático (só o jogo grava); 1 a 3 são os slots manuais.
 */

public interface RepositorioDeSaves {
    int SLOT_AUTOMATICO = 0;
    int PRIMEIRO_SLOT_MANUAL = 1;
    int ULTIMO_SLOT_MANUAL = 3;

    /**
     * grava a partida no slot, substituindo o que existir nele
     *
     * @throws SaveException se o slot for inválido ou a gravação falhar
     */
    void salvar(int slot, EstadoPartida estado) throws SaveException;

    /**
     * lê e valida o save do slot e reconstrói o estado da partida
     *
     * @throws CarregamentoException se o slot estiver vazio ou o arquivo não puder ser lido
     * @throws DadosInvalidosException se o conteúdo não for um save válido
     */
    EstadoPartida carregar(int slot) throws CarregamentoException;

    /**
     * @return metadados do save do slot, ou null se o slot estiver vazio
     * @throws CarregamentoException se o arquivo existir mas estiver ilegível ou inválido
     */
    MetadadosSave lerMetadados(int slot) throws CarregamentoException;

    /**
     * @return true se existe um arquivo de save no slot
     */
    boolean existe(int slot);

    /**
     * apaga o save do slot (não faz nada se ele estiver vazio)
     *
     * @throws SaveException se o slot for inválido ou a exclusão falhar
     */
    void excluir(int slot) throws SaveException;
}
