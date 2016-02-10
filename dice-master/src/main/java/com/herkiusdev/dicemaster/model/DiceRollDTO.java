package com.herkiusdev.dicemaster.model;

public class DiceRollDTO {

    private int numberOfDie;
    private int diceFaces;
    private int modifier;

    public DiceRollDTO() {

    }

    public int getNumberOfDie() {
        return this.numberOfDie;
    }

    public void setNumberOfDie(int numberOfDie) {
        this.numberOfDie = numberOfDie;
    }

    public int getDiceFaces() {
        return this.diceFaces;
    }

    public void setDiceFaces(int diceFaces) {
        this.diceFaces = diceFaces;
    }

    public int getModifier() {
        return this.modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

}
