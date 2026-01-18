package ryusei;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import ryusei.SistemaDeBusca; 
import ryusei.Manga;
import ryusei.Item_menu;

public class Carrinho_de_compras {

    private List<Vendivel> itens_no_carrinho;

    public Carrinho_de_compras() {
        this.itens_no_carrinho = new ArrayList<>();
    }

    public void adicionaItem_carrinho (Vendivel item) {
        // Validação simples de estoque em memória antes de adicionar
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
        for (Vendivel item : itens_no_carrinho) {
            float precoDoItem = item.getPrecoVenda();
            System.out.println("Item: " + item.getNome() + " - Preço: R$" + precoDoItem);
            total += precoDoItem;
        }
        System.out.println("----------------------------");
        return total;
    }

    public List<Vendivel> itensNoCarrinho() {
        return this.itens_no_carrinho;
    }
    
    public void limparCarrinho() {
        this.itens_no_carrinho.clear();
    }

    // O método agora recebe o SistemaDeBusca para poder salvar no banco
    public void AtualizaEstoquePosVenda(SistemaDeBusca sistema) {
        
        Map<Vendivel, Integer> contagemItens = new HashMap<>();

        // Conta quantos itens de cada tipo foram vendidos
        for (Vendivel item : itens_no_carrinho) {
            contagemItens.put(item, contagemItens.getOrDefault(item, 0) + 1); // adiciona 1 à contagem se já existir
        }

        // Itera sobre os itens únicos
        for (Map.Entry<Vendivel, Integer> entry : contagemItens.entrySet()) {
            Vendivel item = entry.getKey();
            int quantidadeVendida = entry.getValue();

            // A. Atualiza o objeto na Memória RAM
            item.qntdVendas_e_estoqueAtualiza(quantidadeVendida); 

            // B. Atualiza no Banco de Dados (Postgres)            
            if (item instanceof Manga) {
                // Converte (Cast) para Manga e manda o sistema salvar
                sistema.atualizarEstoqueManga((Manga) item); 
                
            } else if (item instanceof Item_menu) {
                // Converte (Cast) para Item_menu e manda o sistema salvar
                sistema.atualizarEstoqueItemMenu((Item_menu) item);
            }
        }
        
        System.out.println("Estoques atualizados no banco de dados!");
    }
}