package driver;

//import com.uttesh.exude.ExudeData;
//import com.uttesh.exude.exception.InvalidDataException;
import com.sun.media.sound.InvalidDataException;
import engine.SearchEngine;
import engine.Stemmer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by turtle on 17/2/17.
 */
public class test {
    public static void main(String args[]) throws InvalidDataException {

        SearchEngine searchEng = new SearchEngine();
        CorpusReader reader = new CorpusReader(args[0]);
        reader.populateSearchEngine(searchEng);
        Scanner sc = new Scanner(System.in);
        Stemmer stemmer = new Stemmer();
        while(true){
            System.out.println("Enter Query:");

            String query = new Scanner(System.in).nextLine();

            String tokens[] = query.split("\\s+");

            List<String> stemmedTokens = new ArrayList<String>();
            for(String token : tokens){
                char tokArray[] = token.toCharArray();
                stemmer.add(tokArray, tokArray.length);
                stemmer.stem();
                stemmedTokens.add(stemmer.toString());
            }


            System.out.println("Enter number between 1 and 3");
            System.out.println("● Entering “1” should display the result of tokenization and normalization of the query.\n"+
                    "● Entering “2” should display the result of the spell correction of the query. \n"+
                    "● Entering “3” should fetch the documents for the query. \n");
            int n=sc.nextInt();

            if(n==1){
                System.out.println(stemmedTokens);
            }else if(n==2){
                searchEng.spellChecker(query);
            }else{
                if(query.contains("*"))
                    searchEng.searchWildTerm(query,query.length());
                else
                    searchEng.executeQuery(stemmedTokens);
            }
        }


        //String query="apoll";
        //t.spellChecker(query);
    }
}
