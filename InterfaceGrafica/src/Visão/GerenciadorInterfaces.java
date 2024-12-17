package Visão;

import Controle.Sistema;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorInterfaces extends JFrame {
    private InterfaceLogin login;
    private InterfacePrincipal principal;
    private InterfaceCatalogo verCatalogo;
    private InterfaceRegistro registro;
    private InterfaceDadosUsuario dadosUsuario;
    private InterfaceEditorDadosUsuario editorDadosUSuario;
    private InterfaceAdicaoAnime adicionadorAnime;
    private InterfaceRegistraCartao registraCartao;
    private CardLayout cardLayout;
    private JPanel telaAtual;
    private InterfacePagamento telaPagamento;
    private InterfaceNovaTemporada novaTemporada;
    private InterfaceNovoEpisodio novoEpisodio;
    private InterfaceApagaAnime apagaAnime;

    // Mapa para armazenar as telas
    private Map<String, JPanel> telas;
    private List<Atualizavel> interfacesAtualizaveis;

    // Constantes para os nomes das telas
    static final String LOGIN = "login";
    static final String CATALOGO = "verCatalogo";
    static final String PRINCIPAL = "principal";
    static final String REGISTRO = "registro";
    static final String DADOS_USUARIO = "dadosUsuario";
    static final String EDITOR_DADOS_USUARIO = "editorDadosUsuario";
    static final String NOVO_ANIME = "adicionadorAnime";
    static final String NOVO_CARTAO = "registraCartao";
    static final String NOVO_PAGAMENTO = "novoPagamento";
    static final String NOVA_TEMPORADA = "novaTemporada";
    static final String NOVO_EPISODIO = "novoEpisodio";
    static final String APAGA_ANIME = "apagaAnime";

    public GerenciadorInterfaces() {
        setTitle("PlaceHolder");
        setSize(Sistema.screenSize.width, Sistema.screenSize.height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        telaAtual = new JPanel(cardLayout);

        // Inicialize as telas de login e interfaces de usuário
        login = new InterfaceLogin(this);
        verCatalogo = new InterfaceCatalogo(this);
        principal = new InterfacePrincipal(this);
        registro = new InterfaceRegistro(this);
        dadosUsuario = new InterfaceDadosUsuario(this);
        editorDadosUSuario = new InterfaceEditorDadosUsuario(this);
        adicionadorAnime = new InterfaceAdicaoAnime(this);
        registraCartao = new InterfaceRegistraCartao(this);
        telaPagamento = new InterfacePagamento(this);
        novaTemporada = new InterfaceNovaTemporada(this);
        novoEpisodio = new InterfaceNovoEpisodio(this);
        apagaAnime = new InterfaceApagaAnime(this);

        // Mapa de telas
        telas = new HashMap<>();
        telas.put(LOGIN, login);
        telas.put(CATALOGO, verCatalogo);
        telas.put(PRINCIPAL, principal);
        telas.put(REGISTRO, registro);
        telas.put(DADOS_USUARIO, dadosUsuario);
        telas.put(EDITOR_DADOS_USUARIO, editorDadosUSuario);
        telas.put(NOVO_ANIME, adicionadorAnime);
        telas.put(NOVO_CARTAO, registraCartao);
        telas.put(NOVO_PAGAMENTO, telaPagamento);
        telas.put(NOVA_TEMPORADA, novaTemporada);
        telas.put(NOVO_EPISODIO, novoEpisodio);
        telas.put(APAGA_ANIME,apagaAnime);

        interfacesAtualizaveis = new ArrayList<>();
        interfacesAtualizaveis.add(login);
        interfacesAtualizaveis.add(principal);
        interfacesAtualizaveis.add(registro);
        interfacesAtualizaveis.add(dadosUsuario);
        interfacesAtualizaveis.add(editorDadosUSuario);
        interfacesAtualizaveis.add(registraCartao);
        interfacesAtualizaveis.add(telaPagamento);
        interfacesAtualizaveis.add(verCatalogo);
        interfacesAtualizaveis.add(novaTemporada);
        interfacesAtualizaveis.add(novoEpisodio);
        interfacesAtualizaveis.add(apagaAnime);

        // Adicione os painéis ao CardLayout
        for (String key : telas.keySet()) {
            telaAtual.add(telas.get(key), key);
        }
        add(telaAtual);
        cardLayout.show(telaAtual, PRINCIPAL); // Exibe inicialmente a tela de login
    }

    // Método para alternar a interface após login
    public void trocarParaTela(String novaTela) {
        // Verifica se o nome da tela existe no mapa
        if (telas.containsKey(novaTela)) {
            System.out.println("Trocando de Tela");
            atualizaInterfaces();
            cardLayout.show(telaAtual, novaTela);
        } else {
            JOptionPane.showMessageDialog(this, "Tipo de tela desconhecido.");
        }
    }

    private void atualizaInterfaces(){
        for (Atualizavel interfaceAtualizavel : interfacesAtualizaveis) {
            interfaceAtualizavel.atualizarInterface();
        }
    }

}
