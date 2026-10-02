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
