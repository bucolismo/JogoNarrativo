public class ConstrutorDeCenas {

    private Cena cenaAtual;
    private String cenaID;

    public ConstrutorDeCenas() {
        this.cenaAtual = new Cena();
    }

    public Cena criarPrologo(Personagem narrador) {
        Cena prologo = new Cena();

        // NPCs secundários criados localmente apenas para ambientação do Prólogo
        Personagem radio = new Personagem("Rádio", 0, "Informativo");
        Personagem moradora = new Personagem("Moradora Local", 50, "Figurante");
        Personagem policial = new Personagem("Policial Militar", 35, "Figurante");
        Personagem jornaleiro = new Personagem("Jornaleiro", 60, "Figurante");

        // 1. Início
        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                Silêncio.
                
                Por alguns segundos, nenhum som.
                Então, lentamente, começa a chover. Primeiro algumas gotas.
                Depois, uma chuva constante.
                
                Ao fundo, quase imperceptível, o som de carros passando
                sobre o asfalto molhado.
                """
        ));

        // 2. Visão geral de Santa Aurora
        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                Uma cidade é vista de longe.
                O céu está completamente encoberto. A chuva cai pesada sobre a arquitetura que mistura
                prédios antigos e construções recentes. Uma fina camada de névoa arrasta-se
                pelas avenidas movimentadas.
                """
        ));

        // 3. Introdução narrativa da cidade
        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                "Santa Aurora nunca foi uma cidade tranquila... mas também nunca foi do tipo
                que chamava atenção. Pelo menos era o que todos diziam."
                """
        ));

        // 4. Noticiário do Rádio
        prologo.adicionaDialogo(new Dialogo(
                radio,
                """
                (Chiado de sintonização):
                "...a polícia confirmou hoje a identidade da quinta vítima relacionada à se-
                quência de homicídios que vem preocupando os moradores de Santa Aurora. Ape-
                sar da repercussão, as autoridades afirmam que ainda não há evidências sufi-
                cientes para interligar os crimes."
                """
        ));

        // 5. Relato da Moradora
        prologo.adicionaDialogo(new Dialogo(
                moradora,
                """
                [Rua residencial / Fachada de uma casa]
                Pessoas caminham apressadas sob guarda-chuvas. Uma senhora tranca a porta de
                casa com as mãos trêmulas e olha ao redor antes de puxar o casaco.
                
                "Não dá mais para andar tranquila depois que o sol se põe. Essa névoa... esse
                lugar está mudando."
                """
        ));

        // 6. Alerta do Policial Militar
        prologo.adicionaDialogo(new Dialogo(
                policial,
                """
                [CENA: Esquina da Avenida Central / Perímetro isolado]
                A luz giratória de uma viatura reflete na pista molhada. Fitas amarelas de
                isolamento bloqueiam a passagem para um beco escuro.
                
                "Circulando, pessoal! Deixem o trabalho com a perícia. Não tem nada para ver
                aqui."
                """
        ));

        // 7. Fala do Jornaleiro
        prologo.adicionaDialogo(new Dialogo(
                jornaleiro,
                """
                [CENA: Banca de Jornal]
                O jornaleiro ajusta os papéis sob o toldo que goteja. A manchete em destaque
                sob o plástico protetor diz: "QUINTA MORTE EM SEIS MESES".
                
                "Quinta morte em seis meses... Se continuar assim, nem o jornal da manhã vai
                dar conta de vender tanta notícia ruim."
                """
        ));

        // 8. Encerramento do Prólogo
        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                "O problema de uma cidade pequena é que segredos não ficam enterrados por muito
                tempo. E Santa Aurora estava prestes a cobrar o seu preço."
                
                ===============================================================================
                Pressione [ENTER] para iniciar a investigação...
                ===============================================================================
                """
        ));

        return prologo;
    }
}