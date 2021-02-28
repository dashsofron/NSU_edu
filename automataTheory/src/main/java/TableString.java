import java.util.ArrayList;

public class TableString {
    private String stateName;
    private ArrayList<TableNode>  stateTransitions;
    TableString(){
        stateTransitions=new ArrayList<>();
        stateName=null;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public ArrayList<TableNode> getStateTransitions() {
        return stateTransitions;
    }

    public void setStateTransitions(ArrayList<TableNode> stateTransitions) {
        this.stateTransitions = stateTransitions;
    }
}

class TableNode{
    private Character triggerSymbol;
    private String stateName;
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

    public Character getTriggerSymbol() {
        return triggerSymbol;
    }

    public void setTriggerSymbol(Character triggerSymbol) {
        this.triggerSymbol = triggerSymbol;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}