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
}