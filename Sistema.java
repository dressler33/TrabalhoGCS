import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Sistema {
    private final List<Departamento> departamentos = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private int proximoIdPedido = 1;
    private Usuario usuarioAtual;

    public Sistema() {
        carregarDadosIniciais();
    }

    public Usuario getUsuarioAtual() { return usuarioAtual; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Departamento> getDepartamentos() { return departamentos; }

    // ---- Pedidos ----
    public List<Pedido> getPedidos() { return Collections.unmodifiableList(pedidos); }
    public int proximoIdPedido() { return proximoIdPedido++; }
    public void adicionarPedido(Pedido p) { pedidos.add(p); }
    public boolean removerPedido(Pedido p) { return pedidos.remove(p); }

    public boolean trocarUsuario(int id) {
        for (Usuario u : usuarios) {
            if (u.getId() == id) {
                usuarioAtual = u;
                return true;
            }
        }
        return false;
    }

    private void carregarDadosIniciais() {
        Departamento fin = new Departamento("Financeiro", 5000);
        Departamento rh = new Departamento("RH", 2000);
        Departamento eng = new Departamento("Engenharia", 20000);
        Departamento man = new Departamento("Manutencao", 8000);
        Departamento ti = new Departamento("TI", 15000);
        departamentos.add(fin);
        departamentos.add(rh);
        departamentos.add(eng);
        departamentos.add(man);
        departamentos.add(ti);

        Usuario.Tipo F = Usuario.Tipo.FUNCIONARIO;
        Usuario.Tipo A = Usuario.Tipo.ADMINISTRADOR;
        usuarios.add(new Usuario(1, "Ana Paula Souza", A, fin));
        usuarios.add(new Usuario(2, "Bruno Lima", F, fin));
        usuarios.add(new Usuario(3, "Carla Mendes", F, fin));
        usuarios.add(new Usuario(4, "Diego Ferreira", A, rh));
        usuarios.add(new Usuario(5, "Elisa Moraes", F, rh));
        usuarios.add(new Usuario(6, "Fabio Rocha", F, rh));
        usuarios.add(new Usuario(7, "Gabriela Nunes", F, eng));
        usuarios.add(new Usuario(8, "Henrique Alves", F, eng));
        usuarios.add(new Usuario(9, "Isabela Costa", A, eng));
        usuarios.add(new Usuario(10, "Joao Pedro Silva", F, man));
        usuarios.add(new Usuario(11, "Karen Dias", F, man));
        usuarios.add(new Usuario(12, "Lucas Barbosa", F, man));
        usuarios.add(new Usuario(13, "Marina Teixeira", F, ti));
        usuarios.add(new Usuario(14, "Nicolas Prado", F, ti));
        usuarios.add(new Usuario(15, "Olivia Ribeiro", F, ti));
        usuarios.add(new Usuario(16, "Paulo Henrique Gomes", A, ti));
        usuarioAtual = usuarios.get(0);
        PedidosIniciais.carregar(this);
    }
}