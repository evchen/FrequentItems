import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/18/16.
 */
public class Large_itemsets {

    static Map<List<Integer>, Integer> candidate_itemsets;
    static int counter =0;


    public Large_itemsets(Map<List<Integer>, Integer> candidate_itemsets){
        this.candidate_itemsets = candidate_itemsets;
    }
    public static Map<List<Integer>, Integer> get_l_itemsets(){

        Map<List<Integer>, Integer> reduced_itemsets= new HashMap<>();

        candidate_itemsets.forEach((list,value)->
        {
            counter = 0;


            Main.TRANSACTIONS.forEach(l-> {
                if (contains(list, l)) {

                    counter++;
                }
            }
            );

            if (counter>=Main.S){
                reduced_itemsets.put(list,counter);}
        });


        return reduced_itemsets;
    }

    private static boolean contains(List<Integer> candidate, List<Integer> transaction){
        Iterator<Integer> c_it = candidate.iterator();
        Iterator<Integer> t_it = transaction.iterator();
        while(c_it.hasNext()){
            Integer item_c = c_it.next();
            Integer item_t = t_it.next();

            while (item_c>item_t){
                if(t_it.hasNext()){
                item_t = t_it.next();}
                else {
                    return false;}
            }
            if(item_c<item_t){
                return false;
            }
            if(!t_it.hasNext())
            {

                if(item_c.equals(item_t)) {
                    return true;
                }
                else
                    return false;
            }

        }
        return true;
    }

}
