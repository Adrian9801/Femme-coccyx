package tda;

import library.IConstants;

public class Hash<T> implements IConstants{
	private int Size;
	private Object[] Table;
	
	public Hash() {
		Size = HASH_SIZE;
		Table = new Object[Size];
	}
	
	public Object functionHush(T pValue, int pKey) {
		int key = pKey%Size;
		if(Table[key] == null) {
			Table[key] = pValue;
			return null;
		}
		return Table[key];
	}
	
	public Object getValue(int pKey) {
		int key = pKey%Size;
		return Table[key];
	}
}