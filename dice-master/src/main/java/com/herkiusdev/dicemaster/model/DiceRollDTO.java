package com.herkiusdev.dicemaster.model;

public class DiceRollDTO {

    private int numberOfDie;
    private int numberOfSides;
    private boolean modifierAdding;
    private int modifier;
    private int result;

    public DiceRollDTO() {
        this.numberOfDie = 0;
        this.numberOfSides = 0;
        this.modifierAdding = true;
        this.modifier = 0;
        this.result = 0;
    }

    public int getNumberOfDie() {
        return this.numberOfDie;
    }

    public void setNumberOfDie(int numberOfDie) {
        this.numberOfDie = numberOfDie;
    }

    public int getNumberOfSides() {
        return this.numberOfSides;
    }

    public void setNumberOfSides(int numberOfSides) {
        this.numberOfSides = numberOfSides;
    }

    public boolean isModifierAdding() {
        return this.modifierAdding;
    }

    public void setModifierAdding(boolean modifierAdding) {
        this.modifierAdding = modifierAdding;
    }

    public int getModifier() {
        return this.modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

}
