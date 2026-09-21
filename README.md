Jogo Narrativo

Projeto desenvolvido em Java para a disciplina de Algoritmos II.

Jogo narrativo em modo texto (terminal). O jogador cria um protagonista e toma decisões ao longo de diálogos organizados em capítulos/cenas. Este é um jogo narrativo de investigação em que o jogador acompanha a história de Santa Aurora e toma decisões que alteram seu desenvolvimento. As escolhas alteram atributos do protagonista, o inventário e o nível de confiança com NPCs, e podem liberar ou bloquear outras escolhas mais à frente. Diferentes finais são possíveis.

O projeto utiliza o padrão MVC e possui:

Personagens e NPCs
Cenas, diálogos e escolhas
Atributos de Razão, Paranoia e Violência
Sistema de confiança dos NPCs
Inventário e itens
Efeitos das escolhas
Requisitos para determinadas escolhas (condição de acesso)
Testes unitários com JUnit

Todos os arquivos .java estão em src/.

cd src
javac *.java
java Partida

Atenção:
O ponto de entrada é a classe Partida, que inicia o jogo pelo terminal, instancia Controlador e chama menuInicial().
