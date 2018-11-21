package tda;

public class ListDoubleNode<T> {
	private ListDoubleNode<T> Next;
	private ListDoubleNode<T> Previous;
	private T Value;
	private int Prioridad;
	private int Position;
	
	public ListDoubleNode() {
		Next = null;
		Previous = null;
	}
	
	public ListDoubleNode(T pValue) {
		this();
		Value = pValue;
		Prioridad = 0;
		Position = 0;
	}
	
	public ListDoubleNode<T> getNext() {
		return Next;
	}
	
	public void setNext(ListDoubleNode<T> next) {
		Next = next;
	}
	
	public ListDoubleNode<T> getPrevious() {
		return Previous;
	}
	
	public void setPrevious(ListDoubleNode<T> previous) {
		Previous = previous;
	}
	
	public T getValue() {
		return Value;
	}
	
	public void setValue(T value) {
		Value = value;
	}

	public int getPrioridad() {
		return Prioridad;
	}

	public void setPrioridad(int prioridad) {
		Prioridad = prioridad;
	}

	public int getPosition() {
		return Position;
	}

	public void setPosition(int position) {
		Position = position;
	}
}
