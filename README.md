# ☕ Ryusei Café - Sistema de Gerenciamento

> Sistema de gerenciamento para um Manga Café desenvolvido como projeto final para a disciplina de **Programação Orientada a Objetos (POO)**.

![Status](https://img.shields.io/badge/Status-Finalizado-success)
![Java](https://img.shields.io/badge/Java-17%2B-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Integrated-blue)

---

## 📖 Sobre o Projeto

O **Ryusei Café** é uma aplicação Java projetada para simular a administração de um estabelecimento de leitura e alimentação. O objetivo principal foi aplicar na prática os pilares da POO, evoluindo de uma persistência em memória para uma integração robusta com **Banco de Dados Relacional (SQL)**.

O sistema opera em dois modos:
1.  **Interface Gráfica (GUI - Swing):** Para uso completo, incluindo vendas visuais, gestão de estoque e CRUD de pessoas.
2.  **Linha de Comando (CLI):** Para testes rápidos de lógica e conexão.

---

## ⚙️ Funcionalidades

- [x] **Gestão de Produtos:** Cadastro, edição e atualização de estoque de Itens do Menu e Mangás.
- [x] **Vendas e Carrinho:** Interface visual para adicionar itens, calcular totais e baixar estoque automaticamente via transação no banco.
- [x] **Gestão de Pessoas (CRUD):** - Cadastro e Edição de **Clientes** (com sistema de assinaturas).
- [x] Cadastro e Edição de **Funcionários** (com cargos e salários).
- [x] **Histórico de Pagamentos:** Registro persistente de todas as vendas realizadas.
- [x] **Persistência de Dados (PostgreSQL):** Conexão via JDBC com banco de dados real.
- [x] **Inicialização Automática:** O sistema verifica e cria as tabelas necessárias (`init.sql`) na primeira execução.

---

## 🧠 Conceitos de POO e Arquitetura Aplicados

Este projeto vai além do básico, implementando padrões de projeto e conceitos avançados:

* **Padrão DAO (Data Access Object):** Separação completa entre a lógica de negócios e o acesso ao banco de dados (`MangaDAO`, `UsuarioDAO`, etc.), facilitando a manutenção.
* **JDBC (Java Database Connectivity):** Uso de drivers para conexão e execução de comandos SQL seguros (Prepared Statements) para evitar SQL Injection.
* **Modularização (Packages):** Estruturação em pacotes (`DAOs`, `ryusei`, `pessoa`, `gui`) para melhor organização.
* **Interfaces e Polimorfismo:** Uso da interface `Vendivel` para tratar Mangás e Itens de Menu de forma genérica no carrinho de compras.
* **Herança:** Estrutura `Pessoa` -> `Funcionario` / `Usuario` refletida tanto nas classes Java quanto na modelagem do Banco de Dados (Tabelas Relacionais).
* **Tratamento de Exceções:** Uso robusto de `try-catch` para garantir que o sistema não feche em caso de erros de conexão ou validação de dados.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos

1.  **Java JDK** instalado (Versão 17 ou superior recomendada).
2.  **Maven** instalado (para gerenciamento de dependências).
3.  **PostgreSQL** instalado e rodando.

### 📦 Configuração do Banco de Dados

Antes de rodar, crie um banco de dados e um usuário no seu Postgres local com as seguintes credenciais (ou altere a classe `ConnectionFactory`):

* **Database:** `ryusei_cafe`
* **Usuário:** `ryusei`
* **Senha:** `ryusei`

> **Nota:** Não é necessário criar as tabelas manualmente. O sistema possui um arquivo `src/main/resources/init.sql` que é executado automaticamente na primeira conexão para criar a estrutura do banco.

### 🛠️ Compilação e Execução

Abra o terminal na pasta raiz do projeto e execute:

**1. Compilar o projeto e baixar dependências (Driver Postgres):**
```bash
mvn clean compile
```
Após isso, escolha qual das duas seguintes opções você deseja usar.

#### Opção 1: Versão com Interface Gráfica (GUI)
Ideal para a experiência completa do usuário.

```bash
mvn exec:java -Dexec.mainClass="RyuseiCafeGUI"
```

#### Opção 2: Versão Linha de Comando (CLI)
Ideal para verificar a lógica sem dependência de janelas.

```bash
mvn exec:java -Dexec.mainClass="ryusei_cafe"
```

---
## 📝 Licença
Este projeto é de uso educacional. Sinta-se à vontade para forká-lo e aprender com ele.