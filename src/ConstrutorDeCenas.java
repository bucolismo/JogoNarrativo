/**
 * é a classe responsável por construir as cenas que compõem a narrativa do jogo
 * Cada método cria uma cena com seus respectivos diálogos, personagens
 * e escolhas, organizando o desenvolvimento da história
 */

import java.util.ArrayList;
import java.util.List;

public class ConstrutorDeCenas {

    private Cena cenaAtual;
    private String cenaID;

    private static final int CONFIANCA_INICIAL = 5;        // todo NPC começa aqui (escala 0-10)
    private static final int CONFIANCA_ALIADO_LEAL = 9;     // portão de "parceria total"
    private static final int CONFIANCA_PARCERIA_BASICA = 7; // portão de acesso a favores pontuais
    private static final int CONFIANCA_HOSTIL = 2;          // abaixo disso, o NPC vira obstáculo

    private static final int LIMITE_CHECKPOINT_ATO_IV = 15; // portão da escolha decisiva do Ato IV
    private static final int LIMITE_FINAL_ATO_VI = 24;       // portão de atributo para os finais
    private static final int LIMITE_PROVAS_FINAL_RAZAO = 6;  // evidências mínimas para o final legal

    public ConstrutorDeCenas() {
        this.cenaAtual = new Cena();
    }

    Efeito aumentaRazao = new Efeito("ATRIBUTO", "Razão", 3);
    Efeito aumentaParanioa = new Efeito("ATRIBUTO", "Paranoia", 3);
    Efeito aumentaViolencia = new Efeito("ATRIBUTO", "Violência", 3);

    Efeito ganhaProva = new Efeito("ATRIBUTO", "Provas", 1);

