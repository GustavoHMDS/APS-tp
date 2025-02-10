package Visão;

import Controle.SistemaGeral;
import Modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class InterfacePagamento extends InterfaceComum implements Atualizavel {
    JLabel labelDescricao;
    JLabel labelPreco;
    JLabel labelDuracao;
    JLabel labelCartao;
    GridBagConstraints gbc;
    JComboBox<String> comboBoxCartoes;
    JButton confirmar;
    JButton cancelar;
    double preco = 15;
    int cartaoSelecionado = 11;
    public InterfacePagamento(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        //formatador
        gbc = new GridBagConstraints();
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 1; // Linha 1
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //caixa com durações e cartões
        String[] cartaoOptions = {"Escolhe Cartão", "Cartão 1", "Cartão 2", "Cartão 3", "Cartão 4", "Cartão 5", "Cartão 6", "Cartão 7", "Cartão 8", "Cartão 9", "Cartão 10"};
        comboBoxCartoes = new JComboBox<>(cartaoOptions);
        inicializaBox();
        atualizarInterface();
    }
    @Override
    public void atualizarInterface() {
        if(sistema.getUsuario() instanceof Cliente) {
            //informações
            labelDescricao = new JLabel("Faça uma assinatura para ter acesso completo!");
            labelPreco = new JLabel("Preço: " + preco);
            labelDuracao = new JLabel("Duracao: " + ((Cliente) sistema.getUsuario()).getVencimento());
            labelCartao = new JLabel("Cartão: ");


            //botoes
            confirmar = new JButton("Fazer Pagamento");
            cancelar = new JButton("Cancelar");
            inicializaBotoes();

            // aplica estilo
            Styles.setLabelStyle(labelDescricao);
            Styles.setLabelStyle(labelPreco);
            Styles.setLabelStyle(labelDuracao);
            Styles.setLabelStyle(labelCartao);

            // Adicionar ao painel central
            gbc.gridx = 0; // Colunas
            gbc.gridy = 0; // Linhas
            centerPanel.add(labelDescricao, gbc);
            gbc.gridy = 1;
            centerPanel.add(labelDuracao, gbc);
            gbc.gridy = 2;
            centerPanel.add(labelPreco, gbc);
            gbc.gridy = 3;
            centerPanel.add(labelCartao, gbc);
            gbc.gridy = 4;
            centerPanel.add(comboBoxCartoes, gbc);
            gbc.gridy = 5;
            centerPanel.add(confirmar, gbc);
            gbc.gridy = 6;
            centerPanel.add(cancelar, gbc);
        }
        // Atualizar interface
        revalidate();
        repaint();
    }
    private void inicializaBox() {
        comboBoxCartoes.addActionListener(e -> {
            String selecionado = (String) comboBoxCartoes.getSelectedItem();
            System.out.println("Selecionado: " + selecionado);
            // Verificando a seleção
            assert selecionado != null;
            if (selecionado.equals("Escolhe Cartão")) {
                cartaoSelecionado = 11; // "Escolhe Cartão" selecionado
            } else {
                int cartaoNum = Integer.parseInt(selecionado.split(" ")[1]) - 1;
                // Verifique se o sistema.getUsuario() é do tipo Cliente e se o número do cartão é válido
                if (sistema.getUsuario() instanceof Cliente cliente && cartaoNum < cliente.getCartoesQuantidade()) {
                    cartaoSelecionado = cartaoNum;
                    labelCartao.setText("Cartão: " + cliente.getCartoes()[cartaoNum].getNumeroCartao());
                } else {
                    labelCartao.setText("Cartão inválido ou não disponível.");
                    cartaoSelecionado = 11; // Se o cartão não for válido, definimos o valor como -1
                }
            }

            // Exibindo o valor de cartaoSelecionado para depuração
            System.out.println("Cartão selecionado: " + cartaoSelecionado);

            // Atualizar a interface (revalidate e repaint são utilizados para garantir que a interface seja atualizada)
            centerPanel.revalidate();
            centerPanel.repaint();
        });
    }


    private void inicializaBotoes(){
        Styles.setButtonStyle(confirmar);
        Styles.setButtonStyle(cancelar);
        confirmar.addActionListener(e-> {
            if(sistema.fazPagamento(cartaoSelecionado)){
                JOptionPane.showMessageDialog(null, "Pagamento Realizado com sucesso", "Pagamento aprovado", JOptionPane.INFORMATION_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "O pagamento não foi aprovado, confira a validade do cartão", "Pagamento recusado", JOptionPane.ERROR_MESSAGE);
        });
        cancelar.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO));
    }
}

