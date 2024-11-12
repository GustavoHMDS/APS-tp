package Visão;

import Controle.Sistema;

import javax.swing.*;
import java.awt.*;

public class InterfaceLogin extends InterfaceComum {

    public InterfaceLogin(GerenciadorInterfaces gerenciador) {
        // Chama o construtor da classe pai (InterfaceComum)
        super(gerenciador);

        // Configurações específicas do painel central para a tela de login
        centerPanel.setLayout(new GridLayout(6, 1, 10, 15)); // Define uma grade para os componentes

        // Rótulos e campos de texto
        JLabel labelUsuario = new JLabel("Email:");
        labelUsuario.setForeground(new Color(254, 244, 129));
        JTextField campoUsuario = new JTextField(20);
        campoUsuario.setBackground(new Color(Transparency.TRANSLUCENT));
        campoUsuario.setForeground(new Color(255,255,255));
        campoUsuario.setBorder(null);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setForeground(new Color(254, 244, 129));
        JPasswordField campoSenha = new JPasswordField(20);
        campoSenha.setBackground(new Color(Transparency.TRANSLUCENT));
        campoSenha.setForeground(new Color(255,255,255));
        campoSenha.setBorder(null);

        JButton botaoEntrar = CriaBotaoPreDefinido("Entrar");

        // Nome do app (pode ser substituído pelo nome real do seu aplicativo)
        //JLabel nomeApp = new JLabel(Sistema.nomeApp, SwingConstants.CENTER);
        //nomeApp.setFont(new Font("Arial", Font.PLAIN, 30));

        // Adiciona os componentes ao painel central herdado
        //centerPanel.add(nomeApp);
        centerPanel.add(labelUsuario);
        centerPanel.add(campoUsuario);
        centerPanel.add(labelSenha);
        centerPanel.add(campoSenha);
        centerPanel.add(botaoEntrar);

        // Ação do botão "Entrar"
        botaoEntrar.addActionListener(_ -> {
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            // Verifica as credenciais
            if (Sistema.sistemaLogin(usuario, senha)) {
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
