import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ParseRegularExpression {
    String stateDefaultName = "S";

    public List<StateNode> parseRegularExpression(String expression) {
        System.out.println(expression);
        Set<Character> alphabet = getAlphabet(expression);
        System.out.println(alphabet);
        ArrayList<TreeNode> tree = getTreeFromString(expression, 0);
        printTree(tree, "");
        StateNode stateTree = new StateNode(buildStateTree(tree, new ArrayList<>()));
        setNumbers(stateTree, 1);
        System.out.println();
        System.out.println("State machine\n");
        printStateMachine(stateTree,"");
        System.out.println();
        ArrayList<TableString> start = new ArrayList<>();
        makeStateTable(stateTree, alphabet, start);
        printTable(start, alphabet);
        return null;
    }


    public Set<Character> getAlphabet(String expression) {
        TreeSet<Character> alphabet = new TreeSet<>();
        int currentNum = 0;
        while (currentNum < expression.length()) {
            switch (expression.charAt(currentNum)) {
                case '(':
                    break;
                case ')':
                    break;
                case '+':
                    break;
                case '*':
                    break;
                case '?':
                    break;
                case '|':
                    break;
                default:
                    alphabet.add(expression.charAt(currentNum));
            }
            currentNum++;
        }
        return alphabet;
    }

    public void makeStateTable(StateNode stateMachine, Set<Character> alphabet, ArrayList<TableString> start) {
        //System.out.println(stateMachine.myNum);
        if (stateMachine == null||stateMachine.getTable) return;
        stateMachine.getTable=true;
        start.add(tableBuilding(stateMachine, alphabet));
        if (stateMachine.nextStates != null && !stateMachine.nextStates.isEmpty())
            for (StateNode childNode : stateMachine.nextStates
            ) {
                    makeStateTable(childNode, alphabet, start);
            }
    }

    public TableString tableBuilding(StateNode currentState, Set<Character> alphabet) {
        TableString currentString = new TableString();
        currentString.stateName = stateDefaultName + currentState.myNum;
        for (Character symb : alphabet
        ) {
            for (StateNode child : currentState.nextStates
            ) {
                if (child.myTriggerSymbol.equals(symb))
                    currentString.stateTransitions.add(new TableNode(symb, stateDefaultName + child.myNum));
            }
            if (currentString.stateTransitions == null || currentString.stateTransitions.isEmpty() || !currentString.stateTransitions.get(currentString.stateTransitions.size() - 1).equals(symb))
                currentString.stateTransitions.add(new TableNode(symb));
        }
        return currentString;
    }

    public void printTable(ArrayList<TableString> table, Set<Character> alphabet) {
        System.out.printf("%8s", stateDefaultName);
        for (Character symb : alphabet
        ) {
            System.out.printf("%8s", symb);
        }
        System.out.println();
        for (TableString string : table
        ) {
            System.out.printf("%8s", string.stateName);
            for (TableNode node : string.stateTransitions
            ) {
                System.out.printf("%8s", node.stateName);
            }
            System.out.println();


        }
    }

    //----------------------------make machine state by simple tree
    public ArrayList<StateNode> buildStateTree(ArrayList<TreeNode> simpleTree, ArrayList<StateNode> start) {
        ArrayList<StateNode> current = start;
        for (TreeNode node : simpleTree
        ) {
            if (node.children != null) {//если есть дети, идем в глубину и для листьев создаем автоматы, используя созданные предыдущие
                start = buildStateTree(node.children, start);
            } else {//иначе это и есть лист и надо строить автомат
                for (int i = 0; i < node.value.length(); i++) {
                    char currentSymb = node.value.charAt(i);//взяли символ
//проверка на *


                    StateNode currentNode;
                    Integer wayNum = findPath(current, currentSymb);//посчитали номер элемента, у которого нужный нам переход
                    if (wayNum == null)//если нет необходимого нам перехода, создаем и переходим на следующий шаг
                    {
                        current.add(new StateNode(currentSymb));
                        current.get(current.size() - 1).nextStates = new ArrayList<>();
                        currentNode = current.get(current.size() - 1);
                    } else {//иначе переходим не создавая
                        currentNode = current.get(wayNum);
                    }
                    current = currentNode.nextStates;
                    if (i < node.value.length() - 1 && node.value.charAt(i + 1) == '*' && currentSymb != ')' && haveLoop(currentNode) == null)
                    //если след символ *, а сейчас - нескобка - повторение одного символа. Надо сделать ссылку нп себя же
                    {
                        current.add(currentNode);
                    }

                }
            }

        }
        return start;
    }

    public Integer findPath(ArrayList<StateNode> ways, char currentSymb) {
        if (ways == null || ways.isEmpty()) return null;
        for (int i = 0; i < ways.size(); i++)
            if (ways.get(i).myTriggerSymbol == currentSymb)
                return i;
        return null;
    }

    public Integer haveLoop(StateNode current) {
        if (current == null || current.nextStates.isEmpty()) return null;
        for (int i = 0; i < current.nextStates.size(); i++)
            if (current.nextStates.get(i).equals(current))
                return i;
        return null;
    }

    public int setNumbers(StateNode node, int number) {
        if (node.myNum != null) return number;
        node.myNum = number++;
        if (node.nextStates == null || node.nextStates.isEmpty()) return number;
        for (int i = 0; i < node.nextStates.size(); i++)
            number = setNumbers(node.nextStates.get(i), number);
        return number;
    }

    public void printStateMachine(StateNode start, String pagging) {
        if (start == null || start.printed) return;
        String defPagging = "  ";
        System.out.println(pagging + start.myNum);
        start.printed = true;
        if (start.nextStates != null && !start.nextStates.isEmpty())
            for (StateNode node : start.nextStates
            ) {
                printStateMachine(node, pagging + defPagging);
            }
    }

    //----------------------------Tree without +, simplify the machine state build
    public ArrayList<TreeNode> getTreeFromString(String expression, int iter) {
        ArrayList<TreeNode> tree = new ArrayList<>();
        Set<String> treeSet = new TreeSet<>();
        if (expression.indexOf('+') < 0) {
            if (iter == 0) {
                tree.add(new TreeNode(checkExcess(expression)));
                return tree;
            } else return null;
        }
        while (expression.indexOf('(') == 0 && expression.lastIndexOf(')') == expression.length() - 1)
            expression = expression.substring(1, expression.length() - 1);
        StringBuilder current = new StringBuilder();
        int currentNum = 0;
        int brackNum = 0;
        while (currentNum < expression.length()) {
            switch (expression.charAt(currentNum)) {
                case '+':
                    if (brackNum == 0) {
                        treeSet.add(checkExcess(current));
                        current.delete(0, current.length());
                    } else current.append(expression.charAt(currentNum));
                    break;
                case '(':
                    current.append(expression.charAt(currentNum));
                    brackNum++;
                    break;
                case ')':
                    current.append(expression.charAt(currentNum));
                    brackNum--;
                    break;
                default:
                    current.append(expression.charAt(currentNum));

            }
            currentNum++;
            if (currentNum == expression.length() && current.length() > 0) treeSet.add(checkExcess(current));

        }
        if (current.toString().equals(expression)) return null;

        for (String expr : treeSet
        ) {
            tree.add(new TreeNode(expr));

        }


        for (TreeNode treeNode : tree
        ) {
            treeNode.setChildren(getTreeFromString(treeNode.value, 1));
        }


        return tree;
    }

    public void printTree(ArrayList<TreeNode> tree, String num) {
        for (int i = 0; i < tree.size(); i++)
            System.out.println(num + "|" + i + " :" + tree.get(i).value);
        for (int i = 0; i < tree.size(); i++) {
            if (tree.get(i).children != null)
                printTree(tree.get(i).children, i + num);
        }


    }

    public String checkExcess(StringBuilder string) {
        for (int i = 0; i < string.length() - 2; i++)
            if (string.charAt(i) == string.charAt(i + 1) && string.charAt(i + 2) == '*')
                string.delete(i, i + 1);
        return string.toString();
    }

    public String checkExcess(String string) {
        StringBuilder b = new StringBuilder(string);
        return checkExcess(b);

    }
}
