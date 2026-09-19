import java.util.ArrayList;

public class ConstrutorDeCenas {

    private Cena cenaAtual;
    private String cenaID;

    public ConstrutorDeCenas() {
        this.cenaAtual = new Cena();
    }
    //Se o valor dos aumentos forem padrão é melhor declarar os efeitos primeiro
    Efeito aumentaRazao =  new Efeito("ATRIBUTO", "Razão", 3);
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

    public Cena criarAtoI(Protagonista protagonista,Personagem narrador, Personagem jonas, Personagem daniel){
        Cena atoI= new Cena();

        atoI.adicionaDialogo(new Dialogo(narrador, """
                [CENA: Interior de um ônibus de viagem. Chuva bate contra a janela.]
                
                O balanço do veículo é constante. Do lado de fora, a névoa de Santa Aurora\s
                engole a paisagem familiar. Você observa seu próprio reflexo no vidro molhado.
                
                > PROTAGONISTA:
                 "Eu não lembrava da cidade parecer tão pequena. Talvez fosse eu que tivesse\s
                 mudado."
                
                *(O ônibus desacelera até parar com um chiado nos freios. As portas se abrem.)*
                
                Você desce na plataforma da rodoviária deserta. O ar frio da noite atinge\s
                seu rosto. No seu bolso, o celular vibra.
                
                [NOVA MENSAGEM - DANIEL]
                "Quando chegar, me procure na delegacia."
                
                Você guarda o aparelho. Um peso no peito lembra o motivo de estar aqui:
                """));

        atoI.adicionaDialogo(new Dialogo(narrador, """
                Background do passado do protagonista +Gabriel"""));

        atoI.adicionaDialogo(new Dialogo(protagonista, """
        Você guarda o celular no bolso. O eco das vozes ainda vibra nos seus ouvidos,
        mas o peso do medalhão de bronze no seu bolso — o mesmo que seu irmão te deu
        na infância — traz um choque doloroso de realidade.
        
        Você precisa decidir como dar o primeiro passo nesta cidade.
        """,
                new Escolha("""
                [1] Analisar o mapa da cidade na parede da estação para calcular o trajeto
                    mais rápido até a delegacia e entender onde a quinta vítima foi achada.""", aumentaRazao, """
                        Você se aproxima do mapa manchado de umidade. Com calma, cruza as informações
                        			da mensagem de Daniel com os locais de isolamento policial que ouviu no rádio.
                        
                        			> VOZ :
                        			> "O tempo entre a rodoviária e o centro é de dez minutos a pé. Se cortarmos\s
                        			> pela travessa, evitamos a névoa densa e chegamos com fatos, não com 				suposições."
                        """),
                new Escolha("""
                [2] Observar os arredores da plataforma. Aquele sujeito encostado no poste
                    parece estar vigiando quem desce do ônibus.""", aumentaParanioa, """
                        Você ajusta o gola do casaco e disfarça o olhar. O homem no poste acende um
                        			cigarro, mas os olhos dele acompanham cada movimento das suas mãos.
                        
                        			VOZ
                        			"Ele sabe quem você é. A notícia da sua volta já circulou antes mesmo de o
                        			ônibus estacionar. Ninguém fica parado no frio à meia-noite sem um motivo.\""""),
                new Escolha("""
                [3] Segurar com força o medalhão no bolso, ignorar a chuva e marchar firme
                    direto para a rua, pronto para encarar qualquer um que cruzar seu caminho.""", aumentaViolencia, """
                        Você ignora o mapa, a névoa e as pessoas. Seus passos ecoam pesados contra
                        			o paralelepípedo molhado, cortando a noite com determinação cega.
                        
                        			VOZ DA VIOLÊNCIA:
                        			"Deixe que vejam você chegando. Se alguém nesta cidade acha que pode brincar
                        			com a memória do seu irmão, vai descobrir do pior jeito que você voltou."
                        """)
        ));
        atoI.adicionaDialogo(new Dialogo(protagonista, """
                A caminho do seu destino, uma figura surge da névoa. É o Velho Jonas, um antigo 
                conhecido da família que trabalhava com seu irmão. Ele parece assustado e segura 
                uma lanterna com a mão trêmula.
                
                MORADOR (JONAS):
                "Garoto?... É você mesmo? Pelas barbas de Deus, você não devia ter voltado. 
                Aquele assunto do seu irmão... ele estava mexendo onde não devia!"
                """,
                                new Escolha("""
                                    [1] "Jonas, acalme-se. Me diga exatamente o que ele estava fazendo nos últimos 
                                        dias e quem foi a última pessoa a falar com ele."
                                    """, aumentaRazao, """
                                            Jonas solta um suspiro pesado, ajeitando o casaco. Sua postura firme o acalma.
                                            
                                            MORADOR (JONAS):
                                            > "Ele... ele estava na antiga fábrica desativada na semana passada. Dizia que 
                                            > achou registros antigos da prefeitura que não batiam. Falou que ia encontrar 
                                            > o Daniel para entregar tudo..."
                                            
                                            (Pista de Jonas adicionada ao inventário)
                                            """),
                                new Escolha("""
                                    [2] "Quem te mandou aqui para me avisar? Você está vigiando a entrada da cidade 
                                        para quem?"
                                    """, aumentaParanioa, """
                                            Jonas recua dois passos, assustado com o seu olhar desconfiado.
                                            
                                            MORADOR (JONAS):
                                            > "Ninguém me mandou! Eu só... eu vi a movimentação no posto de polícia mais cedo.
                                            > Tinha gente estranha lá, garoto. Gente que não é da cidade perguntando por você!"
                                            """),
                                new Escolha("""
                                    [3] Segurar Jonas pelo colarinho do casaco: "Chega de rodeios, velho! Fala de 
                                        uma vez o que aconteceu com ele antes que eu perca a paciência!"
                                    """, aumentaViolencia, """
                                            Jonas treme, arregalando os olhos com o impacto. Ele aponta para o beco da delegacia.
                                            
                                            MORADOR (JONAS):
                                            > "Calma, garoto! Eu não sei de nada! Só sei que o Daniel tá te esperando no beco
                                            > dos fundos da delegacia... e ele não tá sozinho!"
                                            """)
        ));
        atoI.adicionaDialogo(new Dialogo(protagonista, """
            Ao se aproximar do bloco policial, a iluminação da rua falha. Você percebe que 
            a entrada principal está trancada. Há um rastro de documentos espalhados na lama 
            perto da porta de serviço, além de uma figura caída na penumbra do beco.
            
            > VOZ :
            > "Examine a cena primeiro. Recolha os papéis e verifique os sinais vitais 
            > antes de tomar qualquer atitude impulsiva."
            
            > VOZ  :
            > "É uma emboscada clara. Alguém deixou essa cena montada para ver como você 
            > reage. Mantenha as costas na parede e observe as sombras."
            
            > VOZ :
            > "Esqueça a cautela. Avance rápido, segure quem estiver ali e garanta que 
            > ninguém saia desse beco sem te dar respostas."
            """,
                new Escolha("""
                    [1] Agachar-se para recolher as provas na lama e checar a pessoa no chão de 
                        forma metódica.
                    """, aumentaRazao, """
                    Você analisa as páginas molhadas: são cópias do laudo do seu irmão com rasuras 
                    propositais. A pessoa no chão é apenas um manequim usado para chamar atenção. 
                    Você obteve o documento.
                    """),
                new Escolha("""
                    [2] Dar a volta por trás dos caixotes, usando a névoa como cobertura para 
                        analisar o beco sem ser visto.
                    """, aumentaParanioa, """
                            Sua cautela revela um olheiro escondido no topo da escada de incêndio, com uma 
                            câmera apontada para o beco. Ao notar que foi descoberto, o sujeito foge, 
                            deixando para trás uma pista sobre quem está monitorando a polícia.
                            
                            (Pista Manequim adicionada ao inventário)
                            """),
                new Escolha("""
                    [3] Marchar direto em direção à sombra na penumbra, pronto para o confronto.
                    """, aumentaViolencia, """
                            Você arromba a porta de serviço com um chute, surpreendendo um suspeito que 
                            tentava queimar o restante dos arquivos do seu irmão. Você o imobiliza antes 
                            que as evidências sejam destruídas.
                            
                            (Eram evidências falsas)
                            """)
        ));
    return atoI;
    }
}
