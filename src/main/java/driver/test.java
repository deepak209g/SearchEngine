package driver;

//import com.uttesh.exude.ExudeData;
//import com.uttesh.exude.exception.InvalidDataException;
import com.sun.media.sound.InvalidDataException;
import engine.SearchEngine;

import java.util.Scanner;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]) throws InvalidDataException {
        SearchEngine searchEng = new SearchEngine();
        CorpusReader reader = new CorpusReader("D:/mini_newsgroups");
        reader.populateSearchEngine(searchEng);
        System.out.println("Enter Query:");
        Scanner sc = new Scanner(System.in);
        String query = new Scanner(System.in).nextLine();
        System.out.println("Enter number between 1 and 3");
        System.out.println("● Entering “1” should display the result of tokenization and normalization of the query.\n"+
        "● Entering “2” should display the result of the spell correction of the query. \n"+
        "● Entering “3” should fetch the documents for the query. \n");
        int n=sc.nextInt();
        if(n==1){

        }else if(n==2){
            searchEng.spellChecker(query);
        }else{
            if(query.contains("*"))
                searchEng.searchWildTerm(query,query.length());
            else
                searchEng.executeQuery(query);
        }

        //String query="apoll";
        //t.spellChecker(query);
    }
}
