public class DescuentoNuevo implements EstrategiaDescuento{
    @Override
    public boolean aplica(String tipoCliente) {
        return "NUEVO".equals(tipoCliente);
    }

    @Override
    public double calcular(Double subtotal) {
        return 0;
    }
}
