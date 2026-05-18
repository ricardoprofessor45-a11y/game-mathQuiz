package br.com.mathquiz;

public class Jogo {
    private Jogador jogador;
    private Pergunta perguntaAtual;
    private int tempoRestante;
    private boolean jogoAtivo;

    public Jogo() {
        this.tempoRestante = 50;
        this.jogoAtivo = false;
    }

    public void iniciarJogo(String nomeJogador) {
        this.jogador = new Jogador(nomeJogador);
        this.tempoRestante = 50;
        this.jogoAtivo = true;
        gerarNovaPergunta();
    }

    public void gerarNovaPergunta() {
        this.perguntaAtual = new Pergunta();
    }

    public boolean verificarResposta(int respostaUsuario) {
        if (!jogoAtivo) {
            return false;
        }

        if (respostaUsuario == perguntaAtual.getRespostaCorreta()) {
            jogador.adicionarPontos(10);
            gerarNovaPergunta();
            return true;
        } else {
            gerarNovaPergunta();
            return false;
        }
    }

    public void diminuirTempo() {
        if (tempoRestante > 0) {
            tempoRestante--;
        }

        if (tempoRestante == 0) {
            jogoAtivo = false;
        }
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Pergunta getPerguntaAtual() {
        return perguntaAtual;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public boolean isJogoAtivo() {
        return jogoAtivo;
    }
}