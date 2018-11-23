package tda;

import java.util.ArrayList;
import logic.Sample;

public class Grafo<T> {
	private ArrayList<GrafoNode<Nodo<Sample>>> Vertices;
	private int PosNode;
	private IQueue<GrafoNode<Nodo<Sample>>> MayorPeso;
	private IQueue<GrafoNode<Nodo<Sample>>> MenorPeso;
	private ArrayList<GrafoNode<Nodo<Sample>>> NodosOrigen;
	private Hash<GrafoNode<Nodo<Sample>>> NodosDestino;
	private GrafoNode<Nodo<Sample>>[] MST;
	private HashList<ArrayList<Nodo<Sample>>> HashCaminos;
	
	public Grafo() {
		Vertices = new ArrayList<GrafoNode<Nodo<Sample>>>();
		PosNode = 0;
		MayorPeso = new DoubleList<GrafoNode<Nodo<Sample>>>(false);
		MenorPeso = new DoubleList<GrafoNode<Nodo<Sample>>>(true);
		NodosOrigen =  new ArrayList<GrafoNode<Nodo<Sample>>>();
		NodosDestino = new Hash<GrafoNode<Nodo<Sample>>>();
		HashCaminos = new HashList<ArrayList<Nodo<Sample>>>();
	}
	
	public void addVertice(Nodo<Sample> pValue) {
		GrafoNode<Nodo<Sample>> newNode = new GrafoNode<Nodo<Sample>>(pValue, Vertices.size());
		if(Vertices.size() == 0 || pValue.getValue().getPixeles().size() > Vertices.get(PosNode).getValue().getValue().getPixeles().size())
			PosNode = Vertices.size();
		Vertices.add(newNode);
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
				NodosDestino.functionHush(Node, Node.getPos());
		}
	}
	
	public void addArco(int pPosOrigen, int pDestino, int pPeso) {
		Vertices.get(pPosOrigen).addArco(Vertices.get(pDestino), pPeso);
		if(pPeso > 1) {
			MayorPeso.enqueue(Vertices.get(pPosOrigen), pPeso, pPosOrigen);
			MenorPeso.enqueue(Vertices.get(pDestino), pPeso, pDestino);
		}
	}
	
	public ArrayList<GrafoNode<Nodo<Sample>>> getVertices(){
		return Vertices;
	}
	
	public GrafoNode<Nodo<Sample>>[] getMST() {
		return MST;
	}
	
	public void crearHashCaminos() {
		for(int i = 0; i < NodosOrigen.size(); i++) {
			dijkstra(NodosOrigen.get(i));
		}
	}
	
	public void dijkstra(GrafoNode<Nodo<Sample>> pNodo) {
		int cantBuscar = Vertices.size()/2;
		cantBuscar = cantBuscar/10;
		ArrayList<Nodo<Sample>>[] caminos= new ArrayList[Vertices.size()];
		caminos[pNodo.getPos()] = new ArrayList<Nodo<Sample>>();
		caminos[pNodo.getPos()].add(pNodo.getValue());
		IQueue<GrafoNode<Nodo<Sample>>> colaPrioridad = new DoubleList<GrafoNode<Nodo<Sample>>>(true);
		int[] distancia = new int[Vertices.size()];
		for(int i = 0; i < Vertices.size(); i++) {
			distancia[i] = -1;
		}
		distancia[pNodo.getPos()] = 0;
		pNodo.setVisitado(true);
		GrafoNode<Nodo<Sample>> initial = pNodo;
		for(int i = 0; i < Vertices.size(); i++) {
			for(int cantArcos = 0; cantArcos < initial.getArcos().size(); cantArcos++) {
				GrafoNode<Nodo<Sample>> nodoDestino = initial.getArcos().get(cantArcos).getDestino();
				if(!nodoDestino.isVisitado()) {
					if(distancia[nodoDestino.getPos()] == -1 || distancia[nodoDestino.getPos()] > initial.getArcos().get(cantArcos).getPeso()+distancia[initial.getPos()]){
						distancia[nodoDestino.getPos()] = initial.getArcos().get(cantArcos).getPeso()+distancia[initial.getPos()];
						colaPrioridad.enqueue(nodoDestino, distancia[nodoDestino.getPos()],initial.getPos());
					}
				}
			}
			if(!colaPrioridad.isEmpty()) {
				ListDoubleNode<GrafoNode<Nodo<Sample>>> node = colaPrioridad.dequeue();
				initial = node.getValue();
				initial.setVisitado(true);
				ArrayList<Nodo<Sample>> camino = (ArrayList<Nodo<Sample>>) caminos[node.getPosition()].clone();
				camino.add(initial.getValue());
				caminos[initial.getPos()] = camino;
				if(NodosDestino.getValue(initial.getPos()) != null) {
					HashCaminos.functionHush(camino, pNodo.getPos());
					cantBuscar--;
					if(cantBuscar <= 0)
						break;
				}
			}
		}
		for(int i = 0; i < Vertices.size(); i++)
			Vertices.get(i).setVisitado(false);
	}
	
	public void prim() {
		GrafoNode<Nodo<Sample>> pNodo = Vertices.get(PosNode);
		MST = new GrafoNode[Vertices.size()];
		GrafoNode<Nodo<Sample>> newNode = new GrafoNode<Nodo<Sample>>(pNodo.getValue(),0);
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
			newNode = new GrafoNode<Nodo<Sample>>(nodo.getValue().getValue(),0);
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
