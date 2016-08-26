package com.example.scardenas.dice_master.util;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

public class MathParser {

    private static MathParser singleton;

    private static final int PREVIOUS_NOTHING = 0;
    private static final int PREVIOUS_NUMBER = 1;
    private static final int PREVIOUS_OPERATION = 2;
    private static final int PREVIOUS_PARENTHESIS = 3;
    private static final int PREVIOUS_CLOSE_PARENTHESIS = 4;

    private MathParser() {
    }

    public static MathParser getInstance() {
        if (singleton == null) {
            singleton = new MathParser();
        }
        return singleton;
    }

    public String evaluateExpression(String expression) throws ArithmeticException, NumberFormatException {
        return evaluateExpressionWithAccuracy(expression, 0);
    }

    /**
     * Dada una expresión matemótica, devolveró el resultado de su evaluación
     * @param expression
     * @param accuracy
     * @return resultado de la evaluación
     * @throws ArithmeticException en caso de que en la expresión aparezcan operaciones ilegales
     * @throws NumberFormatException en caso que en la expresión no aparezcan elementos matemóticos
     */
    public String evaluateExpressionWithAccuracy(String expression, int accuracy) throws ArithmeticException, NumberFormatException {

        String result;
        Stack numbersStack = new Stack();
        Stack operatorsStack = new Stack();
        // Expression previous treatment, in this case we use lower case letters, with no blank spaces and dots instead of commas
        String treatedExpression = expression.toLowerCase().replace(" ", "");
        treatedExpression = treatedExpression.replace(",", ".");
        int position = 0;
        final String validCharacters = "1 2 3 4 5 6 7 8 9 0 ( ) + - * /";
        final String openParenthesisChars = "(";
        // This variable stores the last parsed expression, so we can check what's allowed after it
        int previous = MathParser.PREVIOUS_NOTHING;

        while (position < treatedExpression.length()) {

            char currentCharacter = treatedExpression.charAt(position);

            if (validCharacters.lastIndexOf(currentCharacter)!=-1){

                // Case 1 - The current character is a digit, we start looking for the entire number to store it in the numbersStack
                if (Character.isDigit(currentCharacter)) {
                    String completeNumber = "";
                    do {
                        currentCharacter = treatedExpression.charAt(position);
                        completeNumber += currentCharacter;
                        position++;
                    } while (position < treatedExpression.length() &&
                            (Character.isDigit(treatedExpression.charAt(position)) || treatedExpression.charAt(position)=='.' || treatedExpression.charAt(position)==','));
                    // Used to check if we have a well-formed number, if it isn't an exception will be thrown
                    Double.parseDouble(completeNumber);
                    numbersStack.push(completeNumber);
                    previous = MathParser.PREVIOUS_NUMBER;
                }

                // Case 2 - The current character is a '-' without a previous operator, meaning it's a minus sign for a number after it
                // In this case we'll treat the expression: "-2" as the binary operation: "(-1) * 2"
                else if (currentCharacter == '-' &&
                        (previous == MathParser.PREVIOUS_NOTHING || previous == MathParser.PREVIOUS_PARENTHESIS || previous == MathParser.PREVIOUS_OPERATION)) {
                    numbersStack.push("-1");
                    extractOperators(numbersStack, operatorsStack, "*");
                    previous = MathParser.PREVIOUS_OPERATION;
                    position++;
                }

                // Case 3 - The current character is a binary operator
                else if (currentCharacter == '+' || currentCharacter == '-' || currentCharacter == '*' || currentCharacter == '/') {
                    if (previous == MathParser.PREVIOUS_NOTHING || previous == MathParser.PREVIOUS_PARENTHESIS || previous == MathParser.PREVIOUS_OPERATION) {
                        throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): Error parsing the mathematical expression. A binary operator was preceded by an invalid character: | (+2 | +2 | ++2 |");
                    }
                    extractOperators(numbersStack, operatorsStack, String.valueOf(currentCharacter));
                    previous = MathParser.PREVIOUS_OPERATION;
                    position++;
                }

                // Case 4  - The current character is an opening parenthesis
                else if (currentCharacter == '(') {
                    if (previous != MathParser.PREVIOUS_NOTHING && previous != MathParser.PREVIOUS_PARENTHESIS && previous != MathParser.PREVIOUS_OPERATION) {
                        throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): Error parsing the mathematical expression. An opening parenthesis was preceded by an invalid character");
                    }
                    operatorsStack.push(String.valueOf(currentCharacter));
                    previous = MathParser.PREVIOUS_PARENTHESIS;
                    position++;
                }

                // Case 5 - The current character is a closing parenthesis
                else if (currentCharacter == ')') {
                    if (previous != MathParser.PREVIOUS_NUMBER && previous != MathParser.PREVIOUS_CLOSE_PARENTHESIS) {
                        throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): Error parsing the mathematical expression. A closing parenthesis can only be preceded by a number or another closing parenthesis");
                    }
                    while(!operatorsStack.empty() && !openParenthesisChars.contains((String) operatorsStack.peek())){
                        extractOperator(numbersStack, operatorsStack);
                    }
                    if (!operatorsStack.empty() && openParenthesisChars.contains((String) operatorsStack.peek())){
                        operatorsStack.pop();
                    }
                    previous = MathParser.PREVIOUS_CLOSE_PARENTHESIS;
                    position++;
                }

            } else {
                throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): Invalid character found in expression: " + treatedExpression.charAt(position));
            }
        }

        while (!operatorsStack.empty()){
            if (openParenthesisChars.contains((String) operatorsStack.peek())){
                throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): Invalid expression: there is an opening parenthesis without a closing one");
            }
            extractOperator(numbersStack, operatorsStack);
        }

        result = (String)numbersStack.pop();
        if (result != null){
            result = new BigDecimal(result).setScale(accuracy, BigDecimal.ROUND_HALF_DOWN).toString();
        }

        if (!numbersStack.isEmpty()){
            throw new ArithmeticException("MathParser - evaluateExpressionWithAccuracy(...): There is an error in the expression: " + treatedExpression);
        }

        return result;
    }

    /**
     * Extracts the operator with highest priority
     * @param numbersStack
     * @param operatorsStack
     * @param operator
     */
    void extractOperators(Stack numbersStack, Stack operatorsStack, String operator){
        final String parentesis = "(";
        while(!operatorsStack.empty() &&
                !parentesis.contains((String) operatorsStack.peek()) &&
                ((String)operatorsStack.peek()).length()==1 &&
                obtainOperationPriority(((String)operatorsStack.peek()).charAt(0))>= obtainOperationPriority(operator.charAt(0))){
            extractOperator(numbersStack, operatorsStack);
        }
        operatorsStack.push(operator);
    }

    /**
     * Extracts the last operator in operatorsStack, checks if it's binary and if it is extracts two numbers from numbersStack
     * and calls performOperation(...)
     * @param numbersStack
     * @param operatorsStack
     * @throws EmptyStackException
     * @throws ArithmeticException
     */
    private void extractOperator(Stack numbersStack, Stack operatorsStack) throws EmptyStackException, ArithmeticException {
        String operator;
        BigDecimal a, b;
        String result;
        final String binaryOperators = "+ - * /";
        operator = (String)operatorsStack.pop();

        if (binaryOperators.contains(operator)){
            b = new BigDecimal((String)numbersStack.pop());
            a = new BigDecimal((String)numbersStack.pop());
            result = performOperation(operator.charAt(0), a, b);
            numbersStack.push(result);
        } else {
            throw new ArithmeticException("MathParser - extractOperator(...): Unkown operator: " + operator);
        }
    }

    private String performOperation(char operator, BigDecimal a, BigDecimal b) throws ArithmeticException {
        switch (operator){
            case '+':
                return (a.add(b)).toString();
            case '-':
                return (a.subtract(b)).toString();
            case '*':
                return (a.multiply(b)).toString();
            case '/':
                return (a.divide(b, 10, BigDecimal.ROUND_HALF_UP)).toString();
            default:
                throw new ArithmeticException("MathParser - performOperation(...): Unknown operator: " + operator);
        }
    }

    private int obtainOperationPriority(char c) {
        switch (c){
            case '+':
            case '-':
                return 0;
            case '*':
            case '/':
                return 1;
            default:
                return -1;
        }
    }

}