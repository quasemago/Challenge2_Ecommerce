# Challenge 2 - Compass.UOL

## API de Gerenciamento de Produtos em E-commerce

**1. Introdução:**

A API REST de gerenciamento de produtos em E-commerce é um sistema desenvolvido em linguagem Java 17 usando o framework Spring Boot 3. Projetada para facilitar operações relacionadas a produtos e pedidos em uma aplicação de comércio eletrônico, a API oferece funcionalidades básicas de criação, leitura, atualização e exclusão (CRUD).

**2. Entidade: Produto (`Product`):**

A entidade "Produto" é representado por um modelo Java denominado `Product`. Cada produto possui atributos essenciais como nome, preço, e descrição, contendo cada um configurações específicas obedecendo as regras de negócio.

**3. Como Usar a API:**

- **Endpoints:**

  - A API disponibiliza endpoints RESTful para interação. Os principais são:

    - `GET /products`: Retorna a lista de todos os produtos.
    - `GET /products/{id}`: Retorna os detalhes de um produto específico.
    - `POST /products`: Cria um novo produto.
    - `PUT /products/{id}`: Atualiza os detalhes de um produto existente.
    - `DELETE /products/{id}`: Deleta um produto existente.
