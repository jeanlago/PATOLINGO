package Classes;

import java.awt.*;
import javax.swing.*;

public class TransparentProgressBar extends JProgressBar {
    public TransparentProgressBar(int min, int max) {
        super(min, max);
        setOpaque(false);
        setBorder(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = (int) (getWidth() * getPercentComplete());
        int height = getHeight();

        g2d.setColor(getForeground());
        g2d.fillRect(0, 0, width, height);

        g2d.dispose();
    }
}

