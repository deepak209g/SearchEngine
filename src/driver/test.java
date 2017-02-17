package driver;

import data_structures.Trie;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]){
        Trie t = new Trie();
        t.insert("abcd", "doc1");
        t.insert("abcde", "doc1");
        t.insert("abcdasfd", "doc1");
        t.insert("abcdx", "doc1");
        t.printDictionary();
    }
}
