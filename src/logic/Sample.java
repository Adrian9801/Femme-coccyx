package logic;

import java.awt.Color;
import java.util.ArrayList;

public class Sample {
	private Color SampleColor;
	private ArrayList<Pixel> Pixeles;
	private int Region;
	private Tag Tag;
	
	public Sample(Color pSampleColor, int pRegion) {
		SampleColor = pSampleColor;
		Pixeles = new ArrayList<Pixel>();
		Region = pRegion;
	}
	
	public void addPixel(int pPosX, int pPosY) {
		Pixeles.add(new Pixel(pPosX, pPosY));
	}

	public Color getSampleColor() {
		return SampleColor;
	}

	public ArrayList<Pixel> getPixeles() {
		return Pixeles;
	}

	public Tag getTag() {
		return Tag;
	}

	public void setTag(Tag tag) {
		Tag = tag;
	}

	public int getRegion() {
		return Region;
	}

	public void setRegion(int region) {
		Region = region;
	}
}