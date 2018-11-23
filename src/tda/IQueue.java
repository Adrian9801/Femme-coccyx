package tda;

public interface IQueue<T> {
	public void enqueue(T pValue, int pPrioridad, int pPosition);
	public ListDoubleNode<T> dequeue();
	public boolean isEmpty();
}