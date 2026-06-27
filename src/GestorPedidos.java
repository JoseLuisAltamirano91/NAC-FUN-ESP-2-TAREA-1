import java.util.*;
import java.io.*;
import java.sql.*;

public class GestorPedidos {

    private FacturaService facturaService = new FacturaService();

    private ValidadorCliente validadorCliente = new ValidadorCliente();

    private PedidoRepository pedidoRepository;

    private Connection conexionBD;


    public GestorPedidos() {
        try {
           // this.conexionBD = DriverManager.getConnection("jdbc:mysql://localhost:3306/tienda", "root", "admin123");
            this.conexionBD = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/tienda",
                    "postgres",
                    "admin");
            this.pedidoRepository = new PedidoRepository(conexionBD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void procesarPedido(String nombreCliente, String emailCliente, List<String> nombresProductos, List<Double> preciosProductos, List<Integer> cantidades, String tipoCliente) {
        if (!validadorCliente.validar(nombreCliente, emailCliente)) {
            return;
        }
        double subtotal = 0;
        for (int i = 0; i < nombresProductos.size(); i++) {
            subtotal += preciosProductos.get(i) * cantidades.get(i);
        }
        double descuento = 0;
        if (tipoCliente.equals("VIP")) {
            descuento = subtotal * 0.20;
        } else if (tipoCliente.equals("FRECUENTE")) {
            descuento = subtotal * 0.10;
        } else if (tipoCliente.equals("REGULAR")) {
            descuento = subtotal * 0.05;
        } else if (tipoCliente.equals("NUEVO")) {
            descuento = 0;
        }
        double impuesto = (subtotal - descuento) * 0.12;
        double total = subtotal - descuento + impuesto;

        pedidoRepository.guardarPedido(nombreCliente, total);

        facturaService.generarFactura(nombreCliente, nombresProductos, preciosProductos, cantidades, subtotal, descuento, impuesto, total);

        System.out.println("Enviando correo a " + emailCliente + "...");
        System.out.println("Asunto: Confirmacion de pedido");
        System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido por $" + total + " ha sido procesado.");
        System.out.println("[LOG] Pedido procesado para " + nombreCliente + " - Total: " + total);
    }

    public void cancelarPedido(String nombreCliente, String emailCliente, int idPedido) {
        if (!validadorCliente.validar(nombreCliente, emailCliente)) {
            return;
        }
        pedidoRepository.cancelarPedido(idPedido);
        System.out.println("Enviando correo a " + emailCliente + "...");
        System.out.println("Asunto: Cancelacion de pedido");
        System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido #" + idPedido + " ha sido cancelado.");
    }
}