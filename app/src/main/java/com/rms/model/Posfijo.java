package com.rms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rms on 27/03/2017.
 */

public class Posfijo {

    String aux;
    Pila operadores;
    Pila operandos;
    ArrayList<String> lines;
    ArrayList<String> lexema;
    ArrayList<String> res;
    public Map<String, Integer> prioridad = new HashMap<>();

    /**
     * @param lexema
     * @throws IOException
     */
    public Posfijo(ArrayList<String> lexema) throws IOException {
        this.lexema = lexema;
        operadores = new Pila(lexema.size());
        operandos = new Pila(lexema.size());
        this.getPrioridad();
    }

    private void getPrioridad() {
        prioridad.put("-", 2);
        prioridad.put("^", 3);
        prioridad.put("v", 3);
        prioridad.put("→", 3);
        prioridad.put("↔", 3);
    }

    public ArrayList<String> getPosfijo() {
        for (String lex : lexema) {
            switch (lex) {
                case ")":
                    while (!operadores.empty() && !"(".equals(operadores.stacktop())) {//mientras no este vacia y operadores no sea un (
                        aux = operadores.pop();
                        operandos.push(aux);//cambia lo que esta en la pila operadores a la pila operandos
                    }
                    if (operadores.empty()) {
                        System.out.println("Expresión Errónea");
                    } else {
                        operadores.pop();//sacamos el paréntesis que abre
                    }
                    break;
                case "(":
                    operadores.push(lex);
                    break;
                case "^":
                case "v":
                case "→":
                case "↔":
                case "-":
                    if (operadores.empty()) {
                        operadores.push(lex);
                    } else {
                        while (!operadores.empty() && !operadores.stacktop().equals("(") && this.prioridad.get(lex) >= this.prioridad.get(operadores.stacktop())) {
                            aux = operadores.pop();
                            operandos.push(aux);
                        }
                        operadores.push(lex);
                    }
                    break;
                default:
                    operandos.push(lex);
                    break;
            }
        }
        while (!operandos.empty()) {
            operadores.push(operandos.pop());
        }
        return mostrarPila(operadores);
    }

    public ArrayList<String> mostrarPila(Pila p) {
        ArrayList<String> posfijo = new ArrayList();
        while (!p.empty()) {
            posfijo.add(p.pop());
        }
        return posfijo;
    }
}
