package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;;

/**
 * A simple file reader containing static methods for reading and returning
 * basic .kv (key-value) files
 * @author Louis Capitanchik
 */
public class KvReader {

    /**
	 * A pre-compiled pattern to check that the filename section of a path represents a kv file
	 * while also allowing extension based invalidation in multiple formats (e.g. an editor creates
	 * a backup with the extension .bck.kv <em>or</em> .kv.bck that should not be loaded because of
	 * duplication).
	 */
    public static final Pattern KvPattern = Pattern.compile("^[\\w]+([\\w\\s]*\\w+)\\.kv$");

    /**
     * Reads the given file into a HashMap object and returns it.
     * @param filePath The path to the file that will be read and returned
     * @return A HashMap object populated with the contents of the file. Will
     *  return null if the file does not exist or throws an error on read.
     */
    public static HashMap<String, String> readFile(String filePath){
        HashMap<String, String> returnMap = new HashMap<String, String>();
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
                
                returnMap.put(KvReader.cleanText(kv[0]), KvReader.cleanText(kv[1]));
            }
        } catch(IOException e) {
            return null;
        }
        
        try {
            fileReader.close();
            fileInput.close();
        } catch(IOException e){
            e.printStackTrace(System.err);
        }
        
        return returnMap;
    }
    
    
    /**
     * Recursively searches through the given directory and creates a set of relative paths
     * to every .kv file found. If dir is not a directory, an empty HashSet will be returned
     * unless dir is itself a .kv file, in which case the HashSet will contain dir
     * @param dir The root directory to search for .kv files
     * @return A HashSet containing the relative paths of every .kv file found
     * @throws IOException Throws an IOException if it fails to create a DirectoryStream from the given string
     */
    public static HashSet<String> getKvFiles(String dir) throws IOException {
        HashSet<String> returnSet = new HashSet<>();
        Matcher fileMatch = KvPattern.matcher(dir);
        Path init = Paths.get(dir);
        
        if (Files.exists(init)){
            if (Files.isDirectory(init)){
                Iterator<Path> dirStream = Files.newDirectoryStream(init).iterator();
                while(dirStream.hasNext()){
                    Path nextPath = dirStream.next();
                    if(Files.isDirectory(nextPath)){
                        returnSet.addAll(getKvFiles(nextPath.toString()));
                    } else {
                        fileMatch.reset(nextPath.getFileName().toString());
                        if(fileMatch.matches()){
                            returnSet.add(nextPath.toString());
                        }
                    }
                }
            } else if(fileMatch.matches()) {
                returnSet.add(dir);
            }
        }
        return returnSet;
    }
    
    /**
     * Replaces some simple file based formatting elements with the appropriate tokens:
     * <ul>
     * 	<li> \n is replaced with a newline </li>
     * 	<li> ⇥ is replaced with a tab </li>
     * </ul>
     * Also trims trailing and leading whitespace
     * 
     * @param input The input string that will be cleaned
     * @return A clean version of the input string, with formatting applied and whitespace trimmed
     */
    private static String cleanText(String input){
        input = input.trim();
		input = input.replaceAll("\\\\n", "\n");
		input = input.replaceAll("⇥", "\t");
		return input;
    }
}
