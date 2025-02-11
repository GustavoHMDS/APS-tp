package Visão;
import Controle.SistemaGeral;
import Modelo.Anime;
import Modelo.Episodio;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;

public class InterfaceNovoEpisodio extends InterfaceComum implements Atualizavel{
    Anime anime;
    InterfaceNovoEpisodio(GerenciadorInterfaces gerenciadorInterfaces, SistemaGeral sistema) {
        super(gerenciadorInterfaces, sistema);

        atualizarInterface();
    }

    public void atualizarInterface() {
        super.centerPanel.removeAll();

        JPanel empilhamentoPanel = preparaPainel();

        if(sistema.getCatalogo().animes.size() > 0) {
            String[] animes = new String[sistema.getCatalogo().animes.size()];
            for(int i = 0; i < sistema.getCatalogo().animes.size(); i++) {
                    animes[i] = sistema.getCatalogo().animes.get(i).getNome();
            }
            JLabel labelAnime = new JLabel("Anime: ");
            JComboBox animeSelect = new JComboBox(animes);
            Styles.setLabelStyle(labelAnime);
            animeSelect.setSelectedIndex(0);
            anime = sistema.getCatalogo().animes.get(0);

            JLabel labelTemporada = new JLabel("Temporada");
            JComboBox<String> temporadaComboBox = new JComboBox<>();
            if(anime.getTemporadasQuantidade() == 0) {
                temporadaComboBox.addItem("Nenhuma temporada disponível para adicionar episódio");
            } else {
                for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
                    temporadaComboBox.addItem("Temporada " + (i + 1));
                }
            }
            temporadaComboBox.setSelectedIndex(0);

            animeSelect.addActionListener(e -> {
                anime = sistema.getCatalogo().getAnime(animeSelect.getSelectedItem().toString());
                temporadaComboBox.removeAllItems();
                if(anime.getTemporadasQuantidade() == 0) {
                    temporadaComboBox.addItem("Nenhuma temporada disponível para adicionar episódio");
                } else {
                    for(int i = 0; i < anime.getTemporadasQuantidade(); i++) {
                        temporadaComboBox.addItem("Temporada " + (i + 1));
                    }
                }
                temporadaComboBox.setSelectedIndex(0);
            });
            JLabel labelNome = new JLabel("Nome: ");
            JTextField campoNome = new JTextField();
            JLabel labelCodigo = new JLabel("Codigo: ");
            JTextField campoCodigo = new JTextField();
            JLabel labelPath = new JLabel("Nome do arquivo: (episodio1.mp4)");
            JTextField campoPath = new JTextField();

            Styles.setLabelStyle(labelTemporada);
            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCodigo);
            Styles.setLabelStyle(labelPath);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoCodigo);
            Styles.setTextFielStyle(campoPath);
            JButton adicionarEpisodio = CriaBotaoPreDefinido("Adicionar episódio");
            JButton cancelarButton = CriaBotaoPreDefinido("Cancelar");

            empilhaComponentes(
                    empilhamentoPanel, animeSelect,labelTemporada, temporadaComboBox,
                    labelNome, campoNome, labelCodigo, campoCodigo, labelPath, campoPath,
                    adicionarEpisodio, cancelarButton
            );

            adicionarEpisodio.addActionListener(e -> {
                if(temporadaComboBox.getSelectedItem().toString().equals("Nenhuma temporada disponível para adicionar episódio")) {
                    JOptionPane.showMessageDialog(null, "Não é possível adicionar episódio sem existir uma temporada");
                    return;
                }
                if(campoNome.getText() == "" || campoPath.getText() == "" || campoCodigo.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos");
                    return;
                }
                int temporada = Integer.parseInt(temporadaComboBox.getSelectedItem().toString().split(" ")[1]);
                Anime animeSelecionado = anime;
                System.out.println(animeSelecionado.getNome() + " " + animeSelecionado.getTemporadasQuantidade());
                String nome = campoNome.getText();
                int codigo = Integer.parseInt(campoCodigo.getText());
                String path = campoPath.getText();
                Temporada temp = sistema.buscarTemporada(animeSelecionado, temporada);
                Episodio novoEpisodio = new Episodio(nome, temp, codigo, "episodios/" + path);
                sistema.cadastrarEpisodio(temp, novoEpisodio);

                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });
            cancelarButton.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL));
        } else {
            JLabel nenhum = new JLabel("Nenhuma anime adicionado");
            Styles.setLabelStyle(nenhum);
            empilhamentoPanel.add(nenhum);
        }
        centerPanel.add(empilhamentoPanel);
    }
}
