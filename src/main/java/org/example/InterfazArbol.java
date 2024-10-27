package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazArbol extends JFrame {

    private JPanel panel;
    private Arbol arbol;
    private JLabel labelHojas;
    private boolean esHorizontal = false; // Variable para controlar el modo de visualización

    public InterfazArbol() {
        setTitle("Representación de Árbol Binario");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel donde se dibujará el árbol
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (arbol != null && arbol.getRaiz() != null) {
                    System.out.println("Dibujando árbol...");
                    if (esHorizontal) {
                        dibujarNodoHorizontal(g, arbol.getRaiz(), 50, getHeight() / 2, getHeight() / 4);
                    } else {
                        dibujarNodoVertical(g, arbol.getRaiz(), getWidth() / 2, 50, getWidth() / 4);
                    }
                } else {
                    System.out.println("No hay árbol para dibujar.");
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(700, 400);
            }
        };

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        containerPanel.add(panel, BorderLayout.CENTER);

        // Panel de información y control
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelHojas = new JLabel("Número de hojas: 0");
        labelHojas.setFont(new Font("Arial", Font.BOLD, 14));

        // Botón para cambiar la orientación
        JButton btnCambiarVista = new JButton("Cambiar Vista");
        configurarBoton(btnCambiarVista);
        btnCambiarVista.addActionListener(e -> {
            esHorizontal = !esHorizontal;
            repaint();
        });

        infoPanel.add(labelHojas);
        infoPanel.add(Box.createHorizontalStrut(20)); // Espacio entre elementos
        infoPanel.add(btnCambiarVista);

        containerPanel.add(infoPanel, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton botonArbol1 = new JButton("((a * b) + (c / d))");
        JButton botonArbol2 = new JButton("(((a + b) + c) + d)");
        JButton botonArbol3 = new JButton("(((-a) + (x + y)) / ((+b) * (c * d)))");

        configurarBoton(botonArbol1);
        configurarBoton(botonArbol2);
        configurarBoton(botonArbol3);

        panelBotones.add(botonArbol1);
        panelBotones.add(botonArbol2);
        panelBotones.add(botonArbol3);

        mainPanel.add(containerPanel, BorderLayout.CENTER);
        mainPanel.add(panelBotones, BorderLayout.SOUTH);

        add(mainPanel);

        // Botón 1: ((a * b) + (c / d))
        botonArbol1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol = new Arbol();
                Nodo raiz = new Nodo("+");

                raiz.izquierdo = new Nodo("*");
                raiz.izquierdo.izquierdo = new Nodo("a");
                raiz.izquierdo.derecho = new Nodo("b");

                raiz.derecho = new Nodo("/");
                raiz.derecho.izquierdo = new Nodo("c");
                raiz.derecho.derecho = new Nodo("d");

                arbol.setRaiz(raiz);
                actualizarContadorHojas();
                repaint();
            }
        });

        // Botón 2: (((a + b) + c) + d)
        botonArbol2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol = new Arbol();
                Nodo raiz = new Nodo("+");

                Nodo nivel2 = new Nodo("+");
                Nodo nivel1 = new Nodo("+");

                nivel2.izquierdo = new Nodo("a");
                nivel2.derecho = new Nodo("b");

                nivel1.izquierdo = nivel2;
                nivel1.derecho = new Nodo("c");

                raiz.izquierdo = nivel1;
                raiz.derecho = new Nodo("d");

                arbol.setRaiz(raiz);
                actualizarContadorHojas();
                repaint();
            }
        });

        // Botón 3: (((-a) + (x + y)) / ((+b) * (c * d)))
        botonArbol3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arbol = new Arbol();
                Nodo raiz = new Nodo("/");

                raiz.izquierdo = new Nodo("+");
                raiz.izquierdo.izquierdo = new Nodo("-");
                raiz.izquierdo.izquierdo.derecho = new Nodo("a");

                raiz.izquierdo.derecho = new Nodo("+");
                raiz.izquierdo.derecho.izquierdo = new Nodo("x");
                raiz.izquierdo.derecho.derecho = new Nodo("y");

                raiz.derecho = new Nodo("*");
                raiz.derecho.izquierdo = new Nodo("+");
                raiz.derecho.izquierdo.derecho = new Nodo("b");

                raiz.derecho.derecho = new Nodo("*");
                raiz.derecho.derecho.izquierdo = new Nodo("c");
                raiz.derecho.derecho.derecho = new Nodo("d");

                arbol.setRaiz(raiz);
                actualizarContadorHojas();
                repaint();
            }
        });

        pack();
        setMinimumSize(new Dimension(800, 600));
    }

    private int contarHojas(Nodo nodo) {
        if (nodo == null) {
            return 0;
        }
        if (nodo.izquierdo == null && nodo.derecho == null) {
            return 1;
        }
        return contarHojas(nodo.izquierdo) + contarHojas(nodo.derecho);
    }

    private void actualizarContadorHojas() {
        if (arbol != null && arbol.getRaiz() != null) {
            int numHojas = contarHojas(arbol.getRaiz());
            labelHojas.setText("Número de hojas: " + numHojas);
        } else {
            labelHojas.setText("Número de hojas: 0");
        }
    }

    private void configurarBoton(JButton boton) {
        boton.setPreferredSize(new Dimension(200, 30));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(240, 240, 240));
        boton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
    }

    // Método para dibujar el árbol verticalmente
    private void dibujarNodoVertical(Graphics g, Nodo nodo, int x, int y, int xOffset) {
        if (nodo == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2f));

        // Determinar el color del nodo
        if (nodo.izquierdo == null && nodo.derecho == null) {
            g2d.setColor(new Color(144, 238, 144)); // Verde claro para hojas
        } else {
            g2d.setColor(new Color(230, 230, 250));
        }

        g2d.fillOval(x - 20, y - 20, 40, 40);
        g2d.setColor(new Color(70, 130, 180));
        g2d.drawOval(x - 20, y - 20, 40, 40);
        g2d.setColor(Color.BLACK);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(nodo.valor);
        g2d.drawString(nodo.valor, x - textWidth/2, y + fm.getAscent()/2);

        if (nodo.izquierdo != null) {
            g2d.drawLine(x, y, x - xOffset, y + 50);
            dibujarNodoVertical(g2d, nodo.izquierdo, x - xOffset, y + 50, xOffset / 2);
        }

        if (nodo.derecho != null) {
            g2d.drawLine(x, y, x + xOffset, y + 50);
            dibujarNodoVertical(g2d, nodo.derecho, x + xOffset, y + 50, xOffset / 2);
        }
    }

    // Método para dibujar el árbol horizontalmente
    private void dibujarNodoHorizontal(Graphics g, Nodo nodo, int x, int y, int yOffset) {
        if (nodo == null) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2f));

        // Determinar el color del nodo
        if (nodo.izquierdo == null && nodo.derecho == null) {
            g2d.setColor(new Color(144, 238, 144)); // Verde claro para hojas
        } else {
            g2d.setColor(new Color(230, 230, 250));
        }

        g2d.fillOval(x - 20, y - 20, 40, 40);
        g2d.setColor(new Color(70, 130, 180));
        g2d.drawOval(x - 20, y - 20, 40, 40);
        g2d.setColor(Color.BLACK);

        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(nodo.valor);
        g2d.drawString(nodo.valor, x - textWidth/2, y + fm.getAscent()/2);

        if (nodo.izquierdo != null) {
            g2d.drawLine(x + 20, y, x + 70, y - yOffset);
            dibujarNodoHorizontal(g2d, nodo.izquierdo, x + 70, y - yOffset, yOffset / 2);
        }

        if (nodo.derecho != null) {
            g2d.drawLine(x + 20, y, x + 70, y + yOffset);
            dibujarNodoHorizontal(g2d, nodo.derecho, x + 70, y + yOffset, yOffset / 2);
        }
    }
}