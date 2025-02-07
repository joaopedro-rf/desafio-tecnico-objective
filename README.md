# Desafio Técnico Objective

Sistema de gestão bancária

## Pré-requisitos

1. **Instalar o Docker**
    - Baixe e instale o Docker Desktop a partir do [site oficial](https://www.docker.com/products/docker-desktop).

2. **Verificar a instalação do Docker Compose**
    - O Docker Compose geralmente já está incluído no Docker Desktop. Verifique se ele está instalado executando:
      ```bash  
      docker-compose --version  
      ```  

3. **Clonar o Repositório do Projeto**
    - Baixe os arquivos do projeto utilizando Git:
      ```bash  
      git clone <URL_DO_REPOSITORIO>  
      cd <PASTA_DO_PROJETO>  
      ```  

---

## Passos para Executar a Aplicação

1. **Construir e Subir os Conteineres**
    - Execute o seguinte comando no diretório raiz do projeto (onde estão localizados o `Dockerfile` e o `docker-compose.yml`):
      ```bash  
      docker-compose up --build  
      ```  

2. **Acessar a Aplicação**
    - Após os contêineres estarem em execução, abra o navegador e acesse:
      ```
      http://localhost:8080  
      ```  

3. **Parar os Contêineres**
    - Para parar a aplicação e remover os contêineres, pressione `CTRL+C` no terminal e execute:
      ```bash  
      docker-compose down  
      ```  

---

## Notas Adicionais

- Certifique-se de que a porta `8080` não está sendo usada antes de iniciar os contêineres.
- Para verificar logs e solucionar problemas, utilize o comando:
  ```bash  
  docker logs <CONTAINER_ID>  

---



# Documentação da API

## Criar uma conta

### Endpoint

```http
POST /conta
```

### Requisição

```json
{
  "numero_conta": 234,
  "saldo": 180.37
}
```

### Resposta

- **HTTP STATUS 201**

```json
{
  "numero_conta": 234,
  "saldo": 180.37
}
```

---

## Realizar uma transação

### Endpoint

```http
POST /transacao
```

### Requisição

```json
{
  "forma_pagamento": "D",
  "numero_conta": 234,
  "valor": 10
}
```

### Respostas

- **HTTP STATUS 201** (Transação realizada com sucesso)

```json
{
  "numero_conta": 234,
  "saldo": 170.07
}
```

- **HTTP STATUS 404** (Caso não tenha saldo disponível)

---

## Consultar conta

### Endpoint

```http
GET /conta?numero_conta=234
```

### Respostas

- **HTTP STATUS 200** (Conta encontrada)

```json
{
  "numero_conta": 234,
  "saldo": 170.07
}
```

- **HTTP STATUS 404** (Caso a conta não exista)

