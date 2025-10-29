package funcoes_auxiliares;

public class Item_menu {
    private String nome;
    private int qtdVenda;
    private float preco;
    private int id_item;

    //construtor da classe
    public Item_menu(String nome, String ingredientes, float preco, int id_item) {
        this.nome = nome;
        this.qtdVenda = 0;
        this.preco = preco;
        this.id_item = id_item;
    }

    public void nomeAtualiza(String novo_nome) {
        this.nome = novo_nome;
    }


    public void vendasAtualiza (int qntd_novas_vendas) {
        this.qtdVenda += qntd_novas_vendas;
    }

    public void precoAtualiza (float novo_preco) {
        this.preco = novo_preco;
    }

    public void mostraItem() {
        System.out.println("Nome: " + this.nome);
        System.out.println("Quantidade vendida: " + this.qtdVenda);
        System.out.println("Preço: R$" + this.preco);
    }

    public int getID_menu () {
        return this.id_item;
    }

}