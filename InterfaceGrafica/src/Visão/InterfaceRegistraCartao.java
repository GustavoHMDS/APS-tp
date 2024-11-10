package Visão;

import Controle.Sistema;
import Modelo.Cliente;

import javax.swing.*;
import java.awt.*;

public class InterfaceRegistraCartao extends InterfaceComum implements Atualizavel {
    public InterfaceRegistraCartao(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = Sistema.getTipoUsuario();
        super.centerPanel.removeAll();
        Cliente cliente = Sistema.getCliente();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        JLabel labelNumeroCartao;
        JTextField campoNumeroCartao;
        JLabel labelcodigoCartao;
        JTextField campoCodigoCartao;
        JLabel labelDataValidade;
        JPanel painelDataValidade;
        if(cliente != null && cliente.getCartaoPagamento() != null) {
            labelNumeroCartao = new JLabel("Numero do cartao: " + cliente.getCartaoPagamento().getNumeroCartao());
            labelcodigoCartao = new JLabel("Codigo do cartao: " + cliente.getCartaoPagamento().getCodigoCartao());
            labelDataValidade = new JLabel("Data de validade: " + cliente.getCartaoPagamento().getCodigoCartao());
            campoNumeroCartao = new JTextField(String.valueOf(cliente.getCartaoPagamento().getNumeroCartao()));
            campoCodigoCartao = new JTextField(cliente.getCartaoPagamento().getCodigoCartao());
            painelDataValidade = painelData(cliente.getCartaoPagamento().getValidadeCartao());
        } else {
            labelNumeroCartao = new JLabel("Numero do cartao: ");
            labelcodigoCartao = new JLabel("Codigo do cartao: ");
            labelDataValidade = new JLabel("Data de validade: ");
            campoNumeroCartao = new JTextField(15);
            campoCodigoCartao = new JTextField(15);
            painelDataValidade = painelData();
        }


        JButton salvarCartao = CriaBotaoPreDefinido("Salvar", 200, 25, 16);
        JButton cancelar = CriaBotaoPreDefinido("Cancelar", 200, 25, 16);
        salvarCartao.addActionListener(e -> {
            Sistema.trocaCartaoUsuario(campoNumeroCartao.getText(), campoCodigoCartao.getText(), campoDia.getText(), campoMes.getText(), campoAno.getText());
            gerenciador.trocarParaTela(gerenciador.DADOS_USUARIO);
        });
        cancelar.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO));

        empilhamentoPanel.add(labelNumeroCartao);
        empilhamentoPanel.add(campoNumeroCartao);
        empilhamentoPanel.add(labelcodigoCartao);
        empilhamentoPanel.add(campoCodigoCartao);
        empilhamentoPanel.add(labelDataValidade);
        empilhamentoPanel.add(painelDataValidade);
        empilhamentoPanel.add(salvarCartao);
        empilhamentoPanel.add(cancelar);

        centerPanel.add(empilhamentoPanel);
        empilhamentoPanel.setMaximumSize(new Dimension(400, 500));
        empilhamentoPanel.setPreferredSize(new Dimension(350, 400));
        centerPanel.setPreferredSize(new Dimension(600, 500));
    }
}
