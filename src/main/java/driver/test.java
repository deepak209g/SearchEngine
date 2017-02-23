package driver;

import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;
import engine.SearchEngine;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]) throws InvalidDataException {
        SearchEngine t = new SearchEngine();
        CorpusReader reader = new CorpusReader("/home/turtle/Downloads/mini_newsgroups");
        reader.populateSearchEngine(t);

//        String query="apol*";
//        t.searchWildTerm(query,query.length());

//        String inputData = "Kannada is a Southern Dravidian language, and according to Dravidian scholar Sanford Steever, its history can be conventionally divided into three periods; Old Kannada (halegannada) from 450–1200 A.D., Middle Kannada (Nadugannada) from 1200–1700 A.D., and Modern Kannada from 1700 to the present.[20] Kannada is influenced to an appreciable extent by Sanskrit. Influences of other languages such as Prakrit and Pali can also be found in Kannada language.";
//        String output = ExudeData.getInstance().filterStoppings(inputData);
//        System.out.println(output);
//        System.out.println(inputData.length());
//        System.out.println(output.length());
        String query="apoll";
        t.spellChecker(query);
    }
}
