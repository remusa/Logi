package com.rms.model;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by rms on 11/05/2017.
 */

public class Validations {

    public String proposition;
    public boolean validate;
    public boolean balanced;

    public Validations() {
    }

    public Validations(String proposition) {
        this.proposition = proposition;
        this.validate = false;
        this.balanced = false;
    }

    public static boolean validateProposition(String proposition) {
        String var = "[PQRSTU]";
        String op = "[y||o||f||b]";
        ArrayList<String> pattern = new ArrayList();
        pattern.add("^[( || -]*" + var + "[)]*(" + op + "[( || -]*" + var + "[)]*)*$");
        for (int i = 0; i < pattern.size(); i++) {
            if (proposition.matches((String) pattern.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static boolean validateParenthesis(String proposition) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < proposition.length(); i++) {
            char c = proposition.charAt(i);
            if (c == '[' || c == '(' || c == '{') {
                stack.push(c);
            } else if (c == ']') {
                if (stack.isEmpty())
                    return false;
                if (stack.pop() != '[')
                    return false;

            } else if (c == ')') {
                if (stack.isEmpty())
                    return false;
                if (stack.pop() != '(')
                    return false;

            } else if (c == '}') {
                if (stack.isEmpty())
                    return false;
                if (stack.pop() != '{')
                    return false;
            }

        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        ArrayList<String> propositions = new ArrayList();
        propositions.add("-P");
        propositions.add("(((-P)");
        propositions.add("PyQ");
        propositions.add("PoQ");
        propositions.add("PfQ");
        propositions.add("PbQ");
        propositions.add("(-PoQ)yR");
        propositions.add("(-PoQ)fR)");
        for (int i = 0; i < propositions.size(); i++) {
            boolean validate = validateProposition((String) propositions.get(i));
            boolean balanced = validateParenthesis((String) propositions.get(i));
            if (validate && balanced) {
                System.out.println(((String) propositions.get(i)) + "\t - TRUE");
            } else {
                System.out.println(((String) propositions.get(i)) + "\t - FALSE");
            }
        }
    }

}
