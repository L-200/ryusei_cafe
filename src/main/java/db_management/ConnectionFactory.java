package db_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection recuperarConexao() {
        try {
            return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/ryusei_cafe", 
                "ryusei", 
                "ryusei"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco: " + e.getMessage());
        }
    }
}