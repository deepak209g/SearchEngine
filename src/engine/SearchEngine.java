package engine;

import data_structures.Trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by turtle on 17/2/17.
 */
public class SearchEngine {

    public Trie trie;

    public SearchEngine() {
        this.trie = new Trie();
    }

    public void insert(String key, String docId){
        trie.insert(key, docId);
        // update the number of words in the current document
    }


}
