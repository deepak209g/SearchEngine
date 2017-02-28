package engine;

import javax.print.Doc;

/**
 * Created by turtle on 17/2/17.
 */
public class DocFreqPair {
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

    public String toString() {
        return "Doc id: " + docid + ", term: " + term + ", tf: " + tf + ", idf: " + idf;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        DocFreqPair pair = (DocFreqPair) obj;
        return (this.docid.equalsIgnoreCase(pair.docid)) && (this.idf == pair.idf) && (this.term.equalsIgnoreCase(pair.term)) && (this.tf == pair.tf);
    }


}
