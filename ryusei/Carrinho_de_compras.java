package ryusei;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Carrinho_de_compras {

    //a lista não é de mangas nem de itens do menu, aqui que aplicaremos o polimorfismo
    private List<Vendivel> itens_no_carrinho;

    //construtor
    public Carrinho_de_compras() {
        this.itens_no_carrinho = new ArrayList<>();
    }

    public void adicionaItem_carrinho (Vendivel item) {
        if (item.getEstoque() <= 0) {
            System.out.println("Desculpe, o item " + item.getNome() + " está sem estoque no momento.");
            return;
        }

        System.out.println("Adicionado o item " + item.getNome());
        this.itens_no_carrinho.add(item);
    }

    public float calcula_total () {
        float total = 0.0f;

        System.out.println("\n--- Processando Carrinho ---");
        // Polimorfismo em ação!
        for (Vendivel item : itens_no_carrinho) {
            // O loop não sabe se 'item' é Manga ou Item_menu
            // Ele só sabe que 'item' tem um método getPrecoVenda()
            
            float precoDoItem = item.getPrecoVenda();
            System.out.println("Item: " + item.getNome() + " - Preço: R$" + precoDoItem);
            
            total += precoDoItem;
        }
        System.out.println("----------------------------");
        return total;
    }

    // Método Getter para acessar a lista de itens no carrinho
    public List<Vendivel> itensNoCarrinho() {
        return this.itens_no_carrinho;
    }
    
    public void limparCarrinho() {
        this.itens_no_carrinho.clear();
    }

    public void AtualizaEstoquePosVenda() {
    // Cria um mapa para armazenar o item e sua quantidade
    Map<Vendivel, Integer> contagemItens = new HashMap<>();

    // Percorre a lista original contando as repetições
    for (Vendivel item : itens_no_carrinho) {
        contagemItens.put(item, contagemItens.getOrDefault(item, 0) + 1);
    }

    // Itera sobre os itens únicos e passa a quantidade total
    for (Map.Entry<Vendivel, Integer> entry : contagemItens.entrySet()) {
        Vendivel item = entry.getKey();
        int quantidade = entry.getValue();

        // Passa a quantidade como argumento
        item.qntdVendas_e_estoqueAtualiza(quantidade); 
    }
}
}