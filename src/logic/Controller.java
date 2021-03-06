package logic;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.StringTokenizer;
import tda.BAsteriskTree;
import tda.Grafo;
import tda.Hash;
import tda.Nodo;
import tda.SimpleList;
import analysis.AnalyzeImage;
import analysis.AnalyzeText;
import library.IConstants;

public class Controller implements IConstants{
	private static Controller Instance = null;
	private ArrayList<Tag> ListTags;
	private Hash<Sample> HashTable;
	private SimpleList<Sample> ListSamples;
	private BAsteriskTree<WordSample> WordsTree;
	private Grafo<Sample> grafoSamples;
	private double TotalTags;
	
	public static Controller getInstance() {
		if(Instance == null)
			Instance = new Controller();
		return Instance;
	}
	
	private Controller() {
		ListTags = new ArrayList<Tag>();
		HashTable = new Hash<Sample>();
		ListSamples = new SimpleList<Sample>();
		WordsTree = new BAsteriskTree<WordSample>(6);
		ListTags = new ArrayList<Tag>();
		grafoSamples = new Grafo<Sample>();
		TotalTags = 0;
	}
	
	public void recibirNode(Nodo<Sample> pNode) {
		grafoSamples.addVertice(pNode);
	}
	
	public void recibirGrafo(int pPos, int pDestino,int pDistancia) {
		grafoSamples.addArco(pPos, pDestino, pDistancia);
	}
	
	public void getTagsPorcentaje() {
		for(int i = ListTags.size()-1; ListTags.size() > 10; i--) {
			ListTags.remove(i);
		}
	}
	
	public void analyzeImage(String pUrl) {
		AnalyzeImage imageAnalysis = new AnalyzeImage();
		imageAnalysis.analyze(pUrl);
		imageAnalysis.tags(ListTags);
		for(Tag tag : ListTags)
			TotalTags+=tag.getConfidence();
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
	}
	
	private void splitText(String pText, int pRegion) {
		StringTokenizer stringToken = new StringTokenizer(pText, " ,.:;");
		ArrayList<String> listWords = new ArrayList<String>();
		while(stringToken.hasMoreTokens()) {
			listWords.add(stringToken.nextToken());
		}
		int numberWords = (stringToken.countTokens()*30)/100;
		int posWord = (int)(Math.random()*(numberWords-1))+1;
		for(int i = 0; numberWords > i; i++) {
			WordSample sample = new WordSample(listWords.get(posWord), pRegion); 
			WordsTree.add(sample, sample.getWord().substring(0, 1));
			posWord = (int)(Math.random()*(numberWords-1))+1;
		}
	}
	
	public void assignTags() {
		ArrayList<Tag> tags = getListTags();
		double count = 0.0;
		int index = 0;
		int control = 1;
		if(tags.size() >= 2)
			count = tags.get(0).getConfidence() + tags.get(1).getConfidence();
		else {
			count = tags.get(0).getConfidence();
			control = 0;
		}
		count = (count*ListSamples.getLength())/TotalTags;
		for(int i = 0; i < ListSamples.getLength(); i++) {
			if(count > 0) {
				index*=control;
				ListSamples.getFirst().setTag(tags.get(index));
				index*=-1;
				index++;
				count--;
			}
			else {
				if(tags.size() >= 4) {
					tags.remove(0);
					tags.remove(0);
					count+=tags.get(1).getConfidence();
				}
				else if(tags.size() >= 3) {
					tags.remove(0);
					tags.remove(0);
					control=0;
				}
				else
					count+=tags.get(control).getConfidence();
				count+=tags.get(0).getConfidence();
				count = (count*ListSamples.getLength())/TotalTags;
				ListSamples.getFirst().setTag(tags.get(0));
			}
		}
		enviarSamples(ListSamples.getFirstNode(), this);
		getTagsPorcentaje();
		grafoSamples.prim();
		grafoSamples.getManMinNodes();
		grafoSamples.crearHashCaminos();
	}
	 
	public ArrayList<Tag> getListTags() {
		return ListTags;
	}
	
	public void generateRegions(BufferedImage buffer) {
		int initialPosX = 0;
		int initialPosY = 0;
		int finalPosX = buffer.getWidth()/4;
		int finalPosY = buffer.getHeight()/4;
		for(int region = 0; region < NUM_REGIONS; region++) {
			extractPixeles(buffer, initialPosX, initialPosY, finalPosX, finalPosY, region);
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
		assignTags();
	}
	
	private void extractPixeles(BufferedImage buffer, int pInitialPosX, int pInitialPosY, int pFinalPosX, int pFinalPosY, int pRegion) {
		int regionWidth = buffer.getWidth()/4;
		int regionHeigth = buffer.getHeight()/4;
		int numPixeles = (int)(Math.random()*(MAX_PORCENTAJE-MIN_PORCENTAJE))+MIN_PORCENTAJE;
		numPixeles = (regionWidth*regionHeigth*numPixeles)/100;
		for(int i = 0; numPixeles > i; i++) {
			int posX = (int)(Math.random()*(pFinalPosX-pInitialPosX))+pInitialPosX;
			int posY = (int)(Math.random()*(pFinalPosY-pInitialPosY))+pInitialPosY;
			Sample sample = new Sample(new Color(buffer.getRGB(posX, posY)), pRegion);
			sample.addPixel(posX,posY);
			int key = sample.getSampleColor().getRGB()*(-1);
			Sample sampleHash = (Sample) HashTable.functionHush(sample, key);
			if(sampleHash != null)
				sampleHash.addPixel(posX, posY);
			else
				ListSamples.add((Sample) HashTable.getValue(key));
		}
	}
	
	private native Nodo<Sample> sendSamples(Nodo<Sample> pNode, Controller controller);
	 
    static {
        System.load("C:\\Users\\adri-\\OneDrive\\Escritorio\\Estructuras\\Proyecto git\\Femme-coccyx\\src/ConexionJNI.dll");
    }
 
    public Nodo<Sample> enviarSamples(Nodo<Sample> pNode, Controller controller) {
        return sendSamples(pNode, controller);
    }
}
