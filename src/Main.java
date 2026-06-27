import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        Connection conexion = conexionBD.obtenerConexion();

        PedidoRepository pedidoRepository = new PedidoRepository(conexion);
        FacturaService facturaService = new FacturaService();
        ValidadorCliente validadorCliente = new ValidadorCliente();
        CorreoService correoService = new CorreoService();

        List<EstrategiaDescuento> estrategias = Arrays.asList(
                new DescuentoVip(),
                new DescuentoFrecuente(),
                new DescuentoRegular(),
                new DescuentoNuevo()
        );

        CalculadorDescuento calculadorDescuento = new CalculadorDescuento(estrategias);

        GestorPedidos gestor = new GestorPedidos(
                facturaService,
                validadorCliente,
                correoService,
                calculadorDescuento,
                pedidoRepository
        );

        gestor.procesarPedido(
                "Jose",
                "jose@gmail.com",
                Arrays.asList("Laptop", "Mouse"),
                Arrays.asList(1000.0, 20.0),
                Arrays.asList(1, 2),
                "VIP"
        );
    }
}