package Controle;

import Visão.GerenciadorInterfaces;

import javax.swing.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Sistema.defineDataAtual();
        SwingUtilities.invokeLater(() -> {
            GerenciadorInterfaces gerenciador = new GerenciadorInterfaces();
            gerenciador.setVisible(true);
        });
    }
}