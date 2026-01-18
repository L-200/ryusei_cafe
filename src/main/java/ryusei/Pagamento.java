package ryusei;

public class Pagamento {

    private int id;
    private String usuario; //ID ou nome
    private float valor;
    private String metodo;
    private String data; //25/08/2025, etc

    //construtor da classe
    public Pagamento(int id, String usr, float val, String met, String da){
        this.id = id;
        this.usuario = usr;
        this.valor = val;
        this.metodo = met;
        this.data = da;
    }

    public void mostraPagamento() {
        System.out.println("ID do processo: " + this.id);
        System.out.println("Usuário: " + this.usuario);
        System.out.println("Valor: R$" + this.valor);
        System.out.println("Método: " + this.metodo);
        System.out.println("Data: " + this.data);
    }

    public int getID_pagamento () {
        return this.id;
    }

    public float getValor () {
        return this.valor;
    }

    public String getUsuario () {
        return this.usuario;
    }

    public String getMetodo () {
        return this.metodo;
    }

    public String getData () {
        return this.data;
    }

    public void setID_pagamento (int novo_id) {
        this.id = novo_id;
    }
    
}