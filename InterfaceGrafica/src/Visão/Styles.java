package Visão;

import Controle.Sistema;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;

public class Styles {
    public static Color background = new Color(64, 44, 94);

    public static void setLabelStyle(JLabel label) {
        label.setForeground(new Color(254, 244, 129));
        label.setFont(new Font("Coimbra", Font.PLAIN, 25));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    public static void setTextFielStyle(JTextField textField) {
        textField.setBackground(new Color(Transparency.TRANSLUCENT));
        textField.setForeground(new Color(255,255,255));
        textField.setMargin(new Insets(5,20,5,20));
        textField.setCaretColor(Color.YELLOW);
        textField.setSelectionColor(Color.YELLOW);
        textField.setBorder(new BasicBorders.FieldBorder(background,background,Color.YELLOW,background));
        textField.setFont(new Font("Coimbra", Font.PLAIN, 18));
    }

    public static void setButtonStyle(JButton button) {
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setFocusable(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Cambria", Font.BOLD, 25));
        button.setBackground(new Color(254, 244, 129)); // Define a cor de fundo (Cornflower Blue)
        button.setForeground(new Color(64, 44, 94)); // Define a cor do texto
        button.setContentAreaFilled(true); // Preencher o conteúdo com a cor de fundo
        button.setBorderPainted(false); // Remove a borda
        button.setMaximumSize(new Dimension((Sistema.screenSize.width * 6)/10, 50));
    }
}
