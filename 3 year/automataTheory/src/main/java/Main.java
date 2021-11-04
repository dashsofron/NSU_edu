import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) {
        String regularExpression = "(1+0*1+((1+11*+(0+01*))))";
        System.out.println("Input expression:");
        System.out.println(regularExpression);
        BuildStateMachine builder=new BuildStateMachine();
        ArrayList<TableString> machine=builder.buildStateMachine(regularExpression);
        System.out.println("state table:");
        builder.printTable(machine);
        System.out.println("null means end state");


    }

}
