package Interfaces;

import java.util.List;

/**
 *
 * @author JeanM
 */
public interface Perguntas {
    String getPergunta();
    void setPergunta(String pergunta);
    List<String> getOpcoes();
    void setOpcoes(List<String> opcoes);
    String getRespostaCorreta();
    void setRespostaCorreta(String Resposta);
}
