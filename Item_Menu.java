class Usuario {

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
    
}

class manga {
    private String id;
    private String nome;
    private String[] autores; //Pode haver multiplos
    private String[] generos; //geralmente tem multiplos
    private String serie;
    private int volume;
    private String localizacao;
    private int qtdVenda;
    //private int qtdLeitura_manga; *como não vamos emprestar mangas, sugiro retirar isso

    public void criarManga(String id, String Nome, String[] auto, String[] gen, String ser, int vol, String local, int qVen, int qLei){
        this.id = id;
        this.nome = Nome;
        this.autores = auto;
        this.generos = gen;
        this.serie = ser;
        this.volume = vol;
        this.localizacao = local;
        this.qtdVenda = qVen;
        //this.qtdLeitura = qLei;
    }
  /*public void idAtualiza(String id){ 
        this.id = id;
    } não vamos precisar trocar o id de um item*/
    public void nomeAtualiza(String Nome){
        this.nome = Nome;
    }
    public void autoresAtualiza(String[] aut){
        this.autores = aut;
    }
    public void generosAtualiza(String[] gen){
        this.generos = gen;
    }
    public void serieAtualiza(String ser){
        this.serie = ser;
    }
    public void volumeAtualiza(int vol){
        this.volume = vol;
    }
    public void localizacaoAtualiza(String local){
        this.localizacao = local;
    }
    public void qtdVendaAtualiza(int qtd){
        this.qtdVenda += qtd;
    }
    /*public void qtdLeituraAtualiza(int qtd){
        this.qtdLeitura += qtd;
    }*/
}
// nao sei se funciona, nem se é uma boa ideia
/*
class item_do_Estoque{
    String nome;
    int qtd;
}
class Estoque_Geral{
    item_do_Estoque[] estq_ger;
}
*/
class pagamentos {
    private String idProcesso;
    private String usuario; //ID ou nome
    private float valor;
    private String tipo;
    private String metodo;
    private String data; //25/08/2025, etc
    private String status; //pendente, pago...
    
    public void novoPagamento(String id, String usr, float val, String type, String met, String da, String stt){
        this.idProcesso = id;
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

public class Item_Menu {
    private String nome;
    private String ingredientes;
    private int qtdVenda;
    private float preco;

    public void novoItem(String nome, String ingredientes, float preco) {
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.qtdVenda = 0;
        this.preco = preco;
    }

    public void nomeAtualiza(String novo_nome) {
        this.nome = novo_nome;
    }

    public void ingredientesAtualiza (String nova_lista_ingredientes) {
        this.ingredientes = nova_lista_ingredientes;
    }

    public void vendasAtualiza (int qntd_novas_vendas) {
        this.qtdVenda += qntd_novas_vendas;
    }

    public void precoAtualiza (float novo_preco) {
        this.preco = novo_preco;
    }

}

