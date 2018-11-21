#ifndef GRAPH_H
#define GRAPH_H
#include <vector>
#include "ArbolAvl.h"
#include "Monticulo.h"
#include "NodeGraph.cpp"
#include "NodoComparable.h"
#include <jni.h>
#include <iostream>
#include <algorithm>
#include <stdio.h>
using namespace std;


template <class T> class Graph{
	public:
		Graph(){
		listaGraphAvl = vector< NodeGraph<T>* >(500000);
		listaGraphMon = vector< NodeGraph<T>* >(500000);
		}
		void comparar(ArbolAvl<T> pTree, Monticulo<T> pMon);
		void correrArbol(ArbolAvl<T> pTree);
		void correrMonticulo(Monticulo<T> pMon);
		void agregarArcos();
		void agregarArcosAVLMON();
		void agregarArcosAVLMON(int pIndiceMon, int pPosRelativaMon);
		vector<NodeGraph<T>*> getVertices();
		
	private:
		vector<NodeGraph<T>*> Vertices;
		vector< NodeGraph<T>* > listaGraphAvl;
		vector< NodeGraph<T>* > listaGraphMon;
};
#endif
