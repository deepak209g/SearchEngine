package data_structures;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by turtle on 17/2/17.
 */
public class Trie {
    Node<Character> original;
    Node<Character> reverse;
    Map<String, Integer> docs; // how many words each document has

    public Trie() {
        this.original = new Node<>(false);
        this.reverse = new Node<>(false);
        this.docs = new HashMap<>();
    }

    public void insert(String str, String docId){
        if(str.length() == 0){
            return;
        }
        Node<Character> tempF = original;
        Node<Character> tempR = reverse;
        str = str.toLowerCase();

        if(docs.containsKey(docId)){
            int totalwords = docs.get(docId) + 1;
            docs.put(docId, totalwords);
        }else{
            docs.put(docId, 1);
        }

//        Insert the word
        //traverse to the final node
        for(int i=0; i<str.length(); i++){
            char cF = str.charAt(i);
            char cR = str.charAt(str.length() - i - 1);

            if(tempF.search(cF) >= 0){
                // key present
                tempF = tempF.nodes.get(tempF.search(cF)).second;
                if(i == str.length()-1){
                    // last char
                    if(tempF.docs == null){
                        tempF.docs = new HashMap<>();
                    }
                }
            }else{
                if(i==str.length()-1){
                    // last char
                    tempF.insertLeafNode(cF);
                    tempF = tempF.nodes.get(tempF.search(cF)).second;
                }else{
                    tempF.insertInnerNode(cF);
                    tempF = tempF.nodes.get(tempF.search(cF)).second;
                }

            }

            if(tempR.search(cR) >= 0){
                // key present
                tempR = tempR.nodes.get(tempR.search(cR)).second;
                if(i == str.length()-1){
                    // last char
                    if(tempR.docs == null){
                        tempR.docs = new HashMap<>();
                    }
                }
            }else{
                if(i==str.length()-1){
                    // last char
                    tempR.insertLeafNode(cR);
                    tempR = tempR.nodes.get(tempR.search(cR)).second;
                }else{
                    tempR.insertInnerNode(cR);
                    tempR = tempR.nodes.get(tempR.search(cR)).second;
                }

            }

        }

        Map<String, Integer> mapF = tempF.docs;
        Map<String, Integer> mapR = tempR.docs;

        if(mapF.containsKey(docId)){
            int freq = mapF.get(docId);
            mapF.put(docId, freq+1);
            mapR.put(docId, freq+1);
        }else{
            mapF.put(docId, 1);
            mapR.put(docId, 1);
        }

    }

    public void printDictionary(){
        printDictionary(this.original, "");
    }

    private void printDictionary(Node<Character> root, String st){
        for(int i=0; i<root.nodes.size(); i++){
            Node temp = root.nodes.get(i).second;
            char c = root.nodes.get(i).first;
//            st += c;
            if(temp.docs != null){
                System.out.println(st+c);
            }
            printDictionary(temp, st+c);
        }
    }

    private Map<String, Integer> searchForward(String str){
        Node<Character> tempF = original;
        str = str.toLowerCase();
        for(int i=0; i<str.length(); i++){
            char cF = str.charAt(i);
            if(tempF.search(cF) >= 0){
                // key present
                tempF = tempF.nodes.get(tempF.search(cF)).second;
            }else{
                // word is not present
                return null;
            }
        }

        if(tempF.docs != null){
            return tempF.docs;
        }
        return null;
    }

    public void searchPrint(String key){
        Map<String, Integer> mp = searchForward(key);
        for(String s: mp.keySet()){
            System.out.println(s);
        }
    }
}
