# Guia de Testes - API Automanager

## Base URL
```
http://localhost:8080
```

## 1. EMPRESAS

### GET /empresas
Lista todas as empresas
```
GET http://localhost:8080/empresas
```

### GET /empresas/{id}
Busca empresa por ID
```
GET http://localhost:8080/empresas/1
```

### POST /empresas
Cria nova empresa
```
POST http://localhost:8080/empresas
Content-Type: application/json

{
  "razaoSocial": "Empresa Teste LTDA",
  "nomeFantasia": "Empresa Teste",
  "cnpj": "12345678000190",
  "cadastro": "2025-11-28T00:00:00"
}
```

### PUT /empresas/{id}
Atualiza empresa
```
PUT http://localhost:8080/empresas/1
Content-Type: application/json

{
  "razaoSocial": "Empresa Atualizada LTDA",
  "nomeFantasia": "Empresa Atualizada",
  "cnpj": "12345678000190",
  "cadastro": "2025-11-28T00:00:00"
}
```

### DELETE /empresas/{id}
Exclui empresa
```
DELETE http://localhost:8080/empresas/1
```

### GET /empresas/{empresaId}/usuarios
Lista usuários da empresa
```
GET http://localhost:8080/empresas/1/usuarios
```

---

## 2. USUARIOS

### GET /usuarios
Lista todos os usuários
```
GET http://localhost:8080/usuarios
```

### GET /usuarios/{id}
Busca usuário por ID
```
GET http://localhost:8080/usuarios/1
```

### POST /usuarios
Cria novo usuário
```
POST http://localhost:8080/usuarios
Content-Type: application/json

{
  "nome": "João Silva",
  "nomeSocial": "João",
  "empresaId": 1
}
```

### PUT /usuarios/{id}
Atualiza usuário
```
PUT http://localhost:8080/usuarios/1
Content-Type: application/json

{
  "nome": "João Silva Atualizado",
  "nomeSocial": "João",
  "empresaId": 1
}
```

### DELETE /usuarios/{id}
Exclui usuário
```
DELETE http://localhost:8080/usuarios/1
```

### GET /usuarios/{usuarioId}/veiculos
Lista veículos do usuário
```
GET http://localhost:8080/usuarios/1/veiculos
```

### GET /usuarios/{id}/vendas
Lista vendas do usuário
```
GET http://localhost:8080/usuarios/1/vendas
```

---

## 3. CREDENCIAIS

### GET /credenciais
Lista todas as credenciais
```
GET http://localhost:8080/credenciais
```

### GET /credenciais/{id}
Busca credencial por ID
```
GET http://localhost:8080/credenciais/1
```

### POST /credenciais
Cria nova credencial
```
POST http://localhost:8080/credenciais
Content-Type: application/json

{
  "criacao": "2025-11-28T00:00:00",
  "ultimoAcesso": "2025-11-28T00:00:00",
  "inativo": false,
  "nomeUsuario": "usuario123",
  "senha": "senha123",
  "usuarioId": 1
}
```

### PUT /credenciais/{id}
Atualiza credencial
```
PUT http://localhost:8080/credenciais/1
Content-Type: application/json

{
  "criacao": "2025-11-28T00:00:00",
  "ultimoAcesso": "2025-11-28T12:00:00",
  "inativo": false,
  "nomeUsuario": "usuario123",
  "senha": "novaSenha123",
  "usuarioId": 1
}
```

### DELETE /credenciais/{id}
Exclui credencial
```
DELETE http://localhost:8080/credenciais/1
```

---

## 4. VEICULOS

### GET /veiculos
Lista todos os veículos
```
GET http://localhost:8080/veiculos
```

### GET /veiculos/{id}
Busca veículo por ID
```
GET http://localhost:8080/veiculos/1
```

### POST /veiculos
Cria novo veículo
```
POST http://localhost:8080/veiculos
Content-Type: application/json

{
  "tipo": "SUV",
  "modelo": "Corolla Cross",
  "placa": "ABC-1234",
  "usuarioId": 1
}
```

### PUT /veiculos/{id}
Atualiza veículo
```
PUT http://localhost:8080/veiculos/1
Content-Type: application/json

{
  "tipo": "SEDA",
  "modelo": "Corolla",
  "placa": "ABC-1234",
  "usuarioId": 1
}
```

### DELETE /veiculos/{id}
Exclui veículo
```
DELETE http://localhost:8080/veiculos/1
```

