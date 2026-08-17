import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Celula extends JButton {

    private String valor = "";
    private double escala = 0.0;
    private boolean travada = false;
    private Timer timerAnimacao;

    public Celula() {
        setPreferredSize(new Dimension(90, 90));
        setBackground(Tema.CELULA);
        setForeground(Tema.TEXTO);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(true);
        setFont(Tema.FONTE_CELULA);
        setText("");

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (valor.isEmpty() && !travada) {
                    setBackground(Tema.CELULA_HOVER);
                }
            }

            public void mouseExited(MouseEvent e) {
                if (valor.isEmpty() && !travada) {
                    setBackground(Tema.CELULA);
                }
            }
        });
    }

    public String getValor() {
        return valor;
    }

    public void definirValor(String novoValor) {
        if (!valor.isEmpty()) {
            return;
        }

        valor = novoValor;
        setForeground(novoValor.equals("X") ? Tema.X : Tema.O);
        animarEntrada();
    }

    public void limpar() {
        valor = "";
        escala = 0.0;
        travada = false;
        setBackground(Tema.CELULA);
        repaint();
    }

    public void destacarVitoria() {
        travada = true;
        final Color inicio = getBackground();
        final Color destino = Tema.DESTAQUE_VITORIA;
        final int passosTotais = 20;
        final int[] passoAtual = {0};

        Timer timer = new Timer(15, null);
        timer.addActionListener(e -> {
            passoAtual[0]++;
            float t = Math.min(1f, passoAtual[0] / (float) passosTotais);
            setBackground(misturarCores(inicio, destino, t));
            if (t >= 1f) {
                timer.stop();
            }
        });
        timer.start();
    }

    private Color misturarCores(Color a, Color b, float t) {
        int r = (int) (a.getRed() + (b.getRed() - a.getRed()) * t);
        int g = (int) (a.getGreen() + (b.getGreen() - a.getGreen()) * t);
        int bl = (int) (a.getBlue() + (b.getBlue() - a.getBlue()) * t);
        return new Color(r, g, bl);
    }

    private void animarEntrada() {
        escala = 0.0;
        if (timerAnimacao != null && timerAnimacao.isRunning()) {
            timerAnimacao.stop();
        }

        timerAnimacao = new Timer(12, null);
        timerAnimacao.addActionListener(e -> {
            escala += 0.12;
            if (escala >= 1.0) {
                escala = 1.0;
                timerAnimacao.stop();
            }
            repaint();
        });
        timerAnimacao.start();
    }

    private double facilitarSaida(double t) {
        return 1 - Math.pow(1 - t, 3);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (valor.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        float escalaAnimada = (float) (0.6 + 0.4 * facilitarSaida(escala));
        Font fonteEscalada = Tema.FONTE_CELULA.deriveFont(Tema.FONTE_CELULA.getSize2D() * escalaAnimada);
        g2.setFont(fonteEscalada);
        g2.setColor(getForeground());

        FontMetrics fm = g2.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(valor)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

        g2.drawString(valor, x, y);
        g2.dispose();
    }
}