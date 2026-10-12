import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarregadorDeCenas {

    private static final Gson GSON = new Gson();

    //  Data Transfer Objects (espelham o JSON)
    static class CenaDTO {
        String id;
        List<DialogoDTO> dialogos;
    }
    static class DialogoDTO {
        String personagem;
        List<String> texto;
        List<EscolhaDTO> escolhas;
    }
    static class EscolhaDTO {
        List<String> texto;
        List<String> resposta;
        List<EfeitoDTO> efeitos;

        Integer requisitoItem;
        String requisitoAtributo;
        int valorRequisitoAtributo;
        String requisitoNpc;
        int confiancaMinima;
    }
    static class EfeitoDTO {
        String tipo;
        String nome;        // PREDEFINIDO
        String atributo;    // ATRIBUTO
        String personagem;  // RELACAO
        int valor;          // ATRIBUTO / RELACAO
        Integer idItem;     // ITEM / REMOVER_ITEM
    }

    //  Leitura do arquivo
    public static CenaDTO carregar(String caminho) throws IOException {//Exeção de erro de leitura(IOExeption
        try (InputStream in = CarregadorDeCenas.class.getResourceAsStream(caminho)) {//Lê os bytes do arquivo Json
            if (in == null) {
                throw new FileNotFoundException("Recurso não encontrado: " + caminho);
            }
            return GSON.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), CenaDTO.class);//Para ler os caractéres especias
        } catch (JsonParseException e) {
            throw new IOException("JSON malformado em " + caminho + ": " + e.getMessage(), e);//As exeções tem que tratar no menu
        }
    }

    // Montagem da cena
    public static Cena montarCena(String caminho, Map<String, Personagem> elenco, Map<String, Efeito> predefinidos) throws IOException {
        CenaDTO dto = carregar(caminho);
        if (dto == null) {
            throw new IOException("JSON vazio ou inválido: " + caminho);
        }
        if (dto.id == null) {
            throw new IOException("Cena sem \"id\" em " + caminho);
        }
        Cena cena = new Cena(dto.id);
        if (dto.dialogos != null) {
            for (DialogoDTO d : dto.dialogos) {
                cena.adicionaDialogo(montarDialogo(d, elenco, predefinidos));
            }
        }
        return cena;
    }

    private static Dialogo montarDialogo(DialogoDTO d, Map<String, Personagem> elenco, Map<String, Efeito> predefinidos) {
        Personagem falante = buscarPersonagem(elenco, d.personagem);//Busca na Hash o personagem que está falando no dialogo atual
        String texto = juntar(d.texto);

        if (d.escolhas == null || d.escolhas.isEmpty()) {
            return new Dialogo(falante, texto);
        }

        Escolha[] escolhas = new Escolha[d.escolhas.size()];
        for (int i = 0; i < escolhas.length; i++) {
            escolhas[i] = montarEscolha(d.escolhas.get(i), elenco, predefinidos);
        }
        return new Dialogo(falante, texto, escolhas);
    }

    private static Escolha montarEscolha(EscolhaDTO e, Map<String, Personagem> elenco, Map<String, Efeito> predefinidos) {
        ArrayList<Efeito> efeitos = new ArrayList<Efeito>();

        if (e.efeitos != null) {
            for (EfeitoDTO f : e.efeitos) {
                efeitos.add(montarEfeito(f, elenco, predefinidos));
            }
        }

        String resposta = (e.resposta == null || e.resposta.isEmpty()) ? null : juntar(e.resposta);

        Escolha escolha = new Escolha(juntar(e.texto), efeitos, resposta);

        if (e.requisitoItem != null) {
            escolha.defineRequisitoItem(e.requisitoItem);
        }
        if (e.requisitoAtributo != null) {
            escolha.defineRequisitoAtributo(e.requisitoAtributo, e.valorRequisitoAtributo);
        }
        if (e.requisitoNpc != null) {
            escolha.defineRequisitoConfianca(buscarNpc(elenco, e.requisitoNpc), e.confiancaMinima);
        }
        return escolha;
    }

    private static Efeito montarEfeito(EfeitoDTO f, Map<String, Personagem> elenco, Map<String, Efeito> predefinidos) {
        if (f.tipo == null) {
            throw new IllegalStateException("Efeito sem tipo");
        }

        return switch (f.tipo) {
            case "PREDEFINIDO" -> {
                Efeito ef = (predefinidos == null) ? null : predefinidos.get(f.nome);
                if (ef == null) {
                    throw new IllegalStateException("Efeito predefinido desconhecido: " + f.nome);
                }
                yield ef;
            }
            case "ATRIBUTO" -> {
                if (f.atributo == null) {
                    throw new IllegalStateException("Efeito ATRIBUTO sem atributo");
                }
                yield new Efeito("ATRIBUTO", f.atributo, f.valor);
            }
            case "RELACAO" -> new Efeito(buscarNpc(elenco, f.personagem), f.valor);
            case "ITEM" -> new Efeito(exigirIdItem(f));
            case "REMOVER_ITEM" -> new Efeito("REMOVER_ITEM", null, exigirIdItem(f));
            default -> throw new IllegalStateException("Tipo de efeito desconhecido: " + f.tipo);
        };
    }

    // Auxiliares
    private static int exigirIdItem(EfeitoDTO f) {
        if (f.idItem == null) {
            throw new IllegalStateException("Efeito " + f.tipo + " sem idItem");
        }
        return f.idItem;
    }

    private static Personagem buscarPersonagem(Map<String, Personagem> elenco, String chave) {
        Personagem p = (chave == null) ? null : elenco.get(chave);
        if (p == null) {
            throw new IllegalStateException("Personagem não definido: " + chave);
        }
        return p;
    }

    private static NPC buscarNpc(Map<String, Personagem> elenco, String chave) {
        if (buscarPersonagem(elenco, chave) instanceof NPC npc) {
            return npc;
        }
        throw new IllegalStateException(chave + " não é um NPC");
    }

    private static String juntar(List<String> linhas) {
        if (linhas == null || linhas.isEmpty()) {
            return "";
        }
        return String.join("\n", linhas) + "\n";
    }
}
