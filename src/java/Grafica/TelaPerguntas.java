package Grafica;

import Classes.Pergunta;
import Classes.RoundedPanel;
import Database.BancoDeDados;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.*;

public class TelaPerguntas {
    private final JFrame frame;
    private final int idAluno;
    private BancoDeDados bancoDeDados;
    private Pergunta perguntaAtual;
    private final Set<Integer> perguntasRespondidas = new HashSet<>();
    private int acertos = 0;
    private String idiomaSelecionado = null;


    public TelaPerguntas(JFrame frame, int idAluno, String idiomaSelecionado) {
        this.frame = frame;
        this.idAluno = idAluno;
        this.idiomaSelecionado = idiomaSelecionado;

        try {
            this.bancoDeDados = new BancoDeDados();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao conectar com o banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        criarTelaPerguntas();
    }

    private void criarTelaPerguntas() {
        frame.getContentPane().removeAll();
        frame.setLayout(null);
        String backgroundPath = "C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\background_perguntas.png";
        ImageIcon backgroundImage = new ImageIcon(backgroundPath);
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        String botaoVoltarPath = "C:\\Users\\JeanM\\OneDrive\\Documentos\\OGNILOUD\\OGNILOUD\\src\\resources\\images\\botao_voltar.png";
        ImageIcon iconVoltar = new ImageIcon(botaoVoltarPath);
        Image imagemRedimensionada = iconVoltar.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        iconVoltar = new ImageIcon(imagemRedimensionada);
        JButton botaoVoltar = new JButton(iconVoltar);
        botaoVoltar.setBounds(10, 10, 70, 70);
        botaoVoltar.setContentAreaFilled(false);
        botaoVoltar.setBorderPainted(false);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> new TelaAluno(frame, idAluno));

        RoundedPanel painelFundoPerguntas = new RoundedPanel(30);
        painelFundoPerguntas.setBackground(new Color(217, 217, 217));
        painelFundoPerguntas.setBounds((frame.getWidth() - 817) / 2, 30, 817, 310);
        painelFundoPerguntas.setLayout(null);

        JLabel perguntaLabel = new JLabel("Pergunta:");
        perguntaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        perguntaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        perguntaLabel.setBounds(10, 10, 797, 290);
        painelFundoPerguntas.add(perguntaLabel);

        JPanel painelRespostas = new JPanel();
        painelRespostas.setLayout(new BoxLayout(painelRespostas, BoxLayout.Y_AXIS));
        painelRespostas.setBounds((frame.getWidth() - 817) / 2, 380, 817, 300);
        painelRespostas.setOpaque(false);


        RoundeButton[] botoesResposta = new RoundeButton[4];
        for (int i = 0; i < 4; i++) {
            botoesResposta[i] = new RoundeButton("Resposta " + (i + 1), 30);
            botoesResposta[i].setFont(new Font("Arial", Font.PLAIN, 18));
            botoesResposta[i].setPreferredSize(new Dimension(500, 60));
            botoesResposta[i].setMinimumSize(new Dimension(500, 60));
            botoesResposta[i].setMaximumSize(new Dimension(500, 60));
            botoesResposta[i].setAlignmentX(JButton.CENTER_ALIGNMENT);
            botoesResposta[i].setBackground(new Color(65, 184, 190));
            botoesResposta[i].setForeground(Color.WHITE);
            botoesResposta[i].setFocusPainted(false);
            int index = i;
            botoesResposta[i].addActionListener(e -> verificarResposta(e.getActionCommand(), perguntaLabel, botoesResposta));
            botoesResposta[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    botoesResposta[index].setBackground(new Color(50, 150, 180));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    botoesResposta[index].setBackground(new Color(65, 184, 190));
                }
            });
            painelRespostas.add(botoesResposta[i]);
            painelRespostas.add(Box.createVerticalStrut(10));
        }


        frame.add(botaoVoltar);
        frame.add(painelFundoPerguntas);
        frame.add(painelRespostas);
        frame.add(backgroundLabel);


        carregarPergunta(perguntaLabel, botoesResposta);

        frame.revalidate();
        frame.repaint();
    }

    private static class RoundeButton extends JButton {
        private final int radius;

        public RoundeButton(String text, int radius) {
            super(text);
            this.radius = radius;
            setFocusPainted(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
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
    }

    private void carregarPergunta(JLabel perguntaLabel, JButton[] botoesResposta) {
        try {
            List<Pergunta> perguntas = bancoDeDados.getPerguntasPorIdioma(idiomaSelecionado);
            if (perguntas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Não há perguntas disponíveis para o idioma selecionado.");
                new TelaAluno(frame, idAluno);
                return;
            }

            List<Pergunta> perguntasNaoRespondidas = perguntas.stream()
                    .filter(p -> !perguntasRespondidas.contains(p.getId()))
                    .toList();

            if (perguntasNaoRespondidas.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Você já respondeu todas as perguntas disponíveis.");
                new TelaAluno(frame, idAluno);
                return;
            }

            Random random = new Random();
            perguntaAtual = perguntasNaoRespondidas.get(random.nextInt(perguntasNaoRespondidas.size()));
            perguntasRespondidas.add(perguntaAtual.getId());

            String nomeProfessor = bancoDeDados.getNomeProfessorPorId(perguntaAtual.getId());
            perguntaLabel.setText("<html>" + perguntaAtual.getPergunta() + "<br><i> - Professor " + nomeProfessor + "</i></html>");

            List<String> respostas = new ArrayList<>(perguntaAtual.getOpcoes());
            if (!respostas.contains(perguntaAtual.getRespostaCorreta())) {
                respostas.add(perguntaAtual.getRespostaCorreta());
            }
            while (respostas.size() < 4) {
                respostas.add("Alternativa " + (respostas.size() + 1));
            }
            Collections.shuffle(respostas);

            for (int i = 0; i < botoesResposta.length; i++) {
                botoesResposta[i].setText(respostas.get(i));
                botoesResposta[i].setEnabled(true);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao carregar pergunta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void verificarResposta(String respostaEscolhida, JLabel perguntaLabel, JButton[] botoesResposta) {
        if (respostaEscolhida.equals(perguntaAtual.getRespostaCorreta())) {
            acertos++;
            int xpGanho = 2;


            JButton botaoCorreto = null;
            for (JButton botao : botoesResposta) {
                if (botao.getText().equals(perguntaAtual.getRespostaCorreta())) {
                    botaoCorreto = botao;
                    break;
                }
            }

            if (botaoCorreto != null) {
                JButton finalBotaoCorreto = botaoCorreto;

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        for (int i = 0; i < 7; i++) {
                            finalBotaoCorreto.setBackground(Color.GREEN);
                            Thread.sleep(100);
                            finalBotaoCorreto.setBackground(new Color(65, 184, 190));
                            Thread.sleep(100);
                        }
                        return null;
                    }

                    @Override
                    protected void done() {

                        try {
                            bancoDeDados.atualizarProgresso(idAluno, xpGanho);
                        } catch (SQLException e) {
                            JOptionPane.showMessageDialog(frame, "Erro ao atualizar progresso: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }


                        if (acertos == 5) {
                            JOptionPane.showMessageDialog(frame, "Parabéns! Você acertou 5 perguntas! Retornando à tela do aluno.");
                            try {
                                int xpAtual = bancoDeDados.getProgresso(idAluno);
                                int nivelAtual = bancoDeDados.getNivelAtual(idAluno);
                                frame.getContentPane().removeAll();
                                TelaAluno telaAluno = new TelaAluno(frame, idAluno);
                                telaAluno.atualizarProgressoNaTela(xpAtual, nivelAtual);
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(frame, "Erro ao carregar progresso na tela do aluno: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                            return;
                        }


                        carregarPergunta(perguntaLabel, botoesResposta);
                    }
                };
                worker.execute();
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Resposta Incorreta!");
        }
    }

}
