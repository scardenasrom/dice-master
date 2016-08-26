package com.example.scardenas.dice_master.util;

import com.example.scardenas.dice_master.data.Roll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class RollParser {

    private static final int PREVIOUS_NOTHING = 0;
    private static final int PREVIOUS_NUMBER = 1;
    private static final int PREVIOUS_OPERATION = 2;

    private static RollParser singleton;

    private List<List<Integer>> listOfResults;

    private RollParser() {

    }

    public static RollParser getInstance() {
        if (singleton == null) {
            singleton = new RollParser();
        }
        return singleton;
    }

    public Roll parseExpression(String expression) throws ArithmeticException, NumberFormatException {
        listOfResults = new ArrayList<>();

        String result;
        Stack numbersStack = new Stack();
        Stack operatorsStack = new Stack();
        String expr = expression.replace(" ", "");
        int position = 0;
        final String functions = "1 2 3 4 5 6 7 8 9 0 + - d";
        int previous = RollParser.PREVIOUS_NOTHING;

        while (position < expr.length()){
            char current = expr.charAt(position);

            if (functions.lastIndexOf(current) != -1){
                if (isANumber(current)){
                    String completeNumber = "";
                    do {
                        current = expr.charAt(position);
                        completeNumber += current;
                        position++;
                    } while (position < expr.length() && (isANumber(expr.charAt(position))));
                    Double.parseDouble(completeNumber);
                    numbersStack.push(completeNumber);
                    previous = RollParser.PREVIOUS_NUMBER;

                } else if (current == '+' || current == '-' || current == 'd'){
                    if (previous == RollParser.PREVIOUS_NOTHING || previous == RollParser.PREVIOUS_OPERATION) {
                        numbersStack.push("1");
                    }
                    extractOperators(numbersStack, operatorsStack, String.valueOf(current));
                    previous = RollParser.PREVIOUS_OPERATION;
                    position++;

                }
            } else {
                throw new ArithmeticException();
            }
        }

        while (!operatorsStack.empty()){
            extractSingleOperator(numbersStack, operatorsStack);
        }

        result = (String)numbersStack.pop();
        if (result!=null){
            result = new BigDecimal(result).setScale(0, BigDecimal.ROUND_HALF_DOWN).toString();
        }

        if (!numbersStack.isEmpty()){
            throw new ArithmeticException();
        }

        Roll resultRoll = new Roll();
        resultRoll.setRollString(expression);
        resultRoll.setRollResult(result);
        resultRoll.setListOfResults(listOfResults);
        return resultRoll;
    }

    private void extractSingleOperator(Stack numbersStack, Stack operatorsStack) {
        String operator;
        BigDecimal a, b;
        String result;
        final String binaryOperators = "+ - d";
        operator = (String)operatorsStack.pop();
        if (binaryOperators.contains(operator)){
            b = new BigDecimal((String)numbersStack.pop());
            a = new BigDecimal((String)numbersStack.pop());
            result = performOperation(operator.charAt(0), a, b);
            numbersStack.push(result);
        } else {
            throw new ArithmeticException();
        }
    }

    private void extractOperators(Stack numbersStack, Stack operatorsStack, String operator) {
        while(!operatorsStack.empty()
                && ((String)operatorsStack.peek()).length() == 1
                && obtainPriority(((String)operatorsStack.peek()).charAt(0)) >= obtainPriority(operator.charAt(0))){
            extractSingleOperator(numbersStack, operatorsStack);
        }
        operatorsStack.push(operator);
    }

    private int obtainPriority(char operator) {
        switch (operator){
            case '+':
            case '-':
                return 0;
            case 'd':
                return 1;
            default:
                return -1;
        }
    }

    private String performOperation(char operator, BigDecimal a, BigDecimal b) throws ArithmeticException {
        switch (operator){
            case 'd':
                return rollTheDie(a.intValue(), b.intValue());
            case '+':
                return (a.add(b)).toString();
            case '-':
                return (a.subtract(b)).toString();
            default:
                throw new ArithmeticException();
        }
    }

    private boolean isANumber(char c) {
        return Character.isDigit(c);
    }

    private String rollTheDie(int numberOfDie, int diceSides) {
        List<Integer> listOfCurrentResults = new ArrayList<>(numberOfDie);
        int result = 0;
        for (int i = 0; i < numberOfDie; i++) {
            Random r = new Random();
            int random = r.nextInt(diceSides) + 1;
            listOfCurrentResults.add(random);
            result = result + random;
        }
        listOfResults.add(listOfCurrentResults);
        return ""+result;
    }

}
