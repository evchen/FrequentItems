import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by root on 11/19/16.
 */
public class HashTree {

    public final Node root = new Node(0);

    /*
     * Create hashtree with candidate sets
     */
    public void make_hashtree(Map<List<Integer>,Integer> candidate_sets){
        candidate_sets.forEach((l,v) ->
        {
            insert(l);
        });
    }

    private void insert(List<Integer> candidate){

        Node node = root;
        Iterator<Integer> iterator = candidate.iterator();

        while(iterator.hasNext()){

            Integer temp = iterator.next();
            if(node.isLeaf()){
                node.leaf = null;
                node.table = new Hashtable<>();
                node.table.put(temp,new Node(0));
                node = node.table.get(temp);
            }
            else if(node.table.containsKey(temp)){
                node = node.table.get(temp);
            }
            else
            {
                node.table.put(temp,new Node(0));
                node = node.table.get(temp);
            }

        }
    }

    public void search(List<Integer> transaction){

        CopyOnWriteArrayList<Node> nodeList = new CopyOnWriteArrayList<>();

        if (root.isLeaf()) return;

        //MyList nodeList = new MyList();
        nodeList.add(root);
        transaction.forEach(item->
        {
            Iterator<Node> iterator = nodeList.iterator();
            int j = 0;
            while (iterator.hasNext()) {

                Node node = iterator.next();

                if (node.table.containsKey(item))
                {

                    Node temp = node.table.get(item);
                    if(temp.isLeaf()){
                        temp.leaf++;
                    }
                    else{
                        nodeList.add(node.table.get(item));
                    }
                }
                j++;
            }
//            System.out.println("item " + item + "tree "+root);
        });
//        System.out.println();

    }


    Map<List<Integer>, Integer> map;
    int[] array;
    int i;

    public Map<List<Integer>,Integer> create_table(int depth){
        if (!root.isLeaf())
        {
            map = new HashMap();
            array = new int[depth];
            i = 0;
            create_entry(root.table);
        }
        return map;
    }

    private void create_entry(Hashtable<Integer,Node> table){
        table.forEach((key,node)->
        {
            if (node.isLeaf())
            {
                if(node.leaf>=Main.S){
                    array[i] = key;
                    map.put(get_candidate(),node.leaf);
                }

            }
            else{
                array[i]=key;
                i++;
                create_entry(node.table);
            }
        });

        i--;

    }

    private List<Integer> get_candidate(){
        List<Integer> list1 = new ArrayList<>(array.length);
        for (int item:array) {
            list1.add(item);
        }
        return list1;
    }




}
