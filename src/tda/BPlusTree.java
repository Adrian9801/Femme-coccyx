package tda;

import java.util.Stack;
import logic.WordSample;

public class BPlusTree<T> {
	private BPlusPage<WordSample> Root;
	private Block<WordSample> CS;
	private int M;
	private int Height;
	
	public BPlusTree(int pM) {
		Root = new BPlusPage<WordSample>(pM);
		M = pM;
		Height = 0;
		CS = new Block<WordSample>();
	}
	
	public void add(WordSample pValue, String pKey) {
		BPlusPage<WordSample> search = Root;
		if(Height <= 0) {
			if(search.add(pValue, pKey)) {
				BPlusPage<WordSample> pageFather = new BPlusPage<WordSample>(M);
				pageFather.addBranche(search, false);
				splitPage(search, pageFather);
				Root = pageFather;
				Height++;
			}
		}
		else {
			int auxHeight = Height; 
			Stack<BPlusPage<WordSample>> stack = new Stack<BPlusPage<WordSample>>();  
			for(int i = 0; i <= Height; i++) {
				if(i >= Height){
					if(search.add(pValue, pKey)) {
						boolean pageFull = true;
						BPlusPage<WordSample> pageFather;
						while(pageFull) {
							pageFather = stack.pop();
							pageFull = splitPage(search, pageFather);
							search = pageFather;
							auxHeight--;
							if(auxHeight <= 0 && pageFull) {
								pageFather = new BPlusPage<WordSample>(M);
								pageFather.addBranche(search, false);
								splitPage(search, pageFather);
								Root = pageFather;
								Height++;
								break;
							}
						}
						break;
					}
				}
				stack.push(search);
				search = search.search(pKey);
			}
		}
		refreshRootCS();
	}
	
	public void refreshRootCS() {
		BPlusPage<WordSample> search = Root;
		for(int i = 0; i <= Height; i++) {
			if(i == Height) {
				CS = search.getBlocks()[0];
				break;
			}
			search = search.getBranches()[0];
		}
	}

	private boolean splitPage(BPlusPage<WordSample> pSearch, BPlusPage<WordSample> pFather) {
		int median = M/2;
		boolean full = false;
		BPlusPage<WordSample> newPage = new BPlusPage<WordSample>(M);
		full = pFather.addKey(pSearch.getNodes()[median]);// No quiero que le pase hasta la referencia al bloque
		for(int i = 0; i <= median; i++) {
			if(M%2 != 0 || i != median) {
				newPage.addKey(pSearch.getNodes()[median+i]);
				if(pSearch.getNumBranches() > 0) {
					newPage.addBranche(pSearch.getBranches()[median+i+1], true);
					pSearch.setNumBranches(median+1);
				}
				else {
					newPage.addBlock(pSearch.getBlocks()[median+i]);
				}
			}
		}
		pSearch.setNumKeys(median);
		pFather.addBranche(newPage, false);
		return full;
	}
}