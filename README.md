
# LIBRARY-API

Se trata de uma aplicação em Java com Spring de gerenciamento bibliotecário. Apresenta a listagem dos livros e o recurso de requisição desses livros, assim como um CRUD relacionado ao usuário (Associado).

O admin possui poder de alterar outras contas além da sua, porém cada usuário só pode alterar a própria conta e fazer requisições para a mesma.

O admin é criado automaticamente na primeira vez que rodar a aplicação.




## Tecnologias Utilizadas

- **Java 17**
- **MySQL**
- **Maven**
- **JWT**
- **Spring Boot**
- **Spring Security**
- **Spring Data JPA**
- **Swagger** para documentação de rotas


## Configuração

Para rodar esse projeto, você vai precisar configurar o properties com a conexão com o banco de dados e a senha do JWT.

Na pasta resources possui um properties de exemplo.


## Rodando localmente

Clone o projeto

```bash
  git clone https://github.com/RaaphaelGomesS/Library-api.git
```

Entre no diretório do projeto

```bash
  cd Library-api
```

Execute a aplicação

```bash
  mvn spring-boot:run
```


## Documentação

A documentação está disponível no **Swagger** rodando localmente.

