-- para o package pessoas
CREATE TABLE Pessoas (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20)
)

CREATE TABLE Funcionario (
    cpf VARCHAR(11) PRIMARY KEY REFERENCES Pessoas(cpf) ON DELETE CASCADE,
    salario DECIMAL (10, 2),
    funcao VARCHAR(30)
)

CREATE TABLE Usuario (
    cpf VARCHAR(11) PRIMARY KEY REFERENCES Pessoas(cpf) ON DELETE CASCADE,
    assinatura CHAR(1)
)

-- para o package ryusei

CREATE TABLE Item_menu (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    preco DECIMAL (10, 2) NOT NULL
)

CREATE TABLE Manga (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100),
    genero VARCHAR(50),
    serie VARCHAR(100),
    volume INT,
    localizacao VARCHAR(50),
    qtd_vendas INT, 
    estoque INT,
    preco DECIMAL (10, 2) NOT NULL
)

CREATE TABLE Pagamento (
    id SERIAL PRIMARY KEY,
    cpf_cliente VARCHAR(11) REFERENCES Pessoas(cpf),
    metodo VARCHAR(50) NOT NULL,
    valor DECIMAL (10, 2) NOT NULL,
    data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP

)
