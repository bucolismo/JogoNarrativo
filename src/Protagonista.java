public class Protagonista extends Personagem {
    private Atributo[] atributos;
    private Inventario inventario;

    public Protagonista(String nome, int idade, String genero) {
        super(nome, idade, genero);

        atributos = new Atributo[3];

        atributos[0] = new Atributo("Razão", 50, 100);
        atributos[1] = new Atributo("Paranoia", 50, 100);
        atributos[2] = new Atributo("Violencia", 50, 100);

        inventario = new Inventario();
    }

    public void alteraAtributo(String nomeAtributo, int quantidade) {
        for (int i = 0; i < atributos.length; i++) {
            if (atributos[i].getNome().equalsIgnoreCase(nomeAtributo)) {
                if (quantidade >= 0) {
                    atributos[i].aumentaValor(quantidade);
                } else {
                    atributos[i].reduzValor(Math.abs(quantidade));
                }
                return;
            }
        }
        System.out.println("Atributo não existente");
    }

    public int getAtributo(String nomeAtributo) {
        for (int i = 0; i < atributos.length; i++) {
            if (atributos[i].getNome().equalsIgnoreCase(nomeAtributo)) {
                return atributos[i].getValor();
            }
        }
        return 0;
    }

    public void cadastrarItens(){
        // IDs confirmados via new Efeito(N) / defineRequisitoItem(N) em ConstrutorDeCenas

        Item pistaDeJonas    = new Item("Pista de Jonas", "Pista", "Informação repassada pelo Velho Jonas sobre a fábrica desativada", 6);
        Item pistaManequim   = new Item("Pista do Manequim", "Pista", "Uma pista plantada por alguém em um manequim no beco", 7);
        Item laudoAdulterado = new Item("Laudo Adulterado", "Evidência", "Laudo pericial do caso de Gabriel com rasuras propositais", 8);
        Item diarioDoIrmao   = new Item("Diário de Gabriel", "Prova", "Diário de Gabriel encontrado no esconderijo da fábrica", 10);
        Item gravacao        = new Item("Gravação de Gabriel", "Prova", "Gravação de voz deixada por Gabriel alertando sobre Daniel", 11);
        Item radioPolicial   = new Item("Rádio de Frequência Restrita", "Acesso", "Rádio da polícia entregue por Daniel após ganhar sua confiança total", 12);
        Item pastaOriginal   = new Item("Pasta Original do Laudo", "Evidência", "Laudo original resgatado do Arquivo Morto da delegacia, sem as rasuras oficiais", 13);
        Item cartaoPrefeitura   = new Item("Cartão de Acesso da Prefeitura", "Acesso", "Cartão magnético timbrado da Prefeitura, encontrado no bolso do incendiário", 15);
        Item canalGabinete      = new Item("Pista: Canal do Gabinete", "Pista", "Código interno do gabinete municipal usado para dar ordens ao suspeito", 16);
        Item vinculoVicePrefeito= new Item("Pista: Vínculo com Vice-Prefeito", "Pista", "Confissão de que o pagamento veio do gabinete do vice-prefeito", 17);
        Item testemunhoVerbal   = new Item("Testemunho Verbal Não-Assinado", "Pista", "Confirmação verbal do envolvimento da administração, sem assinatura", 18);
        Item nomeAssessor       = new Item("Nome do Assessor", "Pista", "Nome do assessor direto do vice-prefeito, obtido sob pressão", 19);
        Item pontoEncontro      = new Item("Pista: Ponto de Encontro do Transfuga", "Pista", "Local e horário da entrega do restante do material queimado", 20);
        Item procuradoAgressao = new Item("Procurado por Agressão", "Status", "Alerta ativo contra você por violência cometida durante a investigação", 21);
        Item fitaAudio       = new Item("Fita de Áudio Confidencial do Irmão", "Prova", "Fita microcassete recuperada da Central de Custódia", 22);
        Item diariosSobrado  = new Item("Diários do Sobrado de Gabriel", "Prova", "Diários ocultos sob o assoalho da casa de Gabriel", 23);
        Item ordemBusca      = new Item("Ordem de Busca e Apreensão Paralela", "Pista", "Pasta de ordens interceptada do carro descaracterizado", 24);
        Item contaFantasma   = new Item("Mapeamento da Conta Fantasma", "Pista", "Mapa das contas bancárias ligadas à empresa fantasma", 25);
        Item dossieCadaveres = new Item("Dossiê de Ocultação de Cadáveres", "Prova", "Organograma que liga Daniel a quatro homicídios anteriores", 26);
        Item panfletos       = new Item("Panfletos de Denúncia Operacional", "Item", "Cópias impressas prontas para distribuição", 27);
        Item rotaGalerias    = new Item("Rota das Galerias do Centro", "Acesso", "Caminho pelo canal subterrâneo de escoamento", 28);
        Item pistolaTatica   = new Item("Pistola Tática com Silenciador", "Item", "Arma tomada do agente na penumbra do beco", 29);
        Item alertaGeral      = new Item("Alerta Geral na Cidade", "Status", "Panfletos com o depoimento e as fotos já circulam publicamente pela cidade", 32);
        Item ferimentosLeves  = new Item("Ferimentos Leves", "Status", "Dano físico leve sofrido em confronto corpo a corpo", 33);
        Item armaBranca       = new Item("Arma Branca Adquirida", "Item", "Arma branca tomada de um policial durante confronto", 34);
        Item gameOver = new Item(("GameOver"),"GameOver","GameOver",-1);

        // ATENÇÃO: ID 14 pressupõe que você altere, em criarAtoIIRota3A (escolhaAuto12),
        // "new Efeito(13)" para "new Efeito(14)" — hoje ela colide com o item 13 acima.
        Item chaveArquivoMorto = new Item("Chave do Arquivo Morto", "Acesso", "Chave entregue por Daniel ao atingir parceria total com ele", 14);

        inventario.cadastrarItem(pistaDeJonas);
        inventario.cadastrarItem(pistaManequim);
        inventario.cadastrarItem(laudoAdulterado);
        inventario.cadastrarItem(diarioDoIrmao);
        inventario.cadastrarItem(gravacao);
        inventario.cadastrarItem(radioPolicial);
        inventario.cadastrarItem(pastaOriginal);
        inventario.cadastrarItem(chaveArquivoMorto);
        inventario.cadastrarItem(cartaoPrefeitura);
        inventario.cadastrarItem(canalGabinete);
        inventario.cadastrarItem(vinculoVicePrefeito);
        inventario.cadastrarItem(testemunhoVerbal);
        inventario.cadastrarItem(nomeAssessor);
        inventario.cadastrarItem(pontoEncontro);
        inventario.cadastrarItem(procuradoAgressao);
        inventario.cadastrarItem(fitaAudio);
        inventario.cadastrarItem(diariosSobrado);
        inventario.cadastrarItem(ordemBusca);
        inventario.cadastrarItem(contaFantasma);
        inventario.cadastrarItem(dossieCadaveres);
        inventario.cadastrarItem(panfletos);
        inventario.cadastrarItem(rotaGalerias);
        inventario.cadastrarItem(pistolaTatica);
        inventario.cadastrarItem(alertaGeral);
        inventario.cadastrarItem(ferimentosLeves);
        inventario.cadastrarItem(armaBranca);
        inventario.cadastrarItem(gameOver);
    }

    public Inventario getInventario() {
        return this.inventario;
    }

}
