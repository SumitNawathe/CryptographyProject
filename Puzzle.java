import java.util.*;

public class Puzzle {
	public static final int UNTRANSLATED = 0;
	public static final int TRANSLATED = 1;
	public static final int UNTRANSLATABLE = 2;
	
	private String[] message;
	private Translation translation;
	private int[] statusArray;
	private int[] statusSummary;
	ArrayList<ArrayList<String>> matches;
	
	public String[] getMessage () { return message; }
	public Translation getTranslation () { return translation; }
	public int[] getStatus () { return statusArray; }
	public int[] getStatusSummary () { return statusSummary; }
	public ArrayList<ArrayList<String>> getMatches () { return matches; }
	
	public Puzzle (String[] message, PatternTable table) {
		this.message = message;
		translation = new Translation();
		statusArray = new int[message.length];
		statusSummary = new int[] {message.length, 0, 0};
		matches = new ArrayList<ArrayList<String>>();
		for (String word : message)
			matches.add(table.wordsWithSamePatternAs(word));
	}
	
	public int messageSize () {
		return message.length;
	}
	
	public String nthWord (int n) {
		return message[n];
	}
	
	public int statusOfWord (int n) {
		return statusArray[n];
	}
	
	public void setStatus (int n, int status) {
		statusSummary[statusArray[n]]--;
		statusSummary[status]++;
		statusArray[n] = status;
	}
	
	public int numberOfWordsWithStatus (int status) {
		return statusSummary[status];
	}
	
	public ArrayList<String> wordsWithSamePatternAs (int n) {
		ArrayList<String> patternMatches = matches.get(n);
		ArrayList<String> translationMatches = new ArrayList<String>();
		for (String str : patternMatches) {
			if (translation.couldTranslateTo(message[n], str))
				translationMatches.add(str);
		}
		return translationMatches;
	}
	
	/* Preconditions:
	 * (1) word is the same length as the nth word
	 * (2) word has the pattern of the nth word
	 * (3) nth word could be translated into word 
	 * 		based on the current letters
	 */
	public void translateNthWordTo (int n, String word) {
		String encoded = nthWord(n);
		for (int i = 0; i < encoded.length(); i++)
			translation.translateLetterTo(encoded.substring(i, i+1), word.substring(i, i+1));
		
		for (int i = 0; i < message.length; i++) {
			for (int j = 0; j < matches.get(i).size(); j++) {
				if (!translation.couldTranslateTo(message[i], matches.get(i).get(j))) {
					matches.get(i).remove(j);
					j--;
				}
			}
		}
	}
	
	public Puzzle (Puzzle p) {
		this.message = p.getMessage().clone();
		this.translation = new Translation(p.getTranslation());
		this.statusArray = p.getStatus().clone();
		this.statusSummary = p.getStatusSummary().clone();
		this.matches = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> arr : p.getMatches()) {
			matches.add((ArrayList<String>) arr.clone());
		}
	}
	
	public int easiestToTranslate () {
		for (int n = 1; n <= 2; n++) {
			for (int i = 0; i < messageSize(); i++) {
				if (statusOfWord(i) == Puzzle.UNTRANSLATED && message[i].length() == n) {
					return i;
				}
			}
		}
		
		int minPos = 0, minValue = Integer.MAX_VALUE;
		for (int i = 0; i < messageSize(); i++) {
			if (statusOfWord(i) == Puzzle.UNTRANSLATED && 
					matches.get(i).size() < minValue) {
				minValue = matches.get(i).size();
				minPos = i;
			}
		}
		return minPos;
	}
}
