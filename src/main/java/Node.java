import java.util.Hashtable;
import java.util.List;

/**
 * Created by root on 11/19/16.
 */
public class Node {

    public Hashtable<Integer, Node> table;
    public Integer leaf = null;

    public Node(Hashtable table){
        this.table = table;
    }

    public Node(Integer leaf){
        this.leaf = leaf;
    }

    public boolean isLeaf(){
        if (leaf ==null){
            return false;
        }
        else return true;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (leaf!=null)
            return leaf.toString();
        else
        {
            table.forEach((number, node) -> sb.append("{"+number.toString()+node.toString()+"}"));
        }
        return sb.toString();
    }


}