    public Cena criarPrologo(Personagem narrador, Protagonista protagonista) {
        Cena prologo = new Cena();

        // NPCs secundários criados localmente apenas para ambientação do Prólogo
        Personagem radio = new Personagem("Rádio", 0, "Informativo");
        Personagem moradora = new Personagem("Moradora Local", 50, "Figurante");
        Personagem policial = new Personagem("Policial Militar", 35, "Figurante");
        Personagem jornaleiro = new Personagem("Jornaleiro", 60, "Figurante");

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

        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                        Uma cidade é vista de longe.
                        O céu está completamente encoberto. A chuva cai pesada sobre a arquitetura que mistura
                        prédios antigos e construções recentes. Uma fina camada de névoa arrasta-se
                        pelas avenidas movimentadas.
                        """
        ));

        prologo.adicionaDialogo(new Dialogo(
                narrador,
                """
                        "Santa Aurora nunca foi uma cidade tranquila... mas também nunca foi do tipo
                        que chamava atenção. Pelo menos era o que todos diziam."
                        """
        ));

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

    public Cena criarAtoI(Protagonista protagonista, Personagem narrador, NPC jonas, Personagem daniel) {

        Cena atoI = new Cena("ato_1");

        // =========================================================================
        // A CHEGADA EM SANTA AURORA
        // =========================================================================

        Dialogo dialogo1 = new Dialogo(narrador, """
                [CENA: Interior de um ônibus de viagem. Chuva bate contra a janela.]

                O balanço do veículo é constante. Do lado de fora, a névoa de Santa Aurora
                engole a paisagem familiar. Você observa seu próprio reflexo no vidro molhado.

                > PROTAGONISTA:
                "Eu não lembrava da cidade parecer tão pequena. Talvez fosse eu que tivesse
                mudado."

                *(O ônibus desacelera até parar com um chiado nos freios. As portas se abrem.)*

                Você desce na plataforma da rodoviária deserta. O ar frio da noite atinge
                seu rosto. No seu bolso, o celular vibra.

                [NOVA MENSAGEM - DANIEL]
                "Quando chegar, me procure na delegacia."

                Você guarda o aparelho. Um peso no peito lembra o motivo de estar aqui.
                """);

        atoI.adicionaDialogo(dialogo1);

        // =========================================================================
        // A LEMBRANÇA DE GABRIEL
        // =========================================================================

        Dialogo dialogo2 = new Dialogo(narrador, """
                A lembrança do seu irmão Gabriel preenche seus pensamentos. O desentendimento
                que os afastou anos atrás parece distante e irrelevante agora perante a notícia
                de sua morte brutal em Santa Aurora.
                """);

        atoI.adicionaDialogo(dialogo2);

        // =========================================================================
        // DIÁLOGO 3 — O PRIMEIRO PASSO NA ESTAÇÃO
        // =========================================================================

        Escolha escolhaDialogo3_1 = new Escolha("""
                [1] Analisar o mapa da cidade na parede da estação para calcular o trajeto
                    mais rápido até a delegacia e entender onde a quinta vítima foi achada.
                """,
                aumentaRazao,
                """
                        Você se aproxima do mapa manchado de umidade. Com calma, cruza as informações
                        da mensagem de Daniel com os locais de isolamento policial que ouviu no rádio.

                        > VOZ :
                        > "O tempo entre a rodoviária e o centro é de dez minutos a pé. Se cortarmos
                        > pela travessa, evitamos a névoa densa e chegamos com fatos, não com suposições."
                        """
        );

        Escolha escolhaDialogo3_2 = new Escolha("""
                [2] Observar os arredores da plataforma. Aquele sujeito encostado no poste
                    parece estar vigiando quem desce do ônibus.
                """,
                aumentaParanioa,
                """
                        Você ajusta a gola do casaco e disfarça o olhar. O homem no poste acende um
                        cigarro, mas os olhos dele acompanham cada movimento das suas mãos.

                        > VOZ:
                        > "Ele sabe quem você é. A notícia da sua volta já circulou antes mesmo de o
                        > ônibus estacionar. Ninguém fica parado no frio à meia-noite sem um motivo."
                        """
        );

        Escolha escolhaDialogo3_3 = new Escolha("""
                [3] Segurar com força o medalhão no bolso, ignorar a chuva e marchar firme
                    direto para a rua, pronto para encarar qualquer um que cruzar seu caminho.
                """,
                aumentaViolencia,
                """
                        Você ignora o mapa, a névoa e as pessoas. Seus passos ecoam pesados contra
                        o paralelepípedo molhado, cortando a noite com determinação cega.

                        > VOZ:
                        > "Deixe que vejam você chegando. Se alguém nesta cidade acha que pode brincar
                        > com a memória do seu irmão, vai descobrir do pior jeito que você voltou."
                        """
        );

        Dialogo dialogo3 = new Dialogo(
                protagonista,
                """
                        Você guarda o celular no bolso. O eco das vozes ainda vibra nos seus ouvidos,
                        mas o peso do medalhão de bronze no seu bolso — o mesmo que seu irmão te deu
                        na infância — traz um choque doloroso de realidade.

                        Você precisa decidir como dar o primeiro passo nesta cidade.
                        """,
                escolhaDialogo3_1,
                escolhaDialogo3_2,
                escolhaDialogo3_3
        );

        atoI.adicionaDialogo(dialogo3);

        // =========================================================================
        // DIÁLOGO 4 — ENCONTRO COM O VELHO JONAS
        // =========================================================================

        Escolha escolhaDialogo4_1 = new Escolha("""
                [1] "Jonas, acalme-se. Me diga exatamente o que ele estava fazendo nos últimos
                    dias e quem foi a última pessoa a falar com ele."
                """,
                new ArrayList<>(List.of(aumentaRazao, ganhaProva)),
                """
                        Jonas solta um suspiro pesado, ajeitando o casaco. Sua postura firme o acalma.

                        MORADOR (JONAS):
                        "Ele... ele estava na antiga fábrica desativada na semana passada. Dizia que
                        achou registros antigos da prefeitura que não batiam. Falou que ia encontrar
                        o Daniel para entregar tudo..."

                        (Obteve Pista: Informação de Jonas [ID: 6])
                        """
        );

        Escolha escolhaDialogo4_2 = new Escolha("""
                [2] "Quem te mandou aqui para me avisar? Você está vigiando a entrada da cidade
                    para quem?"
                """,
                aumentaParanioa,
                """
                        Jonas recua dois passos, assustado com o seu olhar desconfiado.

                        MORADOR (JONAS):
                        "Ninguém me mandou! Eu só... eu vi a movimentação no posto de polícia mais cedo.
                        Tinha gente estranha lá, garoto. Gente que não é da cidade perguntando por você!"
                        """
        );

        Escolha escolhaDialogo4_3 = new Escolha("""
                [3] Segurar Jonas pelo colarinho do casaco: "Chega de rodeios, velho! Fala de
                    uma vez o que aconteceu com ele antes que eu perca a paciência!"
                """,
                aumentaViolencia,
                """
                        Jonas treme, arregalando os olhos com o impacto. Ele aponta para o beco da delegacia.

                        MORADOR (JONAS):
                        "Calma, garoto! Eu não sei de nada! Só sei que o Daniel tá te esperando no beco
                        dos fundos da delegacia... e ele não tá sozinho!"
                        """
        );

        Dialogo dialogo4 = new Dialogo(
                jonas,
                """
                        A caminho do seu destino, uma figura surge da névoa. É o Velho Jonas, um antigo
                        conhecido da família que trabalhava com seu irmão. Ele parece assustado e segura
                        uma lanterna com a mão trêmula.

                        MORADOR (JONAS):
                        "Garoto?... É você mesmo? Pelas barbas de Deus, você não devia ter voltado.
                        Aquele assunto do seu irmão... ele estava mexendo onde não devia!"
                        """,
                escolhaDialogo4_1,
                escolhaDialogo4_2,
                escolhaDialogo4_3
        );

        atoI.adicionaDialogo(dialogo4);

        // =========================================================================
        // DIÁLOGO 4.1 — GANHANDO (OU PERDENDO) A CONFIANÇA DE JONAS
        // =========================================================================
        // Requer os efeitos melhoraRelacaoJonas / pioraRelacaoJonas — defina-os
        // como campos da classe, no mesmo padrão de aumentaRazao/aumentaParanioa.

        Escolha escolhaDialogo4_1_1 = new Escolha("""
                [1] "Eu sei que isso é pesado, Jonas. Mas confio em você — foi o Gabriel
                    quem sempre disse que você era de confiança. Me ajuda a entender
                    o resto."
                """,
                new ArrayList<>(List.of(new Efeito(jonas,3), aumentaRazao)),
                """
                        Jonas relaxa os ombros pela primeira vez desde que apareceu na névoa.
                        Ele parece aliviado por ouvir o nome do seu irmão dito sem raiva.

                        MORADOR (JONAS):
                        "Ele falava de você toda semana, sabia? Achava que um dia vocês dois
                        iam resolver as coisas. Vem, garoto... te mostro onde ele escondia
                        as anotações."

                        (Relação com Jonas melhorou)
                        """
        );

        Escolha escolhaDialogo4_1_2 = new Escolha("""
                [2] "Não tenho tempo pra isso agora, Jonas. Se sabe de algo, fala logo
                    ou sai da minha frente."
                """,
                new ArrayList<>(List.of(new Efeito(jonas,-2), aumentaParanioa)),
                """
                        Jonas se encolhe, os olhos marejados. Ele dá um passo para trás,
                        a lanterna tremendo ainda mais na mão.

                        MORADOR (JONAS):
                        "Tudo bem... tudo bem. Você mudou mesmo, garoto. Não vou te atrapalhar
                        mais."

                        (Relação com Jonas piorou)
                        """
        );

        Escolha escolhaDialogo4_1_3 = new Escolha("""
                [3] Ficar em silêncio, apenas observando Jonas, esperando que ele
                    continue por conta própria.
                """,
                aumentaParanioa,
                """
                        O silêncio pesa entre vocês. Jonas se remexe, incomodado, mas
                        acaba enchendo o vazio sozinho.

                        MORADOR (JONAS):
                        "Você sempre foi de poucas palavras... assim como o Gabriel quando
                        tava puto com alguma coisa. Só espero que não esteja puto comigo."
                        """
        );

        Dialogo dialogo4_1 = new Dialogo(
                jonas,
                """
                        Jonas hesita antes de continuar falando, os olhos passeando entre
                        você e a névoa atrás de si, como se avaliasse se pode confiar no
                        que vai dizer a seguir.
                        """,
                escolhaDialogo4_1_1,
                escolhaDialogo4_1_2,
                escolhaDialogo4_1_3
        );

        atoI.adicionaDialogo(dialogo4_1);

        // =========================================================================
        // DIÁLOGO 4.2 — A PISTA DE JONAS (condicionada ao relacionamento)
        // =========================================================================
        // Se seu motor suportar condição de estado nas Escolhas, prefira travar
        // escolhaDialogo4_2_1 atrás de uma condição do tipo:
        // estado.getRelacaoJonas() >= 1

        Escolha escolhaDialogo4_2_1 = new Escolha("""
                [1] "Jonas, se você confia em mim, me entrega o que o Gabriel te deixou."
                """,
                new ArrayList<>(List.of(new Efeito(jonas,3), new Efeito(6), ganhaProva)),
                """
                        Jonas suspira fundo, como quem solta um peso carregado por dias.
                        Ele tira do bolso do casaco um envelope amassado e úmido de chuva.

                        MORADOR (JONAS):
                        "Ele me disse... 'só entrega isso pra alguém que eu confiaria de
                        olhos fechados'. Acho que só pode ser você, garoto."

                        (Obteve Pista: Pista De Jonas)
                        (Relação com Jonas melhorou)
                        """
        );

        Escolha escolhaDialogo4_2_2 = new Escolha("""
                [2] "Jonas, para de enrolar e me entrega logo isso que você tá escondendo!"
                """,
                new Efeito(jonas,-2),
                """
                        Jonas recua, protegendo o bolso do casaco com o braço.

                        MORADOR (JONAS):
                        "Não! Não do jeito que você tá agora. O Gabriel confiava em mim
                        justamente pra saber a hora certa. E essa hora não é essa."

                        (Jonas se recusa a entregar a pista)
                        """
        );

        Dialogo dialogo4_2 = new Dialogo(
                jonas,
                """
                        Jonas segura algo no bolso do casaco, indeciso. A forma como ele
                        reage agora parece depender inteiramente de como você o tratou
                        até aqui.
                        """,
                escolhaDialogo4_2_1,
                escolhaDialogo4_2_2
        );

        atoI.adicionaDialogo(dialogo4_2);

        // =========================================================================
        // DIÁLOGO 5 — A CENA NO BECO
        // =========================================================================

        Escolha escolhaDialogo5_1 = new Escolha("""
                [1] Agachar-se para recolher as provas na lama e checar a pessoa no chão de
                    forma metódica.
                """,
                new ArrayList<>(List.of(aumentaRazao, new Efeito(7), ganhaProva)),
                """
                        Você analisa as páginas molhadas: são cópias do laudo do seu irmão com rasuras
                        propositais. A pessoa no chão é apenas um manequim usado para chamar atenção.

                        (Obteve Pista: Pista do Manequim [ID: 7])
                        """
        );

        Escolha escolhaDialogo5_2 = new Escolha("""
                [2] Dar a volta por trás dos caixotes, usando a névoa como cobertura para
                    analisar o beco sem ser visto.
                """,
                aumentaParanioa,
                """
                        Sua cautela revela um olheiro escondido no topo da escada de incêndio, com uma
                        câmera apontada para o beco. Ao notar que foi descoberto, o sujeito foge,
                        porém rápido demais para saber quem estava monitorando a polícia.
                        """
        );

        Escolha escolhaDialogo5_3 = new Escolha("""
                [3] Marchar direto em direção à sombra na penumbra, pronto para o confronto.
                """,
                new ArrayList<>(List.of(aumentaViolencia, new Efeito(8), ganhaProva)),
                """
                        Você arromba a porta de serviço com um chute, surpreendendo um suspeito que
                        tentava queimar o restante dos arquivos do seu irmão. Você o imobiliza antes que
                        as evidências sejam destruídas.

                        (Obteve Evidência: Laudo Adulterado [ID: 8])
                        """
        );

        Dialogo dialogo5 = new Dialogo(
                protagonista,
                """
                        Ao se aproximar do bloco policial, a iluminação da rua falha. Você percebe que
                        a entrada principal está trancada. Há um rastro de documentos espalhados na lama
                        perto da porta de serviço, além de uma figura caída na penumbra do beco.

                        > VOZ DA RAZÃO:
                        > "Examine a cena primeiro. Recolha os papéis e verifique os sinais vitais
                        > antes de tomar qualquer atitude impulsiva."

                        > VOZ DA CONSPIRAÇÃO:
                        > "É uma emboscada clara. Alguém deixou essa cena montada para ver como você
                        > reage. Mantenha as costas na parede e observe as sombras."

                        > VOZ DA VIOLÊNCIA:
                        > "Esqueça a cautela. Avance rápido, segure quem estiver ali e garanta que
                        > ninguém saia desse beco sem te dar respostas."
                        """,
                escolhaDialogo5_1,
                escolhaDialogo5_2,
                escolhaDialogo5_3
        );

        atoI.adicionaDialogo(dialogo5);

        return atoI;
    }


    public Cena criarFinalAtoI(Personagem narrador, Protagonista protagonista) {
        Cena cenaFinalAtoI = new Cena("fim_ato_1");

        // Requisito: Laudo Adulterado (item 8) — você precisa da prova física
        // para confrontar Daniel de forma metódica.
        Escolha escolha1 = new Escolha("""
                [1] [Opção 1A — O Confronto Metódico]
                    Entrar na delegacia com os documentos rasurados em mãos e confrontar Daniel com a lógica.
                """,
                aumentaRazao,
                """
                        Você ajusta o casaco, aperta os documentos sob o braço e caminha firme em direção às portas de vidro da delegacia.

                        [ROTA ATIVADA: ATO II - OPÇÃO 1A (O CONFRONTO METÓDICO)]
                        """
        );
        escolha1.defineRequisitoItem(8);

        // Sem requisitos — o "blefe" não depende de provas concretas.
        Escolha escolha2 = new Escolha("""
                [2] [Opção 1B — O Jogo Duplo]
                    Entrar na delegacia fingindo ingenuidade para ver até onde Daniel mentirá.
                """,
                aumentaParanioa,
                """
                        Você guarda os papéis no bolso interno, respira fundo e assume uma postura inofensiva antes de empurrar a porta da delegacia.

                        [ROTA ATIVADA: ATO II - OPÇÃO 1B (O JOGO DUPLO)]
                        """
        );

        // Requisito: Pista de Jonas (item 6)
        Escolha escolha3 = new Escolha("""
                [3] [Opção 2A — O Esconderijo Subterrâneo]
                    Ignorar a delegacia e seguir as pistas de Jonas até a fábrica abandonada.
                """,
                aumentaParanioa,
                """
                        Você dá as costas para a iluminação da delegacia e caminha pelas sombras em direção ao distrito industrial abandonado.

                        [ROTA ATIVADA: ATO II - ROTA 2A (O ESCONDERIJO SUBTERRÂNEO)]
                        """
        );
        escolha3.defineRequisitoItem(6);

        // Requisito: Pista do Manequim (item 7)
        Escolha escolha4 = new Escolha("""
                [4] [Opção 2B — A Rota dos Arquivos Mortos]
                    Ir ao Cartório/Biblioteca Municipal para cruzar as rasuras com os registros de imóveis.
                """,
                aumentaRazao,
                """
                        Você decide que a resposta está nos documentos oficiais antigos. Você segue sob a chuva até o prédio do Cartório Central.

                        [ROTA ATIVADA: ATO II - ROTA 2B (OS ARQUIVOS MORTOS)]
                        """
        );
        escolha4.defineRequisitoItem(7);

        //Sem pré requisitos
        // Leva ao confronto no beco (Rota 3A), que ainda permite investigar.
        Escolha escolha5 = new Escolha("""
                [5] [Opção 3A — O Confronto no Beco]
                       Marchar diretamente até o beco lateral da delegacia ao perceber movimentação
                       suspeita perto dos contêineres.""",
                aumentaViolencia,
                """
                        Você marcha até o beco lateral, os punhos fechados, os olhos fixos na movimentação suspeita entre os contêineres.

                        [ROTA ATIVADA: ATO II - ROTA 3A (O CONFRONTO NO BECO)]
                        """
        );

        //Requisito Violêcia = 7
        Escolha escolha6 = new Escolha("""
                [6] [Opção 3B — Invasão à Delegacia]
                    Chutar a porta da delegacia, peitar o detetive Daniel e exigir acesso imediato.
                """,
                aumentaViolencia,
                """
                        A raiva se sobrepõe completamente ao bom senso. Você marcha até a delegacia e escancara as portas de entrada com um golpe, sem plano e sem provas nas mãos.

                        [ROTA ATIVADA: ATO II - ROTA 3B (INVASÃO À DELEGACIA)]
                        """
        );
        escolha6.defineRequisitoAtributo("VIOLÊNCIA", 7);

        Dialogo dialogoFinal = new Dialogo(
                narrador,
                """
                        ===============================================================================
                        ATO I — A ENCRUZILHADA DE SANTA AURORA (FINAL DO ATO I)
                        ===============================================================================

                        [CENA: Praça Central de Santa Aurora / Noite Chuvosa / 00h45]

                        A névoa espessa desce dos morros e abraça os paralelepípedos frios da praça.
                        Gotas grossas de chuva escorrem do seu casaco enquanto o letreiro luminoso da
                        delegacia pisca ao fundo, refletindo poças avermelhadas no asfalto.

                        Jonas acabou de se afastar na escuridão, desaparecendo entre as vielas. Suas
                        palavras ainda ecoam na sua mente, somando-se às poucas certezas que você reuniu
                        até agora. O relógio da torre matriz bate uma badalada ecoante. É o momento
                        de decidir seu próximo passo.

                        > VOZ DA RAZÃO:
                        > "Temos dados e papéis rasurados. Se usarmos a lógica e os fatos frios, podemos
                        > encurralar quem está mentindo no relatório antes que queimem os arquivos originais."

                        > VOZ DA CONSPIRAÇÃO:
                        > "Ninguém nesta cidade é confiável. A polícia quer abafar o caso e as respostas
                        > reais estão escondidas nas sombras ou trancadas sob velhas escrituras."

                        > VOZ DA VIOLÊNCIA:
                        > "Chega de rodeios, burocracia e conversas sutis. Se você quer justiça pelo seu
                        > irmão, precisa tomar a iniciativa e fazer os culpados sangrarem."
                        """,
                escolha1,
                escolha2,
                escolha3,
                escolha4,
                escolha5,
                escolha6
        );

        cenaFinalAtoI.adicionaDialogo(dialogoFinal);

        return cenaFinalAtoI;
    }

    public Cena criarAtoIIRota1B(Personagem narrador, Protagonista protagonista, NPC daniel) {
        Escolha escolhaAuto1 = new Escolha("""
                [1] Fingir choque emocional e desorientação:
                    "Eu não sei de nada, Daniel... Só quero entender o que aconteceu. Quem faria uma coisa dessas com ele?"
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, 2))), """
                Daniel relaxa os ombros ao ver sua reação desolada. Ele se encosta na cadeira,
                convencido de que você não passa de um familiar vulnerável buscando conforto.

                DANIEL:
                "É difícil aceitar, eu sei. Ele se envolveu com as pessoas erradas nos últimos
                meses... Pessoas perigosas da periferia. Mas não se preocupe, estamos cuidando."

                [CONFIANÇA COM DANIEL: +2 — ele acredita que você é inofensivo]
                """);

        Escolha escolhaAuto2 = new Escolha("""
                [2] Demonstrar cooperação analítica:
                    "A cidade parece a mesma de sempre, mas os boatos no rádio dizem que a polícia não tem pistas. Isso é verdade?"
                """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
                Daniel ajeita as pastas sobre a mesa, medindo as palavras com cuidado.

                DANIEL:
                "O rádio exagera para vender notícia. Temos pistas, sim, mas precisamos de
                cautela para não alertar os suspeitos antes da hora."

                [CONFIANÇA COM DANIEL: +1]
                """);

        Escolha escolhaAuto3 = new Escolha("""
                [3] Dar um passo à frente com o olhar fixo:
                    "Eu passei por pessoas estranhas na rua, Daniel. E sinto que a própria polícia está pisando em ovos."
                """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -3))), """
                Daniel estreita os olhos e cruza os braços, assumindo uma postura defensiva.

                DANIEL:
                "Medir as palavras aqui dentro seria uma boa ideia. A polícia faz o trabalho
                dela, e você deveria focar no enterro do seu irmão."

                [CONFIANÇA COM DANIEL: -3]
                """);

        Escolha escolhaAuto4 = new Escolha("""
                [1] Permanecer parado na mesma posição, ignorando o arquivo para não demonstrar nenhum interesse suspeito.
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, 1))), """
                Você não move um centímetro. Daniel retorna com o copo de água e percebe
                sua postura contida. Ele conclui que você é inofensivo e não desconfia de nada.

                [CONFIANÇA COM DANIEL: +1 (Ele acredita que tem o controle da situação)]
                """);

        Escolha escolhaAuto5 = new Escolha("""
                [2] Usar o reflexo do vidro da janela para tentar ler o título da folha principal sem mover um músculo da mão.
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -1))), """
                Pelo reflexo do vidro, você decifrou o cabeçalho: "Relatório de Depoimento —
                Testemunha Não Identificada". Daniel nota que você olhava para o vidro e fica
                mais cauteloso.

                [CONFIANÇA COM DANIEL: -1 (Ele percebe que você desconfia)]
                """);

        Escolha escolhaAuto6 = new Escolha("""
                [3] Puxar o arquivo rapidamente e folhear as páginas em busca dos nomes de testemunhas antes que ele se vire.
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -3))), """
                Ao ouvir o papel se mover, Daniel se vira rapidamente e bate a mão sobre a mesa,
                recolhendo a pasta antes que você leia os detalhes.

                [CONFIANÇA COM DANIEL: -3 (Ele passa a te enxergar como uma ameaça imprevisível)]
                """);

        Cena atoIIOpcao1B = new Cena("ato2_opcao1b");
        atoIIOpcao1B.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ATO II — O JOGO DUPLO (ROTA: CONSPIRAÇÃO / OPÇÃO 1B)
                ===============================================================================

                [CENA: Sala do Detetive Daniel. Luz amarelada, chuva batendo contra a janela.]

                Você entra na sala de Daniel com uma expressão neutra, escondendo as certezas
                que já possui. O detetive está sentado atrás da mesa, organizando papéis sob a
                luz fraca de um abajur. Ele parece surpreso ao ver você caminhar tão calmo.

                DANIEL:
                "Você demorou... Sinto muito pelo seu irmão. Eu sei que as coisas entre vocês
                ficaram difíceis no final, mas estamos fazendo tudo o que podemos aqui. Você
                notou algo de estranho ao chegar na cidade?"

                > VOZ DA CONSPIRAÇÃO:
                > "Ele está testando até onde você sabe. Ele quer medir o seu nível de suspeita
                > antes de te dar qualquer informação falsa. Mantenha as cartas escondidas."
                """,
                escolhaAuto1,
                escolhaAuto2,
                escolhaAuto3
        ));
        atoIIOpcao1B.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ESCOLHA 2 — A ISCA SOBRE A MESA
                ===============================================================================

                Daniel abre a gaveta e retira um arquivo parcial do caso, colocando-o bem na
                sua frente sobre a mesa. Ele se levanta para pegar um copo de água no canto
                da sala, deixando você a sós com o documento por alguns segundos.

                > VOZ DA CONSPIRAÇÃO:
                > "É um teste clássico. Ele quer ver se você vai tentar bisbilhotar o relatório
                > oficial enquanto ele está de costas."
                """,
                escolhaAuto4,
                escolhaAuto5,
                escolhaAuto6
        ));
        return atoIIOpcao1B;
    }

    public Cena criarAtoIIRota2B(Personagem narrador, Protagonista protagonista) {
        Escolha escolhaAuto1 = new Escolha("""
                [1] Mapear o painel elétrico nos fundos e desligar a energia para desativar
                    quaisquer alarmes do prédio antes de entrar.
                """, aumentaRazao, """
                Você desliga o disjuntor principal. O prédio mergulha na escuridão total. Usando
                a lanterna do celular velada pela mão, você avança diretamente para a ala dos
                livros do século passado.
                """);

        Escolha escolhaAuto2 = new Escolha("""
                [2] Observar as janelas do andar superior. Há uma luz fraca acesa no escritório
                    do arquivista; alguém já está lá dentro.
                """, aumentaParanioa, """
                Sua cautela evita que você seja surpreendido. Da sombra do pátio, você vê duas
                figuras de terno vasculhando as prateleiras do fundo e ateando fogo em pastas
                específicas.
                """);

        Escolha escolhaAuto3 = new Escolha("""
                [3] Forçar a tranca da porta dos fundos com uma alavanca para garantir uma
                    saída rápida, independentemente de fazer barulho.
                """, aumentaViolencia, """
                A madeira cede com um estrondo seco. O barulho ecoa pelo saguão, chamando
                a atenção imediata dos homens que limpavam o local.
                """);

        Escolha escolhaAuto4 = new Escolha("""
                [1] Fotografar as rasuras e as datas dos documentos para cruzar os horários
                    com os laudos da polícia mais tarde.
                """, new ArrayList<>(List.of(aumentaRazao, ganhaProva)), """
                Você registra os carimbos e as datas apagadas. As fotos garantem que você
                tenha provas de que o arquivo foi adulterado, mas o tempo gasto na operação
                permite que o prédio seja cercado em silêncio.
                """);

        Escolha escolhaAuto5 = new Escolha("""
                [2] Procurar por relatórios anexados que mencionem quem solicitou a retirada
                    dessas pastas do arquivo público.
                """, new ArrayList<>(List.of(aumentaParanioa, ganhaProva)), """
                Você encontra um protocolo de retirada assinado por um código interno da
                prefeitura. Antes que possa decifrar o número, o som seco de uma tranca sendo
                acionada na porta do corredor ressoa pelo prédio.
                """);

        Escolha escolhaAuto6 = new Escolha("""
                [3] Coletar os papéis mais recentes e guardá-los no casaco antes de continuar
                    vasculhando as gavetas.
                """, aumentaViolencia, """
                Você puxa as pastas principais, garantindo a posse dos papéis originais. O
                barulho das gavetas metálicas se chocando ecoa pelo galpão e chama a atenção
                dos homens que patrulhavam a ala externa.
                """);

        Cena cenaAtoII = new Cena("ato2_rota2b");
        cenaAtoII.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ATO II — A ROTA DOS ARQUIVOS MORTOS (ROTA: 2B)
                ===============================================================================

                [CENA: Cartório e Arquivo Municipal de Santa Aurora. Madrugada.]

                Você decide que confiar em qualquer estrutura da delegacia é um risco desnecessário.
                Com as cópias dos relatórios rasurados em mãos, você caminha sob a chuva densa até o antigo
                prédio do Cartório Municipal, onde os registros imobiliários e arquivos mortos da cidade são guardados.

                O prédio histórico está silencioso. As portas de madeira maciça estão trancadas, mas uma
                janela de ventilação nos fundos permite o acesso ao piso inferior.

                > VOZ DA RAZÃO:
                > "Se os horários das mortes foram alterados e as propriedades ao redor dos crimes mudaram de
                > dono recentemente, os livros de registro do cartório indicarão o verdadeiro beneficiário."
                """,
                escolhaAuto1,
                escolhaAuto2,
                escolhaAuto3
        ));

        cenaAtoII.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ESCOLHA 2 — A BUSCA NOS ARQUIVOS
                ===============================================================================

                No fundo do arquivo morto, você localiza as pastas de registros antigos da
                região onde seu irmão foi encontrado. Os papéis estão amarelados e cobertos
                de mofo, mas revelam incongruências gritantes: assinaturas rasuradas, taxas
                imobiliárias sem comprovante e nomes de testemunhas que simplesmente sumiram
                dos cadastros da prefeitura nos últimos seis meses.

                Seu irmão não estava apenas investigando um crime isolado; ele estava
                rastreando a origem de documentos que a cidade tentou apagar há anos.

                > VOZ:
                > "As datas das rasuras coincidem com o início das mortes. Há um padrão de
                > ocultação aqui, mas o motivo principal ainda está oculto."
                """,
                escolhaAuto4,
                escolhaAuto5,
                escolhaAuto6
        ));

        cenaAtoII.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                DESFECHO DA ROTA 2B — GAME OVER (O PREÇO DO CONHECIMENTO)
                ===============================================================================

                [CENA: Saguão do Arquivo Municipal]

                Você segura as pastas e tenta alcançar a saída dos fundos. No entanto, a luz
                de projetores externos atravessa os vitrais do cartório. Viaturas sem identificação
                bloqueiam todas as vias do quarteirão.

                As portas da frente são arrombadas simultaneamente. Homens usando uniformes
                táticos pretos invadem o salão. No centro do grupo, surge o Prefeito de Santa
                Aurora, acompanhado por um Daniel silencioso e de cabeça baixa.

                PREFEITO:
                "Seu irmão cometeu exatamente o mesmo erro que você. Ele achou que podia mexer
                em arquivos antigos sem que ninguém notasse. Santa Aurora tem coisas que
                devem permanecer esquecidas."

                Você tenta reagir ou usar a documentação como barganha, mas a desproporção de
                força é absoluta. Os homens armados avançam. As provas são tiradas das suas
                mãos e queimadas em um tambor de metal ali mesmo no saguão.

                > VOZ DA RAZÃO / CONSPIRAÇÃO / VIOLÊNCIA:
                > "Aproximamo-nos demais sem saber com o que estávamos lidando..."

                Você é imobilizado. A névoa de Santa Aurora invade o salão pela porta aberta,
                enquanto a cidade garante que nem você, nem seu irmão, jamais façam as
                perguntas certas.

                ===============================================================================
                FIM DE JOGO — [ROTA 2B: SILENCIADO PELO ARQUIVO]
                ===============================================================================
                """)
        );

        return cenaAtoII;
    }

    public Cena criarAtoIIRota3B(Personagem narrador, Protagonista protagonista) {
        Cena cenaRota3B = new Cena("ato3_rota3b");
        cenaRota3B.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                DESDOBRAMENTOS DA OPÇÃO 3B — A INVASÃO À DELEGACIA
                ===============================================================================

                [CENA: Saguão da Delegacia de Santa Aurora]

                Tomado pelo impulso e pela raiva, você força a passagem pela porta principal
                da delegacia, empurrando o plantonista e exigindo acesso imediato aos arquivos
                do seu irmão.

                Sua abordagem agressiva faz soar o alarme interno. Em segundos, três policiais
                armados saem das salas laterais e o cercam no saguão. Daniel surge logo em
                seguida, mantendo a mão no coldre e uma expressão severa.

                DANIEL:
                "Você passou de todos os limites. Isso aqui é uma repartição pública, não a
                sua casa. Você está preso por desacato, invasão e obstrução de justiça."

                ===============================================================================
                RESULTADO DA AÇÃO — PRISÃO E FIM DE JOGO (GAME OVER)
                ===============================================================================

                Você é imobilizado contra o balcão de atendimento e algemado antes que possa
                esboçar qualquer reação. Todos os seus pertences — incluindo as poucas pistas
                que havia coletado até agora — são apreendidos e guardados no cofre da perícia.

                Trancado em uma cela no subsolo da delegacia, você assiste da grade enquanto
                Daniel assina a sua transferência para o presídio regional na manhã seguinte.

                Sem liberdade para investigar e sem acesso aos documentos, a busca pela
                verdade sobre o seu irmão termina ali, abafada pela burocracia e pelo peso
                da lei em Santa Aurora.

                > VOZ DA VIOLÊNCIA:
                > "Agimos no impulso... e o sistema usou a própria força dele para nos anular."

                ===============================================================================
                FIM DE JOGO — [ROTA 3B: PRESO POR DESACATO]
                ===============================================================================
                """
        ));

        return cenaRota3B;
    }

    public Cena criarAtoIIRota1A(Personagem narrador, Protagonista protagonista, NPC daniel) {
        Escolha escolhaAuto1 = new Escolha("""
                [1] Apontar para a diferença de horários entre o chamado e o laudo pericial:
                    "A autópsia indica a morte duas horas antes do chamado oficial. Por que o relatório foi alterado, Daniel?"
                """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2), ganhaProva)), """
                Daniel desvia o olhar para os papéis na mesa e solta um suspiro pesado. Ele
                tranca a porta do escritório para que os outros policiais não ouçam a conversa.

                DANIEL:
                "Você sempre foi analítico como ele... Escute, nem tudo o que acontece nesta
                delegacia passa pelas minhas mãos. Se esse relatório foi alterado, veio de
                instâncias superiores."

                [CONFIANÇA COM DANIEL: +2 — ele reconhece que você age com método]
                """);

        Escolha escolhaAuto2 = new Escolha("""
                [2] Observar a reação do detetive enquanto fala:
                    "Você parece surpreso em ver esses papéis. Estava esperando que eles ficassem perdidos no beco?"
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -1))), """
                Os olhos de Daniel se arregalam por um segundo antes de ele recuperar a postura.
                Ele dá um passo para trás e cruza os braços, adotando uma postura defensiva.

                DANIEL:
                "Você está vendo fantasmas onde não tem. Se alguém deixou esses papéis no beco,
                foi para nos colocar um contra o outro."

                [CONFIANÇA COM DANIEL: -1]
                """);

        Escolha escolhaAuto3 = new Escolha("""
                [3] Bater a mão na mesa de Daniel:
                    "Deixe as condolências de lado e me diga quem mandou adulterar o relatório do meu irmão!"
                """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -3))), """
                O baque no tampo da mesa faz a xícara de café de Daniel balançar. Dois policiais
                no corredor olham pelo vidro da porta, mas Daniel levanta a mão pedindo calma.

                DANIEL:
                "Abaixe o tom! Você não está em posição de fazer exigências aqui. Se quiser
                respostas, vai ter que me ouvir sem chilique."

                [CONFIANÇA COM DANIEL: -3]
                """);

        Escolha escolhaAuto4 = new Escolha("""
                [1] "Se colaborarmos, eu garanto que essa pasta nunca precisa vazar para os jornais fora de Santa Aurora."
                """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2))), """
                Daniel avalia a proposta com cautela. Um acordo tácito, não uma ameaça vazia,
                é algo que ele consegue respeitar. Ele desliza a pasta sobre a mesa.

                [CONFIANÇA COM DANIEL: +2 — ele vê em você alguém racional, não um inimigo]
                """);

        Escolha escolhaAuto5 = new Escolha("""
                [2] "Abra a pasta na minha frente e me mostre os carimbos de entrada antes de me dizer o que tem aí dentro."
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -1), ganhaProva)), """
                Daniel folheia os papéis sob seu olhar atento. A verificação dos carimbos revela
                que faltam relatórios vitais anexados na data do crime, provando que o arquivo
                já foi filtrado antes de chegar a você.

                [CONFIANÇA COM DANIEL: -1 — ele se sente vigiado]
                """);

        Escolha escolhaAuto6 = new Escolha("""
                [3] Arrancar a pasta vermelha da mão do detetive antes que ele feche a gaveta.
                """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -3))), """
                Você toma a pasta em um gesto rápido. Daniel recua, surpreso pela audácia,
                mas não tenta reavê-la; ele apenas cruza os braços, tenso com o rumo do confronto.

                [CONFIANÇA COM DANIEL: -3]
                """);

        // Teste de confiança. Máximo teoricamente possível até aqui:
        // 5 (inicial) + 2 (escolhaAuto1) + 2 (escolhaAuto4) = 9.
        Escolha escolhaAuto7 = new Escolha("""
                [1] "Ele cometeu erros, e eu também. Mas os fatos mostram que ele estava certo sobre o esquema. Vamos trabalhar juntos para expor quem fez isso."
                """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1), new Efeito(12), ganhaProva)), """
                Daniel assente com a cabeça e entrega o rádio de frequência restrita da polícia.

                * CAMINHO LIBERADO: Você ganha um aliado interno que fornecerá avisos
                  sobre operações oficiais e interceptações de chamadas em tempo real.

                (Obteve Item: Rádio de Frequência Restrita da Polícia [ID: 12])
                """);
        escolhaAuto7.defineRequisitoConfianca(daniel,9);//CONFIANCA_ALIADO_LEAL

        Escolha escolhaAuto8 = new Escolha("""
                [2] "Não tente inverter o jogo, Daniel. Você não ajudou ele na época e agora parece estar escondendo o resto dos documentos para se proteger."
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -2))), """
                Daniel recolhe os papéis restantes e guarda na gaveta trancada, encerrando a conversa.

                * CAMINHO LIBERADO: Você é forçado a agir na ilegalidade, precisando
                  invadir o arquivo morto da delegacia durante a madrugada para obter o restante das pistas.

                [CONFIANÇA COM DANIEL: -2]
                """);

        Escolha escolhaAuto9 = new Escolha("""
                [3] "Minha relação com meu irmão não é da sua conta. Só me diga o nome do suspeito principal antes que eu descubra por conta própria."
                """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -4))), """
                Daniel, intimidado pela sua postura, solta o nome do último empresário que
                esteve com seu irmão antes da morte.

                * CAMINHO LIBERADO: Rota de investigação rápida focada em interrogatórios
                  agressivos com suspeitos civis na área nobre da cidade.

                [CONFIANÇA COM DANIEL: -4 — ele passa a te ver como um risco]
                """);
        escolhaAuto9.defineRequisitoAtributo("VIOLÊNCIA", 10);

        Cena cenaOpcao1A = new Cena("ato2_opcao1a");
        cenaOpcao1A.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ATO II — O CONFRONTO METÓDICO (ROTA: RAZÃO / OPÇÃO 1A)
                ===============================================================================

                [CENA: Interior da Delegacia de Santa Aurora. Luzes fluorescentes piscando.]

                Você ignora as distrações da rua e atravessa a recepção da delegacia com o
                envelope de provas na mão. O cheiro de café velho e papel mofado preenche o
                ambiente. No fundo do corredor, o Detetive Daniel organiza uma pilha de
                pastas amarelas com expressão cansada.

                Ao notar sua presença, ele para o que está fazendo e ajusta a postura,
                tentando manter um tom de controle paternal.

                DANIEL:
                "Você demorou... Sinto muito pelo seu irmão. Eu sei que as coisas entre vocês
                não eram fáceis no final, mas nós estamos fazendo o possível para entender
                o que aconteceu."

                Você não responde de imediato. Em vez disso, coloca sobre a mesa as cópias
                do laudo preliminar com as inconsistências e rasuras expostas.

                > VOZ:
                > "Não se altere. Mantenha os fatos em ordem. Aparelho de análise, divergência
                > de horários e rasuras no documento. Faça-o admitir as falhas."
                """,
                escolhaAuto1,
                escolhaAuto2,
                escolhaAuto3
        ));

        cenaOpcao1A.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ESCOLHA 2 — A PRESSÃO TÁTICA
                ===============================================================================

                Daniel pega uma chave na gaveta e abre um arquivo de aço no canto da sala,
                revelando uma pasta vermelha etiquetada com o nome do seu irmão. Ele hesita
                em entregá-la.

                DANIEL:
                "Há informações aqui que podem colocar você em risco. O que seu irmão descobriu
                envolve gente grande em Santa Aurora."

                > VOZ:
                > "Proponha um acordo justo: colaboração mútua em troca de acesso. Ameaças vazias
                > só fecham as portas que você precisa abrir."

                > VOZ:
                > "Essa pasta vermelha pode ser uma isca. Verifique se as páginas internas
                > batem com os carimbos oficiais antes de aceitar o que ele diz."

                > VOZ:
                > "Pegue a pasta da mão dele antes que ele decida fechar o arquivo novamente."
                """,
                escolhaAuto4,
                escolhaAuto5,
                escolhaAuto6
        ));

        cenaOpcao1A.adicionaDialogo(new Dialogo(protagonista, """
                ===============================================================================
                ESCOLHA 3 — O DIÁLOGO COM DANIEL (TESTE DE CONFIANÇA)
                ===============================================================================

                Daniel se encosta na janela molhada pela chuva e encara você. O olhar dele
                demonstra um misto de cansaço e receio.

                DANIEL:
                "Seu irmão achava que podia derrubar a estrutura toda sozinho. Ele me procurou
                dias antes de morrer, cheio de acusações, mas recusei ajuda imediata porque
                precisava de provas firmes. Agora me diga... você veio a Santa Aurora buscar
                justiça ou apenas descarregar a culpa por ter abandonado ele?"

                A pergunta ecoa na sala. A forma como você responde vai definir o nível de
                CONFIANÇA entre você e o Detetive Daniel, alterando o acesso a recursos
                policiais e revelações no futuro.
                """,
                escolhaAuto7,
                escolhaAuto8,
                escolhaAuto9));

        return cenaOpcao1A;
    }

    public Cena criarAtoIIRota2A(Personagem narrador, Protagonista protagonista) {
        Escolha escolhaAto2_2A_1 = new Escolha("""
                [1] Analisar os rastros no chão e o mecanismo do alçapão para identificar se há algum fio esticado ou sistema de alarme improvisado.
                """, aumentaRazao, """
                Você ilumina o chão com a tela do celular e nota um fio de nylon esticado na
                dobradiça da porta metálica, ligado a uma lata cheia de pregos pendurada no teto.
                Você desarma o alarme improvisado com cuidado e desce a escada com a certeza
                de que ninguém no andar de baixo ouviu sua chegada.
                """);

        Escolha escolhaAto2_2A_2 = new Escolha("""
                [2] Descer em silêncio absoluto pela escada lateral de emergência, usando a escuridão como cobertura para tomar o piso inferior de surpresa.
                """, aumentaParanioa, """
                Você ignora o alçapão principal e contorna pela estrutura de ferro até a escada
                de incêndio lateral. Nas sombras do mezanino, você percebe que a lâmpada do
                subsolo pisca devagar. Da sua posição elevada, você consegue ver uma mesa de
                trabalho sem ser notado por qualquer câmera ou vigilante.
                """);

        Escolha escolhaAto2_2A_3 = new Escolha("""
                [3] Puxar o alçapão com força de uma vez e descer rápido, pronto para reagir se houver alguém te esperando lá embaixo.
                """, aumentaViolencia, """
                Você puxa a tampa de ferro com estrondo, ignorando o alarme de latas que desaba
                no chão. O barulho ecoa por todo o galpão deserto. Você desce os degraus de três
                em três, impondo sua presença no espaço antes mesmo que qualquer ameaça oculta
                possa se organizar.
                """);

        Escolha escolhaAto2_2A_4 = new Escolha("""
                [1] Tentar abrir o cadeado do diário usando datas marcantes do passado de vocês.
                """, aumentaRazao, """
                Você tenta uma série de datas importantes para você e seu irmão, mas nenhuma
                funciona. Você guarda o diário fechado no casaco, frustrado — vai precisar de
                outro jeito de abri-lo mais tarde.
                """);

        Escolha escolhaAto2_2A_5 = new Escolha("""
                [2] Pressionar o botão de reprodução do gravador de voz para ouvir a última gravação do seu irmão.
                """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(11), ganhaProva)), """
                A fita chia antes de revelar a voz grave do seu irmão: "Se alguém está
                ouvindo isso, o Daniel mentiu sobre o laudo. Não confie na polícia. Eles estão
                usando a névoa para encobrir o que tiram do subsolo da cidade..." A gravação
                corta bruscamente com o som de uma porta sendo arrombada.

                (Obteve Item: Gravação de Gabriel [ID: 11])
                """);

        Escolha escolhaAto2_2A_6 = new Escolha("""
                [3] Usar um pé de cabra encostado na mesa para quebrar o fecho da gaveta e do diário de uma vez.
                """, new ArrayList<>(List.of(aumentaRazao, new Efeito(10), ganhaProva)), """
                O metal cede sob a alavanca. Além dos documentos rasgados no impacto, você
                encontra um compartimento secreto na gaveta contendo o diário de seu irmão com o
                símbolo do cartório central.

                (Obteve Item: Diário de Gabriel [ID: 10])
                """);

        Escolha escolhaAto2_2A_7 = new Escolha("""
                [1] Apagar as luzes e escapar pelo duto de ventilação lateral com os documentos.
                """, aumentaRazao, """
                Você desliza pelo duto de ar estreito em silêncio enquanto os invasores vasculham
                a sala vazia abaixo. Você sai nos fundos da fábrica com o diário e o mapa intactos,
                pronto para usar as informações de forma calculada.
                """);

        Escolha escolhaAto2_2A_8 = new Escolha("""
                [2] Esconder-se atrás dos arquivadores para identificar os rostos dos invasores.
                """, aumentaParanioa, """
                Da penumbra, você reconhece os distintivos ocultos sob os casacos dos invasores:
                são homens da própria guarda municipal. Você confirma que a polícia está ativamente
                limpando os rastros deixados pelo seu irmão.
                """);

        Escolha escolhaAto2_2A_9 = new Escolha("""
                [3] Derrubar a prateleira pesada contra a escada para desestabilizar os sujeitos e confrontá-los.
                """, aumentaViolencia, """
                A estrutura de ferro desaba com um estrondo ensurdecedor, prendendo um dos
                invasores sob os destroços. O outro recua assustado pela sua reação implacável,
                deixando o caminho da saída totalmente livre para você.
                """);

        Cena atoIIRota2A = new Cena("ato2_rota2a");

        atoIIRota2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO II — O ESCONDERIJO SUBTERRÂNEO (ROTA: 2A)
        ===============================================================================

        [CENA: Periferia de Santa Aurora / Antiga Fábrica Desativada]

        Você ignora a delegacia e segue as orientações de Jonas. A chuva aperta
        enquanto você caminha até o distrito industrial abandonado. A estrutura de
        ferro e tijolos da velha fábrica ergue-se contra o céu escuro como uma carcaça
        esquecida.

        Você lembra da voz de Jonas vacilando: "Ele... estava na fábrica na semana
        passada. Dizia que achou registros que não batiam."

        Ao contornar a entrada principal trancada por correntes ferrugentas, você acha
        uma porta de serviço entreaberta. Ao entrar, o cheiro de mofo e óleo queimado
        toma conta do ar. O chão de concreto está coberto de poeira, mas há rastros
        recentes de pegadas levando a uma alçapão metálico no piso inferior.

        > VOZ DA CONSPIRAÇÃO:
        > "Seu irmão não viria aqui sem deixar uma rota de fuga ou uma armadilha para
        > curiosos. Olhe ao redor antes de descer."
        """,
                escolhaAto2_2A_1,
                escolhaAto2_2A_2,
                escolhaAto2_2A_3
        ));

        atoIIRota2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ESCOLHA 2 — O ACHADO NO ESCONDERIJO
        ===============================================================================

        No fundo do subsolo, entre velhos arquivadores e caixotes de madeira, você
        encontra a mesa de trabalho do seu irmão. Sobre ela, há um mapa de Santa Aurora
        com vários pontos marcados em vermelho e um diário de anotações trancado por
        um cadeado numérico de três dígitos. Ao lado, o gravador de voz dele ainda
        está com a luz de prontidão acesa.

        > VOZ DA RAZÃO:
        > "O cadeado do diário usa três dígitos. A data em que você foi embora da cidade
        > pode ser a senha que ele escolheu."

        > VOZ DA CONSPIRAÇÃO:
        > "O gravador de voz está pronto para reproduzir. Pode haver uma mensagem de
        > aviso deixada especialmente para quem encontrasse este lugar."

        > VOZ DA VIOLÊNCIA:
        > "Não perca tempo tentando adivinhar senhas. Force a trava do diário com a
        > ferramenta na bancada e pegue o que precisa."
        """,
                escolhaAto2_2A_4,
                escolhaAto2_2A_5,
                escolhaAto2_2A_6
        ));

        atoIIRota2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ESCOLHA 3 — A AMEAÇA NO GALPÃO
        ===============================================================================

        Enquanto você examina os achados, o som pesados de passos no piso de madeira
        acima do subsolo faz poeira cair do teto. Duas sombras se posicionam no topo
        da escada. Uma voz desconhecida ecoa no galpão: "Sabíamos que alguém viria
        atrás das coisas dele. Tranquem as saídas."

        > VOZ DA RAZÃO:
        > "Apague a luz da bancada. Use a tubulação de ar à esquerda para sair sem ser
        > visto e leve as provas com você."

        > VOZ DA CONSPIRAÇÃO:
        > "Eles não esperavam que você já tivesse descoberto o diário. Mantenha-se nas
        > sombras e observe quem são eles antes de tomar qualquer atitude."

        > VOZ DA VIOLÊNCIA:
        > "Eles estão no alto da escada, mas você tem o elemento surpresa na escuridão.
        > Derrube a prateleira de ferro sobre a passagem e avance para cima deles."
        """,
                escolhaAto2_2A_7,
                escolhaAto2_2A_8,
                escolhaAto2_2A_9
        ));

        atoIIRota2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        RESOLUÇÃO DO SEGMENTO 2A — O SEGREDO DO SUBSOLO
        ===============================================================================

        O silêncio volta a tomar conta da fábrica abandonada enquanto a névoa de
        Santa Aurora entra pelas frestas das janelas quebradas.

        Com as pistas resgatadas do esconderijo do seu irmão, a névoa que encobria a
        morte dele começa a se dissipar, revelando uma verdade incômoda: a sequência
        de homicídios nunca foi obra de um criminoso comum, mas sim uma cortina de
        fumaça operada por figuras poderosas da própria cidade.

        Seu irmão descobriu o esquema e pagou com a vida por tentar expor a verdade.
        Agora, os registros estão com você.

        ===============================================================================
        ESTADO ATUAL DA INVESTIGAÇÃO
        ==============================================================================

        * **AMEAÇA IDENTIFICADA:** Elementos da segurança pública estão ativamente
          apagando os rastros do caso.
        * **PRÓXIMO PASSO:** Usar essas informações para encurralar quem ordenou o crime.
        ===============================================================================
        """));

        return atoIIRota2A;
    }

    public Cena criarAtoIIRota3A(Personagem narrador, Protagonista protagonista, NPC daniel) {
        Escolha escolhaAuto1 = new Escolha("""
            [1] Pressionar o homem contra a parede de tijolos e exigir o nome de quem o contratou:
                "Quem te pagou para queimar os relatórios no beco? Fala agora!"
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -2))), """
            O homem treme e murmura um apelido pouco antes de a porta de serviço se
            abrir bruscamente. Daniel sai armado, vê a cena e manda você soltar o suspeito
            imediatamente. O sujeito é levado algemado para a cela, mas Daniel fica
            extremamente irritado com a sua violência em propriedade policial.

            [CONFIANÇA COM DANIEL: -2]
            """);

        // ANTES: new AdicionaItem("Cartão de Acesso da Prefeitura") — agora com ID de catálogo
        Escolha escolhaAuto2 = new Escolha("""
            [2] Revistar os bolsos do sujeito rapidamente em busca de carteira, celular ou chaves antes que a patrulha perceba a movimentação.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(15), ganhaProva)), """
            Sua agilidade permite que você encontre um cartão de acesso magnético com um
            símbolo timbrado da Prefeitura no bolso da jaqueta dele. Você guarda a prova
            no casaco um segundo antes de a patrulha dobrar a esquina.

            (Obteve Item: Cartão de Acesso da Prefeitura [ID: 15])
            """);

        // ANTES: new AdicionaItem("Pista: Canal do Gabinete")
        Escolha escolhaAuto3 = new Escolha("""
            [3] Arrastar o sujeito para a penumbra do portão dos fundos para evitar a luz da rua e interrogar em silêncio.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(16), ganhaProva)), """
            Escondido no ponto cego das câmeras de segurança, você faz o sujeito confessar
            que recebia ordens por mensagens anônimas de um número com o código interno do
            gabinete municipal. Você obtém a informação sem expor sua posição para a polícia.

            (Obteve Pista: Canal do Gabinete [ID: 16])
            """);

        Escolha escolhaAuto4 = new Escolha("""
            [4] Largar o homem no chão e chamar o Detetive Daniel para assumir a custódia do suspeito de forma oficial.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2))), """
            Daniel atende ao seu chamado de rádio e rende o suspeito no beco. Impressionado
            pela captura limpa e sem uso de força desmedida, ele agradece a ajuda e
            concorda em realizar o interrogatório em conjunto na sala oficial.

            [CONFIANÇA COM DANIEL: +2]
            """);

        // ANTES: new AdicionaItem("Pista: Vínculo com Vice-Prefeito")
        Escolha escolhaAuto5 = new Escolha("""
            [1] Apresentar as fotos do local do crime e apontar as falhas do plano dele:
                "O seu contratante deixou a placa do seu carro no sistema. Você foi descartado."
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1), new Efeito(17), ganhaProva)), """
            O homem olha os papéis, empalidece e cede à lógica. Ele confessa que o
            pagamento veio de uma conta ligada ao gabinete do vice-prefeito de
            Santa Aurora para apagar as provas daquela noite.

            (Obteve Pista: Vínculo com Vice-Prefeito [ID: 17])
            [CONFIANÇA COM DANIEL: +1]
            """);

        // CORRIGIDO: era "new Efeito("Testemunho Verbal Não-Assinado")" — Efeito(String)
        // não existe no motor; provavelmente não compilava. Agora segue o padrão Efeito(int).
        Escolha escolhaAuto6 = new Escolha("""
            [2] Blefar dizendo que o celular apreendido já revelou todas as conversas apagadas:
                "Nós já resgatamos as mensagens do servidor. Se não confirmar agora, leva a culpa sozinho."
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(18))), """
            O suspeito hesita, desconfiado do blefe, mas treme ao ouvir o nome da prefeitura.
            Ele confirma o envolvimento da administração, mas se recusa a assinar o depoimento.

            (Obteve Pista: Testemunho Verbal Não-Assinado [ID: 18])
            [CONFIANÇA COM DANIEL: NEUTRA]
            """);

        // ANTES: new AdicionaItem("Nome do Assessor")
        Escolha escolhaAuto7 = new Escolha("""
            [3] Bater as mãos com força na mesa de metal e encarar o homem a poucos centímetros:
                "Você tem três segundos para me dar esse nome antes que eu perca a paciência!"
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -1), new Efeito(19))), """
            O homem se encolhe na cadeira e grita o nome do assessor direto do vice-prefeito.
            Daniel intervém imediatamente, segurando seu ombro e afastando você da mesa
            para evitar uma agressão física.

            (Obteve Pista: Nome do Assessor [ID: 19])
            [CONFIANÇA COM DANIEL: -1]
            """);

        // ANTES: new AdicionaItem("Ponto de Encontro do Transfuga")
        Escolha escolhaAuto8 = new Escolha("""
            [4] Olhar para Daniel e deixar que o detetive faça a pergunta decisiva sobre a ordem:
                "Daniel, mostre a ele o que acontece com quem cobre os crimes do alto escalão."
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2), new Efeito(20), ganhaProva)), """
            Daniel assume o comando com calma profissional. O suspeito, sentindo a
            pressão da autoridade policial oficial, entrega o local e o horário exatos
            onde entregaria o restante do material queimado.

            (Obteve Pista: Ponto de Encontro do Transfuga [ID: 20])
            [CONFIANÇA COM DANIEL: +2]
            """);

        Escolha escolhaAuto9 = new Escolha("""
            [1] "Vamos montar uma operação oficial com o que temos e grampear o gabinete do vice-prefeito."
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2))), """
            Daniel acena positivamente com a cabeça. Ele garante que usará sua influência
            para conseguir um mandado de busca discreto sem acionar os alertas da prefeitura.

            [CONFIANÇA COM DANIEL: +2 | ROTA ATIVADA: OPERAÇÃO CONJUNTA]
            """);

        Escolha escolhaAuto10 = new Escolha("""
            [2] "Vou até a prefeitura por conta própria antes que eles saibam que o suspeito falou."
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -1))), """
            Daniel joga a bituca do cigarro no chão e suspira, preocupado com sua imprudência.
            Ele avisa que não poderá te cobrir se você for pego invadindo prédios públicos.

            [CONFIANÇA COM DANIEL: -1 | ROTA ATIVADA: INFILTRAÇÃO SOLO]
            """);

        Escolha escolhaAuto11 = new Escolha("""
            [3] "Vou caçar o assessor do vice-prefeito hoje mesmo e fazer ele pagar pelo meu irmão."
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -3))), """
            Daniel põe a mão no coldre e avisa em tom ameaçador que não hesitará em te prender
            se você tentar fazer justiça com as próprias mãos em Santa Aurora.

            [CONFIANÇA COM DANIEL: -3 | ROTA ATIVADA: VIGILANTE PERSEGUIDO]
            """);

        // CORRIGIDO: era "new Efeito(13)" — colidia com "Pasta Original do Laudo" (Ato III,
        // Perspectiva 1A). Chave do Arquivo Morto passa a usar o ID 14, já reservado
        // pra ela no catálogo revisado.
        Escolha escolhaAuto12 = new Escolha("""
            [4] "Me passe o acesso ao arquivo morto do caso e vamos dividir as tarefas de investigação."
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 3), new Efeito(14), ganhaProva)), """
            Daniel retira uma cópia da chave do arquivo do bolso e entrega na sua mão.
            Ele demonstra confiança total na sua capacidade de conduzir o caso ao seu lado.

            (Obteve Item: Chave do Arquivo Morto [ID: 14])
            [CONFIANÇA COM DANIEL: +3 | ROTA ATIVADA: PARCERIA TOTAL]
            """);
        Cena cenaRota3A = new Cena("ato2_rota3a");

        cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                ATO II — O CONFRONTO NO BECO (ROTA: 3A)
                ===============================================================================

                [CENA 1: Beco Lateral da Delegacia de Santa Aurora / Noite Chuvosa / 01h15]

                A chuva cai em pancadas pesadas, transformando o beco lateral da delegacia em
                um corredor de lama e poças refletindo o neon piscante de uma farmácia distante.
                O vapor da água fria atingindo o asfalto quente cria uma névoa rasteira.

                Você encurrala o homem no canto cego do beco, bem ao lado dos contêineres de lixo
                da delegacia, antes que ele consiga acender o isqueiro sobre a pasta com papéis
                parcialmente queimados. O cheiro de papel incinerado e gasolina barata impregnam o ar.

                Ele está encharcado, respirando de forma afobada, com os olhos arregalados
                procurando desesperadamente uma rota de fuga entre as paredes altas de tijolos.

                SUSPEITO:
                "Calma aí, cara! Eu só recebi um dinheiro pra sumir com esse entulho da lixeira!
                Não sei de nada sobre o seu irmão, juro por Deus!"

                > VOZ DA VIOLÊNCIA:
                > "Ele está mentindo para ganhar tempo. Faça ele falar antes que a patrulha
                > faça a ronda dos fundos."

                > VOZ DA RAZÃO:
                > "O envelope que ele tenta esconder tem a marca d'água oficial do Cartório.
                > Se o fogo destruir a numeração, perdemos a conexão com a autópsia."

                > VOZ DA CONSPIRAÇÃO:
                > "Repare nas câmeras do muro: a lâmpada do poste da delegacia foi quebrada de
                > propósito. Alguém facilitou a entrada dele aqui."
                """,
                escolhaAuto1,
                escolhaAuto2,
                escolhaAuto3,
                escolhaAuto4
        ));

        cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                PONTE NARRATIVA I — DO BECO À SALA DE INTERROGATÓRIO
                ===============================================================================

                O suspeito é arrastado para dentro da delegacia sob o olhar curioso dos policiais
                de plantão. A água da chuva escorre de suas roupas, deixando um rastro escuro no
                piso de linóleo gasto do corredor.

                Daniel empurra a porta de carvalho pesado da Sala de Interrogatório 02.

                ===============================================================================
                CENA 2: Sala de Interrogatório 02 / 01h45
                ===============================================================================

                SUSPEITO:
                "Eu já disse tudo o que sei lá fora... Eu só fui pago para queimar aquela pasta!
                Se eu falar o nome de quem mandou o Pix, eu tô morto antes de pisar no pátio do presídio!"

                > VOZ DA RAZÃO:
                > "Ele está apavorado, mas a ganância o fez guardar o comprovante ou o registro no
                > celular. Apresentar os dados em ordem fará ele quebrar."

                > VOZ DA CONSPIRAÇÃO:
                > "Repare no relógio dele: é um modelo caro demais para um queimador de arquivo.
                > Esse cara não é um zé-ninguém da rua, é um funcionário comissionado."

                > VOZ DA VIOLÊNCIA:
                > "Pule a burocracia. Encoste a cadeira dele na parede e mostre que a polícia é o
                > menor dos problemas dele nesta noite."
                """,
                escolhaAuto5,
                escolhaAuto6,
                escolhaAuto7,
                escolhaAuto8
        ));

        cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                ===============================================================================
                PONTE NARRATIVA II — O CONFRONTO DE PLANOS NO CORREDOR
                ===============================================================================

                Daniel tranca a porta da Sala de Interrogatório e entrega a chave para o guarda de plantão.

                Vocês dois caminham em silêncio pelo corredor até a saída dos fundos da delegacia.

                ===============================================================================
                CENA 3: Saída dos Fundos da Delegacia / 02h15
                ===============================================================================

                DANIEL:
                "As peças estão se encaixando... e a imagem que está se formando é pior do que
                eu imaginava. O que seu irmão descobriu não é briga de gangue. Vai direto ao topo
                da prefeitura. Agora me diga, sem rodeios: qual é o seu próximo passo?"

                > VOZ DA CONSPIRAÇÃO:
                > "Decida o quanto vai revelar a ele. O próximo movimento define se você terá um
                > parceiro na lei ou se agirá como um alvo isolado nas ruas."
                """,
                escolhaAuto9,
                escolhaAuto10,
                escolhaAuto11,
                escolhaAuto12
        ));

        return cenaRota3A;
    }

    public Cena criarAtoIIIPerspectiva1A(Personagem narrador, Protagonista protagonista, NPC helena, NPC homemArmado) {

        Escolha escolhaAto3_1 = new Escolha("""
        [1] Usar a parceria com Daniel para se abrigar sob uma marquise e interceptar o rádio de frequência restrita da Perícia.
        """, aumentaParanioa, """
        Você se abriga sob o toldo de uma loja fechada. Liga o rádio no volume mínimo.
        O chiado dá lugar à voz do operador: "Central, transferência das amostras do caso
        Gabriel para o Cartório Central agendada para as 02h15." Você obtém o horário exato.
        """);
        escolhaAto3_1.defineRequisitoItem(12);
        Escolha escolhaAto3_2 = new Escolha("""
        [2] Esperar a troca de guarda no estacionamento dos fundos e invadir o Arquivo Morto da delegacia pela janela basculante.
        """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(13), ganhaProva)), """
        Você se esconde atrás da caçamba até as 02h00, quando o plantão troca. Deslizando
        pela janela do subsolo, você alcança as gavetas e resgata a pasta original do laudo
        sem as rasuras oficiais.

        (Obteve Item: Pasta Original do Laudo [ID: 13])
        """);

        Escolha escolhaAto3_3 = new Escolha("""
        [3] Caminhar até a Área Nobre para vigiar o endereço do empresário citado por Daniel.
        """, aumentaViolencia, """
        Você corta caminho pelas alamedas arborizadas do bairro nobre. Da calçada,
        observa a mansão: as luzes do escritório estão acesas e um homem tritura documentos
        na janela.
        """);

        Escolha escolhaAto3_5 = new Escolha("""
        [1] Colocar os documentos na mesa e propor um cruzamento de dados com o arquivo de matérias censuradas do jornal.
        """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 2), ganhaProva)), """
        Helena puxa as pastas de arquivos mortos do jornal. Cruzando as datas dos
        obituários com as desapropriações de imóveis, surge um padrão claro: todas
        as vítimas eram proprietárias de terrenos no traçado da nova rodovia municipal.
        """);
        escolhaAto3_5.defineRequisitoItem(13);

        Escolha escolhaAto3_6 = new Escolha("""
        [2] Mostrar apenas trechos do laudo e questionar o que ela realmente sabia sobre a relação entre Gabriel e as autoridades.
        """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 1))), """
        Helena fecha as persianas da sala, respirando fundo. Ela abre uma gaveta
        falsa e confirma que Gabriel tentou expor a fraude dos terrenos antes de ser silenciado.
        """);

        Escolha escolhaAto3_7 = new Escolha("""
        [3] Bater as provas na mesa e exigir que ela use a impressora de emergência para expor o laudo imediatamente.
        """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -1))), """
        Helena recua diante da sua postura, incomodada com a pressão, mas cede à gravidade
        dos fatos e entrega o mapa do acesso de serviço para a gráfica e o setor de imprensa.
        """);

        Escolha escolhaAto3_8 = new Escolha("""
        [1] Virar as costas e tentar fugir correndo pela escada de incêndio ou pela travessa lateral.
        """, new Efeito(-1), """
        "A NÉVOA ENGOLIU MAIS UMA VÍTIMA")
        Você se vira abruptamente e tenta correr. O som abafado dos disparos com
        silenciador atinge suas costas em cheio. Você desaba no paralelepípedo molhado
        e os papéis são arrancados do seu casaco. O caso termina no fundo do beco.
        """);

        Escolha escolhaAto3_9 = new Escolha("""
        [2] Recuar devagar para a sombra dos contêineres para captar as ordens passadas pelo rádio dos homens.
        """, aumentaParanioa, """
        Você recua para a mancha de sombra. O rádio de um dos homens chia: "O Gabinete
        do Prefeito quer o arquivo destruído antes do amanhecer!" Distraídos pelo rádio,
        você desliza pela fresta do portão de serviço.
        """);

        Escolha escolhaAto3_10 = new Escolha("""
        [3] Agarrar uma barra de ferro encostada na parede e apagar a iluminação do beco, atacando o agressor na penumbra.
        """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(21))), """
        Um golpe preciso esmaga a luminária, jogando o beco no breu total. No caos dos
        tiros cegos, você usa a névoa para desarmar o primeiro homem com brutalidade e
        escapar pela travessa.

        (Você agora está marcado: Procurado por Agressão [ID: 21])
        """);
        Cena atoIII1A = new Cena("ato3_perspectiva1a");

        atoIII1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO III — AS CONTRADIÇÕES (PERSPECTIVA 1A: O CONFRONTO METÓDICO)
