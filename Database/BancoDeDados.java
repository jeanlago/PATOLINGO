/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Database;

/**
 *
 * @author JeanM
 */

import Classes.Aluno;
import Classes.Pergunta;
import Classes.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDeDados {
    private static final String URL = "jdbc:mysql://localhost:3306/ogniloud";
    private static final String USER = "root";
    private static final String PASSWORD = "2617";

    private Connection connection;

    //construtor
    public BancoDeDados() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to the database", e);
        }
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    // CRUD for Aluno
    public void addAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (idalunos, nome, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, aluno.getId());
            statement.setString(2, aluno.getNome());
            statement.setString(3, aluno.getEmail());
            statement.executeUpdate();
        }
    }

    public Aluno getAluno(int id) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE idalunos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new Aluno(rs.getString("nome"), rs.getString("email"), rs.getString("cpf"));
            }
        }
        return null;
    }

    public void updateAluno(Aluno aluno) throws SQLException {
        String sql = "UPDATE alunos SET nome = ?, email = ? WHERE idalunos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getEmail());
            statement.setInt(3, aluno.getId());
            statement.executeUpdate();
        }
    }

    public  void deleteAluno(int id) throws SQLException {
        String sql = "DELETE FROM alunos WHERE idalunos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // CRUD para Professor
    public void addProfessor(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (idprofessor, nome, email) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professor.hashCode());
            statement.setString(2, professor.getNome());
            statement.setString(3, professor.getEmail());
            statement.executeUpdate();
        }
    }

    // CRUD for Pergunta
    public void addPergunta(Pergunta pergunta, int professorId) throws SQLException {
        String sql = "INSERT INTO perguntas (idperguntas, texto, resposta, idioma, nivel, idprofessor) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, pergunta.hashCode());
            statement.setString(2, pergunta.getPergunta());
            statement.setString(3, pergunta.getRespostaCorreta());
            statement.setString(4, pergunta.getIdioma());
            statement.setString(5, pergunta.getNivel());
            statement.setInt(6, professorId);
            statement.executeUpdate();
        }
    }

    // Fetch perguntas by professor
    public List<Pergunta> getPerguntasByProfessor(int professorId) throws SQLException {
        String sql = "SELECT * FROM perguntas WHERE idprofessor = ?";
        List<Pergunta> perguntas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Pergunta pergunta = new Pergunta(
                        rs.getString("texto"),
                        rs.getString("resposta"),
                        rs.getString("idioma"),
                        rs.getString("nivel")
                );
                perguntas.add(pergunta);
            }
        }
        return perguntas;
    }
}
