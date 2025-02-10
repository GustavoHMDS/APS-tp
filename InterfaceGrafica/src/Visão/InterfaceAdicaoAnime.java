package Visão;

import Controle.SistemaGeral;
import Modelo.Anime;

import javax.swing.*;
import java.awt.*;

public class InterfaceAdicaoAnime extends InterfaceComum implements Atualizavel{
    public InterfaceAdicaoAnime(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();
        centerPanel.setSize(new Dimension(SistemaGeral.getScreenSize().width, SistemaGeral.getScreenSize().height - 120));
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
                sistema.cadastrarAnime(nome, id);
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

        empilhaComponentes(
                empilhamentoPanel, animeNome, campoAnimeNome,
                animeID, campoAnimeID, salvar, cancelar
        );
        centerPanel.add(empilhamentoPanel);
    }
}
