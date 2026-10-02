import java.time.LocalDate;
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

    /** Pedidos aprovados que ainda não foram concluídos (itens ainda não entregues). */
    public static List<Pedido> pedidosParaConcluir(List<Pedido> pedidos) {
        List<Pedido> r = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getStatus() == StatusPedido.APROVADO && p.getDataConclusao() == null) {
                r.add(p);
            }
        }
        return r;
    }

    /**
     * Conclui o pedido (itens entregues). Somente administrador, somente pedido aprovado
     * e ainda não concluído. A data não pode ser anterior à data do pedido nem futura.
     */
    public static void concluir(Usuario avaliador, Pedido pedido, LocalDate data) {
        if (avaliador == null || !avaliador.isAdministrador()) {
            throw new IllegalStateException("Somente administradores podem concluir pedidos.");
        }
        if (data.isBefore(pedido.getDataPedido())) {
            throw new IllegalArgumentException("A data de conclusão não pode ser anterior à data do pedido.");
        }
        if (data.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("A data de conclusão não pode estar no futuro.");
        }
        pedido.concluir(data);
    }
}