import java.time.LocalDate;
import java.util.List;

/**
 * Pedidos de exemplo para facilitar os testes. Os solicitantes vêm dos usuários já
 * cadastrados e os valores são calculados a partir do limite de cada departamento
 * (nunca ultrapassam o limite). Chamado pelo Sistema depois de criar os usuários.
 */
public class PedidosIniciais {

    private PedidosIniciais() {
    }

    private static final String[][] CATALOGO = {
            {"Notebook", "Mouse sem fio"},
            {"Cadeira ergonomica", "Apoio de pes"},
            {"Monitor 24 polegadas", "Cabo HDMI"},
            {"Resma de papel A4", "Caneta esferografica"},
            {"Toner para impressora", "Pen drive 64GB"},
            {"Furadeira", "Jogo de brocas"},
            {"Teclado mecanico", "Webcam"},
            {"Projetor", "Tela de projecao"},
            {"Cafe (pacote 1kg)", "Copos descartaveis"},
            {"Livro tecnico", "Licenca de software"}
    };

    // dias atrás em que cada pedido foi feito (mistura de recentes e antigos)
    private static final int[] DIAS_ATRAS = {1, 3, 7, 12, 18, 25, 40, 55, 70, 2};

    // 0 = aberto, 1 = aprovado, 2 = aprovado e concluído, 3 = reprovado
    private static final int[] SITUACAO = {0, 0, 0, 0, 1, 1, 2, 3, 3, 0};

    public static void carregar(Sistema sistema) {
        List<Usuario> usuarios = sistema.getUsuarios();
        if (usuarios.isEmpty()) {
            return;
        }

        for (int i = 0; i < CATALOGO.length; i++) {
            Usuario solicitante = usuarios.get((i * 3) % usuarios.size());
            double limite = solicitante.getDepartamento().getLimitePorPedido();

            int qtd1 = 1 + (i % 4);
            int qtd2 = 1 + ((i + 1) % 3);
            // item 1 ocupa ~20% do limite e item 2 ~10%: total ~30%, sempre dentro do limite
            double valor1 = arredondar(limite * 0.20 / qtd1);
            double valor2 = arredondar(limite * 0.10 / qtd2);

            Pedido p = new Pedido(sistema.proximoIdPedido(), solicitante,
                    LocalDate.now().minusDays(DIAS_ATRAS[i]));
            p.adicionarItem(new Item(CATALOGO[i][0], valor1, qtd1));
            p.adicionarItem(new Item(CATALOGO[i][1], valor2, qtd2));

            switch (SITUACAO[i]) {
                case 1:
                    p.aprovar();
                    break;
                case 2:
                    p.aprovar();
                    LocalDate conclusao = p.getDataPedido().plusDays(5);
                    if (conclusao.isAfter(LocalDate.now())) {
                        conclusao = LocalDate.now();
                    }
                    p.concluir(conclusao);
                    break;
                case 3:
                    p.reprovar();
                    break;
                default:
                    break; // fica aberto
            }
            sistema.adicionarPedido(p);
        }
    }

    private static double arredondar(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
