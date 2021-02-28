import java.util.ArrayList;
import java.util.Set;

public class BuildStateMachine {
    String stateDefaultName = "S";
    Set<Character> alphabet=null;
    public ArrayList<TableString>  buildStateMachine(String expression ){
        ParseRegularExpression parser=new ParseRegularExpression();
        alphabet=parser.getAlphabet(expression);
        expression=parser.checkExcess(expression);
        Set<Character> alphabet = parser.getAlphabet(expression);
        Set<String> cleanTree=parser.parseRegularExpression(expression);

        StateNode stateTree = new StateNode();
        buildStateTree(cleanTree, stateTree);
        setNumbers(stateTree, 0);

        ArrayList<TableString> start = new ArrayList<>();
        makeStateTable(stateTree, alphabet, start);
        return start;

    }
    public void buildStateTree(Set<String> simpleTree, StateNode start) {

        for (String term : simpleTree
        ) {
            StateNode currentNode = start;
            for (int i = 0; i < term.length(); i++) {
                char currentSymb = term.charAt(i);//взяли символ
//проверка на *
                int currentNum;
                Integer wayNum = findPath(currentNode, currentSymb);//посчитали номер элемента, у которого нужный нам переход
                if (wayNum == null)//если нет необходимого нам перехода, создаем и переходим на следующий шаг
                {
                    if (currentNode.getNextStates() == null) currentNode.setNextStates(new ArrayList<>());
                    currentNode.getNextStates().add(new StateNode(currentSymb));
                    currentNum=start.getNextStates().size() - 1;
                } else {//иначе переходим не создавая
                    currentNum=wayNum;
                }
                currentNode = currentNode.getNextStates().get(currentNum);

                if (i < term.length() - 1 && term.charAt(i + 1) == '*' && currentSymb != ')' && haveLoop(currentNode) == null)
                //если след символ *, а сейчас - нескобка - повторение одного символа. Надо сделать ссылку нп себя же
                {
                    if (currentNode.getNextStates() == null) currentNode.setNextStates(new ArrayList<>());
                    currentNode.getNextStates().add(currentNode);
//                    currentNode.nextStates.get(currentNode.nextStates.size() - 1).nextStates = new ArrayList<>();
//                    currentNode.nextStates.get(currentNode.nextStates.size() - 1).nextStates.add(currentNode.nextStates.get(currentNode.nextStates.size() - 1));
                }


            }
        }
    }

    public Integer haveLoop(StateNode current) {
        if (current == null || current.getNextStates() == null || current.getNextStates().isEmpty()) return null;
        for (int i = 0; i < current.getNextStates().size(); i++)
            if (current.getNextStates().get(i).equals(current))
                return i;
        return null;
    }

    public Integer findPath(StateNode root, char currentSymb) {
        if (root.getNextStates() == null || root.getNextStates().isEmpty()) return null;
        for (int i = 0; i < root.getNextStates().size(); i++)
            if (root.getNextStates().get(i).getMyTriggerSymbol() == currentSymb)
                return i;
        return null;
    }

    public int setNumbers(StateNode node, int number) {
        if (node.getMyNum() != null) return number;
        node.setMyNum(number++);
        if (node.getNextStates() == null || node.getNextStates().isEmpty()) return number;
        for (int i = 0; i < node.getNextStates().size(); i++)
            number = setNumbers(node.getNextStates().get(i), number);
        return number;
    }

    public void printStateMachine(StateNode start, String pagging) {
        if (start == null || start.isPrinted()) return;
        String defPagging = "  ";
        System.out.println(pagging + start.getMyNum());
        start.setPrinted(true);
        if (start.getNextStates() != null && !start.getNextStates().isEmpty())
            for (StateNode node : start.getNextStates()
            ) {
                printStateMachine(node, pagging + defPagging);
            }
    }
    public void makeStateTable(StateNode stateMachine, Set<Character> alphabet, ArrayList<TableString> start) {
        //System.out.println(stateMachine.myNum);
        if (stateMachine == null || stateMachine.isGetTable()) return;
        stateMachine.setGetTable(true);
        TableString newStr = tableBuilding(stateMachine, alphabet);
        if (newStr != null) start.add(newStr);
        if (stateMachine.getNextStates() != null && !stateMachine.getNextStates().isEmpty())
            for (StateNode childNode : stateMachine.getNextStates()
            ) {
                makeStateTable(childNode, alphabet, start);
            }
    }

    public TableString tableBuilding(StateNode currentState, Set<Character> alphabet) {
        TableString currentString = new TableString();
        currentString.setStateName(stateDefaultName + currentState.getMyNum());
        if (currentState.getNextStates() == null) return null;
        for (Character symb : alphabet
        ) {
            boolean find = false;
            for (StateNode child : currentState.getNextStates()
            ) {
                if (child.getMyTriggerSymbol().equals(symb)) {
                    currentString.getStateTransitions().add(new TableNode(symb, stateDefaultName + child.getMyNum()));
                    find = true;
                }
            }
            if (!find) currentString.getStateTransitions().add(new TableNode(symb));
        }
        return currentString;
    }
    public void printTable(ArrayList<TableString> table ) {
        System.out.printf("%8s", stateDefaultName);
        for (Character symb : alphabet
        ) {
            System.out.printf("%8s", symb);
        }
        System.out.println();
        for (TableString string : table
        ) {
            System.out.printf("%8s", string.getStateName());
            for (TableNode node : string.getStateTransitions()
            ) {
                System.out.printf("%8s", node.getStateName());
            }
            System.out.println();


        }
    }
}