===============================================================================

[CENA 1: Corredor da Delegacia / Saída do Gabinete de Daniel / 01h45]

O clique da tranca da porta de Daniel ecoa no corredor silencioso. O ar dentro da
delegacia é denso, impregnado com cheiro de café requentado e o mofo de papéis velhos.

Você caminha a passos lentos, sentindo o peso do envelope sob o casaco molhado.

Você pisa na calçada. A tempestade em Santa Aurora atingiu o pico. O vento
frio arrasta a névoa pelas sarjetas.

> VOZ DA RAZÃO:
> "Daniel abriu uma brecha, mas palavras e papéis rasurados são frágeis. Se você
> não cruzar esse laudo com registros oficiais nas próximas horas, a versão deles se fechará."

> VOZ DA CONSPIRAÇÃO:
> "Aquele olhar dos policiais no corredor não foi casual. Eles viram o envelope.
> O rádio da delegacia já deve estar notificando quem controla as sombras desta cidade."

> VOZ DA VIOLÊNCIA:
> "Deixe que venham. Você já tem o suficiente para saber que o laudo é uma farsa.
> A única linguagem que essa cidade entende é o confronto."
""",
                escolhaAto3_1,
                escolhaAto3_2,
                escolhaAto3_3
        ));

        atoIII1A.adicionaDialogo(new Dialogo(helena, """
