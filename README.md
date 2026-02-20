# 🏬 Loja Java

Projeto em Java desenvolvido para gerenciar produtos de uma loja, com persistência em banco de dados MySQL, validações automáticas e testes unitários.  
O sistema segue boas práticas de programação, incluindo o padrão DAO, princípios SOLID e arquitetura modular.

---

# 🛠 Tecnologias Utilizadas
# Backend
Java 11+: Linguagem principal

Spring Boot 2.7: Framework de aplicação

Spring MVC: Camada web

Spring Data JDBC: Persistência de dados

MySQL 8.0: Banco de dados relacional

Lombok: Redução de código boilerplate

Maven: Gerenciamento de dependências

# Frontend
Thymeleaf: Template engine

Bootstrap 5: Framework CSS responsivo

HTML5/CSS3: Estrutura e estilização

# Testes
JUnit 5: Framework de testes unitários

Mockito: Mocking de dependências

JaCoCo: Análise de cobertura de código

Selenium WebDriver: Testes de interface automatizados

WebDriverManager: Gerenciamento de drivers de navegador

Fuzz Testing: Testes de segurança com entradas maliciosas

---

## 📁 Estrutura do Projeto
```
📦 lojaJava [crud-java-mysql]
├── 📁 .idea
├── 📁 .mvn
├── 📁 src
│   ├── 📁 main
│   │   ├── 📁 java
│   │   │   └── 📁 br.com.lojaJava
│   │   │       ├── 📁 connection
│   │   │       │   └── 📄 ConnectionFactory.java          
│   │   │       ├── 📁 controller
│   │   │       │   └── 📄 ProdutoController.java
│   │   │       ├── 📁 dao
│   │   │       │   ├── 📄 ProdutoDAO.java
│   │   │       │   └── 📄 ProdutoDAO_antigo.txt
│   │   │       ├── 📁 exception
│   │   │       │   ├── 📄 PersistenceException.java
│   │   │       │   └── 📄 ValidationException.java
│   │   │       ├── 📁 model
│   │   │       │   └── 📄 Produto.java
│   │   │       ├── 📁 validacoes
│   │   │       │   ├── 📄 IValidacaoProduto.java
│   │   │       │   ├── 📄 IValidacaoProdutoDelete.java
│   │   │       │   ├── 📄 ValidacaoNomeProduto.java
│   │   │       │   ├── 📄 ValidacaoPrecoProduto.java
│   │   │       │   ├── 📄 ValidacaoQuantidadeProduto.java
│   │   │       │   └── 📄 ValidarIdDeleteProduto.java
│   │   │       └── 📄 LojaJavaApplication.java
│   │   │
│   │   └── 📁 resources
│   │       ├── 📁 templates
│   │       │   ├── 📄 form-produto.html
│   │       │   └── 📄 lista-produtos.html
│   │       └── 📄 db.properties
│   │
│   └── 📁 test
│       └── 📁 java
│           └── 📁 br.com.lojaJava
│               ├── 📁 controller
│               │   ├── 📄 ProdutoControllerTest.java
│               │   └── 📄 ProdutoWebTest.java
│               ├── 📄 connectionTest.java
│               ├── 📄 exceptionTest.java
│               ├── 📄 ProdutoDAOTest.java
│               ├── 📄 ProdutoTest.java
│               ├── 📄 ValidacaoNomeProdutoTest.java
│               ├── 📄 ValidacaoPrecoParametrizadoTest.java
│               ├── 📄 ValidacaoPrecoProdutoTest.java
│               ├── 📄 ValidacaoProdutoNuloTest.java
│               └── 📄 ValidacaoQuantidadeProdutoTest.java
│
├── 📄 pom.xml
└── 📄 README.md
```
---

## ⚙️ Tecnologias Utilizadas

| Tecnologia            | Descrição |
|-----------------------|------------|
| ☕ Java 21            | Linguagem principal |
| 🧱 JDBC               | Conexão com MySQL |
| 🐬 MySQL              | Banco de dados relacional |
| 🧩 JUnit 5            | Testes unitários |
| 🧰 Lombok             | Geração automática de getters/setters |
| 🧼 SOLID + Clean Code | Boas práticas de desenvolvimento |

---

## 🧠 Conceitos Aplicados

- Padrão DAO (Data Access Object)  
  Camada de persistência desacoplada da lógica de negócio.

- Princípios SOLID  
  Cada classe tem uma responsabilidade única e bem definida.

- Validações desacopladas  
  Novas regras podem ser adicionadas sem alterar o código existente.

- Tratamento de exceções customizado  
  Uso de PersistenceException e ValidationException para maior clareza.

- Testes automatizados  
  Cobrem as operações de CRUD e todas as validações.

---

## 🧩 Modelo de Dados

**Tabela `produto`**

| Campo       | Tipo           | Nulo | Descrição |
|--------------|----------------|------|------------|
| id           | INT (PK, AI)   | NÃO  | Identificador único do produto |
| nome         | VARCHAR(255)   | NÃO  | Nome do produto |
| descricao    | TEXT           | SIM  | Descrição opcional do produto |
| preco        | DECIMAL(10,2)  | NÃO  | Preço do produto |
| quantidade   | INT            | NÃO  | Quantidade disponível em estoque |

Exemplo de criação da tabela:

```sql
CREATE DATABASE loja_java;

USE loja_java;

CREATE TABLE produto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  descricao TEXT,
  preco DECIMAL(10,2) NOT NULL,
  quantidade INT NOT NULL
);
```
---
# Configure as propriedades
Crie/edite o arquivo src/main/resources/db.properties:
```
db.url=jdbc:mysql:jdbc:mysql://localhost:3306/loja_java
db.user=loja_user
db.password=senha123
```
---
# Acessando a aplicação
```
🌐 http://localhost:8080/produtos
```
# Executar todos os testes
```
mvn clean test
```
# Gerar relatório de cobertura
```
mvn clean test jacoco:report
```