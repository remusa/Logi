package com.rms.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static com.rms.model.Evaluacion.contarVariables;

/**
 * Created by rms on 27/03/2017.
 */

public class Principal {

    public static int noVar = 0;
    public static Object[] vectorVariables;
//    private DefaultTableModel model;

    private static final char CONJUNCION = Valores.CONJUNCION;
    private static final char DISYUNCION = Valores.DISYUNCION;
    private static final char CONDICIONAL = Valores.CONDICIONAL;
    private static final char BICONDICIONAL = Valores.BICONDICIONAL;
    private static final char NEGACION = Valores.NEGACION;
    private static final char PARENTESISABRE = Valores.PARENTESISABRE;
    private static final char PARENTESISCIERRA = Valores.PARENTESISCIERRA;

    private static final char P = 'P';
    private static final char Q = 'Q';
    private static final char R = 'R';
    private static final char S = 'S';
    private static final char T = 'T';
    private static final char U = 'U';

    private static ArrayList<Character> varP = new ArrayList<>();
    private static ArrayList<Character> varQ = new ArrayList<>();
    private static ArrayList<Character> varR = new ArrayList<>();
    private static ArrayList<Character> varS = new ArrayList<>();
    private static ArrayList<Character> varT = new ArrayList<>();
    private static ArrayList<Character> varU = new ArrayList<>();

    /**
     * Método para mostrar las tablas
     *
     * @param proposicion
     */
    public static void mostrarTablasVariables(String proposicion) {
        noVar = contarVariables(proposicion);

        vectorVariables = Valores.iniciar(noVar);
        //es un vector de máx. longitud 6, cada índice contiene
        //un ArrayList<Character> con los valores de la tabla de verdad

        switch (noVar) {
            case 6:
                varU = (ArrayList) vectorVariables[5];
            case 5:
                varT = (ArrayList) vectorVariables[4];
            case 4:
                varS = (ArrayList) vectorVariables[3];
            case 3:
                varR = (ArrayList) vectorVariables[2];
            case 2:
                varQ = (ArrayList) vectorVariables[1];
            case 1:
                varP = (ArrayList) vectorVariables[0];
                break;
        }

//        model = new DefaultTableModel();
//        for (int i = 0; i < Evaluacion.arrayTabla.size(); i++) {
//            Object[] obj = Evaluacion.arrayTabla.get(i).toArray();
//            model.addColumn(Evaluacion.columna.get(i), obj);
//        }
//
//        tbVariables.setModel(model);

    }

    public static String postfijo(String proposicion) throws IOException {
        ArrayList<String> res = new ArrayList();
        String prop[] = proposicion.split("");
        res.addAll(Arrays.asList(prop));
        Posfijo p = new Posfijo(res);
        res = p.getPosfijo();

        String temp = Arrays.toString(res.toArray());
        temp = temp.concat("").replaceAll("[ ,()]", "");
        temp = temp.substring(1, temp.length() - 1);
        return temp;
    }

}
