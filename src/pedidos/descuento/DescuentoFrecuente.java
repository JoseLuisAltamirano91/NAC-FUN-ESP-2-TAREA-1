package pedidos.descuento;

public class DescuentoFrecuente implements EstrategiaDescuento {

    @Override
    public boolean aplica(String tipoCliente) {
        return "FRECUENTE".equals(tipoCliente);
    }

    @Override
    public double calcular(Double subtotal) {
        return subtotal * 0.10;
    }
}
