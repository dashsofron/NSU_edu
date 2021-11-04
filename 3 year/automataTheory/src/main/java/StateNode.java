import java.util.ArrayList;
import java.util.List;

public class StateNode {
    private Integer myNum=null;
    private Character myTriggerSymbol;
    private boolean printed=false;
    private boolean getTable=false;
    private ArrayList<StateNode> nextStates;
    StateNode( char trig){
        myTriggerSymbol=trig;
    }
    StateNode( ArrayList<StateNode> nextStates){
        this.nextStates=nextStates;
        myTriggerSymbol=null;
    }
    StateNode(){
        nextStates=null;

    }

    public Integer getMyNum() {
        return myNum;
    }

    public void setMyNum(Integer myNum) {
        this.myNum = myNum;
    }

    public boolean isGetTable() {
        return getTable;
    }

    public void setGetTable(boolean getTable) {
        this.getTable = getTable;
    }

    public boolean isPrinted() {
        return printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    public ArrayList<StateNode> getNextStates() {
        return nextStates;
    }

    public void setNextStates(ArrayList<StateNode> nextStates) {
        this.nextStates = nextStates;
    }

    public Character getMyTriggerSymbol() {
        return myTriggerSymbol;
    }

    public void setMyTriggerSymbol(Character myTriggerSymbol) {
        this.myTriggerSymbol = myTriggerSymbol;
    }
}
