package pedidos.service;

public class CorreoService {

    public void enviarConfirmacionPedido(String emailCliente, String nombreCliente, double total) {
        System.out.println("Enviando correo a " + emailCliente + "...");
        System.out.println("Asunto: Confirmacion de pedido");
        System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido por $" + total + " ha sido procesado.");
    }

    public void enviarCancelacionPedido(String emailCliente, String nombreCliente, int idPedido) {
        System.out.println("Enviando correo a " + emailCliente + "...");
        System.out.println("Asunto: Cancelacion de pedido");
        System.out.println("Cuerpo: Estimado " + nombreCliente + ", su pedido #" + idPedido + " ha sido cancelado.");
    }

}
