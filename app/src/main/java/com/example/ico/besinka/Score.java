package com.example.ico.besinka;

import java.io.Serializable;

/**
 * Created by Ico on 28.3.2017 г..
 */

public class Score implements Serializable {
    String word;
    String name;
    String taim;
    int attempts;

    public Score(String word, String name, String taim, int attempts) {
        this.word = word;
        this.name = name;
        this.taim = taim;
        this.attempts = attempts;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaim() {
        return taim;
    }

    public void setTaim(String taim) {
        this.taim = taim;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}
