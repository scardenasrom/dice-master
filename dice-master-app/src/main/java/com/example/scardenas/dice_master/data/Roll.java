package com.example.scardenas.dice_master.data;

import android.support.annotation.NonNull;

import java.util.List;

public class Roll implements Comparable<Roll> {

    private String name;
    private String rollString;
    private List<List<Integer>> listOfResults;
    private String rollResult;
    private long timeOfRoll;

    public Roll() {

    }

    public Roll(String name, String rollString) {
        this.name = name;
        this.rollString = rollString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollString() {
        return rollString;
    }

    public void setRollString(String rollString) {
        this.rollString = rollString;
    }

    public List<List<Integer>> getListOfResults() {
        return this.listOfResults;
    }

    public void setListOfResults(List<List<Integer>> listOfResults) {
        this.listOfResults = listOfResults;
    }

    public String getRollResult() {
        return rollResult;
    }

    public void setRollResult(String rollResult) {
        this.rollResult = rollResult;
    }

    public long getTimeOfRoll() {
        return timeOfRoll;
    }

    public void setTimeOfRoll(long timeOfRoll) {
        this.timeOfRoll = timeOfRoll;
    }

    @Override
    public int compareTo(@NonNull Roll roll) {
        return this.getName().compareTo(roll.getName());
    }

}
