import java.util.*;

public class Decoder {
	private WordDictionary dictionary;
	private PatternTable patternTable;
	private static int debugLevel;
	public static final String WORD_LIST_FILE_NAME = "engmixnoap.txt";
	public static boolean DEBUGGING = false;
	private static String puzzleString;
	
	public Decoder () {
		dictionary = new WordDictionary(new HashSet<String>());
		(new WordGetter()).getWords(dictionary, WORD_LIST_FILE_NAME);
		System.out.println("WordDictionary read.");
		System.out.println("WordDictionary size: " + dictionary.size());
		dictionary.resetList();
		patternTable = new PatternTable();
		String word;
		while ((word = dictionary.nextWord()) != null)
			patternTable.addWord(word);
//		System.out.println("PatternTable created.");
	}
	
	public LinkedList<Puzzle> solveMonoalphabeticCipher (Puzzle puzzle, int cut) {
		if (DEBUGGING) {
			System.out.println(puzzle.getTranslation());
			System.out.println(debugLevel);
			debugLevel++;
			if (debugLevel > 5) {
				System.out.print("hi");
			}
		}
		if (puzzle.numberOfWordsWithStatus(Puzzle.UNTRANSLATED) == 0) {
			LinkedList<Puzzle> sol = new LinkedList<Puzzle>();
			sol.add(puzzle);
			if (DEBUGGING)
				debugLevel--;
			return sol;
		} else {
			HashSet<Puzzle> solutions = new HashSet<Puzzle>();
			int wordNum = puzzle.easiestToTranslate();
			ArrayList<String> wordsToCheck;
			if (puzzle.getMessage()[wordNum].length() == 1) {
				wordsToCheck = new ArrayList<String>();
				wordsToCheck.add("A");
				wordsToCheck.add("I");
			} else if (puzzle.getMessage()[wordNum].length() == 2) {
				wordsToCheck = new ArrayList<String>();
				for (String str : new String[] {"OF", "TO", "IN", "IT", 
						"IS", "BE", "AS", "AT", "SO", "WE", "HE", "BY", 
						"OR", "ON", "DO", "IF", "ME", "MY", "UP", "AN", 
						"GO", "NO", "US", "AM"}) {
					wordsToCheck.add(str);
				}
			} else if (puzzle.getMessage()[wordNum].length() == 3) {
				wordsToCheck = new ArrayList<String>();
				ArrayList<String> commonWords = new ArrayList<String>();
				for (String str : new String[] {"THE", "AND", "FOR", "ARE", 
						"BUT", "NOT", "YOU", "ALL", "ANY", "CAN", "HAD", 
						"HER", "WAS", "ONE", "OUR", "OUT", "DAY", "GET", 
						"HAS", "HIM", "HIS", "HOW", "MAN", "NEW", "OLD", 
						"SEE", "TWO", "WAY", "WHO", "BOY", "DID", "ITS", 
						"LET", "PUT", "SAY", "SHE", "TOO", "USE", "WHY",
						"KID", "RED", "SON", "SIX", "KEY", "CAT", "ANT"}) {
					commonWords.add(str);
					wordsToCheck.add(str);
				}
				for (String s : patternTable.wordsWithSamePatternAs(
						puzzle.nthWord(wordNum)))
					if (!commonWords.contains(s))
						wordsToCheck.add(s);
//				System.out.println("hi");
			} else {
				wordsToCheck = patternTable.wordsWithSamePatternAs(
						puzzle.nthWord(wordNum));
			}
			
			for (String patternMatch : wordsToCheck) {
				if (puzzle.getTranslation().couldTranslateTo(
						puzzle.nthWord(wordNum), patternMatch)) {
					Puzzle newPuzzle = new Puzzle(puzzle);
					newPuzzle.setStatus(wordNum, Puzzle.TRANSLATED);
					newPuzzle.translateNthWordTo(wordNum, patternMatch);
					for (Puzzle possibleSolution : 
							solveMonoalphabeticCipher(newPuzzle, cut)) {
						if (possibleSolution.numberOfWordsWithStatus(
								Puzzle.UNTRANSLATABLE) <= cut) {
							System.out.println(possibleSolution.getTranslation().translation(puzzleString));
							solutions.add(possibleSolution);
							cut = possibleSolution.
									numberOfWordsWithStatus(
									Puzzle.UNTRANSLATABLE);
						}
					}
				}
			}
			if (puzzle.numberOfWordsWithStatus(
					Puzzle.UNTRANSLATABLE) < cut) {
				Puzzle newPuzzle = new Puzzle(puzzle);
				newPuzzle.setStatus(wordNum, Puzzle.UNTRANSLATABLE);
				for (Puzzle possibleSolution : 
						solveMonoalphabeticCipher(newPuzzle, cut)) {
					if (possibleSolution.numberOfWordsWithStatus(
							Puzzle.UNTRANSLATABLE) <= cut)
						solutions.add(possibleSolution);
				}
			}			
			int minUntranslatable = Integer.MAX_VALUE;
			for (Puzzle solution : solutions)
				if (solution.numberOfWordsWithStatus(
						Puzzle.UNTRANSLATABLE) < minUntranslatable)
					minUntranslatable = solution.
						numberOfWordsWithStatus(Puzzle.UNTRANSLATABLE);
			LinkedList<Puzzle> trimmedSolutions = new LinkedList<Puzzle>();
			for (Puzzle sol : solutions)
				if (sol.numberOfWordsWithStatus(Puzzle.UNTRANSLATABLE) 
						== minUntranslatable)
					trimmedSolutions.add(sol);
			if (DEBUGGING)
				debugLevel--;
			return trimmedSolutions;
		}
	}
	
