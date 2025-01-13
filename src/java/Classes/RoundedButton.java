package Classes;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class RoundedButton extends JButton {
    private final int radius;
    private Color normalColor;
    private Color hoverColor;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;


        normalColor = new Color(65, 184, 190);
        hoverColor = normalColor.darker();

        setBackground(normalColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalColor);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g);
        g2d.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {

    }

    public void setNormalColor(Color normalColor) {
        this.normalColor = normalColor;
        this.hoverColor = normalColor.darker();
        setBackground(normalColor);
    }
}
