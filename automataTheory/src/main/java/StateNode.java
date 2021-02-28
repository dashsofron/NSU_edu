import java.util.ArrayList;
import java.util.List;

public class StateNode {
    public Integer myNum;
    public Character myTriggerSymbol;
    boolean printed=false;
    boolean getTable=false;
    public ArrayList<StateNode> nextStates;
    StateNode( char trig){
        myTriggerSymbol=trig;
        myNum=null;
    }
    StateNode( ArrayList<StateNode> nextStates){
        this.nextStates=nextStates;
        myTriggerSymbol=null;
        myNum=null;
    }
    StateNode(){
        nextStates=null;
        myNum=null;

    }
}
