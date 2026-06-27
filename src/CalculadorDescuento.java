

public class CalculadorDescuento {
    public double calcular(String tipoCliente, double subtotal) {
        switch (tipoCliente) {
            case "VIP":
                return subtotal * 0.20;
            case "FRECUENTE":
                return subtotal * 0.10;
            case "REGULAR":
                return subtotal * 0.05;
            case "NUEVO":
                return 0;
            default:
                return 0;
        }
    }
}
