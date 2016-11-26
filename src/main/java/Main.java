import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import java.lang.reflect.Array;
import java.util.*;


/**
 * Created by root on 11/18/16.
 */
public class Main {

    // Each transaction with string
    public static List<List<Integer>> TRANSACTIONS;
    public static int S;
    public static double C;

    // Get all unique items
    static Map<List<Integer>,Integer> large_itemsets;

    public static void main(String[] arg){
        SparkConf conf = new SparkConf().setAppName("local").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("OFF");

        S = 500;
        C = 1/2;
        init("T10I4D100K.dat",sc);
        List<Map<List<Integer>,Integer>> itemsets= new LinkedList();






        Map<List<Integer>, Integer> candidate_map;
        int i = 1;
        while(large_itemsets!=null) {

            System.out.println("itemsets size "+i + " has " + large_itemsets.size()+" items: ");
            printMap(large_itemsets);
            i++;
            itemsets.add(large_itemsets);

            candidate_map = new Candidates(large_itemsets).make_candidates();

            HashTree ht = new HashTree();

            ht.make_hashtree(candidate_map);

            Iterator<List<Integer>> iterator = TRANSACTIONS.iterator();
            while(iterator.hasNext()){
                ht.search(iterator.next());
            }



            //System.out.println(ht.root);

            large_itemsets = ht.create_table(i);


        }
        Assosiation_rules ar = new Assosiation_rules(itemsets);
        List<String> list = ar.getConsequents();

        list.forEach(con->
        {
            System.out.println("rule: " + con);
        });
        //System.out.println(itemsets.size());
    }

    private static void printMap(Map<List<Integer>,Integer> map){
        map.forEach((items,number)-> {
            System.out.print("{ ");
            items.forEach(item->{
                System.out.print(item+" ");
            });
            System.out.print("}\t");
            }
        );
        System.out.println();
    }

    public static void init(String doc_name, JavaSparkContext sc){
        // import document
        JavaRDD<String> file = sc.textFile(doc_name);

        // each transaction is a string in JavaRDD
        JavaRDD<String> transaction = file.flatMap(new FlatMapFunction<String, String>() {
            public Iterator<String> call(String s) { return Arrays.asList(s.split("\n")).iterator(); }
        });

        // each transection is turned into a list of integers
        TRANSACTIONS = new LinkedList();
        transaction.foreach(new VoidFunction<String>() {
            @Override
            public void call(String s) throws Exception {
                List<String> strings =  Arrays.asList(s.split(" "));
                List<Integer> ints = new ArrayList<Integer>();
                strings.forEach(string -> {ints.add(Integer.parseInt(string));});
                TRANSACTIONS.add(ints);
            }
        });

        // get all the items
        JavaRDD<Integer> temp = file.flatMap(new FlatMapFunction<String, Integer>() {
            public Iterator<Integer> call(String s) {
                List<String> strings =  Arrays.asList(s.split(" "));
                List<Integer> ints = new LinkedList<Integer>();
                strings.forEach(string -> {ints.add(Integer.parseInt(string));});
                return ints.iterator();
            }
        });

        // reduce the items
        Map<Integer, Long> items_with_count = temp.countByValue();
        large_itemsets = new HashMap<>();
        items_with_count.forEach((item, count) -> {
            int c = (int) (long) count;
            if (count>=S) {
                List l = new ArrayList(1);
                l.add(item);
                large_itemsets.put(l, c);
            }
        });

    }
}
