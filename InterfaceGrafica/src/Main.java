import Controle.FileAnimes;
import Controle.FileContas;
import Controle.SistemaGeral;
import Visão.GerenciadorInterfaces;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws Exception {
        SistemaGeral sistema = SistemaGeral.getInstance();
        SistemaGeral.setScreenSize(Toolkit.getDefaultToolkit().getScreenSize());
        SistemaGeral.defineDataAtual();
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