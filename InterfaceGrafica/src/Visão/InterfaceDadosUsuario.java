package Visão;

import Controle.SistemaGeral;
import Modelo.Admin;
import Modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InterfaceDadosUsuario extends InterfaceComum implements Atualizavel{
    public InterfaceDadosUsuario(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
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
        if(sistema.getUsuario() != null) {
            JLabel labelNome = new JLabel("Nome: " + sistema.getUsuario().getNome());
            JLabel labelCPF = new JLabel("CPF: " + sistema.getUsuario().getCPF());
            JLabel labelEmail = new JLabel("Email: " + sistema.getUsuario().getEmail());
            String senha = "";
            for(int i = 0; i < sistema.getUsuario().getSenha().length(); i++) senha += '*';
            JLabel labelSenha = new JLabel("Senha: " + senha);
            LocalDate data = sistema.getUsuario().getDataNascimento();
            JLabel labelDataNascimento = new JLabel("Data Nascimento: " + data.getDayOfMonth() + "/" + data.getMonthValue() + "/" + data.getYear());
            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCPF);
            Styles.setLabelStyle(labelEmail);
            Styles.setLabelStyle(labelSenha);
            Styles.setLabelStyle(labelDataNascimento);


            //salvarNome.addActionListener(e -> sistema.editarUsuario(sistema.getUsuario().getCPF(), "Nome", campoNome.getText()));
            //salvarEmail.addActionListener(e -> sistema.editarUsuario(sistema.getUsuario().getCPF(), "Email", campoEmail.getText()));
            //salvarSenha.addActionListener(e -> sistema.editarUsuario(sistema.getUsuario().getCPF(), "Senha", campoSenha.getText()));

            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(labelCPF);
            empilhamentoPanel.add(labelEmail);
            empilhamentoPanel.add(labelSenha);
            empilhamentoPanel.add(labelDataNascimento);

            if(sistema.getUsuario() instanceof Cliente cliente) {
                String assinatura = (cliente.isPremium()) ? "Premium" : "Free";
                JLabel labelAssinatura = new JLabel("Assinatura: " + assinatura);
                JLabel labelNumCartoes = new JLabel("Quantidade de cartões: " + cliente.getCartoesQuantidade());

                Styles.setLabelStyle(labelAssinatura);
                Styles.setLabelStyle(labelNumCartoes);
                empilhamentoPanel.add(labelAssinatura);
                empilhamentoPanel.add(labelNumCartoes);

                if(cliente.getCartoesQuantidade() > 0) {
                    String[] str = new String[cliente.getCartoesQuantidade()];
                    for(int i = 0; i < cliente.getCartoesQuantidade(); i++) {
                        str[i] = String.valueOf(i + 1);
                    }
                    JComboBox cartaoSelect = new JComboBox(str);
                    //cartaoSelect.paintComponents();
                    int selecionado = Integer.parseInt(cartaoSelect.getSelectedItem().toString());
                    LocalDate validadeCartao = cliente.getCartoes()[selecionado-1].getValidadeCartao();
                    JLabel labelNumeroCartao = new JLabel("Número do cartão: " + cliente.getCartoes()[selecionado - 1].getNumeroCartao());
                    JLabel labelCodigoCartao = new JLabel("Código do cartão: " + cliente.getCartoes()[selecionado - 1].getCodigoCartao());
                    JLabel labelValidadeCartao = new JLabel("Validade do cartão: " + validadeCartao.getDayOfMonth() + "/" + validadeCartao.getMonthValue() + "/" + validadeCartao.getYear());
                    JButton excluirCartao = CriaBotaoPreDefinido("Excluir cartão", 200, 30, 16);

                    Styles.setLabelStyle(labelNumeroCartao);
                    Styles.setLabelStyle(labelCodigoCartao);
                    Styles.setLabelStyle(labelValidadeCartao);

                    cartaoSelect.addActionListener(e -> {
                        if(cartaoSelect.getSelectedItem() != null) {
                            int cartao = Integer.parseInt(cartaoSelect.getSelectedItem().toString());
                            LocalDate validade = cliente.getCartoes()[cartao - 1].getValidadeCartao();
                            labelNumeroCartao.setText("Número do cartão: " + cliente.getCartoes()[cartao - 1].getNumeroCartao());
                            labelCodigoCartao.setText("Código do cartão: " + cliente.getCartoes()[cartao - 1].getCodigoCartao());
                            labelValidadeCartao.setText("Validade do cartão: " + validade.getDayOfMonth() + "/" + validade.getMonthValue() + "/" + validade.getYear());
                        } else{
                            labelNumeroCartao.setText("");
                            labelCodigoCartao.setText("");
                            labelValidadeCartao.setText("");
                        }
                    });

                    excluirCartao.addActionListener(e -> {
                        if(cliente.getCartoesQuantidade() > 0) {
                            int cartao = Integer.parseInt(cartaoSelect.getSelectedItem().toString());
                            cartaoSelect.removeItemAt(cartaoSelect.getItemCount() - 1);
                            cliente.removerCartao(cartao-1);
                            if(sistema.removerCartao(cliente.getEmail(), cartao)) {
                                labelNumCartoes.setText("Quantidade de cartões: " + cliente.getCartoesQuantidade());
                            }
                            if(cartaoSelect.getItemCount() > 0) {
                                cartaoSelect.setSelectedIndex(0);
                            } else {
                                cartaoSelect.setSelectedIndex(-1);
                                labelNumeroCartao.setText("");
                                labelCodigoCartao.setText("");
                                labelValidadeCartao.setText("");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Você não tem cartões cadastrados para excluir!");
                        }
                    });

                    empilhamentoPanel.add(cartaoSelect);
                    empilhamentoPanel.add(labelNumeroCartao);
                    empilhamentoPanel.add(labelCodigoCartao);
                    empilhamentoPanel.add(labelValidadeCartao);
                    empilhamentoPanel.add(excluirCartao);
                    empilhamentoPanel.add(Box.createVerticalStrut(10));

                }
                    JButton cadastrarCartao = CriaBotaoPreDefinido("Adicionar novo cartao", 200, 30, 16);
                    JButton realizarPagamento = CriaBotaoPreDefinido("Realizar pagamento", 200, 30, 16);
                    JButton editarDados = CriaBotaoPreDefinido("Editar Dados", 200, 30, 16);

                    cadastrarCartao.addActionListener(e -> {
                        if(cliente.getCartoesQuantidade() < 10) {
                            gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_CARTAO);
                        } else {
                            JOptionPane.showMessageDialog(null, "Você já atingiu o número máximo de cartões cadastrados!");
                        }
                    });
                    realizarPagamento.addActionListener(e -> {
                        gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_PAGAMENTO);
                    });
                    editarDados.addActionListener((e -> gerenciador.trocarParaTela(GerenciadorInterfaces.EDITOR_DADOS_USUARIO)));
                    empilhamentoPanel.add(cadastrarCartao);
                    empilhamentoPanel.add(Box.createVerticalStrut(10));
                    if(sistema.getCliente().getCartoes().length > 0){
                        empilhamentoPanel.add(realizarPagamento);
                        empilhamentoPanel.add(Box.createVerticalStrut(10));
                    }
                    empilhamentoPanel.add(editarDados);
                    empilhamentoPanel.add(Box.createVerticalStrut(10));
                }
            }
            else if(sistema.getUsuario() instanceof Admin admin) {
                JLabel labelID = new JLabel("Quantidade de cartões: " + admin.getId());
                Styles.setLabelStyle(labelID);
                empilhamentoPanel.add(labelID);
            }

        JButton excluirConta = CriaBotaoPreDefinido("Excluir conta", 200, 30, 16);
        excluirConta.addActionListener(e -> {
            try {
                sistema.deletarUsuario(sistema.getUsuario().getEmail());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            sistema.logOffUsuario();
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });

        JButton voltar = CriaBotaoPreDefinido("Voltar", 250, 30, 16);
        voltar.addActionListener(e -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
        empilhamentoPanel.add(voltar);

        centerPanel.add(empilhamentoPanel);
        //empilhamentoPanel.setSize(new Dimension(800, SistemaGeral.getScreenSize().height - 120));
        //empilhamentoPanel.setPreferredSize(new Dimension(350, 600));
        centerPanel.setPreferredSize(new Dimension(600, 600));
        centerPanel.setMaximumSize(new Dimension(800, SistemaGeral.getScreenSize().height - 120));
    }
}
