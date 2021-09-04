import java.util.*;
import java.io.*;

public class WordDictionary {
	private Set<String> storage;
	private Iterator<String> iterator;
	
	public WordDictionary (Set<String> storage) {
		storage.clear();
		this.storage = storage;
		resetList();
	}
	
	public void addWord (String word) {
		storage.add(word.toUpperCase());
	}
	
	public boolean isInDictionary (String word) {
		return storage.contains(word.toUpperCase());
	}
	
	public void resetList () {
		iterator = storage.iterator();
	}
	
	public String nextWord () {
		if (iterator.hasNext())
			return iterator.next();
		return null;
	}
	
	public int size () {
		return storage.size();
	}
	
	public void write (String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			Iterator<String> printIterator = storage.iterator();
			while (printIterator.hasNext())
				writer.println(printIterator.next());
			writer.close();
		} catch (Exception e) {}
	}
	
	public void read (String filename) {
		storage.clear();
		BufferedReader file = null;
		try {
			file = new BufferedReader(new FileReader(filename));
			while (true)
				addWord(file.readLine());
		} catch (Exception e) {}
		if (file != null)
			try {
				file.close();
			} catch (Exception e) {}
	}
}
