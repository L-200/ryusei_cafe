package funcoes_axuliares;

public class Manga {
     private String id;
    private String nome;
    private String[] autores; //Pode haver multiplos
    private String[] generos; //geralmente tem multiplos
    private String serie;
    private int volume;
    private String localizacao;
    private int qtdVenda;
    private int estoque;
    //private int qtdLeitura_manga; *como não vamos emprestar mangas, sugiro retirar isso

    public void criarManga(String id, String Nome, String[] auto, String[] gen, String ser, int vol, String local, int qVen, int estoque){
        this.id = id;
        this.nome = Nome;
        this.autores = auto;
        this.generos = gen;
        this.serie = ser;
        this.volume = vol;
        this.localizacao = local;
        this.qtdVenda = qVen;
        this.estoque = estoque;
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
        this.estoque -= qtd;
    }

    public void estoqueAtualiza (int mangas_novos) {
        this.estoque += mangas_novos;
    }
    
    /*public void qtdLeituraAtualiza(int qtd){
        this.qtdLeitura += qtd;
    }*/
}
