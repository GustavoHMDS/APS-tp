package Visão;
import Controle.Sistema;
import Modelo.Anime;
import Modelo.Temporada;

import javax.swing.*;
import java.awt.*;

public class InterfaceNovoEpisodio extends InterfaceComum implements Atualizavel{
    InterfaceNovoEpisodio(GerenciadorInterfaces gerenciadorInterfaces) {
        super(gerenciadorInterfaces);

        atualizarInterface();
    }

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
            Styles.setTextFielStyle(campoTemporada);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoCodigo);
            Styles.setTextFielStyle(campoPath);

            empilhamentoPanel.add(animeSelect);
            empilhamentoPanel.add(labelTemporada);
            empilhamentoPanel.add(campoTemporada);
            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(labelCodigo);
            empilhamentoPanel.add(campoCodigo);
            empilhamentoPanel.add(labelPath);
            empilhamentoPanel.add(campoPath);

            JButton adicionarEpisodio = CriaBotaoPreDefinido("Adicionar episodio", 250, 30, 16);
            adicionarEpisodio.addActionListener(e -> {
                if(campoNome.getText() == "" || campoTemporada.getText() == "" || campoPath.getText() == "" || campoCodigo.getText() == "") {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos");
                    return;
                }
                int temporada = Integer.parseInt(campoTemporada.getText());
                Anime animeSelecionado = Sistema.catalogo.getAnime(animeSelect.getSelectedItem().toString());
                System.out.println(animeSelecionado.getNome() + " " + animeSelecionado.getTemporadasQuantidade());
                if(temporada > animeSelecionado.getTemporadasQuantidade() || temporada <= 0) {
                    JOptionPane.showMessageDialog(null, "Temporada iválida para o anime " + animeSelecionado.getNome());
                    return;
                }
                String nome = campoNome.getText();
                int codigo = Integer.parseInt(campoCodigo.getText());
                String path = campoPath.getText();
                Temporada temp = animeSelecionado.getTemporada(temporada);
                //System.out.println(temp.getNome());
                temp.adicionarEpisodio(nome, codigo, "episodios/" + path);

                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });
            empilhamentoPanel.add(adicionarEpisodio);
        } else {
            JLabel nenhum = new JLabel("Nenhuma anime adicionado");
            Styles.setLabelStyle(nenhum);
            empilhamentoPanel.add(nenhum);
        }
        centerPanel.add(empilhamentoPanel);
    }
}
