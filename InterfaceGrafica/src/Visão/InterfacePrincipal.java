package Visão;

import Controle.SistemaGeral;

import javax.swing.*;
import java.awt.*;

public class InterfacePrincipal extends InterfaceComum implements Atualizavel {
    JButton catalogo, dadosUsuario, sair, login, registrar, novoAnime, novaTemporada, novoEpisodio, apagarConteudo;

    public InterfacePrincipal(GerenciadorInterfaces gerenciador, SistemaGeral sistema) {
        super(gerenciador, sistema);
        atualizarInterface();
    }

    private void InicializaBotoesGerais() {
        catalogo.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.CATALOGO);
        });
    }

    private void InicializaBotoesUsuario() {
        dadosUsuario.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO);
        });
        sair.addActionListener(_ -> {
            sistema.logOffUsuario();
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
    }

    private void InicializaBotoesAdmin() {
        InicializaBotoesUsuario();
        registrar.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.REGISTRO);
        });
        novoAnime.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_ANIME);
        });
        novaTemporada.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.NOVA_TEMPORADA);
        });
        novoEpisodio.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_EPISODIO);
        });
        apagarConteudo.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.APAGA_ANIME);
        });
    }

    private void InicializaBotoesGuest() {
        login.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.LOGIN);
        });
        registrar.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.REGISTRO);
        });
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = sistema.getTipoUsuario();
        super.centerPanel.removeAll();
        centerPanel.setBackground(Color.RED);
        centerPanel.setSize(new Dimension(SistemaGeral.getScreenSize().width, SistemaGeral.getScreenSize().height - 120));


        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setBackground(Styles.background);

        // Adicione os botões com base no tipo de usuário
        catalogo = CriaBotaoPreDefinido("Ver catálogo");
        InicializaBotoesGerais();
        empilhamentoPanel.add(catalogo);
        switch (tipoUsuario) {
            case "Cliente":
                dadosUsuario = CriaBotaoPreDefinido("Meus dados");
                sair = CriaBotaoPreDefinido("Sair");
                empilhaComponentes(empilhamentoPanel, dadosUsuario, sair);
                InicializaBotoesUsuario();
                break;
            case "Admin":
                dadosUsuario = CriaBotaoPreDefinido("Meus dados");
                registrar = CriaBotaoPreDefinido("Registrar novo administrador");
                novoAnime = CriaBotaoPreDefinido("Adicionar Anime");
                novaTemporada = CriaBotaoPreDefinido("Adicionar Temporada");
                novoEpisodio = CriaBotaoPreDefinido("Adicionar Episodio");
                apagarConteudo = CriaBotaoPreDefinido("Apagar Conteudo");
                sair = CriaBotaoPreDefinido("Sair");
                empilhaComponentes(empilhamentoPanel ,dadosUsuario, registrar, novoAnime);
                if(!sistema.getCatalogo().animes.isEmpty()){
                    empilhaComponentes(empilhamentoPanel, novaTemporada, novoEpisodio, apagarConteudo);
                }
                empilhaComponentes(empilhamentoPanel, sair);
                InicializaBotoesAdmin();
                break;
            case "Guest":
                login = CriaBotaoPreDefinido("Login");
                registrar = CriaBotaoPreDefinido("Registrar");
                empilhaComponentes(empilhamentoPanel, login, registrar);
                InicializaBotoesGuest();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido!");
                return;
        }

        // Centraliza o painel de empilhamento no centerPanel usando GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(empilhamentoPanel, gbc);

        // Revalida e repinta
        revalidate();
        repaint();
    }
}
