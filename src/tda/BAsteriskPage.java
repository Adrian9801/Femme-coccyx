package tda;

import logic.WordSample;

public class BAsteriskPage<T> {
	private BAsteriskNode<WordSample>[] Nodes;
	private BAsteriskPage<WordSample>[] Branches;
	private int NumKeys;
	private int NumBranches;
	
	public BAsteriskPage(int pM) {
		Nodes = new BAsteriskNode[pM];
		Branches = new BAsteriskPage[pM+1];
		NumKeys = 0;
		NumBranches = 0;
	}
	
	public boolean add(WordSample pValue, String pKey) {
		int compare = 0;
		for(int i = 0; i < Nodes.length; i++) {
			if(NumKeys == i) {
				Nodes[i] = new BAsteriskNode<WordSample>(pValue, pKey);
				NumKeys++;
				break;
			}
			compare = Nodes[i].getKey().compareTo(pKey);
			if(compare > 0) {
				for(int v = Nodes.length-2; v >= i; v--)
					Nodes[v+1] = Nodes[v]; 
				Nodes[i] = new BAsteriskNode<WordSample>(pValue, pKey);
				NumKeys++;
				break;
			}
			else if(compare == 0) {
				Nodes[i].addWord(pValue);
				break;
			}
		}
		if(NumKeys >= Nodes.length)
			return true;
		return false;
	}
	
	public boolean add(BAsteriskNode<WordSample> pNode) {
		int compare = 0;
		for(int i = 0; i < Nodes.length; i++) {
			if(NumKeys == i) {
				Nodes[i] = pNode;
				NumKeys++;
				break;
			}
			compare = Nodes[i].getKey().compareTo(pNode.getKey());
			if(compare > 0) {
				for(int v = Nodes.length-2; v >= i; v--)
					Nodes[v+1] = Nodes[v]; 
				Nodes[i] = pNode;
				NumKeys++;
				break;
			}
		}
		if(NumKeys >= Nodes.length)
			return true;
		return false;
	}

	public BAsteriskPage<WordSample> search(String pKey) {
		int compare = 0;
		for(int i = 0; i < Nodes.length; i++) {
			if(NumKeys == i) {
				return Branches[i];
			}
			compare = Nodes[i].getKey().compareTo(pKey);
			if(compare > 0)
				return Branches[i];
			else if(compare == 0)
				return null;
		}
		return null;
	}
	
	public void reorder(int pPosition, boolean pInitation) {
		for(int i = pPosition; NumKeys-1 > i; i++)
			Nodes[i] = Nodes[i+1];
		for(int i = 0; pInitation && i < NumKeys; i++)
			Branches[i] = Branches[i+1];
		if(pInitation && NumBranches >0)
			NumBranches--;
		NumKeys--;
	}
	
	public void addBranche(BAsteriskPage<WordSample> pPage) {
		int compare = 0;
		if(pPage != null) {
			for(int i = 0; i < Branches.length; i++) {
				if(NumBranches == i) {
					Branches[i] = pPage;
					break;
				}
				compare = Nodes[i].getKey().compareTo(pPage.getNodes()[0].getKey());
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
	
	public int numBranche(String pKey) {
		for(int i = 0; i < Branches.length; i++) {
			if(Branches[i].getNodes()[0].getKey().compareTo(pKey) == 0)
				return i;
		}
		return 0;
	}

	public void pageDivided(int pM) {
		NumKeys = 1;
		Nodes[0] = Nodes[pM];
	}
	
	public BAsteriskNode<WordSample>[] getNodes() {
		return Nodes;
	}

	public BAsteriskPage<WordSample>[] getBranches() {
		return Branches;
	}

	public int getNumKeys() {
		return NumKeys;
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