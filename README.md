# Implementação CRUD Completo - Equipamentos

## 📋 Visão Geral

Este documento descreve a implementação completa de operações CRUD (Create, Read, Update, Delete) para a entidade **Equipamento**, seguindo os princípios de **Arquitetura Orientada a Serviços** e **REST API Best Practices**.

## 🏗️ Arquitetura Implementada

### Camadas da Aplicação

```
┌─────────────────────────────────────────┐
│         Cliente (Insomnia/Browser)       │
└─────────────────┬───────────────────────┘
                  │ HTTP Request/Response
┌─────────────────▼───────────────────────┐
│     Controller Layer (REST Endpoints)    │
│  - EquipamentoController                 │
│  - Validação de entrada                  │
│  - Documentação Swagger                  │
└─────────────────┬───────────────────────┘
                  │ DTOs
┌─────────────────▼───────────────────────┐
│         Service Layer (Lógica)           │
│  - EquipamentoService                    │
│  - Regras de negócio                     │
│  - Conversão Entity ↔ DTO                │
└─────────────────┬───────────────────────┘
                  │ Entities
┌─────────────────▼───────────────────────┐
│    Repository Layer (Persistência)       │
│  - EquipamentoRepository (JPA)           │
│  - Queries customizadas                  │
└─────────────────┬───────────────────────┘
                  │ SQL
┌─────────────────▼───────────────────────┐
│       Database (H2 In-Memory)            │
│  - Tabela: equipamento                   │
│  - Dados iniciais: data.sql              │
└─────────────────────────────────────────┘
```

## 📁 Estrutura de Arquivos Criados/Modificados

### Novos Arquivos

```
src/main/java/com/fiap/reserva_equipamentos/api/
├── exception/
│   ├── ResourceNotFoundException.java      
│   └── GlobalExceptionHandler.java         
├── dto/
│   ├── EquipamentoRequestDTO.java          
│   └── EquipamentoResponseDTO.java         

src/main/resources/
└── data.sql                                 
```

### Arquivos Modificados

```
src/main/java/com/fiap/reserva_equipamentos/api/
├── controller/
│   └── EquipamentoController.java          
└── service/
    └── EquipamentoService.java            
```

## 🔌 Endpoints REST Implementados

### 1. **CREATE** - Criar Equipamento
```http
POST /api/equipamentos
Content-Type: application/json

{
  "descricao": "Datashow Sony VPL-EX295",
  "ativo": true
}
```
**Resposta:** `201 Created`

---

### 2. **READ** - Buscar por ID
```http
GET /api/equipamentos/{id}
```
**Resposta:** `200 OK` ou `404 Not Found`

---

### 3. **READ** - Listar Todos
```http
GET /api/equipamentos
```
**Resposta:** `200 OK`

---

### 4. **READ** - Listar Ativos
```http
GET /api/equipamentos/ativos
```
**Resposta:** `200 OK`

---

### 5. **READ** - Buscar por Termo
```http
GET /api/equipamentos?q=datashow
```
**Resposta:** `200 OK`

---

### 6. **UPDATE** - Atualizar Equipamento
```http
PUT /api/equipamentos/{id}
Content-Type: application/json

{
  "descricao": "Datashow Epson PowerLite X41+ (Atualizado)",
  "ativo": true
}
```
**Resposta:** `200 OK` ou `404 Not Found`

---

### 7. **DELETE** - Deletar Logicamente
```http
DELETE /api/equipamentos/{id}
```
**Resposta:** `204 No Content` ou `404 Not Found`

> ⚠️ **Nota:** A operação DELETE é **lógica**, apenas marca o equipamento como `ativo=false`.

## 📊 Códigos de Status HTTP

