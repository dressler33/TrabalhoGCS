import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);

    // ---------- Leitura segura ----------

    /** Lê uma linha de texto não vazia. */
    static String lerTexto(String pergunta) {
        while (true) {
            System.out.print(pergunta);
            if (!in.hasNextLine()) {
                throw new IllegalStateException("Entrada encerrada");
            }
            String s = in.nextLine().trim();
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("Entrada vazia. Tente novamente.");
        }
    }

    /** Lê um inteiro; repete a pergunta se a entrada for inválida. */
    static int lerInt(String pergunta) {
        while (true) {
            String s = lerTexto(pergunta);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido: digite um número inteiro.");
            }
        }
    }

    /** Lê um número decimal (aceita vírgula ou ponto); repete se for inválido. */
    static double lerDouble(String pergunta) {
        while (true) {
            String s = lerTexto(pergunta).replace(',', '.');
            try {
                double v = Double.parseDouble(s);
                if (Double.isNaN(v) || Double.isInfinite(v)) {
                    throw new NumberFormatException();
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido: digite um número (ex.: 12,50).");
            }
        }
    }

    /** Lê uma data no formato dd/mm/aaaa; repete a pergunta se for inválida. */
    static LocalDate lerData(String pergunta) {
        while (true) {
            String s = lerTexto(pergunta + " (dd/mm/aaaa): ");
            try {
                String[] p = s.split("/");
                if (p.length != 3) {
                    throw new NumberFormatException();
                }
                return LocalDate.of(Integer.parseInt(p[2].trim()),
                        Integer.parseInt(p[1].trim()),
                        Integer.parseInt(p[0].trim()));
            } catch (NumberFormatException e) {
                System.out.println("Data inválida. Use o formato dd/mm/aaaa.");
            } catch (DateTimeException e) {
                System.out.println("Essa data não existe. Tente novamente.");
            }
        }
    }

    static void mostrarMenu(Sistema sistema) {
        System.out.println();
        System.out.println("=== Controle de Aquisições ===");
        System.out.println("Operador atual: " + sistema.getUsuarioAtual());
        System.out.println("1 - Trocar de usuário");
        System.out.println("2 - Listar usuários");
        System.out.println("3 - Listar departamentos");
        System.out.println("9 - Registrar novo pedido");
        System.out.println("10 - Excluir pedido (somente os seus, abertos)");
        if (sistema.getUsuarioAtual().isAdministrador()) {
            System.out.println("--- Administrador ---");
            System.out.println("4 - Avaliar pedido (aprovar/reprovar)");
            System.out.println("5 - Listar pedidos entre duas datas");
            System.out.println("6 - Buscar pedidos por funcionário");
            System.out.println("7 - Buscar pedidos por item");
            System.out.println("8 - Ver detalhes de um pedido");
            System.out.println("11 - Estatísticas gerais");
        }
        System.out.println("0 - Sair");
    }

    static boolean exigirAdmin(Sistema sistema) {
        if (!sistema.getUsuarioAtual().isAdministrador()) {
            System.out.println("Opção restrita a administradores.");
            return false;
        }
        return true;
    }

    static void listarUsuarios(Sistema sistema) {
        for (Usuario u : sistema.getUsuarios()) {
            System.out.println(u);
        }
    }

    static void listarDepartamentos(Sistema sistema) {
        for (Departamento d : sistema.getDepartamentos()) {
            System.out.println(d);
        }
    }

    static void trocarUsuario(Sistema sistema) {
        listarUsuarios(sistema);
        int id = lerInt("Digite o id do usuário: ");
        if (sistema.trocarUsuario(id)) {
            System.out.println("Usuário alterado para: " + sistema.getUsuarioAtual());
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    static void mostrarPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println(p);
        }
    }

    static void avaliarPedido(Sistema sistema) {
        List<Pedido> abertos = AvaliacaoPedido.pedidosAbertos(sistema.getPedidos());
        if (abertos.isEmpty()) {
            System.out.println("Não há pedidos abertos para avaliar.");
            return;
        }
        mostrarPedidos(abertos);
        int id = lerInt("Número do pedido (0 para voltar): ");
        if (id == 0) {
            return;
        }
        Pedido pedido = ConsultasAdmin.porId(sistema.getPedidos(), id);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
            return;
        }
        System.out.println(ConsultasAdmin.detalhes(pedido));
        if (!pedido.isAberto()) {
            System.out.println("Este pedido já está " + pedido.getStatus() + " e não pode ser alterado.");
            return;
        }
        String r = lerTexto("Aprovar (a), reprovar (r) ou voltar (v)? ").toLowerCase();
        if (r.equals("a") || r.equals("r")) {
            try {
                AvaliacaoPedido.avaliar(sistema.getUsuarioAtual(), pedido, r.equals("a"));
                System.out.println("Pedido #" + pedido.getId() + " agora está " + pedido.getStatus() + ".");
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
        } else if (!r.equals("v")) {
            System.out.println("Opção inválida. Nada foi alterado.");
        }
    }

    static void listarEntreDatas(Sistema sistema) {
        LocalDate ini = lerData("Data inicial");
        LocalDate fim = lerData("Data final");
        if (fim.isBefore(ini)) {
            System.out.println("A data final deve ser igual ou posterior à inicial.");
            return;
        }
        mostrarPedidos(ConsultasAdmin.entreDatas(sistema.getPedidos(), ini, fim));
    }

    static void buscarPorFuncionario(Sistema sistema) {
        String nome = lerTexto("Nome do funcionário: ");
        mostrarPedidos(ConsultasAdmin.porFuncionario(sistema.getPedidos(), nome));
    }

    static void buscarPorItem(Sistema sistema) {
        String desc = lerTexto("Descrição do item: ");
        mostrarPedidos(ConsultasAdmin.porItem(sistema.getPedidos(), desc));
    }

    static void verDetalhes(Sistema sistema) {
        int id = lerInt("Número do pedido: ");
        Pedido pedido = ConsultasAdmin.porId(sistema.getPedidos(), id);
        if (pedido == null) {
            System.out.println("Pedido não encontrado.");
        } else {
            System.out.println(ConsultasAdmin.detalhes(pedido));
        }
    }

    public static void main(String[] args) {
        Sistema sistema = new Sistema();
        boolean executando = true;
        while (executando) {
            mostrarMenu(sistema);
            int opcao = lerInt("Escolha uma opção: ");
            switch (opcao) {
                case 1:
                    trocarUsuario(sistema);
                    break;
                case 2:
                    listarUsuarios(sistema);
                    break;
                case 3:
                    listarDepartamentos(sistema);
                    break;
                case 4:
                    if (exigirAdmin(sistema)) avaliarPedido(sistema);
                    break;
                case 5:
                    if (exigirAdmin(sistema)) listarEntreDatas(sistema);
                    break;
                case 6:
                    if (exigirAdmin(sistema)) buscarPorFuncionario(sistema);
                    break;
                case 7:
                    if (exigirAdmin(sistema)) buscarPorItem(sistema);
                    break;
                case 8:
                    if (exigirAdmin(sistema)) verDetalhes(sistema);
                    break;
                case 9:
                    RegistrarPedido.executar(sistema);
                    break;
                case 10:
                    ExcluirPedido.executar(sistema);
                    break;
                case 11:
                    if (exigirAdmin(sistema)) {
                        System.out.println(new Estatisticas(sistema.getPedidos()).relatorio());
                    }
                    break;
                case 0:
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        System.out.println("Até logo!");
    }
}