package Visual;

import Console.Sistema;

import javax.swing.*;
import java.awt.*;

public class InterfacePrincipal extends JPanel implements Atualizavel {
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

        // Painel central configurado com GridBagLayout para melhor controle de centralização
        centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setPreferredSize(new Dimension(220, 300)); // Define o tamanho desejado para a área

        // Alinhando o painel central no centro
        JPanel centerWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerWrapper.add(centerPanel);

        // Adicionando os painéis à interface principal
        add(topPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);

        // Inicializa os botões de acordo com o tipo de usuário
        atualizarInterface();
    }

    private JButton CriaBotaoPreDefinido(String nomeBotao) {
        JButton novoBotao = new JButton(nomeBotao);
        // Define o tamanho preferido e máximo para o botão
        Dimension buttonSize = new Dimension(200, 50); // Define a largura e altura específicas
        Font buttonFont = new Font("Arial", Font.BOLD, 16); // Aumenta o tamanho da fonte

        novoBotao.setPreferredSize(buttonSize);
        novoBotao.setMaximumSize(buttonSize); // Garante o tamanho máximo
        novoBotao.setFont(buttonFont);
        return novoBotao;
    }


    private void InicializaBotoesGerais() {
        catalogo.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.CATALOGO);
        });
        home.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.PRINCIPAL);
        });
    }

    private void InicializaBotoesUsuario() {
        dadosUsuario.addActionListener(_ -> {
            gerenciador.trocarParaTela(GerenciadorInterfaces.DADOS_USUARIO);
        });
        sair.addActionListener(_ -> {
            Sistema.LogOffUsuario();
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
        String tipoUsuario = Sistema.getTipoUsuario();
        centerPanel.removeAll();

        // Criar um painel para empilhar os botões, usando BoxLayout vertical
        JPanel empilhamentoPanel = new JPanel();
        empilhamentoPanel.setLayout(new BoxLayout(empilhamentoPanel, BoxLayout.Y_AXIS));
        empilhamentoPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizar os botões dentro do empilhamentoPanel

        // Adicione os botões com base no tipo de usuário
        catalogo = CriaBotaoPreDefinido("Ver catálogo");
        empilhamentoPanel.add(catalogo);

        switch (tipoUsuario) {
            case "Cliente":
                dadosUsuario = CriaBotaoPreDefinido("Ver dados Usuario");
                sair = CriaBotaoPreDefinido("Sair");
                empilhamentoPanel.add(dadosUsuario);
                empilhamentoPanel.add(sair);
                InicializaBotoesUsuario();
                break;
            case "Admin":
                dadosUsuario = CriaBotaoPreDefinido("Ver dados Usuario");
                registrar = CriaBotaoPreDefinido("Registrar novo administrador");
                novoAnime = CriaBotaoPreDefinido("Adicionar Anime");
                sair = CriaBotaoPreDefinido("Sair");
                empilhamentoPanel.add(dadosUsuario);
                empilhamentoPanel.add(sair);
                empilhamentoPanel.add(registrar);
                empilhamentoPanel.add(novoAnime);
                InicializaBotoesAdmin();
                break;
            case "Guest":
                login = CriaBotaoPreDefinido("Login");
                registrar = CriaBotaoPreDefinido("Registrar");
                empilhamentoPanel.add(login);
                empilhamentoPanel.add(registrar);
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
