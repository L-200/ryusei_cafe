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