===============================================================================
PONTE NARRATIVA I — O CAMINHO ATÉ A REDAÇÃO
===============================================================================

Você cruza a Praça da Matriz e sobe até a redação de 'A Voz de Santa Aurora',
encontrando Helena com uma xícara de café e caneta na mão.

===============================================================================
CENA 2: Redação do Jornal "A Voz de Santa Aurora" / 02h30
===============================================================================

HELENA:
"Você tem a cara de quem acabou de mexer no ninho de marimbondos da delegacia.
O Daniel te deu as respostas que você queria ou só te empurrou mais conversa fiada?"
""",
                escolhaAto3_5,
                escolhaAto3_6,
                escolhaAto3_7
        ));

        atoIII1A.adicionaDialogo(new Dialogo(homemArmado, """
===============================================================================
PONTE NARRATIVA II — A EMBOSCADA NA SAÍDA
===============================================================================

Ao sair pela porta dos fundos para o beco estreito, o ronco grave de um motor V8
ecoa. Um SUV preto sem placas fecha a saída. Dois homens desembarcam, sacando pistolas
com silenciadores.

===============================================================================
CENA 3: O Beco dos Fundos do Jornal / 03h20
===============================================================================

HOMEM ARMADO:
"Você deveria ter pegado o próximo ônibus de volta, garoto. Entrega a pasta
e nem pense em correr."
""",
                escolhaAto3_8,
                escolhaAto3_9,
                escolhaAto3_10
        ));

        atoIII1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO III — A CAMINHO DO ATO IV
===============================================================================

[CENA: Mirante do Parque da Cidade / Visão Geral de Santa Aurora / 04h00]

A garoa cessa. De cima do mirante, você observa o Prédio da Prefeitura Municipal — com o
último andar inteiramente iluminado em plena madrugada.

O mistério da morte de Gabriel foi esclarecido: as execuções visavam desapropriar
terrenos para o projeto da nova rodovia. Não há mais espaço para hesitações.
"""));

        return atoIII1A;
    }


    public Cena criarAtoIIIPerspectiva1B(Personagem narrador, Protagonista protagonista, NPC helena, NPC agenteSeguranca) {
        // ANTES: new AdicionaItem("Fita de Áudio Confidencial do Irmão")
        Escolha escolhaAto3_1B_1 = new Escolha("""
            [1] Usar o termo/lista obtido para ir à Central de Custódia e resgatar o gravador de voz que ficou fora do inventário.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(22), ganhaProva)), """
            Você se apresenta no balcão de evidências com a requisição parcial. O guarda
            do plantão, sem suspeitar da manobra, entrega a caixa de pertences secundários.
            Dentro, sob um maço de chaves, você recupera a fita microcassete do seu irmão.

            (Obteve Item: Fita de Áudio Confidencial do Irmão [ID: 22])
            """);

        // ANTES: new AdicionaItem("Diários do Sobrado de Gabriel")
        Escolha escolhaAto3_1B_2 = new Escolha("""
            [2] Desviar do caminho principal e se esgueirar pelos becos até a casa de Gabriel para chegar antes da patrulha de vigilância de Daniel.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(23), ganhaProva)), """
            Movendo-se na sombra dos quarteirões, você observa a viatura de Daniel parar
            na frente da casa. Deslizando pelo telhado vizinho, você entra pela claraboia,
            recupera os diários ocultos sob o assoalho e sai sem acionar os policiais.

            (Obteve Item: Diários do Sobrado de Gabriel [ID: 23])
            """);

        // ANTES: new AdicionaItem("Ordem de Busca e Apreensão Paralela")
        Escolha escolhaAto3_1B_3 = new Escolha("""
            [3] Rastrear o carro descaracterizado que Daniel usou para mandar a escolta e interceptar os documentos na marra.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(24))), """
            Você espera o veículo desacelerar no cruzamento da Praça Central. Numa ação
            rápida, você fura o pneu traseiro com um estilete na penumbra da chuva e
            arranca a pasta de ordens do banco traseiro enquanto os guardas checam o dano.

            (Obteve Item: Ordem de Busca e Apreensão Paralela [ID: 24])
            """);

        // ANTES: new AdicionaItem("Mapeamento da Conta Fantasma")
        Escolha escolhaAto3_1B_4 = new Escolha("""
            [1] Reproduzir apenas um trecho da gravação/diário e pedir que ela identifique as vozes e os codinomes citados pela alta cúpula.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 2), new Efeito(25), ganhaProva)), """
            Helena reconhece a voz no áudio imediatamente: é o Secretário de Governo
            autorizando o repasse de verbas para a empresa fantasma. Ela entrega o mapa
            das contas bancárias que guardava em segredo.

            (Obteve Item: Mapeamento da Conta Fantasma [ID: 25])
            """);

        // ANTES: new AdicionaItem("Dossiê de Ocultação de Cadáveres")
        Escolha escolhaAto3_1B_5 = new Escolha("""
            [2] Colocar todo o material sobre a mesa e propor uma investigação assinada em conjunto para desmantelar a farsa de Daniel e da Prefeitura.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 2), new Efeito(26), ganhaProva)), """
            Helena abre o acervo confidencial do jornal. Juntos, vocês montam o organograma
            que prova como Daniel abafou os outros quatro homicídios anteriores ao de Gabriel.

            (Obteve Item: Dossiê de Ocultação de Cadáveres [ID: 26])
            """);

        // ANTES: new AdicionaItem("Panfletos de Denúncia Operacional")
        Escolha escolhaAto3_1B_6 = new Escolha("""
            [3] Exigir que ela use o mimeógrafo industrial para produzir centenas de cópias dos documentos e jogá-los no pátio da delegacia.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -1), new Efeito(27))), """
            Helena recua diante da exigência abrupta, mas cede ao ver a gravidade das provas:
            ela aciona a máquina de panfletos para distribuição no turno da manhã.

            (Obteve Item: Panfletos de Denúncia Operacional [ID: 27])
            """);

        // Game Over original — mantido como a via "arriscada" de fuga
        Escolha escolhaAto3_1B_7 = new Escolha("""
            [1] Virar as costas e tentar correr desesperadamente a pé em direção à avenida aberta.
            """, new Efeito(-1), """
                A MÁSCARA CAIU E A VERDADE FOI APAGADA")
            Você se vira e dispara em corrida pela calçada molhada. O som abafado de dois
            disparos com silenciador corta a névoa. As balas perfuram suas costas e coxa.
            Você desaba sobre o paralelepípedo molhado, incapaz de se mover. Os agentes
            se aproximam em silêncio, recolhem o dossiê do seu casaco e jogam seu corpo
            no porta-malas do carro sem placas. Seu jogo duplo termina no escuro.""");

        Escolha escolhaAto3_1B_7_sucesso = new Escolha("""
            [1] Virar as costas e correr — mas você já mapeou essa rua andando pelas sombras a noite toda.
            """, aumentaParanioa, """
            Você sabia que essa avenida tinha essa saída lateral antes mesmo de precisar
            dela. Os disparos erram no nevoeiro enquanto você dobra a esquina e desaparece
            na correnteza de gente saindo de um bar ainda aberto.
            """);
        escolhaAto3_1B_7_sucesso.defineRequisitoAtributo("PARANOIA", 12); // ajuste o valor conforme o teto real acumulável até este ponto


        Escolha escolhaAto3_1B_8 = new Escolha("""
            [2] Render-se simuladamente, dar três passos para trás e jogar a pasta na sombra para recuperar a fita de áudio/gravação e escapar pela tubulação de escoamento.
            """, new ArrayList<>(List.of(aumentaParanioa, aumentaRazao, new Efeito(28))), """
            Você finge obedecer, soltando uma pasta fútil na iluminação principal. Enquanto
            os agentes se aproximam distraídos para recolher a isca, você desliza pela
            grade da tubulação de serviço, escapando pelo canal subterrâneo com as provas reais.

            (Obteve Item: Rota das Galerias do Centro [ID: 28])
            """);

        // ANTES: new AdicionaItem("Pistola Tática com Silenciador") + AdicionaStatus("Procurado por Agressão")
        // Status reaproveita o ID 21 já cadastrado na Perspectiva 1A — mesmo estado, mesmo ID.
        Escolha escolhaAto3_1B_9 = new Escolha("""
            [3] Usar a lixeira de metal para quebrar a iluminação da lanterna, fechar a distância e subjugar o primeiro agente na penumbra da névoa.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(29), new Efeito(21))), """
            Você tomba a lixeira com força contra as pernas do primeiro homem. Na confusão
            dos tiros cegos sob o nevoeiro, você desarma o agressor com um golpe de joelho,
            toma sua pistola silenciada e foge pela travessa lateral.

            (Obteve Item: Pistola Tática com Silenciador [ID: 29])
            (Você agora está marcado: Procurado por Agressão [ID: 21])
            """);

        Cena atoIII1B = new Cena("ato3_perspectiva1b");

        atoIII1B.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO III — AS FISSURAS NA MÁSCARA (PERSPECTIVA 1B: O JOGO DUPLO)
        ===============================================================================

        [CENA 1: Corredor da Delegacia / Saída da Sala de Daniel / 01h50]

        A porta da sala de Daniel fecha atrás de você com um clique seco.

        > VOZ DA CONSPIRAÇÃO:
        > "Daniel comprou sua cena ou está apenas dando corda para ver onde você vai."

        > VOZ DA RAZÃO:
        > "Sua vantagem atual é a assimetria de informação. Use esse ponto cego antes que a guarda troque."

        > VOZ DA VIOLÊNCIA:
        > "Chega de teatro. Se uma viatura tentar cercar a casa do Gabriel, corte os pneus ou force a entrada."
        """,
                escolhaAto3_1B_1,
                escolhaAto3_1B_2,
                escolhaAto3_1B_3
        ));

        atoIII1B.adicionaDialogo(new Dialogo(helena, """
        ===============================================================================
        CENA 2: Redação de "A Voz de Santa Aurora" / 03h00
        ===============================================================================

        HELENA:
        "Você tem a cara de quem acabou de jogar um jogo de xadrez no escuro com o Daniel.
        O que você trouxe daí?"
        """,
                escolhaAto3_1B_4,
                escolhaAto3_1B_5,
                escolhaAto3_1B_6
        ));

        atoIII1B.adicionaDialogo(new Dialogo(agenteSeguranca, """
        ===============================================================================
        CENA 3: A Viela dos Sapateiros / 04h00
        ===============================================================================

        AGENTE DA SEGURANÇA:
        "Chega de jogo duplo, garoto. O Detetive Daniel manda lembranças. Coloque a pasta
        no chão, dê três passos para trás e mantenha as mãos onde a gente possa ver."
        """,
                escolhaAto3_1B_7,
                escolhaAto3_1B_8,
                escolhaAto3_1B_9
        ));

        atoIII1B.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        PONTE NARRATIVA FINAL DO ATO III (PERSPECTIVA 1B) — O CONFRONTO IMINENTE
        ===============================================================================

        [CENA: Mirante da Caixa D'Água / Visão Panorâmica de Santa Aurora / 04h45]

        As máscaras caíram: Daniel não é apenas um policial omisso, mas o braço
        operacional do Gabinete do Prefeito encarregado de limpar os rastros de
        sangue deixados pelas desapropriações de terras.
        """));

        return atoIII1B;
    }

    public Cena criarAtoIIIPerspectiva2A(Personagem narrador, Protagonista protagonista, NPC helena, NPC segurancaPrivado) {
        Escolha escolhaAto3_2A_1 = new Escolha("""
            [1] Usar a lanterna e a lente para fotografar as marcas de rasura química nos livros matrizes sem danificar os selos oficiais.
            """, new ArrayList<>(List.of(aumentaRazao, ganhaProva)), """
            Suas fotos registram claramente o reagente químico usado para apagar o nome
            original do dono e sobrepor o nome da empresa fantasma ligada ao projeto.

            (Provas +1)
            """);


        Escolha escolhaAto3_2A_2 = new Escolha("""
            [2] Forçar a gaveta do Tabelião Chefe para obter a agenda privada de atendimentos fora do expediente.
            """, new ArrayList<>(List.of(aumentaParanioa, ganhaProva)), """
            A gaveta cede com um estalo seco. Dentro, você encontra uma lista de pagamentos
            em dinheiro vivo feitos por um corretor que atua como laranja da Prefeitura.

            (Provas +1)
            """);

        Escolha escolhaAto3_2A_3 = new Escolha("""
            [3] Arrancar as páginas rasuradas diretamente do livro matriz e confiscar os selos cartorários não utilizados.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(30))), """
            Com um rasgo seco, você arranca a folha da certidão fraudada. O alarme silencioso
            do cofre pisca no painel da parede, alertando a segurança privada.

            (Você disparou o alarme: Alarme Disparado [ID: 30])
            """);

        Escolha escolhaAto3_2A_4 = new Escolha("""
            [1] Dissecá-la a fraude selo por selo, mostrando como as terras foram transferidas ilegalmente para laranjas da Secretaria de Obras.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 2), ganhaProva)), """
            Helena usa a lente de aumento e confirma a falsificação. Ela conecta os nomes
            dos laranjas ao Secretário de Governo, montando o fluxograma perfeito para a capa.

            (Provas +1)
            """);

        Escolha escolhaAto3_2A_5 = new Escolha("""
            [2] Entregar apenas o Livro de Caixas Clandestino e pedir que ela verifique o código das contas bancárias sem expor as páginas matrizes.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 1))), """
            Helena cruza as contas com o registro de empresas do jornal e descobre a
            existência de uma empreiteira fantasma sediada no próprio endereço da Prefeitura.
            """);

        Escolha escolhaAto3_2A_6 = new Escolha("""
            [3] Bater as folhas arrancadas na mesa e exigi-la a impressão de 500 panfletos para distribuição nos pontos de ônibus antes das 06h00.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -1), new Efeito(27))), """
            Helena hesita perante o risco legal, mas a visão das páginas rasuradas a convence:
            ela liga a duplicadora manual nos fundos para gerar as cópias de denúncia.

            (Obteve Item: Panfletos de Denúncia [ID: 27])
            """);

        Escolha escolhaAto3_2A_7 = new Escolha("""
            [1] Virar de costas e tentar escalar a tubulação de escoamento para alcançar a calha.
            """, new Efeito(-1), """
            A FRAUDE CARTORÁRIA PERMANECEU OCULTA"
            Você se vira para saltar em direção ao tubo de ferro. O som abafado de três
            disparos perfura a névoa. As balas atingem suas costas e coxas antes mesmo
            de suas mãos tocarem o metal.
            """);

        Escolha escolhaAto3_2A_7_sucesso = new Escolha("""
            [1] Virar de costas e escalar a tubulação — você notou essa saída de manutenção assim que entrou no arquivo.
            """, aumentaRazao, """
            Você já tinha registrado essa rota como plano de contingência ao entrar no
            arquivo. Os disparos acertam o cano vazio: você já está dois metros à frente,
            subindo para a calha antes que percebam o erro de cálculo deles.
            """);
        escolhaAto3_2A_7_sucesso.defineRequisitoAtributo("RAZÃO", 12); // ajustar após recalcular o teto real acumulável até este ponto
        Escolha escolhaAto3_2A_8 = new Escolha("""
            [2] Jogar a lanterna/carteira em uma direção e usar a névoa para se esgueirar até o carro deles com o motor ligado, escapando com o veículo do inimigo.
            """, new ArrayList<>(List.of(aumentaParanioa, aumentaRazao, new Efeito(31))), """
            Você lança um objeto metálico contra os contêineres do outro lado. Os atiradores
            desviam o foco da lanterna por dois segundos — o suficiente para você rastejar
            sob a névoa e arrancar, quebrando o bloqueio dos homens.

            (Obteve Item: Veículo dos Seguranças [ID: 31])
            """);

        Escolha escolhaAto3_2A_9 = new Escolha("""
            [3] Chutar o contêiner de ferro pesado contra as pernas do primeiro homem, fechar a distância na penumbra e tomar a arma tática do agressor.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(29), new Efeito(21))), """
            O contêiner de duzentos quilos atinge os joelhos do homem na frente. No caos
            dos tiros desviados na névoa, você avança, quebra o pulso do atirador e
            dispara duas vezes para o ar, fazendo os outros recuarem na escuridão.

            (Obteve Item: Pistola Tática com Silenciador [ID: 29])
            (Você agora está marcado: Procurado por Agressão [ID: 21])
            """);

        Cena atoIII2A = new Cena("ato3_perspectiva2a");

        atoIII2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO III — AS CONTRADIÇÕES (PERSPECTIVA 2A: A ROTA DOS ARQUIVOS MORTOS)
        ===============================================================================

        [CENA 1: Corredor de Arquivos da Central de Registros / Subsolo / 01h45]

        Nas suas mãos, as pastas de transferência de propriedades revelam uma rasura
        grosseira: a assinatura do seu irmão Gabriel foi forjada em um documento de
        cessão de direitos assinado dois dias após a data oficial de sua morte.
        """,
                escolhaAto3_2A_1,
                escolhaAto3_2A_2,
                escolhaAto3_2A_3
        ));

        atoIII2A.adicionaDialogo(new Dialogo(helena, """
        ===============================================================================
        CENA 2: Redação do Jornal "A Voz de Santa Aurora" / 03h00
        ===============================================================================

        HELENA:
        "Você invadiu os arquivos do 2º Ofício? O que você encontrou nesses papéis mofados?"
        """,
                escolhaAto3_2A_4,
                escolhaAto3_2A_5,
                escolhaAto3_2A_6
        ));

        atoIII2A.adicionaDialogo(new Dialogo(segurancaPrivado, """
        ===============================================================================
        CENA 3: O Beco do Canal / 04h00
        ===============================================================================

        SEGURANÇA PRIVADO:
        "Parado! Você mexeu onde não devia no subsolo do Cartório, garoto. Entrega as folhas
        matrizes e os arquivos que a jornalista fez. Devagar, sem palhaçada."
        """,
                escolhaAto3_2A_7,
                escolhaAto3_2A_8,
                escolhaAto3_2A_9
        ));

        atoIII2A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        PONTE NARRATIVA FINAL DO ATO III — O RASTRO BANCÁRIO E CARTORÁRIO
        ===============================================================================

        A execução de Gabriel e das outras quatro vítimas foi a engrenagem usada pela
        elite financeira e política da cidade para tomar a posse de centenas de hectares
        sem pagar indenizações.
        """));

        return atoIII2A;
    }

    public Cena criarAtoIIIPerspectiva3A(Personagem narrador, Protagonista protagonista, NPC helena, NPC policial) {
        // ANTES: new AdicionaItem("Registro Visual da Destruição") — removido, flavor
        Escolha escolhaAto3_3A_1 = new Escolha("""
            [1] Fotografar/gravar o assessor destruindo os documentos pela janela antes de chamar Daniel ou agir.
            """, new ArrayList<>(List.of(aumentaRazao, ganhaProva)), """
            As lentes da sua câmera capturam claramente o rosto do assessor e os papéis
            com o timbre da obra da rodovia sendo triturados.

            (Provas +1)
            """);

        // ANTES: new AdicionaItem("Contratos Originais da Rodovia") — removido
        Escolha escolhaAto3_3A_2 = new Escolha("""
            [2] Usar o Cartão de Acesso/Ferramentas para cortar a energia do anexo e render o assessor na escuridão do subsolo.
            """, new ArrayList<>(List.of(aumentaParanioa, ganhaProva)), """
            O anexo fica no breu total. Assustado, o assessor tenta sair do carro com a
            pasta de originais. Você o rende na penumbra e recupera os documentos intactos.

            (Provas +1)
            """);

        // ANTES: new AdicionaItem("Celular Desbloqueado do Assessor") — removido; e a via
        // violenta não registrava Prova nenhuma no original, apesar de render evidência real
        // (mensagens do Vice-Prefeito). Corrigido para manter paridade com as outras vias.
        Escolha escolhaAto3_3A_3 = new Escolha("""
            [3] Quebrar o vidro do motorista com uma chave de roda e arrancar o assessor do carro.
            """, new ArrayList<>(List.of(aumentaViolencia, ganhaProva)), """
            Os cacos de vidro voam. Você puxa o homem pelo colarinho. Em pânico, ele
            entrega o celular pessoal desbloqueado, com mensagens do Vice-Prefeito.

            (Provas +1)
            """);

        // ANTES: new AdicionaItem("Edição Extraordinária Impressa") — removido
        Escolha escolhaAto3_3A_4 = new Escolha("""
            [1] Entregar o registro fotográfico e cruzar os contratos com as matérias antigas para redigir a capa do jornal.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 2), ganhaProva)), """
            Helena monta a edição extraordinária em tempo recorde. A manchete estampa
            as fotos do assessor e os números dos contratos da rodovia com o selo de fraude.

            (Provas +1)
            """);

        // ANTES: new AdicionaItem("Ordem do Ministério Público") — removido
        Escolha escolhaAto3_3A_5 = new Escolha("""
            [2] Passar apenas o áudio/áudio do celular para Helena e pedir que ela acione seus contatos no Ministério Público Estadual.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 1), ganhaProva)), """
            Helena faz três ligações encriptadas. Um promotor de justiça de plantão
            confirma o recebimento do áudio e emite uma ordem de busca preventiva.

            (Provas +1)
            """);

        // ANTES: new AdicionaStatus("Alerta Geral na Cidade") — convertido
        Escolha escolhaAto3_3A_6 = new Escolha("""
            [3] Exigir que ela use a duplicadora de panfletos para espalhar o depoimento do beco diretamente nas portas da prefeitura e da câmara.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -1), new Efeito(32))), """
            Centenas de cópias do depoimento e das fotos começam a ser impressas.
            Helena entrega os fardos para os entregadores da madrugada espalharem no centro.

            (A cidade inteira agora sabe: Alerta Geral na Cidade [ID: 32])
            """);

        // Game Over original — mantido como a via arriscada
        Escolha escolhaAto3_3A_7 = new Escolha("""
            [1] Virar as costas e tentar correr diretamente a pé de volta para o beco do jornal.
            """, new Efeito(-1), """
            A CORRUPÇÃO VENCEU A CORRIDA
            Ao se virar para fugir pela rua aberta, o oficial dispara duas vezes com
            munição não-letal de impacto direto nas suas pernas. Você cai na calçada
            molhada, incapacitado.
            """);

        Escolha escolhaAto3_3A_7_sucesso = new Escolha("""
            [1] Virar as costas e correr — você já notou o ponto cego da ronda ao chegar aqui.
            """, aumentaParanioa, """
            Você contou os passos da ronda antes de agir. O oficial mira, mas você já
            virou a esquina para o ponto cego que identificou minutos atrás.
            """);
        escolhaAto3_3A_7_sucesso.defineRequisitoAtributo("PARANOIA", 12); // ajustar após recálculo do teto real

        Escolha escolhaAto3_3A_8 = new Escolha("""
            [2] Flanquear o bloqueio usando as colunas do monumento central para acessar a entrada de serviço da prefeitura sem ser visto.
            """, new ArrayList<>(List.of(aumentaRazao, aumentaParanioa)), """
            Você se move em silêncio entre as estátuas e colunas de pedra, mantendo todas
            as provas secas e sob seu controle.
            """);
        Escolha escolhaAto3_3A_9 = new Escolha("""
            [3] Enfrentar o primeiro policial no corpo a corpo usando o ambiente molhado para desarmá-lo e abrir caminho pela força bruta.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(33), new Efeito(34))), """
            Você avança rápido, usa a poça de água para deslizar sob o golpe de cassetete
            e derruba o oficial com uma rasteira violenta.

            (Você sofreu: Ferimentos Leves [ID: 33])
            (Obteve Item: Arma Branca Adquirida [ID: 34])
            """);

        Cena atoIII3A = new Cena("ato3_perspectiva3a");

        atoIII3A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO III — O CERCO AO GABINETE (PERSPECTIVA 3A: A ROTA DO DEPOIMENTO)
        ===============================================================================

        [CENA 1: A Garagem do Anexo Municipal / 03h15]

        A SUV preta do Assessor do Vice-Prefeito está parada na vaga reservada. Pelo vidro
        fumê, você percebe a silhueta de alguém triturando papéis com uma máquina portátil.
        """,
                escolhaAto3_3A_1,
                escolhaAto3_3A_2,
                escolhaAto3_3A_3
        ));

        atoIII3A.adicionaDialogo(new Dialogo(helena, """
        ===============================================================================
        CENA 2: Laboratório Fotográfico de Helena / 04h15
        ===============================================================================

        HELENA:
        "O que é isso? O depoimento do beco vazou para a rádio comunitária há vinte minutos.
        O que você tem aí?"
        """,
                escolhaAto3_3A_4,
                escolhaAto3_3A_5,
                escolhaAto3_3A_6
        ));

        atoIII3A.adicionaDialogo(new Dialogo(policial, """
        ===============================================================================
        CENA 3: A Praça da Matriz sob Névoa / 05h15
        ===============================================================================

        POLICIAL:
        "Parado aí! Por ordem do Gabinete de Segurança, essa área está interditada para
        varredura. Entrega esse volume que você está carregando e encoste no muro!"
        """,
                escolhaAto3_3A_7,
                escolhaAto3_3A_8,
                escolhaAto3_3A_9
        ));

        atoIII3A.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        PONTE NARRATIVA FINAL DO ATO III (PERSPECTIVA 3A) — A CAMINHO DO CONFRONTO FINAL
        ===============================================================================

        [CENA: Hall de Entrada da Prefeitura Municipal de Santa Aurora / 05h45]

        Você consegue passar pelas portas pesadas de bronze do Palácio Municipal.
        A teia construída pelo interrogatório do beco levou você até as portas do poder.
        """));

        return atoIII3A;
    }

    public Cena criarAtoIV(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {
        // ANTES: AdicionaStatus("Infiltração Silenciosa Concluída") — removido, flavor sem uso posterior
        Escolha escolhaAto4_1 = new Escolha("""
            [1] Usar a passagem técnica de manutenção para acessar o sistema de áudio e projeção, carregando a prova correspondente ao seu caminho para execução imediata.
            """, aumentaRazao, """
            Você se posiciona na cabine de controle. As provas colhidas no Ato III estão
            prontas para serem injetadas no sistema de som da câmara.
            """);

        // ANTES: AdicionaStatus("Ponto de Acesso Tomado no Aço") — removido
        Escolha escolhaAto4_2 = new Escolha("""
            [2] Aproveitar a troca de guarda das 05h30 para render o sentinela do subsolo e tomar o controle da entrada de emergência do plenário à força.
            """, aumentaViolencia, """
            Com um golpe certeiro, você neutraliza a segurança do acesso privado. O
            plenário está vulnerável a uma invasão direta.
            """);

        // ANTES: AdicionaStatus("Disfarce de Imprensa Ativo") — removido, Efeito(helena,1) já cobre o ganho real
        Escolha escolhaAto4_3 = new Escolha("""
            [3] Aguardar a chegada de Helena com a equipe da imprensa na porta lateral para entrar disfarçado entre os jornalistas e fotógrafos credenciados.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(helena, 1))), """
            Helena pisca para você e te entrega um crachá de imprensa reserva. Você passa
            pela barreira policial sem disparar um único alarme.
            """);

        // Mantido como está — decisão sem plano, Game Over intencional (sem via alternativa)
        Escolha escolhaAto4_4 = new Escolha("""
            [4] Tentar forçar as portas de bronze do plenário principal sem armas ou disfarce.
            """, new Efeito(-1), """
            
            A FALTA DE ESTRATÉGIA CUSTOU A SUA VIDA
            Os guardas municipais na entrada identificam você imediatamente. Cercado no
            saguão e sem cobertura, você é alvejado antes de alcançar os degraus.
            """);

        // ANTES: AdicionaStatus("Daniel Desarmado Politicamente") — removido
        Escolha escolhaAto4_5 = new Escolha("""
            [1] Usar a cobertura da imprensa trazida por Helena para expor publicamente a prova diante dos repórteres.
            """, aumentaRazao, """
            Os repórteres registram as provas em fotos e áudio. Daniel percebe que disparar
            diante das câmeras é suicídio e recua desarmado.
            """);

        // ANTES: AdicionaStatus("Daniel Imobilizado na Força") — removido
        Escolha escolhaAto4_6 = new Escolha("""
            [2] Sacar a arma e confrontar Daniel em um duelo direto antes que ele abra fogo.
            """, aumentaViolencia, """
            O espoco do tiro ecoa no corredor. Daniel é atingido no braço e larga a arma,
            caindo gemendo no chão de mármore.
            """);

        // ANTES: AdicionaStatus("Daniel Coagido a Cooperar") — removido
        Escolha escolhaAto4_7 = new Escolha("""
            [3] Usar as provas obtidas no Ato III para chantagear Daniel, oferecendo uma rota de fuga para ele caso ele entregue a cumplicidade do Prefeito na tribuna.
            """, aumentaParanioa, """
            Daniel avalia o peso das provas e percebe que foi abandonado pela prefeitura.
            DANIEL: "Se eu abrir aquela porta para você, você promete que o meu nome sai da manchete principal?"
            """);

        // Corrigido: antes, esta opção SEMPRE dava Game Over, ignorando a
        // confiança construída com Helena ao longo do jogo. Agora, se a
        // parceria for forte o suficiente, ela realmente intervém.
        // AdicionaStatus("Daniel Distraído por Helena") removido — flavor sem uso posterior.
        Escolha escolhaAto4_8_sucesso = new Escolha("""
            [4] Fazer um sinal para Helena — contando com a confiança que vocês construíram ao longo da noite — para que ela distraia Daniel.
            """, aumentaRazao, """
            Helena não hesita. Ela avança e derruba uma pilha de processos sobre o braço
            armado de Daniel, dando a você a fração de segundo necessária para desarmá-lo.

            * A noite inteira construindo confiança com ela valeu a pena.
            """);
        escolhaAto4_8_sucesso.defineRequisitoConfianca(helena, CONFIANCA_PARCERIA_BASICA);

        Escolha escolhaAto4_8_falha = new Escolha("""
            [4] Gritar por ajuda e torcer para que Helena arrisque a própria vida por você.
            """, new Efeito(-1), """
            SUA ALIADA NÃO CONFIAVA EM VOCÊ PARA ARRISCAR A VIDA
            Devido ao histórico de atritos entre vocês, Helena hesita — e a hesitação dá
            tempo para Daniel disparar no seu peito. Vocês nunca construíram confiança
            suficiente para isso.
            """);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1A...") — convertido, mantido como marcador de rota
        Escolha escolhaAto4_9 = new Escolha("""
            [1] Executar a gravação nas caixas de som/distribuir as fraudes para a mesa diretora, forçando a Polícia Federal a dar ordem de prisão ao Prefeito em plenário.
            """, new ArrayList<>(List.of(aumentaRazao, new Efeito(35))), """
            As provas ecoam e circulam no plenário. Os agentes federais fecham as saídas e dão voz de prisão ao Prefeito diante de todas as câmeras.

            [Rota Ativada: Ato V — A Queda pelo Direito]
            """);
        escolhaAto4_9.defineRequisitoAtributo("RAZÃO", LIMITE_CHECKPOINT_ATO_IV);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1B...") — convertido
        Escolha escolhaAto4_10 = new Escolha("""
            [2] Vazar as cópias para a população no saguão e incitar a multidão a cercar a câmara, impedindo a saída dos corruptos e derrubando o governo municipal.
            """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(36))), """
            A multidão em fúria rompe os bloqueios e cerca o prédio. O governo municipal desmorona sob o peso da insurreição popular.

            [Rota Ativada: Ato V — A Revolta da Cidade]
            """);
        escolhaAto4_10.defineRequisitoAtributo("PARANOIA", LIMITE_CHECKPOINT_ATO_IV);

        // ANTES: AdicionaStatus("Libera Ato V — Rota 1C...") — convertido
        Escolha escolhaAto4_11 = new Escolha("""
            [3] Invadir a tribuna de arma em punho, forçar o Prefeito a assinar a confissão da morte de Gabriel sob a mira do aço e executar sua vingança antes de fugir.
            """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(37))), """
            Sob a mira do aço, o Prefeito assina o papel em pânico antes de ser derrubado. Vingança cumprida, você desaparece na névoa da cidade.

            [Rota Ativada: Ato V — A Sentença de Sangue]
            """);
        escolhaAto4_11.defineRequisitoAtributo("VIOLÊNCIA", LIMITE_CHECKPOINT_ATO_IV);

        // Mantido — Game Over intencional, tematicamente documentado no cabeçalho (nota 2)
        Escolha escolhaAto4_12 = new Escolha("""
            [4] Tentar impedir a votação apenas com um discurso moral, sem apresentar provas ou armas.
            """, new Efeito(-1),"""
            PALAVRAS SEM PROVAS NÃO DERRUBAM O PODER
            O Prefeito ignora suas palavras, bate o martelo e manda os guardas te levarem
            para o subsolo. Você é executado em segredo antes do fim do dia.

            (Nenhum dos seus atributos alcançou compromisso suficiente com uma linha de
            conduta clara — indecisão não muda uma cidade como Santa Aurora.)
            """);

        Cena atoIV = new Cena("ato4_a_verdade");

        atoIV.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        ATO IV — A VERDADE (CONVERGÊNCIA UNIVERSAL DAS ROTAS DO ATO III)
        ===============================================================================

        [CENA 1: Subsolo da Câmara Municipal de Santa Aurora / 05h15]

        Não importa o caminho que você percorreu até aqui — todas as pistas apontam para
        o mesmo destino: a Sessão Extraordinária das 06h00.

        ===============================================================================
        CENA 2: A PREPARAÇÃO TÁTICA
        ===============================================================================
        """,
                escolhaAto4_1,
                escolhaAto4_2,
                escolhaAto4_3,
                escolhaAto4_4
        ));

        atoIV.adicionaDialogo(new Dialogo(daniel, """
        ===============================================================================
        PONTE NARRATIVA I — O CONFRONTO NO CORREDOR DOS VEREADORES / 05h45
        ===============================================================================

        DANIEL:
        "Eu sabia que você viria. Você não vai estragar a votação das seis da manhã."

        Ele empunha a arma. Passos apressados ecoam atrás de você: é Helena.
        """,
                escolhaAto4_5,
                escolhaAto4_6,
                escolhaAto4_7,
                escolhaAto4_8_sucesso,
                escolhaAto4_8_falha
        ));

        atoIV.adicionaDialogo(new Dialogo(narrador, """
        ===============================================================================
        PONTE NARRATIVA II — A ENTRADA NO PLENÁRIO / 06h00
        ===============================================================================

        O relógio do Palácio Municipal soa seis batidas graves. As portas duplas do
        plenário se abrem. O Prefeito ergue o martelo na tribuna.

        ===============================================================================
        CENA 4: A ESCOLHA DECISIVA DO ATO IV
        ===============================================================================
        """,
                escolhaAto4_9,
                escolhaAto4_10,
                escolhaAto4_11,
                escolhaAto4_12
        ));

        return atoIV;
    }

    // =============================================================================
    // ATO V — ROTA 1A: A QUEDA PELO DIREITO (Razão)
    // =============================================================================
    public Cena criarAtoVRota1A(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1A_1 = new Escolha("""
    [1] Apresentar a cadeia de custódia completa, dos arquivos do Cartório até o dossiê, e chamar Helena como testemunha da origem das provas.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 1))), """
    Você expõe cada etapa em ordem cronológica. Helena confirma sob juramento que o
    material passou pelo jornal antes de chegar à mesa. O argumento de prova ilícita
    perde o fôlego.
    """);

        // ANTES: AdicionaStatus("Servidores Apreendidos") — removido, sem uso posterior
        Escolha escolhaAto5_1A_2 = new Escolha("""
    [2] Exigir que a Polícia Federal apreenda os servidores da Câmara antes que aconteça uma "pane" conveniente nos registros.
    """, aumentaParanioa, """
    O delegado hesita, mas você aponta o técnico de informática suando junto à sala
    de máquinas. Minutos depois, encontram um script de exclusão programado.
    """);

        Escolha escolhaAto5_1A_3 = new Escolha("""
    [3] Interromper o advogado aos gritos e avançar até a tribuna para arrancar o martelo do Prefeito.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(helena, -1))), """
    Dois agentes federais seguram seus braços antes que você alcance a tribuna. O
    advogado sorri: agora ele tem o que precisava para te pintar como um agressor.
    """);

        // Mantido — passividade em julgamento, mesmo tipo de Game Over intencional do Ato IV
        Escolha escolhaAto5_1A_4 = new Escolha("""
    [4] Ficar em silêncio e deixar que a Polícia Federal decida sozinha o que fazer com as provas.
    """, new Efeito(-1), """
    
    A PROVA SEM DONO FOI ANULADA
    Sem ninguém para sustentar a origem do material, o juiz de plantão o anula. O
    Prefeito sai do plenário de cabeça erguida.
    """);

        // new Efeito(10) mantido como está — reutilização correta do mesmo ID do
        // "Diário do Irmão" (cadastrado originalmente em criarAtoIIRota2A). Rota
        // alternativa de recuperação do mesmo item, não uma colisão.
        Escolha escolhaAto5_1A_5 = new Escolha("""
    [1] Pedir que Helena entregue as cópias catalogadas do Diário de Gabriel que ela guardou no cofre do jornal e exigir uma perícia independente.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 1), new Efeito(10), ganhaProva)), """
    Helena tira do cofre do jornal a cópia integral do Diário de Gabriel. A perícia
    independente confirma a autenticidade da letra do seu irmão.

    (Você recuperou o Diário de Gabriel [ID: 10])
    """);

        // ANTES: AdicionaStatus("Proteção de Testemunha") — removido, sem uso posterior
        Escolha escolhaAto5_1A_6 = new Escolha("""
    [2] Exigir proteção de testemunha antes de depor: o Gabinete tem homens dentro da polícia.
    """, aumentaParanioa, """
    O delegado aceita, contrariado. Você é transferido para uma sala sem janelas com
    dois agentes de confiança dele.
    """);

        Escolha escolhaAto5_1A_7 = new Escolha("""
    [3] Depor com raiva e exigir a prisão imediata de Daniel e de todo o Gabinete, sem esperar o trâmite.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -2))), """
    O delegado pede calma duas vezes. Você bate na mesa na terceira. O depoimento
    registra que você "demonstrou hostilidade".
    """);

        Cena atoV1A = new Cena("ato5_rota1a");

        atoV1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A QUEDA PELO DIREITO (ROTA 1A)
