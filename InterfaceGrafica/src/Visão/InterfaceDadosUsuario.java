package Visão;

import Controle.Sistema;
import Modelo.Admin;
import Modelo.Cliente;

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
        super.centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        if(Sistema.usuario != null) {
            JLabel labelNome = new JLabel("Nome: " + Sistema.usuario.getNome());
            JLabel labelCPF = new JLabel("CPF: " + Sistema.usuario.getCPF());
            JLabel labelEmail = new JLabel("Email: " + Sistema.usuario.getEmail());
            String senha = "";
            for(int i = 0; i < Sistema.usuario.getSenha().length(); i++) senha += '*';
            JLabel labelSenha = new JLabel("Senha: " + senha);
            LocalDate data = Sistema.usuario.getDataNascimento();
            JLabel labelDataNascimento = new JLabel("Data Nascimento: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());

            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCPF);
            Styles.setLabelStyle(labelEmail);
            Styles.setLabelStyle(labelSenha);
            Styles.setLabelStyle(labelDataNascimento);

            //salvarNome.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Nome", campoNome.getText()));
            //salvarEmail.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Email", campoEmail.getText()));
            //salvarSenha.addActionListener(e -> Sistema.editarUsuario(Sistema.usuario.getCPF(), "Senha", campoSenha.getText()));

            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(labelCPF);
            empilhamentoPanel.add(labelEmail);
            empilhamentoPanel.add(labelSenha);
            empilhamentoPanel.add(labelDataNascimento);

            if(Sistema.usuario instanceof Cliente cliente) {
                JLabel labelNumCartoes = new JLabel("Quantidade de cartões: " + cliente.getCartoesQuantidade());
                Styles.setLabelStyle(labelNumCartoes);
                empilhamentoPanel.add(labelNumCartoes);
            }
            else if(Sistema.usuario instanceof Admin admin) {
                JLabel labelID = new JLabel("Quantidade de cartões: " + admin.getId());
                Styles.setLabelStyle(labelID);
                empilhamentoPanel.add(labelID);
            }

            if(Sistema.getTipoUsuario().equals("Cliente")) {
                JButton cadastrarCartao = CriaBotaoPreDefinido("Cadastrar cartao", 200, 25, 16);
                JButton realizarPagamento = CriaBotaoPreDefinido("Realizar pagamento", 200, 25, 16);
                JButton editarDados = CriaBotaoPreDefinido("Editar Dados", 200, 25, 16);

                cadastrarCartao.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_CARTAO));
                realizarPagamento.addActionListener(e -> {
                    gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_PAGAMENTO);
                });
                editarDados.addActionListener((e -> gerenciador.trocarParaTela(GerenciadorInterfaces.EDITOR_DADOS_USUARIO)));
                empilhamentoPanel.add(cadastrarCartao);
                if(Sistema.getCliente().getCartoes().length > 0)empilhamentoPanel.add(realizarPagamento);
                empilhamentoPanel.add(editarDados);
            }
        }
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(400, 800));
        empilhamentoPanel.setPreferredSize(new Dimension(350, 600));
        centerPanel.setPreferredSize(new Dimension(600, 500));
    }
}
