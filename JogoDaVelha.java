import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JogoDaVelha extends JFrame {

    private final Celula[] celulas = new Celula[9];
    private final JLabel statusLabel = new JLabel();
    private String jogadorAtual = "X";
    private boolean jogoFinalizado = false;

    public JogoDaVelha() {
        super("Jogo da Velha");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setBackground(Tema.FUNDO);
        setLayout(new BorderLayout());

        add(criarPainelTopo(), BorderLayout.NORTH);
        add(criarPainelTabuleiro(), BorderLayout.CENTER);
        add(criarPainelInferior(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel criarPainelTopo() {
        JPanel painel = new JPanel();
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(new EmptyBorder(24, 24, 12, 24));
        
        JLabel titulo = new JLabel("JOGO DA VELHA");
        titulo.setFont(Tema.FONTE_TITULO);
        titulo.setForeground(Tema.TEXTO);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        statusLabel.setFont(Tema.FONTE_STATUS);
        statusLabel.setForeground(Tema.TEXTO_FRACO);
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel container = new JPanel(new GridLayout(2, 1));
        container.setBackground(Tema.FUNDO);
        container.add(titulo);
        container.add(statusLabel);

        painel.add(container);
        atualizarStatus();

        return painel;
    }

    private JPanel criarPainelTabuleiro() {
        JPanel painel = new JPanel(new GridLayout(3, 3, 10, 10));
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(new EmptyBorder(0, 24, 12, 24));

        for (int i = 0; i < 9; i++) {
            int indice = i;
            Celula celula = new Celula();
            celula.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    clicarCelula(indice);
                }
            });
            celulas[i] = celula;
            painel.add(celula);
        }

        return painel;
    }

    private JPanel criarPainelInferior() {
        JPanel painel = new JPanel();
        painel.setBackground(Tema.FUNDO);
        painel.setBorder(new EmptyBorder(0, 24, 24, 24));

        JButton botaoReiniciar = new JButton("Novo jogo");
        botaoReiniciar.setFont(Tema.FONTE_BOTAO);
        botaoReiniciar.setForeground(Tema.FUNDO);
        botaoReiniciar.setBackground(Tema.TEXTO);
        botaoReiniciar.setFocusPainted(false);
        botaoReiniciar.setBorderPainted(false);
        botaoReiniciar.setOpaque(true);
        botaoReiniciar.setPreferredSize(new Dimension(140, 36));
        botaoReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reiniciarJogo();
            }
        });

        painel.add(botaoReiniciar);
        return painel;
    }

    private void clicarCelula(int indice) {
        if (jogoFinalizado || !celulas[indice].getValor().isEmpty()) {
            return;
        }

        celulas[indice].definirValor(jogadorAtual);

        int[] combinacaoVencedora = verificarVitoria(jogadorAtual);
        if (combinacaoVencedora != null) {
            jogoFinalizado = true;
            for (int indiceCelula : combinacaoVencedora) {
                celulas[indiceCelula].destacarVitoria();
            }
            statusLabel.setText(jogadorAtual + " venceu!");
            statusLabel.setForeground(jogadorAtual.equals("X") ? Tema.X : Tema.O);
            return;
        }

        if (tabuleiroCheio()) {
            jogoFinalizado = true;
            statusLabel.setText("Empate!");
            statusLabel.setForeground(Tema.TEXTO_FRACO);
            return;
        }

        jogadorAtual = jogadorAtual.equals("X") ? "O" : "X";
        atualizarStatus();
    }

    private void atualizarStatus() {
        statusLabel.setText("Vez de: " + jogadorAtual);
        statusLabel.setForeground(jogadorAtual.equals("X") ? Tema.X : Tema.O);
    }

    private int[] verificarVitoria(String jogador) {
        int[][] combinacoes = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
        };

        for (int[] combinacao : combinacoes) {
            if (celulas[combinacao[0]].getValor().equals(jogador)
                    && celulas[combinacao[1]].getValor().equals(jogador)
                    && celulas[combinacao[2]].getValor().equals(jogador)) {
                return combinacao;
            }
        }
        return null;
    }

    private boolean tabuleiroCheio() {
        for (Celula celula : celulas) {
            if (celula.getValor().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void reiniciarJogo() {
        for (Celula celula : celulas) {
            celula.limpar();
        }
        jogadorAtual = "X";
        jogoFinalizado = false;
        atualizarStatus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JogoDaVelha().setVisible(true);
            }
        });
    }
}