### GET /veiculos/{id}/vendas
Lista vendas do veículo
```
GET http://localhost:8080/veiculos/1/vendas
```

---

## 5. MERCADORIAS

### GET /mercadorias
Lista todas as mercadorias
```
GET http://localhost:8080/mercadorias
```

### GET /mercadorias/{id}
Busca mercadoria por ID
```
GET http://localhost:8080/mercadorias/1
```

### POST /mercadorias
Cria nova mercadoria
```
POST http://localhost:8080/mercadorias
Content-Type: application/json

{
  "nome": "Produto Exemplo",
  "descricao": "Descrição do produto",
  "preco": 99.90,
  "codigoBarra": "7891234567890"
}
```

### PUT /mercadorias/{id}
Atualiza mercadoria
```
PUT http://localhost:8080/mercadorias/1
Content-Type: application/json

{
  "nome": "Produto Atualizado",
  "descricao": "Nova descrição",
  "preco": 149.90,
  "codigoBarra": "7891234567890"
}
```

### DELETE /mercadorias/{id}
Exclui mercadoria
```
DELETE http://localhost:8080/mercadorias/1
```

---

## 6. SERVICOS

### GET /servicos
Lista todos os serviços
```
GET http://localhost:8080/servicos
```

### GET /servicos/{id}
Busca serviço por ID
```
GET http://localhost:8080/servicos/1
```

### POST /servicos
Cria novo serviço
```
POST http://localhost:8080/servicos
Content-Type: application/json

{
  "nome": "Serviço Exemplo",
  "descricao": "Descrição do serviço",
  "preco": 150.00,
  "codigoServico": "SRV001"
}
```

### PUT /servicos/{id}
Atualiza serviço
```
PUT http://localhost:8080/servicos/1
Content-Type: application/json

{
  "nome": "Serviço Atualizado",
  "descricao": "Nova descrição",
  "preco": 200.00,
  "codigoServico": "SRV001"
}
```

### DELETE /servicos/{id}
Exclui serviço
```
DELETE http://localhost:8080/servicos/1
```

---

## 7. VENDAS

### GET /vendas
Lista todas as vendas
```
GET http://localhost:8080/vendas
```

### GET /vendas/{id}
Busca venda por ID
```
GET http://localhost:8080/vendas/1
```

### POST /vendas
Cria nova venda (com itens)
```
POST http://localhost:8080/vendas
Content-Type: application/json

{
  "clienteId": 1,
  "veiculoId": 1,
  "itensMercadoria": [
    {
      "mercadoriaId": 1,
      "quantidade": 2
    },
    {
      "mercadoriaId": 2,
      "quantidade": 1
    }
  ],
  "itensServico": [
    {
      "servicoId": 1,
      "quantidade": 1
    },
    {
      "servicoId": 2,
      "quantidade": 2
    }
  ]
}
```

### PUT /vendas/{id}
Atualiza venda
```
PUT http://localhost:8080/vendas/1
Content-Type: application/json

{
  "cadastro": "2025-11-28T00:00:00",
  "identificacao": "VENDA001",
  "total": 500.00
}
```

### DELETE /vendas/{id}
Exclui venda
```
DELETE http://localhost:8080/vendas/1
```

---

## TESTES DE ERRO

### Teste 404 - Recurso não encontrado
```
GET http://localhost:8080/empresas/999
```

### Teste 400 - Validação (dados inválidos)
```
POST http://localhost:8080/empresas
Content-Type: application/json

{
  "razaoSocial": "",
  "cadastro": null
}
```

---

## H2 Console

Acesse o console H2 para visualizar os dados:
```
http://localhost:8080/h2-console
```

**Configurações de conexão:**
- JDBC URL: `jdbc:h2:mem:testdb`
- User Name: `sa`
- Password: (deixe em branco)

---

## Ordem Recomendada de Testes

1. **Criar Empresa** → POST /empresas
2. **Criar Usuário** → POST /usuarios (com empresaId)
3. **Criar Credencial** → POST /credenciais (com usuarioId)
4. **Criar Veículo** → POST /veiculos (com usuarioId)
5. **Criar Mercadorias** → POST /mercadorias
6. **Criar Serviços** → POST /servicos
7. **Criar Venda** → POST /vendas (com todos os IDs criados)
8. **Testar endpoints de listagem** → GET /empresas/{id}/usuarios, GET /usuarios/{id}/veiculos, etc.
9. **Testar erros** → 404, 400

