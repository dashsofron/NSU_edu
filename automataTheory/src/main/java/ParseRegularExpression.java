import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ParseRegularExpression {
    String stateDefaultName = "S";

    public List<StateNode> parseRegularExpression(String expression) {
        System.out.println(expression);
        expression=checkExcess(expression);
        System.out.println(expression);

        Set<Character> alphabet = getAlphabet(expression);
        System.out.println(alphabet);
        Set<StringNode> terms = new TreeSet<>();
        getTreeFromString(new StringNode(expression), terms);
        for (StringNode node:terms
             ) {
            System.out.print(node.value+" | ");
        }
        System.out.println();
        Set<String> cleanTree = cleanTree(terms);
        System.out.println(cleanTree);
        StateNode stateTree = new StateNode();
        buildStateTree(cleanTree, stateTree);
        setNumbers(stateTree, 0);
        System.out.println();
//        System.out.println("State machine\n");
//        printStateMachine(stateTree, "");
//        System.out.println();
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
        if (stateMachine == null || stateMachine.getTable) return;
        stateMachine.getTable = true;
        TableString newStr = tableBuilding(stateMachine, alphabet);
        if (newStr != null) start.add(newStr);
        if (stateMachine.nextStates != null && !stateMachine.nextStates.isEmpty())
            for (StateNode childNode : stateMachine.nextStates
            ) {
                makeStateTable(childNode, alphabet, start);
            }
    }

    public TableString tableBuilding(StateNode currentState, Set<Character> alphabet) {
        TableString currentString = new TableString();
        currentString.stateName = stateDefaultName + currentState.myNum;
        if (currentState.nextStates == null) return null;
        for (Character symb : alphabet
        ) {
            boolean find = false;
            for (StateNode child : currentState.nextStates
            ) {
                if (child.myTriggerSymbol.equals(symb)) {
                    currentString.stateTransitions.add(new TableNode(symb, stateDefaultName + child.myNum));
                    find = true;
                }
            }
            if (!find) currentString.stateTransitions.add(new TableNode(symb));
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
                    if (currentNode.nextStates == null) currentNode.nextStates = new ArrayList<>();
                    currentNode.nextStates.add(new StateNode(currentSymb));
                    currentNum=start.nextStates.size() - 1;
                } else {//иначе переходим не создавая
                    currentNum=wayNum;
                }
                currentNode = currentNode.nextStates.get(currentNum);

                if (i < term.length() - 1 && term.charAt(i + 1) == '*' && currentSymb != ')' && haveLoop(currentNode) == null)
                //если след символ *, а сейчас - нескобка - повторение одного символа. Надо сделать ссылку нп себя же
                {
                    if (currentNode.nextStates == null) currentNode.nextStates = new ArrayList<>();
                    currentNode.nextStates.add(currentNode);
//                    currentNode.nextStates.get(currentNode.nextStates.size() - 1).nextStates = new ArrayList<>();
//                    currentNode.nextStates.get(currentNode.nextStates.size() - 1).nextStates.add(currentNode.nextStates.get(currentNode.nextStates.size() - 1));
                }


            }
        }
    }

    public Integer findPath(StateNode root, char currentSymb) {
        if (root.nextStates == null || root.nextStates.isEmpty()) return null;
        for (int i = 0; i < root.nextStates.size(); i++)
            if (root.nextStates.get(i).myTriggerSymbol == currentSymb)
                return i;
        return null;
    }

    public Integer haveLoop(StateNode current) {
        if (current == null || current.nextStates == null || current.nextStates.isEmpty()) return null;
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
    public void getTreeFromString(StringNode expression,  Set<StringNode> treeSet) {
        //ArrayList<TreeNode> tree = new ArrayList<>();
        expression.value=checkExcess(expression.value);
        treeSet.add(expression);
        expression.visited = true;
        if (expression.value.indexOf('+') < 0) {
            return;
        } else expression.delete = true;

        StringBuilder current = new StringBuilder();
        int currentNum = 0;
        int brackNum = 0;
        while (currentNum < expression.value.length()) {
            switch (expression.value.charAt(currentNum)) {
                case '+':
                    if (brackNum == 0) {
                        treeSet.add(new StringNode(checkExcess(current)));
                        current.delete(0, current.length());
                    } else current.append(expression.value.charAt(currentNum));
                    break;
                case '(':
                    current.append(expression.value.charAt(currentNum));
                    brackNum++;
                    break;
                case ')':
                    current.append(expression.value.charAt(currentNum));
                    brackNum--;
                    break;
                default:
                    current.append(expression.value.charAt(currentNum));

            }
            currentNum++;
            if(expression.value.equals(current.toString()))
                expression.delete=false;
            if (currentNum == expression.value.length() && current.length() > 0)
                treeSet.add(new StringNode(checkExcess(current)));

        }

        for (StringNode string : treeSet
        ) {
            if (!string.visited)
                getTreeFromString(string, treeSet);
        }

    }

    public Set<String> cleanTree(Set<StringNode> tree) {
        TreeSet<String> cleanTree = new TreeSet<>();
        for (StringNode node : tree
        ) {
            if (!node.delete) cleanTree.add(node.value);

        }
        return cleanTree;
    }

    public String checkExcess(StringBuilder string) {
        for (int i = 0; i < string.length() - 2; i++) {
            if (string.charAt(i) == string.charAt(i + 1) && string.charAt(i + 2) == '*')
                string.delete(i, i + 1);
            if(string.charAt(i)=='*'&&string.charAt(i-1)==string.charAt(i+1))
                string.delete(i,i+1);
        }
        return string.toString();
    }


    public String checkExcess(String expression) {
        while (expression.indexOf('(') == 0 && expression.lastIndexOf(')') == expression.length() - 1)
            expression = expression.substring(1, expression.length() - 1);
        StringBuilder b = new StringBuilder(expression);
        return checkExcess(b);

    }
}
