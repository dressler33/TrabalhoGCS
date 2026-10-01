import java.util.Locale;

public class Item {
    private final String descricao;
    private final double valorUnitario;
    private final int quantidade;

    public Item(String descricao, double valorUnitario, int quantidade) {
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public String getDescricao() { return descricao; }
    public double getValorUnitario() { return valorUnitario; }
    public int getQuantidade() { return quantidade; }

    public double getTotal() {
        return valorUnitario * quantidade;
    }

    @Override
    public String toString() {
        Locale br = new Locale("pt", "BR");
        return String.format(br, "%s | %d x R$ %,.2f = R$ %,.2f",
                descricao, quantidade, valorUnitario, getTotal());
    }
}
 