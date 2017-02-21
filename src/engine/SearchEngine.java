package engine;

import data_structures.Trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by turtle on 17/2/17.
 */
public class SearchEngine {
    Map<String, Integer> docs; // how many words each document has
    Trie trie;

    public SearchEngine() {
        this.trie = new Trie();
        this.docs = new HashMap<>();
    }

    public void insert(String key, String docId){
        trie.insert(key, docId);
        // update the number of words in the current document
        if(docs.containsKey(docId)){
            int totalwords = docs.get(docId) + 1;
            docs.put(docId, totalwords);
        }else{
            docs.put(docId, 1);
        }
    }


}
