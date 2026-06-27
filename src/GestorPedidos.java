import java.util.*;
import java.sql.*;

public class GestorPedidos {

    private FacturaService facturaService = new FacturaService();

    private ValidadorCliente validadorCliente = new ValidadorCliente();

    private CorreoService correoService = new CorreoService();

    private CalculadorDescuento calculadorDescuento =
            new CalculadorDescuento(Arrays.asList(
                    new DescuentoVip(),
                    new DescuentoFrecuente(),
                    new DescuentoRegular(),
                    new DescuentoNuevo()
            ));

    private ConexionBD conexionBDService = new ConexionBD();

    private PedidoRepository pedidoRepository;

    private Connection conexionBD;


    public GestorPedidos() {
        this.conexionBD = conexionBDService.obtenerConexion();
        this.pedidoRepository = new PedidoRepository(conexionBD);
    }

    public void procesarPedido(String nombreCliente, String emailCliente, List<String> nombresProductos, List<Double> preciosProductos, List<Integer> cantidades, String tipoCliente) {
        if (!validadorCliente.validar(nombreCliente, emailCliente)) {
            return;
        }
        double subtotal = 0;
        for (int i = 0; i < nombresProductos.size(); i++) {
            subtotal += preciosProductos.get(i) * cantidades.get(i);
        }

        double descuento = calculadorDescuento.calcular(tipoCliente, subtotal);

        double impuesto = (subtotal - descuento) * 0.12;
        double total = subtotal - descuento + impuesto;

        pedidoRepository.guardarPedido(nombreCliente, total);

        facturaService.generarFactura(nombreCliente, nombresProductos, preciosProductos, cantidades, subtotal, descuento, impuesto, total);

        correoService.enviarConfirmacionPedido(emailCliente, nombreCliente, total);
        System.out.println("[LOG] Pedido procesado para " + nombreCliente + " - Total: " + total);
    }

    public void cancelarPedido(String nombreCliente, String emailCliente, int idPedido) {
        if (!validadorCliente.validar(nombreCliente, emailCliente)) {
            return;
        }
        pedidoRepository.cancelarPedido(idPedido);

        correoService.enviarCancelacionPedido(emailCliente, nombreCliente, idPedido);
    }
}