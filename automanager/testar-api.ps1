# Script de Testes para API Automanager
# Execute: .\testar-api.ps1
# Certifique-se de que a aplicação está rodando em http://localhost:8080

$baseUrl = "http://localhost:8080"
$headers = @{
    "Content-Type" = "application/json"
}

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TESTES DA API AUTOMANAGER" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Função para fazer requisições
function Invoke-ApiRequest {
    param(
        [string]$Method,
        [string]$Endpoint,
        [object]$Body = $null
    )
    
    $uri = "$baseUrl$Endpoint"
    Write-Host "[$Method] $uri" -ForegroundColor Yellow
    
    try {
        if ($Body) {
            $jsonBody = $Body | ConvertTo-Json -Depth 10
            $response = Invoke-WebRequest -Uri $uri -Method $Method -Headers $headers -Body $jsonBody -UseBasicParsing
        } else {
            $response = Invoke-WebRequest -Uri $uri -Method $Method -Headers $headers -UseBasicParsing
        }
        
        Write-Host "Status: $($response.StatusCode)" -ForegroundColor Green
        Write-Host "Response:" -ForegroundColor Green
        $response.Content | ConvertFrom-Json | ConvertTo-Json -Depth 10 | Write-Host
        Write-Host ""
        return $response
    } catch {
        Write-Host "Erro: $($_.Exception.Message)" -ForegroundColor Red
        if ($_.Exception.Response) {
            $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
            $responseBody = $reader.ReadToEnd()
            Write-Host "Response: $responseBody" -ForegroundColor Red
        }
        Write-Host ""
    }
}

# Verificar se a aplicação está rodando
Write-Host "Verificando se a aplicação está rodando..." -ForegroundColor Yellow
try {
    $testResponse = Invoke-WebRequest -Uri "$baseUrl/empresas" -Method "GET" -UseBasicParsing -ErrorAction Stop
    Write-Host "Aplicação está rodando!" -ForegroundColor Green
    Write-Host ""
} catch {
    Write-Host "ERRO: A aplicação não está rodando em $baseUrl" -ForegroundColor Red
    Write-Host "Por favor, inicie a aplicação primeiro com: .\mvnw.cmd spring-boot:run" -ForegroundColor Yellow
    Write-Host ""
    exit
}

# 1. TESTE EMPRESAS
Write-Host "=== 1. TESTANDO EMPRESAS ===" -ForegroundColor Magenta
Write-Host ""

# Criar empresa
$empresa = @{
    razaoSocial = "Empresa Teste LTDA"
    nomeFantasia = "Empresa Teste"
    cnpj = "12345678000190"
    cadastro = "2025-11-28T00:00:00"
}
$empresaResponse = Invoke-ApiRequest -Method "POST" -Endpoint "/empresas" -Body $empresa
$empresaId = if ($empresaResponse) { ($empresaResponse.Content | ConvertFrom-Json).id } else { 1 }

# Listar empresas
Invoke-ApiRequest -Method "GET" -Endpoint "/empresas"

# Buscar empresa por ID
Invoke-ApiRequest -Method "GET" -Endpoint "/empresas/$empresaId"

# 2. TESTE USUARIOS
Write-Host "=== 2. TESTANDO USUARIOS ===" -ForegroundColor Magenta
Write-Host ""

# Criar usuário
$usuario = @{
    nome = "João Silva"
    nomeSocial = "João"
    empresaId = $empresaId
}
$usuarioResponse = Invoke-ApiRequest -Method "POST" -Endpoint "/usuarios" -Body $usuario
$usuarioId = if ($usuarioResponse) { ($usuarioResponse.Content | ConvertFrom-Json).id } else { 1 }

# Listar usuários
Invoke-ApiRequest -Method "GET" -Endpoint "/usuarios"

# Buscar usuário por ID
Invoke-ApiRequest -Method "GET" -Endpoint "/usuarios/$usuarioId"

# Listar usuários da empresa
Invoke-ApiRequest -Method "GET" -Endpoint "/empresas/$empresaId/usuarios"

# 3. TESTE CREDENCIAIS
Write-Host "=== 3. TESTANDO CREDENCIAIS ===" -ForegroundColor Magenta
Write-Host ""

# Criar credencial
$credencial = @{
    criacao = "2025-11-28T00:00:00"
    ultimoAcesso = "2025-11-28T00:00:00"
    inativo = $false
    nomeUsuario = "usuario123"
    senha = "senha123"
    usuarioId = $usuarioId
}
$credencialResponse = Invoke-ApiRequest -Method "POST" -Endpoint "/credenciais" -Body $credencial
$credencialId = if ($credencialResponse) { ($credencialResponse.Content | ConvertFrom-Json).id } else { 1 }

# Listar credenciais
Invoke-ApiRequest -Method "GET" -Endpoint "/credenciais"

# 4. TESTE VEICULOS
Write-Host "=== 4. TESTANDO VEICULOS ===" -ForegroundColor Magenta
Write-Host ""

