package br.com.mathquiz;

import java.util.Random;

public class Pergunta {
    private int numero1;
    private int numero2;

    public Pergunta() {
        Random random = new Random();
        this.numero1 = random.nextInt(50) + 1;
        this.numero2 = random.nextInt(50) + 1;
    }

    public String getEnunciado() {
        return numero1 + " + " + numero2 + " = ?";
    }

    public int getRespostaCorreta() {
        return numero1 + numero2;
    }
}