	public static void main(String[] args) {
		WordDictionary dictionary = new WordDictionary(new HashSet<String>());
		(new WordGetter()).getWords(dictionary, WORD_LIST_FILE_NAME);
		dictionary.resetList();
		PatternTable patternTable = new PatternTable();
		String word;
		while ((word = dictionary.nextWord()) != null)
			patternTable.addWord(word);
		
		debugLevel = 0;
		WordMaker wordMaker = new WordMaker();
		ArrayList<String> puzzleWords = new ArrayList<String>();
//		puzzleString = "J XPVMEO'U BOTXFS UIF NBSJKVBOB RVFTUJPO. "
//				+ "ZPV LOPX XIZ? CFDBVTF J EPO'U XBOU TPNF MJUUMF LJE "
//				+ "EPJOH XIBU J USJFE";
//		String puzzleString = "If you look at what you have in life, you'll always have more. If you look at what you don't have in life, you'll never have enough.";
//		I WOULDN'T ANSWER THE MARIJUANA QUESTION. 
//		YOU KNOW WHY? BECAUSE I DON'T WANT SOME LITTLE 
//		KID DOING WHAT I TRIED
		
//		puzzleString = "A LMHIB ZFJRB AR FWK PABBIK "
//				+ "ME EAEFW JUKRHK JRB ZWMMF ZMPKGMBV, JRB"
//				+ " A XMHIBR'F IMZK JRV UMFKZ";
		
		puzzleString = "VX'U GSS SPTX NI OINX EIA NWX DXHN ZGJ DYN WX'H JXOXA G QGJUPUGNX.";
		
		for (int i = 0; i < puzzleString.length(); i++) {
			wordMaker.addChar(puzzleString.substring(i, i+1).charAt(0));
			if (wordMaker.wordReady() && wordMaker.getWord().length() > 0) {
				puzzleWords.add(wordMaker.getWord());
				wordMaker.reset();
			}
		}
		if (wordMaker.getWord().length() > 0) {
			puzzleWords.add(wordMaker.getWord());
		}
		String[] puzzleWordsArray = new String[puzzleWords.size()];
		for (int i = 0; i < puzzleWords.size(); i++)
			puzzleWordsArray[i] = puzzleWords.get(i);
		
		Puzzle puzzle = new Puzzle(puzzleWordsArray, patternTable);
		Decoder decoder = new Decoder();
		
//		System.out.println("puzzle input");
//		for (String s : puzzle.getMessage())
//			System.out.println(s);
		
		LinkedList<Puzzle> solutions = decoder.solveMonoalphabeticCipher(puzzle, 0);
		System.out.println("----------");
		for (Puzzle p : solutions) {
			System.out.println(p.getTranslation().translation(puzzleString));
		}
	}
}
