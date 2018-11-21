package tda;

import java.util.ArrayList;

public class GrafoNode<T> {
	private T Value;
	private ArrayList<Arco<T>> Arcos;
	private boolean Visitado;
	
	public GrafoNode(T pValue) {
		Arcos = new ArrayList<Arco<T>>();
		Value = pValue;
		Visitado = false;
	}
	
	public void addArco(GrafoNode<T> pDestino, int pPeso) {
		Arcos.add(new Arco(pDestino, pPeso));
	}

	public T getValue() {
		return Value;
	}

	public ArrayList<Arco<T>> getArcos() {
		return Arcos;
	}

	public boolean isVisitado() {
		return Visitado;
	}

	public void setVisitado(boolean visitado) {
		Visitado = visitado;
	}
}
