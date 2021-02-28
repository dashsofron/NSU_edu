import java.util.List;
public class Main {
    public static void main(String[] args) {
        String regularExpression = args[0];
        ParseRegularExpression parser = new ParseRegularExpression();
        List<StateNode> machineState=parser.parseRegularExpression(regularExpression);
    }
}
