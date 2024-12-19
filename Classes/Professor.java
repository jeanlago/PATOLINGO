package Classes;

import Interfaces.User;
import java.util.List;

/**
 *
 * @author JeanM
 */
public class Professor implements User {
    private String nome;
    private String email;
    private List<String> idiomas; //idiomas que o professor ensina.

    //construtor
    public Professor(String nome, String email, List<String> idiomas){
        this.nome = nome;
        this.email = email;
        this.idiomas = idiomas;
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
        this.email = email;
    }

    public List<String> getIdiomas(){
        return this.idiomas;
    }


    public void setIdiomas(List<String> idiomas){
        this.idiomas = idiomas;
    }
    //adicionar métodos de adicionar, remover e editar perguntas...
}
