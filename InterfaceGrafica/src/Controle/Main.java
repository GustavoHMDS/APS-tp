package Controle;

import Visão.GerenciadorInterfaces;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Sistema.defineDataAtual();
        SwingUtilities.invokeLater(() -> {
            GerenciadorInterfaces gerenciador = new GerenciadorInterfaces();
            gerenciador.setVisible(true);
        });
    }
}