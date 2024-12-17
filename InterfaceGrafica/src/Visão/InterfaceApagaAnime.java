package Visão;

import Controle.Sistema;
import Modelo.Anime;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class InterfaceApagaAnime extends InterfaceComum implements Atualizavel{
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
            String[] animes = new String[Sistema.catalogo.animes.size()];
            for(int i = 0; i < Sistema.catalogo.animes.size(); i++) {
                animes[i] = Sistema.catalogo.animes.get(i).getNome();
            }
            JLabel labelAnime = new JLabel("Anime: ");
            JComboBox animeSelect = new JComboBox(animes);
            Styles.setLabelStyle(labelAnime);
            animeSelect.setSelectedIndex(0);

            JLabel labelTemporada = new JLabel("Temporada");
            JTextField campoTemporada = new JTextField();
            JLabel labelEpisodio = new JLabel("Episodio");
            JTextField campoEpisodio = new JTextField();

            Styles.setLabelStyle(labelTemporada);
            Styles.setLabelStyle(labelEpisodio);
            Styles.setTextFielStyle(campoTemporada);
            Styles.setTextFielStyle(campoEpisodio);

            empilhamentoPanel.add(animeSelect);
            empilhamentoPanel.add(labelTemporada);
            empilhamentoPanel.add(campoTemporada);
            empilhamentoPanel.add(labelEpisodio);
            empilhamentoPanel.add(campoEpisodio);

            JButton excluir = CriaBotaoPreDefinido("Excluir", 250, 50, 16);
            excluir.addActionListener(e -> {
                String animeSelecionado = animeSelect.getSelectedItem().toString();
                Anime anime = Sistema.catalogo.getAnime(animeSelecionado);
                if(campoEpisodio.getText().isEmpty() && campoTemporada.getText().isEmpty()) { // apaga anime
                    System.out.println("Apaga anime");
                    Sistema.deletarPastaRecursivamente(new File(anime.getPath()));
                    Sistema.catalogo.removeAnime(animeSelecionado);
                } else if(campoEpisodio.getText().isEmpty() && !campoTemporada.getText().isEmpty()) { // apaga temporada
                    System.out.println("Apaga temporada");
                    int temporada = Integer.parseInt(campoTemporada.getText());
                    if(temporada <= anime.getTemporadasQuantidade() && temporada > 0) {
                        anime.removerTemporada(temporada);
                    }
                } else if(!campoEpisodio.getText().isEmpty() && !campoTemporada.getText().isEmpty()) { // apaga episodio
                    int temporada = Integer.parseInt(campoTemporada.getText());
                    int episodio = Integer.parseInt(campoEpisodio.getText());
                    if(temporada <= anime.getTemporadasQuantidade() && temporada > 0){
                        System.out.println("Apaga episodio");
                        Temporada temp = anime.getTemporada(temporada);
                        System.out.println(temp.getEpisodiosQuantidade());
                        if(episodio <= temp.getEpisodiosQuantidade() && episodio > 0) {
                            temp.removerEpisodio(episodio);
                        }
                    }
                }
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });
            empilhamentoPanel.add(excluir);


        } else {
            JLabel nenhum = new JLabel("Nenhuma anime adicionado");
            Styles.setLabelStyle(nenhum);
            empilhamentoPanel.add(nenhum);
        }

        centerPanel.add(empilhamentoPanel);
    }
}
