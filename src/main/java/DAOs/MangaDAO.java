package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ryusei.Manga; // classe do DAO

public class MangaDAO {

    private Connection conn;

    public MangaDAO(Connection conn) {
        this.conn = conn;
    }

    public void salvar(Manga manga) {
    String sql = "INSERT INTO Manga (titulo, autores, generos, serie, volume, localizacao, qtd_vendas, estoque, preco) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) { // preparação da consulta sql para o postgres
        
        // 1. Título
        stmt.setString(1, manga.getNome()); // setando os valores da consulta

        // 2. Autores
        stmt.setString(2, manga.getNome());

        // 3. Gêneros 
        stmt.setString(3, manga.getGeneros());

        // 4. Série
        stmt.setString(4, manga.getSerie());

        // 5. Localização
        stmt.setString(6, manga.getLocalizacao());

        // 6. Vendas
        stmt.setInt(7, manga.getVendas());

        // 7. Estoque
        stmt.setInt(8, manga.getEstoque());

        // 8. Preço
        stmt.setFloat(9, manga.getPreco()); 

        stmt.execute();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar mangá: " + e.getMessage(), e);
        }
    }

    // READ GERAL 

    public List<Manga> listarTodos() {

        List<Manga> listaMangas = new ArrayList<>();
        String sql = "SELECT * FROM MANGA";

        try (PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Manga novoManga = new Manga(
                    rs.getInt("id"),
                    rs.getString("titulo"), 
                    rs.getString("autores"), 
                    rs.getString("generos"), 
                    rs.getString("serie"),
                    rs.getString("localização"),
                    rs.getInt("qtd_vendas"),
                    rs.getInt("estoque"), 
                    rs.getFloat("preco"));

                    listaMangas.add(novoManga);
                }
        } catch (Exception e) {
        throw new RuntimeException("Erro ao listar mangás do banco", e);
        }
        return listaMangas;
    }

    // READ ESPECIFICO
    public Optional<Manga> buscaMangaPorNome(String nomeDesejado) {
    String sql = "SELECT * FROM Manga WHERE titulo = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nomeDesejado); // Passa o ID inteiro para o banco

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                
                Manga manga = new Manga(
                    rs.getInt("id"),
                    rs.getString("titulo"), 
                    rs.getString("autores"), 
                    rs.getString("generos"), 
                    rs.getString("serie"),
                    rs.getString("localizacao"), 
                    rs.getInt("qtd_vendas"),
                    rs.getInt("estoque"), 
                    rs.getFloat("preco")
                );

                return Optional.of(manga);
            }
        }
    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar mangá por nome", e);
    }

    return Optional.empty();
}

 public Optional<Manga> buscaMangaPorID(int id_desejado) {
    String sql = "SELECT * FROM Manga WHERE id = ?";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id_desejado); // Passa o ID inteiro para o banco

        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                
                Manga manga = new Manga(
                    rs.getInt("id"),
                    rs.getString("titulo"), 
                    rs.getString("autores"), 
                    rs.getString("generos"), 
                    rs.getString("serie"),
                    rs.getString("localizacao"), 
                    rs.getInt("qtd_vendas"),
                    rs.getInt("estoque"), 
                    rs.getFloat("preco")
                );

                return Optional.of(manga);
            }
        }
    } catch (Exception e) {
        throw new RuntimeException("Erro ao buscar mangá por ID", e);
    }

    return Optional.empty();
}
}