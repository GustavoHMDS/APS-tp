package Visual;

import Console.Sistema;

import javax.swing.*;
import java.awt.*;

public class InterfacePrincipal extends JPanel implements Atualizavel{
    GerenciadorInterfaces gerenciador;
    JButton home, catalogo, dadosUsuario, sair, login, registrar, novoAnime;
    private JPanel centerPanel;

    public InterfacePrincipal(GerenciadorInterfaces gerenciador) {
        this.gerenciador = gerenciador;
        setSize(900, 600);
        setLayout(new BorderLayout());

        // Painel superior para o nome do programa e o botão "Home"
        JPanel topPanel = new JPanel(new BorderLayout());
        home = CriaBotaoPreDefinido("PlaceHolder");

        // Alinhando o título à esquerda e o botão à direita no painel superior
        topPanel.add(home, BorderLayout.WEST);

        // Painel central para os botões "Ver catálogo", "Login" e "Registrar" em uma coluna centralizada
        centerPanel = new JPanel(new GridLayout(3, 1, 0, 50)); // 3 linhas, 1 coluna com espaço entre linhas
        catalogo = CriaBotaoPreDefinido("Ver catálogo");

        // Inicializa os botões de acordo com o tipo de usuário
        atualizarInterface();

        // Alinhando o painel central no centro
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.add(centerPanel);

        // Adicionando os painéis à interface principal
        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    private JButton CriaBotaoPreDefinido(String nomeBotao){
        JButton novoBotao = new JButton(nomeBotao);
        // Aumentando o tamanho dos botões
        Dimension buttonSize = new Dimension(200, 50); // Define uma largura e altura específicas
        // Aumentando o tamanho da fonte
        Font buttonFont = new Font("Arial", Font.BOLD, 16);

        novoBotao.setPreferredSize(buttonSize);
        novoBotao.setFont(buttonFont);
        return novoBotao;
    }

    private void InicializaBotoesGerais(){
        catalogo.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.CATALOGO);
        });
        home.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
    }
    private void InicializaBotoesUsuario(){
        dadosUsuario.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO);
        });
        sair.addActionListener(_ -> {
            Sistema.LogOffUsuario();
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
    }
    private void InicializaBotoesAdmin(){
        InicializaBotoesUsuario();
        registrar.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.REGISTRO);
        });
        novoAnime.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.NOVO_ANIME);
        });
    }
    private void InicializaBotoesGuest(){
        login.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.LOGIN);
        });
        registrar.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.REGISTRO);
        });
    }

    @Override
    public void atualizarInterface() {
        String tipoUsuario = Sistema.getTipoUsuario();
        centerPanel.removeAll();

        // Adicione os botões com base no tipo de usuário
        centerPanel.add(catalogo);
        switch (tipoUsuario) {
            case "Cliente":
                dadosUsuario = CriaBotaoPreDefinido("Ver dados Usuario");
                sair = CriaBotaoPreDefinido("Sair");
                centerPanel.add(dadosUsuario);
                centerPanel.add(sair);
                InicializaBotoesUsuario();
                break;
            case "Admin":
                dadosUsuario = CriaBotaoPreDefinido("Ver dados Usuario");
                registrar = CriaBotaoPreDefinido("Registrar novo administrador");
                novoAnime = CriaBotaoPreDefinido("Adicionar Anime");
                sair = CriaBotaoPreDefinido("Sair");
                centerPanel.add(dadosUsuario);
                centerPanel.add(sair);
                centerPanel.add(registrar);
                centerPanel.add(novoAnime);
                InicializaBotoesAdmin();
                break;
            case "Guest":
                login = CriaBotaoPreDefinido("Login");
                registrar = CriaBotaoPreDefinido("Registrar");
                centerPanel.add(login);
                centerPanel.add(registrar);
                InicializaBotoesGuest();
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de usuário desconhecido!");
                return;
        }

        // Revalida e repinta
        revalidate();
        repaint();
    }
}
