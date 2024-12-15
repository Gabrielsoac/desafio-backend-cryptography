[![Typing SVG](https://readme-typing-svg.herokuapp.com?font=Fira+Code&pause=1000&color=F70000&width=435&lines=Desafio+Backend+-+Cryptography+)](https://git.io/typing-svg)

Desafio da Backend Brasil - https://github.com/backend-br/desafios

# O Desafio

Seu desafio será implementar a criptografia em um serviço de forma transparente para a API e para as camadas de serviço de sua aplicação. O objetivo é garantir que os campos sensíveis dos objetos de entidade não sejam visíveis diretamente, realizando a criptografia em tempo de execução durante a conversão da entidade para a coluna correspondente no banco de dados, e vice-versa.

# Solução

## Ferramentas Utilizadas
- Java 17
- Spring Boot 3.x.x
- Maven
- PostgreSQL 16
- Docker
- FlyWay (Migrattions)

  <br>

  ## Comece por aqui
 
  ### O Projeto pode ser executado utilizando Docker Compose  (RECOMENDADO)
  - Caso não saiba como instalar e configurar o Docker, siga a documentação: https://docs.docker.com/engine/install/

    <br>

  Faça um clone do projeto em uma pasta de sua preferência
  ```
  git clone https://github.com/Gabrielsoac/desafio-backend-cryptography.git
  ```
 
  Com o Docker instalado, navegue até a pasta inicial com o arquivo **docker-compose.yaml** utilize o comando para iniciar o projeto: <br>
  ```
  docker compose up -d --build
  ```
  Desta forma, provavelmente seu projeto já terá sido iniciado! <br>

  Caso queira parar o projeto, utilize: <br>
  ```
  docker compose down
  ```

  <br>

  Caso ocorra algum problema de "port already in use", o projeto utiliza a porta 8080 para executar a API e a 5432 para o banco de dados (Padrão do PostgreSQL). <br>
  Lembre-se de fechar qualquer serviço que esteja utilizando essas portas

  ### Caso tenha erros com a porta 5432 (Debian/Ubuntu)
  Utilize o comando: <br>
  ```
  sudo systemctl stop postgres
  ```
  ou

  ```
  sudo service postgresql stop
  ```
  
  Isso pausará o postgres do seu host, mas não se preocupe, após testar a API, poderá ativar seu postgres novamente com o comando: <br>
    ```
  sudo systemctl start postgres
  ```
  ou
  ```
  sudo service postgresql start
  ```
  
  <br>
  
  ## Projeto 
  A aplicação foi dockerizada com Multi-staging no Dockerfile para disponibilizar uma imagem mais leve e também automatizada com Docker-compose para fácil inicialização.


  ## END-POINTS
  
  ### **Listar todos Purchase**

  - **Endpoint**: `/purchase`
  - **Método**: `GET`
  - **Código**: `200 OK`
  - **Descrição**: Busca todos os purchase.
  
  Exemplo de URL:
  ```
  localhost:8080/purchase
  ```
  <br>

  Exemplo de JSON retornado

  ```
  {
    {
        "id": 5,
        "userDocument": "12340987",
        "creditCardToken": "1234-5432-2123-5423",
        "value": 1234
    },
    {
        "id": 6,
        "userDocument": "12340987",
        "creditCardToken": "1234-5432-2123-5423",
        "value": 1234
    }
  ]
  ```

  
   ### **Obter Purchase por ID**

  - **Endpoint**: `/purchase/id`
  - **Método**: `GET`
  - **Código**: `200 OK`
  - **Descrição**: Busca um purchase por ID.
<br>

  Exemplo de URL com busca por UUID
  ```
  localhost:8080/purchase/1
  ```
  <br>
  Exemplo de JSON Retornado
  
  ```
  {
    "id": 1,
    "userDocument": "321321",
    "creditCardToken": "321321",
    "value": 2600
  }
  ```
<br>

   ### **Criar Purchase**

  - **Endpoint**: `/purchase`
  - **Método**: `POST`
  - **Código**: `201 CREATED`
  - **Descrição**: Cria um novo purchase com os dados enviados <br>
  - URI: Locale /purchase/{id}

  Exemplo de URL para criar o Purchase
  ```
  localhost:8080/purchase
  ```
  <br>
  Exemplo de JSON enviado
  
  ```
  {
  	"userDocument": "321321",
    "creditCardToken": "321321",
    "value": 2600
  }
  ```
  <br>
  Exemplo de JSON Retornado
  
    ```
    {
      "id": 1,
      "userDocument": "321321",
      "creditCardToken": "321321",
      "value": 2600
    }
    ```

  <br>
  
  URI: `localhost:8080/purchase/1`
 
   <br>
   
  ### **Editar Purchase**

  - **Endpoint**: `/purchase/{id}`
  - **Método**: `PUT`
  - **Código**: `200 OK`
  - **Descrição**: Atualiza um Purchase, selecionando o post via ID no URL e enviando um JSON com os campos <br>

  Exemplo de URL com ID
  ```
  localhost:8080/purchase/1
  ```
  <br>
  Exemplo de JSON enviado
  
  ```
  {
	  "userDocument": "12345465",
    "creditCardToken": "1234-5432-2123-5465",
    "value": 1231
  }
  ```
  <br>
  Exemplo de JSON Retornado
  
  ```
  {
    "id": 1,
    "userDocument": "12345465",
    "creditCardToken": "1234-5432-2123-5465",
    "value": 1231
  }
```

  ### **Deletar Purchase**

  - **Endpoint**: `/purchase/{id}`
  - **Método**: `DELETE`
  - **Código**: `200 OK`
  - **Descrição**: Apaga um purchase do Banco de Dados <br>

  Exemplo de URL com ID
  ```
  localhost:8080/purchase/1
  ```

  ## **Padrão de Erros Restful**

  O padrão de retorno da API segue o padrão Restful, retornando os campos: <br>
  - timestamp
  - name
  - code
  - error

  JSON de erro: <br>
  ```
{
    "timestamp": "2024-12-05T18:53:12.631344509Z",
    "name": "NOT_FOUND",
    "code": 404,
    "error": "Purchase Not Found"
}
```
  > Para os padrões de erros, é utilizado um builder para construir um objeto de resposta de erros

## Conferindo os dados Cryptografados

Caso tenha executado o projeto como recomendado com Docker Compose, utilize este comando para conferir os dados criptografados:

Utilizando o postgreSQL (Via CLI)

### Entre no banco de dados

```
psql -h localhost -U postgres
```
Digite a senha do DB: 1234

### Navegue para o DATABASE
```
\c purchases
```

### Verifique os dados
```
SELECT * FROM purchase;
```

### Para sair do banco de dados
```
\q
```
