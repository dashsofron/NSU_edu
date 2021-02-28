import java.util.ArrayList;
import java.util.List;

public class StateNode {
    public Integer myNum=null;
    public Character myTriggerSymbol;
    boolean printed=false;
    boolean getTable=false;
    public ArrayList<StateNode> nextStates;
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
}
