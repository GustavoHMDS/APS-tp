package Visão;
import Controle.Sistema;
import Modelo.Anime;

import javax.swing.*;
import java.awt.*;

public class InterfaceNovaTemporada extends InterfaceComum implements Atualizavel{
    public InterfaceNovaTemporada(GerenciadorInterfaces gerenciador) {
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

        if(Sistema.catalogo.animes.size() > 1) {
            String[] animes = new String[Sistema.catalogo.animes.size()];
            for(int i = 0; i < Sistema.catalogo.animes.size(); i++) {
                animes[i] = Sistema.catalogo.animes.get(i).getNome();
            }
            JLabel labelAnime = new JLabel("Anime: ");
            JComboBox animeSelect = new JComboBox(animes);
            Styles.setLabelStyle(labelAnime);
            animeSelect.setSelectedIndex(0);

            JLabel novaTemporada = new JLabel("Dados nova temporada:");
            JLabel labelNome = new JLabel("Nome: ");
            JTextField campoNome = new JTextField();
            JLabel labelCodigo = new JLabel("Codigo: ");
            JTextField campoCodigo = new JTextField();

            Styles.setLabelStyle(novaTemporada);
            Styles.setLabelStyle(labelNome);
            Styles.setLabelStyle(labelCodigo);
            Styles.setTextFielStyle(campoNome);
            Styles.setTextFielStyle(campoCodigo);

            empilhamentoPanel.add(animeSelect);
            empilhamentoPanel.add(novaTemporada);
            empilhamentoPanel.add(labelNome);
            empilhamentoPanel.add(campoNome);
            empilhamentoPanel.add(labelCodigo);
            empilhamentoPanel.add(campoCodigo);

            JButton adicionarTemporada = CriaBotaoPreDefinido("Adicionar temporada", 250, 30, 16);
            adicionarTemporada.addActionListener(e -> {
                String nome = campoNome.getText();
                int codigo = Integer.parseInt(campoCodigo.getText());
                Anime animeSelecionado = Sistema.catalogo.getAnime(animeSelect.getSelectedItem().toString());
                System.out.println(animeSelecionado.adicionarTemporada(nome, codigo));
                System.out.println(animeSelecionado.getTemporadasQuantidade());
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
