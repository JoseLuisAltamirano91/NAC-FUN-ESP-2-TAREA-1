package pedidos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public Connection obtenerConexion() {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tienda",
                    "postgres",
                    "admin"
            );
        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }
}
