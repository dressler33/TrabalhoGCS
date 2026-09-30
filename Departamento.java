public class Departamento {
    private final String nome;
    private final double limitePorPedido;

    public Departamento(String nome, double limitePorPedido) {
        this.nome = nome;
        this.limitePorPedido = limitePorPedido;
    }

    public String getNome() {
         return nome; }
    public double getLimitePorPedido() {
         return limitePorPedido; }

    @Override
    public String toString() {
        return nome + " (limite por pedido: R$ " + String.format("%.2f", limitePorPedido) + ")";
    }
}