===============================================================================

[CENA 1: Plenário da Câmara Municipal / 06h20]

O advogado do Prefeito se levanta, ajeita a gravata e sorri.

ADVOGADO:
"Excelência, tudo o que foi apresentado aqui foi obtido por invasão de imóvel público,
arrombamento de cartório e furto de arquivos oficiais. Nada disso é prova. É crime."
""",
                escolhaAto5_1A_1,
                escolhaAto5_1A_2,
                escolhaAto5_1A_3,
                escolhaAto5_1A_4
        ));

        atoV1A.adicionaDialogo(new Dialogo(helena, """
===============================================================================
CENA 2: Sala de Depoimentos da Polícia Federal / 08h00
===============================================================================

HELENA:
"O que você disser aqui vai ser lido por um juiz daqui a um ano. Não diga nada
que você não consiga provar."
""",
                escolhaAto5_1A_5,
                escolhaAto5_1A_6,
                escolhaAto5_1A_7
        ));

        atoV1A.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1A) — O PROCESSO COMEÇA
===============================================================================

O depoimento termina às 13h40. A notícia já chegou às ruas: "PREFEITO PRESO EM
PLENÁRIO". O processo será longo. Mas a prova existe, está catalogada e está
sob custódia federal.
"""));

        return atoV1A;
    }

    // =============================================================================
    // ATO V — ROTA 1B: A REVOLTA DA CIDADE (Paranoia)
    // =============================================================================
    public Cena criarAtoVRota1B(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1B_1 = new Escolha("""
    [1] Subir na escadaria de mármore e organizar a multidão em uma comissão pacífica, lendo em voz alta os nomes dos envolvidos.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 1))), """
    Sua voz atravessa o saguão. Um a um, os nomes são lidos. A multidão, que estava
    prestes a virar uma turba, se transforma em plateia.
    """);

        // ANTES: AdicionaStatus("Provocadores Identificados") — removido, sem uso posterior
        Escolha escolhaAto5_1B_2 = new Escolha("""
    [2] Observar a multidão e identificar os provocadores infiltrados pela Guarda Municipal para justificar uma repressão.
    """, aumentaParanioa, """
    Três homens de jaqueta preta distribuem pedras para quem está na frente. Você os
    aponta para a imprensa. Sem o pretexto, a Guarda Municipal recua.
    """);

        // ANTES: AdicionaStatus("Tumulto Descontrolado") — removido. Nota: o texto descreve
        // uma consequência real (provas pisoteadas, Secretário foge); hoje isso não afeta
        // nenhum atributo. Se quiser dar peso mecânico de verdade, o lugar certo seria um
        // Efeito("ATRIBUTO", "Provas", -1), não um status nunca lido.
        Escolha escolhaAto5_1B_3 = new Escolha("""
    [3] Incitar a multidão a invadir o gabinete do Prefeito e quebrar tudo o que encontrar.
    """, aumentaViolencia, """
    A multidão explode. Documentos que poderiam servir de prova são pisoteados junto
    com o resto. Na confusão, o Secretário de Governo escapa.
    """);

        // Mantido — abandono/passividade, mesmo tipo de Game Over intencional já visto
        Escolha escolhaAto5_1B_4 = new Escolha("""
    [4] Sair pela porta lateral e deixar a multidão sozinha, sem rosto nem liderança.
    """, new Efeito(-1), """
    UMA REVOLTA SEM ROSTO É ESMAGADA
    Sem ninguém para conduzi-la, a multidão é dispersa à força. O Gabinete afirma que
    foi um tumulto de vândalos.
    """);

        Escolha escolhaAto5_1B_5 = new Escolha("""
    [1] Exigir que a rádio comunitária leia a ata da sessão e os nomes oficiais envolvidos, sem tocar a gravação.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 1))), """
    O locutor lê a ata e os nomes em tom sóbrio. A rádio ganha credibilidade, mas não
    emociona ninguém.
    """);

        // new Efeito(11) mantido como está — reutilização correta do mesmo ID da
        // "Gravação de Gabriel" (cadastrada originalmente em criarAtoIIRota2A).
        Escolha escolhaAto5_1B_6 = new Escolha("""
    [2] Pedir ao operador da rádio a cópia da gravação que ele guardou e transmiti-la na frequência aberta para toda a cidade.
    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(11), ganhaProva)), """
    O operador tira de uma caixa de sapatos a cópia da fita que Gabriel deixou com ele
    semanas antes. A voz do seu irmão ecoa em todos os rádios de Santa Aurora.

    (Você recuperou a Gravação [ID: 11])
    """);

        // ANTES: AdicionaStatus("Secretário Capturado no Túnel") — removido, sem uso posterior
        Escolha escolhaAto5_1B_7 = new Escolha("""
    [3] Descer pelas galerias subterrâneas e capturar o Secretário de Governo à força antes que ele fuja da cidade.
    """, aumentaViolencia, """
    No fim de uma galeria úmida, você o alcança. Você o arrasta de volta à superfície
    pelo colarinho diante de dezenas de testemunhas.
    """);

        Cena atoV1B = new Cena("ato5_rota1b");

        atoV1B.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A REVOLTA DA CIDADE (ROTA 1B)
===============================================================================

[CENA 1: Saguão da Câmara e Praça da Matriz / 06h20]

As cópias vazadas passam de mão em mão. A cidade inteira parece prender o fôlego.
""",
                escolhaAto5_1B_1,
                escolhaAto5_1B_2,
                escolhaAto5_1B_3,
                escolhaAto5_1B_4
        ));

        atoV1B.adicionaDialogo(new Dialogo(helena, """
===============================================================================
CENA 2: Rádio Comunitária de Santa Aurora / 07h30
===============================================================================

OPERADOR DA RÁDIO:
"Gabriel me trouxe uma fita na semana em que morreu. Acho que agora não posso mais
ter medo."
""",
                escolhaAto5_1B_5,
                escolhaAto5_1B_6,
                escolhaAto5_1B_7
        ));

        atoV1B.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1B) — A CIDADE ACORDA
