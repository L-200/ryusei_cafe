package ryusei;

public class Pagamento {

    private static int nextid = 1;
    private String id_pagamento;
    private String usuario; //ID ou nome
    private float valor;
    private String metodo;
    private String data; //25/08/2025, etc
    private String status; //pendente, pago...

    //construtor da classe
    public Pagamento(String usr, float val, String met, String da, String stt){
        this.id_pagamento = nextid + "";
        nextid++;
        this.usuario = usr;
        this.valor = val;
        this.metodo = met;
        this.data = da;
        this.status = stt;
    }
    public void Pago() { //chamado quando a pessoa realizar o pagamento para mudar o status
        this.status = "pago";
    }

    public void mostraPagamento() {
        System.out.println("ID do processo: " + this.id_pagamento);
        System.out.println("Usuário: " + this.usuario);
        System.out.println("Valor: R$" + this.valor);
        System.out.println("Método: " + this.metodo);
        System.out.println("Data: " + this.data);
        System.out.println("Status: " + this.status);
    }

    public String getID_pagamento () {
        return this.id_pagamento;
    }

    public String getStatus () {
        return this.status;
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

    public void setStatus (String novo_status) {
        this.status = novo_status;
    }
    
    public void setID_pagamento (String novo_id) {
        this.id_pagamento = novo_id;
    }
    
}