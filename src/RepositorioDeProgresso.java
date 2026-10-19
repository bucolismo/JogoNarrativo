/**
 * guarda o progresso global (finais alcançados e conquistas) em um arquivo JSON,
 * por padrão saves/progresso.json
 *
 * Se o arquivo ainda não existe (primeira vez que o jogo é aberto), o progresso começa vazio.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositorioDeProgresso {
    private final Path pasta;
    private final Path arquivo;
    private final Gson gson;

    public RepositorioDeProgresso() {
        this(Paths.get(RepositorioJson.PASTA_PADRAO));
    }

    public RepositorioDeProgresso(Path pasta) {
        this.pasta = pasta;
        this.arquivo = pasta.resolve("progresso.json");
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * lê o progresso salvo
     *
     * @return o progresso gravado, ou um progresso vazio se o arquivo ainda não existe
     * @throws CarregamentoException se o arquivo existe mas não pôde ser lido
     * @throws DadosInvalidosException se o conteúdo do arquivo não é um progresso válido
     */
    public ProgressoGlobal carregar() throws CarregamentoException {
        if (!Files.exists(arquivo)) {
            return new ProgressoGlobal();
        }

        try {
            String json = Files.readString(arquivo);
            ProgressoGlobal progresso = gson.fromJson(json, ProgressoGlobal.class);

            if (progresso == null) {
                throw new DadosInvalidosException("O arquivo de progresso está vazio.");
            }

            progresso.validar();
            return progresso;
        } catch (IOException e) {
            throw new CarregamentoException("Não foi possível ler o progresso: " + e.getMessage(), e);
        } catch (JsonParseException e) {
            throw new DadosInvalidosException("O arquivo de progresso está corrompido.", e);
        }
    }

    /**
     * grava o progresso, substituindo o que já existia
     *
     * @param progresso progresso a ser gravado
     * @throws SaveException se a gravação falhar
     */
    public void salvar(ProgressoGlobal progresso) throws SaveException {
        try {
            Files.createDirectories(pasta);
            Files.writeString(arquivo, gson.toJson(progresso));
        } catch (IOException e) {
            throw new SaveException("Não foi possível salvar o progresso: " + e.getMessage(), e);
        }
    }
}
