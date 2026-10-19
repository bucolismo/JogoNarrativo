/**
 * guarda as configurações do jogador em um arquivo JSON, por padrão saves/config.json
 *
 * Se o arquivo ainda não existe (primeira vez que o jogo é aberto), valem as configurações padrão.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositorioDeConfiguracoes {
    private final Path pasta;
    private final Path arquivo;
    private final Gson gson;

    public RepositorioDeConfiguracoes() {
        this(Paths.get(RepositorioJson.PASTA_PADRAO));
    }

    public RepositorioDeConfiguracoes(Path pasta) {
        this.pasta = pasta;
        this.arquivo = pasta.resolve("config.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * lê as configurações salvas
     *
     * @return as configurações gravadas, ou as configurações padrão se o arquivo ainda não existe
     * @throws CarregamentoException se o arquivo existe mas não pôde ser lido
     * @throws DadosInvalidosException se o conteúdo do arquivo não é uma configuração válida
     */
    public Configuracoes carregar() throws CarregamentoException {
        if (!Files.exists(arquivo)) {
            return new Configuracoes();
        }

        try {
            String json = Files.readString(arquivo);
            Configuracoes configuracoes = gson.fromJson(json, Configuracoes.class);

            if (configuracoes == null) {
                throw new DadosInvalidosException("O arquivo de configurações está vazio.");
            }

            configuracoes.validar();
            return configuracoes;
        } catch (IOException e) {
            throw new CarregamentoException("Não foi possível ler as configurações: " + e.getMessage(), e);
        } catch (JsonParseException e) {
            throw new DadosInvalidosException("O arquivo de configurações está corrompido.", e);
        }
    }

    /**
     * grava as configurações, substituindo as que já existiam
     *
     * @param configuracoes configurações a serem gravadas
     * @throws SaveException se a gravação falhar
     */
    public void salvar(Configuracoes configuracoes) throws SaveException {
        try {
            Files.createDirectories(pasta);
            Files.writeString(arquivo, gson.toJson(configuracoes));
        } catch (IOException e) {
            throw new SaveException("Não foi possível salvar as configurações: " + e.getMessage(), e);
        }
    }
}
