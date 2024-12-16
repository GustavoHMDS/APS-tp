package Visão;
import Controle.Sistema;
import Modelo.Anime;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;

public class InterfaceUpdateAnime extends InterfaceComum implements Atualizavel{
    public InterfaceUpdateAnime(GerenciadorInterfaces gerenciador) {
        super(gerenciador);
        atualizarInterface();
    }

    public void atualizarInterface() {
        super.centerPanel.removeAll();

        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel
        empilhamentoPanel.setBackground(new Color(64, 44, 94));
        empilhamentoPanel.setAutoscrolls(true);

        String[] animes = new String[Sistema.catalogo.animes.size()];
        for(int i = 0; i < Sistema.catalogo.animes.size(); i++) {
            animes[i] = Sistema.catalogo.animes.get(i).getNome();
        }
        Temporada temporada = null;
        JLabel labelAnime = new JLabel("Anime: ");
        Styles.setLabelStyle(labelAnime);

        JComboBox animeSelect = new JComboBox(animes);
        JLabel labelTemporada = new JLabel("Temporada: ");
        Styles.setLabelStyle(labelTemporada);

        empilhamentoPanel.add(animeSelect);
        animeSelect.setSelectedIndex(0);
        Anime anime = Sistema.catalogo.animes.get(0);
        if(anime.getTemporadasQuantidade() > 0) {
            String[] temporadas = new String[anime.getTemporadasQuantidade()];
            for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
                temporadas[i] = "Temporada " + i;
            }
            JComboBox temporadaSelect = new JComboBox(temporadas);
            empilhamentoPanel.add(temporadaSelect);
        }
        centerPanel.add(empilhamentoPanel);
    }
}
