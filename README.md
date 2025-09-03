# PetTour API 🐾

API RESTful desenvolvida em Java com Spring Boot para o backend do aplicativo PetTour, um catálogo de serviços para pets.

## 📜 Descrição

Este projeto é o backend responsável por toda a regra de negócio, segurança e persistência de dados do sistema PetTour. 
Ele fornece endpoints para gerenciamento de usuários, autenticação, perfis e um CRUD completo para os serviços oferecidos.

## ✨ Funcionalidades

* **Autenticação Segura:** Sistema de registro e login com senhas criptografadas e autenticação via JSON Web Tokens (JWT).
* **Gerenciamento de Serviços:** CRUD completo (Criar, Ler, Atualizar, Deletar) para os serviços do catálogo.
* **Gerenciamento de Perfis:** Usuários autenticados podem visualizar e atualizar suas próprias informações de perfil.
* **Validação de Dados:** Proteção contra dados de entrada inválidos (ex: emails mal formatados, campos em branco).
* **Tratamento de Erros:** Respostas de erro padronizadas e claras para o cliente.

## 🛠️ Tecnologias Utilizadas

* **Java 17**
* **Spring Boot 3**
* **Spring Security:** Para segurança e JWT.
* **Spring Data JPA / Hibernate:** Para persistência de dados.
* **H2 Database:** Banco de dados em memória/arquivo para ambiente de desenvolvimento.
* **Maven:** Gerenciador de dependências e build.
* **Lombok:** Para redução de código boilerplate.
* **Jakarta Bean Validation:** Para validação dos dados de entrada.
* **java-jwt (Auth0):** Para geração e validação de tokens JWT.

## 🚀 Como Executar o Projeto

Siga os passos abaixo para executar a API localmente.

### Pré-requisitos

* **JDK 17** ou superior.
* **Maven 3.8** ou superior.

### Passos para Execução

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    cd [NOME_DA_PASTA_DO_PROJETO]
    ```

2.  **Compile e execute o projeto usando o Maven Wrapper:**
    * No Windows:
      ```bash
      ./mvnw spring-boot:run
      ```
    * No Linux ou macOS:
      ```bash
      ./mvnw spring-boot:run
      ```

3.  A API estará rodando em `http://localhost:8080`.

### Acessando o Banco de Dados H2

* Com a aplicação rodando, acesse `http://localhost:8080/h2-console` no seu navegador.
* Use as seguintes credenciais para conectar:
    * **JDBC URL:** `jdbc:h2:file:./pettourdb`
    * **User Name:** `sa`
    * **Password:** `password`

## ⚙️ Configuração

As configurações principais podem ser encontradas no arquivo `src/main/resources/application.properties`. 
A configuração mais importante é a chave secreta para a assinatura dos tokens JWT:

```properties
# Chave secreta para JWT
api.jwt.secret=SuaChaveSuperSecretaAqui
```

## Endpoints da API

A seguir, a documentação dos endpoints disponíveis na API.

---

### Autenticação (`/auth`)

#### `POST /auth/registrar`
Registra um novo usuário no sistema.
* **Protegido:** Não
* **Corpo da Requisição (JSON):**
    ```json
    {
        "nome": "Leo",
        "email": "leo@email.com",
        "senha": "senha123"
    }
    ```
* **Resposta de Sucesso (200 OK):** O objeto do usuário criado (com a senha criptografada).

#### `POST /auth/login`
Autentica um usuário e retorna um token JWT.
* **Protegido:** Não
* **Corpo da Requisição (JSON):**
    ```json
    {
        "email": "leo@email.com",
        "senha": "senha123"
    }
    ```
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    }
    ```

---

### Perfil do Usuário (`/perfil`)

> **Nota:** Todos os endpoints de perfil requerem um token JWT no cabeçalho `Authorization: Bearer <token>`.

#### `GET /perfil`
Retorna as informações do perfil do usuário atualmente logado.
* **Protegido:** Sim
* **Resposta de Sucesso (200 OK):**
    ```json
    {
        "id": 1,
        "nome": "Leo",
        "email": "leo@email.com",
        "telefone": "21999998888",
        "fotoUrl": "[http://example.com/foto.jpg](http://example.com/foto.jpg)"
    }
    ```

#### `PUT /perfil`
Atualiza as informações do perfil do usuário logado.
* **Protegido:** Sim
* **Corpo da Requisição (JSON):**
    ```json
    {
        "nome": "Leo Atualizado",
        "telefone": "21987654321",
        "fotoUrl": "[http://example.com/nova-foto.jpg](http://example.com/nova-foto.jpg)"
    }
    ```
* **Resposta de Sucesso (200 OK):** O objeto do perfil já com os dados atualizados.

---

### Serviços (`/servicos`)

#### `GET /servicos`
Lista todos os serviços cadastrados.
* **Protegido:** Não
* **Resposta de Sucesso (200 OK):** Uma lista de objetos de serviço.

#### `POST /servicos`
Cadastra um novo serviço.
* **Protegido:** Sim (Requer token JWT)
* **Corpo da Requisição (JSON):**
    ```json
    {
        "nome": "Banho e Tosa",
        "descricao": "Banho completo com produtos antialérgicos.",
        "preco": 89.90,
        "contato": "contato@petshop.com"
    }
    ```
* **Resposta de Sucesso (200 OK):** O objeto do serviço criado, com seu novo `id`.

#### `GET /servicos/{id}`
Busca um serviço específico pelo seu ID.
* **Protegido:** Sim (Requer token JWT)
* **Resposta de Sucesso (200 OK):** O objeto do serviço encontrado.

#### `PUT /servicos/{id}`
Atualiza um serviço existente pelo seu ID.
* **Protegido:** Sim (Requer token JWT)
* **Corpo da Requisição (JSON):** Similar ao do `POST`.
* **Resposta de Sucesso (200 OK):** O objeto do serviço atualizado.

#### `DELETE /servicos/{id}`
Deleta um serviço pelo seu ID.
* **Protegido:** Sim (Requer token JWT)
* **Resposta de Sucesso (204 No Content):** Resposta vazia, indicando sucesso.
