package Visão;

import Controle.Sistema;
import Modelo.Admin;
import Modelo.Cliente;
import Modelo.Convidado;

import javax.swing.*;
import java.awt.*;

public class InterfaceCatalogo extends InterfaceComum implements Atualizavel{
    public InterfaceCatalogo(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setBackground(Styles.background);

        if(Sistema.catalogo.getSize() > 0) {
            System.out.println(Sistema.catalogo.animes.get(0).getNome());
        } else {
            JLabel mensagem = new JLabel("Não há animes disponíveis no momento");
            Styles.setLabelStyle(mensagem);
            empilhamentoPanel.add(mensagem);
        }

        centerPanel.setSize(new Dimension(Sistema.screenSize.width, Sistema.screenSize.height - 120));
        empilhamentoPanel.setSize(new Dimension(Sistema.screenSize.width, Sistema.screenSize.height - 120));
        centerPanel.add(empilhamentoPanel);
    }
}
