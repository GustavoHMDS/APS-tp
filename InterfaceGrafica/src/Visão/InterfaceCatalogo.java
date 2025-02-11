package Visão;

import Controle.SistemaGeral;
import Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

public class InterfaceCatalogo extends InterfaceComum implements Atualizavel {
    String pathAnime;
    String pathTemp;

    public InterfaceCatalogo(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    @Override
    public void atualizarInterface() {
        super.centerPanel.removeAll();
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setBackground(Styles.background);
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));

        // Cria JLabel para exibir informações do anime
        JLabel animeInfoLabel = new JLabel("Selecione um anime para ver os detalhes.");
        Styles.setLabelStyle(animeInfoLabel);
        animeInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        empilhamentoPanel.add(animeInfoLabel);

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
        JButton assistirButton = CriaBotaoPreDefinido("Assistir");
        JLabel episodioInfoLabel = new JLabel("");
        Styles.setLabelStyle(episodioInfoLabel);
        episodioInfoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton voltar = CriaBotaoPreDefinido("Voltar");

        if (!sistema.getCatalogo().animes.isEmpty()) {
            // Preenche a ComboBox de animes
            for (int i = 0; i < sistema.getCatalogo().animes.size(); i++) {
                animeComboBox.addItem(sistema.getCatalogo().animes.get(i).getNome());
            }

            // Listener para atualizar informações do anime e temporadas
            animeComboBox.addActionListener(e -> {
                String nomeSelecionado = (String) animeComboBox.getSelectedItem();
                if (nomeSelecionado != null) {
                    Anime anime = sistema.getCatalogo().getAnime(nomeSelecionado);
                    if (anime != null) {
                        animeInfoLabel.setText(
                                "<html><b>Nome:</b> " + anime.getNome() +
                                        "<br><b>Temporadas:</b> " + anime.getTemporadasQuantidade() + "</html>"
                        );

                        // Salva o path do anime
                        pathAnime = anime.getPath();

                        // Atualiza a ComboBox de temporadas
                        temporadaComboBox.removeAllItems();
                        episodioComboBox.removeAllItems();

                        File temporadaDir = new File(anime.getPath());
                        if (temporadaDir.exists() && temporadaDir.isDirectory()) {
                            File[] temporadas = temporadaDir.listFiles(File::isDirectory);
                            if (temporadas != null) {
                                for (File temporada : temporadas) {
                                    if (temporada.getName().matches("temporada\\d+")) {
                                        temporadaComboBox.addItem(temporada.getName());
                                    }
                                }
                                if (temporadaComboBox.getItemCount() > 0) {
                                    temporadaComboBox.setSelectedIndex(0);
                                    pathTemp = new File(anime.getPath(), (String) temporadaComboBox.getSelectedItem()).getPath();
                                    temporadaInfoLabel.setText("Temporada: " + temporadaComboBox.getSelectedItem());
                                }
                            } else {
                                temporadaComboBox.addItem("Vazio");
                                temporadaInfoLabel.setText("Temporada: Vazio");
                            }
                        }
                    }
                }
            });

            // Listener para atualizar episódios quando a temporada é selecionada
            temporadaComboBox.addActionListener(e -> {
                String temporadaSelecionada = (String) temporadaComboBox.getSelectedItem();
                if (temporadaSelecionada != null) {
                    Anime anime = sistema.getCatalogo().getAnime((String) animeComboBox.getSelectedItem());
                    if (anime != null) {
                        File temporadaDir = new File(anime.getPath(), temporadaSelecionada);
                        File episodiosDir = new File(temporadaDir, "Episodios");
                        episodioComboBox.removeAllItems();  // Limpa os itens da ComboBox de episódios

                        if (temporadaDir.exists() && temporadaDir.isDirectory()) {
                            // Verifica se existem arquivos .txt para episódios dentro da pasta da temporada
                            File[] episodiosTxt = temporadaDir.listFiles((dir, name) -> name.endsWith(".txt") && !name.equals("dados.txt"));
                            if (episodiosTxt != null && episodiosTxt.length > 0) {
                                for (File episodioTxt : episodiosTxt) {
                                    String episodioNome = episodioTxt.getName().replace(".txt", "");
                                    episodioComboBox.addItem(episodioNome);
                                }
                            } else {
                                episodioComboBox.addItem("Nenhum episódio disponível");
                            }
                        } else {
                            episodioComboBox.addItem("Nenhum episódio disponível");
                        }
                    }
                }
            });


            // Listener para assistir o episódio
            assistirButton.addActionListener(e -> {
                if(!sistema.getTipoUsuario().equals("Guest") || sistema.getUsuario() instanceof Cliente cliente && cliente.isPremium()){
                    String episodioSelecionado = (String) episodioComboBox.getSelectedItem();
                    if (episodioSelecionado != null) {
                        Anime anime = sistema.getCatalogo().getAnime((String) animeComboBox.getSelectedItem());
                        String temporadaSelecionada = (String) temporadaComboBox.getSelectedItem();
                        if (anime != null) {
                            File temporadaDir = new File(anime.getPath(), temporadaSelecionada);
                            File episodiosDir = new File(temporadaDir, "Episodios");
                            File episodioTxt = new File(temporadaDir, episodioSelecionado + ".txt");

                            if (episodioTxt.exists()) {
                                // Aqui você pode ler o arquivo .txt para obter o caminho do .mp4 correspondente
                                // Por exemplo, você pode armazenar os dados do episódio no próprio objeto Episodio
                                try{
                                    List<String> episodioLinha = Files.readAllLines(episodioTxt.toPath());
                                    String[] path =  episodioLinha.get(2).split(": ");
                                    sistema.play(path[1]);

                                } catch(Exception ee) {
                                    System.out.println("Erro na leitura de path");
                                }
                            }
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Usuário deve ser um cliente com assinatura premium", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            });
            voltar.addActionListener(e -> gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL));

            // Seleção inicial do primeiro anime
            animeComboBox.setSelectedIndex(0);
            animeComboBox.getActionListeners()[0].actionPerformed(null);
        } else {
            // Caso não haja animes disponíveis
            animeComboBox.addItem("Não há animes disponíveis");
            animeComboBox.setEnabled(false);
            temporadaComboBox.addItem("Vazio");
            temporadaComboBox.setEnabled(false);
            episodioComboBox.addItem("Vazio");
            episodioComboBox.setEnabled(false);
            assistirButton.setEnabled(false);
            animeInfoLabel.setText("Nenhum anime disponível para exibição.");
            temporadaInfoLabel.setText("Temporada: Vazio");
            episodioInfoLabel.setText("Episódio: Vazio");
        }

        // Adiciona componentes ao painel
        animeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        temporadaComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        episodioComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        assistirButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        empilhaComponentes(
                empilhamentoPanel, animeComboBox, temporadaComboBoxLabel, temporadaComboBox,
                temporadaInfoLabel, episodioComboBoxLabel, episodioComboBox, episodioInfoLabel,
                assistirButton, voltar
        );

        // Configuração dos tamanhos
        centerPanel.setSize(new Dimension(SistemaGeral.getScreenSize().width, SistemaGeral.getScreenSize().height - 120));
        empilhamentoPanel.setSize(new Dimension(SistemaGeral.getScreenSize().width, SistemaGeral.getScreenSize().height - 120));

        // Adiciona o painel principal
        centerPanel.add(empilhamentoPanel);

        // Atualiza a interface
        super.centerPanel.revalidate();
        super.centerPanel.repaint();
    }
}
