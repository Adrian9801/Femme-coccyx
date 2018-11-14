package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;

import tda.BAsteriskTree;
import tda.Hash;
import tda.SimpleList;
import logic.Tag;
import logic.WordSample;
import analysis.AnalyzeText;
import library.IConstants;

public class Controller implements IConstants{
	private ArrayList<Tag> ListTags;
	
	public Controller() {
		ListTags = new ArrayList<Tag>();
	}
	
	public void analyzeText(String pPath) {
		AnalyzeText textAnalysis = new AnalyzeText();
		textAnalysis.analize(pPath);
		String text = textAnalysis.getText();
		int region = text.length()/NUM_REGIONS;
		int initialRegion = 0;
		int finaldRegion = region;
		for(int i = 0; i < NUM_REGIONS; i++) {
			if(i+1 >= NUM_REGIONS)
				finaldRegion = text.length();
			splitText(text.substring(initialRegion, finaldRegion) ,i);
			initialRegion = finaldRegion;
			finaldRegion+=region;
		}
		System.out.println("Prueba");
	}
	// Saber bien que guarda los samples del texto
	private void splitText(String pText, int pRegion) {
		StringTokenizer stringToken = new StringTokenizer(pText, " ,.:;");
		int numberWords = (stringToken.countTokens()*30)/100;
		int number = 40;
		while (stringToken.hasMoreTokens() && numberWords > 0 && number > 0) {
			WordSample sample = new WordSample(stringToken.nextToken(), pRegion); 
			numberWords--;
			number--;
		}
	}
	
	public ArrayList<Tag> getListTags() {
		return ListTags;
	}
	//Si el hash se ocupa mas adelante crear uno para cada region de lo contrario limpiar
	public void generateRegions(BufferedImage buffer) {
		int initialPosX = 0;
		int initialPosY = 0;
		int finalPosX = buffer.getWidth()/4;
		int finalPosY = buffer.getHeight()/4;
		for(int region = 0; region < NUM_REGIONS; region++) {
			//extractPixeles(buffer, initialPosX, initialPosY, finalPosX, finalPosY, region);
			if((region+1)%4 != 0) {
				initialPosX = finalPosX;
				finalPosX = finalPosX + buffer.getWidth()/4;
			}
			else {
				initialPosX = 0;
				finalPosX = buffer.getWidth()/4;
				initialPosY = finalPosY;
				finalPosY = finalPosY + buffer.getHeight()/4;
			}
		}
		//assignTags();
	}
}
