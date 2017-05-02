package com.rms.model;

/**
 * Created by rms on 27/03/2017.
 */

public class Pila {

    private int tope;
    private final String[] arreglo;

    public Pila(int longuitud) {
        arreglo = new String[longuitud];
        tope = -1;
    }

    public void push(String dato) {//agregar un nuevo dato a la pila
        tope++;
        arreglo[tope] = dato;
    }

    public String pop() {//eliminar y conocer el valor eliminado
        String temporal;

        temporal = arreglo[tope];
        tope--;
        return temporal;
    }

    public String stacktop() {//conocer el valor que se encuentra en el tope
        return arreglo[tope];

    }

    public boolean empty() {//pila vacia si es asi retornar true si no esta vacia retornara falso
        if (tope == -1) {
            return true;
        } else {
            return false;
        }

    }

    public boolean full() {//verifica si la pila esta llena
        if (tope == arreglo.length - 1)
            return true;
        else
            return false;
    }
}
