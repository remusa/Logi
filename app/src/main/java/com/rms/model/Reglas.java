package com.rms.model;

import java.util.ArrayList;

/**
 * Created by rms on 27/03/2017.
 */

public class Reglas {

    /**
     * Método para negar cada valor recibido en un ArrayList
     *
     * @param x
     * @return
     */
    public static ArrayList<Character> negacion(ArrayList<Character> x) {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) == 'V') {
                temp.add('F');
            } else if (x.get(i) == 'F') {
                temp.add('V');
            }
        }
        return temp;
    }

    /**
     * Método para evaluar la conjunción
     *
     * @param x
     * @param y
     * @return
     */
    public static ArrayList<Character> conjuncion(ArrayList<Character> x, ArrayList<Character> y) {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) == 'V' && y.get(i) == 'V') {
                temp.add('V');
            } else {
                temp.add('F');
            }
        }
        return temp;
    }

    /**
     * Método para evaluar la disyunción
     *
     * @param x
     * @param y
     * @return
     */
    public static ArrayList<Character> disyuncion(ArrayList<Character> x, ArrayList<Character> y) {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) == 'F' && y.get(i) == 'F') {
                temp.add('F');
            } else {
                temp.add('V');
            }
        }
        return temp;
    }

    /**
     * Método para evaluar la condicional
     *
     * @param x
     * @param y
     * @return
     */
    public static ArrayList<Character> condicional(ArrayList<Character> x, ArrayList<Character> y) {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if (x.get(i) == 'V' && y.get(i) == 'F') {
                temp.add('F');
            } else {
                temp.add('V');
            }
        }
        return temp;
    }

    /**
     * Método para evaluar la bicondicional
     *
     * @param x
     * @param y
     * @return
     */
    public static ArrayList<Character> bicondicional(ArrayList<Character> x, ArrayList<Character> y) {
        ArrayList<Character> temp = new ArrayList<>();
        for (int i = 0; i < x.size(); i++) {
            if ((x.get(i) == 'V' && y.get(i) == 'V')
                    || (x.get(i) == 'F' && y.get(i) == 'F')) {
                temp.add('V');
            } else {
                temp.add('F');
            }
        }
        return temp;
    }

}
