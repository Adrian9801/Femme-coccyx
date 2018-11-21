/* Replace "dll.h" with the name of your header */
#include <jni.h>
#include <stdio.h>
#include <iostream>
#include <windows.h>
#include <sstream>
#include <string.h>
#include "Logic_Nodo.h"
#include "ArbolAvl.cpp"
#include "Graph.cpp"
#include "Monticulo.cpp"
#include "Arco.cpp"
using namespace std;

JNIEXPORT jobject JNICALL Java_logic_Controller_sendSamples
(JNIEnv *env, jobject obj, jobject pNodo, jobject pController){
	jclass classNodo = env->FindClass("Logic/Nodo");
	jclass classController = env->FindClass("Logic/Controller");
	jmethodID metodoID;
	jmethodID metodoNextNode;
	jmethodID metodogetNode;
	jmethodID metodogetGrafo;
	
	metodogetNode = env->GetMethodID(classController, "recibirNode", "(LLogic/Nodo;)V");
	metodogetGrafo = env->GetMethodID(classController, "recibirGrafo", "(IIIZ)V");
	Monticulo<jobject> mon = Monticulo<jobject>();
	ArbolAvl<jobject> Tree = ArbolAvl<jobject>();
	Graph<jobject> grafo = Graph<jobject>();
	metodoID = env->GetMethodID(classNodo, "getID", "()I");
	int ID = env->CallIntMethod(pNodo, metodoID);
	Tree.insert(pNodo,ID);
	mon.addElement(pNodo,ID);
	ID = -1;
	metodoNextNode = env->GetMethodID(classNodo, "getNext", "()LLogic/Nodo;");
	while(ID != 0){
		pNodo = env->CallObjectMethod(pNodo, metodoNextNode);
		metodoID = env->GetMethodID(classNodo, "getID", "()I");
		ID = env->CallIntMethod(pNodo, metodoID);
		if(ID != 0){
			Tree.insert(pNodo,ID);
			mon.addElement(pNodo,ID);
		}
	}
	grafo.comparar(Tree, mon);
	vector<NodeGraph<jobject>*> listVertices = grafo.getVertices();
	for(int i = 0; i < listVertices.size(); i++){
		pNodo = listVertices[i]->getValue()->getValue();
		env->CallVoidMethod(pController, metodogetNode, pNodo);
	}
	vector<Arco<jobject>*> adyacentes;
	for(int i = 0; i < listVertices.size(); i++){
		adyacentes = listVertices[i]->getVertices();
		for(int index = 0; index < listVertices[i]->getVertices().size(); index++){
			env->CallVoidMethod(pController, metodogetGrafo, i, adyacentes[index]->getDestino()->getValue()->getID(),adyacentes[index]->getDistancia(), adyacentes[index]->getDestino()->isAvl());
		}
	}
	return pNodo;
}
