package Grafica;

import Classes.RoundedButton;
import Classes.TransparentProgressBar;
import Database.BancoDeDados;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class TelaAluno {
    private final JFrame frame;

    private JProgressBar barraProgresso;
    private BancoDeDados bancoDeDados;
    private final int idAluno;
    private String idiomaSelecionado = null;
    private int idUsuario;

    public TelaAluno(JFrame frame, int idAluno) {
        this.frame = frame;
        this.idAluno = idAluno;
        try {
            this.bancoDeDados = new BancoDeDados();
            this.idUsuario = bancoDeDados.getIdUsuarioPorAluno(idAluno);
            System.out.println("ID do usuário carregado: " + idUsuario);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar com o banco de dados ou carregar dados: " + e.getMessage());
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


        String backgroundPath;
        try {
            backgroundPath = bancoDeDados.getFundoTela(idUsuario);
            if (backgroundPath == null || backgroundPath.isEmpty()) {
                backgroundPath = "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png";
            }
        } catch (SQLException e) {
            backgroundPath = "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png";
        }

        JLabel backgroundLabel = new JLabel(new ImageIcon(new ImageIcon(backgroundPath).getImage()));
        backgroundLabel.setBounds(-10, -5,1280,720);


        JPanel painelCentral = new JPanel();
        painelCentral.setBounds(434, 200, 411, 600);
        painelCentral.setLayout(new BoxLayout(painelCentral, BoxLayout.Y_AXIS));
        painelCentral.setOpaque(false);
        painelCentral.add(Box.createVerticalStrut(170));


 //       ImageIcon fotoPerfil = null;
 //       try {
 //           String caminhoFoto = bancoDeDados.getFotoPerfil(idUsuario);
 //           if (caminhoFoto != null && !caminhoFoto.isEmpty()) {
 //               fotoPerfil = new ImageIcon(new ImageIcon(caminhoFoto).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
 //           }
 //       } catch (SQLException e) {
 //           fotoPerfil = new ImageIcon("C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/default_profile.png");
 //       }
//
 //       JLabel labelFoto = new JLabel(fotoPerfil);
 //       labelFoto.setAlignmentX(JLabel.CENTER_ALIGNMENT);
 //       painelCentral.add(labelFoto);



        JLabel alunoNome = new JLabel(nomeAluno != null ? "Bem-vindo, " + nomeAluno + "!" : "Bem-vindo, Aluno!");
        alunoNome.setFont(new Font("Arial", Font.BOLD, 32));
        alunoNome.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(alunoNome);
        painelCentral.add(Box.createVerticalStrut(65));


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

        JButton botaoAlterarFundo = new JButton(new ImageIcon(new ImageIcon("C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/change_button.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        botaoAlterarFundo.setBounds(718, 304, 40, 40);
        botaoAlterarFundo.setContentAreaFilled(false);
        botaoAlterarFundo.setBorderPainted(false);
        botaoAlterarFundo.addActionListener(e -> alterarFundoTela());

        JButton botaoVoltar = new JButton(new ImageIcon(new ImageIcon("C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/botao_voltar.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        botaoVoltar.setBounds(10, 10, 40, 40);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.addActionListener(e -> new TelaLogin(frame));

        frame.add(botaoVoltar);
        frame.add(botaoAlterarFundo);
        frame.add(painelCentral);
        frame.add(backgroundLabel);
        frame.revalidate();
        frame.repaint();

    }


//    private void alterarFotoPerfil() {
//    JFileChooser fileChooser = new JFileChooser();
//    fileChooser.setDialogTitle("Selecione uma foto de perfil");
//    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens", "jpg", "png", "jpeg"));
//
//    int result = fileChooser.showOpenDialog(frame);
//    if (result == JFileChooser.APPROVE_OPTION) {
//        try {
//            // Caminho do arquivo selecionado
//            String caminhoArquivo = fileChooser.getSelectedFile().getAbsolutePath();
//
//            // Processar a foto (ex.: copiar para um diretório específico do servidor)
//            String diretorioFotos = "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/fotos";
//            String nomeArquivo = "foto_usuario_" + idAluno + ".jpg";
//            String caminhoFoto = UploadFoto.salvarFoto(diretorioFotos, nomeArquivo, Files.readAllBytes(fileChooser.getSelectedFile().toPath()));
//
//            // Atualizar no banco de dados
//            bancoDeDados.atualizarFotoPerfil(idAluno, caminhoFoto);
//
//            JOptionPane.showMessageDialog(frame, "Foto de perfil alterada com sucesso!");
//
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(frame, "Erro ao alterar foto de perfil: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//}

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

    private void alterarFundoTela() {
        JFrame janelaSelecao = new JFrame("Selecione o Fundo");
        janelaSelecao.setSize(600, 400);
        janelaSelecao.setLayout(new FlowLayout());
        janelaSelecao.setLocationRelativeTo(null);

        String[][] caminhosFotos = {
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Donald_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Donald_Profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Pata_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Pata_profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Duck_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Duck_profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/None_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/background_aluno.png"}
        };

        for (String[] caminhos : caminhosFotos) {
            String caminhoExibicao = caminhos[0];
            String caminhoFundo = caminhos[1];

            JButton botaoImagem = new JButton(
                new ImageIcon(new ImageIcon(caminhoExibicao)
                    .getImage()
                    .getScaledInstance(100, 100, Image.SCALE_SMOOTH))
            );

            botaoImagem.setContentAreaFilled(false);
            botaoImagem.setBorderPainted(false);
            botaoImagem.addActionListener(e -> {
                try {
                    bancoDeDados.atualizarFundoTela(idUsuario, caminhoFundo);
                    telaAluno();
                    JOptionPane.showMessageDialog(frame, "Fundo alterado com sucesso!");
                    janelaSelecao.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar fundo: " + ex.getMessage());
                }
            });

            janelaSelecao.add(botaoImagem);
        }
        janelaSelecao.setVisible(true);
    }

    public void atualizarProgressoNaTela(int xpAtual, int nivelAtual) {
        if (barraProgresso != null) {
            barraProgresso.setValue(xpAtual);
            barraProgresso.setString("XP: " + xpAtual + " | Nível: " + nivelAtual);
        } else {
            JOptionPane.showMessageDialog(frame, "Erro: Barra de progresso não inicializada.");
        }
    }
}
