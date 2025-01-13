package Classes;

import Interfaces.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


public class Aluno implements User {
    private int idalunos;
    private String cpf;
    private String nome;
    private String email;
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Double> progresso;


    public Aluno(int idalunos, String nome, String email, String cpf) {
        this.idalunos = idalunos;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.progresso = new HashMap<>();
    }


    public Aluno(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.progresso = new HashMap<>();
    }


    public int addAluno(Connection connection) throws SQLException {
        String query = "INSERT INTO alunos (nome, email, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idalunos = rs.getInt(1);
                    return idalunos;
                }
            }
        }
        return -1;
    }


    public boolean updateAluno(Connection connection) throws SQLException {
        String query = "UPDATE alunos SET nome = ?, email = ?, cpf = ? WHERE idalunos = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            stmt.setInt(4, idalunos);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        }
    }


    public boolean deleteAluno(Connection connection) throws SQLException {
        String query = "DELETE FROM alunos WHERE idalunos = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idalunos);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }


    public static Aluno getAlunoById(Connection connection, int idalunos) throws SQLException {
        String query = "SELECT * FROM alunos WHERE idalunos = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idalunos);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(
                    rs.getInt("idalunos"),
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
            }
        }
        return null;
    }


    public int getId() {
        return this.idalunos;
    }

    public void setId(int idalunos) {
        this.idalunos = idalunos;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Double> getProgresso() {
        return this.progresso;
    }

    public void setProgresso(String idiomaNivel, double porcentagem) {
        if (porcentagem < 0 || porcentagem > 100) {
            throw new IllegalArgumentException("Porcentagem deve estar entre 0 e 100.");
        }
        progresso.put(idiomaNivel, porcentagem);
    }

    public double getProgressoPorNivel(String idiomaNivel) {
        return progresso.getOrDefault(idiomaNivel, 0.0);
    }
}
