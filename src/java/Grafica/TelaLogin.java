package Grafica;

import Classes.RoundedButton;
import Classes.RoundedPassword;
import Classes.RoundedTextField;
import Classes.VerificarLogin;
import Main.Main;
import java.awt.*;
import javax.swing.*;

public class TelaLogin {
    private final JFrame frame;

    public TelaLogin(JFrame frame) {
        this.frame = frame;
        criarTelaLogin();
    }

    private void criarTelaLogin() {
        frame.getContentPane().removeAll();


        String backgroundPath = "C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\background_login.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());


        JPanel telaLogin = new JPanel();
        telaLogin.setLayout(new BoxLayout(telaLogin, BoxLayout.Y_AXIS));
        telaLogin.setBounds(434, 150, 411, 440);
        telaLogin.setOpaque(false);

        telaLogin.add(Box.createVerticalGlue());

        JLabel titulo = new JLabel("Bem-vindo! Faça seu login:");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        telaLogin.add(titulo);
        telaLogin.add(Box.createVerticalStrut(20));



        JLabel login = new JLabel("E-Mail:");
        login.setFont(new Font("Arial", Font.BOLD,15));
        login.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        telaLogin.add(login);
        telaLogin.add(Box.createVerticalStrut(10));



        RoundedTextField campoUsuario = new RoundedTextField(15);
        campoUsuario.setFont(new Font("Arial", Font.PLAIN, 18));
        Dimension tamanhoCampoTexto = new Dimension(300, 30);
        campoUsuario.setMaximumSize(tamanhoCampoTexto);
        campoUsuario.setPreferredSize(tamanhoCampoTexto);
        campoUsuario.setMinimumSize(tamanhoCampoTexto);
        campoUsuario.setForeground(Color.DARK_GRAY);
        campoUsuario.setText("");
        telaLogin.add(campoUsuario);
        telaLogin.add(Box.createVerticalStrut(10));



        JLabel senha = new JLabel("Senha:");
        senha.setFont(new Font("Arial", Font.BOLD,15));
        senha.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        telaLogin.add(senha);
        telaLogin.add(Box.createVerticalStrut(10));



        RoundedPassword campoSenha = new RoundedPassword(15);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 18));
        campoSenha.setMaximumSize(tamanhoCampoTexto);
        campoSenha.setPreferredSize(tamanhoCampoTexto);
        campoSenha.setMinimumSize(tamanhoCampoTexto);
        campoSenha.setForeground(Color.DARK_GRAY);
        telaLogin.add(campoSenha);
        telaLogin.add(Box.createVerticalStrut(20));



        RoundedButton botaoEntrar = new RoundedButton("Entrar", 30);
        botaoEntrar.setFont(new Font("Arial", Font.BOLD, 50));
        botaoEntrar.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoEntrar.setMaximumSize(new Dimension(411, 80));
        botaoEntrar.setPreferredSize(new Dimension(411, 80));
        botaoEntrar.setMinimumSize(new Dimension(411, 80));
        botaoEntrar.setBackground(new Color(65, 184, 190));
        botaoEntrar.setForeground(Color.WHITE);


        botaoEntrar.addActionListener(e -> {
            String email = campoUsuario.getText();
            String senhaUser = new String(campoSenha.getPassword());
            VerificarLogin verificarLogin = new VerificarLogin(Main.bancoDeDados);
            verificarLogin.autenticarUsuario(frame, email, senhaUser);
        });
        telaLogin.add(botaoEntrar);

        telaLogin.add(Box.createVerticalStrut(10));



        RoundedButton botaoVoltar = new RoundedButton("Voltar", 30);
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 30));
        botaoVoltar.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoVoltar.setMaximumSize(new Dimension(200, 40));
        botaoVoltar.setPreferredSize(new Dimension(200, 40));
        botaoVoltar.setMinimumSize(new Dimension(200, 40));
        botaoVoltar.setBackground(new Color(65, 184, 190));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.addActionListener(e -> new TelaInicial(frame));
        telaLogin.add(botaoVoltar);


        frame.add(telaLogin);
        frame.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
}
