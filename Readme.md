# Executando uma Aplicação Java Spring Boot via Docker

Este guia mostra como você pode executar uma aplicação Java Spring Boot em um contêiner Docker usando o Dockerfile fornecido.

## Pré-requisitos

Antes de prosseguir, certifique-se de que você tenha os seguintes requisitos instalados:

- Docker: Você pode instalar o Docker a partir do [site oficial](https://docs.docker.com/get-docker/).

## Passos para Executar a Aplicação via Docker

Siga as etapas abaixo para executar a aplicação Spring Boot em um contêiner Docker:

1. Clone este repositório:

   	git clone <https://github.com/LeoCunha98/attornatus-pessoas.git>

2. Navegue até o diretório do projeto:

	cd <NOME DO SEU DIRETÓRIO>

3. Construa a imagem Docker:
Substitua <NOME_DA_IMAGEM> pelo nome que deseja dar à imagem Docker.


	docker build -t <NOME_DA_IMAGEM> .

4. Execute a imagem Docker:

	docker run -p 8000:8080 <NOME_DA_IMAGEM>

A aplicação Spring Boot estará disponível em http://localhost:8000. Você pode acessá-la em um navegador ou fazer chamadas à API, dependendo da configuração da sua aplicação. 

Dentro do diretório da aplicação, em resources/collection, existe uma collection com todas requisições disponíveis para chamada.

