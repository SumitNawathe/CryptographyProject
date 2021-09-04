import java.util.*;

public class PatternMaker {
	public static String makePattern (String input) {
		String answer = "";
		input = input.toLowerCase();
		ArrayList<Character> lettersUsed = new ArrayList<Character>();
		for (char character : input.toCharArray()) {
			if (!lettersUsed.contains(character)) {
				lettersUsed.add(character);
			}
			answer += (char) ('A' + lettersUsed.indexOf(character));
		}
		return answer;
	}
}
