package engine;

import data_structures.Pair;
import data_structures.Trie;

import java.util.*;

/**
 * Created by turtle on 17/2/17.
 */
public class SearchEngine {
    public Trie trie;

    public SearchEngine() {
        this.trie = new Trie();
    }

    public void insert(String term, String docId){
        trie.insert(term, docId);
    }
    public void searchPostWild(String query,int len)
    {
        query=query.substring(0,len-1);
        System.out.println(query);
        ArrayList<DocFreqPair> wildTerms = trie.searchWild(query,0);
        System.out.println(wildTerms);
        System.out.println(wildTerms.size());
    }
    public void executeQuery(String query) {
        PriorityQueue<Pair<Double, String>> queue = new PriorityQueue<>(1000, Collections.reverseOrder());
        StringTokenizer tokens = new StringTokenizer(query);
        Map<String, Double> results = new HashMap<>();
        while (tokens.hasMoreTokens()) {
            String term = tokens.nextToken().toLowerCase().trim();
            System.out.println(term);
            ArrayList<DocFreqPair> res = trie.search(term, 0);
            if (res == null)
                System.out.println("Object Not found");
            else {
                for (DocFreqPair itr : res) {
                    double score = itr.tf * itr.idf;
                    if (!results.containsKey(itr.docid)) {
                        {
                            results.put(itr.docid, score);
                            queue.add(new Pair<>(new Double(score), itr.docid));
                        }
                    } else {
                        queue.remove(new Pair<>(new Double(score), itr.docid));
                        score += results.get(itr.docid);
                        results.put(itr.docid, score);
                        queue.add(new Pair<>(new Double(score), itr.docid));
                    }
                }
            }
            while (queue.size() > 0) {
                Pair<Double,String> p=queue.poll();
                System.out.println(p.first+" "+p.second);
            }
        }
    }
}
