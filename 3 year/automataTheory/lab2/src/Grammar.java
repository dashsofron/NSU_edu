import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Grammar {

    public static final Pattern N = Pattern.compile("[A-Z]");
    public static final Pattern T = Pattern.compile("[\\S&&[^A-Z]]");
    //правила
    static int shift = 0;
    static int startSymbol = 0;
    //начальный символ
    final String start;
    //натереминальные символы
    List<String> n = new LinkedList<>();
    //терминальные символы
    List<String> t = new LinkedList<>();
    private Map<String, List<String>> p = new HashMap<>();

    public Grammar(File file) throws FileNotFoundException, IllegalArgumentException {

        try (Scanner scanner = new Scanner(file)) {
            n.addAll(Arrays.asList(scanner.nextLine().split("\\s+")));
            this.check(N, n.stream(), "N");

            t.addAll(Arrays.asList(scanner.nextLine().split("\\s+")));
            this.check(T, t.stream(), "T");

            start = scanner.next(N);
            while (scanner.hasNextLine()) {

                String s = scanner.next(N);
                this.check(n, s, "N");

                scanner.next("->");
                for (String a : scanner.nextLine().trim().split("\\s*\\|\\s*"))
                    p.computeIfAbsent(a, s1 -> new LinkedList<>()).add(s);

            }
        }

    }


    private void check(List<String> list, String s, String kind)
            throws IllegalArgumentException {

        if (!list.contains(s))
            throw new IllegalArgumentException(s + " не принадлежит " + kind);

    }

    private void check(Pattern pattern, Stream<String> stream, String kind)
            throws IllegalArgumentException {

        if (!stream.allMatch(s -> pattern.matcher(s).matches()))
            throw new IllegalArgumentException(kind + " должно соответствовать: " + pattern.pattern());

    }


    public boolean isEmpty() {

        HashSet<String> N = new HashSet<>(t);
        HashSet<String> toAdd = new HashSet<>();

        do {

            toAdd.clear();

            for (String a : p.keySet()) {
                boolean flag = true;

                for (char c : a.toCharArray())

                    if (!N.contains(String.valueOf(c))) {
                        flag = false;
                        break;
                    }

                if (flag)
                    toAdd.addAll(p.get(a));
            }
            N.addAll(toAdd);

        } while (!toAdd.isEmpty() & !N.contains(start));

        if (N.contains(start))
            return false;

        return true;

    }

    public List<String> getNe() {

        HashSet<String> N = new HashSet<>(t);
        HashSet<String> toAdd = new HashSet<>();

        do {

            toAdd.clear();

            for (String a : p.keySet()) {
                boolean flag = true;

                for (char c : a.toCharArray())

                    if (!N.contains(String.valueOf(c))) {
                        flag = false;
                        break;
                    }

                if (flag)
                    toAdd.addAll(p.get(a));
            }
            N.addAll(toAdd);

        } while (!toAdd.isEmpty() & !N.contains(start));

        N.remove(start);
        return new LinkedList<>(N);
    }


    void printGrammar() {
        System.out.println();

        System.out.println("not terminal");
        for (String str : n)
            System.out.print(str + " ");
        System.out.println();

        System.out.println("terminal");
        for (String str : t)
            System.out.print(str + " ");

        System.out.println();

        System.out.println("start");
        System.out.println(start);


        Set<String> set = new TreeSet<>();
        for (String key : p.keySet()) {
            set.addAll(p.get(key));
        }
        System.out.println();

        System.out.println("rules");
        for (String key : set) {
            System.out.print(key + "->");
            for (String value : getValuesForSymbol(p, key)) {
                System.out.print(value + "|");

            }
            System.out.println();

        }


    }

    List<String> getValuesForSymbol(Map<String, List<String>> p, String symbol) {
        List<String> valuesForSymbol = new ArrayList<>();
        Boolean contains = false;
        for (String key : p.keySet()) {
            for (String value : p.get(key)) {
                contains = false;
                if (value.equals(symbol))
                    contains = true;
                if (contains) {
                    valuesForSymbol.add(key);

                }
            }

        }
        return valuesForSymbol;
    }

    void removeUnreachable() {
//        System.out.println("old");
//        printGrammar();

        Set<String> v = new TreeSet<>();
        Set<String> vToAdd = new TreeSet<>();
        v.add(start);
        while (!v.equals(vToAdd)) {
            v.addAll(vToAdd);
            vToAdd.clear();
            for (String key : p.keySet()) {
                {
                    for (String value : p.get(key)) {
                        if (v.contains(value)) {
                            for (String str : getValuesForSymbol(p, value)) {
                                for (char c : str.toCharArray())
                                    vToAdd.add(String.valueOf(c));

                            }
                        }
                    }
                }

            }
            vToAdd.addAll(v);
        }
        List<String> newN = new ArrayList<>();
        List<String> newT = new ArrayList<>();
        Map<String, List<String>> newP = new HashMap<>();

        for (String str : v) {
            if (n.contains(str))
                newN.add(str);
            if (t.contains(str))
                newT.add(str);
        }

        Boolean contains = true;
        for (String key : p.keySet()) {
            for (char c : key.toCharArray())
                if (!v.contains(String.valueOf(c)))
                    contains = false;
            if (!contains)
                continue;
            for (String value : p.get(key)) {
                if (v.contains(value)) {
                    addToP(newP, key, value);

                }

            }

        }
        n = newN;
        t = newT;
        p = newP;
//        System.out.println("new");
//
//        printGrammar();

    }


    void removeUseless() {
        removeUnreachable();

    }


    private Set<Pair<String, String>> getRules() {
        Set<Pair<String, String>> returnSet = new HashSet<>();

        for (Map.Entry<String, List<String>> entry : p.entrySet()) {
            for (String string : entry.getValue()) {
                returnSet.add(new Pair<>(string, entry.getKey()));
            }
        }

        return returnSet;
    }

    private void addRule(Map<String, List<String>> map, String first, String next) {
        map.computeIfAbsent(next, a -> new LinkedList<>()).add(first);
    }

    public void removeChainRules() {
        Map<String, List<String>> newRuleMap = new HashMap<>();

        Map<String, Set<String>> reachableSets = new HashMap<>();

        for (String nonTerminal : n) {
            reachableSets.put(nonTerminal, getChainReachable(nonTerminal));
        }

        for (Pair<String, String> rule : getRules()) {
            if (!isChainRule(rule.getValue())) {
                for (Map.Entry<String, Set<String>> reachableMapEntry : reachableSets.entrySet()) {
                    if (reachableMapEntry.getValue().contains(rule.getKey())) {
                        addRule(newRuleMap, reachableMapEntry.getKey(), rule.getValue());
                    }
                }
            }
        }

        p = newRuleMap;
    }

    private boolean isChainRule(String transition) {
        return transition.length() == 1 && n.contains(transition);
    }

    private Set<String> getChainReachable(String nonTerminal) {
        if (!n.contains(nonTerminal)) {
            throw new IllegalArgumentException("Argument should be non terminal character");
        }

        Set<String> currentSet;
        Set<String> newSet = new HashSet<>();
        newSet.add(nonTerminal);

        do {
            currentSet = newSet;
            newSet = new HashSet<>(currentSet);

            // could be optimized
            for (String currentSymbol : currentSet) {
                List<String> transitionList = getTransitions(currentSymbol);

                for (String string : transitionList) {
                    if (isChainRule(string)) {
                        newSet.add(string);
                    }
                }
            }
        } while (!newSet.equals(currentSet));

        return currentSet;
    }

    private List<String> getTransitions(String nonTerminal) {
        if (!n.contains(nonTerminal)) {
            throw new IllegalArgumentException("Argument should be non terminal character");
        }

        List<String> returnList = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : p.entrySet()) {
            for (String string : entry.getValue()) {
                if (nonTerminal.equals(string)) {
                    returnList.add(entry.getKey());
                }
            }
        }

        return returnList;
    }


    void setStartSymbol() {
        char c;
        for (String str : n)
            if ((c = str.charAt(0)) > startSymbol)
                startSymbol = c;
    }


    public void turnInHomskiiGrammar() {
        System.out.println("start");
        printGrammar();

        removeUseless();
        removeChainRules();

        System.out.println("prepared");
        printGrammar();

        setStartSymbol();
        HashMap<String, List<String>> newp = new HashMap<>();

        //найдем правила первого типа
        HashMap<String, List<String>> p1 = new HashMap<>();
        //найдем правила второго типа
        HashMap<String, List<String>> p2 = new HashMap<>();
        //правила длины больше 2
        HashMap<String, List<String>> pBig = new HashMap<>();
        //правила с 2 символами где хотя бы 1 терминальный
        HashMap<String, List<String>> pTerminal = new HashMap<>();

        List<String> newN = new LinkedList<>();
        for (String key : p.keySet()) {
            for (String value : p.get(key)) {

                if (key.length() == 1 && t.contains(key)) {
                    if (n.contains(value)) {
                        addToP(p1, key, value);
                    }
                } else if (key.length() == 2 && n.contains(String.valueOf(key.charAt(0))) && n.contains(String.valueOf(key.charAt(1)))) {
                    if (n.contains(value)) {
                        addToP(p2, key, value);

                    }
                } else if (key.length() == 2 && (t.contains(String.valueOf(key.charAt(0))) || t.contains(String.valueOf(key.charAt(1))))) {
                    if (n.contains(value)) {
                        addRuleWithTerminal(newN, pTerminal, key, value);

                    }
                } else if (key.length() > 2) {
                    if (n.contains(value)) {
                        addBigRule(newN, pBig, key, value);

                    }
                }
            }
        }


//            for (String key : p.keySet()) {
//            if (n.contains(key)) {
//                for (String value : p.get(key)) {
//                    if (value.length() == 1 && t.contains(value)) {
//                        addToP(p1, key, value);
//                    }
//                    if (value.length() == 2 &&
//                            n.contains(value.charAt(0)) &&
//                            n.contains(value.charAt(1)))
//                        addToP(p2, key, value);
//                    if (value.length() == 2 &&
//                            (t.contains(value.charAt(0)) || t.contains(value.charAt(1)))) {
//                        addRuleWithTerminal(newN, pTerminal, key, value);
//
//                    }
//                    if (value.length() > 2) {
//                        addBigRule(newN, pBig, key, value);
//                    }
//                }
//            }
//        }


        //point 1
        newp.putAll(p1);


        //point 2
        for (String key : p2.keySet()) {
            for (String value : p2.get(key))
                addToP(newp, key, value);
        }
        //point4+6
        for (String key : pBig.keySet()) {
            for (String value : pBig.get(key))
                addToP(newp, key, value);
        }
        //point5+6
        for (String key : pTerminal.keySet()) {
            for (String value : pTerminal.get(key))
                addToP(newp, key, value);
        }
        //point7
        n.addAll(newN);
        p = newp;

        System.out.println("new");
        printGrammar();


    }

    char getNewSymbol(int shift, int start) {
        shift++;
        return (char) (start + shift);
    }

    private String addNewSymbolForTerminal(List<String> newN, Map<String, List<String>> newP, String terminal) {
        String x = String.valueOf(getNewSymbol(startSymbol, shift++));
        newN.add(x);
        addToP(newP, terminal, x);
        return x;
    }

    private void addRuleWithTerminal(List<String> newN, Map<String, List<String>> newP, String key, String value) {
        String x1 = String.valueOf(key.charAt(0));
        String x2 = String.valueOf(key.charAt(1));

        if (t.contains(x1)) {
            x1 = addNewSymbolForTerminal(newN, newP, x1);
        }
        if (t.contains(x2)) {
            x2 = addNewSymbolForTerminal(newN, newP, x2);

        }
        String newKey = x1 + x2;
        addToP(newP, newKey, value);


    }

    void addToP(Map<String, List<String>> newP, String key, String value) {
        List<String> values;
        if (newP.containsKey(key)) {
            values = newP.get(key);
        } else {
            values = new ArrayList<>();

        }
        values.add(value);

        newP.put(key, values);
    }


    private void addBigRule(List<String> newN, Map<String, List<String>> newP, String key, String value) {
        addToP(newP, addPartlyBigRule(newN, newP, key), value);

    }

    private String addPartlyBigRule(List<String> newN, Map<String, List<String>> newP, String key) {
        if (key.length() == 2)
            return addNewSymbolForTerminal(newN, newP, key);
        String newValue = String.valueOf(key.charAt(0));
        key = key.substring(1, key.length());
        if (t.contains(newValue)) {
            newValue = addNewSymbolForTerminal(newN, newP, newValue);
        }
        String newKey = addPartlyBigRule(newN, newP, key);
        //addToP(newP, newKey, newValue);
        return newValue + newKey;


    }
}



