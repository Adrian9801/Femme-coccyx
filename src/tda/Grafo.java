package tda;

import java.util.ArrayList;
import logic.Sample;

public class Grafo<T> {
	private ArrayList<GrafoNode<Nodo<Sample>>> Vertices;
	private int PosNode;
	private ArrayList<GrafoNode<Nodo<Sample>>> NodosOrigen;
	private ArrayList<GrafoNode<Nodo<Sample>>> NodosDestino;
	
	public Grafo() {
		Vertices = new ArrayList<GrafoNode<Nodo<Sample>>>();
		PosNode = 0;
		NodosOrigen = new ArrayList<GrafoNode<Nodo<Sample>>>();
		NodosDestino = new ArrayList<GrafoNode<Nodo<Sample>>>();
	}
	
	public void addVertice(Nodo<Sample> pValue) {
		GrafoNode<Nodo<Sample>> newNode = new GrafoNode<Nodo<Sample>>(pValue);
		Vertices.add(newNode);
		if(pValue.getValue().getPixeles().size() > Vertices.get(PosNode).getValue().getValue().getPixeles().size()) {
			PosNode = Vertices.size()-1;
		}
	}
	
	public void getManMinNodes() {
		int cant = Vertices.size()/2;
		GrafoNode<Nodo<Sample>> Node = null;
		cant = cant/10;
		for(int i = 0; i < cant; i++) {
			if(Node != null)
				NodosOrigen.add(Node);
			if(Node != null)
				NodosDestino.add(Node);
		}
	}
	
	public void addArco(int pPosOrigen, int pIDDestino, int pPeso, boolean isAvl) {
		int i = 0;
		for(; i < Vertices.size(); i++) {
			if(Vertices.get(i).getValue().getID() == pIDDestino) {
				if(isAvl)
					break;
				else
					isAvl = true;
			}
		}
		Vertices.get(pPosOrigen).addArco(Vertices.get(i), pPeso);
		if(!isAvl) {
			if(pPeso > 1) {
			}
		}
	}
	
	public ArrayList<GrafoNode<Nodo<Sample>>> getVertices(){
		return Vertices;
	}
}
