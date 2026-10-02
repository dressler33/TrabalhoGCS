import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class Pedido {
    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final int id;
    private final Usuario solicitante;
    private final Departamento departamento;
    private final LocalDate dataPedido;
    private LocalDate dataConclusao;
    private StatusPedido status = StatusPedido.ABERTO;
    private final List<Item> itens = new ArrayList<>();

    /** O departamento do pedido é sempre o do solicitante no momento do cadastro. */
    public Pedido(int id, Usuario solicitante, LocalDate dataPedido) {
        this.id = id;
        this.solicitante = solicitante;
        this.departamento = solicitante.getDepartamento();
        this.dataPedido = dataPedido;
    }

    public int getId() { return id; }
    public Usuario getSolicitante() { return solicitante; }
    public Departamento getDepartamento() { return departamento; }
    public LocalDate getDataPedido() { return dataPedido; }
    public LocalDate getDataConclusao() { return dataConclusao; }
    public StatusPedido getStatus() { return status; }

    public List<Item> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public boolean isAberto() {
        return status == StatusPedido.ABERTO;
    }

    /**
     * Adiciona o item SEM validar o limite do departamento
     * (essa validação é feita ao registrar o pedido).
     */
    public void adicionarItem(Item item) {
        exigirAberto();
        itens.add(item);
    }

    public double getValorTotal() {
        double total = 0;
        for (Item item : itens) {
            total += item.getTotal();
        }
        return total;
    }

    // ---- Regras de status: pedido só muda se estiver ABERTO (nunca reabre) ----

    public void aprovar() {
        exigirAberto();
        status = StatusPedido.APROVADO;
    }

    public void reprovar() {
        exigirAberto();
        status = StatusPedido.REPROVADO;
    }

    public void concluir(LocalDate data) {
        if (status != StatusPedido.APROVADO) {
            throw new IllegalStateException("Somente pedidos aprovados podem ser concluídos.");
        }
        if (dataConclusao != null) {
            throw new IllegalStateException("Pedido já concluído.");
        }
        dataConclusao = data;
    }

    private void exigirAberto() {
        if (status != StatusPedido.ABERTO) {
            throw new IllegalStateException("Pedido #" + id + " já está " + status + " e não pode ser alterado.");
        }
    }

    @Override
    public String toString() {
        return String.format(new Locale("pt", "BR"),
                "Pedido #%d | %s (%s) | %s | %s | %s | %d item(ns) | total R$ %,.2f",
                id, solicitante.getNome(), departamento.getNome(),
                dataPedido.format(DATA),
                status,
                dataConclusao == null ? "não concluído" : "concluído em " + dataConclusao.format(DATA),
                itens.size(), getValorTotal());
    }
}
