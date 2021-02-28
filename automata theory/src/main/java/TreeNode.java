import java.util.ArrayList;

public class TreeNode {
    String value;
    ArrayList<TreeNode> children;
    TreeNode(){
        value=null;
        children=null;
    }
    TreeNode(String value)
    {
        this.value=value;
        children=null;
    }
    public void setChildren(ArrayList<TreeNode> children){
        this.children=children;
    }
}
