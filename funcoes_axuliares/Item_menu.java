package funcoes_axuliares;

public class Item_menu {
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