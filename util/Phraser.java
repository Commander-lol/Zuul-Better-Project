package util;

import java.util.HashSet;
/**
 * Phraser is a class that is apart of the the "Go Sunset" application, adapted from the
 * "World of Zuul" application
 * 
 * The Phraser is used to make sure the text is grammatically correct. 
 * It does this by creating a HashSet and storing the values it in it.
 * It then checks whether the the first letter of the object is a vowel,
 * returning the correct prefix
 * 
 * @author Louis Capitanchik
 * @author Joshua Mulcock
 * @author Alice Charterton
 * @author John Stones
 * 
 * @version 2014.02.27
 */
public class Phraser {
	private static HashSet<String> vowels = new HashSet<String>();
	static {
		vowels.add("a");
		vowels.add("e");
		vowels.add("i");
		vowels.add("o");
		vowels.add("u");
	}
	
	/**
	 * Used to determine whether the sting starts with the a vowel, 
	 * by trimming the baseString to one letter and seeing if it matches
	 * any of the HashSet
	 * 
	 * @param baseString is the string containing the items name
	 * @return 'an ' and the baseString if it begins with a vowel
	 * @return 'a ' and the baseString if it doesnt being with a vowel
	 */
	public static String addDeterminer(String baseString){
		if(vowels.contains(baseString.trim().substring(0, 1).toLowerCase())){
			return "an " + baseString;
		} else {
			return "a " + baseString;
		}
	}
}
