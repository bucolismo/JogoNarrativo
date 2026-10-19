/**
 * falha ao ler um save (slot vazio, arquivo ilegível...)
 */
public class CarregamentoException extends PersistenciaException {
    public CarregamentoException(String mensagem) {
        super(mensagem);
    }

    public CarregamentoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
