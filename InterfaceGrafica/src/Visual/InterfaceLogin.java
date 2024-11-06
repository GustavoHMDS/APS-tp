package Visual;

import Console.Sistema;

import javax.swing.*;
import java.awt.*;

public class InterfaceLogin extends JPanel {
    public InterfaceLogin(GerenciadorInterfaces geral) {
        // Configurações do JFrame
        setSize(900, 600);
        setLayout(new GridLayout(9, 6, 10, 30)); // Organiza os componentes em uma grade

        // Rótulos e campos de texto
        JLabel labelUsuario = new JLabel("Usuário:");
        JTextField campoUsuario = new JTextField(15);

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(15);

        JButton btnEntrar = new JButton("Entrar");

        JLabel nomeApp = new JLabel("PlaceHolder", SwingConstants.CENTER);
        nomeApp.setFont(new Font("Arial", Font.PLAIN, 30));
        JLabel nomeCompletoApp = new JLabel("Sistema Integrado de Gestão Educacional", SwingConstants.CENTER);
        nomeCompletoApp.setFont(new Font("Arial", Font.PLAIN, 16));

        // Adiciona componentes ao JFrame
        add(new JLabel()); // Espaço vazio
        add(nomeApp);
        add(nomeCompletoApp);
        add(labelUsuario);
        add(campoUsuario);
        add(labelSenha);
        add(campoSenha);
        add(new JLabel()); // Espaço vazio
        add(btnEntrar);

        // Ação do botão "Entrar"
        btnEntrar.addActionListener(_ -> {
            // Obtém o texto dos campos de login e senha
            String usuario = campoUsuario.getText();
            String senha = new String(campoSenha.getPassword());

            // Verifica as credenciais (aqui você pode colocar uma verificação real)
            if (Sistema.sistemaLogin(usuario, senha)) {
                geral.trocarParaTelaUsuario(Sistema.usuario.getClass().getName());
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceLogin(new GerenciadorInterfaces()).setVisible(true);
            }
        });
    }
}
