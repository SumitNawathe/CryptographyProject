import java.util.*;

public class PatternTable {
	private TreeNode root;
	
	public void addWord (String word) {
		root = addWordToTree(word, PatternMaker.makePattern(word), root);
	}
	
	public ArrayList<String> wordsWithSamePatternAs (String word) {
		return findPatternInTree(PatternMaker.makePattern(word), root);
	}
	
	private ArrayList<String> findPatternInTree (String pat, TreeNode tree) {
		if (tree == null)
			return new ArrayList<String>();
		else if (pat.equals(tree.getPattern()))
			return tree.getList();
		else if (pat.compareTo(tree.getPattern()) > 0)
			return findPatternInTree(pat, tree.getRight());
		return findPatternInTree(pat, tree.getLeft());
	}
	
	private TreeNode addWordToTree (String word, String pat, TreeNode tree) {
		if (tree == null)
			return new TreeNode(word, pat);
		else if (pat.equals(tree.getPattern()))
			tree.getList().add(word);
		else if (pat.compareTo(tree.getPattern()) > 0)
			tree.setRight(addWordToTree(word, pat, tree.getRight()));
		else
			tree.setLeft(addWordToTree(word, pat, tree.getLeft()));
		return tree;
	}
}
