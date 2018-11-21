#ifndef MONTICULO_H
#define MONTICULO_H
#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <sstream>
#include <fstream>
#include <cmath>
#include "NodoComparable.h"
using namespace std;

template <class T> class Node : public NodoComparable<T>{
	private:
	public:
		int getID();
		T getValue();
		Node (T pValue, int pID) : NodoComparable<T>(pValue,pID) {}
		~Node() {}
};

template <class T> class Monticulo {
	private:
		vector< Node<T>* > elements;
	public:
		Monticulo () {elements = vector< Node<T>* >(50000);} 
		~Monticulo() {}
		void setElements(vector< Node<T>* > pElements);
		vector< Node<T>* > getElements();
		void addElement(T pElement, int pID);
		void printElements();
		int searchElement(int pID);
};

template <class T> class Heapsort{
	private:
		void heap(vector<Node<T>* >& pVector, int pLength, int pIndex, bool pFlag);
		
	public:
		vector<Node<T>* > heapSort(Monticulo<T> pMonticulo, bool pFlag);
};
inline int parent(int i) {
    return i >> 1;
}

inline int left(int i) {
    return i << 1;
}

inline int right(int i) {
    return (i << 1) + 1;
}
#endif
