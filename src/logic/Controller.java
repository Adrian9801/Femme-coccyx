package logic;

import java.util.StringTokenizer;

import analysis.AnalyzeText;
import library.IConstants;

public class Controller implements IConstants{
	
	public Controller() {
		
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
}
