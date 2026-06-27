import java.sql.*;

public class PedidoRepository {
    private Connection conexionBD;

    public PedidoRepository(Connection conexionBD) {
        this.conexionBD = conexionBD;
    }

    public void guardarPedido(String nombreCliente, double total) {
        try {
            Statement stmt = conexionBD.createStatement();
            String sql = "INSERT INTO pedidos (cliente, total) VALUES ('" + nombreCliente + "', " + total + ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al guardar el pedido: " + e.getMessage());
        }
    }

    public void cancelarPedido(int idPedido) {
        try {
            Statement stmt = conexionBD.createStatement();
            String sql = "DELETE FROM pedidos WHERE id = " + idPedido;
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error al cancelar el pedido: " + e.getMessage());
        }
    }
}
