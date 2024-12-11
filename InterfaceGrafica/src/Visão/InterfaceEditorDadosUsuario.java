package Visão;

import Controle.Sistema;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InterfaceEditorDadosUsuario extends InterfaceComum implements Atualizavel {
    public InterfaceEditorDadosUsuario(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = Sistema.getTipoUsuario();
        super.centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        if(Sistema.usuario != null) {
            JLabel labelNome = new JLabel("Nome:");
            JTextField campoNome = new JTextField(Sistema.usuario.getNome());
            JLabel labelEmail = new JLabel("Email:");
            JTextField campoEmail = new JTextField(Sistema.usuario.getEmail());
            JLabel labelSenha = new JLabel("Senha:");
            JTextField campoSenha = new JTextField(Sistema.usuario.getSenha());

            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelEmail);
            Styles.setLabelStyle(labelSenha);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoEmail);
            Styles.setTextFielStyle(campoSenha);

            JButton salvar = CriaBotaoPreDefinido("Salvar", 200, 25, 16);
            salvar.addActionListener(e -> {
                String[] dados = {campoNome.getText(), campoEmail.getText(),campoSenha.getText()};
                Sistema.editarUsuario(Sistema.usuario.getEmail(),dados);
                gerenciador.trocarParaTela(gerenciador.DADOS_USUARIO);
            });

            JButton cancelar = CriaBotaoPreDefinido("Cancelar", 200, 25, 16);
            cancelar.addActionListener(e -> {
                gerenciador.trocarParaTela((gerenciador.DADOS_USUARIO));
            });

            //salvarNome.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Nome", campoNome.getText()));
            //salvarEmail.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Email", campoEmail.getText()));
            //salvarSenha.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Senha", campoSenha.getText()));

            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(labelEmail);
            empilhamentoPanel.add(campoEmail);
            empilhamentoPanel.add(labelSenha);
            empilhamentoPanel.add(campoSenha);
            empilhamentoPanel.add(salvar);
            empilhamentoPanel.add(cancelar);
        }
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(400, 500));
        empilhamentoPanel.setPreferredSize(new Dimension(350, 400));
        centerPanel.setPreferredSize(new Dimension(600, 500));
    }
}