| Código | Significado | Quando Ocorre |
|--------|-------------|---------------|
| 200 OK | Sucesso | GET, PUT bem-sucedidos |
| 201 Created | Recurso criado | POST bem-sucedido |
| 204 No Content | Sucesso sem corpo | DELETE bem-sucedido |
| 400 Bad Request | Dados inválidos | Validação falhou |
| 404 Not Found | Recurso não existe | ID não encontrado |
| 500 Internal Server Error | Erro do servidor | Exceção não tratada |

## 🛡️ Validações Implementadas

### EquipamentoRequestDTO

```java
@NotBlank(message = "Descrição é obrigatória")
@Size(min = 3, max = 255, message = "Descrição deve ter entre 3 e 255 caracteres")
private String descricao;

private Boolean ativo = true; // Valor padrão
```

### Tratamento de Erros

**Exemplo de resposta de validação (400):**
```json
{
  "status": 400,
  "errors": {
    "descricao": "Descrição é obrigatória"
  },
  "timestamp": "2026-04-15T00:56:00"
}
```

**Exemplo de recurso não encontrado (404):**
```json
{
  "status": 404,
  "message": "Equipamento não encontrado com id: 999",
  "timestamp": "2026-04-15T00:56:00"
}
```

## 🗄️ Banco de Dados H2

### Configuração (application-dev.properties)

```properties
spring.datasource.url=jdbc:h2:mem:reserva
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
```

### Dados Iniciais (data.sql)

O arquivo `data.sql` popula o banco com **15 equipamentos** de exemplo:
- 13 equipamentos ativos
- 2 equipamentos inativos

**Exemplos:**
- Datashow Epson PowerLite X41+
- Notebook Dell Inspiron 15
- Projetor BenQ MH535A
- Microfone sem fio Shure SM58
- E mais...

## 📚 Documentação Swagger

Acesse a documentação interativa da API:

```
http://localhost:8080/swagger-ui/index.html
```

### Recursos do Swagger:
- ✅ Descrição de todos os endpoints
- ✅ Exemplos de requisições e respostas
- ✅ Teste interativo dos endpoints
- ✅ Schemas dos DTOs
- ✅ Códigos de status documentados

## 🧪 Testando com Insomnia

1. **Importe a coleção:** `Insomnia_2026-04-13.json`
2. **Configure o ambiente:** Professor (username: professor, password: 123456)
3. **Execute as requisições:**
   - Listar equipamentos ativos
   - Buscar por termo
   - Criar novo equipamento
   - Buscar por ID
   - Atualizar equipamento
   - Deletar equipamento

## 🔐 Segurança

A API utiliza **Basic Authentication**:
- Username: `professor`
- Password: `123456`

Configurado em: `SecurityConfig.java`

## 🚀 Como Executar

### 1. Compilar o projeto
```bash
mvn clean install
```

### 2. Executar a aplicação
```bash
mvn spring-boot:run
```

### 3. Acessar a API
```
http://localhost:8080/api/equipamentos
```

### 4. Acessar o Swagger
```
http://localhost:8080/swagger-ui/index.html
```

## 📝 Conceitos REST Aplicados

### ✅ Princípios Implementados

1. **Recursos baseados em URLs**
   - `/api/equipamentos` representa a coleção
   - `/api/equipamentos/{id}` representa um recurso específico

2. **Verbos HTTP apropriados**
   - GET para leitura
   - POST para criação
   - PUT para atualização completa
   - DELETE para remoção

3. **Códigos de status HTTP corretos**
   - 2xx para sucesso
   - 4xx para erros do cliente
   - 5xx para erros do servidor

4. **Representação JSON**
   - Content-Type: application/json
   - Accept: application/json

5. **Stateless**
   - Cada requisição contém todas as informações necessárias
   - Autenticação via Basic Auth em cada request

6. **Separação de Responsabilidades**
   - DTOs para entrada/saída
   - Entities para persistência
   - Services para lógica de negócio

7. **Tratamento de Erros Padronizado**
   - GlobalExceptionHandler com @ControllerAdvice
   - Respostas de erro consistentes

