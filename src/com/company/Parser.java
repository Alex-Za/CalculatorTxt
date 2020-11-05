package com.company;

import java.util.*;

class Parser {
    private String operators = "+-*/";
    private String delimiters = "() " + operators;
    public boolean flag = true;

    private boolean isDelimiter(String token) {
        if (token.length() != 1) return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (token.charAt(0) == delimiters.charAt(i)) return true;
        }
        return false;
    }

    private boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < operators.length(); i++) {
            if (token.charAt(0) == operators.charAt(i)) return true;
        }
        return false;
    }

    private int priority(String token) {
        if (token.equals("(")) return 1;
        if (token.equals("+") || token.equals("-")) return 2;
        if (token.equals("*") || token.equals("/")) return 3;
        return 4;
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public List<String> parse(String infix) {
        List<String> postfix = new ArrayList<String>();
        Deque<String> stack = new ArrayDeque<String>();
        StringTokenizer tokenizer = new StringTokenizer(infix, delimiters, true);
        flag = true;
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (curr.equals(" ")) continue;

            String errorMessage = checkError(tokenizer, curr, prev);
            if (errorMessage!= "") {
                flag = false;
                System.out.println(errorMessage);
                postfix.add(errorMessage);
                return postfix;
            }

            else if (isDelimiter(curr)) {
                if (curr.equals("(")) stack.push(curr);
                else if (curr.equals(")")) {
                        if (!stack.isEmpty()) {
                            while (!stack.peek().equals("(")) {
                                postfix.add(stack.pop());
                                if (stack.isEmpty()) {
                                    System.out.println("Скобки не согласованы");
                                    flag = false;
                                    postfix.add("Скобки не согласованы");
                                    return postfix;
                                }
                            }
                            stack.pop();
                        } else {
                            System.out.println("Не корректный ввод");
                            flag = false;
                            postfix.add("Не корректный ввод");
                            return postfix;
                        }
                }
                else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev) && !prev.equals(")")))) {
                        curr = "u-";
                    }
                    else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            postfix.add(stack.pop());
                        }
                    }
                    stack.push(curr);
                }
            }

            else {
                postfix.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) postfix.add(stack.pop());
            else {
                System.out.println("Скобки не согласованы");
                flag = false;
                postfix.add("Скобки не согласованы");
                return postfix;
            }
        }
        return postfix;
    }

    private String checkError(StringTokenizer tokenizer, String curr, String prev) {
        if (!isDelimiter(curr)) {
            if (!isNumeric(curr))
                return "Некорректное выражение, введите число";
        }
        if (!tokenizer.hasMoreTokens() && isOperator(curr))
            return "Некорректное выражение";
        if (curr.equals("0") && prev.equals("/"))
            return "На 0 делить нельзя";
        if (curr.equals("*") && prev.equals(""))
            return "Некорректное выражение";
        if (isOperator(curr) && isOperator(prev) && !curr.equals("-"))
            return "Некорректное выражение";
        if (curr.equals("-") && prev.equals("u-")) {
            return "Не корректный ввод";
        }

        return "";
    }

    public Double calc(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<Double>();

        for (String x : postfix) {

            if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            }
            else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("/")) {
                Double b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            }
            else if (x.equals("u-")) stack.push(-stack.pop());
            else stack.push(Double.valueOf(x));
        }
        return stack.pop();
    }
}
