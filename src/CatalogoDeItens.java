/**
 * catálogo com todos os itens, pistas e marcadores de estado que a história pode conceder
 *
 * Os identificadores (ids) precisam bater com os que o ConstrutorDeCenas usa nos efeitos
 * (Efeito(int)) e nos requisitos de escolha (defineRequisitoItem). Um id usado na história
 * mas ausente aqui faz o item nunca ser concedido (e escolhas ficarem bloqueadas para sempre),
 * por isso existe um teste que cruza os dois (CatalogoDeItensTest).
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CatalogoDeItens {

    private CatalogoDeItens() {
    }

    /**
     * @return os identificadores de todos os itens do catálogo (usado para validar o conteúdo das cenas)
     */
    public static Set<Integer> ids() {
        Set<Integer> ids = new HashSet<>();

        for (Item item : criarTodos()) {
            ids.add(item.getItemID());
        }

        return ids;
    }

    /**
     * cria uma lista nova com todos os itens do jogo, todos inicialmente fora do inventário
     *
     * @return lista de itens cadastráveis no inventário do protagonista
     */
    public static List<Item> criarTodos() {
        List<Item> itens = new ArrayList<>();

        // Acesso e ferramentas do Ato I
        itens.add(new Item("Cartão do IML", "Acesso", "Um cartão de acesso que permite acessar o IML da cidade", 1));
        itens.add(new Item("Chave Mestra do Cartório", "Acesso", "Uma chave estranha com o símbolo do cartório central que permite acessar o prédio de registros", 2));
        itens.add(new Item("Cartão de Acesso da Prefeitura", "Acesso", "Um cartão magnético que permite acessar o prédio da prefeitura municipal", 3));
        itens.add(new Item("Chave do Cofre da Prefeitura", "Acesso", "Uma chave de segurança que permite acessar o cofre e o arquivo restrito", 4));
        itens.add(new Item("Pé de Cabra", "Acesso", "Uma ferramenta pesada que permite forçar e abrir portas e acessos bloqueados", 5));
        itens.add(new Item("Pista de Jonas", "Acesso", "Uma pista que permite seguir outro caminho", 6));
        itens.add(new Item("Pista do Manequim", "Acesso", "Uma pista plantada por alguém em um manequim", 7));
        itens.add(new Item("Laudo Adulterado", "Evidência", "Um laudo adulterado do caso de Gabriel", 8));

        // Evidências e equipamentos obtidos a partir do Ato II
        itens.add(new Item("Diário de Gabriel", "Evidência", "O diário de Gabriel, com anotações sobre o que ele descobriu", 10));
        itens.add(new Item("Gravação de Gabriel", "Evidência", "Uma gravação deixada por Gabriel", 11));
        itens.add(new Item("Rádio de Frequência Restrita da Polícia", "Equipamento", "Permite ouvir as comunicações internas da polícia", 12));
        itens.add(new Item("Pasta Original do Laudo", "Evidência", "A pasta com o laudo original, antes da adulteração", 13));
        itens.add(new Item("Chave do Arquivo Morto", "Acesso", "Dá acesso ao arquivo morto", 14));
        itens.add(new Item("Cartão de Acesso da Prefeitura", "Acesso", "Outro cartão de acesso à prefeitura, obtido durante a investigação", 15));

        // Pistas obtidas em interrogatórios
        itens.add(new Item("Pista: Canal do Gabinete", "Pista", "Mensagens anônimas partiam de um número com o código interno do gabinete municipal", 16));
        itens.add(new Item("Pista: Vínculo com Vice-Prefeito", "Pista", "O pagamento veio de uma conta ligada ao gabinete do vice-prefeito", 17));
        itens.add(new Item("Pista: Testemunho Verbal Não-Assinado", "Pista", "Um suspeito confirmou o envolvimento da prefeitura, mas não assinou o depoimento", 18));
        itens.add(new Item("Pista: Nome do Assessor", "Pista", "O nome do assessor direto do vice-prefeito", 19));
        itens.add(new Item("Pista: Ponto de Encontro do Transfuga", "Pista", "Local e horário da entrega do material queimado", 20));

        // Estados e marcadores (status) usados como condição de acesso/consequência
        itens.add(new Item("Procurado por Agressão", "Status", "Você agora está marcado como procurado por agressão", 21));
        itens.add(new Item("Fita de Áudio Confidencial do Irmão", "Evidência", "Uma fita de áudio confidencial do irmão", 22));
        itens.add(new Item("Diários do Sobrado de Gabriel", "Evidência", "Diários encontrados no sobrado de Gabriel", 23));
        itens.add(new Item("Ordem de Busca e Apreensão Paralela", "Documento", "Uma ordem de busca e apreensão obtida por fora do caminho oficial", 24));
        itens.add(new Item("Mapeamento da Conta Fantasma", "Evidência", "O mapeamento de uma conta fantasma usada nos pagamentos", 25));
        itens.add(new Item("Dossiê de Ocultação de Cadáveres", "Evidência", "Um dossiê sobre a ocultação de cadáveres", 26));
        itens.add(new Item("Panfletos de Denúncia", "Evidência", "Panfletos com a denúncia, prontos para distribuição", 27));
        itens.add(new Item("Rota das Galerias do Centro", "Informação", "Uma rota de fuga pelas galerias do centro", 28));
        itens.add(new Item("Pistola Tática com Silenciador", "Equipamento", "Uma pistola tomada de um agressor", 29));
        itens.add(new Item("Alarme Disparado", "Status", "O alarme silencioso do cartório foi disparado", 30));
        itens.add(new Item("Veículo dos Seguranças", "Equipamento", "O veículo dos seguranças, tomado durante a fuga", 31));
        itens.add(new Item("Alerta Geral na Cidade", "Status", "A cidade inteira agora sabe da denúncia", 32));
        itens.add(new Item("Ferimentos Leves", "Status", "Você sofreu ferimentos leves", 33));
        itens.add(new Item("Arma Branca Adquirida", "Equipamento", "Uma arma branca tomada durante o confronto", 34));

        // Marcadores de rota do Ato IV (indicam qual rota do Ato V foi ativada)
        itens.add(new Item("Rota Ativada: A Queda pelo Direito", "Marcador de rota", "Ato V - rota da Razão", 35));
        itens.add(new Item("Rota Ativada: A Revolta da Cidade", "Marcador de rota", "Ato V - rota da Paranoia", 36));
        itens.add(new Item("Rota Ativada: A Sentença de Sangue", "Marcador de rota", "Ato V - rota da Violência", 37));

        return itens;
    }
}
