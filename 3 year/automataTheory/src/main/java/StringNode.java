public class StringNode implements Comparable<StringNode> {
    private String value;
    private boolean delete=false;
    private boolean visited=false;
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

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setValue( String newValue){
        value=newValue;
    }
    public String getValue( ){
        return value;
    }
}