===============================================================================

Ao meio-dia, Santa Aurora parece outra cidade. O governo municipal ruiu sem que
ninguém tivesse dado um tiro de aviso: a maioria dos vereadores renunciou antes
do almoço.
"""));

        return atoV1B;
    }

    // =============================================================================
    // ATO V — ROTA 1C: A SENTENÇA DE SANGUE (Violência)
    // =============================================================================
    public Cena criarAtoVRota1C(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        Escolha escolhaAto5_1C_1 = new Escolha("""
    [1] Usar a confissão assinada como escudo legal e exigir que Helena a fotografe e publique antes de qualquer passo seu.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(helena, 1))), """
    Helena fotografa a confissão com as mãos trêmulas e corre até o telefone público.
    Em dez minutos, a imagem está nas mãos de três redações.
    """);

        // ANTES: AdicionaStatus("Rastro Apagado") — removido, sem uso posterior
        Escolha escolhaAto5_1C_2 = new Escolha("""
    [2] Fugir pelas galerias subterrâneas e despistar a Guarda Municipal por um caminho que só você conhece.
    """, aumentaParanioa, """
    Você se move pelos túneis como se os tivesse construído. Os guardas perdem o seu
    rastro na terceira bifurcação.
    """);

        // ANTES: AdicionaStatus("Ferido a Bala") — removido, sem uso posterior
        // (por critério revisado, dano físico só ganha ID quando checado em algum
        // requisito posterior; se quiser um sistema de ferimentos com efeito real
        // no jogo, isso pede uma mecânica dedicada, não um "status" solto)
        Escolha escolhaAto5_1C_3 = new Escolha("""
    [3] Abrir caminho à bala pelo corredor central e sair pela porta principal diante de todos.
    """, aumentaViolencia, """
    Você abre caminho a tiros. Um deles o atinge de raspão no ombro, mas os guardas
    recuam diante de alguém que não tem mais nada a perder.
    """);

        // Mantido — rendição/passividade, mesmo padrão de Game Over intencional já visto
        Escolha escolhaAto5_1C_4 = new Escolha("""
    [4] Baixar a arma e se render aos guardas para "explicar tudo com calma".
    """, new Efeito(-1), """
    
    A CONFISSÃO FOI CASSADA E VOCÊ TAMBÉM
    A confissão, feita sob a mira de uma arma, é declarada nula em minutos. Você é
    preso por sequestro e tentativa de homicídio.
    """);

        Escolha escolhaAto5_1C_5 = new Escolha("""
    [1] Ligar para Daniel de um telefone público e propor uma troca: ele entrega o laudo original em troca de tempo para fugir.
    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
    Daniel escuta em silêncio. Depois diz apenas: "Cinco minutos." Ele deixa o laudo
    original na caixa de correio da delegacia.
    """);

        Escolha escolhaAto5_1C_6 = new Escolha("""
    [2] Esperar escondido a saída de Daniel e segui-lo para descobrir se ele guarda as provas originais em algum lugar.
    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -1))), """
    Daniel sai da delegacia, olha para os lados e pega um caminho torto até a casa
    de Gabriel. Ele entra e não sai mais.
    """);

        // new Efeito(8) mantido como está — reutilização correta do "Laudo Adulterado",
        // cadastrado originalmente no Ato I.
        Escolha escolhaAto5_1C_7 = new Escolha("""
    [3] Arrombar o armário de evidências da delegacia, recuperar o laudo original adulterado e queimar o resto.
    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(8), ganhaProva)), """
    A fechadura cede na segunda pancada. Entre dezenas de envelopes, você encontra o
    laudo original, com as rasuras que você reconheceria em qualquer lugar.

    (Você recuperou o Laudo Adulterado [ID: 8])
    """);

        Cena atoV1C = new Cena("ato5_rota1c");

        atoV1C.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO V — A SENTENÇA DE SANGUE (ROTA 1C)
