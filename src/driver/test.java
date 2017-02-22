package driver;

import data_structures.Trie;
import engine.SearchEngine;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]){
        SearchEngine t = new SearchEngine();

        CorpusReader reader = new CorpusReader("D:/mini_newsgroups");
        reader.populateSearchEngine(t);

        t.trie.printDictionary();
        String query="apol*";
        t.searchPostWild(query,query.length());

    }
}
