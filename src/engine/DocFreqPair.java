package engine;

/**
 * Created by turtle on 17/2/17.
 */
public class DocFreqPair{
    String docid;
    String term;
    double tf;
    double idf;
    public DocFreqPair(String docid, String term, double tf, double idf) {
        super();
        this.docid = docid;
        this.tf = tf;
        this.term = term;
        this.idf = idf;
    }
    public String toString(){
        return "Doc id: " + docid + ", term: " + term + ", tf: " + tf + ", idf: " + idf;
    }
}
