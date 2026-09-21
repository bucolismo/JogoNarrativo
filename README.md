# Jogo Narrativo

Projeto desenvolvido em Java para a disciplina de EXA 863 - MI - Programação.

Jogo narrativo em modo texto (terminal). O jogador cria um protagonista e toma decisões ao longo de diálogos organizados em capítulos/cenas.

Este é um jogo narrativo de investigação em que o jogador acompanha a história de Santa Aurora e toma decisões que alteram seu desenvolvimento. As escolhas alteram atributos do protagonista, o inventário e o nível de confiança com NPCs, e podem liberar ou bloquear outras escolhas mais à frente. Diferentes finais são possíveis.

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

Todos os arquivos `.java` estão em `src/`.

```bash
cd src
javac *.java
```

## Ponto de entrada

A classe Partida inicia o jogo pelo terminal, instancia o Controlador e chama o menuInicial().

## Testes

Testes de unidade em JUnit, na pasta test/, cobrindo as classes com lógica de estado: Atributo, NPC, Inventario, Protagonista. Classes de entrada/saída (Menu, Entrada) e de conteúdo narrativo (ConstrutorDeCenas, Escolha) não possuem testes de unidade dedicados.

Discentes:

Levi Sena Andrade
Luis Felipe Santana Batista
