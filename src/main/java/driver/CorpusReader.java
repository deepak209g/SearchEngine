package driver;

import engine.SearchEngine;
import engine.Stemmer;
import org.tartarus.snowball.ext.PorterStemmer;

import java.io.*;

/**
 * Created by turtle on 21/2/17.
 */
public class CorpusReader {
    String directory;
    Stemmer stemmer;




    public CorpusReader(String directory) {
        this.directory = directory;
    }

    public void populateSearchEngine(SearchEngine se){
        populateSearchEngine(se, directory);
    }


    // Add the vocabulary to the search engine
    private void populateSearchEngine(SearchEngine se, String file){
        stemmer = new Stemmer();
        File temp = new File(file);
        if(temp.isFile()){
            // read contents
            System.out.println("reading file:  " + file);
            BufferedReader br = null;

            try {

                String sCurrentLine;
                br = new BufferedReader(new FileReader(file));

                while ((sCurrentLine = br.readLine()) != null) {

                    // got a line from file
                    // add it to the engine
                    sCurrentLine = sCurrentLine.replaceAll("[^a-zA-Z0-9]"," ");
                    String[] split = sCurrentLine.split("\\s+");
                    for(String token: split){
                        token = token.toLowerCase().trim();
                        char arr[] = token.toCharArray();
                        stemmer.add(arr, arr.length);
                        stemmer.stem();
                        token = stemmer.toString();
                        if(token.length()==0)
                            continue;
                        se.insert(token, file);
                        se.insertGram(token);  //for inserting bigrams
                    }



                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }else{
            for(String files : temp.list()){
                //System.out.println(files);
                populateSearchEngine(se, file + "/" +files);
            }
        }
    }

}
