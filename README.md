# Projeto de Sistema de Perguntas e Respostas

Este é um sistema de perguntas e respostas educacional desenvolvido em Java. Ele possui funcionalidades para gerenciar alunos, professores, perguntas, progresso de aprendizado e interações por meio de uma interface gráfica.

## Estrutura do Projeto

O projeto está organizado em diferentes pacotes, cada um contendo classes específicas para uma responsabilidade clara. A seguir, está a estrutura do projeto:

```
├── Classes
│   ├── Aluno.java
│   ├── AlunosPerguntas.java
│   ├── Pergunta.java
│   ├── Professor.java
│   ├── Progresso.java
│   ├── RoundedButton.java
│   ├── RoundedPanel.java
│   ├── RoundedPassword.java
│   ├── RoundedTextField.java
│   ├── TransparentProgressBar.java
│   ├── VerificarLogin.java
│
├── Database
│   ├── BancoDeDados.java
│
├── Grafica
│   ├── TelaAluno.java
│   ├── TelaCadastro.java
│   ├── TelaInicial.java
│   ├── TelaLogin.java
│   ├── TelaPerguntas.java
│   ├── TelaProfessor.java
│
├── Interfaces
│   ├── Perguntas.java
│   ├── User.java
│
├── libs
│   ├── mysql-connector-j-9.0.0.jar
│
├── Main
│   ├── Main.java
```

## Descrição dos Pacotes e Classes

### **Pacote Classes**

- **Aluno.java**: Representa os dados do aluno (nome, email, CPF, etc.).
- **AlunosPerguntas.java**: Armazena o relacionamento entre alunos e perguntas respondidas.
- **Pergunta.java**: Define a estrutura de uma pergunta (texto, resposta correta, alternativas, idiomas, etc.).
- **Professor.java**: Representa os dados do professor (nome, email, CPF, etc.).
- **Progresso.java**: Gerencia o progresso de aprendizado de um aluno (XP, nível, etc.).
- **RoundedButton.java**, **RoundedPanel.java**, **RoundedPassword.java**, **RoundedTextField.java**: Customizações gráficas para melhorar a aparência da interface.
- **TransparentProgressBar.java**: Uma barra de progresso com fundo transparente.
- **VerificarLogin.java**: Valida credenciais de login de usuários.

### **Pacote Database**

- **BancoDeDados.java**: Implementa toda a lógica de comunicação com o banco de dados, incluindo CRUD de alunos, professores e perguntas. Também gerencia progresso e login.

### **Pacote Grafica**

- **TelaAluno.java**: Interface gráfica para alunos, exibindo progresso e acesso a perguntas.
- **TelaCadastro.java**: Tela de cadastro para novos usuários.
- **TelaInicial.java**: Tela de boas-vindas do sistema.
- **TelaLogin.java**: Interface de login.
- **TelaPerguntas.java**: Tela para exibir e responder perguntas.
- **TelaProfessor.java**: Interface gráfica para professores gerenciarem perguntas.

### **Pacote Interfaces**

- **Perguntas.java**: Interface para perguntas (definições de métodos).
- **User.java**: Interface genérica para usuários (aluno e professor).

### **Pacote Main**

- **Main.java**: Classe principal para inicializar o sistema.

### **Pacote libs**

- **mysql-connector-j-9.0.0.jar**: Driver para conexão com o banco de dados MySQL.

## Funcionalidades

1. **Gerenciamento de Usuários**:
   - Cadastro e login de alunos e professores.
   - Associação de credenciais com diferentes perfis de usuário.

2. **Gerenciamento de Perguntas**:
   - Professores podem adicionar, editar e excluir perguntas.
   - Idiomas de origem e destino são atribuídos a cada pergunta.

3. **Sistema de Progresso**:
   - Alunos acumulam XP ao responder perguntas corretamente.
   - Progresso e nível são exibidos em tempo real.

4. **Sistema de Idiomas**:
   - Perguntas são categorizadas por idioma de origem e destino.
   - Alunos podem selecionar o idioma antes de responder perguntas.

5. **Interface Gráfica**:
   - Navegação intuitiva para alunos e professores.
   - Componentes gráficos personalizados (botões arredondados, barra de progresso, etc.).

## Configuração do Banco de Dados

1. Configure o MySQL e crie um banco de dados chamado `ogniloud`.
2. Importe o driver MySQL Connector (localizado em `libs/mysql-connector-j-9.0.0.jar`).
3. Certifique-se de que o arquivo `BancoDeDados.java` tenha as credenciais corretas:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/ogniloud";
   private static final String USER = "root";
   private static final String PASSWORD = "2617";
   ```

4. Execute o script SQL fornecido (se aplicável) para criar as tabelas necessárias.

## Como Executar o Projeto

1. Certifique-se de que o MySQL está em execução.
2. Compile o projeto usando um IDE como IntelliJ IDEA ou Eclipse.
3. Certifique-se de adicionar o driver MySQL ao classpath do projeto.
4. Execute a classe `Main.java`.

## Requisitos do Sistema

- Java 8 ou superior.
- MySQL 8.0 ou superior.
- IDE para Java (IntelliJ IDEA, Eclipse, etc.).

## Estrutura do Banco de Dados

O banco de dados contém as seguintes tabelas principais:

- **alunos**: Armazena informações de alunos.
- **professor**: Armazena informações de professores.
- **usuarios**: Gerencia as credenciais de login.
- **perguntas**: Contém perguntas, alternativas e idiomas relacionados.
- **progresso_alunos**: Gerencia o progresso de XP e nível dos alunos.

## Observações

Este sistema é um projeto em andamento e pode ser ampliado com novas funcionalidades, como relatórios ou integração com APIs externas.

## Licença

Este projeto está licenciado sob a MIT License.

