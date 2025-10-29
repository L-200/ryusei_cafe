package funcoes_auxiliares;

public class Pagamento {

    private static int nextid = 1;
    private String idProcesso;
    private String usuario; //ID ou nome
    private float valor;
    private String tipo;
    private String metodo;
    private String data; //25/08/2025, etc
    private String status; //pendente, pago...

    //construtor da classe
    public Pagamento(String usr, float val, String type, String met, String da, String stt){
        this.idProcesso = nextid + "";
        nextid++;
        this.usuario = usr;
        this.valor = val;
        this.tipo = type;
        this.metodo = met;
        this.data = da;
        this.status = stt;
    }
    public void Pago() { //chamado quando a pessoa realizar o pagamento para mudar o status
        this.status = "pago";
    }

    public void mostraPagamento() {
        System.out.println("ID do processo: " + this.idProcesso);
        System.out.println("Usuário: " + this.usuario);
        System.out.println("Valor: R$" + this.valor);
        System.out.println("Tipo: " + this.tipo);
        System.out.println("Método: " + this.metodo);
        System.out.println("Data: " + this.data);
        System.out.println("Status: " + this.status);
    }
    
}