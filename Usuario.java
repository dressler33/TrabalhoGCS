public class Usuario {
    public enum Tipo { FUNCIONARIO, ADMINISTRADOR }

    private final int id;
    private final String nome;
    private final Tipo tipo;
    private final Departamento departamento;

    public Usuario(int id, String nome, Tipo tipo, Departamento departamento) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.departamento = departamento;
    }

    public int getId() {
        return id; }
    public String getNome() {
        return nome; }
    public Tipo getTipo() {
        return tipo; }
    public Departamento getDepartamento() {
        return departamento; }
    public boolean isAdministrador() {
        return tipo == Tipo.ADMINISTRADOR; }

    public String getIniciais() {
        StringBuilder sb = new StringBuilder();
        for (String parte : nome.trim().split("\\s+")) {
            if (!parte.isEmpty()) sb.append(Character.toUpperCase(parte.charAt(0)));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return id + " - " + nome + " [" + getIniciais() + "] (" + tipo + ", " + departamento.getNome() + ")";
    }
}