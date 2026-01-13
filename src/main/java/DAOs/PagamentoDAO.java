package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Date;
import java.time.LocalDate;

import ryusei.Pagamento; // classe do DAO

public class PagamentoDAO {

    private Connection conn;
    
    public PagamentoDAO (Connection conn) {
        this.conn = conn;
    }

    public void salvarPagamento (Pagamento pagamento) {

        String sql = "INSERT INTO Pagamento (cpf_cliente, metodo, valor, data_pagamento) VALUES ( ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pagamento.getUsuario());

            stmt.setString(2, pagamento.getMetodo());

            stmt.setFloat(3, pagamento.getValor());

            LocalDate localDate = LocalDate.parse(pagamento.getData()); // parse direto
            Date sqlDate = Date.valueOf(localDate);

            stmt.setDate(4, sqlDate);

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar pagamento: " + e.getMessage(), e);
        }
    }

    public List<Pagamento> listarTodos() {

        List<Pagamento> listaPagamentos = new ArrayList<>();
        String sql = "SELECT * FROM Pagamentos";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {

                    Date sqlDate = rs.getDate("data_pagamento");

                    String data = sqlDate.toLocalDate().toString();

                    Pagamento novoPagamento = new Pagamento(
                        rs.getInt("id"), 
                        rs.getString("cpf_cliente"),
                        rs.getFloat("valor"), 
                        rs.getString("metodo"),
                        data);

                        listaPagamentos.add(novoPagamento); 
                }
            } catch (Exception e) {
                throw new RuntimeException ("Erro ao listar pagamentos do banco: " + e.getMessage(), e);
            }
            return listaPagamentos;
    }

    public Optional<Pagamento> buscaPagamentoPorID(int id_desejado) {

        String sql = "SELECT * FROM Pagamentos WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_desejado);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    Date sqlDate = rs.getDate("data_pagamento");

                    String data = sqlDate.toLocalDate().toString();

                    Pagamento pagamentoEncontrado = new Pagamento(
                        rs.getInt("id"), 
                        rs.getString("cpf_cliente"),
                        rs.getFloat("valor"), 
                        rs.getString("metodo"),
                        data);
                        
                        return Optional.of(pagamentoEncontrado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar Pagamento por ID: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

}