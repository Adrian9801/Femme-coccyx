package tda;

import logic.WordSample;

public class Block<T> {
	private BPlusNode<WordSample>[] Nodes;
	private Block<WordSample> Next;
	private Block<WordSample> Previous;
	private int Length;
	
	public Block() {
		Nodes = new BPlusNode[3];
		Next = null;
		Previous = null;
		Length = 0;
	}
	
	public boolean addNode(WordSample pValue) {
		if(Length <= Nodes.length) {
			for(int i = 0; i < Nodes.length; i++) {
				if(Nodes[i] == null) {
					Nodes[i] = new BPlusNode<WordSample>(pValue);
					Length++;
					return true;
				}
				else if(Nodes[i].getValue().getRegion() == pValue.getRegion()) {
					Nodes[i].addValue();
					return true;
				}
			}
		}
		return false;
	}

	public BPlusNode<WordSample>[] getNodes() {
		return Nodes;
	}

	public Block<WordSample> getNext() {
		return Next;
	}

	public void setNext(Block<WordSample> next) {
		Next = next;
	}

	public Block<WordSample> getPrevious() {
		return Previous;
	}

	public void setPrevious(Block<WordSample> previous) {
		Previous = previous;
	}
}