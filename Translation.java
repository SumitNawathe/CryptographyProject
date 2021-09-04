import java.util.*;

public class Translation {
	private HashMap<String, String> decrypt;
	public HashMap<String, String> getMap () { return decrypt; }
	
	public void translateLetterTo (String encoded, String decoded) {
			decrypt.put(encoded.toUpperCase(), decoded.toUpperCase());
	}
	
	public boolean isTranslated (String letter) {
		return decrypt.containsKey(letter.toUpperCase());
	}
	
//	public String translation (String encoded) {
//		encoded = encoded.toUpperCase();
//		String decoded = "";
//		for (int i = 0; i < encoded.length(); i++)
//			decoded += translateLetter(encoded.substring(i, i+1));
//		return decoded;
//	}
	
	public String translation (String encoded) {
		encoded = encoded.toUpperCase();
		String decoded = "";
		for (int i = 0; i < encoded.length(); i++)
			if (isAlpha(encoded.substring(i, i+1)))
				decoded += translateLetter(encoded.substring(i, i+1));
			else
				decoded += encoded.substring(i, i+1);
		return decoded;
	}
	
	private boolean isAlpha (String letter) {
		letter = letter.toUpperCase().substring(0, 1);
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < alphabet.length(); i++)
			if (alphabet.substring(i, i+1).equals(letter))
				return true;
		return false;
	}
	
	public boolean canBeTranslated (String encoded) {
		encoded = encoded.toUpperCase();
		for (int i = 0; i < encoded.length(); i++)
			if (!decrypt.containsKey(encoded.substring(i, i+1)))
				return false;
		return true;
	}
	
	public boolean couldTranslateTo (String encoded, String word) {
		encoded = encoded.toUpperCase();
		word = word.toUpperCase();
		if (encoded.length() != word.length() || 
				!PatternMaker.makePattern(encoded).equals(
				PatternMaker.makePattern(word)))
			return false;
		
		for (int i = 0; i < encoded.length(); i++) {
			String decoded = translateLetter(encoded.substring(i, i+1));
			if ((!decoded.equals("*") && !decoded.equals(word.substring(i, i+1))) || 
					(decoded.equals("*") && 
					decrypt.containsValue(word.substring(i, i+1))))
				return false;
		}
		return true;
	}
	
	private String translateLetter (String letter) {
		if (isTranslated(letter))
			return decrypt.get(letter);
		return "*";
	}
	
	public Translation () {
		decrypt = new HashMap<String, String>();
	}
	
	public Translation (Translation t) {
		decrypt = (HashMap<String, String>) t.getMap().clone();
	}
	
	public String toString () {
		String answer = "";
		for (int i = 0; i < 26; i++) {
			answer += decrypt.getOrDefault(Character.toString((char) ('A'+i)).toUpperCase(), "*");
		}
		return answer;
	}
}
