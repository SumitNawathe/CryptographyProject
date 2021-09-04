import java.util.*;

public class Drivers {
	public static void main(String[] args) {
//		WordDictionary dictionary = new WordDictionary(new HashSet<String>());
//		(new WordGetter()).getWords(dictionary, "english-words.txt");
//		System.out.println(dictionary.size());
//		dictionary.resetList();
//		PatternTable patternTable = new PatternTable();
//		String word;
//		while ((word = dictionary.nextWord()) != null) {
//			patternTable.addWord(word);
//		}
//		String testWord = "obscurity";
//		System.out.println(patternTable.wordsWithSamePatternAs(testWord));
//		System.out.println(patternTable.wordsWithSamePatternAs(testWord).size());
//		wordGetterDriver();
		
		puzzleDriver();
	}
	
	public static void puzzleDriver () {
		WordDictionary dictionary = new WordDictionary(new HashSet<String>());
		(new WordGetter()).getWords(dictionary, "english-words.txt");
		System.out.println("Dictionary Size: " + dictionary.size());
		dictionary.resetList();
		PatternTable patternTable = new PatternTable();
		String x;
		while ((x = dictionary.nextWord()) != null) {
			patternTable.addWord(x);
		}
		
		Puzzle puzzle = new Puzzle("NXDDNZ ODTFAZO LZNN STZJD FJAO".split(" "), patternTable);
		System.out.println("message: " + puzzle.getMessage());
		System.out.println("messageSize: " + puzzle.messageSize());
		System.out.println("----------");
		
		System.out.println(puzzle.wordsWithSamePatternAs(0));
		System.out.println(puzzle.wordsWithSamePatternAs(2));
		System.out.println("translating 0th word to little");
		puzzle.translateNthWordTo(0, "little");
		puzzle.setStatus(0, Puzzle.TRANSLATED);
		for (int num : puzzle.getStatus())
			System.out.print(num + " ");
		System.out.println();
		System.out.println(puzzle.getTranslation().translation("NXDDAB"));
		System.out.println(puzzle.easiestToTranslate());
		System.out.println("-----");
		
		Puzzle puzzle2 = new Puzzle(puzzle);
		puzzle.getMessage()[0] = "blah";
		System.out.println(puzzle.getMessage()[0] + " " + puzzle2.getMessage()[0]);
		puzzle.getMatches().set(0, new ArrayList<String>());
		System.out.println(puzzle.getMatches().get(0).size() + " " + puzzle2.getMatches().get(0).size());
	}
	
	public static void translationDriver () {
		Translation translation = new Translation();
		for (String str : "A=J,B=I,C=B,D=E,E=Z,F=L,G=S,H=R,I=X,J=G,K=A,L=N,M=Y,N=Q,O=F,P=M,Q=C,R=T,S=O,T=D,U=H,V=P,W=V,X=U,Y=K,Z=W".split(",")) {
			translation.translateLetterTo(str.substring(2, 3), str.substring(0, 1));
			System.out.println("encoded: " + str.substring(2, 3) + "  decoded: " + str.substring(0, 1));
		}
		System.out.println(translation.translation("NXDDNZ ODTFAZO LZNN STZJD FJAO"));
		System.out.println(translation.canBeTranslated("NXDDNZ ODTFAZO LZNN STZJD FJAO"));
		System.out.println(translation.canBeTranslated("NXDDNZ"));
		System.out.println("-----");
		
		translation = new Translation();
		translation.translateLetterTo("A", "Z");
		translation.translateLetterTo("B", "Y");
		translation.translateLetterTo("C", "X");
		System.out.println(translation.couldTranslateTo("AD", "ZW"));
		System.out.println("-----");
		
		Translation translation2 = new Translation(translation);
		translation.translateLetterTo("A", "D");
		translation.translateLetterTo("B", "E");
		translation.translateLetterTo("C", "F");
		System.out.println(translation.translation("ABC"));
		System.out.println(translation2.translation("ABC"));	
	}
	
	public static void wordGetterDriver2 () {
		WordDictionary dictionary = new WordDictionary(new HashSet<String>());
		(new WordGetter()).getWords(dictionary, "english-words.txt");
		System.out.println(dictionary.size());
		dictionary.resetList();
		PatternTable patternTable = new PatternTable();
		String word;
		while ((word = dictionary.nextWord()) != null) {
			patternTable.addWord(word);
		}
		System.out.println(patternTable.wordsWithSamePatternAs("simplicity").size());
	}
	
	public static void wordGetterDriver () {
		WordDictionary dict = new WordDictionary(new HashSet<String>());
		(new WordGetter()).getWords(dict, "english-words.txt");
		System.out.println(dict.size());
		dict.resetList();
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
		System.out.println(dict.nextWord());
	}
	
	public static void patternTableDriver () {
		PatternTable table = new PatternTable();
		for (String word : new String[] {"hello", "how", "are", "you", "doing", "on", "this", "fine", "day", "kill", "two", "birds", "with", "one", "stone", "finite", "simple", "times", "time", "cent", "i"})
			table.addWord(word);
		System.out.println("table: " + table.wordsWithSamePatternAs("table"));
		System.out.println("him: " + table.wordsWithSamePatternAs("him"));
		System.out.println("a: " + table.wordsWithSamePatternAs("a"));
		System.out.println("abccd: " + table.wordsWithSamePatternAs("abccd"));
	}
	
