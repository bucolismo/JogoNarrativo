/**
 * representa os atributo do personagem e seu valor atual
 *
 * O valor do atributo varia de 0 ao valor maximo informado na inicialização
 *
 */
public class Atributo {
    private String nome;
    private int valor;
    private int valorMaximo;

    public Atributo(String nome, int valor, int valorMaximo) {
        this.nome = nome;
        this.valorMaximo = valorMaximo;

        if (valor < 0) {
            this.valor = 0;
        } else if (valor > valorMaximo) {
            this.valor = valorMaximo;
        } else {
            this.valor = valor;
        }
    }

    public String getNome() {
        return this.nome;
    }

    public int getValor() {
        return this.valor;
    }

    public int getValorMaximo() {
        return this.valorMaximo;
    }

    /**
     * aumenta o valor do atributo pela quantidade informada
     * se quantidade for menor ou igual a zero não altera o valor do atributo
     * o valor do atributo também nunca passa do valor máximo
     *
     * @param quantidade valor que será acrescentado ao atributo
     */
    public void aumentaValor(int quantidade) {
        if (quantidade > 0) {
            this.valor += quantidade;

            if (this.valor > this.valorMaximo) {
                this.valor = this.valorMaximo;
            }
        }
    }

    /**
     * reduz o valor do atributo pela quantidade informada
     * no entanto, o valor do atributo não chega a ficar abaixo de zero
     *
     * @param quantidade valor que será subtraído do atributo
     */
    public void reduzValor(int quantidade) {
        if (quantidade > 0) {
            this.valor -= quantidade;

            if (this.valor < 0) {
                this.valor = 0;
            }
        }
    }
}
