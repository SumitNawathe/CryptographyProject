import java.io.*;

public class WordGetter {
	public void getWords(WordDictionary dict, String filename) {
		WordMaker wordMaker = new WordMaker();
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(filename));
			int input;
			while((input = file.read()) != -1) {
				wordMaker.addChar((char) input);
				if (wordMaker.wordReady() && wordMaker.getWord().length() != 0) {
					dict.addWord(wordMaker.getWord().toUpperCase());
//					System.out.println(wordMaker.getWord().toUpperCase());
					wordMaker.reset();
				}
			}
		} catch (Exception e) {}
		if (file != null)
			try {
				file.close();
			} catch (Exception e) {}
	}
}
