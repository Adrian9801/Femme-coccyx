#ifndef ARBOLAVL_H
#define ARBOLAVL_H
#include <jni.h>
#include <iostream>
#include <string>
#include <sstream>
#include <vector>
#include "NodoComparable.h"
using namespace std;
template <class T> class ArbolAvl;
template <class T> class NodoAvl : public NodoComparable<T>{
	public:
		NodoAvl(T pDato, int pID): NodoComparable<T>(pDato,pID){
			hijoIzq = NULL;
			hijoDer = NULL;
			FB = 0;
		}
		NodoAvl<T>* getHijoDer(){
			return hijoDer;
		}
		NodoAvl<T>* getHijoIzq(){
			return hijoIzq;
		}
	private:
		NodoAvl<T>* hijoIzq;
		NodoAvl<T>* hijoDer;
		int FB;
		friend class ArbolAvl<T>;
};

template <class T> class ArbolAvl
{
	public:
		ArbolAvl(){
			raiz = NULL;
			Hh = false;
		}
		void insertarBalanceado(NodoAvl<T> *&, T, int);
		void rotacionDobleIzquierda(NodoAvl<T> *&, NodoAvl<T> *&);
		void rotacionDobleDerecha(NodoAvl<T> *&, NodoAvl<T> *&);
		void rotacionSimpleDerecha(NodoAvl<T> *&, NodoAvl<T> *&);
		void rotacionSimpleIzquierda(NodoAvl<T> *&, NodoAvl<T> *&);
		NodoAvl<T>* crearNodo(T, int);
		void insert(T, int);
		bool isEmpty();
		NodoAvl<T>* buscar(int);
		NodoAvl<T>* getRaiz();
	private:
		NodoAvl<T> *raiz;
		bool Hh;
};
#endif
