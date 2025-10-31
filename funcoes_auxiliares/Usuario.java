package funcoes_auxiliares;

public class Usuario extends Pessoa {

    //classe historico de compra
    private char assinatura;
    
    //construtor
    public Usuario(String cpf, String nome, String email, String telefone, char assinatura) {
        super(cpf, nome, telefone, email);
        this.assinatura = assinatura;
    }
    
    public void checarAssinatura () {
        System.out.println("A assinatura desse usuário é nivel " + this.assinatura);
    }

    public void mudaAssinatura(char novaAssinatura) {
        this.assinatura = novaAssinatura;
    }

    public void mostraUsuario() {
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Email: " + getEmail());
        System.out.println("Assinatura: " + this.assinatura);
    }

}