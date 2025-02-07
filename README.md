
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

