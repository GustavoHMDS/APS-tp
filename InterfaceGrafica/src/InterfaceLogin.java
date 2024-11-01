import javax.swing.*;
import java.awt.*;

public class InterfaceLogin extends JFrame {
    public InterfaceLogin() {
        // Configurações do JFrame
        setTitle("Tela de Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 6, 10, 30)); // Organiza os componentes em uma grade

        // Rótulos e campos de texto
        JLabel labelUsuario = new JLabel("Usuário:");
        JTextField campoUsuario = new JTextField(15);

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField campoSenha = new JPasswordField(15);

        JButton btnEntrar = new JButton("Entrar");

        JLabel nomeApp = new JLabel("SIGEDU", SwingConstants.CENTER);
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
                JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfaceLogin().setVisible(true);
            }
        });
    }
}
