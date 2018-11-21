#ifndef NODOCOMPARABLE_H
#define NODOCOMPARABLE_H
#include <iostream>
#include <string>
#include <sstream>
#include <vector>
using namespace std;
template <class T> class NodoComparable
{
	public:
		NodoComparable(T pValue, int pID){
			Value = pValue;
			ID = pID;
		}
		
		T getValue(){
			return Value;
		}
		int getID(){
			return ID;
		}
	protected:
		T Value;
		int ID;
};
#endif
