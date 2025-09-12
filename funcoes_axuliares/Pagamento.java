package funcoes_axuliares;

public class Pagamento {

    private static int nextid = 1;
    private String idProcesso;
    private String usuario; //ID ou nome
    private float valor;
    private String tipo;
    private String metodo;
    private String data; //25/08/2025, etc
    private String status; //pendente, pago...

    public void novoPagamento(String id, String usr, float val, String type, String met, String da, String stt){
        this.idProcesso = nextid + "";
        nextid++;
        this.usuario = usr;
        this.valor = val;
        this.tipo = type;
        this.metodo = met;
        this.data = da;
        this.status = stt;
    }
    public void Pagamento () { //chamado quando a pessoa realizar o pagamento para mudar o status
        this.status = "pago";
    }
}