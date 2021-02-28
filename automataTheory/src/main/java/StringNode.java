public class StringNode implements Comparable<StringNode> {
    String value;
    boolean delete=false;
    boolean visited=false;
    StringNode(){
        value=null;
    }
    StringNode(String value){
        this.value=value;
    }


    @Override
    public int compareTo(StringNode o) {
        if(this.value.equals(o.value))return 0;
        return 1;
    }
}
