package org.example;

public class ExpresionParser {

    public static Arbol construirArbolExpresion(String expresion) {
        // Lógica de construcción del árbol (asegúrate de que esté completa y correcta)

        Arbol arbol = new Arbol();
        Nodo raiz = new Nodo("+"); // Ejemplo de nodo raíz para fines de prueba
        arbol.setRaiz(raiz);

        System.out.println("Árbol construido con raíz: " + (raiz != null ? raiz.valor : "nulo"));
        return arbol;
    }

}
