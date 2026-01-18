-- TABELA PESSOAS
CREATE TABLE IF NOT EXISTS Pessoas (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20)
);

-- TABELA FUNCIONARIOS
CREATE TABLE IF NOT EXISTS Funcionarios (
    cpf VARCHAR(11) PRIMARY KEY REFERENCES Pessoas(cpf) ON DELETE CASCADE,
    salario DECIMAL (10, 2),
    funcao VARCHAR(30)
);

-- TABELA USUARIOS
CREATE TABLE IF NOT EXISTS Usuarios (
    cpf VARCHAR(11) PRIMARY KEY REFERENCES Pessoas(cpf) ON DELETE CASCADE,
    assinatura CHAR(1)
);

-- TABELA ITENS_MENU
CREATE TABLE IF NOT EXISTS Itens_menu (
    id_menu SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL UNIQUE,
    ingredientes TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    estoque INT DEFAULT 0,
    qtd_venda INT DEFAULT 0
);

-- TABELA MANGAS
CREATE TABLE IF NOT EXISTS Mangas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL UNIQUE,
    autores VARCHAR(200),
    generos VARCHAR(200),
    serie VARCHAR(100),
    localizacao VARCHAR(50),
    qtd_vendas INT, 
    estoque INT,
    preco DECIMAL (10, 2) NOT NULL
);

-- TABELA PAGAMENTOS
-- Correção: Era "IF EXISTS" (errado), mudado para "IF NOT EXISTS"
CREATE TABLE IF NOT EXISTS Pagamentos (
    id SERIAL PRIMARY KEY,
    cpf_cliente VARCHAR(11), 
    metodo VARCHAR(50) NOT NULL,
    valor DECIMAL (10, 2) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);