package Classes;

import java.sql.*;

public class AlunosPerguntas {

    private int idalunos;
    private int idperguntas;

    // Construtor
    public AlunosPerguntas(int idalunos, int idperguntas) {
        this.idalunos = idalunos;
        this.idperguntas = idperguntas;
    }

    // Método para associar aluno e pergunta no banco de dados
    public void associateAlunoPergunta(Connection connection) throws SQLException {
        String query = "INSERT INTO Alunos_Perguntas (idalunos, idperguntas) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idalunos);
            stmt.setInt(2, idperguntas);
            stmt.executeUpdate();
        }
    }

    // Getters e Setters
    public int getIdalunos() {
        return idalunos;
    }

    public void setIdalunos(int idalunos) {
        this.idalunos = idalunos;
    }

    public int getIdperguntas() {
        return idperguntas;
    }

    public void setIdperguntas(int idperguntas) {
        this.idperguntas = idperguntas;
    }
}
