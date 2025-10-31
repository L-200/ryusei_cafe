package funcoes_auxiliares;

public class Pessoa {

    private String cpf;
    private String nome;
    private String telefone;
    private String email;

    // Construtor da Superclasse
    // Recebe os 4 valores e os atribui aos atributos
    public Pessoa(String cpf, String nome, String telefone, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Permitem que outras classes LEIAM os dados privados
    public String getCpf() {
        return this.cpf;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getEmail() {
        return this.email;
    }
}