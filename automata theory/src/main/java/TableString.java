import java.util.ArrayList;

public class TableString {
    public String stateName;
    public ArrayList<TableNode>  stateTransitions;
    TableString(){
        stateTransitions=new ArrayList<>();
        stateName=null;
    }

}

class TableNode{
    Character triggerSymbol;
    String stateName;
    TableNode(){
        triggerSymbol =null;
        stateName=null;
    }
    TableNode(Character triggerSymbol,String stateName){
        this.triggerSymbol=triggerSymbol;
        this.stateName=stateName;
    }
    TableNode(Character triggerSymbol){
        this.triggerSymbol=triggerSymbol;
        this.stateName=null;
    }
}