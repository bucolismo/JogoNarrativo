/**
 * falha ao gravar ou excluir um save (disco cheio, sem permissão, slot inválido...)
 */
public class SaveException extends PersistenciaException {
    public SaveException(String mensagem) {
        super(mensagem);
    }

    public SaveException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
