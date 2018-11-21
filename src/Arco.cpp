#include "Arco.h"
template <class T>
NodeGraph<T>* Arco<T>::getDestino(){
	return Destino;	
}

template <class T>
int Arco<T>::getDistancia(){
	return Distancia;
}
