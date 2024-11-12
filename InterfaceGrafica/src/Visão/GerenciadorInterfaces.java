package Visão;

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
    private InterfaceAdicaoAnime adicionadorAnime;
    private InterfaceRegistraCartao registraCartao;
    private CardLayout cardLayout;
    private JPanel telaAtual;

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

    public GerenciadorInterfaces() {
        setTitle("PlaceHolder");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        telaAtual = new JPanel(cardLayout);

        // Inicialize as telas de login e interfaces de usuário
        login = new InterfaceLogin(this);
        verCatalogo = new InterfaceCatalogo(this);
        principal = new InterfacePrincipal(this);
        registro = new InterfaceRegistro(this);
        dadosUsuario = new InterfaceDadosUsuario(this);
        adicionadorAnime = new InterfaceAdicaoAnime(this);
        registraCartao = new InterfaceRegistraCartao(this);

        // Mapa de telas
        telas = new HashMap<>();
        telas.put(LOGIN, login);
        telas.put(CATALOGO, verCatalogo);
        telas.put(PRINCIPAL, principal);
        telas.put(REGISTRO, registro);
        telas.put(DADOS_USUARIO, dadosUsuario);
        telas.put(NOVO_ANIME, adicionadorAnime);
        telas.put(NOVO_CARTAO, registraCartao);

        interfacesAtualizaveis = new ArrayList<>();
        interfacesAtualizaveis.add(principal);
        interfacesAtualizaveis.add(registro);
        interfacesAtualizaveis.add(dadosUsuario);
        interfacesAtualizaveis.add(registraCartao);

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
