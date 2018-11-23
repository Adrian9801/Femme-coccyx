#ifndef NODEGRAPH_H
#define NODEGRAPH_H
#include "Arco.h"
#include "NodoComparable.h"
#include <iostream>
#include <string>
#include <sstream>
#include <vector>
using namespace std;
template <class T> class Arco;
template <class T> class NodeGraph{
	private:
		NodoComparable<T> *Value;
		vector<Arco<T>*> Vertices;
		bool Avl;
		int Pos;
	public:
		NodeGraph (NodoComparable<T> *&pValue, bool pAvl){
			Value = pValue;
			Avl = pAvl;
		}
		void crearArco(NodeGraph<T> *&pDestino, int pDistancia);
		NodoComparable<T>* getValue(); 
		bool isAvl();
		void setPos(int pPos);
		int getPos();
		vector<Arco<T>*> getVertices();
};
#endif
