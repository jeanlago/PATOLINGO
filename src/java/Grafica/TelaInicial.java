package Grafica;

import Classes.RoundedButton;
import java.awt.*;
import javax.swing.*;

public class TelaInicial {

    private final JFrame frame;

    public TelaInicial(JFrame frame) {
        this.frame = frame;
        criandoTelaInicial();
    }

    private void criandoTelaInicial() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

        frame.setIconImage(new ImageIcon("C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\Logo.png").getImage());


        String backgroundPath = "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_sem_botao.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());


        JPanel painelCentral = new JPanel();
        painelCentral.setBounds(434, 200, 411, 400);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setOpaque(false);
        painelCentral.add(Box.createVerticalStrut(150));


        RoundedButton botaoLogin = new RoundedButton("Logar", 80);
        botaoLogin.setFont(new Font("Arial", Font.BOLD, 50));
        botaoLogin.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoLogin.setMaximumSize(new Dimension(411, 100));
        botaoLogin.setPreferredSize(new Dimension(411, 100));
        botaoLogin.setMinimumSize(new Dimension(411, 100));
        botaoLogin.setBackground(new Color(65, 184, 190));
        botaoLogin.setForeground(Color.WHITE);
        botaoLogin.setFocusPainted(false);
        botaoLogin.addActionListener(e -> new TelaLogin(frame));

        painelCentral.add(botaoLogin);
        painelCentral.add(Box.createVerticalStrut(50));


        RoundedButton botaoCadastrar = new RoundedButton("Cadastrar-se", 80);
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 50));
        botaoCadastrar.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoCadastrar.setMaximumSize(new Dimension(411, 100));
        botaoCadastrar.setPreferredSize(new Dimension(411, 100));
        botaoCadastrar.setMinimumSize(new Dimension(411, 100));
        botaoCadastrar.setBackground(new Color(65, 184, 190));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.addActionListener(e -> new TelaCadastro(frame));


        painelCentral.add(botaoCadastrar);
        painelCentral.add(Box.createVerticalGlue());


        frame.add(painelCentral);
        frame.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
}
