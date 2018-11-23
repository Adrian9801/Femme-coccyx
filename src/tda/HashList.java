package tda;

import library.IConstants;

public class HashList<T> implements IConstants{
	private int Size;
	private SimpleList<T>[] Table;
	
	public HashList() {
		Size = HASH_SIZE;
		Table = new SimpleList[Size];
	}
	
	public void functionHush(T pValue, int pKey) {
		int key = pKey%Size;
		if(Table[key] == null) {
			Table[key] = new SimpleList();
		}
		Table[key].add(pValue);
	}
	
	public SimpleList<T> getValue(int pKey) {
		int key = pKey%Size;
		return Table[key];
	}
}
