package Database;

import Classes.Aluno;
import Classes.Pergunta;
import Classes.Professor;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BancoDeDados {
    private static final String URL = "jdbc:mysql://localhost:3306/ogniloud";
    private static final String USER = "root";
    private static final String PASSWORD = "2617";

    private Connection connection;


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

    public void cadastrarAlunoCompleto(Aluno aluno, String senha) throws SQLException {
        String sqlAluno = "INSERT INTO alunos (nome, email, cpf) VALUES (?, ?, ?)";
        String sqlUsuario = "INSERT INTO usuarios (email, senha, tipo, id_aluno, id_professor) VALUES (?, ?, 'aluno', ?, NULL)";

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement stmtAluno = connection.prepareStatement(sqlAluno, Statement.RETURN_GENERATED_KEYS)) {
                stmtAluno.setString(1, aluno.getNome());
                stmtAluno.setString(2, aluno.getEmail());
                stmtAluno.setString(3, aluno.getCpf());
                stmtAluno.executeUpdate();

                ResultSet rs = stmtAluno.getGeneratedKeys();
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }

            try (PreparedStatement stmtUsuario = connection.prepareStatement(sqlUsuario)) {
                stmtUsuario.setString(1, aluno.getEmail());
                stmtUsuario.setString(2, senha);
                stmtUsuario.setInt(3, aluno.getId());
                stmtUsuario.executeUpdate();
            }

            inicializarProgresso(aluno.getId());
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new SQLException("Erro ao cadastrar aluno e usuário: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true);
        }
    }



    public void addAluno(Aluno aluno) throws SQLException {
        String sql = "INSERT INTO alunos (nome, email, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, aluno.getNome());
            statement.setString(2, aluno.getEmail());
            statement.setString(3, aluno.getCpf());
            statement.executeUpdate();


            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setId(rs.getInt(1));
                }
            }
        }
    }


    public void addProfessor(Professor professor) throws SQLException {
        String sql = "INSERT INTO professor (nome, email, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, professor.getNome());
            statement.setString(2, professor.getEmail());
            statement.setString(3, professor.getCpf());
            statement.executeUpdate();


            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    professor.setId(rs.getInt(1));
                }
            }
        }
    }

    public void editarPergunta(Pergunta pergunta) throws SQLException {
        String sql = "UPDATE perguntas SET texto = ?, resposta = ?, alternativa1 = ?, alternativa2 = ?, alternativa3 = ? WHERE idperguntas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pergunta.getPergunta());
            stmt.setString(2, pergunta.getRespostaCorreta());
            stmt.setString(3, pergunta.getOpcoes().get(1));
            stmt.setString(4, pergunta.getOpcoes().get(2));
            stmt.setString(5, pergunta.getOpcoes().get(3));
            stmt.setInt(6, pergunta.getId());
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Erro: Nenhuma pergunta encontrada com o ID: " + pergunta.getId());
            }
        }
    }


    public void excluirPergunta(int idPergunta) throws SQLException {
        String sql = "DELETE FROM perguntas WHERE idperguntas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPergunta);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Pergunta excluída com sucesso! ID: " + idPergunta);
            } else {
                throw new SQLException("Nenhuma pergunta encontrada com o ID: " + idPergunta);
            }
        }
    }

    public void addPergunta(Pergunta pergunta, int professorId) throws SQLException {
        String sql = "INSERT INTO perguntas (texto, resposta, idioma_origem, idioma_destino, nivel, alternativa1, alternativa2, alternativa3, idprofessor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, pergunta.getPergunta());
            statement.setString(2, pergunta.getRespostaCorreta());

            String[] idiomas = pergunta.getIdioma().split(" → ");
            if (idiomas.length != 2) {
                throw new SQLException("Erro: Idioma de origem e destino não estão corretos.");
            }
            statement.setString(3, idiomas[0].trim());
            statement.setString(4, idiomas[1].trim());

            statement.setString(5, pergunta.getNivel());

            List<String> opcoes = pergunta.getOpcoes();
            if (opcoes.size() >= 4) {
                statement.setString(6, opcoes.get(1));
                statement.setString(7, opcoes.get(2));
                statement.setString(8, opcoes.get(3));
            } else {
                throw new SQLException("Erro: Pergunta com opções insuficientes.");
            }

            statement.setInt(9, professorId);
            statement.executeUpdate();
        }
    }



    public List<Pergunta> getPerguntasByProfessor(int professorId) throws SQLException {
        String sql = "SELECT idperguntas, texto, resposta, nivel, alternativa1, alternativa2, alternativa3, idioma_origem, idioma_destino " +
                     "FROM perguntas WHERE idprofessor = ?";
        List<Pergunta> perguntas = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, professorId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                List<String> opcoes = new ArrayList<>();
                opcoes.add(rs.getString("resposta"));
                opcoes.add(rs.getString("alternativa1"));
                opcoes.add(rs.getString("alternativa2"));
                opcoes.add(rs.getString("alternativa3"));

                String idiomaCompleto = rs.getString("idioma_origem") + " → " + rs.getString("idioma_destino");

                Pergunta pergunta = new Pergunta(
                    rs.getInt("idperguntas"),
                    rs.getString("texto"),
                    rs.getString("resposta"),
                    idiomaCompleto,
                    rs.getString("nivel"),
                    opcoes
                );

                perguntas.add(pergunta);
            }
        }
        return perguntas;
    }



    public void addUsuario(String email, String senha, String tipo, Integer idAluno, Integer idProfessor) throws SQLException {
        String sql = "INSERT INTO usuarios (email, senha, tipo, id_aluno, id_professor) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, senha);
            statement.setString(3, tipo);
            statement.setObject(4, idAluno);
            statement.setObject(5, idProfessor);
            statement.executeUpdate();
        }
    }



    public Object verificarLogin(String email, String senha) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email.trim());
            statement.setString(2, senha.trim());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo");
                int idReferencia = tipo.equals("aluno") ? rs.getInt("id_aluno") : rs.getInt("id_professor");

                if (tipo.equals("aluno")) {
                    return getAluno(idReferencia);
                } else {
                    return getProfessor(idReferencia);
                }
            }
        }
        return null;
    }


    public Professor getProfessor(int id) throws SQLException {
    String sql = "SELECT * FROM professor WHERE idprofessor = ?";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return new Professor(id, rs.getString("nome"), rs.getString("email"), rs.getString("cpf"), Arrays.asList("Inglês"));
        }
    }
    return null;
    }

    public Aluno getAluno(int id) throws SQLException {
        String sql = "SELECT * FROM alunos WHERE idalunos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                return new Aluno(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("cpf")
                );
            }
        }
        return null;
    }


    public int getIdUsuarioPorAluno(int idAluno) throws SQLException {
        String sql = "SELECT idusuarios FROM usuarios WHERE id_aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idusuarios");
            }
        }
        throw new SQLException("Nenhum usuário encontrado para o ID do aluno: " + idAluno);
    }


    public int getIdUsuarioPorProfessor(int idProfessor) throws SQLException {
        String sql = "SELECT idusuarios FROM usuarios WHERE id_professor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProfessor);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("idusuarios");
            }
        }
        throw new SQLException("Usuário não encontrado para o professor com ID: " + idProfessor);
    }


    public int getIdReferencia(String email, String tipo) throws SQLException {
        String sql = "SELECT id_aluno, id_professor FROM usuarios WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                if ("aluno".equals(tipo)) {
                    return rs.getInt("id_aluno");
                } else if ("professor".equals(tipo)) {
                    return rs.getInt("id_professor");
                }
            }
        }
        return -1;
    }

    public void inicializarProgresso(int idAluno) throws SQLException {
        String sql = "INSERT INTO progresso_alunos (id_aluno, xp, nivel) VALUES (?, 0, 1)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.executeUpdate();
        }
    }

    public void atualizarProgresso(int idAluno, int xpGanho) throws SQLException {
        String sql = "UPDATE progresso_alunos SET xp = xp + ? WHERE id_aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, xpGanho);
            stmt.setInt(2, idAluno);
            stmt.executeUpdate();
        }
    }

    public void verificarNivel(int idAluno) throws SQLException {
        String sql = "SELECT xp, nivel FROM progresso_alunos WHERE id_aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int xpAtual = rs.getInt("xp");
                int nivelAtual = rs.getInt("nivel");
                if (xpAtual >= 100) {

                    String sqlNivel = "UPDATE progresso_alunos SET nivel = nivel + 1, xp = 0 WHERE id_aluno = ?";
                    try (PreparedStatement stmtNivel = connection.prepareStatement(sqlNivel)) {
                        stmtNivel.setInt(1, idAluno);
                        stmtNivel.executeUpdate();
                    }
                    System.out.println("Aluno atingiu o próximo nível!");
                }
            }
        }
    }

    public int getProgresso(int idAluno) throws SQLException {
        String sql = "SELECT xp FROM progresso_alunos WHERE id_aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("xp");
            }
        }
        return 0;
    }


    public int getNivelAtual(int idAluno) throws SQLException {
        String sql = "SELECT nivel FROM progresso_alunos WHERE id_aluno = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("nivel");
            }
        }
        return 1;
    }

    public int getXPAtual(int idAluno) throws SQLException {
        String sql = "SELECT xp FROM progresso_alunos WHERE id_aluno = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAluno);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("xp");
            }
        }
        return 0;
    }

    public String getNomeAluno(int idAluno) throws SQLException {
        String sql = "SELECT nome FROM alunos WHERE idalunos = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAluno);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        }
        return null;
    }

    public List<String> getListaAlunos() throws SQLException {
        List<String> alunos = new ArrayList<>();
        String sql = "SELECT nome, email FROM alunos";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String aluno = rs.getString("nome") + " - " + rs.getString("email");
                alunos.add(aluno);
            }
        }
        return alunos;
    }

    public List<String> getNomesAlunos() throws SQLException {
        List<String> nomesAlunos = new ArrayList<>();
        String sql = "SELECT nome FROM alunos";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                nomesAlunos.add(rs.getString("nome"));
            }
        }
        return nomesAlunos;
    }

    public List<String> getIdiomasDisponiveis() throws SQLException {
        String sql = "SELECT DISTINCT CONCAT(idioma_origem, ' → ', idioma_destino) AS idioma FROM perguntas";
        List<String> idiomas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                idiomas.add(rs.getString("idioma"));
            }
        }
        return idiomas;
    }



    public String getNomeProfessorPorId(int idPergunta) throws SQLException {
        String sql = "SELECT p.nome FROM professor p JOIN perguntas q ON p.idprofessor = q.idprofessor WHERE q.idperguntas = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPergunta);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        }
        return "Desconhecido";
    }

    public List<Pergunta> getPerguntasPorIdioma(String idiomaSelecionado) throws SQLException {
        String sql = "SELECT idperguntas, texto, resposta, idioma_origem, idioma_destino, nivel, alternativa1, alternativa2, alternativa3 " +
                     "FROM perguntas WHERE CONCAT(idioma_origem, ' → ', idioma_destino) = ?";
        List<Pergunta> perguntas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, idiomaSelecionado);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                List<String> opcoes = new ArrayList<>();
                opcoes.add(rs.getString("resposta"));
                opcoes.add(rs.getString("alternativa1"));
                opcoes.add(rs.getString("alternativa2"));
                opcoes.add(rs.getString("alternativa3"));

                String idiomaCompleto = rs.getString("idioma_origem") + " → " + rs.getString("idioma_destino");
                Pergunta pergunta = new Pergunta(
                    rs.getInt("idperguntas"),
                    rs.getString("texto"),
                    rs.getString("resposta"),
                    idiomaCompleto,
                    rs.getString("nivel"),
                    opcoes
                );
                perguntas.add(pergunta);
            }
        }
        return perguntas;
    }

    public void atualizarFotoPerfil(int idUsuario, String caminhoFoto) throws SQLException {
        String sql = "UPDATE usuarios SET foto_perfil = ? WHERE idusuarios = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, caminhoFoto);
            stmt.setInt(2, idUsuario);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Erro: Nenhum usuário encontrado com o ID: " + idUsuario);
            }
        }
    }

    public String getFotoPerfil(int idUsuario) throws SQLException {
        String sql = "SELECT foto_perfil FROM usuarios WHERE idusuarios = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("foto_perfil");
            }
        }
        return null;
    }

    public void atualizarFundoTela(int idUsuario, String caminhoFoto) throws SQLException {
        String sql = "UPDATE usuarios SET foto_fundo = ? WHERE idusuarios = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            System.out.println("Atualizando fundo: ID = " + idUsuario + ", Caminho = " + caminhoFoto);
            stmt.setString(1, caminhoFoto);
            stmt.setInt(2, idUsuario);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas == 0) {
                throw new SQLException("Nenhum registro encontrado para o ID: " + idUsuario);
            }
        }
    }


    public String getFundoTela(int idUsuario) throws SQLException {
        String sql = "SELECT foto_fundo FROM usuarios WHERE idusuarios = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String caminho = rs.getString("foto_fundo");
                System.out.println("Caminho do fundo recuperado: " + caminho);
                return caminho;
            }
        }
        return null;
    }


}
