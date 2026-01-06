package ryusei;

//interface para qualquer item que possa ser vendido
public interface Vendivel {

    float getPrecoVenda ();

    String getNome ();

    int getEstoque ();

    void qntdVendas_e_estoqueAtualiza (int qntd_novas_vendas);

    void add_estoque (int qntd_novo_estoque);
}