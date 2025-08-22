class Usuario {

    string cpf;
    string nome;
    string telefone;
    string email;
    //classe historico de compra
    char assinatura;
    
    public void criarUsuario(string CPF, string Nome, string Telefone, string Email, char Assinatura) {
        this.cpf = CPF;
        this.nome = Nome;
        this.telefone = Telefone;
        this.email = Email;
        this.assinatura = Assinatura;
    }
    
}