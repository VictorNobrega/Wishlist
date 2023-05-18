# Wishlist

## Para rodar projeto:

    Para rodar o projeto é necessário possuir
      - JDK 17
      - Maven
    Caso esteja no Mac ou Windows, ter o Docker Desktop

    Na pasta raiz do projeto, executar os comandos:
    - mvn clean install
    - docker build -t wishlist-docker:1.0 .
    - docker-compose up
    
    O Swagger do projeto pode ser acessado no link
    - http://localhost:8080/swagger-ui/index.html

## Objetivo:

Simular uma lista de desejos que receba requisições com a responsabilidade de adicionar, consultar, 
verificar se o produto consta na lista de desejos e remover produtos com regras específicas para a Lista de Desejos.