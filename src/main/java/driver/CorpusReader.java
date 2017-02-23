package driver;

import com.uttesh.exude.ExudeData;
import com.uttesh.exude.exception.InvalidDataException;
import engine.SearchEngine;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by turtle on 21/2/17.
 */
public class CorpusReader {
    String directory;

    public CorpusReader(String directory) {
        this.directory = directory;
    }

    public void populateSearchEngine(SearchEngine se){
        populateSearchEngine(se, directory);
    }


    // Add the vocabulary to the search engine
    private void populateSearchEngine(SearchEngine se, String file){
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
