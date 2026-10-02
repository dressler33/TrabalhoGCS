import java.util.ArrayList;
import java.util.List;

public class AvaliacaoPedido {

    private AvaliacaoPedido() {
    }

    public static List<Pedido> pedidosAbertos(List<Pedido> pedidos) {
        List<Pedido> abertos = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.isAberto()) {
                abertos.add(p);
            }
        }
        return abertos;
    }

    public static void avaliar(Usuario avaliador, Pedido pedido, boolean aprovar) {
        if (avaliador == null || !avaliador.isAdministrador()) {
            throw new IllegalStateException("Somente administradores podem avaliar pedidos.");
        }
        if (aprovar) {
            pedido.aprovar();
        } else {
            pedido.reprovar();
        }
    }
}