import java.util.*;

/**
 * Created by root on 11/18/16.
 */
public class Candidates {

    Map<List<Integer>,Integer> pre_list;
    Map<List<Integer>,Integer> candidate_sets = new HashMap<>();


    public Candidates(Map<List<Integer>,Integer> list){
        pre_list = list;

    }

    public Map<List<Integer>,Integer> make_candidates(){
        List<List<Integer>> candidate_list= new ArrayList<>(pre_list.size());
        pre_list.forEach((key,value) -> {
            candidate_list.add(key);
        });


        for(int i = 0; i<pre_list.size()-1; i++){
            for(int j=i+1; j<pre_list.size(); j++){
                List<Integer> candidate = make_candidate(candidate_list.get(i),candidate_list.get(j));
                if(candidate!=null) {
                    candidate_sets.put(candidate, 0);
                }
            }
        }
        return candidate_sets;

    }



    private List<Integer> make_candidate(List<Integer> p, List<Integer> q){
        Iterator<Integer> it1 = p.iterator();
        Iterator<Integer> it2 = q.iterator();

        List<Integer> candidate = new ArrayList<>();
        Integer i1;
        Integer i2;

        int i = 0;
        while(i<p.size()-1){
            i1 = it1.next();
            i2 = it2.next();
            if (i1==i2)
            {
                candidate.add(i1);
                i++;
            }
            else
                return null;
        }
        i1 = it1.next();
        i2 = it2.next();
        if(i1<i2)
        {
            candidate.add(i1);
            candidate.add(i2);
        }
        else{
            candidate.add(i2);
            candidate.add(i1);
        }



        return prune(candidate);
    }

    private List<Integer> prune(List<Integer> candidate){
        List<List<Integer>> permut = generate_permut(candidate);
        Iterator<List<Integer>> it = permut.iterator();

        while(it.hasNext()){
            if (!pre_list.containsKey(it.next())) {
                return null;
            }
        }
        return candidate;
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
