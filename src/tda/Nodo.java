package tda;

public class Nodo<T> {
	private T Value;
	private Nodo<T> Next;
	private int ID;
	
	public Nodo(T pValue, int pID) {
		Value = pValue;
		Next = null;
		ID = pID;
	}

	public T getValue() {
		return Value;
	}

	public int getID() {
		return ID;
	}

	public Nodo<T> getNext() {
		return Next;
	}

	public void setNext(Nodo<T> next) {
		Next = next;
	}
}