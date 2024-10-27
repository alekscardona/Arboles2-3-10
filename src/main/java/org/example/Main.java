package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazArbol interfaz = new InterfazArbol();
            interfaz.setVisible(true);
        });
    }
}