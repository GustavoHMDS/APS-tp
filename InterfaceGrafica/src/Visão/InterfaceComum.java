package Visão;

import javax.swing.*;
import java.awt.*;

public class InterfaceComum extends JPanel {
    GerenciadorInterfaces gerenciador;
    JButton home;
    protected JPanel centerPanel;
    public InterfaceComum(GerenciadorInterfaces gerenciador) {
        this.gerenciador = gerenciador;
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Painel superior para o nome do programa e o botão "Home"
        JPanel topPanel = new JPanel(new BorderLayout());
        home = CriaBotaoPreDefinido("PlaceHolder");

        // Alinhando o título à esquerda e o botão à direita no painel superior
        topPanel.add(home, BorderLayout.WEST);

        // Painel central configurado com GridBagLayout para melhor controle de centralização
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setPreferredSize(new Dimension(220, 300)); // Define o tamanho desejado para a área

        // Alinhando o painel central no centro
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.add(centerPanel);

        // Adicionando os painéis à interface principal
        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

    }
    protected JButton CriaBotaoPreDefinido(String nomeBotao) {
        JButton novoBotao = new JButton(nomeBotao);
        // Define o tamanho preferido e máximo para o botão
        Dimension buttonSize = new Dimension(200, 50); // Define a largura e altura específicas
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Aumenta o tamanho da fonte

        novoBotao.setPreferredSize(buttonSize);
        novoBotao.setMaximumSize(buttonSize); // Garante o tamanho máximo
        novoBotao.setFont(buttonFont);
        return novoBotao;
    }
}
