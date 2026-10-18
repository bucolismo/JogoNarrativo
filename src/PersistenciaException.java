/**
 * base das falhas de persistência (saves, carregamento e dados do jogo)
 */
public class PersistenciaException extends Exception {
    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
