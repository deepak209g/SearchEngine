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

        t.insert("abcd", "doc1");
        t.insert("bcd", "doc1");
        t.insert("cdasdf", "doc1");
        t.insert("ghraebcd", "doc1");
        t.insert("htabcd", "doc1");




        t.trie.printDictionary();

    }
}
