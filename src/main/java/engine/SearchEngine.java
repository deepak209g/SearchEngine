package engine;

import data_structures.Pair;
import data_structures.Trie;

import java.util.*;

/**
 * Created by turtle on 17/2/17.
 */
public class SearchEngine {
    public Trie trie;
    public Trie ngram;
    private static final int SPELL_CHECK_LENGTH_DIFFERENCE = 2;
    public SearchEngine() {
        this.trie = new Trie();
        this.ngram = new Trie();
    }

//    insert a term in the trie
    public void insert(String term, String docId){
        trie.insert(term, docId);
    }


    // To confirm
    public void searchWildTerm(String query,int len) {
        query=query.substring(0,len-1);
        System.out.println(query);
        ArrayList<DocFreqPair> wildTerms = trie.searchPostWild(query,0);
        System.out.println(wildTerms);
        System.out.println(wildTerms.size());
    }


    // Using a heuristic here
    // For spell check we consider only those words whose difference in length is not more than 2
    public void spellChecker(String query) {
        Map<String, Double> jaccard = new HashMap<String, Double>();
        ArrayList<String> queryBigrams = getBiGrams(query);
        int lenKey;
        int qKey = query.length();

        // iterate through all the bigrams of the query
        for (int i = 0; i < queryBigrams.size(); i++) {
            Map<String, Integer> words = ngram.searchGram(queryBigrams.get(i));
            if(words==null)
                continue;
            for (String key : words.keySet()) {
                lenKey = key.length();
                if(Math.abs(qKey-lenKey) <= SPELL_CHECK_LENGTH_DIFFERENCE) {
                    if (jaccard.containsKey(key)) {
                        double num = jaccard.get(key);
                        num += 1.0;
                        jaccard.put(key, num);
                    } else {
                        jaccard.put(key, 1.0);
                    }
                }
            }
        }

        calculateJaccardCoffecient(jaccard,query);
    }


    public void calculateJaccardCoffecient(Map<String, Double> jaccard,String query){
        ArrayList<Double> coefficients = new ArrayList<Double>();
        for(String key:jaccard.keySet()){
            double num = jaccard.get(key);
            double deno =  query.length() + key.length() + 2 - num;
            double ans = num/deno;
            if(ans > 0.3){
                System.out.println(ans+" key: "+key + " num: " + num + " den: " + deno);
            }
        }
    }


    // To confirm
    // End game. Executes the final query of the user
    public void executeQuery(String query) {
        PriorityQueue<Pair<Double, String>> queue = new PriorityQueue(1000, Collections.reverseOrder());
        StringTokenizer tokens = new StringTokenizer(query);
        Map<String, Double> results = new HashMap();
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
                            queue.add(new Pair(new Double(score), itr.docid));
                        }
                    } else {
                        queue.remove(new Pair(new Double(score), itr.docid));
                        score += results.get(itr.docid);
                        results.put(itr.docid, score);
                        queue.add(new Pair(new Double(score), itr.docid));
                    }
                }
            }
            while (queue.size() > 0) {
                Pair<Double,String> p=queue.poll();
                System.out.println(p.first+" "+p.second);
            }
        }
    }

    // insert Bigrams in ngrag Trie
    public void insertGram(String word){
        ArrayList<String> bigrams = getBiGrams(word);
        for (int i = 0; i < bigrams.size(); i++)
            ngram.insertGramTrie(bigrams.get(i),word);
    }

    public ArrayList<String> getBiGrams(String token){
        int len=token.length();
//        System.out.println(len);
        ArrayList<String> bigrams = new ArrayList();
        bigrams.add("$"+token.charAt(0));
        for(int i=0;i<len-1;i++)
        {
            String gram=""+token.charAt(i)+token.charAt(i+1);
            bigrams.add(gram);
        }
        bigrams.add(token.charAt(len-1)+"$");
        return bigrams;
    }
}
