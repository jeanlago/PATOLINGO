package Grafica;

import Classes.Pergunta;
import Classes.RoundedButton;
import Database.BancoDeDados;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class TelaProfessor {
    private final JFrame frame;
    private final int idProfessor;
    private BancoDeDados bancoDeDados;
    private int idUsuario;

    public TelaProfessor(JFrame frame, int idProfessor) {
        this.frame = frame;
        this.idProfessor = idProfessor;

        try {
            this.bancoDeDados = new BancoDeDados();
            this.idUsuario = bancoDeDados.getIdUsuarioPorProfessor(idProfessor);
            System.out.println("ID do usuário carregado: " + idUsuario);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }

        criarTelaProfessor();
    }

    private void criarTelaProfessor() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);

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


        JLabel professorNome = new JLabel("Bem-vindo, Professor!");
        professorNome.setFont(new Font("Arial", Font.BOLD, 32));
        professorNome.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        painelCentral.add(professorNome);
        painelCentral.add(Box.createVerticalStrut(40));


        RoundedButton botaoRegistrarPergunta = new RoundedButton("Registrar Perguntas", 50);
        botaoRegistrarPergunta.setFont(new Font("Poppins", Font.BOLD, 20));
        botaoRegistrarPergunta.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoRegistrarPergunta.setMaximumSize(new Dimension(300, 75));
        botaoRegistrarPergunta.setBackground(new Color(65, 184, 190));
        botaoRegistrarPergunta.setForeground(Color.WHITE);
        botaoRegistrarPergunta.setFocusPainted(false);


        botaoRegistrarPergunta.addActionListener(e -> registrarPerguntas());

        painelCentral.add(botaoRegistrarPergunta);
        painelCentral.add(Box.createVerticalStrut(20));


        RoundedButton botaoVerPerguntas = new RoundedButton("Ver Perguntas", 50);
        botaoVerPerguntas.setFont(new Font("Poppins", Font.BOLD, 20));
        botaoVerPerguntas.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoVerPerguntas.setMaximumSize(new Dimension(200,40));
        botaoVerPerguntas.setPreferredSize(new Dimension(200,40));
        botaoVerPerguntas.setMinimumSize(new Dimension(200,40));
        botaoVerPerguntas.setBackground(new Color(65, 184, 190));
        botaoVerPerguntas.setForeground(Color.WHITE);
        botaoVerPerguntas.setFocusPainted(false);

        botaoVerPerguntas.addActionListener(e -> mostrarPerguntas());
        painelCentral.add(botaoVerPerguntas);
        painelCentral.add(Box.createVerticalStrut(20));


        RoundedButton botaoVerAlunos = new RoundedButton("Ver Alunos", 50);
        botaoVerAlunos.setFont(new Font("Poppins", Font.BOLD, 20));
        botaoVerAlunos.setAlignmentX(JButton.CENTER_ALIGNMENT);
        botaoVerAlunos.setMaximumSize(new Dimension(200,40));
        botaoVerAlunos.setPreferredSize(new Dimension(200,40));
        botaoVerAlunos.setMinimumSize(new Dimension(200,40));
        botaoVerAlunos.setBackground(new Color(65, 184, 190));
        botaoVerAlunos.setForeground(Color.WHITE);
        botaoVerAlunos.setFocusPainted(false);

        botaoVerAlunos.addActionListener(e -> mostrarAlunos());

        painelCentral.add(botaoVerAlunos);
        painelCentral.add(Box.createVerticalStrut(20));

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

    private void mostrarPerguntas() {
        try {
            List<Pergunta> perguntas = bancoDeDados.getPerguntasByProfessor(idProfessor);
            if (perguntas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Nenhuma pergunta registrada.");
                return;
            }

            DefaultListModel<String> listModel = new DefaultListModel<>();
            JList<String> listaPerguntas = new JList<>(listModel);
            for (Pergunta pergunta : perguntas) {
                listModel.addElement(pergunta.getPergunta() + " (" + pergunta.getIdioma() + ")");
            }

            JScrollPane scrollPane = new JScrollPane(listaPerguntas);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            int escolha = JOptionPane.showOptionDialog(frame, scrollPane, "Minhas Perguntas",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Editar", "Excluir", "Cancelar"}, null);

            int selectedIndex = listaPerguntas.getSelectedIndex();
            if (selectedIndex != -1) {
                Pergunta perguntaSelecionada = perguntas.get(selectedIndex);
                if (escolha == 0) {
                    editarPergunta(perguntaSelecionada);
                } else if (escolha == 1) {
                    excluirPergunta(perguntaSelecionada);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar perguntas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void editarPergunta(Pergunta pergunta) {
        String novoTexto = JOptionPane.showInputDialog(frame, "Edite o texto da pergunta:", pergunta.getPergunta());
        if (novoTexto == null || novoTexto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "O campo pergunta não pode estar vazio!");
            return;
        }

        String novaResposta = JOptionPane.showInputDialog(frame, "Edite a resposta correta:", pergunta.getRespostaCorreta());
        if (novaResposta == null || novaResposta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "O campo resposta correta não pode estar vazio!");
            return;
        }

        List<String> novasAlternativas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String alternativa = JOptionPane.showInputDialog(frame, "Edite a alternativa " + (i + 1) + ":", pergunta.getOpcoes().get(i + 1));
            if (alternativa == null || alternativa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Alternativa " + (i + 1) + " inválida ou não preenchida!");
                return;
            }
            novasAlternativas.add(alternativa);
        }

        pergunta.setPergunta(novoTexto);
        pergunta.setRespostaCorreta(novaResposta);
        List<String> opcoesAtualizadas = new ArrayList<>();
        opcoesAtualizadas.add(novaResposta);
        opcoesAtualizadas.addAll(novasAlternativas);
        pergunta.setOpcoes(opcoesAtualizadas);

        try {
            bancoDeDados.editarPergunta(pergunta);
            JOptionPane.showMessageDialog(frame, "Pergunta editada com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao editar pergunta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirPergunta(Pergunta pergunta) {
        int confirmacao = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir esta pergunta?", "Excluir Pergunta", JOptionPane.YES_NO_OPTION);
        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                bancoDeDados.excluirPergunta(pergunta.getId());
                JOptionPane.showMessageDialog(frame, "Pergunta excluída com sucesso!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(frame, "Erro ao excluir pergunta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registrarPerguntas() {
        String[] idiomasDisponiveis = {"Inglês", "Português", "Espanhol", "Francês", "Alemão", "Italiano", "Japonês", "Chinês", "Russo", "Coreano"};


        String textoPergunta = JOptionPane.showInputDialog(frame, "Digite o texto da pergunta:");
        if (textoPergunta == null || textoPergunta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "O campo pergunta não pode estar vazio!");
            return;
        }

        String idiomaOrigem = (String) JOptionPane.showInputDialog(frame,
                "Selecione o idioma de origem:",
                "Idioma de Origem",
                JOptionPane.QUESTION_MESSAGE,
                null,
                idiomasDisponiveis,
                idiomasDisponiveis[0]);

        if (idiomaOrigem == null) {
            JOptionPane.showMessageDialog(frame, "Operação cancelada.");
            return;
        }


        String idiomaDestino = (String) JOptionPane.showInputDialog(frame,
                "Selecione o idioma de destino:",
                "Idioma de Destino",
                JOptionPane.QUESTION_MESSAGE,
                null,
                idiomasDisponiveis,
                idiomasDisponiveis[1]);

        if (idiomaDestino == null) {
            JOptionPane.showMessageDialog(frame, "Operação cancelada.");
            return;
        }

        if (idiomaOrigem.equals(idiomaDestino)) {
            JOptionPane.showMessageDialog(frame, "O idioma de origem e destino devem ser diferentes!");
            return;
        }


        String respostaCorreta = JOptionPane.showInputDialog(frame, "Digite a resposta correta:");
        if (respostaCorreta == null || respostaCorreta.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "O campo resposta correta não pode estar vazio!");
            return;
        }


        List<String> alternativas = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            String alternativa = JOptionPane.showInputDialog(frame, "Digite a alternativa " + i + ":");
            if (alternativa == null || alternativa.trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Alternativa " + i + " inválida ou não preenchida!");
                return;
            }
            alternativas.add(alternativa);
        }


        Pergunta novaPergunta = new Pergunta(textoPergunta, respostaCorreta, idiomaOrigem + " → " + idiomaDestino, "Fácil");
        novaPergunta.setOpcoes(List.of(respostaCorreta, alternativas.get(0), alternativas.get(1), alternativas.get(2)));

        try {
            bancoDeDados.addPergunta(novaPergunta, idProfessor);
            JOptionPane.showMessageDialog(frame, "Pergunta registrada com sucesso!\n" +
                    "Idioma: " + idiomaOrigem + " → " + idiomaDestino);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao registrar pergunta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void mostrarAlunos() {
        try {
            List<String> alunos = bancoDeDados.getListaAlunos();
            StringBuilder lista = new StringBuilder("Lista de Alunos:\n\n");
            for (String aluno : alunos) {
                lista.append(aluno).append("\n");
            }

            JOptionPane.showMessageDialog(frame, lista.toString(), "Alunos Cadastrados", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar lista de alunos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterarFundoTela() {
        JFrame janelaSelecao = new JFrame("Selecione o Fundo");
        janelaSelecao.setSize(600, 400);
        janelaSelecao.setLayout(new FlowLayout());
        janelaSelecao.setLocationRelativeTo(null);

        String[][] caminhosFotos = {
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Donald_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile_Professor/Donald_Profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Pata_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile_Professor/Pata_profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/Duck_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile_Professor/Duck_profile.png"},
            {"C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile/None_Avatar.png",
             "C:/Users/JeanM/OneDrive/Documentos/OGNILOUD/OGNILOUD/src/resources/images/Profile_Professor/None_profile.png"}
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
                    criarTelaProfessor();
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
}