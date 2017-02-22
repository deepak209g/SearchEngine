package data_structures;

import engine.DocFreqPair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by turtle on 17/2/17.
 */
public class Trie {
    Node<Character> original;
    Node<Character> reverse;
    Map<String, Integer> docs;

    public Trie() {
        this.original = new Node<>(false);
        this.reverse = new Node<>(false);
        this.docs = new HashMap<>();
    }

    public void insert(String str, String docId){
        Node<Character> tempF = original;
        Node<Character> tempR = reverse;
        str = str.toLowerCase();

        if(str.length() == 0){
            return;
        }
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

                if(i == str.length()-1){
                    // last char
                    if(tempF.docs == null){
                        tempF.docs = new HashMap<>();
                    }
                }else{
                    tempF = tempF.nodes.get(tempF.search(cF)).second;
                }

            }else{
                if(i==str.length()-1){
                    // last char
                    tempF.insertLeafNode(cF);
                    //tempF = tempF.nodes.get(tempF.search(cF)).second;
                }else{
                    tempF.insertInnerNode(cF);
                    tempF = tempF.nodes.get(tempF.search(cF)).second;
                }

            }

            if(tempR.search(cR) >= 0){
                // key present

                if(i == str.length()-1){
                    // last char
                    if(tempR.docs == null){
                        tempR.docs = new HashMap<>();
                    }
                }else{
                    tempR = tempR.nodes.get(tempR.search(cR)).second;
                }
            }else{
                if(i==str.length()-1){
                    // last char
                    tempR.insertLeafNode(cR);
                    //tempR = tempR.nodes.get(tempR.search(cR)).second;
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
    public ArrayList<DocFreqPair> tfIdfUtility(Map<String, Integer> map, String str)
    {
        System.out.println("Entry");
        ArrayList<DocFreqPair>  toret = new ArrayList<>();
        double idf = Math.log(docs.size()/map.size());
        for(String key : map.keySet())
        {
            double tf = 1+Math.log(map.get(key));
            wildTerms.add(new DocFreqPair(key,str,tf,idf));
        }
//        return toret;
    }
    public void findWildTerms(Node<Character> root, String term, ArrayList<DocFreqPair> wildTerms)
    {
        for(int i=0; i<root.nodes.size(); i++) {
            if (root.docs != null){
                tfIdfUtility(root.docs,term,wildTerms);
            }
            char ch = root.nodes.get(i).first;
            Node temp = root.nodes.get(i).second;
            findWildTerms(temp,term+ch,wildTerms);
        }
    }
    public ArrayList<DocFreqPair> searchWild(String str,int i)
    {
        return searchWild(this.original,str,i);
    }
    public ArrayList<DocFreqPair> searchWild(Node<Character> root, String str,int i)
    {
        System.out.println(i);
        if(i==str.length()-1){
//           Map<String, Integer> map = root.docs; //when * is ""
            char c=str.charAt(i);
            int pos=root.search(c);
            Node<Character> temp = root.nodes.get(pos).second;
            ArrayList<DocFreqPair> wildTerms = new ArrayList<>();
            findWildTerms(temp,"",wildTerms);
            return wildTerms;
        }
        else{
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
                Node<Character> temp = root.nodes.get(pos).second;
                return searchWild(temp, str, i + 1);
            }
        }
    }
    public ArrayList<DocFreqPair> search(String str,int i){
        return search(this.original,str,i);
    }
    public ArrayList<DocFreqPair> search(Node<Character> root, String str,int i){

        System.out.println(i);
        if(i==str.length()-1){
            Map<String, Integer> map = root.docs;
//            if(map==null)
                System.out.println(map);
            System.out.println(map.size());
            if(map.size()==0)
                return null;
            else
                return tfIdfUtility(map,str);
        }
        else{
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
            Node<Character> temp = root.nodes.get(pos).second;
            return search(temp,str,i+1);
            }
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
            if(root.docs != null){
                System.out.println(st+c + " size :"+root.docs.size());
                //Map<String,>
            }
            printDictionary(temp, st+c);
        }
    }
}
