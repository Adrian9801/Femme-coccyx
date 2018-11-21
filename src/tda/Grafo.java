package tda;

import java.util.ArrayList;
import logic.Sample;

public class Grafo<T> {
	private ArrayList<GrafoNode<Nodo<Sample>>> Vertices;
	private int PosNode;
	private IQueue<GrafoNode<Nodo<Sample>>> MayorPeso;
	private IQueue<GrafoNode<Nodo<Sample>>> MenorPeso;
	private ArrayList<GrafoNode<Nodo<Sample>>> NodosOrigen;
	private ArrayList<GrafoNode<Nodo<Sample>>> NodosDestino;
	
	public Grafo() {
		Vertices = new ArrayList<GrafoNode<Nodo<Sample>>>();
		PosNode = 0;
		MayorPeso = new DoubleList<GrafoNode<Nodo<Sample>>>(false);
		MenorPeso = new DoubleList<GrafoNode<Nodo<Sample>>>(true);
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
			Node = MayorPeso.dequeue().getValue();
			if(Node != null)
				NodosOrigen.add(Node);
			Node = MenorPeso.dequeue().getValue();
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
				MayorPeso.enqueue(Vertices.get(pPosOrigen), pPeso, pPosOrigen);
				MenorPeso.enqueue(Vertices.get(pPosOrigen), pPeso, pPosOrigen);
			}
		}
	}
	
	public ArrayList<GrafoNode<Nodo<Sample>>> getVertices(){
		return Vertices;
	}
	
	public void prim() {
		GrafoNode<Nodo<Sample>> pNodo = Vertices.get(PosNode);
		GrafoNode<Nodo<Sample>>[] MST = new GrafoNode[Vertices.size()];
		GrafoNode<Nodo<Sample>> newNode = new GrafoNode<Nodo<Sample>>(pNodo.getValue());
		IQueue<GrafoNode<Nodo<Sample>>> colaPrioridad = new DoubleList<GrafoNode<Nodo<Sample>>>(true);
		GrafoNode<Nodo<Sample>>[] visitados = new GrafoNode[Vertices.size()];
		visitados[0] = pNodo;
		MST[0] = newNode;
		pNodo.setVisitado(true);
		for(int i = 0; i < pNodo.getArcos().size(); i++)
			colaPrioridad.enqueue(pNodo.getArcos().get(i).getDestino(), pNodo.getArcos().get(i).getPeso(), 0);
		for(int i = 1; i < Vertices.size() && !colaPrioridad.isEmpty(); i++) {
			ListDoubleNode<GrafoNode<Nodo<Sample>>> nodo = colaPrioridad.dequeue();
			while(nodo != null && nodo.getValue().isVisitado())
				nodo = colaPrioridad.dequeue();
			if(nodo == null)
				break;
			newNode = new GrafoNode<Nodo<Sample>>(nodo.getValue().getValue());
			visitados[i] = nodo.getValue();
			nodo.getValue().setVisitado(true);
			MST[nodo.getPosition()].addArco(newNode, nodo.getPrioridad());
			MST[i] = newNode;
			for(int cantArcos = 0; cantArcos < nodo.getValue().getArcos().size(); cantArcos++) {
				if(!nodo.getValue().getArcos().get(cantArcos).getDestino().isVisitado())
					colaPrioridad.enqueue(nodo.getValue().getArcos().get(cantArcos).getDestino(), nodo.getValue().getArcos().get(cantArcos).getPeso(), i);
			}
		}
		for(int i = 0; i < Vertices.size(); i++)
			Vertices.get(i).setVisitado(false);
	}
}
