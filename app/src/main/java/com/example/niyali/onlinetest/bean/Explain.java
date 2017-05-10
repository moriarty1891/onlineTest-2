package com.example.niyali.onlinetest.bean;

/**
 * Created by niyali on 17/4/28.
 */

public class Explain {
    private int id;


    private String correctAnswer;
    private String explain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
