package db_management;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;

public class DB_initializer {

    public static void inicializar() {
        Path path = Paths.get("src/main/db_management/init.sql");

        try (Connection conn = new ConnectionFactory().recuperarConexao();
            Statement stmt = conn.createStatement()) { //tentando se conectar e preparar o BD
                String sql = Files.readString(path);
                stmt.execute(sql);
                System.out.println("Banco de Dados incializado com sucesso!");

            } catch (Exception e) { // se o BD já existe ele vai retornar erro ao tentar criar a mesma Table dnv
                System.out.println("Erro ao inicializar banco (provavelmente já existe, por favor checar o que foi retornado): " + e.getMessage());
            }
    }
}