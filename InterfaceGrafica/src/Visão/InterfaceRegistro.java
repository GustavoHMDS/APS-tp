package Visão;

import Controle.Sistema;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class InterfaceRegistro extends InterfaceComum implements Atualizavel {
    public InterfaceRegistro(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = Sistema.getTipoUsuario();
        super.centerPanel.removeAll();
        Color corDeFundo = new Color(64, 44, 94);

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setBackground(corDeFundo);
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
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
        painelDataNascimento.setBackground(corDeFundo);
        painelDataNascimento.setBorder(new BasicBorders.FieldBorder(corDeFundo, corDeFundo, corDeFundo, corDeFundo));

        Styles.setLabelStyle(labelNome);
        Styles.setLabelStyle(labelCPF);
        Styles.setLabelStyle(labelEmail);
        Styles.setLabelStyle(labelSenha);
        Styles.setLabelStyle(labelDataNascimento);
        Styles.setTextFielStyle(campoNome);
        Styles.setTextFielStyle(campoCPF);
        Styles.setTextFielStyle(campoEmail);
        Styles.setTextFielStyle(campoSenha);

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

        // Adicione os botões com base no tipo de usuário
        if(tipoUsuario.equals("Guest")){
            JButton botaoRegistrarPremium = CriaBotaoPreDefinido("Registrar conta Premium");
            empilhamentoPanel.add(botaoRegistrarPremium);
            botaoRegistrarPremium.addActionListener(_ -> {
                String nome = campoNome.getText();
                String cpf = campoCPF.getText();
                String email = campoEmail.getText();
                String senha = campoSenha.getText();
                int dia = Integer.parseInt(campoDia.getText());
                int mes = Integer.parseInt(campoMes.getText());
                int ano = Integer.parseInt(campoAno.getText());
                Sistema.adicionarUsuario(cpf, email, senha, nome, dia, mes, ano, tipoUsuario);
                gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_CARTAO);
            });
        }
        JButton botaoRegistrar = CriaBotaoPreDefinido("Registrar");
        empilhamentoPanel.add(botaoRegistrar);
        botaoRegistrar.addActionListener(_ -> {
            String nome = campoNome.getText();
            String cpf = campoCPF.getText();
            String email = campoEmail.getText();
            String senha = campoSenha.getText();
            int dia = Integer.parseInt(campoDia.getText());
            int mes = Integer.parseInt(campoMes.getText());
            int ano = Integer.parseInt(campoAno.getText());
            Sistema.adicionarUsuario(cpf, email, senha, nome, dia, mes, ano, tipoUsuario);
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(400, 500));
        empilhamentoPanel.setPreferredSize(new Dimension(350, 400));
        centerPanel.setPreferredSize(new Dimension(600, 500));
    }
}
