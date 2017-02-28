package driver;

import data_structures.Trie;
import engine.SearchEngine;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]){
        SearchEngine t = new SearchEngine();

//        CorpusReader reader = new CorpusReader("D:/mini_newsgroups");
//        reader.populateSearchEngine(t);
        t.insert("abc","doc1");
        t.executeQuery("abd");
        t.searchWildTerm("*bc",3);
        t.searchWildTerm("a*c",3);
        t.trie.printDictionary();
//        String query="apol*";
//        t.searchWildTerm(query,query.length());
//        t.ngram.printDictionary();

    }
}
