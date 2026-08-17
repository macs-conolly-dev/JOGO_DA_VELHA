# 🎮 Jogo da Velha

Um jogo da velha (tic-tac-toe) clássico para 2 jogadores, feito em **Java puro** com interface gráfica (Swing) — sem nenhuma biblioteca externa.

---

## ✨ Funcionalidades

- 🟢 Tabuleiro 3x3 interativo, com destaque ao passar o mouse
- ❌⭕ Alternância automática entre os jogadores X e O
- 🎬 Animação suave ao marcar uma jogada (o símbolo "cresce" ao aparecer)
- 🏆 Detecção automática de vitória (linha, coluna ou diagonal), com destaque dourado animado na combinação vencedora
- 🤝 Detecção de empate
- 🔄 Botão "Novo jogo" para reiniciar sem fechar o programa

---

## 🛠️ Tecnologias

- **Java** (sem dependências externas)
- **Swing** — para toda a interface gráfica
- `javax.swing.Timer` — usado para as animações

---

## ▶️ Como rodar

Pré-requisito: ter o **JDK** instalado ([java -version](https://www.oracle.com/java/technologies/downloads/) para verificar).

```bash
javac *.java
java JogoDaVelha
```

---

## 📁 Estrutura do projeto

```
jogo-da-velha-java/
├── JogoDaVelha.java   # tela principal e regras do jogo
├── Celula.java        # botão animado de cada casinha do tabuleiro
├── Tema.java           # cores e fontes usadas na interface
└── README.md
```

---

## 🚀 Possíveis melhorias futuras

- 🤖 Modo contra o computador (IA simples)
- 📊 Placar acumulado entre partidas
- 🔊 Efeitos sonoros

---

## 👤 Autor

Feito por **Matheus Conolly** — [LinkedIn](https://www.linkedin.com/in/matheus-conolly-dev/) · [GitHub](https://github.com/macs-conolly-dev)
