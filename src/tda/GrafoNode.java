package tda;

import java.util.ArrayList;

public class GrafoNode<T> {
	private T Value;
	private ArrayList<Arco<T>> Arcos;
	private boolean Visitado;
	private int Pos;
	
	public GrafoNode(T pValue, int pPos) {
		Arcos = new ArrayList<Arco<T>>();
		Value = pValue;
		Visitado = false;
		Pos = pPos;
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
	
	public int getPos() {
		return Pos;
	}
}
