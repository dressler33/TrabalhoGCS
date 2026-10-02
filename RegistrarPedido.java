import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Feature: registrar pedido de aquisição validando o limite do departamento. */
public class RegistrarPedido {

    private RegistrarPedido() {
    }

    private static String moeda(double valor) {
        return String.format(new Locale("pt", "BR"), "R$ %,.2f", valor);
    }

    /**
     * Valida e cria um item. Lança IllegalArgumentException se: descrição vazia,
     * valor <= 0, quantidade <= 0 ou se o total do pedido passar do limite do departamento.
     */
    public static Item criarItemValidado(Departamento departamento, double totalAtual,
                                         String descricao, double valorUnitario, int quantidade) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição do item não pode ser vazia.");
        }
        if (valorUnitario <= 0) {
            throw new IllegalArgumentException("O valor unitário deve ser maior que zero.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        Item item = new Item(descricao.trim(), valorUnitario, quantidade);
        double novoTotal = totalAtual + item.getTotal();
        if (novoTotal > departamento.getLimitePorPedido()) {
            throw new IllegalArgumentException(String.format(
                    "Item recusado: o total do pedido passaria a %s, acima do limite de %s do departamento %s.",
                    moeda(novoTotal), moeda(departamento.getLimitePorPedido()), departamento.getNome()));
        }
        return item;
    }

    /** Cria o pedido (status ABERTO, data de hoje) e adiciona ao sistema. */
    public static Pedido registrar(Sistema sistema, Usuario solicitante, List<Item> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("O pedido precisa ter pelo menos um item.");
        }
        Pedido pedido = new Pedido(sistema.proximoIdPedido(), solicitante, LocalDate.now());
        for (Item item : itens) {
            pedido.adicionarItem(item);
        }
        // Segurança extra: confere o limite com o total final
        if (pedido.getValorTotal() > pedido.getDepartamento().getLimitePorPedido()) {
            throw new IllegalArgumentException("O total do pedido excede o limite do departamento.");
        }
        sistema.adicionarPedido(pedido);
        return pedido;
    }

    /** Fluxo interativo (chamado pelo menu do Main). */
    public static void executar(Sistema sistema) {
        Usuario usuario = sistema.getUsuarioAtual();
        Departamento dep = usuario.getDepartamento();

        System.out.println();
        System.out.println("=== Novo pedido de aquisição ===");
        System.out.println("Solicitante: " + usuario.getNome() + " | Departamento: " + dep.getNome());
        System.out.println("Limite por pedido: " + moeda(dep.getLimitePorPedido()));

        List<Item> itens = new ArrayList<>();
        double total = 0;

        boolean continuar = true;
        while (continuar) {
            String descricao = Main.lerTexto("Descrição do item: ");
            double valor = Main.lerDouble("Valor unitário: ");
            int qtd = Main.lerInt("Quantidade: ");

            try {
                Item item = criarItemValidado(dep, total, descricao, valor, qtd);
                itens.add(item);
                total += item.getTotal();
                System.out.println("Item adicionado: " + item);
                System.out.println("Total parcial: " + moeda(total)
                        + " (restam " + moeda(dep.getLimitePorPedido() - total) + ")");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }

            continuar = perguntarSimNao("Adicionar outro item?");
        }

        if (itens.isEmpty()) {
            System.out.println("Nenhum item válido informado. Pedido cancelado.");
            return;
        }

        Pedido pedido = registrar(sistema, usuario, itens);
        System.out.println();
        System.out.println("Pedido registrado com sucesso!");
        System.out.println(pedido);
        for (Item item : pedido.getItens()) {
            System.out.println("  - " + item);
        }
    }

    private static boolean perguntarSimNao(String pergunta) {
        while (true) {
            String r = Main.lerTexto(pergunta + " (s/n): ").toLowerCase();
            if (r.equals("s")) return true;
            if (r.equals("n")) return false;
            System.out.println("Responda com 's' ou 'n'.");
        }
    }
}
