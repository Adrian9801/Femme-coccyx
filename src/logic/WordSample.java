package logic;

public class WordSample {
	private String Word;
	private int Region;
	
	public WordSample(String pWord, int pRegion) {
		Word = pWord;
		Region = pRegion;
	}
	
	public String getWord() {
		return Word;
	}
	
	public int getRegion() {
		return Region;
	}
}