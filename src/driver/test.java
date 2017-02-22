package driver;

import data_structures.Trie;
import engine.SearchEngine;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]){
        SearchEngine t = new SearchEngine();

        CorpusReader reader = new CorpusReader("/home/turtle/Downloads/mini_newsgroups");
        reader.populateSearchEngine(t);

        t.trie.printDictionary();
        t.executeQuery("apollo");

    }
}
