public class Controlador {

    private Menu menu;
    private Entrada entrada;

    public Controlador() {
        entrada = new Entrada();
        menu = new Menu(entrada);
    }

    public void menuInicial() {

        int opcao;

        do {
            menu.mostraMenu();

            opcao = menu.validaOpcao();

            switch (opcao) {

                case 1:
                    iniciarPartida();
                    break;

                case 2:
                    menu.mostraInstrucoes();
                    break;

                case 3:
                    menu.mostraCreditos();
                    break;

                case 4:
                    menu.mostraSaida();
                    break;

            }

        } while (opcao != 4);
    }

    public void iniciarPartida() {
        Cena cenaAtual= new Cena();
        String nome = menu.recebeNome();
        int idade = menu.recebeIdade();
        String genero = menu.recebeGenero();

        ConstrutorDeCenas construtorDeCenas = new ConstrutorDeCenas();

        Protagonista protagonista = new Protagonista(nome, idade, genero);
        protagonista.cadastrarItens();

        Personagem narrador = new Personagem("Narrador", 0, "Neutro");
        NPC jonas = new NPC("Jonas", 30, "Masculino", 50);
        NPC daniel = new NPC("Daniel",42,"Masculino",50);

        int idCenaAtual = 1;

        while (idCenaAtual > 0) {
            cenaAtual = obterProximaCena(idCenaAtual, construtorDeCenas, narrador, protagonista, jonas,daniel);

            if (cenaAtual == null) {
                break;
            }

            executaCena(cenaAtual, protagonista);

            idCenaAtual++;
        }

        System.out.println("\n--- Fim do Jogo ---");

    }

    public void executaCena(Cena cena, Protagonista protagonista) {

        for (Dialogo dialogo : cena.getDialogos()) {

            int escolha = menu.mostraDialogo(dialogo);//Executa a cena e retorna a escolha ou 0 se não tiver escolha em dialogos

            if (dialogo.possuiOpcoes()) {

                Escolha escolhaSelecionada;

                do {
                    //Aq usa o índice da escolha do jogador para pegar o objeto escolha certo
                    escolhaSelecionada = dialogo.getEscolha(escolha);

                    int requisito = escolhaSelecionada.getRequisitoItem();//Pega o Item requisito(o nome dessa variável tá confuso na hr de ler)

                    if (requisito != -1 && !protagonista.getInventario().possuiItemPorId(requisito)) {
                        menu.mostraString("Você não tem o item necessário para essa escolha.");
                        escolhaSelecionada = null;
                        //Se escolheu errado pede dnv( sem mostrar o dialog dnv)

                        escolha = menu.validaOpcao();
                    }

                } while (escolhaSelecionada == null);

                menu.mostraString(escolhaSelecionada.getTexto());
                //Aqui faz a conecção com o ponto atual do roteiro com o próximo dialogo
                menu.mostraString(escolhaSelecionada.getTextoConsequencia());

                for (Efeito efeito : escolhaSelecionada.getEfeitos()) {
                    switch (efeito.getTipo()) {
                        case "ATRIBUTO" ->
                                protagonista.alteraAtributo(efeito.getAlvo(), efeito.getValor());

                        case "CONFIANCA" ->
                                efeito.getNpc().alterarConfianca(efeito.getValor());

                        case "ADICIONAR_ITEM" ->
                                protagonista.getInventario().adicionarItemPorId(efeito.getValor());

                        case "REMOVER_ITEM" ->
                                protagonista.getInventario().removerItemPorId(efeito.getValor());

                        default ->
                                System.out.println("Tipo de efeito desconhecido: " + efeito.getTipo());
                    }
                }
            }
        }
    }

    public Cena obterProximaCena(int idCena, ConstrutorDeCenas construtor, Personagem narrador, Protagonista protagonista, NPC jonas, NPC daniel) {
        //Por enquanto isso aqui funciona, pra a parte em que o roteiro está
        switch (idCena) {
            case 1:
                return construtor.criarPrologo(narrador, protagonista);
            case 2:
                return construtor.criarAtoI(protagonista,narrador,jonas,daniel);
            default:
                System.out.println("Cena não encontrada para o ID: " + idCena);
                return null;
        }
    }
}

