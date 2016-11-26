import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by root on 11/20/16.
 */
public class Test {


    public static void main(String[] arg){
        List<Integer> list = new LinkedList();
        list.add(1);
        list.add(2);
        list.add(3);

        List<List<Integer>> permut = generate_permut(list);


    }
    private static List<List<Integer>> generate_permut(List<Integer> set){
        List<List<Integer>> permutations = new LinkedList<>();

        int i = 0;
        while(i<set.size()){
            List<Integer> permut = new ArrayList<>(set.size()-1);
            int j = 0;
            while(j<set.size()){
                if(i!=j) {
                    permut.add(set.get(j));
                }
                j++;
            }
            i++;
            permut.forEach(item->
            {
                System.out.print(item+" ");

            });
            System.out.println();
            permutations.add(permut);

        }
        return permutations;

    }
}
