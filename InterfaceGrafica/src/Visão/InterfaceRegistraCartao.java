package Visão;

import Controle.Sistema;
import Modelo.Cartao;
import Modelo.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        String[] seletores = new String[10];
        int qtd = (cliente == null) ? 2 : cliente.getCartoes().length;
        for(int i = 0; i < qtd; i++) seletores[i] = String.valueOf(i+1);
        JComboBox cartaoSelect = new JComboBox(seletores);
        int selecionado = 1;
        JLabel labelNumeroCartao;
        JTextField campoNumeroCartao;
        JLabel labelcodigoCartao;
        JTextField campoCodigoCartao;
        JLabel labelDataValidade;
        JPanel painelDataValidade;
        if(cliente != null && cliente.getCartaoPagamento(selecionado - 1) != null) {
            labelNumeroCartao = new JLabel("Numero do cartao: " + cliente.getCartaoPagamento(selecionado-1).getNumeroCartao());
            labelcodigoCartao = new JLabel("Codigo do cartao: " + cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            labelDataValidade = new JLabel("Data de validade: " + cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            campoNumeroCartao = new JTextField(String.valueOf(cliente.getCartaoPagamento(selecionado-1).getNumeroCartao()));
            campoCodigoCartao = new JTextField(cliente.getCartaoPagamento(selecionado-1).getCodigoCartao());
            painelDataValidade = painelData(cliente.getCartaoPagamento(selecionado-1).getValidadeCartao());
        } else {
            labelNumeroCartao = new JLabel("Numero do cartao: ");
            labelcodigoCartao = new JLabel("Codigo do cartao: ");
            labelDataValidade = new JLabel("Data de validade: ");
            campoNumeroCartao = new JTextField(15);
            campoCodigoCartao = new JTextField(15);
            painelDataValidade = painelData();
        }
        labelNumeroCartao.setForeground(new Color(254, 244, 129));
        labelcodigoCartao.setForeground(new Color(254, 244, 129));
        labelDataValidade.setForeground(new Color(254, 244, 129));
        campoNumeroCartao.setBackground(new Color(Transparency.TRANSLUCENT));
        campoNumeroCartao.setForeground(new Color(255,255,255));
        campoNumeroCartao.setBorder(null);
        campoCodigoCartao.setBackground(new Color(Transparency.TRANSLUCENT));
        campoCodigoCartao.setForeground(new Color(255,255,255));
        campoCodigoCartao.setBorder(null);

        JButton salvarCartao = CriaBotaoPreDefinido("Salvar", 1300, 25, 16);
        JButton cancelar = CriaBotaoPreDefinido("Cancelar", 1300, 25, 16);
        salvarCartao.addActionListener(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate data = LocalDate.parse(campoDia.getText() + campoMes.getText() + campoAno.getText(), formatter);
            Cartao cartao = new Cartao(Integer.parseInt(campoNumeroCartao.getText()), Integer.parseInt(campoCodigoCartao.getText()), data);
            try {
                Sistema.adicionarCartao(Sistema.usuario.getEmail(), cartao);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            gerenciador.trocarParaTela(gerenciador.DADOS_USUARIO);
        });
        cancelar.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO));

        empilhamentoPanel.add(cartaoSelect);
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
