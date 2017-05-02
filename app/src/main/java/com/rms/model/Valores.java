package com.rms.model;

import java.util.ArrayList;

/**
 * Created by rms on 27/03/2017.
 */

public class Valores {

    public static final char NEGACION = '-';
    public static final char CONJUNCION = '^';
    public static final char DISYUNCION = 'v';
    public static final char CONDICIONAL = '→';
    public static final char BICONDICIONAL = '↔';
    public static final char PARENTESISABRE = '(';
    public static final char PARENTESISCIERRA = ')';

    public static final ArrayList<Character> arrUno = new ArrayList<>();
    public static final ArrayList<Character> arrDos = new ArrayList<>();
    public static final ArrayList<Character> arrTres = new ArrayList<>();
    public static final ArrayList<Character> arrCuatro = new ArrayList<>();
    public static final ArrayList<Character> arrCinco = new ArrayList<>();
    public static final ArrayList<Character> arrSeis = new ArrayList<>();
    public static Object[] vectorVariables;

    /**
     * Método para limpiar los arreglos
     */
    private static void limpiar() {
        arrUno.clear();
        arrDos.clear();
        arrTres.clear();
        arrCuatro.clear();
        arrCinco.clear();
        arrSeis.clear();
    }

    /**
     * Método para llenar arreglos dependiendo del número de variables seleccionadas (2^n)
     *
     * @param noVar
     */
    private static void crearArreglos(int noVar) {
        limpiar();

        switch (noVar) {
            case 6:
                for (int i = 0; i < Math.pow(2, noVar) / 64; i++) {
                    for (int j = 0; j < 32; j++) {
                        arrSeis.add('V');
                    }
                    for (int j = 0; j < 32; j++) {
                        arrSeis.add('F');
                    }
                }
            case 5:
                for (int i = 0; i < Math.pow(2, noVar) / 32; i++) {
                    for (int j = 0; j < 16; j++) {
                        arrCinco.add('V');
                    }
                    for (int j = 0; j < 16; j++) {
                        arrCinco.add('F');
                    }
                }
            case 4:
                for (int i = 0; i < Math.pow(2, noVar) / 16; i++) {
                    for (int j = 0; j < 8; j++) {
                        arrCuatro.add('V');
                    }
                    for (int j = 0; j < 8; j++) {
                        arrCuatro.add('F');
                    }
                }
            case 3:
                for (int i = 0; i < Math.pow(2, noVar) / 8; i++) {
                    for (int j = 0; j < 4; j++) {
                        arrTres.add('V');
                    }
                    for (int j = 0; j < 4; j++) {
                        arrTres.add('F');
                    }
                }
            case 2:
                for (int i = 0; i < Math.pow(2, noVar) / 4; i++) {
                    for (int j = 0; j < 2; j++) {
                        arrDos.add('V');
                    }
                    for (int j = 0; j < 2; j++) {
                        arrDos.add('F');
                    }
                }
            case 1:
                for (int i = 0; i < Math.pow(2, noVar) / 2; i++) {
                    arrUno.add('V');
                    arrUno.add('F');
                }
                break;
        }
    }

    /**
     * Método para regresar arreglos creados (2^n) en un arreglo de objetos
     *
     * @param noVar
     * @return
     */
    private static Object[] regresar(int noVar) {
        vectorVariables = new Object[noVar];

        switch (noVar) {
            case 1:
                vectorVariables[0] = arrUno;
                break;
            case 2:
                vectorVariables[0] = arrDos;
                vectorVariables[1] = arrUno;
                break;
            case 3:
                vectorVariables[0] = arrTres;
                vectorVariables[1] = arrDos;
                vectorVariables[2] = arrUno;
                break;
            case 4:
                vectorVariables[0] = arrCuatro;
                vectorVariables[1] = arrTres;
                vectorVariables[2] = arrDos;
                vectorVariables[3] = arrUno;
                break;
            case 5:
                vectorVariables[0] = arrCinco;
                vectorVariables[1] = arrCuatro;
                vectorVariables[2] = arrTres;
                vectorVariables[3] = arrDos;
                vectorVariables[4] = arrUno;
                break;
            case 6:
                vectorVariables[0] = arrSeis;
                vectorVariables[1] = arrCinco;
                vectorVariables[2] = arrCuatro;
                vectorVariables[3] = arrTres;
                vectorVariables[4] = arrDos;
                vectorVariables[5] = arrUno;
                break;
        }
        return vectorVariables;
    }

    /**
     * Método para imprimir arreglos creados
     *
     * @param arr
     */
    private static void imprimirArreglos(Object[] arr) {
        for (Object obj : arr) {
            ArrayList<Character> a = (ArrayList) obj;
            for (int i = 0; i < a.size(); i++) {
                System.out.print(a.get(i) + " ");
            }
        }
    }

    /**
     * Método para iniciar
     *
     * @param noVar
     * @return
     */
    public static Object[] iniciar(int noVar) {
        crearArreglos(noVar);
        return regresar(noVar);
    }

}
