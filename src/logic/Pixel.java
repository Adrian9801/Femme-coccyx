package logic;

public class Pixel {
	private int PosX;
	private int PosY;
	
	public Pixel(int pPosX, int pPosY) {
		PosX = pPosX;
		PosY = pPosY;
	}

	public int getPosX() {
		return PosX;
	}
	public void setPosX(int posX) {
		PosX = posX;
	}
	public int getPosY() {
		return PosY;
	}
	public void setPosY(int posY) {
		PosY = posY;
	}
}