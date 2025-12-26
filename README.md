```markdown
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Coverage](https://img.shields.io/badge/coverage-85%25-green)
![Contributors](https://img.shields.io/badge/contributors-1-blue)
```

```markdown
# 🍷 E-Wine API

> Sistema completo de e-commerce de vinhos desenvolvido com Quarkus, PostgreSQL e JWT.

[![Java](https://img.shields.io/badge/Java-25-orange? logo=java)](https://www.oracle.com/java/)
[![Quarkus](https://img.shields.io/badge/Quarkus-3.26.0-blue? logo=quarkus)](https://quarkus.io/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?logo=postgresql)](https://www.postgresql.org/)
[![License](https://img.shields.io/badge/License-MIT-green. svg)](LICENSE)

---

## 📋 **Sobre o Projeto**

E-Wine é uma API REST completa para gerenciamento de e-commerce de vinhos, desenvolvida como projeto acadêmico do curso de **Sistemas de Informação** da **UNITINS**.

### **Funcionalidades Principais:**

- 🛒 **Gestão de Pedidos** - Criação, acompanhamento e cancelamento
- 💳 **Pagamentos** - Suporte a PIX, Boleto e Cartão de Crédito (parcelado)
- 🍷 **Catálogo de Vinhos** - CRUD completo com filtros avançados
- 👥 **Gestão de Usuários** - Clientes e Administradores
- 🔐 **Autenticação JWT** - Segurança com roles (ADMIN/CLIENTE)
- 📊 **Logs Estruturados** - Rotação automática e auditoria
- 📖 **Documentação OpenAPI** - Swagger UI integrado

---

## 🏗️ **Arquitetura**

```
┌─────────────────┐
│   REST Client   │  (Frontend / Postman / Swagger)
└────────┬────────┘
         │
┌────────▼────────┐
│   Resources     │  (Controllers - Endpoints REST)
│  - PedidoResource
│  - PagamentoResource
│  - ProdutoResource
└────────┬────────┘
         │
┌────────▼────────┐
│    Services     │  (Lógica de Negócio)
│  - PedidoService
│  - PagamentoService
│  - ProdutoService
└────────┬────────┘
         │
┌────────▼────────┐
│  Repositories   │  (Panache - Acesso a Dados)
│  - PedidoRepository
│  - ProdutoRepository
└────────┬────────┘
         │
┌────────▼────────┐
│  PostgreSQL DB  │  (Banco de Dados)
└─────────────────┘
```

---

## 🛠️ **Tecnologias Utilizadas**

| Categoria | Tecnologia | Versão |
|-----------|------------|--------|
| **Framework** | Quarkus | 3.26.0 |
| **Linguagem** | Java | 25 (LTS 21 recomendado) |
| **Banco de Dados** | PostgreSQL | 16+ |
| **ORM** | Hibernate Panache | - |
| **Segurança** | SmallRye JWT | - |
| **Validação** | Hibernate Validator | - |
| **Documentação** | OpenAPI (Swagger) | - |
| **Testes** | JUnit 5 + RestAssured | - |
| **Build** | Maven | 3.9+ |
| **Criptografia** | BCrypt (jBCrypt) | 0.4 |

---

## 🚀 **Instalação e Configuração**

### **Pré-requisitos:**

- ☕ **Java 21+** (recomendado:  Java 21 LTS)
- 📦 **Maven 3.9+**
- 🐘 **PostgreSQL 16+**
- 🔧 **Git**

### **1️⃣ Clonar o Repositório:**

```bash
git clone https://github.com/devfellsp/ewine-main. git
cd ewine-main
```

### **2️⃣ Configurar Banco de Dados:**

Criar banco no PostgreSQL:

```sql
CREATE DATABASE ewine_db;
CREATE USER ewine_user WITH PASSWORD 'sua_senha';
GRANT ALL PRIVILEGES ON DATABASE ewine_db TO ewine_user;
```

### **3️⃣ Configurar `application.properties`:**

Editar `src/main/resources/application.properties`:

```properties
# Database
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=ewine_user
quarkus.datasource.password=sua_senha
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/ewine_db

# Hibernate
quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.log.sql=false

# JWT
mp.jwt.verify.publickey. location=publicKey.pem
mp.jwt. verify.issuer=ewine-jwt
smallrye.jwt.sign.key. location=privateKey.pem

# Server
quarkus.http.port=8080
```

### **4️⃣ Gerar Chaves JWT:**

```bash
# Gerar chave privada
openssl genrsa -out src/main/resources/privateKey.pem 2048

# Gerar chave pública
openssl rsa -in src/main/resources/privateKey.pem -pubout -out src/main/resources/publicKey.pem
```

### **5️⃣ Executar o Projeto:**

```bash
# Modo desenvolvimento (hot reload)
mvn quarkus:dev

# Ou compilar e executar
mvn clean package
java -jar target/quarkus-app/quarkus-run.jar
```

### **6️⃣ Acessar Swagger UI:**

Abrir no navegador: 
```
http://localhost:8080/q/swagger-ui
```

---

## 📖 **Documentação da API**

### **Autenticação:**

#### **Login:**
```http
POST /auth
Content-Type: application/json

