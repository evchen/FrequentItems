import java.util.List;

/**
 * Created by root on 11/20/16.
 */
public class Consequent {

    public List<Integer> left;
    public List<Integer> right;
    public int order;

    public Consequent(List<Integer> left, List<Integer> right){
        this.left = left;
        this.right = right;
        order = right.size();
    }

    public String toString(){
        return print_left(left) +" -> "+print_right(right);
    }

    private String print_left(List<Integer> list){
        StringBuilder sb = new StringBuilder("{ ");

        list.forEach(number ->
        {
            if(!right.contains(number)){sb.append(number + " ");}
        });
        sb.append("}");
        return sb.toString();
    }
    private String print_right(List<Integer> list){
        StringBuilder sb = new StringBuilder("{ ");

        list.forEach(number ->
        {
            sb.append(number + " ");
        });
        sb.append("}");
        return sb.toString();
    }


}
