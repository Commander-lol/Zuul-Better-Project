package util;

import java.util.HashSet;

public class Phraser {
	private static HashSet<String> vowels = new HashSet<String>();
	static {
		vowels.add("a");
		vowels.add("e");
		vowels.add("i");
		vowels.add("o");
		vowels.add("u");
	}
	public static String addDeterminer(String baseString){
		if(vowels.contains(baseString.trim().substring(0, 1).toLowerCase())){
			return "an " + baseString;
		} else {
			return "a " + baseString;
		}
	}
}
