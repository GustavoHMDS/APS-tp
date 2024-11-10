package Visão;

import Controle.Sistema;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class InterfaceComum extends JPanel {
    GerenciadorInterfaces gerenciador;
    JButton home;
    protected JTextField campoDia;
    protected JTextField campoMes;
    protected JTextField campoAno;
    protected JPanel centerPanel;
    public InterfaceComum(GerenciadorInterfaces gerenciador) {
        this.gerenciador = gerenciador;
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Painel superior para o nome do programa e o botão "Home"
        JPanel topPanel = new JPanel(new BorderLayout());
        home = CriaBotaoPreDefinido(Sistema.nomeApp);

        // Ação do botão "Home" herdado
        home.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL));

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
    protected JButton CriaBotaoPreDefinido(String nomeBotao, int largura, int altura, int fonte) {
        JButton novoBotao = new JButton(nomeBotao);
        // Define o tamanho preferido e máximo para o botão
        Dimension buttonSize = new Dimension(largura, altura); // Define a largura e altura específicas
        Font buttonFont = new Font("Arial", Font.BOLD, fonte); // Aumenta o tamanho da fonte

        novoBotao.setPreferredSize(buttonSize);
        novoBotao.setMaximumSize(buttonSize); // Garante o tamanho máximo
        novoBotao.setFont(buttonFont);
        return novoBotao;
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
    protected JPanel painelData(){
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        campoDia = new JTextField(2);
        campoMes = new JTextField(2);
        campoAno = new JTextField(4);

        painelData.add(campoDia);
        painelData.add(new JLabel("/"));
        painelData.add(campoMes);
        painelData.add(new JLabel("/"));
        painelData.add(campoAno);

        return painelData;
    }
    protected JPanel painelData(LocalDate data){
        JPanel painelData = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        campoDia = new JTextField(data.getDayOfMonth());
        campoMes = new JTextField(data.getMonthValue());
        campoAno = new JTextField(data.getYear());

        painelData.add(campoDia);
        painelData.add(new JLabel("/"));
        painelData.add(campoMes);
        painelData.add(new JLabel("/"));
        painelData.add(campoAno);

        return painelData;
    }
}
