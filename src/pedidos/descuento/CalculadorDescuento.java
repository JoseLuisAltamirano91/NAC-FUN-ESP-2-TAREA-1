package pedidos.descuento;

import java.util.List;

public class CalculadorDescuento {

    private List<EstrategiaDescuento> estrategias;

    public CalculadorDescuento(List<EstrategiaDescuento> estrategias) {
        this.estrategias = estrategias;
    }

    public double calcular(String tipoCliente, double subtotal) {
        for (EstrategiaDescuento estrategia : estrategias) {
            if (estrategia.aplica(tipoCliente)) {
                return estrategia.calcular(subtotal);
            }
        }
        return 0;
    }
}
