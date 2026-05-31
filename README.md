# 💰 Controle Financeiro API

API REST para controle financeiro pessoal desenvolvida como teste técnico Backend Java.

## 📋 Sobre o Projeto

O sistema permite o gerenciamento de:

* Contas financeiras
* Categorias de receitas e despesas
* Transações financeiras
* Controle de saldo
* Resumo financeiro por período

O projeto foi desenvolvido seguindo boas práticas de arquitetura em camadas utilizando Spring Boot.

---

## 🚀 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.5.14
* Spring Web
* Spring Data JPA
* H2 Database
* Bean Validation
* Lombok
* SpringDoc OpenAPI (Swagger)
* JUnit 5
* Mockito
* Maven

---

## 📂 Estrutura do Projeto

```text
src/main/java/com/jhonatan/financeiro

├── controller
├── dto
├── exception
├── model
├── repository
└── service
```

---

## ⚙️ Funcionalidades

### Contas

* Criar conta
* Listar contas
* Buscar conta por ID
* Atualizar conta
* Desativar conta (soft delete)
* Consultar saldo da conta

### Categorias

* Criar categoria
* Listar categorias
* Buscar categoria por ID
* Buscar categorias por tipo
* Atualizar categoria
* Desativar categoria (soft delete)

### Transações

* Criar transação
* Listar transações
* Buscar por ID
* Buscar por conta
* Buscar por categoria
* Buscar por período
* Atualizar transação
* Excluir transação
* Resumo financeiro por período

---

## 📌 Regras de Negócio

* Não permitir valores negativos em transações.
* Não permitir datas futuras.
* O tipo da transação deve ser compatível com o tipo da categoria.
* Não permitir desativar contas com transações vinculadas.
* Não permitir desativar categorias com transações vinculadas.
* O saldo é calculado pela fórmula:

```text
saldo = saldoInicial + receitas - despesas
```

---

## 📖 Documentação da API

Após iniciar a aplicação:

Swagger UI:

```text
http://localhost:8081/swagger-ui/index.html
```

---

## 🧪 Testes

O projeto possui testes unitários utilizando:

* JUnit 5
* Mockito

Principais cenários testados:

- Cálculo de saldo da conta
- Busca de conta inexistente
- Criação de transação válida
- Validação de tipo da transação x categoria
- Criação de categoria válida
- Regra de exclusão de categoria com transações vinculadas

---

## ▶️ Executando o Projeto

Clone o repositório:

```bash
  git clone https://github.com/Jhonatan-Nowicki/controle-financeiro-api
```

Acesse a pasta:

```bash
  cd controle-financeiro-api
```

Execute:

```bash
  mvn spring-boot:run
```

A aplicação ficará disponível em:

```text
http://localhost:8081
```

---

## 🗄️ Banco de Dados

O projeto utiliza H2 Database em memória.

Console H2:

```text
http://localhost:8081/h2-console
```

---

## 👨‍💻 Autor

Jhonatan Nowicki

Projeto desenvolvido para fins de estudo e avaliação técnica Backend Java.
