package Visão;

import Controle.Sistema;
import Modelo.Admin;
import Modelo.Convidado;

import javax.swing.*;
import java.awt.*;

public class InterfaceRegistro extends InterfaceComum implements Atualizavel {
    public InterfaceRegistro(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario;
        if(Sistema.getTipoUsuario().equals("Guest")) tipoUsuario = "Cliente";
        else tipoUsuario = "Admin";
        super.centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        //empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        // Sempre adiciona:
        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField(15);
        JLabel labelCPF = new JLabel("CPF:");
        JTextField campoCPF = new JTextField(15);
        JLabel labelEmail = new JLabel("Email:");
        JTextField campoEmail = new JTextField(15);
        JLabel labelSenha = new JLabel("Senha:");
        JTextField campoSenha = new JTextField(15);
        JLabel labelDataNascimento = new JLabel("Data Nascimento:");
        JPanel painelDataNascimento = painelData();

        Styles.setLabelStyle(labelNome);
        Styles.setLabelStyle(labelCPF);
        Styles.setLabelStyle(labelEmail);
        Styles.setLabelStyle(labelSenha);
        Styles.setLabelStyle(labelDataNascimento);
        Styles.setTextFielStyle(campoNome);
        Styles.setTextFielStyle(campoCPF);
        Styles.setTextFielStyle(campoEmail);
        Styles.setTextFielStyle(campoSenha);
        painelDataNascimento.setBackground(Styles.background);

        empilhamentoPanel.add(labelNome);
        empilhamentoPanel.add(campoNome);
        empilhamentoPanel.add(labelCPF);
        empilhamentoPanel.add(campoCPF);
        empilhamentoPanel.add(labelEmail);
        empilhamentoPanel.add(campoEmail);
        empilhamentoPanel.add(labelSenha);
        empilhamentoPanel.add(campoSenha);
        empilhamentoPanel.add(labelDataNascimento);
        empilhamentoPanel.add(painelDataNascimento);

        JButton botaoRegistrar = CriaBotaoPreDefinido("Registrar");
        empilhamentoPanel.add(botaoRegistrar);
        botaoRegistrar.addActionListener(_ -> {
            String nome = campoNome.getText();
            String cpf = campoCPF.getText();
            String email = campoEmail.getText();
            String senha = campoSenha.getText();
            String dataNascimento = campoDia.getText() + "/" + campoMes.getText() + "/" + campoAno.getText();
            try {
                Sistema.criarUsuario(cpf, nome, dataNascimento, email, senha, tipoUsuario);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro criando a conta", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            if(Sistema.usuario instanceof Convidado) Sistema.login(email, senha);
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(800, 500));
        empilhamentoPanel.setPreferredSize(new Dimension(550, 400));
        centerPanel.setPreferredSize(new Dimension(800, 500));
    }
}
