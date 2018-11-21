#include "Graph.h"
template <class T>
void Graph<T>::comparar(ArbolAvl<T> pTree, Monticulo<T> pMon)
{
	correrArbol(pTree);
	correrMonticulo(pMon);
	agregarArcos();
	agregarArcosAVLMON();
}

template <class T>
void Graph<T>::correrArbol(ArbolAvl<T> pTree){
	vector< NodoAvl<T>* > listaAvl;
	NodoAvl<T>* padre = pTree.getRaiz();
	NodoAvl<T>* izq;
	NodoAvl<T>* der;
	if(padre != NULL){
		der = padre->getHijoDer();
		izq = padre->getHijoIzq();
		listaAvl.push_back(padre);
		listaAvl.push_back(izq);
		listaAvl.push_back(der);
	}
	NodoComparable<T>* padreComparable = padre;
	listaGraphAvl[0] = new NodeGraph<T>(padreComparable, true);
	int count = 2;
	for(int i = 1; count > 0; i++){
		padre = listaAvl[i];
		if(padre != NULL){
			izq = padre->getHijoIzq();
			der = padre->getHijoDer();
			listaAvl.push_back(izq);
			listaAvl.push_back(der);
			count+=2;
			padreComparable = padre;
			listaGraphAvl[i] = new NodeGraph<T>(padreComparable, true);
		}
		count--;
	}
}

template <class T>
void Graph<T>::correrMonticulo(Monticulo<T> pMon){
	vector< Node<T>* > listaMon = pMon.getElements();
	NodoComparable<T>* comparable;
	for(int i = 0; i < 50000; i++){
		if(listaMon[i] != NULL){
			comparable = listaMon[i];
			listaGraphMon[i] = new NodeGraph<T>(comparable, false);
		} 
	}
}

template <class T>
void Graph<T>::agregarArcos(){
	vector< int > lista;
	lista.push_back(0);
	for(int i = 0; i < lista.size(); i++){
		if(lista[i] != 0 || i == 0){
			if(listaGraphAvl[lista[i]*2+1] != NULL){
				listaGraphAvl[lista[i]]->crearArco(listaGraphAvl[lista[i]*2+1], 1);
				lista.push_back(lista[i]*2+1);
			}
			if(listaGraphAvl[lista[i]*2+2] != NULL){
				listaGraphAvl[lista[i]]->crearArco(listaGraphAvl[lista[i]*2+2], 1);
				lista.push_back(lista[i]*2+2);
			}
		}
	}
	vector< int > lista2;
	lista2.push_back(0);
	for(int i = 0; i < lista2.size(); i++){
		if(lista2[i] != 0 || i == 0){
			if(listaGraphMon[lista2[i]*2+1] != NULL){
				listaGraphMon[i]->crearArco(listaGraphMon[lista2[i]*2+1], 1);
				lista2.push_back(lista2[i]*2+1);
			}
			if(listaGraphMon[lista2[i]*2+2] != NULL){
				listaGraphMon[i]->crearArco(listaGraphMon[lista2[i]*2+2], 1);
				lista2.push_back(lista2[i]*2+2);
			}
		}
	}
}

template <class T>
void Graph<T>::agregarArcosAVLMON(){
	int altura = 0;
	int contador = 1;
	int posRelativaMon;
	int indice = 0;
	for(int i = 0; listaGraphMon[i] != NULL; i++){
		posRelativaMon = 2^(altura)-1 + listaGraphMon[i]->getValue()->getID() - indice;
		agregarArcosAVLMON(i, posRelativaMon);
		if(2^(altura) > contador){
			contador++;
		}
		else{
			indice+=2^(altura);
			altura++;
			contador = 1;
		}
	}
	vector< int > lista;
	lista.push_back(0);
	for(int i = 0; i < lista.size(); i++){
		if(lista[i] != 0 || i == 0){
			if(listaGraphAvl[lista[i]*2+1] != NULL)
				lista.push_back(lista[i]*2+1);
			if(listaGraphAvl[lista[i]*2+2] != NULL)
				lista.push_back(lista[i]*2+2);
			Vertices.push_back(listaGraphAvl[lista[i]]);
		}
	}
	
	vector< int > lista2;
	lista2.push_back(0);
	for(int i = 0; i < lista2.size(); i++){
		if(lista2[i] != 0 || i == 0){
			if(listaGraphMon[lista2[i]*2+1] != NULL)
				lista2.push_back(lista2[i]*2+1);
			if(listaGraphMon[lista2[i]*2+2] != NULL)
				lista2.push_back(lista2[i]*2+2);
			Vertices.push_back(listaGraphMon[lista2[i]]);
		}
	}
	
}

template <class T>
void Graph<T>::agregarArcosAVLMON(int pIndiceMon, int pPosRelativaMon){
	int i = 0;
	int altura = 0;
	int posRelativaAvl;
	int indice = 0;
	for(;listaGraphAvl[i]->getValue()->getID() != listaGraphMon[pIndiceMon]->getValue()->getID();){
		if(listaGraphAvl[i]->getValue()->getID() < listaGraphMon[pIndiceMon]->getValue()->getID())
			i = 2*i+2;
		else
			i = 2*i+1;
		indice+=2^(altura);
		altura++;
	}
	posRelativaAvl = 2^(altura) - 1 +3;
	int distancia = posRelativaAvl-pPosRelativaMon;
	if(distancia<0){
		distancia = distancia*-1;
	}
	listaGraphAvl[i]->crearArco(listaGraphMon[pIndiceMon], distancia);
}

template <class T>
vector<NodeGraph<T>*> Graph<T>::getVertices(){
	return Vertices;
}
