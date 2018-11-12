package tda;

import java.util.Stack;
import logic.WordSample;

public class BAsteriskTree<T> {
	private BAsteriskPage<WordSample> Root;
	private int M;
	private int Height;
	
	public BAsteriskTree(int pM) {
		Root = new BAsteriskPage<WordSample>(pM);
		M = pM;
		Height = 0;
	}
	
	public void add(WordSample pValue, String pKey) {
		BAsteriskPage<WordSample> searchPage = Root;
		if(Height <= 0) {
			if(searchPage.add(pValue, pKey)) {
				BAsteriskPage<WordSample> pageFather = new BAsteriskPage<WordSample>(M);
				pageFather.addBranche(searchPage);
				splitPage(searchPage, pageFather);
				Root = pageFather;
				Height++;
			}
		}
		else {
			int auxHeight = Height; 
			Stack<BAsteriskPage<WordSample>> stack = new Stack<BAsteriskPage<WordSample>>();  
			for(int i = 0; i <= Height; i++) {
				if(i < Height && searchPage.search(pKey) == null) {
					searchPage.add(pValue, pKey);
					break;// Estaba repetida
				}
				if(i >= Height){
					if(searchPage.add(pValue, pKey)) {
						boolean pageFull = true;
						BAsteriskPage<WordSample> pageFather;
						while(pageFull) {
							pageFather = stack.pop();
							if(!share(searchPage, pageFather)) {
								pageFull = splitPage(searchPage, pageFather);
								searchPage = pageFather;
								auxHeight--;
								if(auxHeight <= 0 && pageFull) {
									pageFather = new BAsteriskPage<WordSample>(M);
									pageFather.addBranche(searchPage);
									splitPage(searchPage, pageFather);
									Root = pageFather;
									Height++;
									break;
								}
							}
							else
								break;
						}
						break;
					}
				}
				stack.push(searchPage);
				searchPage = searchPage.search(pKey);
			}
		}
	}

	private boolean splitPage(BAsteriskPage<WordSample> pSearch, BAsteriskPage<WordSample> pFather) {
		int median = M/2;
		boolean full = false;
		BAsteriskPage<WordSample> newPage = new BAsteriskPage<WordSample>(M);
		full = pFather.add(pSearch.getNodes()[median]);
		for(int i = 0; i < median; i++) {
			if(M%2 != 0 || i != median-1) {
				newPage.add(pSearch.getNodes()[median+i+1]);
				if(pSearch.getNumBranches() > 0) {
					newPage.addBranche(pSearch.getBranches()[median+i]);
					pSearch.setNumBranches(median+1);
				}
			}
		}
		if(pSearch.getNumBranches() > 0)
			newPage.addBranche(pSearch.getBranches()[M]);
		pFather.addBranche(newPage);
		pSearch.setNumKeys(median);
		return full;
	}
	
	private boolean share(BAsteriskPage<WordSample> pSearch, BAsteriskPage<WordSample> pFather) {
		int numBranch = pFather.numBranche(pSearch.getNodes()[0].getKey());
		if(numBranch > 0 && pFather.getBranches()[numBranch-1].getNumKeys() < M-1) {
			pFather.add(pSearch.getNodes()[0]);
			pFather.getBranches()[numBranch-1].add(pFather.getNodes()[numBranch-1]);
			pFather.getBranches()[numBranch-1].addBranche(pFather.getBranches()[numBranch].getBranches()[0]);
			pSearch.reorder(0, true);
			pFather.reorder(numBranch-1, false);
			return true;
		}
		else if(numBranch < pFather.getNumBranches()-1 && pFather.getBranches()[numBranch+1].getNumKeys() < M-1) {
			pFather.add(pSearch.getNodes()[M-1]);
			pFather.getBranches()[numBranch+1].add(pFather.getNodes()[numBranch+1]);
			pFather.getBranches()[numBranch+1].addBranche(pFather.getBranches()[numBranch].getBranches()[0]);
			pSearch.reorder(M,false);
			if(pSearch.getNumBranches() > 0)
				pSearch.setNumBranches(pSearch.getNumBranches()-1);
			pFather.reorder(numBranch+1, false);
			return true;
		}
		return false;
	}
}