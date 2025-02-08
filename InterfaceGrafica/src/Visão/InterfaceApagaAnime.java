package Visão;

import Controle.Sistema;
import Modelo.Anime;
import Modelo.Cliente;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class InterfaceApagaAnime extends InterfaceComum implements Atualizavel{
    Anime anime;
    Temporada temporada;
    int episodioIndex, temporadaIndex;
    InterfaceApagaAnime(GerenciadorInterfaces gerenciadorInterfaces){
        super(gerenciadorInterfaces);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();

        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        empilhamentoPanel.setAutoscrolls(true);
        if(Sistema.catalogo.animes.size() > 1) {

            // Texto acima da JComboBox de animes
            JLabel animeComboBoxLabel = new JLabel("Selecione um anime:");
            Styles.setLabelStyle(animeComboBoxLabel);
            animeComboBoxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            empilhamentoPanel.add(animeComboBoxLabel);

            // ComboBox para os nomes dos animes
            JComboBox<String> animeComboBox = new JComboBox<>();

            // Texto acima da JComboBox de temporadas
            JLabel temporadaComboBoxLabel = new JLabel("Selecione uma temporada:");
            Styles.setLabelStyle(temporadaComboBoxLabel);
            temporadaComboBoxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // ComboBox para temporadas
            JComboBox<String> temporadaComboBox = new JComboBox<>();
            JLabel temporadaInfoLabel = new JLabel("");
            Styles.setLabelStyle(temporadaInfoLabel);
            temporadaInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Texto acima da JComboBox de episódios
            JLabel episodioComboBoxLabel = new JLabel("Selecione um episódio:");
            Styles.setLabelStyle(episodioComboBoxLabel);
            episodioComboBoxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // ComboBox para episódios
            JComboBox<String> episodioComboBox = new JComboBox<>();
            JButton excluirButton = new JButton("Excluir");
            JLabel episodioInfoLabel = new JLabel("");
            Styles.setLabelStyle(episodioInfoLabel);
            episodioInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            for(int i = 0; i < Sistema.catalogo.animes.size(); i++) {
                animeComboBox.addItem(Sistema.catalogo.animes.get(i).getNome());
            }
            animeComboBox.setSelectedIndex(0);
            anime = Sistema.catalogo.animes.get(0);
            temporadaComboBox.addItem("Todas as temporadas");
            for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
                temporadaComboBox.addItem("Temporada " + (i+1));
            }
            episodioComboBox.addItem("Todos os episodios");
            temporadaIndex = episodioIndex = -1;

            animeComboBox.addActionListener(e -> {
                anime = Sistema.catalogo.getAnime(animeComboBox.getSelectedItem().toString());
                resetTemporadaComboBox(temporadaComboBox);
                resetEpisodioComboBox(episodioComboBox);
            });

            temporadaComboBox.addActionListener(e -> {
                String temporadaSelecionada = (String )temporadaComboBox.getSelectedItem();
                if(temporadaSelecionada == null) return;
                if(temporadaSelecionada.equals("Todas as temporadas")) {
                    temporada = null;
                    temporadaIndex = -1;
                    resetEpisodioComboBox(episodioComboBox);
                } else {
                    int indice = Integer.parseInt(temporadaSelecionada.split(" ")[1]);
                    temporada = anime.getTemporada(indice);
                    temporadaIndex = indice;
                    episodioComboBox.removeAllItems();
                    episodioComboBox.addItem("Todos os episodios");
                    for(int i = 0; i < temporada.getEpisodiosQuantidade(); i++) {
                        episodioComboBox.addItem("Episodio " + (i+1));
                    }
                    episodioComboBox.setSelectedIndex(0);
                    episodioIndex = -1;
                }
            });

            episodioComboBox.addActionListener(e -> {
                String episodioSelecionado = (String) episodioComboBox.getSelectedItem();
                if(episodioSelecionado == null) return;
                if(episodioSelecionado.equals("Todos os episodios")) {
                    episodioIndex = -1;
                } else {
                    episodioIndex = Integer.parseInt(episodioSelecionado.split(" ")[1]);
                }
            });

            excluirButton.addActionListener(e -> {
                if(anime == null) return;
                if(temporadaIndex == -1) {
                    Sistema.catalogo.removeAnime(anime.getNome());
                } else if(episodioIndex == -1) {
                    anime.removerTemporada(temporadaIndex);
                } else {
                    temporada.removerEpisodio(episodioIndex);
                }
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });

            empilhamentoPanel.add(animeComboBoxLabel);
            empilhamentoPanel.add(animeComboBox);
            empilhamentoPanel.add(temporadaComboBoxLabel);
            empilhamentoPanel.add(temporadaComboBox);
            empilhamentoPanel.add(temporadaInfoLabel);
            empilhamentoPanel.add(episodioComboBoxLabel);
            empilhamentoPanel.add(episodioComboBox);
            empilhamentoPanel.add(episodioInfoLabel);
            empilhamentoPanel.add(excluirButton);


        } else {
            JLabel nenhum = new JLabel("Nenhuma anime adicionado");
            Styles.setLabelStyle(nenhum);
            empilhamentoPanel.add(nenhum);
        }

        centerPanel.add(empilhamentoPanel);
    }

    private void resetTemporadaComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Todas as temporadas");
        for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
            comboBox.addItem("Temporada " + (i+1));
        }
        this.temporada = null;
        this.temporadaIndex = -1;
    }

    private void resetEpisodioComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        comboBox.addItem("Todos os episodios");
        this.episodioIndex = -1;
    }
}
