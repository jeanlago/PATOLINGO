interfaces:
    User: interface na qual professor e alunos devem herdar e implementar seus métodos.
    Perguntas: interface na qual abstrai operações relacionadas às perguntas, permitindo flexibilidade no armazenamento ou geração de perguntas.

Classes:
    Aluno: Classe de usuário que representa o aluno e gerenciar seu progresso.
    Professor: Classe de usuário que representa o professor e permite que ele edite perguntas e/ou respostas.
    Pergunta: Classe que representa as perguntas, suas opções e a resposta correta.
    AlunosPerguntas: Classe que faz a conexão entre os alunos e as perguntas no banco de dados.
    BancoDeDados: Classe responsável por gerenciar os atributos e métodos que conectam o corpo do programa principal com o banco de dados.