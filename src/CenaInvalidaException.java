/**
 * falha ao carregar uma cena da história: arquivo JSON ausente, malformado ou com conteúdo
 * que o jogo não consegue montar (personagem, item, atributo ou tipo de efeito desconhecido...)
 */

public class CenaInvalidaException extends PersistenciaException {
    public CenaInvalidaException(String mensagem) {
        super(mensagem);
    }

    public CenaInvalidaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
