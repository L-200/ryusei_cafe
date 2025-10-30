package funcoes_auxiliares;

import java.util.ArrayList;
import java.util.List;

public class Carrinho_de_compras {

    //a lista não é de mangas nem de itens do menu, aqui que aplicaremos o polimorfismo
    private List<Vendivel> itens_no_carrinho;

    //construtor
    public Carrinho_de_compras() {
        this.itens_no_carrinho = new ArrayList<>();
    }

    public void adicionaItem_carrinho (Vendivel item) {
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
}