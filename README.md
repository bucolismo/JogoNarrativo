# Jogo Narrativo

Projeto desenvolvido em Java para a disciplina de EXA 863 - MI - Programação.

Jogo narrativo em modo texto (terminal). O jogador cria um protagonista e toma decisões ao longo de diálogos organizados em capítulos/cenas.

Este é um jogo narrativo de investigação em que o jogador acompanha a história da cidade de Santa Aurora e toma decisões que alteram seu desenvolvimento. As escolhas alteram atributos do protagonista, o inventário e o nível de confiança com NPCs, e podem liberar ou bloquear outras escolhas mais à frente. Diferentes finais são possíveis.

## Estrutura

O projeto utiliza o padrão MVC e possui:

- Personagens e NPCs
- Cenas, diálogos e escolhas
- Atributos de Razão, Paranoia e Violência
- Sistema de confiança dos NPCs
- Inventário e itens
- Efeitos das escolhas
- Requisitos para determinadas escolhas (condição de acesso)
- Testes de unidade com JUnit

## Execução

Com Maven (Java 17 ou superior), na raiz do projeto:

```bash
mvn compile exec:java      # roda o jogo
mvn test                   # roda os testes de unidade
```

Sem Maven, é preciso o jar do Gson no classpath (exemplo no Linux):

```bash
javac -encoding UTF-8 -cp gson-2.10.1.jar -d out src/*.java
java -Dfile.encoding=UTF-8 -cp out:resources:gson-2.10.1.jar Partida
```

## Ponto de entrada

A classe Partida inicia o jogo pelo terminal e monta as peças do MVC: cria a Entrada, o Menu (View) e o RepositorioJson, entrega os dois últimos ao Controlador e chama o menuInicial().

## Testes

Testes de unidade em JUnit 5, na pasta test/. Cobrem Atributo, NPC, Inventario, Protagonista, Efeito, Dialogo, Entrada, CatalogoDeItens e a integridade da história (HistoriaTest: todo item, atributo e efeito usado nas cenas precisa existir no motor). Classes de saída (Menu) e o conteúdo narrativo em si não possuem testes dedicados.

Sem Maven, com o JUnit Console Launcher (`junit-platform-console-standalone`) e o Gson:

```bash
javac -encoding UTF-8 -cp junit-platform-console-standalone.jar:gson-2.10.1.jar -d out src/*.java test/*.java
java -jar junit-platform-console-standalone.jar execute -cp out:resources:gson-2.10.1.jar --scan-classpath
```

## Cenas em JSON

O conteúdo da história do prólogo até o Ato III fica em arquivos JSON na pasta `resources/cenas/`, lidos pela classe `CarregadorDeCenas`. Os Atos IV, V, VI e os finais ainda são montados em Java no `ConstrutorDeCenas`.

- Para mudar uma fala, escolha, efeito ou requisito basta editar o JSON; não é preciso recompilar o código Java.
- O carregador valida o arquivo ao montar a cena (personagens, tipos de efeito, itens do `CatalogoDeItens`, atributos e limites de confiança) e informa o arquivo, o diálogo e a escolha onde está o erro (`CenaInvalidaException`).
- Efeitos disponíveis: `PREDEFINIDO` (reaproveita um efeito pelo nome), `ATRIBUTO`, `RELACAO` (confiança de um NPC), `ITEM` e `GAME_OVER`. O formato completo está documentado no comentário da classe `CarregadorDeCenas`.
- O `HistoriaTest` carrega todas as cenas (JSON e Java) e confere que nada referencia item, atributo, personagem ou efeito inexistente.

## Configurações do jogador (Fase 2)

O menu principal tem a opção **Configurações**, com três preferências que são mantidas entre as execuções do jogo:

1. **Personagem favorito:** ao criar um personagem em uma nova partida, o jogador pode guardá-lo como favorito. Nas próximas partidas, o jogo oferece usar o favorito em vez de pedir nome, idade e gênero de novo. Pede confirmação antes de substituir um favorito existente, e o favorito pode ser excluído na tela de configurações (também com confirmação).
2. **Mostrar mudanças de atributos e relações após cada escolha:** quando ligada, imprime linhas como `[Razão +3]` e `[Relação com Jonas -10]` com o que mudou depois de cada escolha. Só aparece o que de fato mudou. Vem desligada por padrão.
3. **Pausa entre diálogos:** quando ligada, o jogo espera o jogador apertar ENTER depois de cada diálogo que não tem escolhas. Vem desligada por padrão.

As três ficam em `saves/config.json` (classes `Configuracoes` e `RepositorioDeConfiguracoes`), separadas dos saves e do progresso. Se o arquivo estiver corrompido ou inválido, o jogo avisa e usa os valores padrão. A ideia do personagem favorito e da opção de mostrar as mudanças das escolhas, e a primeira versão de ambas, foram integradas à versão unificada (guardadas nas configurações, e não no estado da partida, para valerem entre execuções).

## Registro de finais e conquistas (Fase 2)

- O jogo tem 3 finais (`FinalDoJogo`): A Balança (Razão), A Névoa se Levanta (Paranoia) e O Preço do Sangue (Violência). O final é decidido na escolha final do Ato VI.
- Quando o jogador termina uma partida em um final, o `ProgressoGlobal` registra o final alcançado e desbloqueia a conquista correspondente (`Conquista`). Ao alcançar os 3 finais, desbloqueia também a **Platina**, que fica oculta ("???" com uma dica) até lá.
- O progresso vale para o jogo todo: não some ao apagar saves nem ao começar uma nova partida. É gravado em `saves/progresso.json` (`RepositorioDeProgresso`) e lido ao abrir o jogo. Se o arquivo estiver corrompido, o jogo avisa e começa com o progresso vazio.
- No menu principal, a opção **Conquistas** mostra os finais alcançados e o estado de cada conquista, e permite redefinir tudo (com confirmação).
- Os marcadores de rota do Ato V (itens "Rota Ativada") **não** são usados no registro: eles indicam o caminho escolhido no Ato IV, e o final depende da escolha do Ato VI.

## Saves (Fase 2)

- Estado da partida: tudo o que muda durante o jogo fica em `EstadoPartida` (protagonista, confiança dos NPCs, ato atual, rotas e decisões). O `Controlador` só faz a ponte entre o jogador, o estado e os saves.
- Pontos de salvamento: ao entrar em cada ato (`Ato.ehPontoDeSalvamento`). Nesses pontos o jogo salva automaticamente no slot automático e oferece salvar manualmente em um dos 3 slots.
- Formato: um arquivo JSON por slot, na pasta `saves/` (`autosave.json`, `slot1.json` a `slot3.json`). Os arquivos são gravados direto; se um save estiver corrompido ao carregar, o jogo avisa em vez de quebrar.
- Menu: "Continuar" retoma o save mais recente, "Carregar partida" lista os slots (nome, data/hora, ato e resumo) e permite carregar ou excluir. Sobrescrever e excluir pedem confirmação.
- Dados inválidos: todo save é validado ao carregar. Falhas viram `SaveException`, `CarregamentoException` ou `DadosInvalidosException`, e o jogo mostra a mensagem em vez de quebrar.

## Regras de balanceamento

- Atributos do protagonista (Razão, Paranoia, Violência e Provas) começam em 0 e vão até 100.
- A confiança de cada NPC começa em 50 e varia de 0 a 100 conforme as ações do jogador.
- Escolhas com efeito `GAME_OVER` encerram a partida imediatamente e voltam ao menu.

Discentes:

Levi Sena Andrade

Luis Felipe Santana Batista
