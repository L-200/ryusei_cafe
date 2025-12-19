# ☕ Ryusei Café - Sistema de Gerenciamento

> Sistema de gerenciamento para um Manga Café desenvolvido como projeto final para a disciplina de **Programação Orientada a Objetos (POO)**.

---

## 📖 Sobre o Projeto

O **Ryusei Café** é uma aplicação Java projetada para simular a administração de um estabelecimento de leitura e alimentação. O objetivo principal foi aplicar na prática os pilares da POO (Programação Orientação a Objetos), criando um sistema robusto, escalável e de fácil manutenção.

O sistema opera em dois modos:
1.  **Interface Gráfica (GUI):** Para uso intuitivo por atendentes.
2.  **Linha de Comando (CLI):** Para testes rápidos e depuração.

---

## ⚙️ Funcionalidades

- [x] **Gestão de Produtos:** Cadastro e venda de itens do menu e mangás.
- [x] **Gestão de Pessoas:** Sistema de herança para diferenciar Funcionários e Clientes.
- [x] **Gestão de Pagamentos:** Simulação de pagamentos, incluindo o modo (cartão, dinheiro, pix) e se o pagamento já foi quitado.
- [x] **Busca Inteligente:** Localização rápida de itens no acervo.
- [x] **Permanência de Dados:** Os dados do sistema são salvos após o sistema ser fechado e podem ser reutilizados em usos futuros.

---

## 🧠 Conceitos de POO Aplicados

Este projeto foca fortemente na aplicação acadêmica de conceitos de POO:

* **Modularização (Packages):** Estruturação do código em pacotes para separar as classes e deixá-las juntas de classes similares. Isso facilita a manutenção e permite o reaproveitamento futuro de componentes isolados.
* **Interfaces (`Vendivel`):** Padronização de métodos para qualquer objeto comercializável (seja um café ou um volume de mangá), garantindo polimorfismo.
* **Herança (`Pessoa` -> `Funcionario`, `Usuario`):** Reutilização de código para atributos comuns (CPF, Nome, Telefone, Email), facilitando a manutenção.
* **Estrutura de Dados (`SistemaDeBusca`):** Implementação de lógica de armazenamento e recuperação de objetos em memória.
* **Encapsulamento:** Proteção dos dados sensíveis das classes através de modificadores de acesso.

---

## 🚀 Como Rodar o Projeto

### Pré-requisitos
* **Java JDK** instalado (Recomendado versão 11 ou superior).
* Terminal ou IDE de sua preferência (VS Code, IntelliJ, Eclipse).

### 📦 Instalação e Execução

Clone este repositório ou baixe os arquivos. Em seguida, abra o terminal na pasta raiz do projeto.

#### Opção 1: Versão com Interface Gráfica (GUI)
Ideal para a experiência completa do usuário.

```bash
# 1. Compile o arquivo principal da GUI
javac RyuseiCafeGUI.java

# 2. Execute a aplicação
java RyuseiCafeGUI
```

#### Opção 3: Versão Linha de Comando (CLI)
Ideal para verificar a lógica sem dependência de janelas.

```bash
# 1. Compile o arquivo do modo texto
javac ryusei_cafe.java

# 2. Execute a aplicação
java ryusei_cafe
```
---
## 📝 Licença
Este projeto é de uso educacional. Sinta-se à vontade para forká-lo e aprender com ele.