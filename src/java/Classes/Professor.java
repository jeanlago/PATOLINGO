package Classes;

import Interfaces.User;
import java.util.List;


public class Professor implements User {
    private int id;
    private String nome;
    private String email;
    private String cpf;
    private List<String> idiomas;


    public Professor(int id, String nome, String email, String cpf, List<String> idiomas) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.idiomas = idiomas;
    }


    public Professor(String nome, String email, String cpf, List<String> idiomas) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.idiomas = idiomas;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<String> getIdiomas() {
        return this.idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

}
