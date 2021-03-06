package com.rms.model;

import java.util.ArrayList;
import java.util.Stack;

import static com.rms.model.Evaluacion.contarVariables;

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

    public boolean validateVariables() {
        int noVar = contarVariables(proposition);
        boolean valid = false;
        switch (noVar) {
            case 1:
                if (proposition.contains("P")) {
                    valid = true;
                }
                break;
            case 2:
                if (proposition.contains("P") && proposition.contains("Q")) {
                    valid = true;
                }
                break;
            case 3:
                if (proposition.contains("P") && proposition.contains("Q") && proposition.contains("R")) {
                    valid = true;
                }
                break;
            case 4:
                if (proposition.contains("P") && proposition.contains("Q") && proposition.contains("R")
                        && proposition.contains("S")) {
                    valid = true;
                }
                break;
            case 5:
                if (proposition.contains("P") && proposition.contains("Q") && proposition.contains("R")
                        && proposition.contains("S") && proposition.contains("T")) {
                    valid = true;
                }
                break;
            case 6:
                if (proposition.contains("P") && proposition.contains("Q") && proposition.contains("R")
                        && proposition.contains("S") && proposition.contains("T") && proposition.contains("U")) {
                    valid = true;
                }
                break;
            default:
                return false;
        }
        return valid;
    }

    public boolean validateProposition() {
        proposition = proposition.replace("v", "o").replace("^", "y").replace("→", "f").replace("↔", "b");
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

    public boolean validateParenthesis() {
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
        propositions.add("P^Q");
        propositions.add("PvQ");
        propositions.add("P→Q");
        propositions.add("P↔Q");
        propositions.add("(-PvQ)^R");
        propositions.add("(-PvQ)→R)↔S");
        propositions.add("((-PvQ)→R)↔S");
        propositions.add("(((-PvQ)→R)↔S)^T");
        for (int i = 0; i < propositions.size(); i++) {
            Validations val = new Validations(propositions.get(i));
            boolean validate = val.validateProposition();
            boolean balanced = val.validateParenthesis();
            if (validate && balanced) {
                System.out.println(((String) propositions.get(i)) + "\t - TRUE");
            } else {
                System.out.println(((String) propositions.get(i)) + "\t - FALSE");
            }
        }
    }

}
