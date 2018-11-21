#include "Monticulo.h"
//Heapsort
template <class T> void Heapsort<T>::heap(vector<Node<T>* >& pVector, int pLength, int pIndex, bool pFlag){
	if (pLength + pIndex > pVector.size()){
		return;
	}
		
	heap(pVector, left(pLength), pIndex, pFlag);
	heap(pVector, right(pLength), pIndex, pFlag);
	
	if (pFlag){
		if (pLength > 1 && pVector[pLength + pIndex - 1].getID() < pVector[parent(pLength) + pIndex - 1].getID() ){
			swap(pVector[pLength+pIndex - 1], pVector[parent(pLength) + pIndex - 1]);
		}	
	} else{
		if (pLength > 1 && pVector[pLength + pIndex - 1].getID() > pVector[parent(pLength) + pIndex - 1].getID() ){
			swap(pVector[pLength+pIndex - 1], pVector[parent(pLength) + pIndex - 1]);
		}
	}
	
}

template <class T> vector<Node<T>* > Heapsort<T>::heapSort(Monticulo<T> pMonticulo, bool pFlag){
	vector<Node<T>* > tempVec = pMonticulo.getElements();
	int n = tempVec.size();
	for (int i = 0 ; i < tempVec.size() ; i++){
		heap(tempVec, 1, i, pFlag);
	}
	return tempVec;
}

//Monticulo methods
template <class T> void Monticulo<T>::setElements(vector< Node<T>* > pElements){
	elements = pElements;
}
	
template <class T> void Monticulo<T>::addElement(T pElement, int pID){
	Node<T> *newElement = new Node<T>(pElement, pID);
	int position = searchElement(pID);
	elements[position] = newElement;
}

template <class T> vector<Node<T>* > Monticulo<T>::getElements(){
	return elements;
}

template <class T> void Monticulo<T>::printElements(){
	for (int i = 0; i < elements.size(); i++){
		cout << elements.at(i).getID() << ' ';
	}
	cout << endl;
}

template <class T> int Monticulo<T>::searchElement(int pID){
	int i = 0;
	Node<T> *result = elements[i];
	while(result != NULL){
		if (result->getID() < pID)
			i++;
		else
			i++;
		result = elements[i];
	}
	return i;
}

template <class T> int Node<T>::getID(){
	return this->ID;
}

template <class T> T Node<T>::getValue(){
	return this->Value;
}
