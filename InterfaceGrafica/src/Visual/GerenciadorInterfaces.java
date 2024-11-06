package Visual;

import javax.swing.*;
import java.awt.*;

public class GerenciadorInterfaces extends JFrame {
    protected InterfaceLogin login;
    protected InterfaceMatriculaAluno aluno;
    protected InterfaceMatriculaProfessor professor;
    protected InterfaceMatriculaAdmin admin;
    private CardLayout cardLayout;
    private JPanel telaAtual;

    public GerenciadorInterfaces() {
        setTitle("SIGEDU");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        telaAtual = new JPanel(cardLayout);

        // Inicialize as telas de login e interfaces de usuário
        login = new InterfaceLogin(this);
        aluno = new InterfaceMatriculaAluno();
        professor = new InterfaceMatriculaProfessor();
        admin = new InterfaceMatriculaAdmin();

        // Adicione os painéis ao CardLayout
        telaAtual.add(login, "login");
        telaAtual.add(aluno, "aluno");
        telaAtual.add(professor, "professor");
        telaAtual.add(admin, "admin");

        add(telaAtual);
        cardLayout.show(telaAtual, "login"); // Exibe inicialmente a tela de login
    }

    // Método para alternar a interface após login
    public void trocarParaTelaUsuario(String tipoUsuario) {
        switch (tipoUsuario) {
            case "Aluno":
                cardLayout.show(telaAtual, "aluno");
                break;
            case "Professor":
                cardLayout.show(telaAtual, "professor");
                break;
            case "Admin":
                cardLayout.show(telaAtual, "admin");
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GerenciadorInterfaces frame = new GerenciadorInterfaces();
            frame.setVisible(true);
        });
    }
}
