package ryuccino;

public class Usuario {

    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    //classe historico de compra
    char assinatura;
    
    public void criarUsuario(String CPF, String Nome, String Telefone, String Email, char Assinatura) {
        this.cpf = CPF;
        this.nome = Nome;
        this.telefone = Telefone;
        this.email = Email;
        this.assinatura = Assinatura;
    }
    
    public void checarAssinatura () {
        System.out.println("A assinatura desse usuário é nivel " + this.assinatura);
    }

    public void mudaAssinatura(char novaAssinatura) {
        this.assinatura = novaAssinatura;
    }

    public void mostraUsuario() {
        System.out.println("Nome: " + this.nome);
        System.out.println("CPF: " + this.cpf);
        System.out.println("Telefone: " + this.telefone);
        System.out.println("Email: " + this.email);
        System.out.println("Assinatura: " + this.assinatura);
    }
    
}