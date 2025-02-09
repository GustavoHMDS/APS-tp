package Visão;

import Controle.Sistema;
import Modelo.Cartao;
import Modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InterfaceRegistraCartao extends InterfaceComum implements Atualizavel {
    public InterfaceRegistraCartao(GerenciadorInterfaces gerenciador, Sistema sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = sistema.getTipoUsuario();
        super.centerPanel.removeAll();
        Cliente cliente = sistema.getCliente();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        JLabel labelNumeroCartao, labelCodigoCartao, labelDataValidade;
        JTextField campoNumeroCartao, campoCodigoCartao;
        JPanel painelDataValidade;
        /*
        if(cliente != null && cliente.getCartaoPagamento(selecionado - 1) != null) {
            labelNumeroCartao = new JLabel("Numero do cartao: " + cliente.getCartaoPagamento(selecionado-1).getNumeroCartao());
            labelCodigoCartao = new JLabel("Codigo do cartao: " + cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            labelDataValidade = new JLabel("Data de validade: " + cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            campoNumeroCartao = new JTextField(String.valueOf(cliente.getCartaoPagamento(selecionado-1).getNumeroCartao()));
            campoCodigoCartao = new JTextField(cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            painelDataValidade = painelData(cliente.getCartaoPagamento(selecionado-1).getValidadeCartao());
            */

        labelNumeroCartao = new JLabel("Numero do cartao: ");
        labelCodigoCartao = new JLabel("Codigo do cartao: ");
        labelDataValidade = new JLabel("Data de validade: ");
        campoNumeroCartao = new JTextField(15);
        campoCodigoCartao = new JTextField(15);
        painelDataValidade = painelData();

        Styles.setLabelStyle(labelNumeroCartao);Styles.setLabelStyle(labelCodigoCartao);
        Styles.setLabelStyle(labelDataValidade);
        Styles.setTextFielStyle(campoNumeroCartao);
        Styles.setTextFielStyle(campoCodigoCartao);
        painelDataValidade.setBackground(Styles.background);

        JButton salvarCartao = CriaBotaoPreDefinido("Salvar", 1300, 25, 16);
        JButton cancelar = CriaBotaoPreDefinido("Cancelar", 1300, 25, 16);
        salvarCartao.addActionListener(e -> {
            int dia = Integer.parseInt(campoDia.getText());
            int mes = Integer.parseInt(campoMes.getText());
            int ano = Integer.parseInt(campoAno.getText());
            LocalDate data = LocalDate.of(ano, mes, dia);
            Cartao cartao = new Cartao(Integer.parseInt(campoNumeroCartao.getText()), Integer.parseInt(campoCodigoCartao.getText()), data);
            try {
                sistema.adicionarCartao(sistema.getUsuario().getEmail(), cartao);
                sistema.getCliente().adicionarCartao(cartao);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            gerenciador.trocarParaTela(gerenciador.DADOS_USUARIO);
        });
        cancelar.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO));

        //empilhamentoPanel.add(cartaoSelect);
        empilhamentoPanel.add(labelNumeroCartao);
        empilhamentoPanel.add(campoNumeroCartao);
        empilhamentoPanel.add(labelCodigoCartao);
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
