#include "NodeGraph.h"
template <class T>
void NodeGraph<T>::crearArco(NodeGraph<T> *&pDestino, int pDistancia){
	Vertices.push_back(new Arco<T>(pDestino, pDistancia));
}

template <class T>
NodoComparable<T>* NodeGraph<T>::getValue(){
	return Value;
} 

template <class T>
bool NodeGraph<T>::isAvl(){
	return Avl;
}

template <class T>
vector<Arco<T>*> NodeGraph<T>::getVertices(){
	return Vertices;
}

template <class T>
int NodeGraph<T>::getPos(){
	return Pos;
}

template <class T>
void NodeGraph<T>::setPos(int pPos){
	Pos = pPos;
}
