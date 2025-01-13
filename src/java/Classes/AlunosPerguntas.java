package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AlunosPerguntas {

    private int idalunos;
    private int idperguntas;


    public AlunosPerguntas(int idalunos, int idperguntas) {
        this.idalunos = idalunos;
        this.idperguntas = idperguntas;
    }


    public boolean associateAlunoPergunta(Connection connection) throws SQLException {
        if (!isAssociationExists(connection)) {
            String query = "INSERT INTO alunos_perguntas (idalunos, idperguntas) VALUES (?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, idalunos);
                stmt.setInt(2, idperguntas);
                stmt.executeUpdate();
                return true;
            }
        }
        return false;
    }


    public void removeAssociation(Connection connection) throws SQLException {
        String query = "DELETE FROM alunos_perguntas WHERE idalunos = ? AND idperguntas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idalunos);
            stmt.setInt(2, idperguntas);
            stmt.executeUpdate();
        }
    }


    public boolean isAssociationExists(Connection connection) throws SQLException {
        String query = "SELECT * FROM alunos_perguntas WHERE idalunos = ? AND idperguntas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idalunos);
            stmt.setInt(2, idperguntas);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }


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