{
  "login": "admin",
  "senha": "123"
}
```

**Response:**
```
eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9... 
```

### **Endpoints Principais:**

#### **🍷 Produtos (Público):**

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `GET` | `/produtos` | Listar todos os vinhos |
| `GET` | `/produtos/{id}` | Buscar vinho por ID |
| `GET` | `/produtos/search? nome=X` | Buscar por nome |

#### **🍷 Produtos (Admin):**

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/produtos/vinhos` | Criar vinho | 🔒 ADMIN |
| `PUT` | `/produtos/{id}/vinhos` | Atualizar vinho | 🔒 ADMIN |
| `PATCH` | `/produtos/{id}/estoque` | Alterar estoque | 🔒 ADMIN |
| `PATCH` | `/produtos/{id}/preco` | Alterar preço | 🔒 ADMIN |

#### **🛒 Pedidos:**

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/pedidos` | Criar pedido | 🔒 CLIENTE |
| `GET` | `/pedidos/meus-pedidos` | Listar meus pedidos | 🔒 CLIENTE |
| `GET` | `/pedidos` | Listar todos | 🔒 ADMIN |
| `PATCH` | `/pedidos/{id}/enviar` | Marcar como enviado | 🔒 ADMIN |
| `PATCH` | `/pedidos/{id}/cancelar` | Cancelar pedido | 🔒 ADMIN |

#### **💳 Pagamentos:**

| Método | Endpoint | Descrição | Autenticação |
|--------|----------|-----------|--------------|
| `POST` | `/pagamentos/{pedidoId}/pix` | Pagar com PIX | 🔒 CLIENTE |
| `POST` | `/pagamentos/{pedidoId}/boleto` | Pagar com Boleto | 🔒 CLIENTE |
| `POST` | `/pagamentos/{pedidoId}/cartao` | Pagar com Cartão | 🔒 CLIENTE |

---

## 🧪 **Executar Testes**

```bash
# Todos os testes
mvn test

# Teste específico
mvn test -Dtest=ProdutoResourceTest

# Com cobertura
mvn clean test jacoco:report
```

---

## 📊 **Modelo de Dados**

### **Entidades Principais:**

```
Pedido
├── Cliente
├── Endereco
├── ItemPedido[]
│   └── Produto (Vinho)
└── Pagamento
    └── FormaPagamento

Vinho (extends Produto)
├── Pais
├── TipoVinho
├── Marca
├── Safra
├── Uva[]
├── Estilo
└── Ocasiao

Usuario
├── Perfil (ADMIN/CLIENTE)
└── Cliente
    └── Endereco[]
```

---

## 🔐 **Segurança**

- ✅ **JWT** com chaves RSA 2048 bits
- ✅ **BCrypt** para hash de senhas
- ✅ **Roles** (`ADMIN`, `CLIENTE`)
- ✅ **Endpoints públicos** apenas para consulta
- ✅ **Validações** em todos os inputs

---

## 📁 **Estrutura do Projeto**

```
ewine-main/
├── src/
│   ├── main/
│   │   ├── java/br/unitins/topicos1/ewine/
│   │   │   ├── model/              # Entidades JPA
│   │   │   │   ├── pedido/
│   │   │   │   ├── produto/
│   │   │   │   └── usuario/
│   │   │   ├── repository/         # Repositórios Panache
│   │   │   ├── service/            # Lógica de negócio
│   │   │   │   └── impl/
│   │   │   ├── resource/           # Endpoints REST
│   │   │   │   ├── pedido/
│   │   │   │   ├── pagamento/
│   │   │   │   └── produto/
│   │   │   └── dto/                # Input/Response DTOs
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── privateKey.pem
│   │       └── publicKey.pem
│   └── test/                       # Testes JUnit
├── logs/                           # Logs rotativos (gerado)
├── . mvn/
│   └── jvm.config                  # Opções JVM (Java 25)
├── pom.xml
└── README.md
```

---

## 🐛 **Problemas Conhecidos e Soluções**

### **⚠️ Warning:  `sun.misc.Unsafe` (Java 25)**

**Causa:** Netty ainda não suporta totalmente Java 25.

**Solução:** Já configurado em `.mvn/jvm.config` e `pom.xml`.

### **🔒 Erro 401 Unauthorized**

**Causa:** Token JWT expirado ou inválido. 

**Solução:** Fazer novo login em `/auth`.

---

## 📝 **Roadmap**

- [ ] Implementar webhook de confirmação de pagamento
- [ ] Upload de imagens de produtos (AWS S3)
- [ ] Sistema de avaliações e comentários
- [ ] Filtros avançados (preço, safra, região)
- [ ] Relatórios de vendas (PDF/Excel)
- [ ] Notificações por email (pedido confirmado/enviado)
- [ ] Integração com APIs de frete (Correios/Transportadoras)

---

## 👥 **Contribuindo**

1. Fork o projeto
2. Crie uma branch:  `git checkout -b feature/nova-funcionalidade`
3. Commit: `git commit -m 'feat: adicionar nova funcionalidade'`
4. Push: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

---

## 📄 **Licença**

Este projeto está sob a licença MIT.  Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 **Autor**

**Felipe Silva** ([@devfellsp](https://github.com/devfellsp))

- 📧 Email: [seu-email@exemplo.com]
- 💼 LinkedIn: [seu-linkedin]

---

## 🙏 **Agradecimentos**

- **UNITINS** - Universidade Estadual do Tocantins
- **Professor Orientador** - [Nome do Professor]
- **Quarkus Community** - Documentação e suporte

---

## 📚 **Referências**

- [Quarkus Documentation](https://quarkus.io/guides/)
- [Hibernate ORM](https://hibernate.org/orm/documentation/)
- [SmallRye JWT](https://smallrye.io/docs/smallrye-jwt/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

<div align="center">

**⭐ Se este projeto te ajudou, deixe uma estrela!  ⭐**

</div>
```






