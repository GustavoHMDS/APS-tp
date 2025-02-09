import Controle.FileAnimes;
import Controle.FileContas;
import Controle.Sistema;
import Modelo.Catalogo;
import Modelo.Convidado;
import Visão.GerenciadorInterfaces;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Sistema sistema = Sistema.getInstance();
        Sistema.setScreenSize(Toolkit.getDefaultToolkit().getScreenSize());
        Sistema.defineDataAtual();
        FileContas sistemaContas = new FileContas(sistema);
        FileAnimes sistemaAnimes = new FileAnimes(sistema);
        sistema.inicializarSistema(sistemaContas, sistemaAnimes);
        SwingUtilities.invokeLater(() -> {
            GerenciadorInterfaces gerenciador = new GerenciadorInterfaces(sistema);
            gerenciador.setVisible(true);
            ImageIcon icone = new ImageIcon("./src/Imagens/image.png");
            gerenciador.setIconImage(icone.getImage());
        });
    }
}