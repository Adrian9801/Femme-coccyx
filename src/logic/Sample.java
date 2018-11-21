package logic;

import java.awt.Color;
import java.util.ArrayList;

public class Sample {
	private Color SampleColor;
	private ArrayList<Pixel> Pixeles;
	private Tag Tag;
	
	public Sample(Color pSampleColor) {
		SampleColor = pSampleColor;
		Pixeles = new ArrayList<Pixel>();
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
}