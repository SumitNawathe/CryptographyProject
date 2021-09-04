import java.util.ArrayList;

public class TreeNode {
	private String pattern;
	private ArrayList<String> list;
	private TreeNode left;
	private TreeNode right;
	
	public String getPattern() { return pattern; }
	public ArrayList<String> getList () { return list; }
	public TreeNode getLeft () { return left; }
	public TreeNode getRight () { return right; }
	
	public void setPattern (String str) { pattern = str; }
	public void setList (ArrayList<String> wl) { list = wl; }
	public void setLeft (TreeNode node) { left = node; }
	public void setRight (TreeNode node) { right = node; }
	
	TreeNode (String word, String pat) {
		pattern = pat;
		list = new ArrayList<String>();
		list.add(word);
		left = null;
		right = null;
	}
}