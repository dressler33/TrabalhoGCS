public enum StatusPedido {
    ABERTO("Aberto"),
    APROVADO("Aprovado"),
    REPROVADO("Reprovado");

    private final String rotulo;

    StatusPedido(String rotulo) {
        this.rotulo = rotulo;
    }

    @Override
    public String toString() {
        return rotulo;
    }
}
 