package tda;

public class BPlusNode<T> {
	private T Value;
	private int Accountant;
	
	public BPlusNode(T pValue){
		Value = pValue;
		Accountant = 1;
	}
	
	public T getValue() {
		return Value;
	}
	
	public int getAccountant() {
		return Accountant;
	}
	
	public void addValue() {
		Accountant++;
	}
}