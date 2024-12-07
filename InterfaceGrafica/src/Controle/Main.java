package Controle;

import Modelo.Convidado;
import Visão.GerenciadorInterfaces;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Sistema.defineDataAtual();
        Sistema.usuario = new Convidado();
        Sistema.AdminFailSafe();
        SwingUtilities.invokeLater(() -> {
            GerenciadorInterfaces gerenciador = new GerenciadorInterfaces();
            gerenciador.setVisible(true);
            ImageIcon icone = new ImageIcon("./src/Imagens/image.png");
            gerenciador.setIconImage(icone.getImage());
        });
    }
}