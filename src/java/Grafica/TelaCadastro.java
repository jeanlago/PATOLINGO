package Grafica;

import Classes.Aluno;
import Classes.Professor;
import Classes.RoundedButton;
import Classes.RoundedPassword;
import Classes.RoundedTextField;
import Database.BancoDeDados;
import Main.Main;
import java.awt.*;
import java.sql.SQLException;
import javax.swing.*;

public class TelaCadastro {
    private final JFrame frame;
    private final BancoDeDados bancoDeDados;

    public TelaCadastro(JFrame frame) {
        this.frame = frame;
        this.bancoDeDados = Main.bancoDeDados;
        telaCadastro();
    }

    private void telaCadastro() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        Dimension tamanhoCampoTexto = new Dimension(300, 30);

        frame.setIconImage(new ImageIcon("C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\Logo.png").getImage());


        String backgroundPath = "C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\background_cadastro.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        JPanel painelCentral = new JPanel();
        painelCentral.setBounds(434, 200, 411, 800);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setOpaque(false);
        painelCentral.add(Box.createVerticalStrut(0));


        JLabel cadastroNome = new JLabel("Nome:");
        cadastroNome.setFont(new Font("Arial", Font.BOLD, 15));
        cadastroNome.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(cadastroNome);
        painelCentral.add(Box.createVerticalStrut(10));

        RoundedTextField campoNome = new RoundedTextField(15);
        campoNome.setFont(new Font("Arial", Font.PLAIN, 18));
        campoNome.setForeground(Color.DARK_GRAY);
        campoNome.setMaximumSize(tamanhoCampoTexto);
        campoNome.setPreferredSize(tamanhoCampoTexto);
        campoNome.setMinimumSize(tamanhoCampoTexto);
        painelCentral.add(campoNome);
        painelCentral.add(Box.createVerticalStrut(10));

        JLabel cadastroEmail = new JLabel("E-Mail:");
        cadastroEmail.setFont(new Font("Arial", Font.BOLD, 15));
        cadastroEmail.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(cadastroEmail);
        painelCentral.add(Box.createVerticalStrut(10));

        RoundedTextField campoEmail = new RoundedTextField(15);
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        campoEmail.setMaximumSize(tamanhoCampoTexto);
        campoEmail.setPreferredSize(tamanhoCampoTexto);
        campoEmail.setMinimumSize(tamanhoCampoTexto);
        campoEmail.setForeground(Color.DARK_GRAY);
        painelCentral.add(campoEmail);
        painelCentral.add(Box.createVerticalStrut(10));

        JLabel cadastroCpf = new JLabel("CPF:");
        cadastroCpf.setFont(new Font("Arial", Font.BOLD, 15));
        cadastroCpf.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(cadastroCpf);
        painelCentral.add(Box.createVerticalStrut(10));

