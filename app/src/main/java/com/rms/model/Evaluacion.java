package com.rms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by rms on 27/03/2017.
 */

public class Evaluacion {

    private static final char CONJUNCION = Valores.CONJUNCION;
    private static final char DISYUNCION = Valores.DISYUNCION;
    private static final char CONDICIONAL = Valores.CONDICIONAL;
    private static final char BICONDICIONAL = Valores.BICONDICIONAL;
    private static final char NEGACION = Valores.NEGACION;
    private static final char PARENTESISABRE = Valores.PARENTESISABRE;
    private static final char PARENTESISCIERRA = Valores.PARENTESISCIERRA;

    public static ArrayList<Object> columna = new ArrayList<>();
    public static ArrayList<ArrayList> arrayTabla = new ArrayList<>();

    /**
     * Método para iniciar la evaluación, convierte una cadena con la proposición en postfijo,
     * después se manda cada caracter a un ArrayList, y las variables se sustituyen por ArrayLists
     *
     * @param proposicion
     * @throws IOException
     */
    public static void evaluacionIniciar(String proposicion) throws IOException {
        columna.clear();
        arrayTabla.clear();

        int noVar = contarVariables(proposicion);

        String[] str = proposicion.split("");
        ArrayList<String> lexema = new ArrayList<>(Arrays.asList(str));
        Posfijo p = new Posfijo(lexema);
        ArrayList<String> aux3 = p.getPosfijo();
        String[] aux2 = aux3.toArray(new String[aux3.size()]);
        String aux = Arrays.toString(aux2);
        aux = aux.replaceAll("[ ,\\[\\]]", "");

        ArrayList<Object> prop = new ArrayList<>();
        for (int i = 0; i < aux.length(); i++) {
            prop.add(aux.charAt(i));
        }
        System.out.println(prop);

        for (int i = 0; i < prop.size(); i++) {
            char c = (char) prop.get(i);
            if (!(prop.get(i).equals(CONJUNCION)
                    || prop.get(i).equals(DISYUNCION)
                    || prop.get(i).equals(CONDICIONAL)
                    || prop.get(i).equals(BICONDICIONAL)
                    || prop.get(i).equals(NEGACION))) {
                switch (c) {
                    case 'P':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[0]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[0]);
                        break;
                    case 'Q':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[1]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[1]);
                        break;
                    case 'R':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[2]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[2]);
                        break;
                    case 'S':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[3]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[3]);
                        break;
                    case 'T':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[4]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[4]);
                        break;
                    case 'U':
                        columna.add(c);
                        prop.set(i, Valores.vectorVariables[5]);
                        arrayTabla.add((ArrayList<Character>) Valores.vectorVariables[5]);
                        break;
                }
            }
        }
        evaluacionTotal(prop);
        System.out.println(prop);
        System.out.println("Columnas: " + columna.toString());
        System.out.println("Arreglos: " + arrayTabla.toString());
    }

    /**
     * Método para evaluar en su totalidad la proposición
     *
     * @param prop
     */
    private static void evaluacionTotal(ArrayList<Object> prop) {
        try {
            for (int i = 0; i < prop.size(); i++) {
                if (prop.get(i).equals(CONJUNCION)
                        || prop.get(i).equals(DISYUNCION)
                        || prop.get(i).equals(CONDICIONAL)
                        || prop.get(i).equals(BICONDICIONAL)) {
                    char op = (char) prop.get(i);

                    ArrayList<Character> x = (ArrayList<Character>) prop.get(i - 2);
                    System.out.println("X: " + x.toString());
                    ArrayList<Character> y = (ArrayList<Character>) prop.get(i - 1);
                    System.out.println("Y: " + y.toString());
                    ArrayList<Character> res = evaluacionOperador(op, x, y);
                    System.out.println("R: " + res.toString());

                    columna.add(op);
                    arrayTabla.add(res);

                    i = i - 2;
                    prop.remove(i);
                    prop.remove(i);
                    prop.remove(i);
                    prop.add(i, res);
                    System.out.println("Final: " + prop.toString() + "\n");
                } else if (prop.get(i).equals(NEGACION)) {
                    char op = (char) prop.get(i);

                    ArrayList<Character> x = (ArrayList<Character>) prop.get(i - 1);
                    System.out.println("X: " + x.toString());
                    ArrayList<Character> res = evaluacionOperador(op, x, null);
                    System.out.println("R: " + res.toString());

                    columna.add(op);
                    arrayTabla.add(res);

                    i = i - 1;
                    prop.remove(i);
                    prop.remove(i);
                    prop.add(i, res);
                    System.out.println("Final: " + prop.toString() + "\n");
                }
            }
        } catch (Exception e) {
            System.out.println("Expresión incorrecta");
        }
    }

    /**
     * Método para evaluar variables con operadores
     *
     * @param op
     * @param x
     * @param y
     * @return
     */
    private static ArrayList<Character> evaluacionOperador(char op, ArrayList<Character> x, ArrayList<Character> y) {
        ArrayList<Character> temp = new ArrayList<>();
        switch (op) {
            case NEGACION:
                temp = Reglas.negacion(x);
                break;
            case CONJUNCION:
                temp = Reglas.conjuncion(x, y);
                break;
            case DISYUNCION:
                temp = Reglas.disyuncion(x, y);
                break;
            case CONDICIONAL:
                temp = Reglas.condicional(x, y);
                break;
            case BICONDICIONAL:
                temp = Reglas.bicondicional(x, y);
                break;
        }
        return temp;
    }

    /**
     * Método para evaluar si ArrayList final es tautología, falacia/contradicción o contingencia
     *
     * @return
     */
    public static String evaluacionTipo() {
        ArrayList<Character> arr = arrayTabla.get(arrayTabla.size() - 1);
        int contadorV = 0;
        int contadorF = 0;

        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == 'V') {
                contadorV++;
            } else {
                contadorF++;
            }
        }
        if (contadorV == arr.size()) {
            return "Tautología";
        } else if (contadorF == arr.size()) {
            return "Falacia/contradicción";
        }
        return "Contingencia";
    }

    /**
     * Método para contar el número de variables (PQRSTU) y crear los arreglos correspondientes
     *
     * @param proposicion
     * @return
     */
    public static int contarVariables(String proposicion) {
        String prop = proposicion.replaceAll("[^A-Ua-uW-Zw-z]", "");

        HashSet<Character> hash = new HashSet<>();
        prop = prop.toUpperCase();
        for (int i = 0; i < prop.length(); i++) {
            hash.add(prop.charAt(i));
        }
        return hash.size();
    }

}
