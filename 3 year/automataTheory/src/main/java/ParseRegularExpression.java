
import java.util.Set;
import java.util.TreeSet;

public class ParseRegularExpression {

    public Set<String> parseRegularExpression(String expression) {
        Set<StringNode> terms = new TreeSet<>();
        getTreeFromString(new StringNode(expression), terms);
        Set<String> cleanTree = cleanTree(terms);
        return cleanTree;
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

    public void getTreeFromString(StringNode expression,  Set<StringNode> treeSet) {
        //ArrayList<TreeNode> tree = new ArrayList<>();
        expression.setValue(checkExcess(expression.getValue()));
        treeSet.add(expression);
        expression.setVisited(true);
        if (expression.getValue().indexOf('+') < 0) {
            return;
        } else expression.setDelete(true);

        StringBuilder current = new StringBuilder();
        int currentNum = 0;
        int brackNum = 0;
        while (currentNum < expression.getValue().length()) {
            switch (expression.getValue().charAt(currentNum)) {
                case '+':
                    if (brackNum == 0) {
                        treeSet.add(new StringNode(checkExcess(current)));
                        current.delete(0, current.length());
                    } else current.append(expression.getValue().charAt(currentNum));
                    break;
                case '(':
                    current.append(expression.getValue().charAt(currentNum));
                    brackNum++;
                    break;
                case ')':
                    current.append(expression.getValue().charAt(currentNum));
                    brackNum--;
                    break;
                default:
                    current.append(expression.getValue().charAt(currentNum));

            }
            currentNum++;
            if(expression.getValue().equals(current.toString()))
                expression.setDelete(false);
            if (currentNum == expression.getValue().length() && current.length() > 0)
                treeSet.add(new StringNode(checkExcess(current)));

        }

        for (StringNode string : treeSet
        ) {
            if (!string.isVisited())
                getTreeFromString(string, treeSet);
        }

    }

    public Set<String> cleanTree(Set<StringNode> tree) {
        TreeSet<String> cleanTree = new TreeSet<>();
        for (StringNode node : tree
        ) {
            if (!node.isDelete()) cleanTree.add(node.getValue());

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
