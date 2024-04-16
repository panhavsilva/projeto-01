![logo.png](/images/logo.png)
 <h1 align="center"> Módulo 1 - Projeto Avaliativo 01 </h1>

## ✒️ Autora
👤 Pâmela Vieira da Silva - [@panhavsilva](https://github.com/panhavsilva)

## ✏️ Descrição
Este projeto consiste em uma API REST completa para gestão de cursos, turmas, conteúdos, docentes e alunos. 
Utiliza tecnologias como Java com Spring Security, Spring REST, e PostgreSQL como banco de dados. 
O projeto é idealizado para integrar soluções web de gestão em escolas e creches da rede pública.

## ❗ Funcionalidades Principais
- Cadastro, consulta, atualização e exclusão de alunos, cursos, docentes, matérias, notas e turmas.
- Autenticação de usuários com geração de token JWT.
- Controle de acesso baseado em papéis (roles) dos usuários.

## ⚠️ Requisitos do Sistema
- Java 11 ou superior
- PostgreSQL
- Maven

## 🔧 Configuração do Banco de Dados
O sistema utiliza PostgreSQL como banco de dados. 
Certifique-se de criar um banco de dados com o nome meuBancoDeDados e configurar o usuário e senha corretamente 
no arquivo application.properties.
```bash
spring.datasource.url=jdbc:postgresql://localhost:1432/meuBancoDeDados
spring.datasource.username=meuUsuario
spring.datasource.password=minhaSenha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

Para criar container docker com as configurações corretas segue comando:
```bash
docker run -d  --name meu-postgres-container  -e POSTGRES_PASSWORD=minhaSenha  -e POSTGRES_USER=meuUsuario  -e POSTGRES_DB=meuBancoDeDados  -p 1432:5432  postgres:latest
```
Isso criará um container Docker com o PostgreSQL configurado conforme especificado.

### Script SQL Inicial
Ao iniciar o programa, o seguinte script SQL será executado para inserir dados iniciais no banco:
```sql
INSERT INTO papel (id, nome)
VALUES
    (1, 'ADMIN'),
    (2, 'PEDAGOGICO'),
    (3, 'RECRUITER'),
    (4, 'PROFESSOR'),
    (5, 'ALUNO')
ON CONFLICT (id) DO NOTHING;

INSERT INTO usuario (id, nome_usuario, senha, id_papel)
VALUES (1, 'dummy', '$2a$10$R8dSgqpWIu.BwmV42pwI.O6a3dHdb1IlQplFnRGiNIPj8hitI6CNy', 1)
ON CONFLICT (id) DO NOTHING;
```
Para seus testes faça login com o usuário ``dummy`` com a senha ``123``, assim você poderá criar novos usuários. 

## 📌 Como usar
Para rodar o projeto localmente, siga estas etapas:
1. Clone o [repositório](https://github.com/panhavsilva/projeto-01) do projeto.
2. Certifique-se de ter o Java JDK instalado na sua máquina.
3. Importe o projeto na sua IDE preferida.
4. Certifique-se de ter as dependências do Maven instaladas. Caso esteja utilizando o IntelliJ IDEA, a IDE fará isso automaticamente.
5. Execute a classe principal Application.java para iniciar a aplicação Spring Boot, ou você pode usar o Maven, navegue até o diretório raiz do projeto e execute o seguinte comando:
   ```bash
   mvn spring-boot:run
   ```
6. A aplicação estará disponível em http://localhost:8080.

## 🎯 Endpoints
A seguir está a documentação dos endpoints do sistema:

### Alunos
- GET /alunos: Retorna todos os alunos cadastrados. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- GET /alunos/{id}: Retorna um aluno específico pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- GET /alunos/{id}/notas: Retorna um aluno específico pelo ID com suas notas. 
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO, PROFESSOR e ALUNO.
- GET /alunos/{id}/pontuacao: Retorna um aluno específico pelo ID com sua pontuação final. 
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO e ALUNO.
- POST /alunos: Cadastra um novo aluno. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody: 
    ```JSON
     {
          "nome": "João Silva",
          "dataNascimento": "1988-05-20",
          "usuarioId": 5,
          "turmaId": 1
     }
    ```
- PUT /alunos/{id}: Atualiza as informações de um aluno existente pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody: 
    ```JSON
    {
          "nome": "João Silva",
          "dataNascimento": "1988-05-20",
          "usuarioId": 5,
          "turmaId": 1
    }
    ```
- DELETE /alunos/{id}: Exclui um aluno pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Cursos
- GET /cursos: Retorna todos os cursos cadastrados.
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- GET /cursos/{id}: Retorna um curso específico pelo ID.
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- GET /cursos/{id}/materias: Retorna um curso específico pelo ID com suas matérias.
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- POST /cursos: Cadastra um novo curso. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody: 
    ```json
    {
      "nome": "Futuro Dev"
    }
    ``` 
- PUT /cursos/{id}: Atualiza as informações de um curso existente pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody:
    ```json
    {
      "nome": "Futuro Dev"
    }
    ```  
- DELETE /cursos/{id}: Exclui um curso pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Docentes
- GET /docentes: Retorna todos os docentes cadastrados.
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO e RECRUITER.
- GET /docentes/{id}: Retorna um docente específico pelo ID.
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO e RECRUITER.
- POST /docentes: Cadastra um novo docente. 
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO e RECRUITER.
  - RequestBody: 
    ```json
    {
      "nome": "Mary Doe",
      "dataEntrada": "2024-04-09",
      "usuarioId": 2
    }
    ```  
- PUT /docentes/{id}: Atualiza as informações de um docente existente pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN, PEDAGOGICO e RECRUITER.
  - RequestBody: 
    ```json
    {
      "nome": "Mary Doe",
      "dataEntrada": "2024-04-09",
      "usuarioId": 2
    }
    ``` 
- DELETE /docentes/{id}: Exclui um docente pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Matérias
- GET /materias: Retorna todas as matérias cadastradas.
  - Rota liberada apenas para o papel ADMIN.
- GET /materias/{id}: Retorna uma matéria específica pelo ID. 
  - Rota liberada apenas para o papel ADMIN.
- POST /materias: Cadastra uma nova matéria. 
  - Rota liberada apenas para o papel ADMIN.
  - RequestBody: 
    ```json
    {
        "nome": "HTML",
        "cursoId": 1
    }
    ``` 
- PUT /materias/{id}: Atualiza as informações de uma matéria existente pelo ID. 
  - Rota liberada apenas para o papel ADMIN.
  - RequestBody: 
    ```json
    {
        "nome": "JAVA",
        "cursoId": 1
    }
    ```  
- DELETE /materias/{id}: Exclui uma matéria pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Notas
- GET /notas: Retorna todas as notas cadastradas.
  - Rota liberada apenas para os papéis: ADMIN, PROFESSOR.
- GET /notas/{id}: Retorna uma nota específica pelo ID.
  - Rota liberada apenas para os papéis: ADMIN, PROFESSOR.
- POST /notas: Cadastra uma nova nota. 
  - Rota liberada apenas para os papéis: ADMIN, PROFESSOR.
  - RequestBody: 
    ```json
    {
        "nota": 7.0,
        "alunoId": 1,
        "materiaId": 1
    }
    ``` 
- PUT /notas/{id}: Atualiza as informações de uma nota existente pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN, PROFESSOR.
  - RequestBody: 
    ```json
    {
        "nota": 10.0,
        "alunoId": 1,
        "materiaId": 1
    }
    ```
- DELETE /notas/{id}: Exclui uma nota pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Turmas
- GET /turmas: Retorna todas as turmas cadastradas.
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- GET /turmas/{id}: Retorna uma turma específica pelo ID.
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
- POST /turmas: Cadastra uma nova turma. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody: 
    ```json
    {
          "nome": "Education",
          "professorId": 1,
          "cursoId": 1
    }
    ```
- PUT /turmas/{id}: Atualiza as informações de uma turma existente pelo ID. 
  - Rota liberada apenas para os papéis: ADMIN e PEDAGOGICO.
  - RequestBody: 
    ```json
    {
          "nome": "Education",
          "professorId": 1,
          "cursoId": 1
    }
    ```
  
- DELETE /turmas/{id}: Exclui uma turma pelo ID. 
  - Rota liberada apenas para o papel ADMIN.

### Usuários
- POST /cadastro: Endpoint para cadastrar novos usuários. 
  - Rota liberada apenas para o papel ADMIN.
  - Faça login com o usuário ``dummy``, senha ``123`` que já está no banco para este teste.  
    - RequestBody: 
      ```json
      {
          "nomeUsuario": "dummy",
          "senha": "123",
          "papel": "ADMIN"
      }
      ```
- GET /usuarios: Retorna todos os usuários cadastrados. 
  - Rota liberada apenas para o papel ADMIN.

### Teste
- GET /teste: Endpoint de teste para verificar a conexão.
  - Rota sem travas quanto a papéis.

### Login
- POST /login: Endpoint para autenticar usuários e gerar token JWT. 
  - Rota sem travas quanto a papéis.
  - RequestBody:
    ```json
      {
        "nomeUsuario": "dummy",
        "senha": "123"
      }
    ```

---
## 🔒 Segurança
O sistema utiliza uma camada de segurança baseada em papéis (roles) para proteger os endpoints. 
Certifique-se de fornecer as configurações adequadas no arquivo SecurityConfig para controlar o acesso aos endpoints 
com base nos papéis dos usuários.

## 📈 Gestão do Projeto
Este projeto foi gerido utilizando o Trello. Você pode encontrar a organização do projeto [aqui](https://trello.com/b/cAO1oe9X).

## ✨ Mostre seu apoio
Dê uma ⭐️ se esse projeto te ajudou!

## 📝 Licença
Copyright © current [Pâmela Vieira da Silva - @panhavsilva](https://github.com/panhavsilva). <br/>
Este projeto é licenciado sob a MIT. Veja o arquivo [LICENSE](/LICENSE.md) para obter detalhes.

--- 
Este README serve como um guia básico para entender e configurar o projeto. 
Para informações mais detalhadas sobre cada parte do sistema, consulte o código-fonte.
