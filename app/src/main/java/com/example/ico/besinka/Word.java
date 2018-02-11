package com.example.ico.besinka;

import java.io.Serializable;

/**
 * Created by Ico on 25.3.2017 г..
 */

public class Word implements Serializable {
    private long id;
    private String word;

    public Word() {
    }

    public Word(String word) {

        this.word = word;
    }

    public long getId() {
        return id;
    }



    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