	public static void benchmarks () {
		String[] letters = new String[] {"a", "b", "c", "d", "e", "f", 
				"g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", 
				"r", "s", "t", "u", "v", "w", "x", "y", "z"};
		WordDictionary hashDictionary = new WordDictionary (new HashSet<String>());
		WordDictionary treeDictionary = new WordDictionary (new TreeSet<String>());
		long start;
		
		System.out.println("Benchmark 1: adding 26^4 words");
		start = System.currentTimeMillis();
		for (String i : letters)
			for (String j : letters)
				for (String k : letters)
					for (String l : letters)
						hashDictionary.addWord(i+j+k+l);
		System.out.println("HashSet: " + (System.currentTimeMillis()-start) + " milisec");
		start = System.currentTimeMillis();
		for (String i : letters)
			for (String j : letters)
				for (String k : letters)
					for (String l : letters)
						treeDictionary.addWord(i+j+k+l);
		System.out.println("TreeSet: " + (System.currentTimeMillis()-start) + " milisec");
		System.out.println();
		
		
		System.out.println("Benchmark 2: counting dictionary 10^5 times");
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			hashDictionary.size();
		System.out.println("HashSet: " + (System.currentTimeMillis()-start) + " milisec");
		start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++)
			treeDictionary.size();
		System.out.println("TreeSet: " + (System.currentTimeMillis()-start) + " milisec");
		System.out.println();
		
		
		System.out.println("Benchmark 3: querying isInDictionary for 26^4 words");
		start = System.currentTimeMillis();
		for (String i : letters)
			for (String j : letters)
				for (String k : letters)
					for (String l : letters)
						hashDictionary.isInDictionary(i+j+k+l);
		System.out.println("HashSet: " + (System.currentTimeMillis()-start) + " milisec");
		start = System.currentTimeMillis();
		for (String i : letters)
			for (String j : letters)
				for (String k : letters)
					for (String l : letters)
						treeDictionary.isInDictionary(i+j+k+l);
		System.out.println("TreeSet: " + (System.currentTimeMillis()-start) + " milisec");
		System.out.println();
		
		
		System.out.println("Benchmark 4: iterating through 26^4 words");
		start = System.currentTimeMillis();
		for (int i = 0; i < 26*26*26*26; i++)
			hashDictionary.nextWord();
		System.out.println("HashSet: " + (System.currentTimeMillis()-start) + " milisec");
		start = System.currentTimeMillis();
		for (int i = 0; i < 26*26*26*26; i++)
			treeDictionary.nextWord();
		System.out.println("TreeSet: " + (System.currentTimeMillis()-start) + " milisec");
		System.out.println();
	}
	
	public static void wordMakerDriver () {
		WordMaker wordMaker = new WordMaker();
		
		wordMaker.reset();
		System.out.println("Expected Output: energy");
		for (char c : new char[] {'e', 'n', 'e', 'r', 'g', 'y', ' '}) {
			wordMaker.addChar(c);
			if (wordMaker.wordReady()) {
				System.out.println(wordMaker.getWord());
				break;
			}
		}
		System.out.println("----------");
		
		wordMaker.reset();
		System.out.println("Expected Output: energy");
		for (char c : new char[] {'e', 'n', '\'', '\'', 'e', 'r', '\'', 'g', 'y', '\'', ' '}) {
			wordMaker.addChar(c);
			if (wordMaker.wordReady()) {
				System.out.println(wordMaker.getWord());
				break;
			}
		}
		System.out.println("----------");
		
		wordMaker.reset();
		System.out.println("Expected Output: true");
		wordMaker.addChar('x');
		wordMaker.addChar(' ');
		wordMaker.addChar(' ');
		System.out.println(wordMaker.wordReady());
		System.out.println("Expected Output: 4 false, 1 true, fish");
		for (char c : new char[] {'f', 'i', 's', 'h', ' '}) {
			wordMaker.addChar(c);
			System.out.println(wordMaker.wordReady());
			if (wordMaker.wordReady()) {
				System.out.println(wordMaker.getWord());
				break;
			}
		}
		System.out.println("----------");
		
		wordMaker.reset();
		
		for (char c : new char[] {'a', '\'', '=', '=', '\'', 'b'}) {
			System.out.println("Adding: " + c);
			wordMaker.addChar(c);
			System.out.println("wordReady: " + wordMaker.wordReady());
			System.out.println("getWord: " + wordMaker.getWord());
			System.out.println("-----");
		}
	}	
	
	private static void patternMakerDriver () {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("PatternMaker.makePattern Driver");
		System.out.println("----------");
		while (true) {
			System.out.print("Input word: ");
			String input = sc.nextLine();
			System.out.println(PatternMaker.makePattern(input));
			System.out.println("----------");
		}
	}
}
