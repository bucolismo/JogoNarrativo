/**
 * serve pra representar os personagens da narrativa do jogo
 *
 * todos os personagem do jogo tem nome, idade e gênero
 * essa é classe é como uma base/modelo para a inicialização
 * de todos os personagnes, seja ele jogável ou não
 */

public class Personagem {
    private String nome;
    private int idade;
    private String genero;

    public Personagem(String nome, int idade, String genero) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
    }

    public String getNome() {
        return this.nome;
    }
}
