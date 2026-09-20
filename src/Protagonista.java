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
        Item cartaoIml = new Item("Cartão do IML", "Acesso", "Um cartão de acesso que permite acessar o IML da cidade", 1);
        Item chaveMestra = new Item("Chave Mestra do Cartório", "Acesso", "Uma chave estranha com o símbolo do cartório central que permite acessar o prédio de registros", 2);
        Item cartaoPrefeitura = new Item("Cartão de Acesso da Prefeitura", "Acesso", "Um cartão magnético que permite acessar o prédio da prefeitura municipal", 3);
        Item chaveCofrePrefeitura = new Item("Chave do Cofre da Prefeitura", "Acesso", "Uma chave de segurança que permite acessar o cofre e o arquivo restrito", 4);
        Item peDeCabra = new Item("Pé de Cabra", "Acesso", "Uma ferramenta pesada que permite forçar e abrir portas e acessos bloqueados", 5);
        Item pistaDeJonas = new Item("Pista de Jonas","Acesso","Uma pista que permite seguir outro caminho",6);
        Item pistaManequim = new Item("Pista do Manequim","Acesso","Uma pista plantada por alguém em um manequi",7);
        Item laudoAdulterado = new Item("Laudo Adulterado","Evidência","Um laudo adulterado do caso de Gabriel",8);

        inventario.cadastrarItem(cartaoIml);
        inventario.cadastrarItem(chaveMestra);
        inventario.cadastrarItem(cartaoPrefeitura);
        inventario.cadastrarItem(chaveCofrePrefeitura);
        inventario.cadastrarItem(peDeCabra);
        inventario.cadastrarItem(pistaDeJonas);
        inventario.cadastrarItem(pistaManequim);
        inventario.cadastrarItem(laudoAdulterado);

    }

    public Inventario getInventario() {
        return inventario;
    }

}
