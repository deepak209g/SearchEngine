package data_structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by turtle on 17/2/17.
 */
public class Node<E extends Comparable<E>>{
    ArrayList<Pair<E, Node>> nodes;
    Map<String, Integer> docs;

    public Node(boolean isLeaf) {
        if(isLeaf){
            this.nodes = new ArrayList<Pair<E, Node>>();
            this.docs = null;
        }else{
            this.nodes = new ArrayList<Pair<E, Node>>();
            this.docs = null;
        }
    }

    public int search(E key) {
        Pair<E, Node> temp = new Pair<E, Node>(key, null);
        int pos = Collections.binarySearch(this.nodes, temp);
//        if pos < 0 the key is new else the key is already present
        return pos;
    }

    public void insertInnerNode(E key){
        Pair<E, Node> temp = new Pair<E, Node>(key, new Node(false));
        int pos = Collections.binarySearch(this.nodes, temp);
        //System.out.println("inserting " + key + " at position : " + pos);
        this.nodes.add(-pos-1, temp);
    }

    public void insertLeafNode(E key){
        Pair<E, Node> temp = new Pair<E, Node>(key, new Node(true));
        int pos = Collections.binarySearch(this.nodes, temp);
        this.nodes.add(-pos-1, temp);
        this.docs = new HashMap<>();
    }

}
