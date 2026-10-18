/**
 * monta as cenas da história a partir de arquivos JSON (pasta resources/cenas)
 *
 * Separa o conteúdo narrativo (textos, escolhas, efeitos e requisitos) do código: para mudar uma
 * fala ou um efeito basta editar o JSON. Como um erro de digitação no JSON só seria percebido
 * no meio do jogo, o carregador valida tudo ao montar a cena: personagens, tipos de efeito, itens
 * (conferidos no CatalogoDeItens), atributos e limites de confiança. Qualquer problema vira uma
 * CenaInvalidaException com o arquivo, o diálogo e a escolha onde o erro está.
 *
 * Formato de uma cena:
 * {
 *   "id": "ato_1",
 *   "dialogos": [
 *     { "personagem": "narrador", "texto": ["linha 1", "linha 2"],
 *       "escolhas": [ {
 *           "texto": ["[1] ..."], "resposta": ["consequência exibida"],
 *           "efeitos": [ {"tipo": "PREDEFINIDO", "nome": "aumentaRazao"},
 *                        {"tipo": "ATRIBUTO", "atributo": "Razão", "valor": 3},
 *                        {"tipo": "RELACAO", "personagem": "jonas", "valor": 10},
 *                        {"tipo": "ITEM", "idItem": 6},
 *                        {"tipo": "GAME_OVER"} ],
 *           "requisitoItem": 6,
 *           "requisitoAtributo": "Violência", "valorRequisitoAtributo": 7,
 *           "requisitoNpc": "daniel", "confiancaMinima": 90 } ] }
 *   ]
 * }
 */

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CarregadorDeCenas {

    private static final Gson GSON = new Gson();
    private static final Set<Integer> IDS_DE_ITENS = CatalogoDeItens.ids();

    // Data Transfer Objects: espelham o JSON
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
        Integer idItem;     // ITEM
    }

    private CarregadorDeCenas() {
    }

    /**
     * lê um arquivo de cena do classpath (ex.: "/cenas/prologo.json") e monta a cena
     *
     * @param caminho caminho do recurso, começando por "/"
     * @param elenco personagens que as cenas podem citar, pela chave usada no JSON
     * @param predefinidos efeitos reutilizáveis que o JSON referencia pelo nome
     * @return cena montada e validada
     * @throws CenaInvalidaException se o arquivo não existir, estiver malformado ou tiver conteúdo inválido
     */
    public static Cena montarCena(String caminho, Map<String, Personagem> elenco,
                                  Map<String, Efeito> predefinidos) throws CenaInvalidaException {
        try (InputStream in = CarregadorDeCenas.class.getResourceAsStream(caminho)) {
            if (in == null) {
                throw new CenaInvalidaException("Recurso da cena não encontrado: " + caminho);
            }

            return montarCena(new InputStreamReader(in, StandardCharsets.UTF_8), caminho, elenco, predefinidos);
        } catch (IOException e) {
            throw new CenaInvalidaException("Não foi possível ler " + caminho + ": " + e.getMessage(), e);
        }
    }

    /**
     * monta uma cena a partir de um JSON já aberto
     *
     * @param leitor origem do JSON
     * @param origem nome mostrado nas mensagens de erro (normalmente o caminho do arquivo)
     * @param elenco personagens que as cenas podem citar, pela chave usada no JSON
     * @param predefinidos efeitos reutilizáveis que o JSON referencia pelo nome
     * @return cena montada e validada
     * @throws CenaInvalidaException se o JSON estiver malformado ou tiver conteúdo inválido
     */
    public static Cena montarCena(Reader leitor, String origem, Map<String, Personagem> elenco,
                                  Map<String, Efeito> predefinidos) throws CenaInvalidaException {
        CenaDTO dto;

        try {
            dto = GSON.fromJson(leitor, CenaDTO.class);
        } catch (JsonParseException e) {
            throw new CenaInvalidaException("JSON malformado em " + origem + ": " + e.getMessage(), e);
        }

        if (dto == null) {
            throw new CenaInvalidaException("JSON vazio em " + origem);
        }
        if (dto.id == null || dto.id.isBlank()) {
            throw new CenaInvalidaException("Cena sem \"id\" em " + origem);
        }
        if (dto.dialogos == null || dto.dialogos.isEmpty()) {
            throw new CenaInvalidaException("Cena sem diálogos em " + origem);
        }

        Cena cena = new Cena(dto.id);

        for (int i = 0; i < dto.dialogos.size(); i++) {
            String contexto = origem + ", diálogo " + (i + 1);
            DialogoDTO dialogo = dto.dialogos.get(i);

            if (dialogo == null) {
                throw new CenaInvalidaException("Diálogo vazio em " + contexto);
            }

            cena.adicionaDialogo(montarDialogo(dialogo, contexto, elenco, predefinidos));
        }

        return cena;
    }

    private static Dialogo montarDialogo(DialogoDTO d, String contexto, Map<String, Personagem> elenco,
                                         Map<String, Efeito> predefinidos) throws CenaInvalidaException {
        Personagem falante = buscarPersonagem(elenco, d.personagem, contexto);

        if (d.texto == null || d.texto.isEmpty()) {
            throw new CenaInvalidaException("Diálogo sem texto em " + contexto);
        }

        String texto = juntar(d.texto);

        if (d.escolhas == null || d.escolhas.isEmpty()) {
            return new Dialogo(falante, texto);
        }

        Escolha[] escolhas = new Escolha[d.escolhas.size()];

        for (int i = 0; i < escolhas.length; i++) {
            String contextoEscolha = contexto + ", escolha " + (i + 1);
            EscolhaDTO escolha = d.escolhas.get(i);

            if (escolha == null) {
                throw new CenaInvalidaException("Escolha vazia em " + contextoEscolha);
            }

            escolhas[i] = montarEscolha(escolha, contextoEscolha, elenco, predefinidos);
        }

        return new Dialogo(falante, texto, escolhas);
    }

    private static Escolha montarEscolha(EscolhaDTO e, String contexto, Map<String, Personagem> elenco,
                                         Map<String, Efeito> predefinidos) throws CenaInvalidaException {
        if (e.texto == null || e.texto.isEmpty()) {
            throw new CenaInvalidaException("Escolha sem texto em " + contexto);
        }

        ArrayList<Efeito> efeitos = new ArrayList<>();

        if (e.efeitos != null) {
            for (EfeitoDTO f : e.efeitos) {
                if (f == null) {
                    throw new CenaInvalidaException("Efeito vazio em " + contexto);
                }
                efeitos.add(montarEfeito(f, contexto, elenco, predefinidos));
            }
        }

        String resposta = (e.resposta == null || e.resposta.isEmpty()) ? null : juntar(e.resposta);

        Escolha escolha = new Escolha(juntar(e.texto), efeitos, resposta);

        if (e.requisitoItem != null) {
            exigirItemExistente(e.requisitoItem, "requisitoItem", contexto);
            escolha.defineRequisitoItem(e.requisitoItem);
        }

        if (e.requisitoAtributo != null) {
            exigirAtributoValido(e.requisitoAtributo, "requisitoAtributo", contexto);
            exigirEntre0e100(e.valorRequisitoAtributo, "valorRequisitoAtributo", contexto);
            escolha.defineRequisitoAtributo(e.requisitoAtributo, e.valorRequisitoAtributo);
        }

        if (e.requisitoNpc != null) {
            exigirEntre0e100(e.confiancaMinima, "confiancaMinima", contexto);
            escolha.defineRequisitoConfianca(buscarNpc(elenco, e.requisitoNpc, contexto), e.confiancaMinima);
        }

        return escolha;
    }

    private static Efeito montarEfeito(EfeitoDTO f, String contexto, Map<String, Personagem> elenco,
                                       Map<String, Efeito> predefinidos) throws CenaInvalidaException {
        if (f.tipo == null) {
            throw new CenaInvalidaException("Efeito sem tipo em " + contexto);
        }

        switch (f.tipo) {
            case "PREDEFINIDO": {
                Efeito predefinido = (predefinidos == null || f.nome == null) ? null : predefinidos.get(f.nome);

                if (predefinido == null) {
                    throw new CenaInvalidaException("Efeito predefinido desconhecido (" + f.nome + ") em " + contexto);
                }
                return predefinido;
            }

            case "ATRIBUTO":
                exigirAtributoValido(f.atributo, "atributo", contexto);
                return new Efeito(Efeito.TIPO_ATRIBUTO, f.atributo, f.valor);

            case "RELACAO":
                return new Efeito(buscarNpc(elenco, f.personagem, contexto), f.valor);

            case "ITEM":
                if (f.idItem == null) {
                    throw new CenaInvalidaException("Efeito ITEM sem idItem em " + contexto);
                }
                exigirItemExistente(f.idItem, "idItem", contexto);
                return new Efeito(f.idItem);

            case "GAME_OVER":
                return Efeito.gameOver();

            default:
                throw new CenaInvalidaException("Tipo de efeito desconhecido (" + f.tipo + ") em " + contexto);
        }
    }

    // validações e auxiliares

    private static void exigirItemExistente(int id, String campo, String contexto) throws CenaInvalidaException {
        if (!IDS_DE_ITENS.contains(id)) {
            throw new CenaInvalidaException("Item inexistente no catálogo (" + campo + " = " + id + ") em " + contexto);
        }
    }

    private static void exigirAtributoValido(String nome, String campo, String contexto) throws CenaInvalidaException {
        if (nome != null) {
            for (String valido : Protagonista.NOMES_DOS_ATRIBUTOS) {
                if (valido.equalsIgnoreCase(nome)) {
                    return;
                }
            }
        }

        throw new CenaInvalidaException("Atributo inexistente (" + campo + " = " + nome + ") em " + contexto);
    }

    private static void exigirEntre0e100(int valor, String campo, String contexto) throws CenaInvalidaException {
        if (valor < 0 || valor > 100) {
            throw new CenaInvalidaException("Valor fora do intervalo de 0 a 100 (" + campo + " = " + valor + ") em " + contexto);
        }
    }

    private static Personagem buscarPersonagem(Map<String, Personagem> elenco, String chave, String contexto)
            throws CenaInvalidaException {
        Personagem personagem = (chave == null) ? null : elenco.get(chave);

        if (personagem == null) {
            throw new CenaInvalidaException("Personagem não definido (" + chave + ") em " + contexto);
        }

        return personagem;
    }

    private static NPC buscarNpc(Map<String, Personagem> elenco, String chave, String contexto)
            throws CenaInvalidaException {
        if (buscarPersonagem(elenco, chave, contexto) instanceof NPC npc) {
            return npc;
        }

        throw new CenaInvalidaException(chave + " não é um NPC, em " + contexto);
    }

    private static String juntar(List<String> linhas) {
        if (linhas == null || linhas.isEmpty()) {
            return "";
        }

        return String.join("\n", linhas) + "\n";
    }
}
