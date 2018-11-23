package tda;

public class Arco<T> {
	private GrafoNode<T> Destino;
	private int Peso;
	
	public Arco(GrafoNode<T> pDestino , int pPeso) {
		Destino = pDestino;
		Peso = pPeso;
	}

	public GrafoNode<T> getDestino() {
		return Destino;
	}

	public int getPeso() {
		return Peso;
	}
}
