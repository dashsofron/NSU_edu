import java.io.File;

public class Main {

    public static void main(String[] arg) {

        if (arg.length == 0) {
            System.out.println("Требуется файл с КСГ");
            return;
        }

        try {
            String path = arg[0];
            Grammar grammar = new Grammar(new File(path));

            System.out.println(grammar.isEmpty());
            grammar.turnInHomskiiGrammar();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}