package Visão;

import Controle.Sistema;
import Modelo.Anime;

import javax.swing.*;
import java.awt.*;

public class InterfaceAdicaoAnime extends InterfaceComum implements Atualizavel{
    public InterfaceAdicaoAnime(GerenciadorInterfaces gerenciador, Sistema sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();
        centerPanel.setSize(new Dimension(Sistema.getScreenSize().width, Sistema.getScreenSize().height - 120));
        centerPanel.setLayout(new GridLayout(6, 1, 10, 15));

        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setSize(centerPanel.getSize());
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        empilhamentoPanel.setBackground(Styles.background);
        JLabel animeNome = new JLabel("Nome do anime: ");
        JTextField campoAnimeNome = new JTextField(20);
        JLabel animeID = new JLabel("ID do anime");
        JTextField campoAnimeID = new JTextField(20);

        animeID.setSize(600, 50);
        animeID.setAlignmentX(50);
        JButton salvar = CriaBotaoPreDefinido("Salvar", 350, 30, 16);
        JButton cancelar = CriaBotaoPreDefinido("Cancelar", 350, 30, 16);

        salvar.addActionListener(e -> {
            String nome = campoAnimeNome.getText();
            int id = Integer.parseInt(campoAnimeID.getText());
            if(!nome.isEmpty()) {
                String path = "animes/" + nome.trim().replace("\\s+", "-") + "/";
                Anime novoAnime = new Anime(nome, id, 0, path);
                sistema.getCatalogo().adicionaAnime(novoAnime);
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            }
        });

        cancelar.addActionListener(e -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });

        Styles.setLabelStyle(animeNome);
        Styles.setLabelStyle(animeID);
        Styles.setTextFielStyle(campoAnimeNome);
        Styles.setTextFielStyle(campoAnimeID);

        empilhamentoPanel.add(animeNome);
        empilhamentoPanel.add(campoAnimeNome);
        empilhamentoPanel.add(Box.createVerticalStrut(10));
        empilhamentoPanel.add(animeID);
        empilhamentoPanel.add(campoAnimeID);
        empilhamentoPanel.add(Box.createVerticalStrut(10));
        empilhamentoPanel.add(salvar);
        empilhamentoPanel.add(Box.createVerticalStrut(10));
        empilhamentoPanel.add(cancelar);
        empilhamentoPanel.add(Box.createVerticalStrut(10));

        centerPanel.add(empilhamentoPanel);
    }
}