        RoundedTextField campoCpf = new RoundedTextField(15);
        campoCpf.setFont(new Font("Arial", Font.PLAIN, 18));
        campoCpf.setMaximumSize(tamanhoCampoTexto);
        campoCpf.setPreferredSize(tamanhoCampoTexto);
        campoCpf.setMinimumSize(tamanhoCampoTexto);
        campoCpf.setForeground(Color.DARK_GRAY);
        painelCentral.add(campoCpf);
        painelCentral.add(Box.createVerticalStrut(10));

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("Arial", Font.BOLD, 15));
        senhaLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(senhaLabel);
        painelCentral.add(Box.createVerticalStrut(10));

        RoundedPassword campoSenha = new RoundedPassword(15);
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 18));
        campoSenha.setMaximumSize(tamanhoCampoTexto);
        campoSenha.setPreferredSize(tamanhoCampoTexto);
        campoSenha.setMinimumSize(tamanhoCampoTexto);
        painelCentral.add(campoSenha);
        painelCentral.add(Box.createVerticalStrut(10));


        JCheckBox checkBoxAluno = new JCheckBox("Aluno");
        checkBoxAluno.setFont(new Font("Arial", Font.BOLD, 15));
        checkBoxAluno.setOpaque(false);
        checkBoxAluno.setBounds(510, 470, 120, 30);

        JCheckBox checkBoxProfessor = new JCheckBox("Professor");
        checkBoxProfessor.setFont(new Font("Arial", Font.BOLD, 15));
        checkBoxProfessor.setOpaque(false);
        checkBoxProfessor.setBounds(650, 470, 120, 30);


        checkBoxAluno.addActionListener(e -> {
            if (checkBoxAluno.isSelected()) {
                checkBoxProfessor.setSelected(false);
            }
        });
        checkBoxProfessor.addActionListener(e -> {
            if (checkBoxProfessor.isSelected()) {
                checkBoxAluno.setSelected(false);
            }
        });


        frame.add(checkBoxAluno);
        frame.add(checkBoxProfessor);
        painelCentral.add(Box.createVerticalStrut(30));


        RoundedButton botaoCadastrar = new RoundedButton("Cadastrar-se", 30);
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 40));
        botaoCadastrar.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoCadastrar.setMaximumSize(new Dimension(411, 60));
        botaoCadastrar.setPreferredSize(new Dimension(411, 60));
        botaoCadastrar.setMinimumSize(new Dimension(411, 60));
        botaoCadastrar.setBackground(new Color(65, 184, 190));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.addActionListener(e -> {
            String nome = campoNome.getText().trim();
            String email = campoEmail.getText().trim();
            String cpf = campoCpf.getText().trim();
            String senha = new String(campoSenha.getPassword()).trim();

            if (nome.isEmpty() || email.isEmpty() || cpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Por favor, preencha todos os campos!");
                return;
            }

            if (!nome.matches("^[A-Za-zÁ-Üá-ü ]+$")) {
                JOptionPane.showMessageDialog(frame, "O nome contém caracteres inválidos!");
                return;
            }

            if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um email válido!");
                return;
            }

            if (!cpf.matches("\\d{11}")) {
                JOptionPane.showMessageDialog(frame, "O CPF deve conter exatamente 11 números!");
                return;
            }

            if (senha.length() < 6) {
                JOptionPane.showMessageDialog(frame, "A senha deve ter pelo menos 6 caracteres!");
                return;
            }


            if (!checkBoxAluno.isSelected() && !checkBoxProfessor.isSelected()) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecione se você é aluno ou professor!");
                return;
            }

            try {
                if (checkBoxAluno.isSelected()) {

                    Aluno aluno = new Aluno(nome, email, cpf);
                    bancoDeDados.cadastrarAlunoCompleto(aluno, senha);
                    JOptionPane.showMessageDialog(frame, "Cadastro de Aluno realizado com sucesso!");
                    new TelaLogin(frame);
                } else {

                    Professor professor = new Professor(nome, email, cpf, null);
                    bancoDeDados.addProfessor(professor);
                    bancoDeDados.addUsuario(email, senha, "professor", null, professor.getId());
                    JOptionPane.showMessageDialog(frame, "Cadastro de Professor realizado com sucesso!");
                    new TelaLogin(frame);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar: " + ex.getMessage());
            }
        });

        painelCentral.add(botaoCadastrar);
        painelCentral.add(Box.createVerticalStrut(10));


        RoundedButton botaoVoltar = new RoundedButton("Voltar", 30);
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 30));
        botaoVoltar.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoVoltar.setMaximumSize(new Dimension(200, 60));
        botaoVoltar.setBackground(new Color(65, 184, 190));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.addActionListener(e -> new TelaInicial(frame));
        painelCentral.add(botaoVoltar);
        painelCentral.add(Box.createVerticalGlue());

        frame.add(painelCentral);
        frame.add(backgroundLabel);

        frame.revalidate();
        frame.repaint();
    }
}
