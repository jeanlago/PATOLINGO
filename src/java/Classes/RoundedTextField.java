package Classes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class RoundedTextField extends JTextField {
    private final int radius;

    public RoundedTextField(int radius) {
        super();
        this.radius = radius;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);


        g2d.setColor(new Color(169, 169, 169));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);

        super.paintComponent(g);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

    }

    @Override
    public void setBorder(Border border) {

    }
}
