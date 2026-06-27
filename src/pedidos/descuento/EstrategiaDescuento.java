package pedidos.descuento;

public interface EstrategiaDescuento {
    boolean aplica(String tipoCliente);
    double calcular(Double subtotal);
}
