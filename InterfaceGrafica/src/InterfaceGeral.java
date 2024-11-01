import javax.swing.*;
import java.awt.*;

public class InterfaceGeral extends JFrame {
    protected InterfaceLogin login;
    protected InterfaceAluno aluno;
    protected InterfaceProfessor professor;
    protected InterfaceAdmin admin;
    private CardLayout cardLayout;
    private JPanel telaAtual;

    public InterfaceGeral() {
        setTitle("Sistema de Login e Usuários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        telaAtual = new JPanel(cardLayout);

        // Inicialize as telas de login e interfaces de usuário
        //login = new InterfaceLogin(this);
        aluno = new InterfaceAluno();
        professor = new InterfaceProfessor();
        admin = new InterfaceAdmin();

        // Adicione os painéis ao CardLayout
        telaAtual.add(login, "login");
        telaAtual.add(aluno, "aluno");
        telaAtual.add(professor, "professor");
        telaAtual.add(admin, "admin");

        add(telaAtual);
        cardLayout.show(telaAtual, "login"); // Exibe inicialmente a tela de login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceGeral frame = new InterfaceGeral();
            frame.setVisible(true);
        });
    }
}
