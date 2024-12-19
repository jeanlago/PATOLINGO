package Classes;

import Interfaces.User;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author JeanM
 */
public class Aluno implements User{
    private int CPF; //essa é a ID do aluno e não pode ser alterada.
    private String nome;
    private String email;
    @SuppressWarnings("FieldMayBeFinal")// parar de dar aviso que progresso deve ser final (imutavel).
    private Map<String, Double> progresso; //(idioma e nível -> porcentagem de conclusão)

    //construtor
    public Aluno(int CPF, String nome, String email){
        this.nome = nome;
        this.email = email;
        this.progresso = new HashMap<>(); //inicializando o mapa
    }

    public int getId(){
        return this.CPF;
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
