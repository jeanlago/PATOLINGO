package Classes;

public class Progresso {
    private int progresso;

    public Progresso() {
        this.progresso = 0;
    }


    public void adicionarProgresso(int perguntasRespondidas) {
        this.progresso += perguntasRespondidas * 10;
        if (this.progresso > 100) {
            this.progresso = 100;
        }
    }


    public int getProgresso() {
        return this.progresso;
    }


    public void resetarProgresso() {
        this.progresso = 0;
    }
}
