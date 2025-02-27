package Visão;

import Controle.SistemaGeral;

import javax.swing.*;
import java.awt.*;

public class InterfaceLogin extends InterfaceComum implements Atualizavel {

    public InterfaceLogin(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        // Chama o construtor da classe pai (InterfaceComum)
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        centerPanel.removeAll();
        // Configurações específicas do painel central para a tela de login
        centerPanel.setLayout(new GridLayout(6, 1, 10, 15)); // Define uma grade para os componentes

        // Rótulos e campos de texto
        JLabel labelUsuario = new JLabel("Email:");
        JTextField campoUsuario = new JTextField(20);
        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(20);

        Styles.setLabelStyle(labelUsuario);
        Styles.setLabelStyle(labelSenha);
        Styles.setTextFielStyle(campoUsuario);
        Styles.setTextFielStyle(campoSenha);

        JButton botaoEntrar = CriaBotaoPreDefinido("Entrar");
        botaoEntrar.setMaximumSize(new Dimension(300,50));
        //JLabel nomeApp = new JLabel(SistemaGeral.nomeApp, SwingConstants.CENTER);
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
            if (sistema.login(usuario, senha)) {
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

    }
}
