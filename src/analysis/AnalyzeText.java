package analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AnalyzeText {
private String Text;
	
	public AnalyzeText() {
		Text = "";
	}
	
	public void analize(String pPath) {
		File file = new File(pPath);
		try {
			String line; 
			@SuppressWarnings("resource")
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			while ((line = bufferedReader.readLine()) != null) {
				if(line.length() > 1)
					Text+=line.toLowerCase();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getText() {
		return Text;
	}
}