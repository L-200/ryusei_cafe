package ryusei;

public class Manga implements Vendivel {

    private static int nextid = 1;
    private String id;
    private String nome;
    private String[] autores; //Pode haver multiplos
    private String[] generos; //geralmente tem multiplos
    private String serie;
    private int volume;
    private String localizacao;
    private int qtdVenda;
    private int estoque;
    private float preco;
    //private int qtdLeitura_manga; *como não vamos emprestar mangas, sugiro retirar isso

    //construtor do manga
    public Manga(String Nome, String[] auto, String[] gen, String ser, int vol, String local, int estoque, float preco){
        this.id = nextid + "";
        nextid++;
        this.nome = Nome;
        this.autores = auto;
        this.generos = gen;
        this.serie = ser;
        this.volume = vol;
        this.localizacao = local;
        this.qtdVenda = 0;
        this.estoque = estoque;
        this.preco = preco;
        //this.qtdLeitura = qLei;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public float getPrecoVenda() {
        return this.preco;
    }

    @Override
    public int getEstoque() {
        return this.estoque;
    }

    @Override
    public void qntdVendas_e_estoqueAtualiza(int qntd_novas_vendas) {
        this.qtdVenda += qntd_novas_vendas;
        this.estoque -= qntd_novas_vendas;
    }

    @Override
    public void add_estoque(int qntd_novo_estoque) {
        this.estoque += qntd_novo_estoque;
    }

    public String getId() {
        return this.id;
    }

    public String[] getAutores() {
        return this.autores;
    }

    public String[] getGeneros() {
        return this.generos;
    }

    public String getSerie() {
        return this.serie;
    }

    public int getVolume() {
        return this.volume;
    }

    public String getLocalizacao() {
        return this.localizacao;
    }

    public float getPreco() {
        return this.preco;
    }

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
    
    public void mostraManga() {
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.print("Autores: ");
        for (String autor : this.autores) {
            System.out.print(autor + " ");
        }
        System.out.println();
        System.out.print("Gêneros: ");
        for (String genero : this.generos) {
            System.out.print(genero + " ");
        }
        System.out.println();
        System.out.println("Série: " + this.serie);
        System.out.println("Volume: " + this.volume);
        System.out.println("Localização: " + this.localizacao);
        System.out.println("Quantidade vendida: " + this.qtdVenda);
        System.out.println("Estoque disponível: " + this.estoque);
        //System.out.println("Quantidade de leituras: " + this.qtdLeitura_manga);
    }

    /*public void qtdLeituraAtualiza(int qtd){
        this.qtdLeitura += qtd;
    }*/
}
