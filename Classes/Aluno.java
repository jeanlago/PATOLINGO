package Classes;

import Interfaces.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JeanM
 */
public class Aluno implements User{
    private int idalunos;
    private String cpf;
    private String nome;
    private String email;
    @SuppressWarnings("FieldMayBeFinal")
    private Map<String, Double> progresso; //(idioma e nível -> porcentagem de conclusão)

    //construtor
    public Aluno(String nome, String email, String cpf){
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.progresso = new HashMap<>(); //inicializando o mapa
    }

    // Método para adicionar aluno ao banco de dados
    public int addAluno(Connection connection) throws SQLException {
        String query = "INSERT INTO alunos (nome, email, cpf) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.setString(3, cpf);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    this.idalunos = rs.getInt(1); // Retorna o ID gerado
                    return idalunos;
                }
            }
        }
        return -1;
    }

    public int getId(){
        return this.idalunos;
    }

    public void setId(int idalunos){
        this.idalunos = idalunos;
    }

    public String getCpf(){
        return this.cpf;
    }

    public void setCpf(String cpf){
        this.cpf = cpf;
    }

    @Override
    public String getNome(){
        return this.nome;
    }

    @Override
    public void setNome(String nome){
        this.nome = nome;
    }

    @Override
    public String getEmail(){
        return this.email;
    }

    @Override
    public void setEmail(String email){
        this.email =email;
    }

    public Map<String, Double> getProgresso(){
        return this.progresso;
    }

    public void setProgresso(String idiomaNivel, double porcentagem){
        if (porcentagem<0 || porcentagem>100){
            throw new IllegalArgumentException("Porcentagem deve estar entre 0 e 100.");
        }
        progresso.put(idiomaNivel, porcentagem); // Atualiza ou adiciona o progresso
    }

    public double getProgressoPorNivel(String idiomaNivel){
        return progresso.getOrDefault(idiomaNivel, 0.0); //retorna 0 se não existe nivel
    }


}