# Criar veículo
$veiculo = @{
    tipo = "SUV"
    modelo = "Corolla Cross"
    placa = "ABC-1234"
    usuarioId = $usuarioId
}
$veiculoResponse = Invoke-ApiRequest -Method "POST" -Endpoint "/veiculos" -Body $veiculo
$veiculoId = if ($veiculoResponse) { ($veiculoResponse.Content | ConvertFrom-Json).id } else { 1 }

# Listar veículos
Invoke-ApiRequest -Method "GET" -Endpoint "/veiculos"

# Listar veículos do usuário
Invoke-ApiRequest -Method "GET" -Endpoint "/usuarios/$usuarioId/veiculos"

# 5. TESTE MERCADORIAS
Write-Host "=== 5. TESTANDO MERCADORIAS ===" -ForegroundColor Magenta
Write-Host ""

# Criar mercadoria 1
$mercadoria1 = @{
    nome = "Produto Exemplo 1"
    descricao = "Descrição do produto 1"
    preco = 99.90
    codigoBarra = "7891234567890"
}
$mercadoria1Response = Invoke-ApiRequest -Method "POST" -Endpoint "/mercadorias" -Body $mercadoria1
$mercadoria1Id = if ($mercadoria1Response) { ($mercadoria1Response.Content | ConvertFrom-Json).id } else { 1 }

# Criar mercadoria 2
$mercadoria2 = @{
    nome = "Produto Exemplo 2"
    descricao = "Descrição do produto 2"
    preco = 149.90
    codigoBarra = "7891234567891"
}
$mercadoria2Response = Invoke-ApiRequest -Method "POST" -Endpoint "/mercadorias" -Body $mercadoria2
$mercadoria2Id = if ($mercadoria2Response) { ($mercadoria2Response.Content | ConvertFrom-Json).id } else { 2 }

# Listar mercadorias
Invoke-ApiRequest -Method "GET" -Endpoint "/mercadorias"

# 6. TESTE SERVICOS
Write-Host "=== 6. TESTANDO SERVICOS ===" -ForegroundColor Magenta
Write-Host ""

# Criar serviço 1
$servico1 = @{
    nome = "Serviço Exemplo 1"
    descricao = "Descrição do serviço 1"
    preco = 150.00
    codigoServico = "SRV001"
}
$servico1Response = Invoke-ApiRequest -Method "POST" -Endpoint "/servicos" -Body $servico1
$servico1Id = if ($servico1Response) { ($servico1Response.Content | ConvertFrom-Json).id } else { 1 }

# Criar serviço 2
$servico2 = @{
    nome = "Serviço Exemplo 2"
    descricao = "Descrição do serviço 2"
    preco = 200.00
    codigoServico = "SRV002"
}
$servico2Response = Invoke-ApiRequest -Method "POST" -Endpoint "/servicos" -Body $servico2
$servico2Id = if ($servico2Response) { ($servico2Response.Content | ConvertFrom-Json).id } else { 2 }

# Listar serviços
Invoke-ApiRequest -Method "GET" -Endpoint "/servicos"

# 7. TESTE VENDAS
Write-Host "=== 7. TESTANDO VENDAS ===" -ForegroundColor Magenta
Write-Host ""

# Criar venda
$venda = @{
    clienteId = $usuarioId
    veiculoId = $veiculoId
    itensMercadoria = @(
        @{
            mercadoriaId = $mercadoria1Id
            quantidade = 2
        },
        @{
            mercadoriaId = $mercadoria2Id
            quantidade = 1
        }
    )
    itensServico = @(
        @{
            servicoId = $servico1Id
            quantidade = 1
        },
        @{
            servicoId = $servico2Id
            quantidade = 2
        }
    )
}
$vendaResponse = Invoke-ApiRequest -Method "POST" -Endpoint "/vendas" -Body $venda
$vendaId = if ($vendaResponse) { ($vendaResponse.Content | ConvertFrom-Json).id } else { 1 }

# Listar vendas
Invoke-ApiRequest -Method "GET" -Endpoint "/vendas"

# Listar vendas do usuário
Invoke-ApiRequest -Method "GET" -Endpoint "/usuarios/$usuarioId/vendas"

# Listar vendas do veículo
Invoke-ApiRequest -Method "GET" -Endpoint "/veiculos/$veiculoId/vendas"

# 8. TESTE DE ERROS
Write-Host "=== 8. TESTANDO TRATAMENTO DE ERROS ===" -ForegroundColor Magenta
Write-Host ""

# Teste 404
Write-Host "Testando 404 (Recurso não encontrado):" -ForegroundColor Yellow
Invoke-ApiRequest -Method "GET" -Endpoint "/empresas/999"

# Teste 400 (validação)
Write-Host "Testando 400 (Validação):" -ForegroundColor Yellow
$empresaInvalida = @{
    razaoSocial = ""
    cadastro = $null
}
Invoke-ApiRequest -Method "POST" -Endpoint "/empresas" -Body $empresaInvalida

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "TESTES CONCLUIDOS!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Acesse o H2 Console em: http://localhost:8080/h2-console" -ForegroundColor Green
Write-Host "JDBC URL: jdbc:h2:mem:testdb" -ForegroundColor Green
Write-Host "User: sa" -ForegroundColor Green
Write-Host "Password: (deixe em branco)" -ForegroundColor Green

