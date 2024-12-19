
package Classes;

import Interfaces.Perguntas;
import java.util.List;

/**
 *
 * @author JeanM
 */
public class Pergunta implements Perguntas {
    private String respostaCorreta;
    private String pergunta;
    private String idioma;
    private String nivel;
    private List<String> opcoes;

    //Construtor
    public Pergunta(String pergunta, String respostaCorreta, String idioma, String nivel){
        this.pergunta = pergunta;
        this.respostaCorreta = respostaCorreta;
        this.idioma = idioma;
        this.nivel = nivel;
        this.opcoes = null;
    }

    @Override
    public String getPergunta(){
        return this.pergunta;
    }

    @Override
    public void setPergunta(String pergunta){
        this.pergunta = pergunta;
    }

    @Override
    public String getRespostaCorreta(){
        return this.respostaCorreta;
    }

    @Override
    public void setRespostaCorreta(String resposta){
        this.respostaCorreta = resposta;
    }

    public String getIdioma(){
        return this.idioma;
    }

    public void setIdioma(String idioma){
        this.idioma = idioma;
    }

    @Override
    public List<String> getOpcoes(){
        return this.opcoes;
    }

    @Override
    public void setOpcoes(List<String> opcoes){
        this.opcoes = opcoes;
    }

    public String getNivel(){
        return this.nivel;
    }

    public void setNivel(String nivel){
        this.nivel = nivel;
    }
}
