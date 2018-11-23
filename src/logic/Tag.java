package logic;

public class Tag {
	private String Name;
	private Double Confidence; 
	
	public Tag(String pName, Double pConfidence) {
		Name = pName;
		Confidence = pConfidence;
	}
	
	public Tag(String pName) {
		Name = pName;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Double getConfidence() {
		return Confidence;
	}

	public void setConfidence(Double confidence) {
		Confidence = confidence;
	}
}