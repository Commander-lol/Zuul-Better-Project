package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * A simple file reader containing static methods for reading and returning
 * basic .kv (key-value) files
 * @author Louis Capitanchik
 */
public class KvReader {

    /**
     * Reads the given file into a HashMap object and returns it.
     * @param filePath The path to the file that will be read and returned
     * @return A HashMap object populated with the contents of the file. Will
     *  return null if the file does not exist or throws an error on read.
     */
    public static HashMap<String, String> readFile(String filePath){
        HashMap<String, String> returnMap = new HashMap<>();
        FileReader fileInput;
        BufferedReader fileReader;
        try {
            fileInput = new FileReader(filePath);
            fileReader = new BufferedReader(fileInput);
            
        } catch (FileNotFoundException e) {
            return null;
        }
        
        String line;
        
        try {
            while((line = fileReader.readLine()) != null){
                line.trim();
                String[] kv = line.split(":");
                if(kv.length!=2){// Malformed line, ignore it
                    continue;
                }
                System.out.println("K: " + kv[0] + ", V: " + kv[1]);
                returnMap.put(kv[0].trim(), kv[1].trim());
            }
        } catch(IOException e) {
            return null;
        }
        
        try{
            fileReader.close();
            fileInput.close();
        } catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        return returnMap;
    }
    
}