# Jogo Narrativo Interativo em Java
EXA863 - MI PROGRAMAÇÃO

Componentes da dupla:
* Levi Sena (https://github.com/bucolismo)
* Luis Felipe Batista (https://github.com/LuisB4tista)

Tutor: Roberto Almeida Bittencourt

Universidade Estadual de Feira de Santana (UEFS) — 2026.2

---

## 1. Introdução

Jogos narrativos interativos são aplicações em que o jogador conduz uma história por meio de escolhas, e em que cada decisão altera o rumo da trama, a relação com os personagens e o desfecho final. Por trás da experiência de leitura, existe um sistema que precisa controlar estado, regras de acesso, consequências e persistência de dados de forma organizada.

Este projeto teve como objetivo o desenvolvimento de um jogo narrativo interativo em linguagem Java, no estilo *visual novel*/RPG narrativo, seguindo os princípios da Programação Orientada a Objetos (POO) e o padrão arquitetural **MVC** (*Model-View-Controller*). Não foi utilizada nenhuma *engine* de jogos pronta: toda a lógica de narrativa, relacionamentos, atributos, recursos e finais foi implementada do zero.

O desenvolvimento foi dividido em três fases:

* **Fase 1:** jogo jogável pelo terminal, com menu, protagonista configurável, ao menos 10 cenas, escolhas com consequências, relacionamentos, atributos, recursos e 3 finais distintos;
* **Fase 2:** sistema de persistência em **JSON**, com múltiplos *slots* de save, salvamento manual e automático, sobrescrita, exclusão, configurações persistentes e exceções customizadas;
* **Fase 3:** interface gráfica com **JavaFX**, preservando o MVC e aplicando padrões de projeto GoF.

[PREENCHER: 1 ou 2 frases sobre a história/ambientação do jogo, sem spoilers dos finais.]

---

## 2. Especificação do Projeto

| Item | Valor |
|---|---|
| Linguagem | Java 17 ou superior |
| Interface (Fases 1 e 2) | Terminal (console) |
| Interface (Fase 3) | JavaFX |
| Persistência | Arquivos JSON - Biblioteca: Gson |
| Testes de unidade | JUnit 4/5 |
| Gerenciador de build | Maven |
| IDE de desenvolvimento | IntelliJ IDEA |
| Controle de versão | Git / GitHub |

---

## 3. Fundamentação Teórica

### 3.1. Programação Orientada a Objetos

O projeto foi modelado com classes que representam as entidades do domínio do jogo (protagonista, personagens não jogáveis, cenas, escolhas, estado da partida), aplicando os conceitos de encapsulamento, abstração, herança e polimorfismo vistos em sala. [PREENCHER: citar onde herança/polimorfismo/interfaces foram usados de fato no código.]

### 3.2. Padrão MVC

A separação em três camadas é o eixo da arquitetura:

* **Model:** guarda os dados e as regras do jogo (protagonista, NPCs, cenas, estado da partida), sem saber nada sobre como serão exibidos;
* **View:** responsável apenas pela exibição e pela captura de entradas do jogador (terminal na Fase 1/2, JavaFX na Fase 3);
* **Controller:** recebe as ações da View, aplica as regras sobre o Model e decide o que será exibido em seguida.

### 3.3. Persistência com JSON

JSON é um formato textual leve e legível, adequado para representar o estado de uma partida (campos simples, listas e objetos aninhados). Ele foi utilizado em dois pontos do projeto: na **definição das cenas** (carregadas por `CarregadorDeCenas`) e no **salvamento das partidas** e configurações do jogador. A escolha por JSON em vez de XML foi motivada pela simplicidade de leitura e escrita.


### 3.4. Padrões de projeto (GoF)

[PREENCHER na Fase 3: descrever os 3 padrões GoF aplicados — nome, onde foi usado no código e qual problema resolveu. Ex.: *Singleton* para ..., *Factory Method* para ..., *Observer* para ...]

---


## 4. Arquitetura

### 4.1. Organização em pacotes

```
src/
├── model/         # entidades e regras do jogo
├── view/          # exibição e entrada do jogador
├── controller/    # fluxo do jogo e orquestração
├── persistencia/  # leitura/escrita de JSON, saves e configurações
├── excecoes/      # exceções customizadas
└── Main.java
test/              # testes de unidade
recursos/cenas/    # arquivos JSON com as cenas
```

> [AJUSTAR a árvore acima para refletir a estrutura real do repositório.]

### 4.2. Diagrama de classes

[INSERIR imagem ou link do diagrama de classes, ex.: `docs/diagrama-de-classes.png`]

### 4.3. Fluxo geral de uma partida

1. O jogador abre o **menu inicial** (Nova partida, Carregar, Instruções, Créditos, Conquistas, Configurações, Sair);
2. Em *Nova partida*, escolhe um personagem salvo (preferência de personagem favorito) ou cria um novo protagonista;
3. O jogo apresenta a **introdução narrativa** e avança pelas cenas;
4. A cada cena, o jogador escolhe entre as opções **liberadas** pelas condições de acesso (atributos, recursos, relacionamentos, decisões anteriores);
5. As escolhas aplicam **efeitos** sobre atributos, recursos e relacionamentos e atualizam o estado da partida;
6. Ao final, é exibido um dos finais disponíveis e o jogador retorna ao menu.

### 4.4. `EstadoPartida` — o estado central do jogo

A classe `EstadoPartida` concentra tudo o que define uma partida em andamento: o protagonista, os NPCs e seus relacionamentos, o ato atual, as rotas seguidas, as decisões tomadas, as regras de bloqueio/efeitos das escolhas e o resumo da jornada. Por reunir todo o estado em um único objeto, ela é o elemento que a Fase 2 serializa para JSON ao salvar e reconstrói ao carregar uma partida.

### 4.5. `CarregadorDeCenas` — cenas em JSON

As cenas do jogo ficam em arquivos JSON, e não dentro do código. O `CarregadorDeCenas` lê esses arquivos e monta os objetos de cena (texto, diálogos, escolhas, condições e efeitos). Com isso, o conteúdo narrativo pode ser alterado sem recompilar o projeto.

---

## 5. Detalhamento das Funcionalidades

### 5.1. Protagonista e atributos

O protagonista é configurado pelo jogador no início da partida e possui [N] atributos variáveis ([LISTAR, ex.: Coragem, Inteligência, ...]), todos iniciando em **0** e evoluindo conforme as escolhas.

### 5.2. Personagens e relacionamentos

O jogo conta com 3 personagens relevantes (Daniel, Helena e Jonas). Os relacionamentos com eles são medidos por **confiança**, em escala de **0 a 100**, iniciando em **50** e subindo ou descendo conforme as ações do jogador.

### 5.3. Recursos gerenciáveis

[PREENCHER: quais recursos o jogo possui — inventário, pontos de ação, energia, dinheiro, pistas, habilidades — e como são consumidos/obtidos.]

### 5.4. Escolhas, consequências e condições de acesso

Cada escolha pode ter **condições de acesso** (por exemplo, exigir um atributo mínimo, um item ou um nível de confiança) que bloqueiam ou liberam opções, além de **efeitos** que alteram o estado da partida. [PREENCHER: dar 1 ou 2 exemplos reais do jogo.] + adicionar o vídeo aqui da restrição de acesso

### 5.5. Finais

<details>
  <summary> Visualizar finais </summary>

  
  O jogo possui 3 finais distintos, ligados às rotas narrativas:
  
  | Final | Rota |
  |---|---|
  | Razão | [PREENCHER: o que leva a este final] |
  | Paranoia | [PREENCHER: o que leva a este final] |
  | Violência | [PREENCHER: o que leva a este final] |

</details>

### 5.6. Sistema de saves (Fase 2)

* Múltiplos *slots* de save;
* Salvamento **manual** e **automático** [PREENCHER: em quais momentos ocorre o automático];
* Sobrescrita e exclusão de saves;
* Tratamento de falhas por **exceções customizadas** [colocar as exceções, talvez a prioridade das exceções]

### 5.7. Configurações persistentes (Fase 2)

Três preferências do jogador são gravadas em JSON e restauradas ao abrir o jogo:

| Preferência | Função |
|---|---|
| Personagem favorito | Após *Nova partida*, permite usar o personagem salvo ou criar um novo |
| Mostrar efeitos das escolhas | Exibe (ou oculta) as consequências numéricas de cada escolha |
| Pausa entre diálogos | Controla o ritmo da exibição dos diálogos |

### 5.8. Conquistas e conteúdo desbloqueável (Fase 2)

O registro de finais alimenta a opção **Conquistas** do menu: uma conquista por final (Razão, Paranoia e Violência) e a conquista **Platina**, concedida ao atingir todos os finais.

### 5.9. Interface gráfica (Fase 3)

[PREENCHER: telas implementadas em JavaFX (menu, cena, escolhas, saves, configurações, conquistas), como a View se comunica com o Controller e como o MVC foi preservado.]

---

## 6. Testes, Limitações e Melhorias Futuras

### 6.1. Testes de unidade

Os testes cobrem [PREENCHER: classes/regras testadas, ex.: efeitos de escolhas, condições de acesso, cálculo de relacionamento, serialização e desserialização do `EstadoPartida`].

Para executar: [COMANDO ou instrução na IDE, ex.: `mvn test`].

### 6.2. Limitações conhecidas

- [PREENCHER: ex.: conteúdo narrativo limitado a N cenas]
- [PREENCHER: ex.: ausência de áudio/imagens na interface]
- [PREENCHER: outras limitações reais]

## 7. Instruções de Reprodução

### Pré-requisitos

* JDK [VERSÃO] instalado;
* JavaFX SDK [VERSÃO] configurado (Fase 3);
* [IDE ou Maven/Gradle].

### Passo a passo

1. Clone o repositório:
   ```
   git clone https://github.com/[USUARIO]/[REPOSITORIO].git
   ```
2. Abra o projeto na IDE [NOME DA IDE] (ou entre na pasta do projeto pelo terminal);
3. Configure o JDK [VERSÃO] e as dependências ([BIBLIOTECA JSON], JavaFX, JUnit);
4. Execute a classe principal `[CAMINHO.DA.Main]`;
5. Para rodar os testes: [COMANDO ou instrução].

> Os arquivos de save e de configuração são gerados em `[PASTA, ex.: saves/]` na primeira execução.

---

## 8. Guia de Uso

### 8.1 Menu inicial

| Opção | Função |
|---|---|
| Nova partida | Inicia uma nova história (usando o personagem favorito ou criando um novo) |
| Carregar partida | Retoma uma partida salva em um dos slots |
| Instruções | Explica como jogar |
| Conquistas | Mostra as conquistas desbloqueadas |
| Configurações | Altera as preferências persistentes |
| Créditos | Exibe a dupla e a disciplina |
| Sair | Encerra o jogo |

> [AJUSTAR as opções conforme o menu real.]

### 8.2 Durante a partida

[PREENCHER: como escolher opções (número/clique), como salvar, como acompanhar atributos/recursos/relacionamentos, como voltar ao menu.]

---

## 9. Vídeos de Demonstração da Execução

Os vídeos a seguir demonstram o funcionamento do jogo.

### 10.1 [TÍTULO, ex.: Menu, criação do protagonista e início da história]

[DESCRIÇÃO CURTA do que o vídeo mostra]

[LINK ou arquivo do vídeo]

### 10.2 [TÍTULO, ex.: Escolhas, consequências e relacionamentos]

[DESCRIÇÃO CURTA do que o vídeo mostra]

[LINK ou arquivo do vídeo]

### 10.3 [TÍTULO, ex.: Saves, configurações e conquistas]

[DESCRIÇÃO CURTA do que o vídeo mostra]

[LINK ou arquivo do vídeo]

### 10.4 [TÍTULO, ex.: Os três finais]

[DESCRIÇÃO CURTA do que o vídeo mostra]

[LINK ou arquivo do vídeo]

> No GitHub, para exibir o vídeo direto no README, edite o README pelo navegador e arraste o arquivo de vídeo para a caixa de edição; o GitHub gera o link `https://github.com/user-attachments/assets/...` automaticamente.

---

## 11. Referências

[PREENCHER no padrão da disciplina/SBC. Exemplos de itens possíveis:]

* Documentação oficial do Java (Oracle).
* Documentação do JavaFX (OpenJFX).
* Documentação da biblioteca JSON utilizada.
* GAMMA, E.; HELM, R.; JOHNSON, R.; VLISSIDES, J. *Padrões de Projeto: Soluções Reutilizáveis de Software Orientado a Objetos*. Bookman.
* Material de aula da disciplina EXA863.
