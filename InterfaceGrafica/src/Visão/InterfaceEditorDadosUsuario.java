package Visão;

import Controle.SistemaGeral;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

import Modelo.Cliente;

public class InterfaceEditorDadosUsuario extends InterfaceComum implements Atualizavel {
    public InterfaceEditorDadosUsuario(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = preparaPainel();
        if(sistema.getUsuario() != null) {
            JLabel labelNome = new JLabel("Nome:");
            JTextField campoNome = new JTextField(sistema.getUsuario().getNome());
            JLabel labelEmail = new JLabel("Email:");
            JTextField campoEmail = new JTextField(sistema.getUsuario().getEmail());
            JLabel labelSenha = new JLabel("Senha:");
            JTextField campoSenha = new JTextField(sistema.getUsuario().getSenha());
            JComboBox cartaoSelect;

            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelEmail);
            Styles.setLabelStyle(labelSenha);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoEmail);
            Styles.setTextFielStyle(campoSenha);

            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(labelEmail);
            empilhamentoPanel.add(campoEmail);
            empilhamentoPanel.add(labelSenha);
            empilhamentoPanel.add(campoSenha);

            JButton salvar = CriaBotaoPreDefinido("Salvar", 200, 25, 16);
            salvar.addActionListener(e -> {
                String[] dados = {campoNome.getText(), campoEmail.getText(),campoSenha.getText()};
                sistema.editarUsuario(sistema.getUsuario().getEmail(),dados);
                gerenciador.trocarParaTela(gerenciador.DADOS_USUARIO);
            });

            JButton cancelar = CriaBotaoPreDefinido("Cancelar", 200, 25, 16);
            cancelar.addActionListener(e -> {
                gerenciador.trocarParaTela((gerenciador.DADOS_USUARIO));
            });

            if(sistema.getUsuario() instanceof Cliente cliente) {
                if(cliente.getCartoesQuantidade() > 0) {
                    String[] str = new String[cliente.getCartoesQuantidade()];
                    for(int i = 0; i < cliente.getCartoesQuantidade(); i++) {
                        str[i] = String.valueOf(i + 1);
                    }
                    cartaoSelect = new JComboBox(str);
                    //cartaoSelect.paintComponents();
                    int selecionado = Integer.parseInt(cartaoSelect.getSelectedItem().toString());
                    LocalDate validadeCartao = cliente.getCartoes()[selecionado-1].getValidadeCartao();
                    JLabel labelNumeroCartao = new JLabel("Número do cartão: ");
                    JTextField campoNumeroCartao = new JTextField("" + cliente.getCartoes()[selecionado - 1].getNumeroCartao());
                    JLabel labelCodigoCartao = new JLabel("Código do cartão: ");
                    JTextField campoCodigoCartao = new JTextField("" + cliente.getCartoes()[selecionado - 1].getCodigoCartao());
                    //JLabel labelValidadeCartao = new JLabel("Validade do cartão" + validadeCartao.getDayOfMonth() + "/" + validadeCartao.getMonthValue() + "/" + validadeCartao.getYear());

                    Styles.setLabelStyle(labelNumeroCartao);
                    Styles.setLabelStyle(labelCodigoCartao);
                    //Styles.setLabelStyle(labelValidadeCartao);
                    Styles.setTextFielStyle(campoNumeroCartao);
                    Styles.setTextFielStyle(campoCodigoCartao);

                    cartaoSelect.addActionListener(e -> {
                        int cartao = Integer.parseInt(cartaoSelect.getSelectedItem().toString());
                        LocalDate validade = cliente.getCartoes()[cartao - 1].getValidadeCartao();
                        campoNumeroCartao.setText("" + cliente.getCartoes()[cartao - 1].getNumeroCartao());
                        campoCodigoCartao.setText("" + cliente.getCartoes()[cartao - 1].getCodigoCartao());
                        //labelValidadeCartao.setText("Validade do cartão: " + validade.getDayOfMonth() + "/" + validade.getMonthValue() + "/" + validade.getYear());
                    });

                    empilhamentoPanel.add(cartaoSelect);
                    empilhamentoPanel.add(labelNumeroCartao);
                    empilhamentoPanel.add(campoNumeroCartao);
                    empilhamentoPanel.add(labelCodigoCartao);
                    empilhamentoPanel.add(campoCodigoCartao);
                    //empilhamentoPanel.add(labelValidadeCartao);
                    salvar.addActionListener(e -> {
                        int cartao = Integer.parseInt((cartaoSelect.getSelectedItem().toString()));
                        cliente.getCartoes()[cartao - 1].setNumeroCartao(Integer.parseInt(campoNumeroCartao.getText()));
                        cliente.getCartoes()[cartao - 1].setCodigoCartao(Integer.parseInt(campoCodigoCartao.getText()));
                        sistema.editarCartao(cliente.getEmail(), cartao,Integer.parseInt(campoNumeroCartao.getText()), Integer.parseInt(campoCodigoCartao.getText()));
                    });
                }
            }

            empilhamentoPanel.add(salvar);
            empilhamentoPanel.add(cancelar);
        }
        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setSize(new Dimension(600, SistemaGeral.getScreenSize().height - 120));
        //empilhamentoPanel.setPreferredSize(new Dimension(350, 400));
        centerPanel.setPreferredSize(new Dimension(600, 500));
        centerPanel.setMaximumSize(new Dimension(800, SistemaGeral.getScreenSize().height - 120));
    }
}
