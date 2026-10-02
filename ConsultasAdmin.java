import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConsultasAdmin {

    private ConsultasAdmin() {
    }
    public static List<Pedido> entreDatas(List<Pedido> pedidos, LocalDate inicio, LocalDate fim) {
        List<Pedido> r = new ArrayList<>();
        for (Pedido p : pedidos) {
            LocalDate d = p.getDataPedido();
            if (!d.isBefore(inicio) && !d.isAfter(fim)) {
                r.add(p);
            }
        }
        return r;
    }

     public static List<Pedido> porFuncionario(List<Pedido> pedidos, String nome) {
        String busca = normalizar(nome);
        List<Pedido> r = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (normalizar(p.getSolicitante().getNome()).contains(busca)) {
                r.add(p);
            }
        }
        return r;
    }

    public static List<Pedido> porItem(List<Pedido> pedidos, String descricao) {
        String busca = normalizar(descricao);
        List<Pedido> r = new ArrayList<>();
        for (Pedido p : pedidos) {
            for (Item item : p.getItens()) {
                if (normalizar(item.getDescricao()).contains(busca)) {
                    r.add(p);
                    break;
                }
            }
        }
        return r;
    }

    public static Pedido porId(List<Pedido> pedidos, int id) {
        for (Pedido p : pedidos) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }
}