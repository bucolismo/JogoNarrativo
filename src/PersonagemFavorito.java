import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PersonagemFavorito {

    private static final Path ARQUIVO = Paths.get("saves", "personagem_favorito.json");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private String nome;
    private int idade;
    private String genero;

    public PersonagemFavorito() { } // exigido pelo Gson

    public PersonagemFavorito(String nome, int idade, String genero) {
        this.nome = nome;
        this.idade = idade;
        this.genero = genero;
    }

    public String getNome() { return nome; }
    public int getIdade() { return idade; }
    public String getGenero() { return genero; }

    public static boolean existe() {
        return Files.exists(ARQUIVO);
    }

    public void salvar() throws IOException {
        Files.createDirectories(ARQUIVO.getParent());
        try (Writer w = Files.newBufferedWriter(ARQUIVO, StandardCharsets.UTF_8)) {
            GSON.toJson(this, w);
        }
    }

    public static PersonagemFavorito carregar() throws IOException {
        try (Reader r = Files.newBufferedReader(ARQUIVO, StandardCharsets.UTF_8)) {
            return GSON.fromJson(r, PersonagemFavorito.class);
        }
    }
}