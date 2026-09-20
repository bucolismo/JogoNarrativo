    import java.util.ArrayList;
    import java.util.List;

    public class ConstrutorDeCenas {

        private Cena cenaAtual;
        private String cenaID;


        public ConstrutorDeCenas() {
            this.cenaAtual = new Cena();
        }

        //Se o valor dos aumentos forem padrão é melhor declarar os efeitos primeiro
        Efeito aumentaRazao = new Efeito("ATRIBUTO", "Razão", 3);
        Efeito aumentaParanioa = new Efeito("ATRIBUTO", "Paranoia", 3);
        Efeito aumentaViolencia = new Efeito("ATRIBUTO", "Violência", 3);

        public Cena criarPrologo(Personagem narrador, Protagonista protagonista) {
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

        public Cena criarAtoI(Protagonista protagonista, Personagem narrador, Personagem jonas, Personagem daniel) {

            Cena atoI = new Cena("ato_1");

            // =========================================================================
            // DIÁLOGO 1 — A CHEGADA EM SANTA AURORA
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
            // DIÁLOGO 2 — A LEMBRANÇA DE GABRIEL
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
                    new ArrayList<>(List.of(aumentaRazao, new Efeito(6))),
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
            // DIÁLOGO 5 — A CENA NO BECO
            // =========================================================================

            Escolha escolhaDialogo5_1 = new Escolha("""
                    [1] Agachar-se para recolher as provas na lama e checar a pessoa no chão de
                        forma metódica.
                    """,
                    new ArrayList<>(List.of(aumentaRazao, new Efeito(7))),
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
                    new ArrayList<>(List.of(aumentaViolencia, new Efeito(8))),
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
            //Requisito Laudo Adulterado
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
            //Sem requisitos
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
            //Requisito: Pista de Jonas
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
            //Requisito violência alta:

            Escolha escolha5 = new Escolha("""
                    [5] [Opção 3A — O Confronto no Beco]
                           Marchar diretamente até o beco lateral da delegacia ao perceber movimentação
                           suspeita perto dos contêineres.""",
                    aumentaViolencia,
                    """
                            A raiva se sobrepõe ao bom senso. Você marcha até a delegacia e escancara as portas de entrada com um golpe.
                            
                            [ROTA ATIVADA: ATO II - ROTA 3A (INVASÃO À DELEGACIA)]
                            """
            );

            //Requisito: Violência Alta
            Escolha escolha6 = new Escolha("""
                    [6] [Opção 3B — Invasão à Delegacia]
                        Chutar a porta da delegacia, peitar o detetive Daniel e exigir acesso imediato.
                    """,
                    aumentaViolencia,
                    """
                            A raiva se sobrepõe ao bom senso. Você marcha até a delegacia e escancara as portas de entrada com um golpe.
                            
                            [ROTA ATIVADA: ATO II - ROTA 3A (INVASÃO À DELEGACIA)]
                            """
            );

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
                    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, 1))), """
                    Daniel relaxa os ombros ao ver sua reação desolada. Ele se encosta na cadeira, 
                    convencido de que você não passa de um familiar vulnerável buscando conforto.
                    
                    DANIEL:
                    "É difícil aceitar, eu sei. Ele se envolveu com as pessoas erradas nos últimos 
                    meses... Pessoas perigosas da periferia. Mas não se preocupe, estamos cuidando."
                    """);

            Escolha escolhaAuto2 = new Escolha("""
                    [2] Demonstrar cooperação analítica:
                        "A cidade parece a mesma de sempre, mas os boatos no rádio dizem que a polícia não tem pistas. Isso é verdade?"
                    """, aumentaRazao, """
                    Daniel ajeita as pastas sobre a mesa, medindo as palavras com cuidado.
                    
                    DANIEL:
                    "O rádio exagera para vender notícia. Temos pistas, sim, mas precisamos de 
                    cautela para não alertar os suspeitos antes da hora."
                    """);

            Escolha escolhaAuto3 = new Escolha("""
                    [3] Dar um passo à frente com o olhar fixo:
                        "Eu passei por pessoas estranhas na rua, Daniel. E sinto que a própria polícia está pisando em ovos."
                    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -1))), """
                    Daniel estreita os olhos e cruza os braços, assumindo uma postura defensiva.
                    
                    DANIEL:
                    "Medir as palavras aqui dentro seria uma boa ideia. A polícia faz o trabalho 
                    dela, e você deveria focar no enterro do seu irmão."
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
                    """, new ArrayList<>(List.of(aumentaParanioa, new Efeito(daniel, -2))), """
                    Ao ouvir o papel se mover, Daniel se vira rapidamente e bate a mão sobre a mesa, 
                    recolhendo a pasta antes que você leia os detalhes.
                    
                    [CONFIANÇA COM DANIEL: -2 (Ele passa a te enxergar como uma ameaça imprevisível)]
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
                    """, aumentaRazao, """
                    Você registra os carimbos e as datas apagadas. As fotos garantem que você 
                    tenha provas de que o arquivo foi adulterado, mas o tempo gasto na operação 
                    permite que o prédio seja cercado em silêncio.
                    """);

            Escolha escolhaAuto5 = new Escolha("""
                    [2] Procurar por relatórios anexados que mencionem quem solicitou a retirada 
                        dessas pastas do arquivo público.
                    """, aumentaParanioa, """
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

            // =========================================================================
            // DIÁLOGO 2 — A BUSCA NOS ARQUIVOS
            // =========================================================================
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
                    
                    > VOZ DA RAZÃO:
                    > "As datas das rasuras coincidem com o início das mortes. Há um padrão de 
                    > ocultação aqui, mas o motivo principal ainda está oculto."
                    """,
                    escolhaAuto4,
                    escolhaAuto5,
                    escolhaAuto6
            ));

            // =========================================================================
            // DIÁLOGO 3 — DESFECHO DA ROTA 2B (GAME OVER)
            // =========================================================================
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
            // =========================================================================
            // DESDOBRAMENTOS DA OPÇÃO 3B — A INVASÃO À DELEGACIA (GAME OVER)
            // =========================================================================
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

        public Cena criarAtoIIRota1A(Personagem narrador, Protagonista protagonista) {
            Escolha escolhaAuto1 = new Escolha("""
                    [1] Apontar para a diferença de horários entre o chamado e o laudo pericial: 
                        "A autópsia indica a morte duas horas antes do chamado oficial. Por que o relatório foi alterado, Daniel?"
                    """, aumentaRazao, """
                    Daniel desvia o olhar para os papéis na mesa e solta um suspiro pesado. Ele 
                    tranca a porta do escritório para que os outros policiais não ouçam a conversa.
                    
                    DANIEL:
                    "Você sempre foi analítico como ele... Escute, nem tudo o que acontece nesta 
                    delegacia passa pelas minhas mãos. Se esse relatório foi alterado, veio de 
                    instâncias superiores."
                    """);

            Escolha escolhaAuto2 = new Escolha("""
                    [2] Observar a reação do detetive enquanto fala:
                        "Você parece surpreso em ver esses papéis. Estava esperando que eles ficassem perdidos no beco?"
                    """, aumentaParanioa, """
                    Os olhos de Daniel se arregalam por um segundo antes de ele recuperar a postura. 
                    Ele dá um passo para trás e cruza os braços, adotando uma postura defensiva.
                    
                    DANIEL:
                    "Você está vendo fantasmas onde não tem. Se alguém deixou esses papéis no beco, 
                    foi para nos colocar um contra o outro."
                    """);

            Escolha escolhaAuto3 = new Escolha("""
                    [3] Bater a mão na mesa de Daniel:
                        "Deixe as condolências de lado e me diga quem mandou adulterar o relatório do meu irmão!"
                    """, aumentaViolencia, """
                    O baque no tampo da mesa faz a xícara de café de Daniel balançar. Dois policiais 
                    no corredor olham pelo vidro da porta, mas Daniel levanta a mão pedindo calma.
                    
                    DANIEL:
                    "Abaixe o tom! Você não está em posição de fazer exigências aqui. Se quiser 
                    respostas, vai ter que me ouvir sem chilique."
                    """);

            Escolha escolhaAuto4 = new Escolha("""
                    [1] "Se você não me entregar essa pasta agora, o laudo adulterado vai direto para os jornais fora de Santa Aurora."
                    """, aumentaRazao, """
                    Daniel engole em seco ao perceber a ameaça calculada. Ele desliza a pasta 
                    sobre a mesa, admitindo que não pode arriscar um escândalo fora da cidade.
                    """);

            Escolha escolhaAuto5 = new Escolha("""
                    [2] "Abra a pasta na minha frente e me mostre os carimbos de entrada antes de me dizer o que tem aí dentro."
                    """, aumentaParanioa, """
                    Daniel folheia os papéis sob seu olhar atento. A verificação dos carimbos revela 
                    que faltam relatórios vitais anexados na data do crime, provando que o arquivo 
                    já foi filtrado antes de chegar a você.
                    """);

            Escolha escolhaAuto6 = new Escolha("""
                    [3] Arrancar a pasta vermelha da mão do detetive antes que ele feche a gaveta.
                    """, aumentaViolencia, """
                    Você toma a pasta em um gesto rápido. Daniel recua, surpreso pela audácia, 
                    mas não tenta reavê-la; ele apenas cruza os braços, tenso com o rumo do confronto.
                    """);

            Escolha escolhaAuto7 = new Escolha("""
                    [1] "Ele cometeu erros, e eu também. Mas os fatos mostram que ele estava certo sobre o esquema. Vamos trabalhar juntos para expor quem fez isso."
                    """, aumentaRazao, """
                    Daniel assente com a cabeça e entrega o rádio de frequência restrita da polícia.
                    
                    * CAMINHO LIBERADO: Você ganha um aliado interno que fornecerá avisos 
                      sobre operações oficiais e interceptações de chamadas em tempo real.
                    [CONFIANÇA COM DANIEL: AUMENTA]
                    """);

            Escolha escolhaAuto8 = new Escolha("""
                    [2] "Não tente inverter o jogo, Daniel. Você não ajudou ele na época e agora parece estar escondendo o resto dos documentos para se proteger."
                    """, aumentaParanioa, """
                    Daniel recolhe os papéis restantes e guarda na gaveta trancada, encerrando a conversa.
                    
                    * CAMINHO LIBERADO: Você é forçado a agir na ilegalidade, precisando 
                      invadir o arquivo morto da delegacia durante a madrugada para obter o restante das pistas.
                    [CONFIANÇA COM DANIEL: REDUZ]
                    """);

            Escolha escolhaAuto9 = new Escolha("""
                    [3] "Minha relação com meu irmão não é da sua conta. Só me diga o nome do suspeito principal antes que eu descubra por conta própria."
                    """, aumentaViolencia, """
                    Daniel, intimidado pela sua postura, solta o nome do último empresário que 
                    esteve com seu irmão antes da morte.
                    
                    * CAMINHO LIBERADO: Rota de investigação rápida focada em interrogatórios 
                      agressivos com suspeitos civis na área nobre da cidade.
                    [CONFIANÇA COM DANIEL: NEUTRA/HOSTIL]
                    """);

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
                    
                    > VOZ DA RAZÃO:
                    > "Não se altere. Mantenha os fatos em ordem. Aparelho de análise, divergência 
                    > de horários e rasuras no documento. Faça-o admitir as falhas."
                    """,
                    escolhaAuto1,
                    escolhaAuto2,
                    escolhaAuto3
            ));

            // =========================================================================
            // CENA 2 — A PRESSÃO TÁTICA
            // =========================================================================
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
                    
                    > VOZ DA RAZÃO:
                    > "Ele está tentando barganhar. Exija ver o arquivo agora ou ameace levar as 
                    > provas rasuradas para a imprensa da capital."
                    
                    > VOZ DA CONSPIRAÇÃO:
                    > "Essa pasta vermelha pode ser uma isca. Verifique se as páginas internas 
                    > batem com os carimbos oficiais antes de aceitar o que ele diz."
                    
                    > VOZ DA VIOLÊNCIA:
                    > "Pegue a pasta da mão dele antes que ele decida fechar o arquivo novamente."
                    """,
                    escolhaAuto4,
                    escolhaAuto5,
                    escolhaAuto6
            ));

            // =========================================================================
            // CENA 3 — O DIÁLOGO COM DANIEL (TESTE DE CONFIANÇA)
            // =========================================================================
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
            //new Escolha("""[4] "Se você realmente se importava com ele, me entregue as chaves do necrotério agora. Deixe que eu veja o corpo e comprove o que a perícia escondeu."""", aumentaViolencia, """Daniel hesita, mas entrega o cartão de acesso lateral do IML municipal.* CAMINHO LIBERADO: Investigação noturna e necropsia independente nocorpo do seu irmão para identificar vestígios não relatados no laudo.[CONFIANÇA COM DANIEL: CONDICIONAL]""")//
            return cenaOpcao1A;
        }

        public Cena criarAtoIIRota2A(Personagem narrador, Protagonista protagonista) {
            Escolha escolhaAuto1 = new Escolha("""
                    [1] Analisar os rastros no chão e o mecanismo do alçapão para identificar se há algum fio esticado ou sistema de alarme improvisado.
                    """, aumentaRazao, """
                    Você ilumina o chão com a tela do celular e nota um fio de nylon esticado na 
                    dobradiça da porta metálica, ligado a uma lata cheia de pregos pendurada no teto. 
                    Você desarma o alarme improvisado com cuidado e desce a escada com a certeza 
                    de que ninguém no andar de baixo ouviu sua chegada.
                    """);

            Escolha escolhaAuto2 = new Escolha("""
                    [2] Descer em silêncio absoluto pela escada lateral de emergência, usando a escuridão como cobertura para tomar o piso inferior de surpresa.
                    """, aumentaParanioa, """
                    Você ignora o alçapão principal e contorna pela estrutura de ferro até a escada 
                    de incêndio lateral. Nas sombras do mezanino, você percebe que a lâmpada do 
                    subsolo pisca devagar. Da sua posição elevada, você consegue ver uma mesa de 
                    trabalho sem ser notado por qualquer câmera ou vigilante.
                    """);

            Escolha escolhaAuto3 = new Escolha("""
                    [3] Puxar o alçapão com força de uma vez e descer rápido, pronto para reagir se houver alguém te esperando lá embaixo.
                    """, aumentaViolencia, """
                    Você puxa a tampa de ferro com estrondo, ignorando o alarme de latas que desaba 
                    no chão. O barulho ecoa por todo o galpão deserto. Você desce os degraus de três 
                    em três, impondo sua presença no espaço antes mesmo que qualquer ameaça oculta 
                    possa se organizar.
                    """);

            Escolha escolhaAuto4 = new Escolha("""
                    [1] Tentar abrir o cadeado do diário usando datas marcantes do passado de vocês.
                    """, aumentaRazao, """
                    O segredo cede com um clique seco quando você insere a data da sua partida. 
                    Dentro do diário, você encontra uma lista detalhada de nomes da prefeitura, 
                    transferências bancárias e os locais exatos onde os outros quatro corpos foram 
                    encontrados antes do seu irmão.
                    
                    (Obteve Pista: Diário do Irmão & Registros de Transferência)
                    """);

            Escolha escolhaAuto5 = new Escolha("""
                    [2] Pressionar o botão de reprodução do gravador de voz para ouvir a última gravação do seu irmão.
                    """, aumentaParanioa, """
                    A fita chia antes de revelar a voz grave do seu irmão: "Se alguém está 
                    ouvindo isso, o Daniel mentiu sobre o laudo. Não confie na polícia. Eles estão 
                    usando a névoa para encobrir o que tiram do subsolo da cidade..." A gravação 
                    corta bruscamente com o som de uma porta sendo arrombada.
                    
                    (Obteve Pista: Fita do Gravador - Denúncia de Daniel)
                    """);

            Escolha escolhaAuto6 = new Escolha("""
                    [3] Usar um pé de cabra encostado na mesa para quebrar o fecho da gaveta e do diário de uma vez.
                    """, aumentaViolencia, """
                    O metal cede sob a alavanca. Além dos documentos rasgados no impacto, você 
                    encontra um compartimento secreto na gaveta contendo uma chave mestra com o 
                    símbolo do cartório central e um mapa marcando o acesso às galerias subterrâneas.
                    
                    (Obteve Item: Chave Mestra do Cartório)
                    """);

            Escolha escolhaAuto7 = new Escolha("""
                    [1] Apagar as luzes e escapar pelo duto de ventilação lateral com os documentos.
                    """, aumentaRazao, """
                    Você desliza pelo duto de ar estreito em silêncio enquanto os invasores vasculham 
                    a sala vazia abaixo. Você sai nos fundos da fábrica com o diário e o mapa intactos, 
                    pronto para usar as informações de forma calculada.
                    """);

            Escolha escolhaAuto8 = new Escolha("""
                    [2] Esconder-se atrás dos arquivadores para identificar os rostos dos invasores.
                    """, aumentaParanioa, """
                    Da penumbra, você reconhece os distintivos ocultos sob os casacos dos invasores: 
                    são homens da própria guarda municipal. Você confirma que a segurança pública está 
                    ativamente limpando os rastros deixados pelo seu irmão.
                    """);

            Escolha escolhaAuto9 = new Escolha("""
                    [3] Derrubar a prateleira pesada contra a escada para desestabilizar os sujeitos e confrontá-los.
                    """, aumentaViolencia, """
                    A estrutura de ferro desaba com um estrondo ensurdecedor, prendendo um dos 
                    invasores sob os destroços. O outro recua assustado pela sua reação implacável, 
                    deixando o caminho da saída totalmente livre para você.
                    """);

            Escolha escolhaAuto10 = new Escolha("""
                    [1] Ir ao Cartório Central para confrontar o tabelião com a chave e os documentos.
                    """, aumentaRazao, """
                    Você decide ir ao arquivo do Cartório Central antes que a notícia da invasão 
                    à fábrica chegue ao gabinete municipal.
                    
                    [ROTA ATIVADA: O SEGREDO DO CARTÓRIO]
                    """);

            Escolha escolhaAuto11 = new Escolha("""
                    [2] Usar os mapas das galerias para acessar o subsolo da Prefeitura em segredo.
                    """, aumentaParanioa, """
                    Aproveitando a névoa densa, você segue as marcações do mapa direto para o 
                    sistema de drenagem que passa por baixo do prédio do Gabinete Municipal.
                    
                    [ROTA ATIVADA: INFILTRAÇÃO NAS GALERIAS]
                    """);

            Escolha escolhaAuto12 = new Escolha("""
                    [3] Interceptar a patrulha da Guarda Municipal que tentou te emboscar na fábrica.
                    """, aumentaViolencia, """
                    Você decide caçar os homens que o perseguiram no galpão para extrair à força 
                    o nome do contratante direto antes que eles informem o fracasso da missão.
                    
                    [ROTA ATIVADA: CONFRONTO COM OS EMBOSCADORES]
                    """);


            Cena cenaRota2A = new Cena("ato2_rota2a");
            // =========================================================================
            // CENA 1 — A ENTRADA NO SUBTERRÂNEO
            // =========================================================================
            cenaRota2A.adicionaDialogo(new Dialogo(narrador, """
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
                    escolhaAuto1,
                    escolhaAuto2,
                    escolhaAuto3
            ));

            // =========================================================================
            // CENA 2 — O ACHADO NO ESCONDERIJO
            // =========================================================================
            cenaRota2A.adicionaDialogo(new Dialogo(narrador, """
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
                    escolhaAuto4,
                    escolhaAuto5,
                    escolhaAuto6
            ));

            // =========================================================================
            // CENA 3 — A AMEAÇA NO GALPÃO
            // =========================================================================
            cenaRota2A.adicionaDialogo(new Dialogo(narrador, """
                    ===============================================================================
                    ESCOLHA 3 — A AMEAÇA NO GALPÃO
                    ===============================================================================
                    
                    Enquanto você examina os achados, o som pesado de passos no piso de madeira 
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
                    escolhaAuto7,
                    escolhaAuto8,
                    escolhaAuto9
            ));

            // =========================================================================
            // CENA 4 — RESOLUÇÃO DO SEGMENTO 2A & DESDOBRAMENTOS / TRANSIÇÃO
            // =========================================================================
            cenaRota2A.adicionaDialogo(new Dialogo(narrador, """
                    ===============================================================================
                    RESOLUÇÃO DO SEGMENTO 2A — O SEGREDO DO SUBSOLO
                    ===============================================================================
                    
                    O silêncio volta a tomar conta da fábrica abandonada enquanto a névoa de 
                    Santa Aurora entra pelas frestas das janelas quebradas. 
                    
                    Com as pistas resgatadas do esconderijo do seu irmão — as anotações do diário, 
                    a fita do gravador e os mapas da cidade —, a névoa que encobria a morte dele 
                    começa a se dissipar, revelando uma verdade incômoda: a sequência de homicídios 
                    nunca foi obra de um criminoso comum, mas sim uma cortina de fumaça operada por 
                    figuras poderosas da própria cidade.
                    
                    Seu irmão descobriu o esquema e pagou com a vida por tentar expor a verdade. 
                    Agora, os registros estão com você.
                    
                    ===============================================================================
                    ESTADO ATUAL DA INVESTIGAÇÃO
                    ===============================================================================
                    
                    * EVIDÊNCIA CHAVE: O diário e os arquivos com nomes de autoridades locais.
                    * AMEAÇA IDENTIFICADA: Elementos da segurança pública estão ativamente 
                      apagando os rastros do caso.
                    * PRÓXIMO PASSO: Usar essas informações para encurralar quem ordenou o crime.
                    
                    ===============================================================================
                    DESDOBRAMENTO — QUAL É O SEU PRÓXIMO DESTINO?
                    ===============================================================================
                    
                    Com as provas em mãos e a fábrica cercada pela neblina, você precisa decidir 
                    onde fará a sua próxima jogada antes do amanhecer.
                    """,
                    escolhaAuto10,
                    escolhaAuto11,
                    escolhaAuto12
            ));

            return cenaRota2A;
        }

        public Cena criarAtoIIRota3A(Personagem narrador, Protagonista protagonista, NPC daniel) {
            Escolha escolhaAuto1 = new Escolha("""
                    [1] Pressionar o homem contra a parede de tijolos e exigir o nome de quem o contratou:
                        "Quem te pagou para queimar os relatórios no beco? Fala agora!"
                    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -1))), """
                    O homem treme e murmura um apelido pouco antes de a porta de serviço se 
                    abrir bruscamente. Daniel sai armado, vê a cena e manda você soltar o suspeito 
                    imediatamente. O sujeito é levado algemado para a cela, mas Daniel fica 
                    extremamente irritado com a sua violência em propriedade policial.
                    
                    [CONFIANÇA COM DANIEL: -1]
                    """);

            Escolha escolhaAuto2 = new Escolha("""
                    [2] Revistar os bolsos do sujeito rapidamente em busca de carteira, celular ou chaves antes que a patrulha perceba a movimentação.
                    """, aumentaRazao, """
                    Sua agilidade permite que você encontre um cartão de acesso magnético com um 
                    símbolo timbrado da Prefeitura no bolso da jaqueta dele. Você guarda a prova 
                    no casaco um segundo antes de a patrulha dobrar a esquina.
                    
                    (Obteve Item: Cartão de Acesso da Prefeitura)
                    [CONFIANÇA COM DANIEL: NEUTRA]
                    """);

            Escolha escolhaAuto3 = new Escolha("""
                    [3] Arrastar o sujeito para a penumbra do portão dos fundos para evitar a luz da rua e interrogar em silêncio.
                    """, aumentaParanioa, """
                    Escondido no ponto cego das câmeras de segurança, você faz o sujeito confessar 
                    que recebia ordens por mensagens anônimas de um número com o código interno do 
                    gabinete municipal. Você obtém a informação sem expor sua posição para a polícia.
                    
                    (Obteve Pista: Canal do Gabinete)
                    [CONFIANÇA COM DANIEL: NEUTRA]
                    """);

            Escolha escolhaAuto4 = new Escolha("""
                    [4] Largar o homem no chão e chamar o Detetive Daniel para assumir a custódia do suspeito de forma oficial.
                    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
                    Daniel atende ao seu chamado de rádio e rende o suspeito no beco. Impressionado 
                    pela captura limpa e sem uso de força desmedida, ele agradece a ajuda e 
                    concorda em realizar o interrogatório em conjunto na sala oficial.
                    
                    [CONFIANÇA COM DANIEL: +1]
                    """);

            Escolha escolhaAuto5 = new Escolha("""
                    [1] Apresentar as fotos do local do crime e apontar as falhas do plano dele:
                        "O seu contratante deixou a placa do seu carro no sistema. Você foi descartado."
                    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
                    O homem olha os papéis, empalidece e cede à lógica. Ele confessa que o 
                    pagamento veio de uma conta ligada ao gabinete do vice-prefeito de 
                    Santa Aurora para apagar as provas daquela noite.
                    
                    (Obteve Pista: Vínculo com Vice-Prefeito)
                    [CONFIANÇA COM DANIEL: +1]
                    """);

            Escolha escolhaAuto6 = new Escolha("""
                    [2] Blefar dizendo que o celular apreendido já revelou todas as conversas apagadas:
                        "Nós já resgatamos as mensagens do servidor. Se não confirmar agora, leva a culpa sozinho."
                    """, aumentaParanioa, """
                    O suspeito hesita, desconfiado do blefe, mas treme ao ouvir o nome da prefeitura. 
                    Ele confirma o envolvimento da administração, mas se recusa a assinar o depoimento.
                    
                    (Obteve Pista: Testemunho Verbal Não-Assinado)
                    [CONFIANÇA COM DANIEL: NEUTRA]
                    """);

            Escolha escolhaAuto7 = new Escolha("""
                    [3] Bater as mãos com força na mesa de metal e encarar o homem a poucos centímetros:
                        "Você tem três segundos para me dar esse nome antes que eu perca a paciência!"
                    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -1))), """
                    O homem se encolhe na cadeira e grita o nome do assessor direto do vice-prefeito. 
                    Daniel intervém imediatamente, segurando seu ombro e afastando você da mesa 
                    para evitar uma agressão física.
                    
                    (Obteve Pista: Nome do Assessor)
                    [CONFIANÇA COM DANIEL: -1]
                    """);

            Escolha escolhaAuto8 = new Escolha("""
                    [4] Olhar para Daniel e deixar que o detetive faça a pergunta decisiva sobre a ordem:
                        "Daniel, mostre a ele o que acontece com quem cobre os crimes do alto escalão."
                    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
                    Daniel assume o comando com calma profissional. O suspeito, sentindo a 
                    pressão da autoridade policial oficial, entrega o local e o horário exatos 
                    onde entregaria o restante do material queimado.
                    
                    (Obteve Pista: Ponto de Encontro do Transfuga)
                    [CONFIANÇA COM DANIEL: +1]
                    """);

            Escolha escolhaAuto9 = new Escolha("""
                    [1] "Vamos montar uma operação oficial com o que temos e grampear o gabinete do vice-prefeito."
                    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 1))), """
                    Daniel acena positivamente com a cabeça. Ele garante que usará sua influência 
                    para conseguir um mandado de busca discreto sem acionar os alertas da prefeitura.
                    
                    [CONFIANÇA COM DANIEL: +1 | ROTA ATIVADA: OPERAÇÃO CONJUNTA]
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
                    """, new ArrayList<>(List.of(aumentaViolencia, new Efeito(daniel, -2))), """
                    Daniel põe a mão no coldre e avisa em tom ameaçador que não hesitará em te prender 
                    se você tentar fazer justiça com as próprias mãos em Santa Aurora.
                    
                    [CONFIANÇA COM DANIEL: -2 | ROTA ATIVADA: VIGILANTE PERSEGUIDO]
                    """);

            Escolha escolhaAuto12 = new Escolha("""
                    [4] "Me passe o acesso ao arquivo morto do caso e vamos dividir as tarefas de investigação."
                    """, new ArrayList<>(List.of(aumentaRazao, new Efeito(daniel, 2))), """
                    Daniel retira uma cópia da chave do arquivo do bolso e entrega na sua mão. 
                    Ele demonstra confiança total na sua capacidade de conduzir o caso ao seu lado.
                    
                    [CONFIANÇA COM DANIEL: +2 | ROTA ATIVADA: PARCERIA TOTAL]
                    """);

            Cena cenaRota3A = new Cena("ato2_rota3a");
            // =========================================================================
            // CENA 1 — A PRESSÃO NO BECO
            // =========================================================================
            cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                    ===============================================================================
                    ATO II — O CONFRONTO NO BECO (ROTA: 3A - REVISADO E CONECTADO)
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

            // =========================================================================
            // CENA 2 — A TÁTICA DE INTERROGATÓRIO
            // =========================================================================
            cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                    ===============================================================================
                    PONTE NARRATIVA I — DO BECO À SALA DE INTERROGATÓRIO
                    ===============================================================================
                    
                    O suspeito é arrastado para dentro da delegacia sob o olhar curioso dos policiais 
                    de plantão. A água da chuva escorre de suas roupas, deixando um rastro escuro no 
                    piso de linóleo gasto do corredor. 
                    
                    Daniel empurra a porta de carvalho pesado da Sala de Interrogatório 02. O ambiente 
                    é frio, iluminado por uma única lâmpada fluorescente que zume no teto. O cheiro de 
                    fumo de corda e tinta fresca domina o ar. 
                    
                    O suspeito é empurrado para a cadeira de ferro fixada no chão. Suas algemas tilintam 
                    contra a mesa de metal. Daniel encosta-se na parede ao lado do espelho falso, cruza 
                    os braços e faz um sinal com a cabeça, passando a condução do questionamento a você.
                    
                    ===============================================================================
                    CENA 2: Sala de Interrogatório 02 / 01h45
                    ===============================================================================
                    
                    O barulho da chuva batendo na janela alta retumba na sala privativa. O suspeito 
                    respira fundo, olhando fixamente para a superfície espelhada da mesa.
                    
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

            // =========================================================================
            // CENA 3 — O FECHAMENTO COM DANIEL
            // =========================================================================
            cenaRota3A.adicionaDialogo(new Dialogo(narrador, """
                    ===============================================================================
                    PONTE NARRATIVA II — O CONFRONTO DE PLANOS NO CORREDOR
                    ===============================================================================
                    
                    Daniel tranca a porta da Sala de Interrogatório e entrega a chave para o guarda de plantão. 
                    
                    Vocês dois caminham em silêncio pelo corredor até a saída dos fundos da delegacia. O vapor 
                    da chuva acumulada no asfalto sobe enquanto os primeiros vestígios do amanhecer começam a 
                    rasgar a névoa densa que cobre os morros de Santa Aurora.
                    
                    Daniel tira um maço amassado do bolso, acende um cigarro com o fogo protegido pela mão e 
                    solta a fumaça azulada contra o vento frio. Ele encara você com os olhos fundos de quem 
                    não dorme há 48 horas.
                    
                    ===============================================================================
                    CENA 3: Saída dos Fundos da Delegacia / 02h15
                    ===============================================================================
                    
                    DANIEL:
                    "As peças estão se encaixando... e a imagem que está se forming é pior do que 
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
    }

