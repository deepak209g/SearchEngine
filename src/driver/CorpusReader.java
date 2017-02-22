package driver;

import engine.SearchEngine;

import java.io.*;
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
                    //tok = new StringTokenizer(sCurrentLine);
                    sCurrentLine = sCurrentLine.replaceAll("[^a-zA-Z]"," ");
                    String[] split = sCurrentLine.split("\\s");
                    for(String token: split){
                        token = token.toLowerCase();
                        se.insert(token, file);
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
