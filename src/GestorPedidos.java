import java.util.*;
import java.sql.*;

public class GestorPedidos {

    private final FacturaService facturaService;
    private final ValidadorCliente validadorCliente;
    private final CorreoService correoService;
    private final CalculadorDescuento calculadorDescuento;
    private final PedidoRepository pedidoRepository;

    public GestorPedidos(FacturaService facturaService,
                         ValidadorCliente validadorCliente,
                         CorreoService correoService,
                         CalculadorDescuento calculadorDescuento,
                         PedidoRepository pedidoRepository) {
        this.facturaService = facturaService;
        this.validadorCliente = validadorCliente;
        this.correoService = correoService;
        this.calculadorDescuento = calculadorDescuento;
        this.pedidoRepository = pedidoRepository;
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