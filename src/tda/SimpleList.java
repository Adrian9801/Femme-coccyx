package tda;

public class SimpleList<T> {
	private Nodo<T> First;
	private Nodo<T> Last;
	private int Length;
	
	public SimpleList() {
		First = null;
		Last = null;
		Length = 0;
	}
	
	public boolean isEmpty() {
		return Length == 0;
	}
	
	public void add(T pValue) {
		Nodo<T> newNodo = new Nodo<T>(pValue, Length);
		if(!isEmpty()) {
			newNodo.setNext(First);
			Last.setNext(newNodo);
			Last = newNodo;
		}
		else {
			First = newNodo;
			Last = newNodo;
			Last.setNext(First);
		}
		Length++;
	}
	
	public T getFirst() {
		Nodo<T> Nodo = First;
		First = First.getNext();
		return Nodo.getValue();
	}
	
	public Nodo<T> getFirstNode() {
		return First;
	}
	
	public int getLength() {
		return Length;
	}
}