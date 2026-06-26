import java.util.*;

public class Main {

    public static void main(String[] args) {

        GestorPedidos gestor = new GestorPedidos();

        List<String> productos = Arrays.asList("Laptop", "Mouse");
        List<Double> precios = Arrays.asList(1000.0, 20.0);
        List<Integer> cantidades = Arrays.asList(1, 2);

        gestor.procesarPedido(
                "Jose",
                "jose@gmail.com",
                productos,
                precios,
                cantidades,
                "VIP"
        );
    }
}