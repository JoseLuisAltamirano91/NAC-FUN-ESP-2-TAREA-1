package pedidos.descuento;

public class DescuentoRegular implements EstrategiaDescuento {
    @Override
    public boolean aplica(String tipoCliente) {
        return "REGULAR".equals(tipoCliente);
    }

    @Override
    public double calcular(Double subtotal) {
        return subtotal * 0.05;
    }
}
