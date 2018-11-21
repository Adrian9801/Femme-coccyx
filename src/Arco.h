#ifndef ARCO_H
#define ARCO_H
#include <iostream>
#include <string>
#include <sstream>
#include "NodeGraph.h"
template <class T> class NodeGraph;
template <class T> class Arco
{
	private:
		int Distancia;
		NodeGraph<T> *Destino;
	public:
		Arco(NodeGraph<T> *&pDestino,int pDistancia){
			Destino = pDestino;
			Distancia = pDistancia;
		}
		NodeGraph<T>* getDestino();
		int getDistancia();
};
#endif
