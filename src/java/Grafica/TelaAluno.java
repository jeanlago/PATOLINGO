package Grafica;

import Classes.Progresso;
import Classes.RoundedButton;
import Classes.TransparentProgressBar;
import Database.BancoDeDados;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class TelaAluno {
    private final JFrame frame;
    private final Progresso progresso;
    private JProgressBar barraProgresso;
    private BancoDeDados bancoDeDados;
    private final int idAluno;
    private String idiomaSelecionado = null;


    public TelaAluno(JFrame frame, int idAluno) {
        this.frame = frame;
        this.idAluno = idAluno;
        this.progresso = new Progresso();
        try {
            this.bancoDeDados = new BancoDeDados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        telaAluno();
    }

    private void telaAluno() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        Dimension tamanhoCampoTexto = new Dimension(300, 75);

        String nomeAluno = null;
        int xpAtual = 0;
        int nivelAtual = 1;

        try {
            nomeAluno = bancoDeDados.getNomeAluno(idAluno);
            xpAtual = bancoDeDados.getXPAtual(idAluno);
            nivelAtual = bancoDeDados.getNivelAtual(idAluno);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar dados do aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        frame.setIconImage(new ImageIcon("C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\Logo.png").getImage());


        String backgroundPath = "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());


        JPanel painelCentral = new JPanel();
        painelCentral.setBounds(434, 200, 411, 600);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setOpaque(false);
        painelCentral.add(Box.createVerticalStrut(180));


        JLabel alunoNome = new JLabel(nomeAluno != null ? "Bem-vindo, " + nomeAluno + "!" : "Bem-vindo, Aluno!");
        alunoNome.setFont(new Font("Arial", Font.BOLD, 32));
        alunoNome.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(alunoNome);
        painelCentral.add(Box.createVerticalStrut(60));


        barraProgresso = new TransparentProgressBar(0, 100);
        barraProgresso.setValue(xpAtual);
        barraProgresso.setString("XP: " + xpAtual + " | Nível: " + nivelAtual);
        barraProgresso.setStringPainted(false);
        barraProgresso.setMaximumSize(new Dimension(500, 20));
        barraProgresso.setPreferredSize(new Dimension(500, 20));
        barraProgresso.setMinimumSize(new Dimension(500, 20));
        barraProgresso.setForeground(new Color(65, 184, 190));
        painelCentral.add(barraProgresso);
        painelCentral.add(Box.createVerticalStrut(30));


        RoundedButton botaoResponder = new RoundedButton("Jogar", 50);
        botaoResponder.setFont(new Font("Poppins", Font.BOLD, 50));
        botaoResponder.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoResponder.setMaximumSize(tamanhoCampoTexto);
        botaoResponder.setBackground(new Color(65, 184, 190));
        botaoResponder.setForeground(Color.WHITE);
        botaoResponder.setFocusPainted(false);
        botaoResponder.addActionListener(e -> {
            if (idiomaSelecionado == null) {
                JOptionPane.showMessageDialog(frame, "Por favor, selecione um idioma antes de jogar.");
                return;
            }

            new TelaPerguntas(frame, idAluno, idiomaSelecionado);
        });

        painelCentral.add(botaoResponder);
        painelCentral.add(Box.createVerticalStrut(20));


        RoundedButton botaoIdiomas = new RoundedButton("Idiomas", 50);
        botaoIdiomas.setFont(new Font("Poppins", Font.BOLD, 20));
        botaoIdiomas.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoIdiomas.setMaximumSize(new Dimension(200,40));
        botaoIdiomas.setPreferredSize(new Dimension(200,40));
        botaoIdiomas.setMinimumSize(new Dimension(200,40));
        botaoIdiomas.setBackground(new Color(65, 184, 190));
        botaoIdiomas.setForeground(Color.WHITE);
        botaoIdiomas.setFocusPainted(false);

        botaoIdiomas.addActionListener(e -> mostrarIdiomasDisponiveis());
        painelCentral.add(botaoIdiomas);

        String botaoVoltarPath = "C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\botao_voltar.png";
        ImageIcon iconVoltar = new ImageIcon(botaoVoltarPath);
        Image imagemRedimensionada = iconVoltar.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconVoltar = new ImageIcon(imagemRedimensionada);
        JButton botaoVoltar = new JButton(iconVoltar);
        botaoVoltar.setBounds(10, 10, 70, 70);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> new TelaLogin(frame));

        frame.add(botaoVoltar);
        frame.add(painelCentral);
        frame.add(backgroundLabel);
        frame.revalidate();
        frame.repaint();
    }


    private void mostrarIdiomasDisponiveis() {
        List<String> idiomasDisponiveis;
        try {
            idiomasDisponiveis = bancoDeDados.getIdiomasDisponiveis();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao buscar idiomas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (idiomasDisponiveis.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum idioma disponível.");
            return;
        }

        JFrame idiomaFrame = new JFrame("Selecione um Idioma");
        idiomaFrame.setSize(400, 300);
        idiomaFrame.setLocationRelativeTo(frame);
        idiomaFrame.setLayout(new BorderLayout());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        idiomasDisponiveis.forEach(listModel::addElement);
        JList<String> listaIdiomas = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaIdiomas);
        idiomaFrame.add(scrollPane, BorderLayout.CENTER);

        JButton confirmar = new JButton("Confirmar");
        confirmar.addActionListener(e -> {
            idiomaSelecionado = listaIdiomas.getSelectedValue();
            if (idiomaSelecionado != null) {
                JOptionPane.showMessageDialog(idiomaFrame, "Idioma selecionado: " + idiomaSelecionado);
                idiomaFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(idiomaFrame, "Selecione um idioma.");
            }
        });

        idiomaFrame.add(confirmar, BorderLayout.SOUTH);
        idiomaFrame.setVisible(true);
    }

    public void atualizarProgressoNaTela(int xpAtual, int nivelAtual) {
        barraProgresso.setValue(xpAtual);
        barraProgresso.setString("XP: " + xpAtual + " | Nível: " + nivelAtual);
    }
}
