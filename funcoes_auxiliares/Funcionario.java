package funcoes_auxiliares;

public class Funcionario extends Pessoa {

    //atributos específicos da classe Funcionario
    private double salario;
    private String funcao; // (Ex: "Vendedor", "Gerente", "Atendente")

    //construtor da classe Funcionario
    public Funcionario(String cpf, String nome, String telefone, String email, 
                       double salario, String funcao) {
        
        // chamando o construtor da superclasse 'Pessoa' primeiro.
        super(cpf, nome, telefone, email);

        this.salario = salario;
        this.funcao = funcao;
    }

    public double getSalario() {
        return this.salario;
    }

    public String getFuncao() {
        return this.funcao;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public void mostraFuncionario() {
        System.out.println("Dados do Funcionário:");
        System.out.println("----------------------");
        System.out.println("Nome: " + getNome());
        System.out.println("CPF: " + getCpf());
        System.out.println("Telefone: " + getTelefone());
        System.out.println("Email: " + getEmail());
        System.out.println("Função: " + this.funcao);
        System.out.println("Salário: R$" + this.salario);
        System.out.println("----------------------");
        System.out.println("");
    }
}