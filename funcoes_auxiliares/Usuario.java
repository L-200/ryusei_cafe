package funcoes_auxiliares;

public class Usuario {

    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    //classe historico de compra
    char assinatura;
    
    //construtor
    public Usuario(String cpf, String nome, String email, String telefone, char assinatura) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.assinatura = assinatura;
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