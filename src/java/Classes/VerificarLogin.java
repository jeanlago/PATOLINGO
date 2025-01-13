package Classes;

import Database.BancoDeDados;
import java.sql.SQLException;
import javax.swing.*;

public class VerificarLogin {
    private final BancoDeDados bancoDeDados;

    public VerificarLogin(BancoDeDados bancoDeDados) {
        this.bancoDeDados = bancoDeDados;
    }

    public void autenticarUsuario(JFrame frame, String email, String senha) {
        try {
            Object usuario = bancoDeDados.verificarLogin(email.trim(), senha.trim());
            if (usuario instanceof Aluno aluno) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido! Bem-vindo, " + aluno.getNome() + "!");
                new Grafica.TelaAluno(frame, bancoDeDados.getIdReferencia(email, "aluno"));
            } else if (usuario instanceof Professor professor) {
                JOptionPane.showMessageDialog(frame, "Login bem-sucedido! Bem-vindo, Professor " + professor.getNome() + "!");

                new Grafica.TelaProfessor(frame, bancoDeDados.getIdReferencia(email, "professor"));
            } else {
                JOptionPane.showMessageDialog(frame, "Usuário inexistente ou senha incorreta!", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Erro ao acessar o banco de dados: " + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
        }
    }
}
