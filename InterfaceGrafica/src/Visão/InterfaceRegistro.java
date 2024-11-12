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
        String tipoUsuario = Sistema.getTipoUsuario();
        super.centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        // Sempre adiciona:
        JLabel labelNome = new JLabel("Nome:");
        labelNome.setForeground(new Color(254, 244, 129));
        JTextField campoNome = new JTextField(15);
        campoNome.setBackground(new Color(Transparency.TRANSLUCENT));
        campoNome.setForeground(new Color(255,255,255));
        campoNome.setBorder(null);
        JLabel labelCPF = new JLabel("CPF:");
        labelCPF.setForeground(new Color(254, 244, 129));
        JTextField campoCPF = new JTextField(15);
        campoCPF.setBackground(new Color(Transparency.TRANSLUCENT));
        campoCPF.setForeground(new Color(255,255,255));
        campoCPF.setBorder(null);
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setForeground(new Color(254, 244, 129));
        JTextField campoEmail = new JTextField(15);
        campoEmail.setBackground(new Color(Transparency.TRANSLUCENT));
        campoEmail.setForeground(new Color(255,255,255));
        campoEmail.setBorder(null);
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setForeground(new Color(254, 244, 129));
        JTextField campoSenha = new JTextField(15);
        campoSenha.setBackground(new Color(Transparency.TRANSLUCENT));
        campoSenha.setForeground(new Color(255,255,255));
        campoSenha.setBorder(null);
        JLabel labelDataNascimento = new JLabel("Data Nascimento:");
        JPanel painelDataNascimento = painelData();

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
