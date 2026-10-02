import java.util.ArrayList;
import java.util.List;

/** Feature: excluir pedido aberto, somente pelo funcionário que o criou. */
public class ExcluirPedido {

    private ExcluirPedido() {
    }

    /** Pedidos que o usuário pode excluir: abertos e criados por ele. */
    public static List<Pedido> pedidosExcluiveis(List<Pedido> pedidos, Usuario usuario) {
        List<Pedido> r = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.isAberto() && p.getSolicitante().getId() == usuario.getId()) {
                r.add(p);
            }
        }
        return r;
    }

    /**
     * Exclui o pedido do sistema. Lança IllegalStateException se o usuário não for
     * o criador do pedido ou se o pedido não estiver mais aberto.
     */
    public static void excluir(Sistema sistema, Usuario usuario, Pedido pedido) {
        if (usuario == null || pedido == null) {
            throw new IllegalStateException("Usuário ou pedido inválido.");
        }
        if (pedido.getSolicitante().getId() != usuario.getId()) {
            throw new IllegalStateException("Somente quem criou o pedido pode excluí-lo.");
        }
        if (!pedido.isAberto()) {
            throw new IllegalStateException(
                    "Só é possível excluir pedidos abertos. Este pedido está " + pedido.getStatus() + ".");
        }
        if (!sistema.removerPedido(pedido)) {
            throw new IllegalStateException("Pedido não encontrado no sistema.");
        }
    }

    /** Fluxo interativo (chamado pelo menu do Main). */
    public static void executar(Sistema sistema) {
        Usuario usuario = sistema.getUsuarioAtual();
        List<Pedido> meus = pedidosExcluiveis(sistema.getPedidos(), usuario);

        System.out.println();
        System.out.println("=== Excluir pedido ===");
        if (meus.isEmpty()) {
            System.out.println("Você não tem pedidos abertos para excluir.");
            return;
        }
        System.out.println("Seus pedidos abertos:");
        Main.mostrarPedidos(meus);

        int id = Main.lerInt("Número do pedido a excluir (0 para voltar): ");
        if (id == 0) {
            return;
        }
        Pedido pedido = ConsultasAdmin.porId(sistema.getPedidos(), id);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
        // Valida antes de pedir confirmação, para dar a mensagem certa
        if (pedido.getSolicitante().getId() != usuario.getId()) {
            System.out.println("Somente quem criou o pedido pode excluí-lo.");
            return;
        }
        if (!pedido.isAberto()) {
            System.out.println("Só é possível excluir pedidos abertos. Este pedido está "
                    + pedido.getStatus() + ".");
            return;
        }

        System.out.println(ConsultasAdmin.detalhes(pedido));
        if (!confirmar("Confirma a exclusão do pedido #" + pedido.getId() + "?")) {
            System.out.println("Exclusão cancelada. Nada foi alterado.");
            return;
        }
        try {
            excluir(sistema, usuario, pedido);
            System.out.println("Pedido #" + pedido.getId() + " excluído com sucesso.");
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    private static boolean confirmar(String pergunta) {
        while (true) {
            String r = Main.lerTexto(pergunta + " (s/n): ").toLowerCase();
            if (r.equals("s")) return true;
            if (r.equals("n")) return false;
            System.out.println("Responda com 's' ou 'n'.");
        }
    }
}
