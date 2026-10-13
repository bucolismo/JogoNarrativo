/**
 * o arquivo foi lido, mas o conteúdo não é um save válido (JSON malformado, campo faltando,
 * valor fora do intervalo, item inexistente, versão incompatível...)
 */

public class DadosInvalidosException extends CarregamentoException {
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }

    public DadosInvalidosException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
