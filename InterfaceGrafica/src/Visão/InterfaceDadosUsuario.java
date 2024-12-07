package Visão;

import Controle.Sistema;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InterfaceDadosUsuario extends InterfaceComum implements Atualizavel{
    public InterfaceDadosUsuario(GerenciadorInterfaces gerenciador) {
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
            JLabel labelCPF = new JLabel("CPF: " + Sistema.usuario.getCPF());
            JLabel labelEmail = new JLabel("Email: ");
            JTextField campoEmail = new JTextField(Sistema.usuario.getEmail());
            JLabel labelSenha = new JLabel("Senha:");
            JTextField campoSenha = new JTextField(Sistema.usuario.getSenha());
            LocalDate data = Sistema.usuario.getDataNascimento();
            JLabel labelDataNascimento = new JLabel("Data Nascimento: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());

            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCPF);
            Styles.setLabelStyle(labelSenha);
            Styles.setLabelStyle(labelDataNascimento);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoEmail);
            Styles.setTextFielStyle(campoSenha);

            JButton salvarNome = CriaBotaoPreDefinido("Salvar nome", 200, 25, 16);
            JButton salvarEmail = CriaBotaoPreDefinido("Salvar email", 200, 25, 16);
            JButton salvarSenha = CriaBotaoPreDefinido("Salvar Senha", 200, 25, 16);


            salvarNome.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Nome", campoNome.getText()));
            salvarEmail.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Email", campoEmail.getText()));
            salvarSenha.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Senha", campoSenha.getText()));



            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(salvarNome);
            empilhamentoPanel.add(labelCPF);
            empilhamentoPanel.add(labelEmail);
            empilhamentoPanel.add(campoEmail);
            empilhamentoPanel.add(salvarEmail);
            empilhamentoPanel.add(labelSenha);
            empilhamentoPanel.add(campoSenha);
            empilhamentoPanel.add(salvarSenha);
            empilhamentoPanel.add(labelDataNascimento);

            if(Sistema.getTipoUsuario().equals("Cliente")) {
                JButton cadastrarCartao = CriaBotaoPreDefinido("Cadastrar cartao", 200, 25, 16);
                JButton realizarPagamento = CriaBotaoPreDefinido("Realizar pagamento", 200, 25, 16);

                cadastrarCartao.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_CARTAO));
                realizarPagamento.addActionListener(e -> {
                    if(Sistema.fazPagamento(0)) JOptionPane.showMessageDialog(null, "Pagamento Realizado com sucesso", "Pagamento aprovado", JOptionPane.INFORMATION_MESSAGE);
                    else JOptionPane.showMessageDialog(null, "O pagamento não foi aprovado, confira a validade do cartão", "Pagamento recusado", JOptionPane.ERROR_MESSAGE);
                });

                empilhamentoPanel.add(cadastrarCartao);
                if(Sistema.getCliente().getCartoes().length > 0)empilhamentoPanel.add(realizarPagamento);
            }
        }
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(400, 500));
        empilhamentoPanel.setPreferredSize(new Dimension(350, 400));
        centerPanel.setPreferredSize(new Dimension(600, 500));
    }
}