===============================================================================

[CENA 1: Tribuna da Câmara Municipal / 06h10]

O Prefeito assina o papel com a mão trêmula, o cano da arma encostado no peito.
Vingança feita? A palavra pesa mais do que você imaginava.
""",
                escolhaAto5_1C_1,
                escolhaAto5_1C_2,
                escolhaAto5_1C_3,
                escolhaAto5_1C_4
        ));

        atoV1C.adicionaDialogo(new Dialogo(daniel, """
===============================================================================
CENA 2: Delegacia Central, Armário de Evidências / 07h40
===============================================================================

DANIEL:
"Eu sabia que você viria aqui. Seu irmão me deixou uma carta antes de morrer, e nela
dizia que, se você chegasse até aqui, eu devia deixar você passar."
""",
                escolhaAto5_1C_5,
                escolhaAto5_1C_6,
                escolhaAto5_1C_7
        ));

        atoV1C.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
PONTE NARRATIVA FINAL DO ATO V (ROTA 1C) — O PREÇO DA SENTENÇA
===============================================================================

Às 09h00, você está escondido no depósito de uma padaria fechada, com o ombro
latejando. A cidade inteira o procura. O Prefeito foi preso. A confissão foi
divulgada. Mas nada disso apaga o fato de que você apontou uma arma para um
homem em plenário.
"""));

        return atoV1C;
    }

    // =============================================================================
    // ATO VI — O PREÇO DE SANTA AURORA (CONVERGÊNCIA E ESCOLHA DO FINAL)
    // =============================================================================
    public Cena criarAtoVI(Personagem narrador, Protagonista protagonista, NPC helena, NPC daniel) {

        // FINAL 1 — A BALANÇA (Razão + Item 10 + Provas mínimas)
        // ANTES: AdicionaStatus("Libera Final 1 — A Balança") — removido. A própria
        // Escolha já direciona para criarFinalBalanca(); o status nunca é relido.
        Escolha escolhaAto6_1 = new Escolha("""
    [1] Colocar o diário de Gabriel sobre o túmulo e prometer que a verdade será levada até o fim nos tribunais, por mais que o processo demore.
    """, aumentaRazao, """
    Você deposita o diário sobre a lápide, ainda molhada da chuva. Não há vingança
    nem multidão. Só a promessa lenta e teimosa de que cada nome escrito naquelas
    páginas será chamado a responder diante de um juiz.

    [FINAL 1 — A BALANÇA: A verdade prevalece pelo caminho da lei]
    """);
        escolhaAto6_1.defineRequisitoAtributo("RAZÃO", LIMITE_FINAL_ATO_VI);
        escolhaAto6_1.defineRequisitoItem(10);

        // FINAL 2 — A NÉVOA SE LEVANTA (Paranoia + Item 11)
        // ANTES: AdicionaStatus("Libera Final 2...") — removido, mesmo motivo
        Escolha escolhaAto6_2 = new Escolha("""
    [2] Tocar a gravação de Gabriel no rádio do cemitério e deixar que a cidade inteira ouça a última mensagem dele, mesmo sabendo que a rede é maior do que se vê.
    """, aumentaParanioa, """
    A voz do seu irmão atravessa o cemitério, sobe os morros e chega às janelas
    de Santa Aurora. A névoa, pela primeira vez, começa a se levantar de verdade.

    [FINAL 2 — A NÉVOA SE LEVANTA: a cidade desperta, e você não está mais sozinho]
    """);
        escolhaAto6_2.defineRequisitoAtributo("PARANOIA", LIMITE_FINAL_ATO_VI);
        escolhaAto6_2.defineRequisitoItem(11);

        // FINAL 3 — O PREÇO DO SANGUE (Violência + Item 8)
        // ANTES: AdicionaStatus("Libera Final 3...") — removido, mesmo motivo
        Escolha escolhaAto6_3 = new Escolha("""
    [3] Queimar o laudo adulterado diante do túmulo, empunhar a arma e partir antes que a polícia chegue, sabendo que agora você é o procurado.
    """, aumentaViolencia, """
    O laudo queima em segundos, junto com a última mentira oficial sobre a morte
    de Gabriel. Você guarda a arma sem olhar para trás.

    [FINAL 3 — O PREÇO DO SANGUE: a vingança cobra o seu preço]
    """);
        escolhaAto6_3.defineRequisitoAtributo("VIOLÊNCIA", LIMITE_FINAL_ATO_VI);
        escolhaAto6_3.defineRequisitoItem(8);

        // Mantido — abandono/passividade, mesmo padrão de Game Over intencional
        Escolha escolhaAto6_4 = new Escolha("""
    [4] Virar as costas ao túmulo, pegar o primeiro ônibus e deixar Santa Aurora sem olhar para trás.
    """, new Efeito(-1), """
    
    O SILÊNCIO DE SANTA AURORA
    O ônibus parte na chuva fina. Pelo vidro molhado, a cidade encolhe. Ninguém
    fala do que aconteceu no plenário, e em uma semana ninguém mais lembra de
    você. A névoa cobre a cidade como sempre cobriu.

    (Nenhuma das três causas foi levada longe o suficiente. Talvez, numa outra
    tentativa, você escolha se comprometer de verdade com uma delas.)
    """);

        Cena atoVI = new Cena("ato6_o_preco");

        atoVI.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
ATO VI — O PREÇO DE SANTA AURORA (CONVERGÊNCIA DAS ROTAS DO ATO V)
===============================================================================

[CENA: Cemitério de Santa Aurora / Fim de Tarde / 17h30]

Não importa o caminho: pela lei, pela revolta ou pelo aço, todos terminam aqui.
O cemitério fica no alto do morro. De lá, dá para ver a cidade inteira, com as
luzes da Prefeitura já apagadas.

O túmulo de Gabriel é simples: uma lápide de concreto, o nome gravado à mão e um
vaso de flores murchas. Você tira do bolso o medalhão de bronze, o mesmo que ele
lhe deu na infância, e o coloca sobre a pedra molhada.

> VOZ DA RAZÃO:
> "Você tem provas, tem registro, tem uma história que pode ser contada com cuidado."

> VOZ DA CONSPIRAÇÃO:
> "A cidade acordou, mas a rede continua lá. Uma voz vale mais que mil papéis."

> VOZ DA VIOLÊNCIA:
> "Você prometeu que ele seria vingado. Não deixe pela metade."

===============================================================================
A ESCOLHA FINAL — O QUE VOCÊ FAZ COM O QUE SOBROU?
===============================================================================
""",
                escolhaAto6_1,
                escolhaAto6_2,
                escolhaAto6_3,
                escolhaAto6_4
        ));

        return atoVI;
    }

    // =============================================================================
    // FINAL 1 — A BALANÇA (Razão)
    // O final "correto", mas deliberadamente incompleto: recompensa cumprir a
    // lei, mas nega o fechamento emocional. A intenção é deixar o jogador
    // sentindo que faltou alguma coisa — e com vontade de tentar de novo.
    // =============================================================================
    public Cena criarFinalBalanca(Personagem narrador, Protagonista protagonista, NPC helena) {
        Cena finalBalanca = new Cena("final1_balanca");

        finalBalanca.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 1 — A BALANÇA
===============================================================================

[CENA: Fórum Federal de Santa Aurora / Onze meses depois]

O julgamento dura quatro semanas. O Prefeito é condenado a trinta e dois anos.
O Secretário de Governo, a vinte e um. Mas o Vice-Prefeito e o Tabelião Chefe
fecham acordo de delação premiada e cumprem pena em regime domiciliar antes do
fim do ano — a lei que você tanto defendeu também protege quem sabe negociar
com ela.
"""));

        finalBalanca.adicionaDialogo(new Dialogo(helena, """
===============================================================================
EPÍLOGO — O QUE SOBRA DEPOIS DA SENTENÇA
===============================================================================

Helena publica a série completa. O jornal recebe assinaturas de todo o estado,
mas ela raramente responde suas mensagens agora — o processo consumiu meses da
vida dela também, e algo entre vocês esfriou no meio das audiências.

HELENA (por telefone, distante):
"Você fez a coisa certa. Isso devia ser o suficiente, não devia?"

Você não sabe responder. Fica na cidade por obrigação, não por escolha — testemunha
de acusação em processos que ainda vão levar anos. Toda sexta-feira sobe ao
cemitério sozinho e troca as flores do túmulo de Gabriel. Nunca chorou por ele,
não de verdade; estava ocupado demais construindo um caso.

Santa Aurora seguiu em frente. Os jornais pararam de falar do assunto depois do
terceiro mês. Você conseguiu a justiça que prometeu, ponto por ponto, prova por
prova — e mesmo assim, sentado sozinho na sala vazia do apartamento alugado,
não consegue deixar de se perguntar se venceu o caso certo, do jeito errado.

===============================================================================
FIM — [FINAL 1: A BALANÇA]
===============================================================================
"""));

        return finalBalanca;
    }

    // =============================================================================
    // FINAL 2 — A NÉVOA SE LEVANTA (Paranoia)
    // O final mais satisfatório: vigilância reenquadrada como cuidado, não
    // isolamento. Vitória coletiva, aliança mantida, sensação de pertencimento.
    // =============================================================================
    public Cena criarFinalNevoa(Personagem narrador, Protagonista protagonista, NPC helena) {
        Cena finalNevoa = new Cena("final2_nevoa");

        finalNevoa.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 2 — A NÉVOA SE LEVANTA
===============================================================================

[CENA: Praça da Matriz / Seis meses depois]

A voz de Gabriel foi tocada tantas vezes que virou trilha de protesto. Na praça,
crianças repetem o refrão "Se alguém está ouvindo isso..." sem saber exatamente
de onde vem. Bairros inteiros se organizaram em associações de vigilância
comunitária — não por medo, mas por cuidado uns com os outros. O governo
municipal foi substituído por uma junta provisória de moradores, e pela primeira
vez em anos as reuniões da Câmara são abertas e transmitidas ao vivo pela rádio
comunitária.
"""));

        finalNevoa.adicionaDialogo(new Dialogo(helena, """
===============================================================================
EPÍLOGO — A CIDADE QUE APRENDEU A OUVIR
===============================================================================

Helena virou editora-chefe do jornal, agora com uma redação de verdade — mesa
para seis repórteres, financiada por assinantes de toda a região. Ela te chamou
para ser o correspondente de investigações locais, um trabalho que parece ter
sido desenhado sob medida para a pessoa que você se tornou.

HELENA:
"Você continua olhando por cima do ombro. Mas agora tem gente olhando com você.
Isso conta como progresso, sabia?"

Você sorri, de verdade, pela primeira vez desde que voltou a Santa Aurora. Na
parede do seu apartamento — que já não é mais provisório —, o velho mapa com
linhas vermelhas foi substituído por fotos: Helena na redação, o pessoal da
associação de bairro, uma foto do túmulo de Gabriel com flores frescas.

Você não parou de ser vigilante. Mas a vigilância deixou de ser solidão para
virar comunidade. Ainda existem nomes no seu mapa mental que a justiça não
alcançou — mas agora você tem uma cidade inteira ajudando a procurar, e isso,
descobre você, é o mais parecido com paz que alguém como você consegue ter.

Santa Aurora acordou. E, pela primeira vez, você acordou junto com ela.

===============================================================================
FIM — [FINAL 2: A NÉVOA SE LEVANTA]
===============================================================================
"""));

        return finalNevoa;
    }

    // =============================================================================
    // FINAL 3 — O PREÇO DO SANGUE (Violência)
    // Final deliberadamente "mediano": justiça real, mas custo pessoal
    // concreto e ambíguo. Nem tragédia total, nem vitória limpa.
    // =============================================================================
    public Cena criarFinalSangue(Personagem narrador, Protagonista protagonista) {
        Cena finalSangue = new Cena("final3_sangue");

        finalSangue.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
FINAL 3 — O PREÇO DO SANGUE
===============================================================================

[CENA: Rodovia Estadual, saindo de Santa Aurora / Noite]

A caminhonete que você comprou com o resto do dinheiro percorre a estrada de
terra sob uma chuva fina. No banco do passageiro, o medalhão de bronze balança
com o movimento. No retrovisor, as luzes da cidade encolhem.

Santa Aurora o chama de herói ou de assassino, dependendo de quem responde. O
Prefeito está preso. O laudo adulterado virou cinzas. Mas o mandado de prisão
está em vigor e o seu retrato circula em todas as delegacias do estado.
"""));

        finalSangue.adicionaDialogo(new Dialogo(narrador, """
===============================================================================
EPÍLOGO — O QUE FICOU PARA TRÁS
===============================================================================

O rádio da caminhonete sintoniza uma estação de outra cidade. Uma locutora fala
sobre um "vigilante armado que invadiu a Câmara Municipal de Santa Aurora" — e
depois, num tom mais sóbrio, sobre um Prefeito preso por corrupção e homicídio.
As duas notícias são a mesma história, contada por gente diferente.

Você não se arrepende de ter feito o Prefeito pagar. Também não se orgulha do
que precisou fazer para chegar até ele. Gabriel, pensa você, jamais teria
apontado a arma — mas Gabriel também não teria sobrevivido ao que você viveu
em uma única madrugada, e pelo menos os nomes na parede da prefeitura hoje
significam alguma coisa para os cinco mortos que vieram antes dele.

Na próxima cidade, você vai usar outro nome. Vai aprender a dormir de olhos
abertos, sem Helena, sem Daniel, sem ninguém que saiba o que você fez ou por
quê. A justiça foi feita — só não sobrou ninguém para comemorar com você.

===============================================================================
FIM — [FINAL 3: O PREÇO DO SANGUE]
===============================================================================
"""));

        return finalSangue;
    }

}
