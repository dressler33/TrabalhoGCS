import java.time.LocalDate;
import java.util.List;

public class Estatisticas {
    private final List<Pedido> pedidos;
    private final LocalDate hoje;

    public Estatisticas(List<Pedido> pedidos) {
        this(pedidos, LocalDate.now());
    }

    public Estatisticas(List<Pedido> pedidos, LocalDate hoje) {
        this.pedidos = pedidos;
        this.hoje = hoje;
    }

    public int total() {
        return pedidos.size();
    }

    public int contar(StatusPedido status) {
        int n = 0;
        for (Pedido p : pedidos) {
            if (p.getStatus() == status) n++;
        }
        return n;
    }

    public double percentual(StatusPedido status) {
        if (pedidos.isEmpty()) return 0;
        return 100.0 * contar(status) / pedidos.size();
    }
}