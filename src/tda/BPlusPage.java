package tda;

import logic.WordSample;

public class BPlusPage<T> {
	private String[] Keys;
	private BPlusPage<WordSample>[] Branches;
	private Block<WordSample>[] Blocks;
	private int NumKeys;
	private int NumBranches;
	
	public BPlusPage(int pM) {
		Keys = new String[pM];
		Branches = new BPlusPage[pM+1];
		Blocks = new Block[pM];
		NumKeys = 0;
		NumBranches = 0;
	}
	
	public boolean add(WordSample pValue, String pKey) {
		int compare = 0;
		for(int i = 0; i < Keys.length; i++) {
			if(NumKeys == i) {
				Keys[i] = pKey;
				Block<WordSample> block = new Block<WordSample>();
				block.addNode(pValue);
				Blocks[i] = block;
				if(i > 0) {
					Block<WordSample> search = Blocks[i-1];
					String word = search.getNodes()[0].getValue().getWord();
					for(;search != null && search.getNodes()[0].getValue().getWord().compareTo(word) == 0; search = search.getNext()) {
						if(search.getNext() == null || search.getNodes()[0].getValue().getWord().compareTo(word) != 0) {
							Blocks[i].setNext(search.getNext());
							Blocks[i].setPrevious(search);
							if(search.getNext() != null)
								search.getNext().setPrevious(Blocks[i]);
							search.setNext(Blocks[i]);
							break;
						}
					}
				}
				NumKeys++;
				break;
			}
			compare = Keys[i].compareTo(pKey);
			if(compare > 0) {
				for(int v = Keys.length-2; v >= i; v--) {
					Keys[v+1] = Keys[v];
					Blocks[v+1] = Blocks[v];
				}
				Keys[i] = pKey;
				Block<WordSample> block = new Block<WordSample>();
				block.addNode(pValue);
				Blocks[i] = block;
				Blocks[i].setNext(Blocks[i+1]);
				Blocks[i].setPrevious(Blocks[i+1].getPrevious());
				if(Blocks[i+1].getPrevious() != null)
					Blocks[i+1].getPrevious().setNext(Blocks[i]);
				Blocks[i+1].setPrevious(Blocks[i]);
				NumKeys++;
				break;
			}
			else if(compare == 0) {
				Block<WordSample> search = Blocks[i];
				while(!search.addNode(pValue)) {
					if(search.getNext() == null || search.getNext().getNodes()[0].getValue().getWord().compareTo(pValue.getWord()) != 0) {
						Block<WordSample> block = new Block<WordSample>();
						block.addNode(pValue);
						block.setPrevious(search);
						block.setNext(search.getNext());
						if(search.getNext() != null)
							search.getNext().setPrevious(block);
						search.setNext(block);
						break;
					}
					search = search.getNext();
				}
				break;
			}
		}
		if(NumKeys >= Keys.length)
			return true;
		return false;
	}
	
	public boolean addKey(String pKey) {
		int compare = 0;
		for(int i = 0; i < Keys.length; i++) {
			if(NumKeys == i) {
				Keys[i] = pKey;
				NumKeys++;
				break;
			}
			compare = Keys[i].compareTo(pKey);
			if(compare > 0) {
				for(int v = Keys.length-2; v >= i; v--)
					Keys[v+1] = Keys[v];
				Keys[i] = pKey;
				NumKeys++;
				break;
			}
		}
		if(NumKeys >= Keys.length)
			return true;
		return false;
	}

	public BPlusPage<WordSample> search(String pKey) {
		int compare = 0;
		for(int i = 0; i < Keys.length; i++) {
			if(NumKeys == i)
				return Branches[i];
			compare = Keys[i].compareTo(pKey);
			if(compare > 0)
				return Branches[i];
		}
		return null;
	}
	
	public Block<WordSample> getValue(String pKey) {
		int compare = 0;
		for(int i = 0; i < Keys.length; i++) {
			if(NumKeys == i)
				return null;
			compare = Keys[i].compareTo(pKey);
			if(compare == 0)
				return Blocks[i];
		}
		return null;
	}

	public void addBranche(BPlusPage<WordSample> pPage, boolean menos) {
		int compare = 0;
		int i = 0;
		if(pPage != null) {
			if(menos) {
				if(NumBranches < 0)
					NumBranches++;
			}
			for(; i < Branches.length; i++) {
				if(NumBranches == i) {
					Branches[i] = pPage;
					break;
				}
				compare = Keys[i].compareTo(pPage.getNodes()[0]);
				if(compare > 0) {
					for(int v = Branches.length-2; v >= i; v--)
						Branches[v+1] = Branches[v]; 
					Branches[i] = pPage;
					break;
				}
			}
			NumBranches++;
		}
	}
	
	public void addBlock(Block<WordSample> block) {
		Blocks[NumKeys-1] = block;
	}

	public String[] getNodes() {
		return Keys;
	}

	public BPlusPage<WordSample>[] getBranches() {
		return Branches;
	}

	public int getNumKeys() {
		return NumKeys;
	}
	public Block<WordSample>[] getBlocks() {
		return Blocks;
	}

	public void setNumKeys(int numLlaves) {
		NumKeys = numLlaves;
	}

	public int getNumBranches() {
		return NumBranches;
	}

	public void setNumBranches(int numRamas) {
		NumBranches = numRamas;
	}
}