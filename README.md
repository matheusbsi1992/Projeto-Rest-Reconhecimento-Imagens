# Projeto (REST) - Spring Boot

## Descrição
Bem-vindo ao repositório do projeto **Rest Reconhecimento de Imagens**, um projeto de demonstração que esta sendo desenvolvido com o framework Spring Boot. Este projeto utiliza tecnologias modernas para criar uma aplicação web com integrações robustas e arquitetura escalável. Aqui você encontra uma solução abrangente para implementar e estudar conceitos de desenvolvimento backend utilizando o Java.

## Tecnologias Utilizadas

### Frameworks e Ferramentas Principais:
- **Spring Boot 3.2.8**: Framework principal utilizado para criar aplicações Java simplificadas.
- **Spring Data JPA**: Para interação com bancos de dados relacionais.
- **Spring Boot Starter Web**: Para desenvolvimento de APIs REST.
- **Spring Boot Starter Security (JWT)**: Implementação de segurança.
- **Flyway**: Controle de versão de esquemas de banco de dados.
- **MapStruct**: Geração automática de mapeamento entre objetos Java.
- **Java JWT**: Implementação de autenticação baseada em tokens JWT.
- **PostgreSQL**: Banco de dados utilizado para persistência.

### Dependências de Desenvolvimento:
- **Spring Boot DevTools**: Para facilitar o desenvolvimento com recarregamento automático.
- **Spring Boot Starter Validation**: Validação de dados de entrada.
- **Spring Boot Starter Test**: Para testes unitários e de integração.
- **Lombok**: Reduz a boilerplate de código em classes Java.

## Requisitos

- **JDK 22**
- **Maven 3.8+**
- **PostgreSQL 16**

## Configuração do Projeto

### Clone o Repositório
```bash
git clone (https://github.com/matheusbsi1992/Projeto-Rest-Reconhecimento-Imagens).git
cd estudos
```

### Configure o Banco de Dados
Certifique-se de que o PostgreSQL esteja em execução e crie um banco de dados chamado `estudos`. Configure as credenciais de acesso no arquivo `application.yml`:

```propertiesserver:
  port: 9293
security:
  jwt:
    token:
      secret-key: L(Al$%@@!#%$(_=--<M>MMJJamedaD0s#rrfr1611)caAFGWER#$¨¨¨¨&**()((!@@#_+__($%!!@@---++++=2ALM@DA1LuizH3nriQu3GreTcheMFMM3x0
      expire-length: 3600000
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/estudos_laudos
    username: postgres
    password: postgres
jpa:
  open-in-view: false
  hibernate:
      ddl-auto: update
      show-sql: true
      format-sql: true
flyway:
  enabled: true
  locations: classpath:db/migration
  baseline-on-migrate: true
  schemas: estudos
spring-doc:
  pathsToMatch:
    - /auth/**
    - /api/**/v1/**
swagger-ui:
  user-root-path: true
```

### Executando a Aplicação
Compile e inicie o projeto:

```bash
mvn spring-boot:run
```

A aplicação estará acessível em [http://localhost:9293](http://localhost:9293).

## Estrutura do Projeto

```
├── src
│   ├── main
│   │   ├── java
│   │   │   └── br.com.projeto.estudos
│   │   │       ├── config
│   │   │       ├── controler
│   │   │       ├── dto
│   │   │       ├── exceptions
│   │   │       ├── execucao
│   │   │       ├── mapeamento
│   │   │       ├── modelo
│   │   │       ├── repositorio
│   │   │       ├── seguranca.jwt
│   │   │       ├── servico
│   │   │       ├── util
│   │   │       ├── validacao
│   │   └── resources
│   │       ├──db.migration
│   │       └── application.yaml
│   └── test
│       └── java
│           └── br.com.projeto.estudos
```

### Pacotes Principais
- **Controler**: Gerencia as requisições HTTP.
- **Servico**: Contém a lógica de negócio.
- **Repositorio**: Interage com o banco de dados.
- **Modelo**: Representa as entidades de domínio.
- **Config**: Configurações adicionais do projeto (Swagger e SecurityConfig).
- **Exceptions**: Analisa as excecoes sobre as entidades de domínio.
- **Execucao**: Executa o projeto.
- **Mapeamento**: Altera o boilerplate pelo MapStruct entre as classes DTO e as entidades.
- **DTO**: Representa as classes de mapeamento de obejtos da classe de dominio.
- **UTIL**: Representa a classe de utilidades (Geracao de Senha encriptada, etc).
- **VALIDACAO**: Contém a analise de lógica de negócios sobre cada uma das classes DTO.
### Recursos Estáticos
- **Templates**: Arquivos HTML renderizados pelo Thymeleaf.

## Documentação da API
A documentação da API está disponível através do SpringDoc OpenAPI. Acesse [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) para visualizar e testar os endpoints.

## Funcionalidades Implementadas

- **CRUD Completo**: Para as entidades principais.
- **Validação de Dados**: Utilizando a anotação `@Valid`.
- **Autenticação JWT**: Segurança robusta com tokens JSON Web Tokens.
- **Migração de Banco de Dados**: Gerenciado pelo Flyway.
- **Mapeamento Automático**: Com MapStruct.

## Contribuição
Contribuições são bem-vindas! Siga os passos abaixo para contribuir:
1. Faça um fork do repositório.
2. Crie um branch para sua feature (`git checkout -b feature/nome-da-feature`).
3. Realize as modificações e faça commit (`git commit -m 'Adiciona nova funcionalidade'`).
4. Envie suas alterações (`git push origin feature/nome-da-feature`).
5. Abra um pull request.

---

Sinta-se à vontade para reportar problemas, sugerir melhorias ou contribuir com novas funcionalidades!

