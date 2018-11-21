#include "ArbolAVL.h"
template <class T>
bool ArbolAvl<T>::isEmpty(){
	return raiz == NULL;
}

template <class T>
NodoAvl<T>* ArbolAvl<T>::buscar(int pId){
	NodoAvl<T> *SearchNodo = raiz;
	if(!isEmpty()){
		while(SearchNodo != NULL){
			if(SearchNodo->Id >= pId){
				if(SearchNodo->Id == pId)
					break;
				SearchNodo = SearchNodo->hijoIzq;
			}
			else
				SearchNodo = SearchNodo->hijoDer;
		}
	}
	return SearchNodo;
}

template <class T>
NodoAvl<T>* ArbolAvl<T>::getRaiz(){
	return raiz;
}

template <class T>
NodoAvl<T>* ArbolAvl<T>::crearNodo(T pDato, int pId){
	NodoAvl<T> *newNodo = new NodoAvl<T>(pDato, pId);
	return newNodo;
}

template <class T>
void ArbolAvl<T>::insert(T pDato, int pId){
	Hh = false;
	if(raiz == NULL){
		raiz = crearNodo(pDato,pId);
	}
	else{
		insertarBalanceado(raiz,pDato,pId);
	}	
}

template <class T>
void ArbolAvl<T>::insertarBalanceado(NodoAvl<T> *&pRaiz, T pDato, int pId)
{
    NodoAvl<T> *Nodo1;
    if(pRaiz == NULL)
	{
        pRaiz = crearNodo(pDato,pId);
        Hh = true;
    }
	else
	{
	    if(pId <= pRaiz->ID)
		{
	        insertarBalanceado(pRaiz->hijoIzq, pDato, pId);
		    if(Hh)
			{
		        switch(pRaiz->FB)
				{
		            case 1: pRaiz->FB = 0;
		            	Hh = false;
		            	break;
		            case 0: pRaiz->FB = -1;
		            	break;
		            case -1: Nodo1 = pRaiz->hijoIzq;
		            	if(Nodo1->FB == -1)
							rotacionSimpleIzquierda(pRaiz, Nodo1);
		            	else
							rotacionDobleIzquierda(pRaiz, Nodo1);
		            	Hh = false;
		            	break;
		        }
		    }
	    }
		else
		{
	        insertarBalanceado(pRaiz->hijoDer, pDato, pId);
	        if(Hh)
			{
	            switch(pRaiz->FB)
				{
	                case -1: pRaiz->FB = 0;
	                    Hh = false;
	                    break;
	                case 0: pRaiz->FB = 1;
	                    break;
	                case 1: Nodo1 = pRaiz->hijoDer;
	                    if(Nodo1->FB == 1)
							rotacionSimpleDerecha(pRaiz, Nodo1);
	                    else
							rotacionDobleDerecha(pRaiz, Nodo1);
	                    Hh = false;
	                    break;
	            }
	        }
	    }
    }
}

template <class T>
void ArbolAvl<T>::rotacionDobleIzquierda(NodoAvl<T> *&pNodo, NodoAvl<T> *&pNodo1)
{
	NodoAvl<T> *Nodo2 = pNodo1->hijoDer;
	pNodo1->hijoDer = Nodo2->hijoIzq;
	Nodo2->hijoIzq = pNodo1;
    pNodo->hijoIzq = Nodo2->hijoDer;
    Nodo2->hijoDer = pNodo;
    if(Nodo2->FB == 1)
        pNodo1->FB = -1;
    else
        pNodo1->FB = 0;
    if(Nodo2->FB == -1)
        pNodo->FB = 1;
    else
        pNodo->FB = 0;
    Nodo2->FB = 0;
    pNodo = Nodo2;
}

template <class T>
void ArbolAvl<T>::rotacionSimpleIzquierda(NodoAvl<T> *&pNodo, NodoAvl<T> *&pNodo1)
{
    pNodo->hijoIzq = pNodo1->hijoDer;
    pNodo1->hijoDer = pNodo;
    if(pNodo1->FB == -1)
	{
        pNodo->FB = 0;
        pNodo1->FB = 0;
    }
	else
	{
        pNodo->FB = -1;
        pNodo1->FB = 1;
        Hh = false;
    }
    pNodo = pNodo1;
}

template <class T>
void ArbolAvl<T>::rotacionDobleDerecha(NodoAvl<T> *&pNodo, NodoAvl<T> *&pNodo1)
{
    NodoAvl<T> *Nodo2 = pNodo1->hijoIzq;
    pNodo1->hijoIzq = Nodo2->hijoDer;
    Nodo2->hijoDer = pNodo1;
    pNodo->hijoDer = Nodo2;
    pNodo->hijoDer = Nodo2->hijoIzq;
    Nodo2->hijoIzq = pNodo;
    if(Nodo2->FB == 1)
        pNodo->FB = -1;
    else
        pNodo->FB = 0;
    if(Nodo2->FB == -1)
        pNodo1->FB = 1;
    else
        pNodo1->FB = 0;
    Nodo2->FB = 0;
    pNodo = Nodo2;
}

template <class T>
void ArbolAvl<T>::rotacionSimpleDerecha(NodoAvl<T> *&pNodo, NodoAvl<T> *&pNodo1)
{
    pNodo->hijoDer = pNodo1->hijoIzq;
    pNodo1->hijoIzq = pNodo;
	
    if(pNodo1->FB == 1)
	{
        pNodo->FB = 0;
        pNodo1->FB = 0;
    }
	else
	{
        pNodo->FB = 1;
        pNodo1->FB = -1;
        Hh = false;
    }
    pNodo = pNodo1;
}
