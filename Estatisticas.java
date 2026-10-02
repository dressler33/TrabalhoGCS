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

        private boolean nosUltimos30Dias(Pedido p) {
        return !p.getDataPedido().isBefore(hoje.minusDays(30))
                && !p.getDataPedido().isAfter(hoje);
    }

    public int pedidosUltimos30Dias() {
        int n = 0;
        for (Pedido p : pedidos) {
            if (nosUltimos30Dias(p)) n++;
        }
        return n;
    }

    public double valorMedioUltimos30Dias() {
        double soma = 0;
        int n = 0;
        for (Pedido p : pedidos) {
            if (nosUltimos30Dias(p)) {
                soma += p.getValorTotal();
                n++;
            }
        }
        return n == 0 ? 0 : soma / n;
    }

        public Pedido maiorPedidoAberto() {
        Pedido maior = null;
        for (Pedido p : pedidos) {
            if (p.isAberto() && (maior == null || p.getValorTotal() > maior.getValorTotal())) {
                maior = p;
            }
        }
        return maior;
    }

    public String relatorio() {
        StringBuilder sb = new StringBuilder();
        sb.append("Total de pedidos: ").append(total()).append("\n");
        sb.append(String.format("Aprovados: %d (%.1f%%)%n",
                contar(StatusPedido.APROVADO), percentual(StatusPedido.APROVADO)));
        sb.append(String.format("Reprovados: %d (%.1f%%)%n",
                contar(StatusPedido.REPROVADO), percentual(StatusPedido.REPROVADO)));
        sb.append(String.format("Ultimos 30 dias: %d pedido(s), valor medio R$ %.2f%n",
                pedidosUltimos30Dias(), valorMedioUltimos30Dias()));

        Pedido maior = maiorPedidoAberto();
        sb.append("Maior pedido ainda aberto:\n");
        if (maior == null) {
            sb.append("  (nenhum pedido aberto)");
        } else {
            sb.append("  ").append(maior).append("\n");
            for (Item item : maior.getItens()) {
                sb.append("    - ").append(item).append("\n");
            }
        }
        return sb.toString();
    }
}