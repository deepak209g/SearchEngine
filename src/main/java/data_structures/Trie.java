package data_structures;

import engine.DocFreqPair;

import javax.print.Doc;
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
        this.original = new Node(false);
        this.reverse = new Node(false);
        this.docs = new HashMap();
    }

    // insert a bigram in the trie
    public void insertGramTrie(String str,String word) {
        Node<Character> tempF = this.original;
        str=str.toLowerCase();
        if(str.length() == 0){
            return;
        }
        for(int i=0;i<str.length();i++){
            char cF=str.charAt(i);
            if(tempF.search(cF)>=0){
                // character found
                tempF = tempF.nodes.get(tempF.search(cF)).second;
            }else{
                // character not found
                if(i==str.length()-1){
                    // last character of the bigram
                    tempF.insertLeafNode(cF);
                }else{
                    tempF.insertInnerNode(cF);
                }
                tempF = tempF.nodes.get(tempF.search(cF)).second;
            }
        }
        tempF.insertWord(word);
    }

    // Insert a string into the trie with the docId of the containing document
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
                tempF = tempF.nodes.get(tempF.search(cF)).second;

            }else{
                if(i==str.length()-1){
                    // last char
                    tempF.insertLeafNode(cF);
                }else{
                    tempF.insertInnerNode(cF);
                }
                tempF = tempF.nodes.get(tempF.search(cF)).second;

            }

            if(tempR.search(cR) >= 0){
                // key present
                tempR = tempR.nodes.get(tempR.search(cR)).second;
            }else{
                if(i==str.length()-1){
                    // last char
                    tempR.insertLeafNode(cR);
                }else{
                    tempR.insertInnerNode(cR);
                }
                tempR = tempR.nodes.get(tempR.search(cR)).second;
            }

        }


        if(tempF.docs == null){
            tempF.docs = new HashMap<String, Integer>();
        }
        if(tempR.docs == null){
            tempR.docs = new HashMap<String, Integer>();
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


    // To confirm
     public void tfIdfUtility(Map<String, Integer> map, String str,ArrayList<DocFreqPair> wildTerms) {
//        ArrayList<DocFreqPair>  toret = new ArrayList<DocFreqPair>();
        double idf = Math.log(docs.size()/map.size())/Math.log(10.0);
        for(String key : map.keySet())
        {
            double tf = 1+(Math.log(map.get(key))/Math.log(10.0));
//            System.out.println(map.get(key));
//            System.out.println(tf);
//            System.out.println(idf);
            wildTerms.add(new DocFreqPair(key,str,tf,idf));
        }
//        return toret;
    }

    // To confirm
    public void findWildTerms(Node<Character> root, String term, ArrayList<DocFreqPair> wildTerms,int flag) {
        if (root.docs != null){
            tfIdfUtility(root.docs,term,wildTerms);
        }
        for(int i=0; i<root.nodes.size(); i++) {
            char ch = root.nodes.get(i).first;
            Node temp = root.nodes.get(i).second;
            if(flag==0)
                findWildTerms(temp,ch+term,wildTerms,flag);
            else
                findWildTerms(temp,term+ch,wildTerms,flag);
        }
    }

//    public ArrayList<DocFreqPair> Intersection(ArrayList<DocFreqPair> postWildTerms,ArrayList<DocFreqPair> preWildTerms){
//        ArrayList<DocFreqPair> wildTerms = new ArrayList<DocFreqPair>();
//        for(DocFreqPair itr:postWildTerms)
//        {
//            if(preWildTerms.contains(itr))
//                wildTerms.add(itr);
//        }
//        return wildTerms;
//    }
    // To confirm
    public ArrayList<DocFreqPair> searchInwild(String str) {
        int len = str.length();
        int pos = str.indexOf('*');
        String postTerm = str.substring(0,pos);
        String preTerm = str.substring(pos+1,len);
        ArrayList<DocFreqPair> postWildTerms = searchPostWild(postTerm,0);
        ArrayList<DocFreqPair> preWildTerms = searchPreWild(preTerm,0);
//        System.out.println(preWildTerms);
//        System.out.println(postWildTerms);
        postWildTerms.retainAll(preWildTerms);
        return postWildTerms;
    }
    public ArrayList<DocFreqPair> searchPreWild(String str,int i) {
        String reverseString = "";

        for(int j=0;j<str.length();j++)
            reverseString=str.charAt(j)+reverseString;

        return searchWild(this.reverse,reverseString,i,"",0);
    }

    // To confirm
    public ArrayList<DocFreqPair> searchPostWild(String str,int i)
    {
        return searchWild(this.original,str,i,"",1);
    }

    // To confirm
    public ArrayList<DocFreqPair> searchWild(Node<Character> root, String str,int i,String term,int flag) {
//        System.out.println(i);
        Node<Character> temp;
        if(i==str.length()-1){
//           Map<String, Integer> map = root.docs; //when * is ""
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else {
                temp = root.nodes.get(pos).second;
                ArrayList<DocFreqPair> wildTerms = new ArrayList();
                if(flag==0)
                    findWildTerms(temp,c+term, wildTerms,flag);
                else
                    findWildTerms(temp,term+c, wildTerms,flag);

                return wildTerms;
            }
        }
        else{
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
                temp = root.nodes.get(pos).second;
                if(flag==0)
                    return searchWild(temp, str, i + 1,c+term,flag);
                else
                    return searchWild(temp, str, i + 1,term+c,flag);
            }
        }
    }



    public Map<String,Integer> searchGram(String str) {
        return searchGram(this.original,str,0);
    }

    private Map<String,Integer> searchGram(Node<Character> root,String str,int i) {
        Node<Character> temp;
        if(i == str.length()-1){
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
                temp = root.nodes.get(pos).second;
                return temp.docs;
            }

        } else{
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
                temp = root.nodes.get(pos).second;
                return searchGram(temp,str,i+1);
            }
        }
    }


    public ArrayList<DocFreqPair> search(String str,int i){
        return search(this.original,str,i);
    }

    public ArrayList<DocFreqPair> search(Node<Character> root, String str,int i){
        Node<Character> temp;
        if(i==str.length()-1){
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else {
                temp= root.nodes.get(pos).second;
                Map<String, Integer> map=temp.docs;
                if (map.size() == 0)
                    return null;
                else {
                    ArrayList<DocFreqPair> simpleTerms = new ArrayList();
                    tfIdfUtility(map, str, simpleTerms);
                    return simpleTerms;
                }
            }
        }
        else{
            char c=str.charAt(i);
            int pos=root.search(c);
            if(pos<0)
                return null;
            else{
                temp = root.nodes.get(pos).second;
                return search(temp,str,i+1);
            }
        }
    }


    // Public wrapper for printDictionary
    public void printDictionary(){
        printDictionary(this.original, "");
    }

    // Print the entire Trie/ Print the entire vocabulary
    private void printDictionary(Node<Character> root, String st){
        if(root.docs != null){
            System.out.println(st + " size :"+root.docs.size());
        }
        for(int i=0; i<root.nodes.size(); i++){
            Node temp = root.nodes.get(i).second;
            char c = root.nodes.get(i).first;
            printDictionary(temp, st+c);
        }
    }
}
