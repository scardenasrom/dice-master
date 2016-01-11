package com.herkiusdev.dicemaster.model;

public class BoardPlayerDTO {

    private String name;
    private int score;

    public BoardPlayerDTO() {

    }

    public BoardPlayerDTO(String name) {
        this.name = name;
        this.score = 0;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
