package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pessoa.Usuario;

public class UsuarioDAO {

    private Connection conn;

    public UsuarioDAO (Connection conn) {
        this.conn = conn;
    }

    public void salvarUsuario (Usuario user) {

        String sqlPessoa = "INSERT INTO Pessoas (cpf, nome, email, telefone) VALUES (?, ?, ?, ?)";
        String sqlUsuario = "INSERT INTO Usuarios (cpf, assinatura) VALUES (?, ?)";

        try {

            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlPessoa)) {

                stmt.setString(1, user.getCpf());
                stmt.setString(2, user.getNome());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, user.getTelefone());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlUsuario)) {

                stmt.setString(1, user.getCpf());
                stmt.setString(2, String.valueOf(user.getAssinatura()));
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {} // Desfaz
            throw new RuntimeException("Erro ao salvar usuário", e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException e) {}
        }
    }

    public List <Usuario> listaUsuarios() {

        List <Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT p.cpf, p.nome, p.email, p.telefone, u.assinatura
                FROM Usuarios u
                INNER JOIN Pessoas p ON u.cpf = p.cpf
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    
                    Usuario user = new Usuario(
                        rs.getString("cpf"), 
                        rs.getString("nome"), 
                        rs.getString("email"), 
                        rs.getString("telefone"), 
                        rs.getString("assinatura").charAt(0) // String -> Char
                    );
                    usuarios.add(user);
                }
            } catch (Exception e) {
                throw new RuntimeException("Erro ao listar todos os usuarios: " + e.getMessage(), e);
            }

            return usuarios;
    }

    public Optional <Usuario> buscaUsuarioPorCPF(String cpf_desejado) {

        String sql = """
                SELECT p.cpf, p.nome, p.email, p.telefone, u.assinatura
                FROM Usuarios u
                INNER JOIN Pessoas p ON u.cpf = p.cpf
                WHERE u.cpf = ?
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf_desejado);
            
            try (ResultSet rs = stmt.executeQuery()) {

                if(rs.next()) {
                    Usuario userAchado = new Usuario(
                        rs.getString("cpf"), 
                        rs.getString("nome"), 
                        rs.getString("email"), 
                        rs.getString("telefone"), 
                        rs.getString("assinatura").charAt(0) // String -> Char
                    );

                    return Optional.of(userAchado);
                }
            } 
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuario: " + e.getMessage(), e);
        }
        return Optional.empty();
    }
}