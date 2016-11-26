import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 11/20/16.
 */
public class Assosiation_rules {
    List<Map<List<Integer>,Integer>> F ;
    List<String> consequents;

    public Assosiation_rules(List<Map<List<Integer>,Integer>> F){
        this.F = F;
    }

    public List<String> getConsequents(){
        consequents = new LinkedList();

        int i = F.size()-1;
        while(i>0){
            int j = i;
            F.get(i).forEach((itemset,count)->
            {
                List<List<Integer>> H1 = generate_permut(itemset);
                genrules(itemset,H1,(double) count,j);
            });
            i--;

        }

        return consequents;
    }



    private void genrules(List<Integer> itemset,List<List<Integer>> permuts, double count,int permut_level ){
        System.out.println("size:"+permuts.size());
        permuts.forEach(permut->{
            double base = (double)F.get(permut_level-1).get(permut);
            if(count/base>=Main.C){
                Consequent cons = new Consequent(itemset,permut);
                if(!consequents.contains(cons.toString()))
                {consequents.add(cons.toString());
                    if(permut_level>1){
                        genrules(permut,generate_permut(permut),count,permut_level-1);
                    }
                }



            }
        });
    }

    private List<List<Integer>> generate_permut(List<Integer> set){
        List<List<Integer>> permutations = new LinkedList<>();

        int i = 0;
        while(i<set.size()){
            List<Integer> permut = new ArrayList<>(set.size()-1);
            int j = 0;
            while(j<set.size()){
                if(i!=j) permut.add(set.get(j));
                j++;
            }
            i++;
            permutations.add(permut);
        }
        return permutations;

    }



}
