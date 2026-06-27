package pedidos.descuento;

public class DescuentoVip implements EstrategiaDescuento {

    @Override
    public boolean aplica(String tipoCliente) {
        return "VIP".equals(tipoCliente);
    }

    @Override
    public double calcular(Double subtotal) {
        return subtotal * 0.20;
    }
}
