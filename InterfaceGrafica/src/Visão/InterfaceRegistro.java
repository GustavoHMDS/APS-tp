package Visão;

import Controle.Sistema;

import javax.swing.*;
import java.awt.*;

public class InterfaceRegistro extends InterfaceComum implements Atualizavel {
    public InterfaceRegistro(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
//        String tipoUsuario = Sistema.getTipoUsuario();
//        super.centerPanel.removeAll();
//
//        // Criar um painel para empilhar os botões, usando BoxLayout vertical
//        JPanel empilhamentoPanel = new JPanel();
//        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
//        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
//        // Sempre adiciona:
//        JLabel labelUsuario = new JLabel("email:");
//        JTextField campoUsuario = new JTextField(15);
//        JLabel labelUsuario = new JLabel("email:");
//        JTextField campoUsuario = new JTextField(15);
//        // Adicione os botões com base no tipo de usuário
//        switch (tipoUsuario) {
//            JLabel labelUsuario = new JLabel("email:");
//            JTextField campoUsuario = new JTextField(15);
//            case "Admin":
//                dadosUsuario = CriaBotaoPreDefinido("Ver dados Usuario");
//                registrar = CriaBotaoPreDefinido("Registrar novo administrador");
//                novoAnime = CriaBotaoPreDefinido("Adicionar Anime");
//                sair = CriaBotaoPreDefinido("Sair");
//                empilhamentoPanel.add(dadosUsuario);
//                empilhamentoPanel.add(registrar);
//                empilhamentoPanel.add(novoAnime);
//                empilhamentoPanel.add(sair);
//                InicializaBotoesAdmin();
//                break;
//            case "Guest":
//                login = CriaBotaoPreDefinido("Login");
//                registrar = CriaBotaoPreDefinido("Registrar");
//                empilhamentoPanel.add(login);
//                empilhamentoPanel.add(registrar);
//                InicializaBotoesGuest();
//                break;
//            default:
//                JOptionPane.showMessageDialog(this, "Esse tipo de usuário não pode fazer registros!");
//                return;
//        }
//
//        // Centraliza o painel de empilhamento no centerPanel usando GridBagLayout
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.anchor = GridBagConstraints.CENTER;
//        centerPanel.add(empilhamentoPanel, gbc);
//        // Revalida e repinta
//        revalidate();
//        repaint();
    }
}
