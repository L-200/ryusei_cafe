package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import pessoa.Funcionario;

public class FuncionarioDAO {

    private Connection conn;

    public FuncionarioDAO (Connection conn) {
        this.conn = conn;
    }

    public void salvarFuncionario (Funcionario funcionario) {

        String sqlPessoa = "INSERT INTO Pessoas (cpf, nome, email, telefone) VALUES (?, ?, ?, ,?)";
        String sqlFuncionario = "INSERT INTO Funcionarios (cpf, salario, funcao) VALUES (?, ?, ?)";

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtP = conn.prepareStatement(sqlPessoa)) {

                stmtP.setString(1, funcionario.getCpf());
                stmtP.setString(2, funcionario.getNome());
                stmtP.setString(3, funcionario.getEmail());
                stmtP.setString(4, funcionario.getTelefone());
                stmtP.executeUpdate();
            }

            try (PreparedStatement stmtF = conn.prepareStatement(sqlFuncionario)) {

                stmtF.setString(1, funcionario.getCpf());
                stmtF.setDouble(2, funcionario.getSalario());
                stmtF.setString(3, funcionario.getFuncao());
                stmtF.executeUpdate();
            }

            conn.commit();
 
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) {} // rollback
            throw new RuntimeException("Erro ao salvar usuário" + e.getMessage(), e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {}
        }
    }

    public List<Funcionario> listaTodos () {
        List <Funcionario> listaDeFuncionarios = new ArrayList<>();

        String sql = """
                SELECT p.cpf, p.nome, p.email, p.telefone, f.salario, f.funcao
                FROM Funcionarios f
                INNER JOIN Pessoas p on f.cpf = p.cpf
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery())  {
                
                while (rs.next()) {
                    Funcionario funcionario = new Funcionario(
                        rs.getString("cpf"), 
                        rs.getString("nome"), 
                        rs.getString("telefone"), 
                        rs.getString("email"), 
                        rs.getDouble("salario"),
                        rs.getString("funcao"));

                    listaDeFuncionarios.add(funcionario);
                }
            } catch (Exception e) {
                throw new RuntimeException ("Erro ao listar funcionários: " + e.getMessage(), e);
            }
            return listaDeFuncionarios;
    }

    public Optional<Funcionario> buscaFuncionarioCPF(String cpf_desejado) {

        String sql = """
                SELECT p.cpf, p.nome, p.email, p.telefone, f.salario, f.funcao
                FROM Funcionarios f
                INNER JOIN Pessoas p on f.cpf = p.cpf
                WHERE f.cpf = ?
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf_desejado);

            try (ResultSet rs = stmt.executeQuery()) {

                if(rs.next()) {
                    Funcionario funcionarioAchado = new Funcionario(rs.getString("cpf"),
                    rs.getString("nome"), 
                    rs.getString("telefone"), 
                    rs.getString("email"), 
                    rs.getDouble("salario"), 
                    rs.getString("funcao"));

                    return Optional.of(funcionarioAchado);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar funcionario: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    public void demitir (String cpf) {
        String sql = "DELETE FROM Pessoas WHERE cpf = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao demitir funcionario: " + e.getMessage(), e);
        }
    }
}