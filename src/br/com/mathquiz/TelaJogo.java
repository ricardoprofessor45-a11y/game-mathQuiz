package br.com.mathquiz;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class TelaJogo extends JFrame {
    private JTextField campoNome;
    private JLabel labelPergunta;
    private JTextField campoResposta;
    private JLabel labelPontuacao;
    private JLabel labelTempo;
    private JButton botaoIniciar;
    private JButton botaoResponder;

    private Jogo jogo;
    private Timer timer;

    public TelaJogo() {
        jogo = new Jogo();

        setTitle("Math Quiz");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        inicializarComponentes();
        configurarEventos();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout());

        JPanel painelTopo = new JPanel(new GridLayout(2, 2, 10, 10));
        painelTopo.add(new JLabel("Nome do jogador:"));
        campoNome = new JTextField();
        painelTopo.add(campoNome);

        labelPontuacao = new JLabel("Pontuação: 0");
        painelTopo.add(labelPontuacao);

        labelTempo = new JLabel("Tempo: 50");
        painelTopo.add(labelTempo);

        add(painelTopo, BorderLayout.NORTH);

        labelPergunta = new JLabel("Clique em Iniciar para começar", SwingConstants.CENTER);
        labelPergunta.setFont(new Font("Arial", Font.BOLD, 24));
        add(labelPergunta, BorderLayout.CENTER);

        JPanel painelBaixo = new JPanel(new GridLayout(2, 2, 10, 10));
        painelBaixo.add(new JLabel("Sua resposta:"));
        campoResposta = new JTextField();
        campoResposta.setEnabled(false);
        painelBaixo.add(campoResposta);

        botaoIniciar = new JButton("Iniciar");
        painelBaixo.add(botaoIniciar);

        botaoResponder = new JButton("Responder");
        botaoResponder.setEnabled(false);
        painelBaixo.add(botaoResponder);

        add(painelBaixo, BorderLayout.SOUTH);
    }

    private void configurarEventos() {
        botaoIniciar.addActionListener(e -> iniciarJogo());

        botaoResponder.addActionListener(e -> responder());

        campoResposta.addActionListener(e -> responder());
    }

    private void iniciarJogo() {
        String nome = campoNome.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome do jogador.");
            return;
        }

        jogo.iniciarJogo(nome);

        campoNome.setEnabled(false);
        botaoIniciar.setEnabled(false);
        campoResposta.setEnabled(true);
        botaoResponder.setEnabled(true);
        campoResposta.requestFocus();

        atualizarTela();

        timer = new Timer(1000, e -> {
            jogo.diminuirTempo();
            atualizarTela();

            if (!jogo.isJogoAtivo()) {
                timer.stop();
                encerrarJogo();
            }
        });

        timer.start();
    }

    private void responder() {
        if (!jogo.isJogoAtivo()) {
            return;
        }

        String textoResposta = campoResposta.getText().trim();

        if (textoResposta.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite uma resposta.");
            return;
        }

        try {
            int resposta = Integer.parseInt(textoResposta);
            boolean acertou = jogo.verificarResposta(resposta);

            if (acertou) {
                JOptionPane.showMessageDialog(this, "Resposta correta!");
            } else {
                JOptionPane.showMessageDialog(this, "Resposta errada!");
            }

            campoResposta.setText("");
            atualizarTela();
            campoResposta.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Digite um número válido.");
        }
    }

    private void atualizarTela() {
        if (jogo.getPerguntaAtual() != null) {
            labelPergunta.setText(jogo.getPerguntaAtual().getEnunciado());
        }

        if (jogo.getJogador() != null) {
            labelPontuacao.setText("Pontuação: " + jogo.getJogador().getPontuacao());
        }

        labelTempo.setText("Tempo: " + jogo.getTempoRestante());
    }

    private void encerrarJogo() {
        campoNome.setEnabled(true);
        botaoIniciar.setEnabled(true);
        campoResposta.setEnabled(false);
        botaoResponder.setEnabled(false);

        String nomeJogador = jogo.getJogador().getNome();
        int pontuacaoFinal = jogo.getJogador().getPontuacao();

        labelPergunta.setText("Fim de jogo!");

        JOptionPane.showMessageDialog(this,
                "Tempo esgotado!\nJogador: " + nomeJogador +
                "\nPontuação final: " + pontuacaoFinal);

        campoResposta.setText("");
    }
}