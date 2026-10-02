import java.util.Scanner;

public class Main {

    private static final Scanner in = new Scanner(System.in);

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

    static void mostrarMenu(Sistema sistema) {
        System.out.println();
        System.out.println("=== Controle de Aquisições ===");
        System.out.println("Operador atual: " + sistema.getUsuarioAtual());
        System.out.println("1 - Trocar de usuário");
        System.out.println("2 - Listar usuários");
        System.out.println("3 - Listar departamentos");
        System.out.println("0 - Sair");
    }

    static void listarUsuarios(Sistema sistema) {
        for (Usuario u : sistema.getUsuarios()) {
            System.out.println(u);
        }
    }
}
