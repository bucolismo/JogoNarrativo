/**
 * dados do personagem que o jogador guardou como favorito: nome, idade e gênero
 *
 * É escolhido no início de uma nova partida, no lugar de criar o personagem de novo, e fica guardado
 * dentro das Configuracoes (arquivo config.json). Ideia e primeira versão do Luis Felipe.
 */
public class PersonagemFavorito {
    private String nome;
    private int idade;
    private String genero;

    public PersonagemFavorito() {
    }

    public PersonagemFavorito(String nome, int idade, String genero) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getGenero() {
        return genero;
    }

    /**
     * confere se os dados lidos de um arquivo servem para criar um protagonista
     *
     * @throws DadosInvalidosException se o nome ou o gênero estiverem vazios ou a idade não for positiva
     */
    public void validar() throws DadosInvalidosException {
        if (nome == null || nome.isBlank()) {
            throw new DadosInvalidosException("Personagem favorito sem nome.");
        }
        if (idade <= 0) {
            throw new DadosInvalidosException("Idade inválida no personagem favorito: " + idade);
        }
        if (genero == null || genero.isBlank()) {
            throw new DadosInvalidosException("Personagem favorito sem gênero.");
        }
    }
}
