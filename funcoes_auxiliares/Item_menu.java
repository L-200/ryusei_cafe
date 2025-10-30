package funcoes_auxiliares;

public class Item_menu implements Vendivel {
    private String nome;
    private int qtdVenda;
    private float preco;
    private static int nextid = 1;
    private int id;
    private String ingredientes;

    //construtor da classe
    public Item_menu(String nome, String ingredientes, float preco) {
        this.nome = nome;
        this.qtdVenda = 0;
        this.preco = preco;
        this.id = nextid;
        nextid++;
        this.ingredientes = ingredientes;
        
    }

    public void nomeAtualiza(String novo_nome) {
        this.nome = novo_nome;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public float getPrecoVenda() {
        return this.preco;
    }

    public void vendasAtualiza (int qntd_novas_vendas) {
        this.qtdVenda += qntd_novas_vendas;
    }

    public void precoAtualiza (float novo_preco) {
        this.preco = novo_preco;
    }

    public void mostraItem() {
        System.out.println("Nome: " + this.nome);
        System.out.println("ID do item no menu: " + this.id);
        System.out.println("Lista de ingerdientes: " + this.ingredientes);
        System.out.println("Quantidade vendida: " + this.qtdVenda);
        System.out.println("Preço: R$" + this.preco);
    }

    public int getID_menu () {
        return this.id;
    }

}