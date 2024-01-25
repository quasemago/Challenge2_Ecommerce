# Challenge 2 - E-commerce (CompassUOL)
O projeto consiste no desenvolvimento de uma API REST para um e-commerce, utilizando as tecnologias e conhecimentos aprendidos até o momento durante essa jornada do programa de bolsas de estágio da Compass UOL | Back-end Journey (Spring Boot) - AWS Cloud Context.

O projeto foi desenvolvimento utilizando o Java JDK na versão 17 e separado em dois domínios: **Produto** e **Pedido**.
Cada domínio possui 5 (cinco) endpoints, e foram todos documentados utilizando o Swagger.

Para testes das implementações, foi utilizado inicialmente o aplicativo Postman, cobrindo assim os testes de requisições.
Ademais, posteriormente para testes de únidades e integração, foi utilizado o JUnit 5 em conjunto com o Mockito.

## Regras de negócio gerais
Apesar do projeto ter sido separado em dois domínios, ambos possuem regras de negócio em comum, sendo elas:
- Todos os campos data, devem seguir o padrão ISO 8601 (exemplo: 2023-07-20T12:00:00Z ).
- Todos os campos data, devem ser definidas automaticamente.
- As funcionalidades pedido e produto podem conter: data de cadastro ( created_date ), data de atualização ( update_date ) e data de cancelamento ( cancel_date ).
- A documentação da API ViaCEP pode ser encontrada no endereço https://viacep.com.br/

## Tecnologias utilizadas
- Java JDK 17
- Spring Boot 3
- Spring Boot Test (inclui o JUnit 5 e Mockito)
- Spring Web e Spring Web Flux
- Spring Data JPA
- Spring Validation
- Spring DevTools
- Spring Doc OpenAPI (Swagger)
- Spring Cloud OpenFeign
- ModelMapper
- Lombok
- Banco de dados H2 (utilizando apenas na camada de testes)
- Banco de dados MySQL
---
# Produto
O domínio **Produto** consiste em uma API REST que permite que os usuários criem, leiam, atualizem e excluam produtos.

Ademais, o domínio possui as seguintes regras de negócio:
- O nome do produto deve ser único;
- A descrição do produto deve ter no mínimo 10 caracteres;
- O valor do produto deve ser um número positivo.

## Estrutura do banco de dados
O domínio possui a seguinte estrutura de banco de dados:

![image](database_products_scheme.png)

**Observação Importante:** Os requisitos do desáfio especificavam inicialmente que a coluna que armazenaria o valor do produto deveria ser nomeada de `value`, porém, devido a conflitos com o banco de dados H2 pois `value` é uma palavra-chave reservada do mesmo, a coluna foi renomeada para `price`.

## Endpoints
A API disponibiliza endpoints REST para interação. Os principais são:
- `GET /products`: Recupera uma lista de todos os produtos cadastrados.
- `GET /products/:id`: Recupera as informações de um produto específico.
- `POST /products`: Cria um novo produto.
- `PUT /products/:id`: Atualiza as informações de um produto existente.
- `DELETE /products/:id`: Deleta um produto existente.

**DTOs (Objetos de Transferência de Dados):**
Para interações com a API, utilize objetos de transferência de dados (DTOs).
- `ProductCreateDto`: Usado para criar novos produtos, fornecendo nome, descrição e preço.
- `ProductResponseDto`: Representa a resposta contendo as informações de um produto para leitura.

**Payloads:**

- Para criar um novo produto:

```json
POST /products
{
  "name": "Nome do Produto",
  "description": "Descrição do Produto",
  "value": 29.99
}
```

- Para obter detalhes de um produto:

```json
GET /products/:id
 ```

- Para atualizar um produto existente:

```json
PUT /products/:id
{
"name": "Novo Nome",
"description": "Nova Descrição",
"value": 39.99
}
```

- Para deletar um produto:
```json
DELETE /products/:id
 ```
---
# Como executar o projeto
TODO

---
# Conclusão
TODO