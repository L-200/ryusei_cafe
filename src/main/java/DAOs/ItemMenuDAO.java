package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ryusei.Item_menu;

public class ItemMenuDAO {

    private Connection conn;

    public ItemMenuDAO (Connection conn) {
        this.conn = conn;
    }

    public void salvarItemMenu (Item_menu item) {

        String sql = "INSERT INTO Itens_menu (nome, ingredientes, preco, estoque, qtd_vendas) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getNome());

            stmt.setString(2, item.getIngredientes());

            stmt.setFloat(3, item.getPreco());

            stmt.setInt(4, item.getEstoque());

            stmt.setInt(5, item.getQtdVenda());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar Item do Menu: " + e.getMessage(), e);
        }
    }

    public List<Item_menu> listarTodos () {

        List <Item_menu> listaDeItens = new ArrayList<>();
        String sql = "SELECT * FROM Itens_menu";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {
                        Item_menu novo_item = new Item_menu(
                            rs.getInt("id_menu"),
                            rs.getString("nome"), 
                            rs.getString("ingredientes"), 
                            rs.getFloat("preco"), 
                            rs.getInt("estoque"), 
                            rs.getInt("qtd_vendas"));
                        
                        listaDeItens.add(novo_item);
                    }
                } catch (Exception e) {
                    throw new RuntimeException ("Erro ao listar todos os Itens do Menu" + e.getMessage(), e);
                }
                return listaDeItens;
    }

    public Optional <Item_menu> buscaItemPorID (int ID) {

        String sql = "SELECT * FROM Itens_menu WHERE id_menu = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ID);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Item_menu item_encontrado = new Item_menu(rs.getInt("id_menu"), 
                    rs.getString("nome"),
                    rs.getString("ingredientes"), 
                    rs.getFloat("preco"), 
                    rs.getInt("estoque"), 
                    rs.getInt("qtd_vendas"));

                    return Optional.of(item_encontrado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pelo item" + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public Optional <Item_menu> buscaItemPorNome (String nome_desejado) {

        String sql = "SELECT * FROM Itens_menu WHERE nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome_desejado);

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    Item_menu item_encontrado = new Item_menu(rs.getInt("id_menu"), 
                    rs.getString("nome"),
                    rs.getString("ingredientes"), 
                    rs.getFloat("preco"), 
                    rs.getInt("estoque"), 
                    rs.getInt("qtd_vendas"));

                    return Optional.of(item_encontrado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar pelo item" + e.getMessage(), e);
        }

        return Optional.empty();
    }
}