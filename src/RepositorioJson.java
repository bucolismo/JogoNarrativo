/**
 * guarda cada save em um arquivo JSON dentro de uma pasta (por padrão, "saves/")
 *
 * slot 0 -> autosave.json | slots 1 a 3 -> slot1.json, slot2.json, slot3.json
 *
 * Os arquivos são gravados direto. Se um save estiver corrompido na hora de carregar, o jogo não
 * quebra: o carregamento lança uma exceção (CarregamentoException ou DadosInvalidosException)
 * e o Controlador avisa o jogador.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RepositorioJson implements RepositorioDeSaves {
    public static final String PASTA_PADRAO = "saves";

    private final Path pasta;
    private final Gson gson;

    public RepositorioJson() {
        this(Paths.get(PASTA_PADRAO));
    }

    public RepositorioJson(Path pasta) {
        this.pasta = pasta;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void salvar(int slot, EstadoPartida estado) throws SaveException {
        validarSlot(slot);

        try {
            Files.createDirectories(pasta);
            String json = gson.toJson(SaveData.de(estado, slot));
            Files.writeString(arquivoDoSlot(slot), json);
        } catch (IOException e) {
            throw new SaveException("Não foi possível salvar no slot " + rotulo(slot) + ": " + e.getMessage(), e);
        }
    }

    @Override
    public EstadoPartida carregar(int slot) throws CarregamentoException {
        return lerDados(slot).paraEstado();
    }

    @Override
    public MetadadosSave lerMetadados(int slot) throws CarregamentoException {
        if (!existe(slot)) {
            return null;
        }

        SaveData dados = lerDados(slot);
        dados.validar();
        return dados.getMetadados();
    }

    @Override
    public boolean existe(int slot) {
        return slotValido(slot) && Files.isRegularFile(arquivoDoSlot(slot));
    }

    @Override
    public void excluir(int slot) throws SaveException {
        validarSlot(slot);

        try {
            Files.deleteIfExists(arquivoDoSlot(slot));
        } catch (IOException e) {
            throw new SaveException("Não foi possível excluir o slot " + rotulo(slot) + ": " + e.getMessage(), e);
        }
    }

    private SaveData lerDados(int slot) throws CarregamentoException {
        if (!slotValido(slot)) {
            throw new CarregamentoException("Slot inválido: " + slot);
        }
        if (!existe(slot)) {
            throw new CarregamentoException("O slot " + rotulo(slot) + " está vazio.");
        }

        String json;
        try {
            json = Files.readString(arquivoDoSlot(slot));
        } catch (IOException e) {
            throw new CarregamentoException("Não foi possível ler o slot " + rotulo(slot) + ": " + e.getMessage(), e);
        }

        SaveData dados;
        try {
            dados = gson.fromJson(json, SaveData.class);
        } catch (JsonParseException e) {
            throw new DadosInvalidosException("O arquivo do slot " + rotulo(slot) + " está corrompido.", e);
        }

        if (dados == null) {
            throw new DadosInvalidosException("O arquivo do slot " + rotulo(slot) + " está vazio.");
        }

        return dados;
    }

    private Path arquivoDoSlot(int slot) {
        if (slot == SLOT_AUTOMATICO) {
            return pasta.resolve("autosave.json");
        }

        return pasta.resolve("slot" + slot + ".json");
    }

    private boolean slotValido(int slot) {
        return slot >= SLOT_AUTOMATICO && slot <= ULTIMO_SLOT_MANUAL;
    }

    private void validarSlot(int slot) throws SaveException {
        if (!slotValido(slot)) {
            throw new SaveException("Slot inválido: " + slot);
        }
    }

    private String rotulo(int slot) {
        if (slot == SLOT_AUTOMATICO) {
            return "automático";
        }

        return String.valueOf(slot);
    }
}
