public class WordMaker {
	private String word = "";
	private boolean wordReady = false;
	
	public void reset () {
		word = "";
		wordReady = false;
	}
	
	public void addChar (char newChar) {
		if (newChar == '\'')
			return;
		
		if (wordReady) {
			if (Character.isLetter(newChar)) {
				word = Character.toString(newChar);
				wordReady = false;
			}
		} else {
			if (Character.isLetter(newChar)) {
				word += Character.toString(newChar);
			} else {
				wordReady = true;
			}
		}
	}
	
	public boolean wordReady () {
		return wordReady;
	}
	
	public String getWord () {
		return word;
	}
}
