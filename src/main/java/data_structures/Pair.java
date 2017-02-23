package data_structures;

/**
 * Created by turtle on 17/2/17.
 */
public class Pair<P extends Comparable<P>, Q> implements Comparable<Pair<P, Q>> {
    public P first;
    public Q second;

    public Pair(P first, Q second) {
        this.first = first;
        this.second = second;
    }

    public int compareTo(Pair<P, Q> pqPair) {
        return this.first.compareTo(pqPair.first);
    }
}
