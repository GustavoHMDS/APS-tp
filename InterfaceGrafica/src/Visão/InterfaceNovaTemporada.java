package Visão;
import Controle.SistemaGeral;
import Modelo.Anime;
import Modelo.Temporada;

import javax.swing.*;

public class InterfaceNovaTemporada extends InterfaceComum implements Atualizavel{
    public InterfaceNovaTemporada(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    public void atualizarInterface() {
        super.centerPanel.removeAll();

        JPanel empilhamentoPanel = preparaPainel();

        if(sistema.getCatalogo().animes.size() > 1) {
            String[] animes = new String[sistema.getCatalogo().animes.size()];
            for(int i = 0; i < sistema.getCatalogo().animes.size(); i++) {
                animes[i] = sistema.getCatalogo().animes.get(i).getNome();
            }
            JLabel labelAnime = new JLabel("Anime: ");
            JComboBox animeSelect = new JComboBox(animes);
            Styles.setLabelStyle(labelAnime);
            animeSelect.setSelectedIndex(0);

            JLabel labelNome = new JLabel("Nome: ");
            JTextField campoNome = new JTextField();
            JLabel labelCodigo = new JLabel("Codigo: ");
            JTextField campoCodigo = new JTextField();

            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCodigo);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoCodigo);

            empilhamentoPanel.add(animeSelect);
            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(labelCodigo);
            empilhamentoPanel.add(campoCodigo);

            JButton adicionarTemporada = CriaBotaoPreDefinido("Adicionar temporada", 250, 30, 16);
            adicionarTemporada.addActionListener(e -> {
                String nome = campoNome.getText();
                int codigo = Integer.parseInt(campoCodigo.getText());
                Anime animeSelecionado = sistema.getCatalogo().getAnime(animeSelect.getSelectedItem().toString());
                String path = animeSelecionado.getPath() + "temporada" + codigo + "/";
                Temporada novaTemporada = new Temporada(nome, animeSelecionado, codigo, 0, path);
                animeSelecionado.adicionarTemporada(novaTemporada);
                gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
            });
            empilhamentoPanel.add(adicionarTemporada);
        } else {
            JLabel nenhum = new JLabel("Nenhuma anime adicionado");
            Styles.setLabelStyle(nenhum);
            empilhamentoPanel.add(nenhum);
        }
        centerPanel.add(empilhamentoPanel);
    }
}
