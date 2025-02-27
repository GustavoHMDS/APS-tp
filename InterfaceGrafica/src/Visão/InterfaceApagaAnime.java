package Visão;

import Controle.SistemaGeral;
import Modelo.Anime;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;

public class InterfaceApagaAnime extends InterfaceComum implements Atualizavel{
    Anime anime;
    Temporada temporada;
    int episodioIndex, temporadaIndex;
    InterfaceApagaAnime(GerenciadorInterfaces gerenciadorInterfaces, SistemaGeral sistema){
        super(gerenciadorInterfaces, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();

        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(Styles.background);
        if(sistema.getCatalogo().getSize() > 0) {
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

            // Texto acima da JComboBox de episódios
            JLabel episodioComboBoxLabel = new JLabel("Selecione um episódio:");
            Styles.setLabelStyle(episodioComboBoxLabel);
            episodioComboBoxLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // ComboBox para episódios
            JComboBox<String> episodioComboBox = new JComboBox<>();
            JButton excluirButton = new JButton("Excluir");
            Styles.setButtonStyle(excluirButton);
            excluirButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            JButton cancelarButton = CriaBotaoPreDefinido("Cancelar");

            for(int i = 0; i < sistema.getCatalogo().getSize(); i++) {
                animeComboBox.addItem(sistema.getCatalogo().animes.get(i).getNome());
            }
            animeComboBox.setSelectedIndex(0);
            anime = sistema.getCatalogo().animes.get(0);
            temporadaComboBox.addItem("Todas as temporadas");
            for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
                temporadaComboBox.addItem("Temporada " + (i+1));
            }
            episodioComboBox.addItem("Todos os episodios");
            temporadaIndex = episodioIndex = -1;

            animeComboBox.addActionListener(e -> {
                anime = sistema.getCatalogo().getAnime(animeComboBox.getSelectedItem().toString());
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
                    temporada = sistema.buscarTemporada(anime, indice);
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
                    sistema.removerAnime(anime);
                } else if(episodioIndex == -1) {
                    sistema.removerTemporada(anime, temporadaIndex);
                    resetTemporadaComboBox(temporadaComboBox);
                    resetEpisodioComboBox(episodioComboBox);
                } else {
                    sistema.removerEpisodio(temporada, episodioIndex);
                    resetEpisodioComboBox(episodioComboBox);
                }
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });
            cancelarButton.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL));

            empilhaComponentes(
                    empilhamentoPanel, animeComboBoxLabel, animeComboBox, temporadaComboBoxLabel,
                    temporadaComboBox, episodioComboBoxLabel, episodioComboBox, excluirButton, cancelarButton
            );

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
