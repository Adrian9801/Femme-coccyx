package tda;

import logic.WordSample;

public class BAsteriskNode<T> {
	private BPlusTree<WordSample> Tree;
	private String Key;
	
	public BAsteriskNode(WordSample pValue, String pKey) {
		Tree = new BPlusTree<WordSample>(6);
		Tree.add(pValue, pValue.getWord());
		Key = pKey;
	}
	
	public void addWord(WordSample pValue) {
		Tree.add(pValue, pValue.getWord());
	}
	
	public String getKey() {
		return Key;
	}
	
	public void setKey(String key) {
		this.Key = key;
	}

	public BPlusTree<WordSample> getTree() {
		return Tree;
	}

	public void setTree(BPlusTree<WordSample> tree) {
		Tree = tree;
	}
}
