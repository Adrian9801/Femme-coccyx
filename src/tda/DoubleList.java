package tda;

public class DoubleList<T> implements IQueue<T>{
	private ListDoubleNode<T> First;
	private ListDoubleNode<T> Last;
	private int Length;
	private boolean Priority;
	
	public DoubleList(boolean pPriority) {
		First = null;
		Last = null;
		Length = 0;
		Priority = pPriority;
	}
	
	public void addLast(T pValue, int pPrioridad, int pPosition) {
		ListDoubleNode<T> newNode = new ListDoubleNode<T>(pValue);
		newNode.setPrioridad(pPrioridad);
		newNode.setPosition(pPosition);
		ListDoubleNode<T> searchPointer = Last;
		boolean entro = false;
		if(getLength() > 0) {
			if(compare(searchPointer.getPrioridad(),pPrioridad)) {
				ajustarPunteros(searchPointer, newNode);
				Last = newNode;
			}
			else {
				while(searchPointer != First) {
					searchPointer = searchPointer.getPrevious();
					if(compare(searchPointer.getPrioridad(),pPrioridad)) {
						ajustarPunteros(searchPointer, newNode);
						entro = true;
						break;
					}
				}
				if(!entro) {
					newNode.setNext(First);
					newNode.setPrevious(Last);
					First.setPrevious(newNode);
					Last.setNext(newNode);
					First = newNode;
				}
			}
		}
		else {
			newNode.setPrevious(newNode);
			newNode.setNext(newNode);
			First = newNode;
			Last = newNode;
		}
		Length++;
	}
	
	public boolean compare(int pPrioridad, int pNewPrioridad) {
		if(pPrioridad < pNewPrioridad)
			return Priority;
		else if(pPrioridad > pNewPrioridad){
			return !Priority;
		}
		return true;
	}
	
	public void ajustarPunteros(ListDoubleNode<T> searchPointer, ListDoubleNode<T> newNode) {
		newNode.setNext(searchPointer.getNext());
		newNode.setPrevious(searchPointer);
		searchPointer.getNext().setPrevious(newNode);
		searchPointer.setNext(newNode);
	}
	
	public ListDoubleNode<T> removeValue(T pValue){
		ListDoubleNode<T> result = null;
		ListDoubleNode<T> searchPointer = First;
		boolean pointer = false;
		int cont = 0;
		if(getLength() != 0 && searchPointer.getValue() == pValue) {
			if(First.getNext() != First) {
				pointer = true;
				First = First.getNext();
			}
			else {
				First = null;
				Last = null;
			}
			Length--;
		}
		while(cont < getLength() && !pointer) {
			if(searchPointer.getValue() == pValue) {
				result = searchPointer;
				pointer = true;
				Length--;
			}
			searchPointer = searchPointer.getNext();
			cont++;
		}
		if(pointer) {
			searchPointer.getNext().setPrevious(searchPointer.getPrevious());
			searchPointer.getPrevious().setNext(searchPointer.getNext());
			searchPointer.setPrevious(null);
			searchPointer.setNext(null);
			Last = First.getPrevious();
		}
		return result;
	}
	
	public ListDoubleNode<T> removeFirst(){
		ListDoubleNode<T> searchPointer = First;
		ListDoubleNode<T> result = null;
		if(getLength() > 0) {
			result = First;
			if(getLength() == 1) {
				First = null;
				Last = null;
			}
			else {
				First = First.getNext();
				First.setPrevious(Last);
				Last.setNext(First);
			}
			Length--;
			searchPointer.setNext(null);
			searchPointer.setPrevious(null);
		}
		return result;
	}
	
	public int getLength() {
		return Length;
	}

	@Override
	public void enqueue(T pValue, int pPrioridad, int pPosition) {
		addLast(pValue, pPrioridad, pPosition);
	}

	@Override
	public ListDoubleNode<T> dequeue() {
		return removeFirst();
	}
	
	@Override
	public boolean isEmpty() {
		return getLength() == 0;
	}
}